import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Auther: WUQW
 * @Date: 2018/11/29 13:36
 * @Description:
 */
public class DubboStart {

    public static void main(String[] args) throws IOException {
        System.out.println("dubbo is starting...");
        ClassPathXmlApplicationContext  context= new ClassPathXmlApplicationContext(new String[]{"/dubbo-provider.xml"});
        context.start();
        System.out.println("dubbo is start success");
        System.out.println("print any key exit");
        System.in.read();
    }
}
