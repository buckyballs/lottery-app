package com.oci.domain;

import org.springframework.stereotype.Component;

/**
 * Created by maqsoodi on 2/1/2017.
 */
@Component
public class LotteryWinner {

    private Participant lotteryWinner;

    public Participant getLotteryWinner() {
        return lotteryWinner;
    }

    public void setLotteryWinner(Participant lotteryWinner) {
        this.lotteryWinner = lotteryWinner;
    }
}
