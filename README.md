# JP-EmailService



![JP-EmailService](https://gs-jj-us-static.oss-accelerate.aliyuncs.com/tmp/photo/20201026//58/yellow.png?x-oss-process=image/resize,l_200 "Email Service")



JP-EmailService 
===============

version：1.0.0（date：2021-09-09）


Introduction：
-----------------------------------

<h3 align="center">Spring boot + mysql+ mybatis-plus + nacos + rabbit</h3>





Background development environment and dependencies
----
- java
- maven
- jdk8
- mysql
- rabbitmq

## Quick Start
It is super easy to get started with your first project.

#### Step 1: Download the binary package 

You can download the package from the [latest stable release](https://github.com/alibaba/nacos/releases).  

Take release `nacos-server-1.0.0.zip` for example:
```sh
unzip nacos-server-1.0.0.zip
cd nacos/bin 
``` 

#### Step 2: Start Server

On the **Linux/Unix/Mac** platform, run the following command to start server with standalone mode: 
```sh
sh startup.sh -m standalone
```

On the **Windows** platform, run the following command to start server with standalone mode.  Alternatively, you can also double-click the `startup.cmd` to run NacosServer.
```
startup.cmd -m standalone
```
#### Step 3: Copy this yaml to nacos

```
server:
  port: 8899
  tomcat:
    uri-encoding: UTF-8
    max-threads: 1000
    min-spare-threads: 30
  servlet:
    context-path: /jpEmail

logging:
  level:
    root: info
  config: classpath:config/logback-spring.xml

mybatis-plus:
  global-config:
    id-type: 0
    field-strategy: 2
    db-column-underline: true
    refresh-mapper: false
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
    logPrefix: dao.
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/date?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&characterSetResults=UTF-8&serverTimezone=GMT
    username: root
    password: root
  redis:
    database: 1
    host: 127.0.0.1
    port: 6379
    password: 123456
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    publisher-confirms: true
    connection-timeout: 50000
    listener:
      simple:
        acknowledge-mode: manual

```

#### Step 4: install redis and rabbitmq,then start 



Remark
----



## Donation 
If you think it's good, invite the author to have a cup of coffee ☺

##Business
<a href="https://www.gs-jj.com">GS-JJ.com</a>

