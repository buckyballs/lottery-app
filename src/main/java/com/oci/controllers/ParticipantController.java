package com.oci.controllers;

import com.oci.domain.Participant;
import com.oci.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@RequestMapping("/participant")
@Controller
public class ParticipantController {

    private ParticipantService participantService;
    private Participant participant;

    @Autowired
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @Autowired
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @ModelAttribute("participant")
    public Participant loadEmptyModelBean() {
        return new Participant();
    }

    @RequestMapping("/new")
    public String newParticipant(Model model) {
        model.addAttribute("participantForm", new Participant());
        return "participant/participantform";
    }

    @RequestMapping("/show")
    public String showParticipant(Model model) {
        model.addAttribute("participant", participantService.getById(participant.getParticipantId()));
        return "participant/show";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Participant participant, BindingResult bindingResult) {
        try {
            participantService.saveOrUpdate(participant);
        } catch (Exception e) {
            return "redirect:errors";
        }
        return "redirect:participant/show";
    }
}