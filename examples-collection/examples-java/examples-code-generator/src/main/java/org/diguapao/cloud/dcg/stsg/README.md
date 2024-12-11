# 指定表结构生产代码（Specifies Table Structure Generation）

## 能力介绍

通过配置 examples-collection/examples-code-generator/src/main/java/org/diguapao/cloud/dcg/stsg/DemoTapeGenSql.sql 

文件里建表语句，可根据建表语句生成代码，亲测过单条建表语句可用，生成的代码测试工作正在进行中……。

运行 org.diguapao.cloud.dcg.stsg.CodeGenerator#main 方法执行代码生成，生成的代码在 org.diguapao.cloud.dcg.stsg.CodeGenerator.OUTPUT_DIR 目录下，可自行更改。

支持生成代码的模板位于 org.diguapao.cloud.dcg.stsg.CodeGenerator.TEMPLATE_DIR 目录下，可进行自定义或者二开。

## 问答答疑

### java.io.File.canRead 返回 false 怎么办？

也就是以下报错可是的原因是因为java.io.File.canRead 返回 false：

```textmate
Exception in thread "main" org.apache.velocity.exception.ResourceNotFoundException: Unable to find resource 'PO.java.vm'
	at org.apache.velocity.runtime.resource.ResourceManagerImpl.loadResource(ResourceManagerImpl.java:465)
	at org.apache.velocity.runtime.resource.ResourceManagerImpl.getResource(ResourceManagerImpl.java:346)
	at org.apache.velocity.runtime.RuntimeInstance.getTemplate(RuntimeInstance.java:1677)
	at org.apache.velocity.runtime.RuntimeInstance.getTemplate(RuntimeInstance.java:1656)
	at org.apache.velocity.app.VelocityEngine.getTemplate(VelocityEngine.java:314)
	at org.diguapao.cloud.dcg.stsg.CodeGenerator.generateFile(CodeGenerator.java:77)
	at org.diguapao.cloud.dcg.stsg.CodeGenerator.main(CodeGenerator.java:57)
```

出现这个问题可能是无权限读取文件，或者文件不存在。