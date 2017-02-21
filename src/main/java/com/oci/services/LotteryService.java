package com.oci.services;

import com.oci.domain.Lottery;
import com.oci.util.DateTimeUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by maqsoodi on 1/26/2017.
 */
public interface LotteryService extends CRUDService<Lottery> {

    default ModelAndView showLottery(Lottery lottery, ModelAndView modelAndView) {
        modelAndView.addObject("lottery", lottery);
        // drawTimeString used by countdown timer
        modelAndView.addObject("drawTimeString", DateTimeUtils.getDateString(lottery.getDrawTime()));
        // provide currentTimeString from server to correctly display countdown timer values
        // in case time mismatches on client and server machines
        modelAndView.addObject("currentTimeString", DateTimeUtils.getDateString(new Date()));
        // remainingTime is also shown on view in case browser does not support javascript
        modelAndView.addObject("remainingTime", DateTimeUtils.calDuration(new Date(), lottery.getDrawTime()));
        modelAndView.setViewName("lottery/show");

        return modelAndView;
    }

    default Lottery setDrawTimeFromMinutes(Lottery lottery) {
        // 60*1000 to convert minutes into milliseconds
        if (lottery.getMinutesToDraw() != null) {
            lottery.setDrawTime(DateTimeUtils.plusDuration(new Date(), 60 * 1000 * Long.valueOf(lottery.getMinutesToDraw())));
        } else {
            lottery.setDrawTime(null);
        }
        return lottery;
    }

}
