package com.oci.controllers;

import com.oci.domain.Participant;
import com.oci.services.LotteryService;
import com.oci.services.ParticipantService;
import com.oci.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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

    @RequestMapping("/show/{id}")
    public String showParticipant(@PathVariable Integer id, Model model) {
        model.addAttribute("participant", participantService.getById(id));
        model.addAttribute("remainingTime", DateTimeUtils.calDuration(new Date(), lotteryService.getById(1).getDrawingTime()));
        model.addAttribute("winChances", participantService.listAll().isEmpty() ? "100%" : (100 / participantService.listAll().size()) + "%");
        return "participant/show";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Participant participant, BindingResult bindingResult) {
        try {
            Participant newParticipant = participantService.saveOrUpdate(participant);
            return "redirect:participant/show/" + newParticipant.getParticipantId();
        } catch (Exception e) {
            return "redirect:errors";
        }
    }
}