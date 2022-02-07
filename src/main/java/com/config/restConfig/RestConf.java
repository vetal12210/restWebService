package com.config.restConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.dao.UserDao;
import com.rest.UserResource;
import com.service.UserService;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;
import java.util.List;
@Configuration
@ComponentScan(basePackages = "com")
public class RestConf {

    private final UserDao userDao;

    @Autowired
    public RestConf(UserDao userDao) {
        this.userDao = userDao;
    }

    @ApplicationPath("/")
    public static class JaxRsApiApplication extends Application {
    }

    @Bean(destroyMethod = "shutdown")
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    @DependsOn("cxf")
    public Server jaxRsServer(ApplicationContext appContext) {
        JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(), JAXRSServerFactoryBean.class);
        factory.setServiceBeans(List.of(userRestController()));
        factory.setAddress("/" + factory.getAddress());
        factory.setProvider(filter());
        factory.setProvider(jsonProvider());
        return factory.create();
    }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }

    @Bean
    public UserService userService() {
        return new UserService(userDao);
    }

    @Bean
    public UserResource userRestController() {
        return new UserResource();
    }

    @Bean
    public CrossOriginResourceSharingFilter filter() {
        return new CrossOriginResourceSharingFilter();
    }
}
