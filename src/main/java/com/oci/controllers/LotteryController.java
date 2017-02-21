package com.oci.controllers;

import com.oci.domain.Lottery;
import com.oci.schedulers.EmailScheduler;
import com.oci.services.LotteryService;
import com.oci.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@RequestMapping("/lottery")
@Controller
public class LotteryController {

    private LotteryService lotteryService;
    private Validator lotteryValidator;
    private Lottery lottery;
    private ParticipantService participantService;

    @Autowired
    private EmailScheduler emailScheduler;

    @Autowired
    public void setLotteryService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @Autowired
    @Qualifier("lotteryValidator")
    public void setLotteryValidator(Validator lotteryValidator) {
        this.lotteryValidator = lotteryValidator;
    }

    @Autowired
    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    @Autowired
    public void setParticipantService(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @ModelAttribute("lottery")
    public Lottery loadEmptyModelBean() {
        return lottery;
    }

    @RequestMapping("/new")
    public String newLottery(Model model) {
        // Allow admin to update lottery, this will preserve previously entered lottery values
        model.addAttribute("lotteryform", lottery);
        return "lottery/lotteryform";
    }

    @RequestMapping("/show/{id}")
    public ModelAndView showLottery(@PathVariable Integer id, ModelAndView modelAndView) {
        Lottery lottery = lotteryService.getById(id);
        lotteryService.showLottery(lottery, modelAndView);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Lottery lottery, BindingResult bindingResult) {
        // 1. Update lottery draw time
        Lottery updatedLottery = lotteryService.setDrawTimeFromMinutes(lottery);
        // 2. Validate lottery
        lotteryValidator.validate(updatedLottery, bindingResult);
        if (bindingResult.hasErrors()) {
            return "lottery/lotteryform";
        }
        // 3. Save lottery to DB
        Lottery savedLottery = lotteryService.saveOrUpdate(updatedLottery);
        //4. Schedule future thread to send email to lottery winner based on new draw time
        emailScheduler.sendEmailToLotteryWinner(savedLottery.getDrawTime());
        return "redirect:lottery/show/" + savedLottery.getLotteryId();
    }

    @RequestMapping(value = "/delete-oci-lottery", method = RequestMethod.GET)
    public String deleteLottery() {
        // delete lottery and all participants
        if (!lotteryService.listAll().isEmpty())
            lotteryService.delete();
        if (!participantService.listAll().isEmpty())
            participantService.delete();
        return "redirect:new";
    }

}
