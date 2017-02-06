package com.oci.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ishtiaq on 2/3/2017.
 */
@Service
public class EmailService {

    //@Value("${mail.from.address.group.order}")
    private String fromAddress;

    //@Value("${mail.images.url}")
    private String imageUrl;

    @Autowired
    private VelocityEngine velocityEngine;

    //@Autowired
    private JavaMailSender mailSender;

    /**
     * Send email to lottery winner
     *
     * @param lotteryWinnerEmailMap contains lottery winner email info
     * @throws Exception
     */
    public boolean sendEmailToLotteryWinner(final Map lotteryWinnerEmailMap) throws Exception {
        MimeMessagePreparator preparator = null;
        try {
            if (EmailValidator.getInstance().isValid(String.valueOf(lotteryWinnerEmailMap.get("winnerEmail")))) {
                preparator = new MimeMessagePreparator() {
                    @Override
                    public void prepare(MimeMessage mimeMessage) throws Exception {
                        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                        message.setFrom(fromAddress, "Object Computing Inc. OCI");
                        message.setTo(String.valueOf(lotteryWinnerEmailMap.get("winnerEmail")));
                        message.setSubject("You've won the Lottery.");
                        Map model = new HashMap<>();
                        model.put("imagesBaseUrl", imageUrl);
                        model.put("winnerName", String.valueOf(lotteryWinnerEmailMap.get("winnerEmail")));
                        model.put("msgToWinner", String.valueOf(lotteryWinnerEmailMap.get("msgToWinner")));
                        model.put("prizeDescription", String.valueOf(lotteryWinnerEmailMap.get("prizeDescription")));
                        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/lotteryWinnerEmail.vm", "UTF-8", model);
                        message.setText(text, true);
                    }
                };
                mailSender.send(preparator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}