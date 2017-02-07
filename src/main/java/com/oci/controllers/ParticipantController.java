package com.oci.controllers;

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
        if (!new Date().before(lotteryService.getById(1).getDrawingTime())) {
            modelAndView.setViewName("redirect:errors");
            return modelAndView;
        } else {
            modelAndView.addObject("participantform", participant);
            modelAndView.setViewName("participant/participantform");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveOrUpdate(@Valid Participant participant, ModelAndView modelAndView) {
        try {
            if (new Date().before(lotteryService.getById(1).getDrawingTime())) {
                List<Participant> participants = (List<Participant>) participantService.listAll();
                if (!ObjectOperations.containsEmail(participants, participant)) {
                    Participant newParticipant = participantService.saveOrUpdate(participant);
                    modelAndView.addObject("newParticipant", newParticipant);
                    modelAndView.addObject("remainingTime", DateTimeUtils.calDuration(new Date(), lotteryService.getById(1).getDrawingTime()));
                    modelAndView.addObject("winChances", participantService.listAll().isEmpty() ? "100%" : BigDecimal.valueOf(100.0 / participantService.listAll().size()).setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
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
            modelAndView.setViewName("redirect:errors");
            return modelAndView;
        }
    }
}