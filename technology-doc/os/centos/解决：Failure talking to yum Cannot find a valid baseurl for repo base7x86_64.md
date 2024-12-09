# 解决：Failure talking to yum Cannot find a valid baseurl for repo base7x86_64

## 错误描述

​		在通过 Kuboard-Spray 部署 k8s 集群过程中，发生错误：

```shell
TASK [kubernetes/preinstall : Install packages requirements] *******************
task path: /data/resource/spray-v2.21.0c_k8s-v1.26.4_v4.4-amd64/content/3rd/kubespray/roles/kubernetes/preinstall/tasks/0070-system-packages.yml:80
The full traceback is:
WARNING: The below traceback may *not* be related to the actual failure.
  File "/tmp/ansible_ansible.legacy.yum_payload_lDEhrA/ansible_ansible.legacy.yum_payload.zip/ansible/modules/yum.py", line 734, in what_provides
  File "/usr/lib/python2.7/site-packages/yum/__init__.py", line 4295, in returnPackagesByDep
    return self.pkgSack.searchProvides(depstring)
  File "/usr/lib/python2.7/site-packages/yum/__init__.py", line 1075, in <lambda>
    pkgSack = property(fget=lambda self: self._getSacks(),
  File "/usr/lib/python2.7/site-packages/yum/__init__.py", line 778, in _getSacks
    self.repos.populateSack(which=repos)
  File "/usr/lib/python2.7/site-packages/yum/repos.py", line 347, in populateSack
    self.doSetup()
  File "/usr/lib/python2.7/site-packages/yum/repos.py", line 157, in doSetup
    self.retrieveAllMD()
  File "/usr/lib/python2.7/site-packages/yum/repos.py", line 88, in retrieveAllMD
    dl = repo._async and repo._commonLoadRepoXML(repo)
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 1482, in _commonLoadRepoXML
    result = self._getFileRepoXML(local, text)
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 1259, in _getFileRepoXML
    size=102400) # setting max size as 100K
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 1025, in _getFile
    result = self.grab.urlgrab(misc.to_utf8(relative), local,
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 703, in <lambda>
    grab = property(lambda self: self._getgrab())
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 698, in _getgrab
    self._setupGrab()
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 635, in _setupGrab
    urls = self.urls
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 881, in <lambda>
    urls = property(fget=lambda self: self._geturls(),
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 878, in _geturls
    self._baseurlSetup()
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 844, in _baseurlSetup
    self.check()
  File "/usr/lib/python2.7/site-packages/yum/yumRepo.py", line 562, in check
    'Cannot find a valid baseurl for repo: %s' % self.ui_id
fatal: [k8s02]: FAILED! => {
    "attempts": 4,
    "changed": false,
    "invocation": {
        "module_args": {
            "allow_downgrade": false,
            "autoremove": false,
            "bugfix": false,
            "cacheonly": false,
            "conf_file": null,
            "disable_excludes": null,
            "disable_gpg_check": false,
            "disable_plugin": [],
            "disablerepo": [],
            "download_dir": null,
            "download_only": false,
            "enable_plugin": [],
            "enablerepo": [],
            "exclude": [],
            "install_repoquery": true,
            "install_weak_deps": true,
            "installroot": "/",
            "list": null,
            "lock_timeout": 30,
            "name": [
                "libselinux-python",
                "device-mapper-libs",
                "nss",
                "conntrack",
                "container-selinux",
                "libseccomp",
                "openssl",
                "curl",
                "rsync",
                "socat",
                "unzip",
                "e2fsprogs",
                "xfsprogs",
                "ebtables",
                "bash-completion",
                "tar",
                "ipvsadm",
                "ipset"
            ],
            "releasever": null,
            "security": false,
            "skip_broken": false,
            "state": "present",
            "update_cache": false,
            "update_only": false,
            "use_backend": "auto",
            "validate_certs": true
        }
    },
    "msg": "Failure talking to yum: Cannot find a valid baseurl for repo: base/7/x86_64"
}
```



## 排查过程

​		确保机器网络是没问题的：

```shell
[root@k8s01 ~]# ping -c 4 jd.com
PING jd.com (211.144.24.218) 56(84) bytes of data.
64 bytes from 211.144.24.218 (211.144.24.218): icmp_seq=1 ttl=128 time=66.6 ms
64 bytes from 211.144.24.218 (211.144.24.218): icmp_seq=2 ttl=128 time=75.7 ms
64 bytes from 211.144.24.218 (211.144.24.218): icmp_seq=3 ttl=128 time=76.1 ms
```



​		查看镜像源：

