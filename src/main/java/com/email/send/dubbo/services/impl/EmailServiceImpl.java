package com.email.send.dubbo.services.impl;
import com.email.send.dto.Message;
import com.email.send.dubbo.services.EmailService;
import com.email.send.utils.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: WUQW
 * @Date: 2018/11/28 14:55
 * @Description:邮件发送服务
 */
@Service("emailServiceImpl")
public class EmailServiceImpl implements EmailService {
    private static Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private EmailUtil emailUtil;

    @Override
    public boolean sendMail(Message message) throws UnsupportedEncodingException, MessagingException {
        if (verify(message)) {
            log.debug("参数缺失或参数为空" + message);
            return false;
        }
        log.info("邮件发送中...");
        boolean b = emailUtil.sendEmail(message);
        if (b) {
            return true;
        } else {
            return false;
        }

    }

    /*校验参数*/

    private boolean verify(Message message) {
        if (message == null) {
            return false;
        }
        if (message.getToAddress() == null || message.getToAddress() == "" || !message.getToAddress().contains("@")) {
            return false;
        }
        return true;
    }
}
