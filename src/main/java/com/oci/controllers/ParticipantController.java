package com.oci.controllers;

import com.oci.domain.Participant;
import com.oci.services.LotteryService;
import com.oci.services.ParticipantService;
import com.oci.util.DateTimeUtils;
import com.oci.util.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

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

    @ModelAttribute("participant")
    public Participant loadEmptyModelBean() {
        return participant;
    }

    @RequestMapping("/new")
    public String newParticipant(Model model) {
        model.addAttribute("participantform", participant);
        return "participant/participantform";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveOrUpdate(@Valid Participant participant, ModelAndView modelAndView) {
        try {
            if (new Date().before(lotteryService.getById(1).getDrawingTime())) {
                Participant newParticipant = participantService.saveOrUpdate(participant);
                modelAndView.addObject("newParticipant", newParticipant);
                modelAndView.addObject("remainingTime", DateTimeUtils.calDuration(new Date(), lotteryService.getById(1).getDrawingTime()));
                modelAndView.addObject("winChances", participantService.listAll().isEmpty() ? "100%" : BigDecimal.valueOf(100.0/participantService.listAll().size()).setScale(2, BigDecimal.ROUND_HALF_UP)  + "%");
                modelAndView.setViewName("participant/show");
                return modelAndView;
            } else {
                Integer winnerIndex = RandomNumber.randInt(0, participantService.listAll().size() - 1);
                Participant winner = (Participant) participantService.listAll().get(winnerIndex);
                modelAndView.addObject("winner", winner);
                modelAndView.addObject("msgToWinner", lotteryService.getById(1).getMsgToWinner());
                modelAndView.addObject("prizeDescription", lotteryService.getById(1).getPrizeDescription());
                modelAndView.setViewName("participant/winner");
                return modelAndView;
            }
        } catch (Exception e) {
            modelAndView.setViewName("redirect:errors");
            return modelAndView;
        }
    }
}