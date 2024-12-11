# k8s 集群部署 Zadig

本文基于 CentOS7.9 部署 Zadig。

## 注意事项

- 若需要使用 Zadig 内置的存储组件（MySQL / MongoDB / MinIO），在安装之前，需要配置 Kubernetes `默认`的 StorageClass，以支持创建 PVC 用于数据持久化。

  - 这里，我们使用内置的 MinIO。因此需要安装NFS并配置StorageClass。

- 受限于部分类型集群网络插件及其配置，Service 的后端 Pod 可能无法通过该 Service 访问自身，在安装之前请确保集群中不存在此问题。

  - 执行 calicoctl get bgpconfig default -o yaml 确保 nodeToNodeMeshEnabled: true

    ```shell
    [root@k8s01 soft]# calicoctl get bgpconfig default -o yaml
    apiVersion: projectcalico.org/v3
    kind: BGPConfiguration
    metadata:
      creationTimestamp: "2024-10-25T06:26:05Z"
      name: default
      resourceVersion: "1036"
      uid: bc38f685-90ee-43c0-bfaa-ed0131ec8cb7
    spec:
      asNumber: 64512
      listenPort: 179
      logSeverityScreen: Info
      nodeToNodeMeshEnabled: true
    ```

- 配置 kube-dns 服务，以支持服务和 Pod 之间的按名称寻址。

  - 在Kubernetes集群中，`kube-dns`（现在通常称为`CoreDNS`）负责为服务和Pod提供DNS解析。

    ```shell
    [root@k8s01 soft]# kubectl get pods -n kube-system -l k8s-app=kube-dns
    NAME                       READY   STATUS    RESTARTS      AGE
    coredns-55859db765-4hmcf   1/1     Running   2 (32m ago)   163m
    coredns-55859db765-q92mh   1/1     Running   1 (32m ago)   77m
    
    [root@k8s01 soft]# kubectl get configmap coredns -n kube-system -o yaml
    apiVersion: v1
    data:
      Corefile: |
        .:53 {
            errors
            health {
                lameduck 5s
            }
            ready
            kubernetes cluster.local in-addr.arpa ip6.arpa {
              pods insecure
              fallthrough in-addr.arpa ip6.arpa
            }
            prometheus :9153
            forward . /etc/resolv.conf {
              prefer_udp
              max_concurrent 1000
            }
            cache 30
    
            loop
            reload
            loadbalance
        }
    kind: ConfigMap
    metadata:
      annotations:
        kubectl.kubernetes.io/last-applied-configuration: |
          {"apiVersion":"v1","data":{"Corefile":".:53 {\n    errors\n    health {\n        lameduck 5s\n    }\n    ready\n    kubernetes cluster.local in-addr.arpa ip6.arpa {\n      pods insecure\n      fallthrough in-addr.arpa ip6.arpa\n    }\n    prometheus :9153\n    forward . /etc/resolv.conf {\n      prefer_udp\n      max_concurrent 1000\n    }\n    cache 30\n\n    loop\n    reload\n    loadbalance\n}\n"},"kind":"ConfigMap","metadata":{"annotations":{},"labels":{"addonmanager.kubernetes.io/mode":"EnsureExists"},"name":"coredns","namespace":"kube-system"}}
      creationTimestamp: "2024-10-25T06:26:42Z"
      labels:
        addonmanager.kubernetes.io/mode: EnsureExists
      name: coredns
      namespace: kube-system
      resourceVersion: "1379"
      uid: bab853fe-e828-496f-a714-a4e44dc6bdad
      
      # 配置CoreDNS支持服务和Pod之间的按名称寻址。cluster.local 是 Kubernetes 集群的默认域名。不清楚可以通过 kubectl get configmap coredns -n kube-system -o yaml 查看，我这里是：cluster.local
    kubectl edit configmap coredns -n kube-system
    kubernetes cluster.local in-addr.arpa ip6.arpa {
        pods insecure
        upstream
        fallthrough in-addr.arpa ip6.arpa
    }
    # 释义
    # kubernetes diguapao-k8s-cluster.local in-addr.arpa ip6.arpa 这一行配置确保了CoreDNS能够正确处理Kubernetes集群内的DNS查询，包括服务、Pod以及反向DNS查询。通过这些配置，你可以实现服务和Pod之间的按名称寻址，并支持反向DNS解析。
    # pods insecure 允许CoreDNS解析Pod的主机名。
    # upstream 允许CoreDNS将无法解析的请求转发到上游DNS服务器。
    # fallthrough in-addr.arpa ip6.arpa 允许反向DNS查找。
    kubectl delete pod -l k8s-app=kube-dns -n kube-system # 这将删除现有的CoreDNS Pod，并由Kubernetes自动重新创建它们，从而应用新的配置
    
    # !!!需要注意的是， upstream 这个配置可能有导致k8s集群出现问题。不建议先行设置。后期zadig有问题再针对性处理。
    ```
    
    

