package com.oci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LotteryApplication {

    public static final String PATH = "/errors";

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LotteryApplication.class, args);
        System.out.println(ctx.getBeanDefinitionCount());
        int i = 1;
        for (String beanNAme : ctx.getBeanDefinitionNames()) {
            System.out.println("[" + i + "]" + beanNAme);
        }
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            //route all errors towards --> /error
            final ErrorPage errorPage = new ErrorPage(PATH);
            container.addErrorPages(errorPage);
        });
    }
}
