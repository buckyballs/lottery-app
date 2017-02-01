package com.oci.config;

import com.oci.domain.LotteryWinner;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by maqsoodi on 1/30/2017.
 */
@Configuration
public class CommonBeanConfig {

    @Bean
    public StrongPasswordEncryptor strongPasswordEncryptor(){
        StrongPasswordEncryptor strongPasswordEncryptor = new StrongPasswordEncryptor();
        return strongPasswordEncryptor;
    }

    @Bean
    public LotteryWinner getLotteryWinner(){
        LotteryWinner lotteryWinner = new LotteryWinner();
        return lotteryWinner;
    }

}
