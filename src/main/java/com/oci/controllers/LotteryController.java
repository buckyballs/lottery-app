package com.oci.controllers;

import com.oci.domain.Lottery;
import com.oci.services.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@RequestMapping("/lottery")
@Controller
public class LotteryController {

    private LotteryService lotteryService;
    //private Validator lotteryValidator;
    private Lottery lottery;

    @Autowired
    public void setLotteryService(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

/*    @Autowired
    @Qualifier("lotteryValidator")
    public void setLotteryValidator(Validator lotteryValidator) {
        this.lotteryValidator = lotteryValidator;
    }*/

    @Autowired
    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    @ModelAttribute("lottery")
    public Lottery loadEmptyModelBean(){
        return new Lottery();
    }

    @RequestMapping("/new")
    public String newLottery(Model model) {
        model.addAttribute("lotteryForm", new Lottery());
        return "lottery/lotteryform";
    }

    @RequestMapping("/show/{id}")
    public String showLOttery(@PathVariable Integer id, Model model) {
        model.addAttribute("lottery", lotteryService.getById(id));
        return "lottery/show";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Lottery lottery, BindingResult bindingResult) {

        //lotteryValidator.validate(lottery, bindingResult);

        if (bindingResult.hasErrors()) {
            return "lottery/lotteryform";
        }

        Lottery newLottery = lotteryService.saveOrUpdate(lottery);
        return "redirect:lottery/show/" + newLottery.getLotteryId();
    }


}
