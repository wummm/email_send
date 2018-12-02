package com.email.send.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @Auther: WUQW
 * @Date: 2018/11/28 16:58
 * @Description:
 */
public class SimpleAuthenticator  extends Authenticator {
    private String userName;
    private String passWord;


    public SimpleAuthenticator(String userName,String passWord){
        this.userName=userName;
        this.passWord=passWord;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName,passWord);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
