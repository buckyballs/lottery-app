package com.oci;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.security.auth.message.config.AuthConfigFactory;

@SpringBootApplication
public class LotteryApplication {

    public static void main(String[] args) {
        if (AuthConfigFactory.getFactory() == null) {
            AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
        }
        ApplicationContext ctx = SpringApplication.run(LotteryApplication.class, args);
        System.out.println(ctx.getBeanDefinitionCount());
        int i = 1;
        for (String beanNAme : ctx.getBeanDefinitionNames()) {
            System.out.println("[" + i + "]" + beanNAme);
        }
    }
}