## 安装 Helm

```shell
ssh root@192.168.11.101
sudo mkdir soft && cd soft
# 下载并安装Helm
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod 700 get_helm.sh
./get_helm.sh

# 执行日志：
# Downloading https://get.helm.sh/helm-v3.16.1-linux-amd64.tar.gz
# Verifying checksum... Done.
# Preparing to install helm into /usr/local/bin
# helm installed into /usr/local/bin/helm

# 验证Helm安装
helm version
```

## 安装 NFS

```shell
ssh root@192.168.11.101

#192.168.11.101 安装 NFS 服务端，root用户
#安装 nfs-utils
yum info nfs-utils
yum install nfs-utils # rpcbind 作为依赖会自动安装
systemctl enable rpcbind
systemctl enable nfs
systemctl start rpcbind && systemctl status rpcbind
systemctl start nfs && systemctl status nfs
firewall-cmd --zone=public --permanent --add-service={rpc-bind,mountd,nfs}
firewall-cmd --reload

# 让客户端具备读写权限
sudo mkdir -p /root/nfs/
sudo chmod 755 /root/nfs # 让nfs客户端可访问
sudo chmod -R 777 /root/nfs
sudo ls -l /root/nfs

vi /etc/exports
#追加以下内容
# 1. 只允许主机 k8s01 访问
/root/nfs k8s01(rw,sync,no_root_squash,no_all_squash)
# 2. 根据 IP 地址范围限制访问
/root/nfs 192.168.0.0/24(rw,sync,no_root_squash,no_all_squash)
# 3. 使用 * 表示访问不加限制
/root/nfs *(rw,sync,no_root_squash,no_all_squash)

#重启 nfs
systemctl restart nfs
# 查看 nfs 服务端的共享目录
showmount -e localhost

#192.168.11.101、192.168.11.102、192.168.11.103 安装 NFS 客户端，root用户
sudo yum install nfs-utils
sudo systemctl enable rpcbind && sudo systemctl start rpcbind && sudo systemctl status rpcbind
# 查看服务端的共享目录
showmount -e 192.168.11.101
mkdir -p /mnt/nfs/
mount -t nfs 192.168.11.101:/root/nfs /mnt/nfs #客户端创建并挂载对应目录
df -hT //mnt/nfs
mount | grep /mnt/nfs

#配置自动挂载
#客户端编辑 /etc/fstab
vi /etc/fstab
#追加
# For nfs
192.168.11.101:/root/nfs                          /mnt/nfs       nfs     defaults             0       0
#重新加载systemctl
systemctl daemon-reload
mount | grep /mnt/nfs

#NFS服务测试写入，客户端看是否有内容
time dd if=/dev/zero of=/root/nfs/test-nfs-speed bs=8k count=1024
time dd if=/root/nfs/test-nfs-speed of=/dev/null bs=8k count=1024

#参考：https://github.com/kubernetes-sigs/nfs-subdir-external-provisioner/blob/master/charts/nfs-subdir-external-provisioner/README.md
helm repo add nfs-subdir-external-provisioner https://kubernetes-sigs.github.io/nfs-subdir-external-provisioner/
#这里指定 storageclass 为：second-nfs-client
helm install second-nfs-subdir-external-provisioner nfs-subdir-external-provisioner/nfs-subdir-external-provisioner \
    --set nfs.server=192.168.11.101 \
    --set nfs.path=/root/nfs \
    --set storageClass.name=second-nfs-client \
    --set storageClass.provisionerName=k8s-sigs.io/second-nfs-subdir-external-provisioner

#删除
#helm delete second-nfs-subdir-external-provisioner

```

