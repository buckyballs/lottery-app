package com.oci.schedulers;

import com.oci.domain.LotteryWinner;
import com.oci.domain.Participant;
import com.oci.services.EmailService;
import com.oci.services.LotteryService;
import com.oci.services.ParticipantService;
import com.oci.util.DateTimeUtils;
import com.oci.util.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maqsoodi on 2/2/2017.
 */
@Component
public class EmailScheduler {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private LotteryWinner lotteryWinner;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private EmailService emailService;

    public void sendEmailToLotteryWinner(Date drawTime) {
        // schedule task to email winner after 5 seconds of draw time
        taskScheduler.schedule(new RunnableTask(), DateTimeUtils.addDuration(drawTime, 5000L));
    }

    class RunnableTask implements Runnable {
        @Override
        public void run() {
            // Have draw to get winner
            Integer winnerIndex = RandomNumber.randInt(0, participantService.listAll().size() - 1);
            Participant winner = (Participant) participantService.listAll().get(winnerIndex);
            winner.setWinner(true);
            Participant savedWinner = participantService.saveOrUpdate(winner);
            lotteryWinner.setLotteryWinner(savedWinner);

            // send email to winner
            final Map lotteryWinnerEmailMap = new HashMap();
            lotteryWinnerEmailMap.put("winnerEmail", lotteryWinner.getLotteryWinner().getEmail());
            lotteryWinnerEmailMap.put("winnerName", lotteryWinner.getLotteryWinner().getName());
            lotteryWinnerEmailMap.put("msgToWinner", lotteryService.getById(1).getMsgToWinner());
            lotteryWinnerEmailMap.put("prizeDescription", lotteryService.getById(1).getPrizeDescription());
            try {
                emailService.sendEmailToLotteryWinner(lotteryWinnerEmailMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
