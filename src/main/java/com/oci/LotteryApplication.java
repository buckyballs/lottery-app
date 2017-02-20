package com.oci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LotteryApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LotteryApplication.class, args);
        System.out.println(ctx.getBeanDefinitionCount());
        int i = 1;
        for (String beanNAme : ctx.getBeanDefinitionNames()) {
            System.out.println("[" + i + "]" + beanNAme);
        }
    }

}