nfs-subdir-external-provisioner 是一个用于动态提供 NFS 存储的 Helm Chart。安装完后，查看 storageClass.name，部署 zadig 要用。

```shell
# Kubernetes集群列出所有 StorageClass
kubectl get storageclass
# 获取 StorageClass 名称
kubectl get storageclass | grep nfs-subdir-external-provisioner
# 我的
[root@k8s03 ~]# kubectl get storageclass | grep nfs-subdir-external-provisioner
second-nfs-client   k8s-sigs.io/second-nfs-subdir-external-provisioner   Delete          Immediate           true                   23h
# 可见storageclass为：second-nfs-client

# 验证 PVC 和 PV
kubectl get pvc
kubectl get pv

# 出现以下问题的处理方法
root@node2:~/soft/nfs/storage# kubectl get pvc
No resources found in default namespace.
# 这里不用担心，部署 zadig 后自动绑定。部署 zadig 后验证
kubectl get pvc -n zadig
# 我的
root@node2:~/soft/nfs/storage# kubectl get pvc -n zadig
NAME       STATUS   VOLUME                                     CAPACITY   ACCESS MODES   STORAGECLASS   AGE
kr-minio   Bound    pvc-668d7149-1b74-4d91-a23a-7d847b65871b   20Gi       RWO            nfs-client     6m56s

```



## 部署 Zadig

