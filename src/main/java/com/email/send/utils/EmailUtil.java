package com.email.send.utils;

import com.email.send.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @Auther: WUQW
 * @Date: 2018/11/28 15:48
 * @Description:发送邮件工具类
 */
@Component
public class EmailUtil {
    private static Logger log = LoggerFactory.getLogger(EmailUtil.class);
    @Value("${form}")
    private String from;  //默认发送者
    @Value("${fromPassword}")
    private String fromPassword;
    @Value("${host}")
    private String host;
    @Value("${auth}")
    private String auth;

    public boolean sendEmail(Message message) throws UnsupportedEncodingException, MessagingException, javax.mail.MessagingException {
        /*创建参数配置, 用于连接邮件服务器的参数配置*/
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.auth", auth);
        /* props.setProperty("mail.smtp.port","25"); 默认25端口不加密*/

        /*使用ssl连接邮箱服务器加上这个*/
      /*  props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  props.setProperty("mail.smtp.socketFactory.fallback", "false");
         props.setProperty("mail.smtp.port", "465");
         props.setProperty("mail.smtp.socketFactory.port", "465");*/

        Authenticator auth1 = new SimpleAuthenticator(message.getFromAddress() == null ? from : message.getFromAddress(), message.getFromPassword() == null ? fromPassword : message.getFromPassword());
        /*创建邮件会话*/
        Session session = Session.getInstance(props, auth1);
        /*调试时候使用，开启sessiondebug模式*/
        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);
        if (message.getFromAddress() != null) {
            mimeMessage.setFrom(new InternetAddress(message.getFromAddress(), "wuqw", "UTF-8"));
        } else {
            mimeMessage.setFrom(new InternetAddress(from));
        }
        /*发送对象*/
        if (message.getToAddress().contains(",")) {
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(message.getToAddress())); //发送多个对象
        } else {
            mimeMessage.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(message.getToAddress()));
        }

        /*抄送*/
        if (message.getCcAddress() != null && !message.getCcAddress().contains(",")) {
            mimeMessage.setRecipient(javax.mail.Message.RecipientType.CC, new InternetAddress(message.getCcAddress()));
        } else if (message.getCcAddress() != null) {
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(message.getCcAddress()));
        }
        /*密送*/
        if (message.getBcAddress() != null && !message.getBcAddress().contains(",")) {
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.BCC, new InternetAddress[]{new InternetAddress(message.getBcAddress())});
        } else if (message.getBcAddress() != null) {
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.BCC, InternetAddress.parse(message.getBcAddress()));
        }
        mimeMessage.setSubject(message.getSubject());
        mimeMessage.setContent(message.getContext(), "text/html;charset=utf-8");
        mimeMessage.setSentDate(new Date());
        /*附件暂不支持*/

        /*创建发送通道*/
        try {
            Transport.send(mimeMessage);
            saveMailSendToFolder(mimeMessage);
        } catch (MessagingException e) {
            log.error("邮件发送失败");
            e.printStackTrace();
            return false;
        }
        log.debug("邮件发送成功");
        return true;
    }

    /*将邮件保存到发件箱*/
    private void saveMailSendToFolder(MimeMessage mimeMessage) throws javax.mail.MessagingException {
        Store store = null;  //邮箱存储
        Folder sentFolder = null;  //发件文件夹
        try {
            sentFolder = getMailFromFolder(mimeMessage, store);
            mimeMessage.setFlag(Flags.Flag.SEEN, true); // 设置已读标志

            /*保存*/
            sentFolder.appendMessages(new MimeMessage[]{mimeMessage});

            log.info("已保存到发件箱...");
        } catch (Exception e) {
            log.info("保存到发件箱失败");
            e.printStackTrace();
        } finally {
            // 判断发件文件夹是否打开如果打开则将其关闭
            if (sentFolder != null && sentFolder.isOpen()) {
                try {
                    sentFolder.close(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            // 判断邮箱存储是否打开如果打开则将其关闭
            if (store != null && store.isConnected()) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*获取发件箱*/
    private Folder getMailFromFolder(MimeMessage message, Store store) throws MessagingException {
        // 准备连接服务器的会话信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", "imap.qq.com");
        props.setProperty("mail.imap.port", "143");

        /** 建立ssl连接 */
       /* props.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.imap.starttls.enable", "true");
        props.setProperty("mail.imap.socketFactory.port", "993");*/

        // 创建Session实例对象
        Session session = Session.getInstance(props);
        URLName urln = new URLName("imap", "imap.qq.com", 143, null,
                from, fromPassword);
        // 创建IMAP协议的Store对象
        store = session.getStore(urln);
        store.connect();

        // 获得发件箱
        Folder folder = store.getFolder("Sent Messages");
        // 以读写模式打开发件箱
        folder.open(Folder.READ_WRITE);
        return folder;
    }
}
