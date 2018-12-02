package com.email.send.dto;

import javax.mail.Address;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: WUQW
 * @Date: 2018/11/28 14:49
 * @Description:邮件实体类
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    /*发送者*/
    private String fromAddress;
    /*发送邮箱密码*/
    private String fromPassword;
    /*接收邮箱*/
    private String toAddress;

    /*抄送邮箱*/
    private String ccAddress;
    /*密送邮箱*/
    private String bcAddress;
    /*邮件标题*/
    private String subject;
    /*邮件类容*/
    private String context;

    private List<String> file;

    public String getFromAddress() {
        return fromAddress;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getBcAddress() {
        return bcAddress;
    }

    public void setBcAddress(String bcAddress) {
        this.bcAddress = bcAddress;
    }



    /*发送者*/
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromPassword() {
        return fromPassword;
    }

    /*发送邮箱密码*/
    public void setFromPassword(String fromPassword) {
        this.fromPassword = fromPassword;
    }

    public String getToAddress() {
        return toAddress;
    }

    /*接收邮箱*/
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    /*邮件标题*/
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    /*邮件类容*/
    public void setContext(String context) {
        this.context = context;
    }
}
