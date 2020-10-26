## 知猿社区

## 运行步骤
mvn
mvn flyway:migrate -Pdev
mvn clean compile package
java -jar target/community-0.0.1-SNAPSHOT.jar

## 部署
## 依赖
- Git
- JDK
- Maven
- MySQL

## 步骤
- yum update
- yum install git
- mkdir App
- cd App
- clone git https://github.com/slsher/programmerCommunity.git
- yum isntall maven
- mvn -v
- mvn clean compile package


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