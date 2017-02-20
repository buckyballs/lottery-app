package com.oci.config;

import com.oci.domain.LotteryWinner;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by maqsoodi on 1/30/2017.
 */
@Configuration
@ImportResource("classpath:xmlconfig.xml")
public class CommonBeanConfig {

    @Bean
    public StrongPasswordEncryptor strongPasswordEncryptor() {
        StrongPasswordEncryptor strongPasswordEncryptor = new StrongPasswordEncryptor();
        return strongPasswordEncryptor;
    }

    @Bean
    public LotteryWinner getLotteryWinner() {
        LotteryWinner lotteryWinner = new LotteryWinner();
        return lotteryWinner;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean
    public VelocityEngine velocityEngine() throws VelocityException, IOException {
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader." +
                        "ClasspathResourceLoader");
        factory.setVelocityProperties(props);

        return factory.createVelocityEngine();
    }

}
