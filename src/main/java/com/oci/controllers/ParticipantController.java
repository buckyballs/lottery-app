package com.oci.controllers;

import com.oci.domain.Lottery;
import com.oci.domain.LotteryWinner;
import com.oci.domain.Participant;
import com.oci.services.LotteryService;
import com.oci.services.ParticipantService;
import com.oci.util.DateTimeUtils;
import com.oci.util.ObjectOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@RequestMapping("/participant")
@Controller
public class ParticipantController {

    private Participant participant;
    private ParticipantService participantService;
    private LotteryService lotteryService;

    @Autowired
    private LotteryWinner lotteryWinner;

    @Autowired
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Autowired
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Autowired
    public void setLotteryService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

/*    @Autowired
    public void setLotteryWinner(LotteryWinner lotteryWinner) {
        this.lotteryWinner = lotteryWinner;
    }*/

    @ModelAttribute("participant")
    public Participant loadEmptyModelBean() {
        return participant;
    }

    @RequestMapping("/new")
    public ModelAndView newParticipant(ModelAndView modelAndView) {
        Lottery lottery = lotteryService.getById(1);
        if (!new Date().before(lottery.getDrawingTime())) {
            modelAndView.setViewName("redirect:errors");
            return modelAndView;
        } else {
            // drawTimeString used by countdown timer
            modelAndView.addObject("drawTimeString", DateTimeUtils.getDateString(lottery.getDrawingTime()));
            // remainingTime is also shown on view in case browser does not support javascript
            modelAndView.addObject("remainingTime", DateTimeUtils.calDuration(new Date(), lottery.getDrawingTime()));
            modelAndView.addObject("participantform", participant);
            modelAndView.setViewName("participant/participantform");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveOrUpdate(@Valid Participant participant, ModelAndView modelAndView) {
        Lottery lottery = lotteryService.getById(1);
        try {
            if (new Date().before(lottery.getDrawingTime())) {
                List<Participant> participants = (List<Participant>) participantService.listAll();
                if (!ObjectOperations.containsEmail(participants, participant)) {
                    // increment participant id to save as new record in table
                    participant.setParticipantId(participants.size() + 1);
                    Participant newParticipant = participantService.saveOrUpdate(participant);
                    modelAndView.addObject("newParticipant", newParticipant);
                    modelAndView.addObject("winChances", participantService.listAll().isEmpty() ? "100%" : BigDecimal.valueOf(100.0 / participantService.listAll().size()).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
                    // drawTimeString used by countdown timer
                    modelAndView.addObject("drawTimeString", DateTimeUtils.getDateString(lottery.getDrawingTime()));
                    // remainingTime is also shown on view in case browser does not support javascript
                    modelAndView.addObject("remainingTime", DateTimeUtils.calDuration(new Date(), lottery.getDrawingTime()));
                    modelAndView.setViewName("participant/show");
                    return modelAndView;
                } else {
                    modelAndView.setViewName("redirect:errors");
                    return modelAndView;
                }
            } else {
                modelAndView.setViewName("redirect:errors");
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("redirect:errors");
            return modelAndView;
        }
    }
}