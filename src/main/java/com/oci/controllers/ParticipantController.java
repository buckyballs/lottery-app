package com.oci.controllers;

import com.oci.domain.Participant;
import com.oci.services.LotteryService;
import com.oci.services.ParticipantService;
import com.oci.services.SecureFormDisplatService;
import com.oci.util.DateTimeUtils;
import com.oci.util.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private SecureFormDisplatService secureFormDisplatService;

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

    @Autowired
    public void setSecureFormDisplatService(SecureFormDisplatService secureFormDisplatService) {
        this.secureFormDisplatService = secureFormDisplatService;
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

    @RequestMapping("/show")
    public String showParticipant(@RequestBody Participant participant, Model model) {
        model.addAttribute("participant", participant);
        model.addAttribute("remainingTime", DateTimeUtils.calDuration(new Date(), lotteryService.getById(1).getDrawingTime()));
        model.addAttribute("winChances", participantService.listAll().isEmpty() ? "100%" : (100 / participantService.listAll().size()) + "%");
        return "participant/show";
    }

    @RequestMapping("/winner")
    public String showWinner(Model model) {
        Integer winnerIndex = RandomNumber.randInt(0, participantService.listAll().size() - 1);
        Participant winner = (Participant) participantService.listAll().get(winnerIndex);
        model.addAttribute("winner", winner);
        model.addAttribute("msgToWinner", lotteryService.getById(1).getMsgToWinner());
        model.addAttribute("prizeDescription", lotteryService.getById(1).getPrizeDescription());
        return "participant/winner";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Participant participant, BindingResult bindingResult) {
        try {
            if (new Date().before(lotteryService.getById(1).getDrawingTime())) {
                Participant newParticipant = participantService.saveOrUpdate(participant);
                secureFormDisplatService.showInfoToParticipant(participant);
                ;
                return "";
            } else {
                secureFormDisplatService.showInfoToParticipant(participant);
                return "";
            }
        } catch (Exception e) {
            return "redirect:errors";
        }
    }
}