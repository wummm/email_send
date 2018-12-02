package com.email.send;

import com.email.send.dto.Message;
import com.email.send.utils.EmailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: WUQW
 * @Date: 2018/11/28 22:55
 * @Description:写这个测试类的时候花了我挺长时间找bug的，写此增加点印象，1.首先我是直接使用EamilUtil这个工具类，导致util中的属性@value无法注入值，造成空指针
 * 原因，要想使用@value首先得把这个类交给spring容器管理，直接new是main管理得，在类上加入@commpent注解。在测试中注入。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/dubbo-provider.xml"})
public class EmailTest {
    @Autowired  /*交给spring容器管理，才能@value注入，直接new不行无法注入*/
    private EmailUtil emailUtil;
    @Test
    public void emailsendTest() throws UnsupportedEncodingException, MessagingException {
        Message message=new Message();
        message.setToAddress("729641409@qq.com,649088596@qq.com");
        message.setCcAddress("695365209@qq.com");
        message.setSubject("测试");
        message.setContext("this is a test,to");
        boolean b = emailUtil.sendEmail(message);
        System.out.println(b);
    }
}
