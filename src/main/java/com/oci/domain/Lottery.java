package com.oci.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@Entity
@Component
public class Lottery {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO) // uncomment it to run app for multiple lotteries
    private Integer lotteryId = 1;

    @Transient
    private Integer minutesToDraw;

    @DateTimeFormat(pattern = "MM/dd/yyyy hh:mm a")
    private Date drawingTime;

    @Transient
    private String formattedDrawningTime;

    private String passwordText;

    @NotEmpty(message = "Prize description cannot be empty")
    private String prizeDescription;

    @NotEmpty(message = "Message to winner cannot be empty")
    private String msgToWinner;

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public Integer getMinutesToDraw() {
        return minutesToDraw;
    }

    public void setMinutesToDraw(Integer minutesToDraw) {
        this.minutesToDraw = minutesToDraw;
    }

    public Date getDrawingTime() {
        return drawingTime;
    }

    public void setDrawingTime(Date drawingTime) {
        this.drawingTime = drawingTime;
    }

    public String getFormattedDrawningTime() {
        return new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(drawingTime);
    }

    public void setFormattedDrawningTime(String formattedDrawningTime) {
        this.formattedDrawningTime = formattedDrawningTime;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
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
