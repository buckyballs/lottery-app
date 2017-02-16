package com.oci.controllers;

import com.oci.domain.Lottery;
import com.oci.schedulers.EmailScheduler;
import com.oci.services.LotteryService;
import com.oci.services.ParticipantService;
import com.oci.util.DateTimeUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

import javax.validation.Valid;
import java.util.Date;

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
        // Allow admin to update lottery, this will keep previously entered participants in the lottery
        model.addAttribute("lotteryform", lottery);
        return "lottery/lotteryform";
    }

    @RequestMapping("/show/{id}")
    public String showLottery(@PathVariable Integer id, Model model) {
        Lottery lottery = lotteryService.getById(id);
        model.addAttribute("lottery", lottery);

        Date drawTime = lottery.getDrawingTime();
        emailScheduler.sendEmailToLotteryWinner(drawTime);
        DateTime dt = new DateTime(drawTime);
        //"2017-02-16 21:00:00"
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String drawTimeString = fmt.print(dt);
        model.addAttribute("drawTimeString", drawTimeString);

        return "lottery/show";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Lottery lottery, BindingResult bindingResult) {

        // Allow admin to update lottery, this will keep previously entered participants in the lottery
        /*if(!lotteryService.listAll().isEmpty()){
            return "redirect:errors";
        }*/
        // 60*1000 to convert minutes into milliseconds
        lottery.setDrawingTime(DateTimeUtils.plusDuration(new Date(), 60 * 1000 * Long.valueOf(lottery.getMinutesToDraw())));
        lotteryValidator.validate(lottery, bindingResult);

        if (bindingResult.hasErrors()) {
            return "lottery/lotteryform";
        }

        Lottery newLottery = lotteryService.saveOrUpdate(lottery);
        emailScheduler.sendEmailToLotteryWinner(newLottery.getDrawingTime());
        return "redirect:lottery/show/" + newLottery.getLotteryId();
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
