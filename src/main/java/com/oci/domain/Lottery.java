package com.oci.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@Entity
@Component
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer lotteryId;

    @NotNull
    @NotBlank
    @NotEmpty
    private Date drawingTime;

    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    @NotNull
    @NotBlank
    @NotEmpty
    private String prizeDescription;

    @NotNull
    @NotBlank
    @NotEmpty
    private String msgToWinner;

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Date getDrawingTime() {
        return drawingTime;
    }

    public void setDrawingTime(Date drawingTime) {
        this.drawingTime = drawingTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public String getMsgToWinner() {
        return msgToWinner;
    }

    public void setMsgToWinner(String msgToWinner) {
        this.msgToWinner = msgToWinner;
    }
}
