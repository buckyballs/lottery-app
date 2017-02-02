package com.oci.schedulers;

import com.oci.domain.LotteryWinner;
import com.oci.services.AsyncEmailService;
import com.oci.services.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * Created by maqsoodi on 2/2/2017.
 */
@EnableScheduling
@Component
public class EmailScheduler {

    @Autowired
    private LotteryWinner lotteryWinner;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private AsyncEmailService asyncEmailService;






}
