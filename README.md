本项目集成了dubbo、spring、springmvc、mybatis，仅作为开发的基础框架，不包含任何业务逻辑
1.项目结构介绍
(1)base：所有项目所有引入的jar包的基础pom.xml
(2)common:包括项目所用的实体类信息以及client和service端所用的interface信息
(3)service:dubbo的服务端，集成了mybatis
(4)clientparent：client端所需的jar包配置
(5)client:dubbo的客户端，继承了springmvc
