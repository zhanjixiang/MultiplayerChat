package com.zjx.Controller;

import com.zjx.API.SendEmailAPI;
import com.zjx.Service.ForgetPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/forgetpwd")
public class ForgetPwdHandler {

    @Autowired
    private ForgetPwdService forgetPwdService;

    private String captcha;

    @RequestMapping(value = "/captcha")
    public void Captcha(String email){
        captcha = new SendEmailAPI().email(email);
        System.out.println("验证码："+captcha);
    }

    @RequestMapping(value = "/updatepwd",params = {"uname","email","yzm","newpwd"})
    public String UpdatePwd(String uname,String email,String yzm,String newpwd){
        if (captcha.equals(yzm)){
            String initemail = forgetPwdService.CheckEmail(uname);

            if (initemail.equals(email)){
                int result = forgetPwdService.UpdatePassword(uname,newpwd,email);
                if (result > 0) {
                    return "true";
                }
            }
        }
        return "false";
    }
}
