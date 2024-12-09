# Kuboard-Spray 部署 k8s 集群

本文基于 CentOS7.9 部署 Kuboard-Spray、k8s 集群。

## 准备资源

| 节点主机名    | IP             | 说明                                                         |
| ------------- | -------------- | ------------------------------------------------------------ |
| kuboard-spray | 192.168.11.130 | ubuntu2204，安装docker后docker内部署 kuboard-spray，docker版本：Docker version 20.10.24, build 297e128 |
| k8s01         | 192.168.11.101 | centos7.9，k8s 控制节点、工作节点、ETCD，配置镜像源为阿里云  |
| k8s02         | 192.168.11.102 | centos7.9，k8s 控制节点、工作节点、ETCD，配置镜像源为阿里云  |
| k8s03         | 192.168.11.103 | centos7.9，k8s 控制节点、工作节点、ETCD，配置镜像源为阿里云  |
|               |                |                                                              |



## 开始部署

根据[文档](https://kuboard.cn/install/install-k8s.html#%E5%AE%89%E8%A3%85-kuboard-spray)进行部署。

### 安装 Docker

```shell
sudo yum update -y

# 获取可用的 Docker 版本列表并列出可用版本
curl -s https://download.docker.com/linux/centos/distro.docker-ce.repo | grep -Po '"docker-ce-\K[^"]*'
sudo yum list docker-ce --showduplicates | sort

# 安装必要的软件包
sudo yum install -y yum-utils

# 添加 Docker 仓库
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# 安装 Docker CE
# sudo yum install docker-ce docker-ce-cli containerd.io -y
# 指定版本安装
sudo yum install -y docker-ce-20.10.11 docker-ce-cli-20.10.11 containerd.io

# 启动 Docker 服务
sudo systemctl start docker
sudo systemctl enable docker

# 验证安装
docker --version
docker info

# 配置 Docker 以支持 SELinux
[root@k8s03 ~]# sudo sestatus
SELinux status:                 enabled
SELinuxfs mount:                /sys/fs/selinux
SELinux root directory:         /etc/selinux
Loaded policy name:             targeted
Current mode:                   permissive
Mode from config file:          permissive
Policy MLS status:              enabled
Policy deny_unknown status:     allowed
Max kernel policy version:      31
# CentOS 系统启用了 SELinux

sudo tee /etc/docker/daemon.json <<EOF
{
  "storage-driver": "overlay2",
  "storage-opts": ["overlay2.override_kernel_check=true"],
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "100m",
    "max-file": "10"
  },
  "security-opt": ["label=type:docker_container"],
  "selinux-enabled": true
}
EOF

sudo systemctl daemon-reload
sudo systemctl restart docker

# 设置 Docker 使用国内镜像
sudo mkdir -p /etc/docker && sudo tee /etc/docker/daemon.json <<EOF
{"registry-mirrors": ["https://mirror.aliyuncs.com"]}
EOF

sudo systemctl daemon-reload
sudo systemctl restart docker

# 加入 Docker 用户组
sudo usermod -aG docker $USER

# 重启
reboot
```



### 部署 kuboard-spray

参考官方文档部署即可。

```shell
docker run -d \
  --privileged \
  --restart=unless-stopped \
  --name=kuboard-spray \
  -p 80:80/tcp \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v ~/kuboard-spray-data:/data \
  eipwork/kuboard-spray:latest-amd64
  # 如果抓不到这个镜像，可以尝试一下这个备用地址：
  # swr.cn-east-2.myhuaweicloud.com/kuboard/kuboard-spray:latest-amd64

```



### 部署成功

```shell
PLAY RECAP *********************************************************************
k8s01                      : ok=1019 changed=242  unreachable=0    failed=0    skipped=1349 rescued=0    ignored=7   
k8s02                      : ok=828  changed=210  unreachable=0    failed=0    skipped=1198 rescued=0    ignored=2   
k8s03                      : ok=836  changed=215  unreachable=0    failed=0    skipped=1184 rescued=0    ignored=2   
localhost                  : ok=2    changed=0    unreachable=0    failed=0    skipped=0    rescued=0    ignored=0   

Playbook run took 0 days, 0 hours, 17 minutes, 59 seconds
Friday 25 October 2024  14:27:39 +0800 (0:00:00.210)       0:17:59.314 ******** 
=============================================================================== 
kubernetes/control-plane : Joining control plane node to the cluster. - 281.31s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/kubernetes/control-plane/tasks/kubeadm-secondary.yml:65 
kubernetes/control-plane : kubeadm | Initialize first master ---------- 126.42s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/kubernetes/control-plane/tasks/kubeadm-setup.yml:168 
download : download_container | Load image into the local container registry -- 46.39s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:112 
kubernetes/preinstall : Install packages requirements ------------------ 36.41s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/kubernetes/preinstall/tasks/0070-system-packages.yml:80 
download : download_container | Upload image to node if it is cached --- 24.88s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:95 
bootstrap-os : Install libselinux python package ----------------------- 18.78s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/roles/bootstrap-os/tasks/centos-alike.yml:41 
download : download_container | Load image into the local container registry -- 17.98s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:112 
download : download_container | Load image into the local container registry --- 9.66s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:112 
etcd : Gen_certs | Write etcd member/admin and kube_control_plane clinet certs to other etcd nodes --- 8.87s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/etcd/tasks/gen_certs_script.yml:99 
etcd : Gen_certs | Write etcd member/admin and kube_control_plane clinet certs to other etcd nodes --- 8.63s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/etcd/tasks/gen_certs_script.yml:99 
download : download_container | Load image into the local container registry --- 6.76s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:112 
kubernetes/preinstall : Preinstall | wait for the apiserver to be running --- 6.18s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/kubernetes/preinstall/handlers/main.yml:109 
download : download_container | Load image into the local container registry --- 5.89s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:112 
os-services/roles/prepare : Disable service firewalld ------------------- 5.45s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/contrib/os-services/roles/prepare/tasks/main.yml:6 
etcd : Configure | Check if etcd cluster is healthy --------------------- 5.25s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/etcd/tasks/configure.yml:2 
download : download_file | Copy file from cache to nodes, if it is available --- 5.20s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_file.yml:119 
download : download_container | Load image into the local container registry --- 4.71s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:112 
download : download_container | Upload image to node if it is cached ---- 4.34s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/download/tasks/download_container.yml:95 
kubernetes-apps/ansible : Kubernetes Apps | Start Resources ------------- 4.32s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/kubernetes-apps/ansible/tasks/main.yml:39 
kubernetes-apps/ansible : Kubernetes Apps | Lay Down CoreDNS templates --- 4.17s
/data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/kubernetes-apps/ansible/tasks/coredns.yml:2 



KUBOARD SPRAY *****************************************************************

[ Kubernetes Cluster has been installed successfully, please go back to the cluster page for information about how to access the cluster. ] 
[ Kubernetes 集群已成功安装，请回到集群详情页面查看如何访问该集群。 ] 
```

![image-20241025152447734](img.assets/image-20241025152447734.png)





## Kubernetes 仪表板（GUI）

```shell
# 登录到 192.168.11.101，切换到 root 用户，从那里运行 kubectl 命令
sudo su -
sudo mkdir -p /root/soft/k8s

# 部署
curl -O https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
vm /root/soft/k8s/recommended.yaml /root/soft/k8s/recommended_v2.7.0.yaml
vi /root/soft/k8s/recommended_v2.7.0.yaml
#修改或增加：type: NodePort
kind: Service
apiVersion: v1
metadata:
  labels:
    k8s-app: kubernetes-dashboard
  name: kubernetes-dashboard
  namespace: kubernetes-dashboard
spec:
  ports:
    - port: 443
      targetPort: 8443
  selector:
    k8s-app: kubernetes-dashboard
  type: NodePort
#应用yaml执行部署
kubectl apply -f /root/soft/k8s/recommended_v2.7.0.yaml
# 监控仪表盘是否安装完成
watch kubectl get all -o wide -n kubernetes-dashboard

# kubectl get all 查看所有资源
kubectl get all -n kubernetes-dashboard

# 查看 kubernetes-dashboard Service暴露的端口：
kubectl get svc -n kubernetes-dashboard -o wide

#访问：https://192.168.11.101:32254

#修改 svc，把 type: ClusterIP 改为 type: NodePort
#kubectl get svc -n kubernetes-dashboard
#kubectl edit svc kubernetes-dashboard-web -n kubernetes-dashboard # 修改好后保存退出
#kubectl get svc -n kubernetes-dashboard

#创建服务帐户
sudo tee /root/soft/k8s/dashboard-adminuser.yaml  <<EOF
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kubernetes-dashboard
EOF
kubectl apply -f /root/soft/k8s/dashboard-adminuser.yaml
#创建 ClusterRoleBinding
sudo tee /root/soft/k8s/dashboard-role-adminuser.yaml  <<EOF
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: admin-user
  namespace: kubernetes-dashboard
EOF
kubectl apply -f /root/soft/k8s/dashboard-role-adminuser.yaml
#获取 ServiceAccount 的长期 Bearer Token，也就是 Secret
sudo tee /root/soft/k8s/dashboard-adminuser-lt-token.yaml  <<EOF
apiVersion: v1
kind: Secret
metadata:
  name: admin-user
  namespace: kubernetes-dashboard
  annotations:
    kubernetes.io/service-account.name: "admin-user"   
type: kubernetes.io/service-account-token
EOF
kubectl apply -f /root/soft/k8s/dashboard-adminuser-lt-token.yaml
#或者这样创建 Secret（创建一个名为 admin-user 的 Secret，并生成一个随机的 Token）
#kubectl -n kubernetes-dashboard create secret generic admin-user --from-literal=token=$(openssl rand -hex 16)
#验证 Secret 数量
kubectl get sa -n kubernetes-dashboard
#创建 Secret 后，我们可以执行以下命令来获取保存在 Secret 中的 Token
kubectl get secret admin-user -n kubernetes-dashboard -o jsonpath={".data.token"} | base64 -d
#永久访问令牌（现在可以通过这个令牌登陆了）
eyJhbGciOiJSUzI1NiIsImtpZCI6IjdYd240TkEwNV82X21xUVR6VDRITFVPeE5SYkZmamJib0U3ZzNsLXNybzAifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiIyOTIyZTc4ZC02MjBlLTQwYTEtYWM0Yy0xNzA5ZmVmNjJkY2EiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZXJuZXRlcy1kYXNoYm9hcmQ6YWRtaW4tdXNlciJ9.XlMu4uXs1CXlu_mdNzskDIpt4u0udYCSTqrQiiWo5R47uq9kbxNSzmWXK-FkQjYGbg7pqDlZIsCSmCQFNyCYj840S8qYlqtGupnyYaDu1GbFNdLoDr9LOV2StW0uWxy42hU6jV9VsKNZVePgchu8BWE-2qpUOZJ4u0V9W-aepBJb5uNyekRKryWeG6z4_Yp2KSkBygXOSg8I-7U6umSBP6mcAzK819tuU_Z1FEAsN6Lq_FEJNJ86YP9yJ6QIUjyuSMHwxigu-X9dMKV_7VOiiCXWUr4RYseh1fbaUhX5Zy6iGRlP-HyddAQ6gZTURtLSNQzQDdpmpw1EzdVj9WvNlQ

#删除 admin 和 .ServiceAccountClusterRoleBinding
#kubectl -n kubernetes-dashboard delete serviceaccount admin-user
#kubectl -n kubernetes-dashboard delete clusterrolebinding admin-user
```



## 注意事项

### 如何解决：Failure talking to yum Cannot find a valid baseurl for repo base7x86_64

参见： [解决：Failure talking to yum Cannot find a valid baseurl for repo base7x86_64.md](解决：Failure talking to yum Cannot find a valid baseurl for repo base7x86_64.md) 

### 逐步备份

就是我们在通过VMware部署过程中，由于是新准备的干净的 centos，不乏会遇到一些问题，当把问题解决后，立即做一个快照，以便后续操作失误即时回滚重来。

### 不建议通过 helm 部署仪表盘

官方文档：[部署和访问 Kubernetes 仪表板（Dashboard） | Kubernetes](https://kubernetes.io/zh-cn/docs/tasks/access-application-cluster/web-ui-dashboard/)

不推荐通过官方文档这样的方式部署（还没整明白这个……）。
