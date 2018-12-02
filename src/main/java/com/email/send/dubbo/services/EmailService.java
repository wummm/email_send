package com.email.send.dubbo.services;

import com.email.send.dto.Message;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: WUQW
 * @Date: 2018/11/28 14:54
 * @Description:邮件服务类
 */
public interface EmailService {
    boolean sendMail(Message message) throws UnsupportedEncodingException, MessagingException;

}