```shell
[root@k8s01 ~]# cat /etc/yum.repos.d/CentOS-Base.repo
# CentOS-Base.repo
#
# The mirror system uses the connecting IP address of the client and the
# update status of each mirror to pick mirrors that are updated to and
# geographically close to the client.  You should use this for CentOS updates
# unless you are manually picking other mirrors.
#
# If the mirrorlist= does not work for you, as a fall back you can try the
# remarked out baseurl= line instead.
#
#

[base]
name=CentOS-$releasever - Base
mirrorlist=http://mirrorlist.centos.org/?release=$releasever&arch=$basearch&repo=os&infra=$infra
#baseurl=http://mirror.centos.org/centos/$releasever/os/$basearch/
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7

#released updates
[updates]
name=CentOS-$releasever - Updates
mirrorlist=http://mirrorlist.centos.org/?release=$releasever&arch=$basearch&repo=updates&infra=$infra
#baseurl=http://mirror.centos.org/centos/$releasever/updates/$basearch/
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7

#additional packages that may be useful
[extras]
name=CentOS-$releasever - Extras
mirrorlist=http://mirrorlist.centos.org/?release=$releasever&arch=$basearch&repo=extras&infra=$infra
#baseurl=http://mirror.centos.org/centos/$releasever/extras/$basearch/
gpgcheck=1
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7

#additional packages that extend functionality of existing packages
[centosplus]
name=CentOS-$releasever - Plus
mirrorlist=http://mirrorlist.centos.org/?release=$releasever&arch=$basearch&repo=centosplus&infra=$infra
#baseurl=http://mirror.centos.org/centos/$releasever/centosplus/$basearch/
gpgcheck=1
enabled=0
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-7
```

可以看到这是默认的，centos 官方源。

## 解决

将其切换为国内阿里云源：

```shell
sudo mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo_def
curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
#其内容如下：
# CentOS-Base.repo
#
# The mirror system uses the connecting IP address of the client and the
# update status of each mirror to pick mirrors that are updated to and
# geographically close to the client.  You should use this for CentOS updates
# unless you are manually picking other mirrors.
#
# If the mirrorlist= does not work for you, as a fall back you can try the 
# remarked out baseurl= line instead.
#
#
 
[base]
name=CentOS-$releasever - Base - mirrors.aliyun.com
failovermethod=priority
baseurl=http://mirrors.aliyun.com/centos/$releasever/os/$basearch/
        http://mirrors.aliyuncs.com/centos/$releasever/os/$basearch/
        http://mirrors.cloud.aliyuncs.com/centos/$releasever/os/$basearch/
gpgcheck=1
gpgkey=http://mirrors.aliyun.com/centos/RPM-GPG-KEY-CentOS-7
 
#released updates 
[updates]
name=CentOS-$releasever - Updates - mirrors.aliyun.com
failovermethod=priority
baseurl=http://mirrors.aliyun.com/centos/$releasever/updates/$basearch/
        http://mirrors.aliyuncs.com/centos/$releasever/updates/$basearch/
        http://mirrors.cloud.aliyuncs.com/centos/$releasever/updates/$basearch/
gpgcheck=1
gpgkey=http://mirrors.aliyun.com/centos/RPM-GPG-KEY-CentOS-7
 
#additional packages that may be useful
[extras]
name=CentOS-$releasever - Extras - mirrors.aliyun.com
failovermethod=priority
baseurl=http://mirrors.aliyun.com/centos/$releasever/extras/$basearch/
        http://mirrors.aliyuncs.com/centos/$releasever/extras/$basearch/
        http://mirrors.cloud.aliyuncs.com/centos/$releasever/extras/$basearch/
gpgcheck=1
gpgkey=http://mirrors.aliyun.com/centos/RPM-GPG-KEY-CentOS-7
 
#additional packages that extend functionality of existing packages
[centosplus]
name=CentOS-$releasever - Plus - mirrors.aliyun.com
failovermethod=priority
baseurl=http://mirrors.aliyun.com/centos/$releasever/centosplus/$basearch/
        http://mirrors.aliyuncs.com/centos/$releasever/centosplus/$basearch/
        http://mirrors.cloud.aliyuncs.com/centos/$releasever/centosplus/$basearch/
gpgcheck=1
enabled=0
gpgkey=http://mirrors.aliyun.com/centos/RPM-GPG-KEY-CentOS-7
 
#contrib - packages by Centos Users
[contrib]
name=CentOS-$releasever - Contrib - mirrors.aliyun.com
failovermethod=priority
baseurl=http://mirrors.aliyun.com/centos/$releasever/contrib/$basearch/
        http://mirrors.aliyuncs.com/centos/$releasever/contrib/$basearch/
        http://mirrors.cloud.aliyuncs.com/centos/$releasever/contrib/$basearch/
gpgcheck=1
enabled=0
gpgkey=http://mirrors.aliyun.com/centos/RPM-GPG-KEY-CentOS-7
```

保存文件并退出。然后清理并重建缓存：

```shell
sudo yum clean all
sudo yum makecache
sudo yum update
```