官方文档：[官方脚本 | Zadig 文档](https://docs.koderover.com/zadig/Zadig v3.1/install/install-on-k8s/#准备资源)

```shell
 # 下载Zadig安装脚本（生产使用）
mkdir -p /root/soft/zadig && cd /root/soft/zadig && curl -LO https://github.com/koderover/zadig/releases/download/v3.1.0/install.sh && chmod +x ./install.sh

export NAMESPACE=zadig
export IP=192.168.11.101
export PORT=32767
export STORAGE_CLASS=second-nfs-client #这里是之前安装nfs后获取的
export EMAIL=changgelp@163.com
export PASSWORD=zadig
# 配置高可用的 MySQL。
# 安装前需要手动在该 MySQL 实例中创建名为 dex 和 user 的 database
export MYSQL_HOST=192.168.11.66
export MYSQL_PORT=3306
export MYSQL_USERNAME=root
export MYSQL_PASSWORD=root
# 配置高可用的 MongoDB
export MONGO_URI="mongodb://admin:admin@192.168.11.66:27017/admin?authSource=admin"
export MONGO_DB=admin
export HELM_REPO=https://koderover.tencentcloudcr.com/chartrepo/chart

./install.sh

# 秘钥：D78DA10299D32797429D31B008B951B3 
# 由安装过程生成，用于数据加密解密，第一次安装后请妥善保存。
# 重装系统时需设置 ENCRYPTION_KEY，才能保证之前的数据可以被正确解密

# 部署失败删除命名空间重新部署
# kubectl delete namespace zadig
# 或者卸载
#根据实际安装的 namespace 修改
# export NAMESPACE=zadig
#根据实际安装的版本，选择对应版本的卸载脚本
# curl -SsL https://github.com/koderover/zadig/releases/download/v3.1.0/uninstall.sh |bash
# 或者
# curl -LO https://github.com/koderover/zadig/releases/download/v3.1.0/uninstall.sh && chmod +x ./uninstall.sh && ./uninstall.sh

# 出现问题，长时间无法部署好
root@changge:~/soft/zadig# kubectl -n zadig get po
NAME                    READY   STATUS    RESTARTS   AGE
gateway-certgen-v6sq4   0/1     Pending   0          41m

# 查看pod描述
kubectl -n zadig describe pod gateway-certgen-v6sq4
# 结果：
Events:
  Type     Reason            Age   From               Message
  ----     ------            ----  ----               -------
  Warning  FailedScheduling  42m   default-scheduler  0/1 nodes are available: 1 node(s) had taint {node-role.kubernetes.io/master: }, that the pod didn't tolerate.
  Warning  FailedScheduling  42m   default-scheduler  0/1 nodes are available: 1 node(s) had taint {node-role.kubernetes.io/master: }, that the pod didn't tolerate.

# kubectl taint nodes <master-node-name> node-role.kubernetes.io/master:NoSchedule-
kubectl taint nodes 192.168.11.128 node-role.kubernetes.io/master:NoSchedule-

# 如果无法pull镜像，手动拉取下来
vim /etc/resolv.conf
# 追加
nameserver 223.5.5.5
nameserver 223.6.6.6
nameserver 8.8.8.8
nameserver 8.8.4.4
# 刷新 DNS 缓存
sudo resolvectl flush-caches
# 手动拉取镜像
ctr images pull koderover.tencentcloudcr.com/koderover-public/user:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-ee/plutus-vendor:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-public/cron:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-public/dex:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-public/time-nlp:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-ee/vendor-portal:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-public/zadig-portal:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-public/init:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-public/aslan:3.1.0
ctr images pull koderover.tencentcloudcr.com/koderover-public/ua:3.1.0

# 验证安装结果
kubectl -n zadig get pod

# 检查事件
kubectl get events -n zadig

# 检查服务状态
kubectl get svc -n zadig

# 删除 zadig 命名空间中不正常的 Pod，并重启所有不正常的 Deployment
apt install jq -Y
NAMESPACE="zadig"; kubectl get pods -n $NAMESPACE -o json | jq -r '.items[] | select(.status.phase != "Running") | .metadata.name' | xargs -I {} kubectl delete pod {} -n $NAMESPACE; kubectl get deployments -n $NAMESPACE -o json | jq -r '.items[] | select(.status.unavailableReplicas > 0) | .metadata.name' | xargs -I {} kubectl rollout restart deployment {} -n $NAMESPACE

# 所有非 Running 状态的 Pod 名称，其其他简要字段信息
NAMESPACE="zadig"; kubectl get pods -n $NAMESPACE -o json | jq -r '.items[] | select(.status.phase != "Running") | [.metadata.name, .status.phase, .status.reason, .spec.nodeName] | @tsv'
```



部署成功截图：

![image-20241031224752166](img.assets/image-20241031224752166.png)



### 问题处理

#### Unable to get an update from the "nfs-subdir-external-provisioner" chart repository (https://kubernetes-sigs.github.io/nfs-subdir-external-provisioner/)

出现这个问题是 helm 无法从远程仓库获取 Chart 更新，可在网络允许时通过以下命令更新 Chart 仓库：

helm repo update



#### Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "8fa5f360c63ff7ceb1308d24dd4d9452a89ee45535629fc4fbb203640e6fbe47": plugin type="calico" failed (add): error getting ClusterInformation: connection is unauthorized: Unauthorized

出现这个问题，表明 Calico 网络插件在尝试设置网络时遇到了授权问题。

需访问：[Kuboard](http://192.168.11.101/kubernetes/default/cluster/serviceaccount?fieldSelector=metadata.namespace%3Dkube-system) 为 calico-node 和 calico-kube-controllers 创建 Token Secret，如下图：

![image-20241031225332573](img.assets/image-20241031225332573.png)

![image-20241031225404469](img.assets/image-20241031225404469.png)

![image-20241031225431830](img.assets/image-20241031225431830.png)



然后 RoleBinding ：http://192.168.11.101/kubernetes/default/cluster/serviceaccount/kube-system/calico-node

![image-20241031225544672](img.assets/image-20241031225544672.png)

进入后，为 zadig 授权

![image-20241031225638677](img.assets/image-20241031225638677.png)

calico-kube-controllers 也是同样的操作。然后重启 Calico 组件：

```shell
# 重启 calico-node
kubectl rollout restart daemonset calico-node -n kube-system

# 重启 calico-kube-controllers
kubectl rollout restart deployment calico-kube-controllers -n kube-system
```

过一段时间，zadig，就部署完成了：

```shell
[root@k8s01 zadig]# kubectl -n zadig get po
NAME                              READY   STATUS    RESTARTS      AGE
aslan-685fd99b49-gbr2s            1/1     Running   1 (16m ago)   16m
cron-7759445bdb-2dnds             1/1     Running   0             16m
dind-0                            1/1     Running   0             16m
discovery-846bbf56f9-dxfkj        1/1     Running   0             16m
gateway-7d58ff944d-mwknp          1/1     Running   0             16m
gateway-proxy-6b4885bbd4-dk6zw    1/1     Running   0             16m
gloo-7b6bf46489-jtq42             1/1     Running   0             16m
hub-server-698dfc699f-954jh       1/1     Running   0             16m
kr-minio-6df5f4cc64-7lcnb         1/1     Running   0             16m
kr-redis-5c898897ff-k2r7w         1/1     Running   0             16m
plutus-vendor-6557d554b-d77gp     1/1     Running   3 (13m ago)   16m
time-nlp-78db5764bc-5hqbt         1/1     Running   0             16m
user-67596cbc77-q8wpb             1/1     Running   0             16m
vendor-portal-7b864fd5c8-22c94    1/1     Running   0             16m
zadig-portal-57d89f66fb-bt6vm     1/1     Running   0             16m
zadig-zadig-dex-679bbfc6f-t6lnb   1/1     Running   3 (15m ago)   16m
```





### 访问 Zadig

从输出结果中获取访问系统的地址：192.168.11.101:32767

```shell
[root@k8s01 zadig]# ./install.sh 
NO ENCRYPTION KEY PROVIDED, ZADIG HAS GENERATED AN ENCRYPTION KEY
D78DA10299D32797429D31B008B951B3
THIS KEY WILL BE USED FOR POSSIBLE FUTURE REINSTALLATION, PLEASE SAVE THIS KEY CAREFULLY
Installing zadig ...
"koderover-chart" already exists with the same configuration, skipping
Hang tight while we grab the latest from your chart repositories...
...Unable to get an update from the "nfs-subdir-external-provisioner" chart repository (https://kubernetes-sigs.github.io/nfs-subdir-external-provisioner/):
	Get "https://kubernetes-sigs.github.io/nfs-subdir-external-provisioner/index.yaml": read tcp 192.168.11.101:48506->185.199.111.153:443: read: connection reset by peer
...Successfully got an update from the "koderover-chart" chart repository
Update Complete. ⎈Happy Helming!⎈
Release "zadig-zadig" does not exist. Installing it now.
NAME: zadig-zadig
LAST DEPLOYED: Thu Oct 31 22:01:02 2024
NAMESPACE: zadig
STATUS: deployed
REVISION: 1
TEST SUITE: None
NOTES:
Zadig has been installed successfully.

Login:  192.168.11.101:32767

Add wechat ID "likeartist007": get FREE Zadig Tech Support, and Join our online community.

THIS KEY WILL BE USED FOR POSSIBLE FUTURE REINSTALLATION, PLEASE SAVE THIS KEY CAREFULLY:
- Encryption Key: D78DA10299D32797429D31B008B951B3
```



#### 注册 Zadig

[管理员注册](http://192.168.11.101:32767/v1/installation/signup)

![image-20241102214800322](img.assets/image-20241102214800322.png)



用户名：diguapao

密码：Diguapao66

邮箱：changgelp@163.com

企业名称：地瓜泡云



#### 选择版本

![image-20241102215127550](img.assets/image-20241102215127550.png)



#### 登录工作台

[工作台](http://192.168.11.101:32767/v1/dashboard)

![image-20241102215904433](img.assets/image-20241102215904433.png)



