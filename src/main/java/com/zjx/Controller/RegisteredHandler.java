package com.zjx.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjx.API.IdGeneratorAPI;
import com.zjx.API.SendEmailAPI;
import com.zjx.API.TokenAndWebTimeAPI;
import com.zjx.Entity.User_Token;
import com.zjx.Service.RegisteredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/Registered")
public class RegisteredHandler {

    private String captcha;

    @Autowired
    private RegisteredService registeredService;

    @RequestMapping(value = "/captcha", params = {"email"})
    public void Captcha(String email){
        captcha = new SendEmailAPI().email(email);
        System.out.println("验证码："+captcha);
    }

    @RequestMapping(value = "/saveuserinfo" , params = {"uname","email","yzm","upwd"})
    public String SaveUserInfo(String uname, String email, String yzm, String upwd, User_Token user_token) throws IOException, ParseException {
        TokenAndWebTimeAPI tokenAndWebTimeAPI = new TokenAndWebTimeAPI();
        String ID = new IdGeneratorAPI().UseridIdGenerator();
        System.out.println("ID : "+ID+"  uname: "+uname+"  email: "+email+" yzm :"+yzm+" upwd : "+upwd);
        if(yzm.equals(captcha)){
            int result = registeredService.SaveUserInfo(ID,uname,upwd,email);
            while (result < 1 ){
                ID = new IdGeneratorAPI().UseridIdGenerator();
                result = registeredService.SaveUserInfo(ID,uname,upwd,email);
            }
            user_token.setU_LoginID(ID);
            user_token.setToken(tokenAndWebTimeAPI.TokenGenerator());
            user_token.setExpireDate(tokenAndWebTimeAPI.WebTime_ExpireTime());
            user_token.setFreeLogin("false");
            int token = registeredService.SaveUserToken(user_token);
            if (token > 0){
                return ID;
            }
        }
        return "false";
    }
}
