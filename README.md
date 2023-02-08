# Native JDBC

Doing a `./gradlew clean nativeCompile` will result in an error during the task `processAot`.
````
...
Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.sql.DataSource]: Factory method 'dataSource' threw exception with message: Failed to determine suitable jdbc url
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:171)
        at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:653)
        ... 87 more
Caused by: org.springframework.boot.autoconfigure.jdbc.DataSourceProperties$DataSourceBeanCreationException: Failed to determine suitable jdbc url
        at org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.determineUrl(DataSourceProperties.java:228)
        at org.springframework.boot.autoconfigure.jdbc.DataSourceProperties.initializeDataSourceBuilder(DataSourceProperties.java:124)
        at com.ilionx.poc.DataSourceConfig.dataSource(DataSourceConfig.java:30)
        at com.ilionx.poc.DataSourceConfig$$SpringCGLIB$$0.CGLIB$dataSource$0(<generated>)
        at com.ilionx.poc.DataSourceConfig$$SpringCGLIB$$2.invoke(<generated>)
        at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:258)
        at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:331)
        at com.ilionx.poc.DataSourceConfig$$SpringCGLIB$$0.dataSource(<generated>)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.base/java.lang.reflect.Method.invoke(Method.java:568)
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:139)
        ... 88 more
````
