# sharding-jdbc-demo
---

[TOC]

# SpringBoot 集成 sharding-jdbc+mybatis-plus的demo

## sharding-jdbc 简单介绍

**官方介绍** 

`sharding-jdbc`将自身定义为轻量级`Java`框架，该框架在`Java` `JDBC`层提供额外的服务。客户端直接连接到数据库时，它以`jar`形式提供服务，并且不需要额外的部署和依赖性。它可以被认为是增强的`JDBC`驱动程序，它与`JDBC`和各种`ORM`框架完全兼容

[官方 GitHub 地址](<https://github.com/apache/incubator-shardingsphere>)



## 代码实例

**~~赘言~~**

由于当当网的`sharding jdbc`历史太久远了，所以没做测试，这里使用的是`apache`的`sharding-jdbc-spring-boot-starter`整合，是`2019.04.21`发布的`4.0.0-RC1`版本，官网地址[shardingsphere.apache.org](<https://shardingsphere.apache.org/document/current/cn/downloads/>)。

在测试的时候使用`SpringBoot 2.2.1.RELEASE`启动会报错，改用了`SpringBoot 2.0.6.RELEASE`

**实例目的**

- 数据库垂直拆分(不同表在不同库中)
- 同库分表(所有分表在一个库中)

### 前期准备

**开发环境**
> MySQL 5.7
> Mven 3.5.3
> Java 1.8
> SpringBoot 2.0.6.RELEASE
> IDEA 2018.1

**pom.xml**

```xml
	<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <!--<version>2.2.1.RELEASE</version>-->
        <version>2.0.6.RELEASE</version>
        <relativePath/> 
    </parent>

	<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.1.1</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- 阿里druid数据库连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.17</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.8</version>
        </dependency>

        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
            <version>4.0.0-RC1</version>
        </dependency>
    </dependencies>
```

### 定义Bean

#### `CsOrder`

```java
@Data
@Builder
public class CsOrder {
    private Long id;
    private Long userId;
    private Integer Amount;
    private Integer status;
    //分表节点
    private Integer node;
}
```
#### `UserInfo`

```java
@Data
@Builder
public class UserInfo implements Serializable {
    private Long id;
    private String userName;
    private Integer account;
    private String password;
}
```
### 配置Mapper
#### CsOrderMapper
**`CsOrderMapper.java`**
```java
@Mapper
public interface CsOrderMapper extends BaseMapper<CsOrder> {
}
```
**`CsOrderMapper.xml`**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.example.sharding.domain.mapper.csorder.CsOrderMapper">
</mapper>
```
#### UserInfoMapper
**`UserInfoMapper.java`**
```java
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
```
**`UserInfoMapper.xml`**

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.example.sharding.domain.mapper.userinfo.UserInfoMapper">
</mapper>
```

### 配置yml

配置多个数据源并将表分配到不同的数据源，并配置分表策略和主键策略

```yml
server:
  port: 18101

spring:
  shardingsphere:

    # 配置多个数据源
    datasource:
      names: ds0,ds1
      ds0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://192.168.1.115:3306/test?characterEncoding=utf-8&useSSL=false
        username: root
        password: 123456
      ds1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://192.168.1.115:33060/test?characterEncoding=utf-8&useSSL=false
        username: root
        password: 123456

    # 数据库垂直拆分（不同的表在不同的库中）
    sharding:
      tables:
        user_info:
          # 配置单表数据源
          actual-data-nodes: ds0.user_info
          # 配置主键策略
#          key-generator-column-name: id
          key-generator:
                  type: SNOWFLAKE
                  column: id
        cs_order:
          # 配置单表数据源(分表处理)
          actual-data-nodes: ds1.cs_order_$->{0..1}
          # 配置主键策略
          key-generator-column-name: id
          key-generator:
                  type: SNOWFLAKE
                  column: id

          ## 分表策略
          table-strategy:
            # inline 表达式配置分表
            inline:
              sharding-column: node
              algorithm-expression: cs_order_$->{node}

             # 自定义分表算法
#            standard:
#              sharding-column: node
#              precise-algorithm-class-name: com.example.sharding.domain.algorithm.CsOrderTableShardingAlgorithm

```


### 建数据表

数据源`ds0`建`user_info`数据表

```sql
CREATE TABLE `user_info` (
  `id` bigint(24) NOT NULL,
  `account` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

数据源`ds1`建`cs_order_0`、`cs_order_1`数据表
```sql
CREATE TABLE `cs_order_0` (
  `id` bigint(24) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `node` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `cs_order_1` (
  `id` bigint(24) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `node` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


### 定义Service

**CsOrderService**

```java
public interface CsOrderService {
    public void addCsOrder(CsOrder csOrder);
    public List<CsOrder> getCsOrderList();
}


@Service
public class CsOrderServiceImpl implements CsOrderService {
    @Autowired
    private CsOrderMapper csOrderMapper;

    @Override
    public void addCsOrder(CsOrder csOrder) {
        csOrderMapper.insert(csOrder);
    }
    @Override
    public List<CsOrder> getCsOrderList() {
        List<CsOrder> csOrderList= csOrderMapper.selectList(new QueryWrapper<>());
        return csOrderList;
    }
}
```

**UserInfoService**

```java
public interface UserInfoService {
    public void addUserInfo(UserInfo userInfo);
    public List<UserInfo> getUserInfoList();
}

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void addUserInfo(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }
    @Override
    public List<UserInfo> getUserInfoList() {
        List<UserInfo> userInfoList= userInfoMapper.selectList(new QueryWrapper<>());
        return userInfoList;
    }
}
```



## 踩坑测试

### 垂直拆分数据库

两张不同表在两个不同数据库

#### 关联查询问题
`OrderVO`类用于接收查询结果

```java
@Data
@Builder
public class OrderVO {
    private Long id;
    private Long userId;
    private Integer amount;
    private Integer status;
}
```
`CsOrderMapper`定义查询接口

```java
@Mapper
public interface CsOrderMapper extends BaseMapper<CsOrder> {
    OrderVO findOrderById(@Param("orderId") Long orderId);
}

```
`CsOrderMapper.xml`中`sql`语句

```xml
<select id="findOrderById" resultMap="OrderVO">
        SELECT o.id as orderId,o.amount,o.status,u.id as userId
        FROM cs_order o,user_info u
        WHERE o.user_id = u.id
         and o.id = #{orderId}
</select>
```

关联`cs_order`和`user_info`表进行查询会报错，由于两张表在不同数据库中，所以无法进行关联查询

如果是单表查询，则能正常执行

```xml
<!--单表查询-->
<select id="findOrderById" resultMap="OrderVO">
		SELECT o.id as orderId,o.amount,o.status,o.user_id as userId
		FROM cs_order o
		WHERE 1=1
		and o.id = #{orderId}
</select>
```
