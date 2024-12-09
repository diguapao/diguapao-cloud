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

```

