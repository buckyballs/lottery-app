package com.oci.controllers;

import com.oci.services.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Ishtiaq on 2/3/2017.
 */
//@Controller
public class EmailController {

    //@Autowired
    private EmailService emailService;

    /**
     * Send prize email to lottery winner
     */
    @RequestMapping(value = "/winner-email", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> sendEmailToLotteryWinner(@RequestBody final Map lotteryWinnerEmailMap) throws Exception {

        Boolean response = emailService.sendEmailToLotteryWinner(lotteryWinnerEmailMap);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
