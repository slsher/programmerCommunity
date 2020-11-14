## 知猿社区

## 运行步骤
- git clone https://github.com/slsher/programmerCommunity.git
- mvn -v
- mvn flyway:migrate -Pdev
- mvn clean compile package
- java -jar target/community-0.0.1-SNAPSHOT.jar

## 部署
## 依赖
- Git
- JDK
- Maven
- MySQL

## 本地运行步骤
1. 安装必备工具  
JDK，Maven
2. 克隆代码到本地
```sh
git clone https://github.com/slsher/programmerCommunity.git
````
3. 运行打包命令
```sh
mvn package
```
4. 运行项目  
```sh
java -jar target/community-0.0.1-SNAPSHOT.jar
```
5. 访问项目
```
http://localhost:8887
```



more src/main/resources/application-production.properties
vim src/main/resources/application-production.properties
## 云端运行步骤
- yum update
- yum install git
- mkdir App
- cd App
- git clone  https://github.com/slsher/programmerCommunity.git
- yum install maven
- mvn -v
- mvn clean  compile package
- cp src/main/resources/application.properties src/main/resources/application-production.properties
- mvn clean compile package/mvn package
- java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar

## 答辩网站
* [ucloud](https://console.ucloud.cn/uhost/uhost)
* [github登陆](https://github.com/settings/applications/1383090)
* [知猿社区](http://123.58.211.164/)


## 资料
* [spring文档](https://spring.io/guides)
* [spring MVC Web服务的文档](https://spring.io/guides/gs/serving-web-content/) 
* [es社区 对标的社区地址](https://elasticsearch.cn/) 
* [github上传方式](https://blog.csdn.net/m0_37725003/article/details/80904824) 
* [github常用命令](https://www.cnblogs.com/xiaowu0371/p/11804219.html) 
* [bootstrap 文档](https://v3.bootcss.com/getting-started/) 
* [github OAuth](https://docs.github.com/en/free-pro-team@latest/developers/apps/authorizing-oauth-apps) 
* [每一个开发人员都应该懂的 UML 规范](https://blog.csdn.net/coderising/article/details/89944201)
* [菜鸟教程](https://www.runoob.com/)
* [H2数据库](http://www.h2database.com/html/main.html)
* [lombok](https://projectlombok.org/setup/maven)
* [thymeleaf](https://www.thymeleaf.org/)
* [mybatis](http://mybatis.org/generator/index.html)
* [postman](http://www.getpostman.com/)
* [editorMarkDown](http://editor.md.ipandao.com/)
* [ucloud ufile-sdk](https://github.com/ucloud/ufile-sdk-java)
## 工具
* [github地址](https://github.com/) 
* [visual](https://www.visual-paradigm.com/cn/)
* [mvnrepository](https://mvnrepository.com/search?q=h2)
* [flyway](https://flywaydb.org/documentation/getstarted/firststeps/maven)
* [jquery](https://jquery.cuishifeng.cn/jQuery.getJSON.html)
* [ucloud](https://console.ucloud.cn/ufile/ufile/detail?id=zhiyuan)

## 脚本
``` sql 

create table USER_1
(
	ID bigint,
	ACCOUNT_ID varchar(100),
	NAME varchar(50),
	TOKEN varchar(36),
	GMT_CREATE bigint,
	GMT_MODIFIED bigint
);

```

```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```