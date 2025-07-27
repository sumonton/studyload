* CI/CD:持续集成，持续部署

# 一、安装部署

## 1、Jenkins在开发部署过程中的位置

![image-20250502161427350](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/20250502161427437.png)



## 2、Jenkins+Maven+Git持续集成的使用

![image-20250502161914415](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/20250502161914492.png)

### 2.1 GitLab

#### 1）SSH安装GitLab

* [官网地址](https://gitlab.cn/install/)

* 安装依赖

  ```bash
  sudo yum update
  sudo yum install -y curl policycoreutils-python-utils openssh-server perl
  sudo systemctl enable sshd
  sudo systemctl start sshd
  ```

* 下载极狐安装包:根据自身情况选择合适的极狐GitLab 版本，比如以极狐GitLab 17.7.0 为例，针对 `x86_64` 架构，执行如下命令即可完成下载

  ```bash
  curl -LOS https://packages.gitlab.cn/repository/el/8/gitlab-jh-17.7.0-jh.0.el8.x86_64.rpm
  ```

* 开始安装

  * 通过设置 `EXTERNAL_URL` 环境变量来指定极狐GitLab 实例的 URL。
  * 如果您想通过 `HTTPS` 来访问实例，那么您可以根据[官方文档](https://gitlab.cn/docs/jh/omnibus/settings/ssl/index.html)进行配置，让实例使用 Let's Encrypt 自动请求 SSL 证书，这需要有效的主机名和入站 HTTP 访问。您也可以使用自己的证书或仅使用 `http://`（不带 `s`）
  * 如果您想为初始管理员用户（ `root` ）指定自定义的初始密码，可以根据[文档指导](https://docs.gitlab.cn/omnibus/installation/index.html#设置初始密码)进行配置。否则将默认生成随机密码。

  ```bash
  # https://gitlab.example.com 改为自己的ip
  export EXTERNAL_URL="https://gitlab.example.com" && rpm -ivh gitlab-jh-17.7.0-jh.0.el8.x86_64.rpm
  
  export EXTERNAL_URL="http://192.168.17.148" && rpm -ivh gitlab-jh-17.7.0-jh.0.el8.x86_64.rpm
  ```

* 常用命令![](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/20250502182627301.png)

* 首次登录，默认密码存放位置。![image-20250502184621381](https://smcjava.oss-cn-hangzhou.aliyuncs.com/java/20250502184621530.png)

#### 2）docker下安装GitLab

* 安装docker

  ```bash
  # 更新yum源
  yum update
  # 安装依赖
  yum install -y yum-utils device-mapper-persistent-data lvm2
  # 添加镜像源
  //国外镜像
  yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
  //阿里镜像
  yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
  #查看可安装版本
  yum list docker-ce --showduplicates | sort -r
  # 安装指定版本docker
  yum install docker-ce-28.1.1-1.el9
  # 启动docker和开启开机自启功能
  systemctl start docker
  systemctl enable docker
  # 查看docker版本
  docker version
  ```

* 使用docker安装启动gitlab

  ```bash
  # hostname 调整为自己的ip或者域名
  docker run --detach \
    --hostname gitlab.example.com \
    --publish 443:443 --publish 80:80 --publish 2289:22 \
    --name gitlab \
    --restart always \
    --volume $GITLAB_HOME/config:/etc/gitlab:Z \
    --volume $GITLAB_HOME/logs:/var/log/gitlab:Z \
    --volume $GITLAB_HOME/data:/var/opt/gitlab:Z \
    --shm-size 256m \
    registry.gitlab.cn/omnibus/gitlab-jh:latest
    
  docker run --detach \
    --hostname 192.168.17.145 \
    --publish 443:443 --publish 80:80 --publish 2289:22 \
    --name gitlab \
    --restart always \
    --volume $GITLAB_HOME/config:/etc/gitlab:Z \
    --volume $GITLAB_HOME/logs:/var/log/gitlab:Z \
    --volume $GITLAB_HOME/data:/var/opt/gitlab:Z \
    --shm-size 256m \
    registry.gitlab.cn/omnibus/gitlab-jh:latest
  ```

* 默认密码：进入到容器内，查看` /etc/gitlab/initial_root_password` ,或者使用命令

  ```bash
  sudo docker exec -it gitlab grep 'Password:' /etc/gitlab/initial_root_password
  ```





## 3、安装硬件环境和知识储备

