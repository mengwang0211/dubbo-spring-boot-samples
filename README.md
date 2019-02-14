# Dubbo Spring Boot 工程

[Dubbo](https://github.com/alibaba/dubbo) Spring Boot 工程致力于简化 Dubbo RPC 框架在
[Spring Boot](https://github.com/spring-projects/spring-boot/) 应用场景的开发。同时也整合了 Spring Boot 特性：

* [自动装配](dubbo-spring-boot-autoconfigure) (比如： 注解驱动, 自动装配等).
* [Production-Ready](dubbo-spring-boot-actuator) (比如： 安全, 健康检查, 外部化配置等).

## 已发行版本

您可以为您的工程引入最新 `dubbo-spring-boot-starter` 的发布，增加以下依赖到工程的 `pom.xml` 文件中：
```xml
```
## 开发版本

从现在开始, `dubbo-spring-boot-project` 将在每个发布中发行两个版本 :

* [`0.2.x`](https://github.com/apache/incubator-dubbo-spring-boot-project) 是支持 Spring Boot 2.x 的主要版本（推荐，长期维护）

* [`0.1.x`](https://github.com/apache/incubator-dubbo-spring-boot-project/tree/0.1.x) 是支持 Spring Boot 1.x 的维护版本（兼容，短期维护）


### 源代码构建

如果你需要尝试最新 `dubbo-spring-boot-project` 的特性，您可将当前工程手动 Maven install 到本地 Maven 仓库：

1. Maven install 当前工程
> Maven install = `mvn install`

### 依赖关系

| versions | Java  | Spring Boot | Dubbo      |
| -------- | ----- | ----------- | ---------- |
| `0.2.1`  | 1.8+ | `2.1.x` | `2.6.5` + |
| `0.1.1`  | 1.7+ | `1.5.x` | `2.6.5` + |



## 快速开始

如果您对 Dubbo 不是非常了解，耽误您几分钟访问 http://dubbo.apache.org/ 。了解后，如果你期望更深入的探讨，可以移步[用户手册](http://dubbo.apache.org/zh-cn/docs/user/quick-start.html)。

通常情况 , Dubbo 应用有两种使用场景 , 其一为 Dubbo 服务提供方 , 另外一个是 Dubbo 服务消费方，当然也允许两者混合，下面我们一起快速开始！

首先，我们假设存在一个 Dubbo RPC API ，由服务提供方为服务消费方暴露接口 :

```java
public interface DemoService {

    String sayHello(String name);

}
```



### 实现 Dubbo 服务提供方

1. 实现 `DemoService` 接口

```java
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class DefaultDemoService implements DemoService {

    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }

}
```



2. 编写 Spring Boot 引导程序

```java
@SpringBootApplication
public class DubboProviderDemo {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DubboProviderDemo.class)
                .web(false) // 非 Web 应用
                .run(args);

    }

}
```


3. 配置 `application.properties` :

```properties
# Spring boot application
spring.application.name = dubbo-provider-demo
server.port = 9090
management.port = 9091

# Service version
demo.service.version = 1.0.0

# Base packages to scan Dubbo Components (e.g @Service , @Reference)
dubbo.scan.basePackages  = com.alibaba.boot.dubbo.demo.provider.service

# Dubbo Config properties
## ApplicationConfig Bean
dubbo.application.id = dubbo-provider-demo
dubbo.application.name = dubbo-provider-demo

## ProtocolConfig Bean
dubbo.protocol.id = dubbo
dubbo.protocol.name = dubbo
dubbo.protocol.port = 12345

## RegistryConfig Bean
dubbo.registry.id = my-registry
dubbo.registry.address = N/A
```

更多的实现细节 , 请参考 [Dubbo 服务提供方示例](dubbo-spring-boot-samples/dubbo-spring-boot-sample-provider).



### 实现 Dubbo 服务消费方


1. 通过 `@Reference` 注入 `DemoService` :

```java
@RestController
public class DemoConsumerController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:12345")
    private DemoService demoService;

    @RequestMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return demoService.sayHello(name);
    }

}
```



2. 编写 Spring Boot 引导程序（Web 应用） :

```java
@SpringBootApplication(scanBasePackages = "com.alibaba.boot.dubbo.demo.consumer.controller")
public class DubboConsumerDemo {

    public static void main(String[] args) {

        SpringApplication.run(DubboConsumerDemo.class,args);

    }

}
```



3. 配置 `application.properties` :

```properties
# Spring boot application
spring.application.name = dubbo-consumer-demo
server.port = 8080
management.port = 8081

# Service Version
demo.service.version = 1.0.0

# Dubbo Config properties
## ApplicationConfig Bean
dubbo.application.id = dubbo-consumer-demo
dubbo.application.name = dubbo-consumer-demo

## ProtocolConfig Bean
dubbo.protocol.id = dubbo
dubbo.protocol.name = dubbo
dubbo.protocol.port = 12345
```

## 模块工程

### [dubbo-spring-boot-samples](dubbo-spring-boot-samples)
#### [dubbo-api-sample](dubbo-api-sample)
#### [dubbo-zookeeper](dubbo-zookeeper)
#### [dubbo-nacos](dubbo-nacos)