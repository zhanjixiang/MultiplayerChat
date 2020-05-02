package com.zjx.API;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import java.util.Random;

public class SendEmailAPI {    //发送邮箱验证码API

    private Random random;
    private StringBuffer rdmth;
    private Boolean TF;

    public String email(String EmailID){
        HtmlEmail htmlEmail = new HtmlEmail();   // 创建邮件发送实例对象
        htmlEmail.setHostName("smtp.qq.com");
        htmlEmail.setCharset("UTF-8");
        random = new Random();
        rdmth = new StringBuffer();
        for(int i = 0; i < 4 ; i++){
            TF = random.nextBoolean();
            //char
            if(TF){
                int suiji = random.nextInt(57)%58+65;    //字母验证码
                char temp = (char) suiji;
                rdmth.append(temp+" ");
            }else {
                int suiji = random.nextInt(9);     //数字验证码
                rdmth.append(suiji+" ");
            }
        }
        String info = "您的验证码是：    "+rdmth.toString()+"     请保管好您的验证码！";
        try {
            htmlEmail.addTo(EmailID);     //收件人
            htmlEmail.setFrom("1083200830@qq.com","zhan","UTF-8");   //发件人
            htmlEmail.setAuthentication("1083200830@qq.com","igvozsectegbhigh"); //发件人邮箱和授权码
            htmlEmail.setSubject("MultiplayerChat验证码");
            htmlEmail.setMsg(info);
            htmlEmail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return rdmth.toString().replaceAll(" ","");
    }

}
