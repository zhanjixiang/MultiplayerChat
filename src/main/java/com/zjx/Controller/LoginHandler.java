package com.zjx.Controller;

import com.zjx.API.TokenAndWebTimeAPI;
import com.zjx.Entity.UserInfo;
import com.zjx.Entity.User_Token;
import com.zjx.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class LoginHandler {
    @Autowired
    private LoginService loginService;

    //登录验证
    @RequestMapping(value = "LoginFindById",params = {"username","userpwd"})
    public String LoginFindById(String username, String userpwd, @RequestParam(value = "freelogin",defaultValue = "false")String freelogin, UserInfo userInfo, User_Token user_token) throws IOException, ParseException {
        TokenAndWebTimeAPI tokenAndWebTimeAPI = new TokenAndWebTimeAPI();
        user_token.setU_LoginID(username);
        user_token.setToken(tokenAndWebTimeAPI.TokenGenerator());
        user_token.setExpireDate(tokenAndWebTimeAPI.WebTime_ExpireTime());
        user_token.setFreeLogin(freelogin);
        if(loginService.FindUserById(username).equals(userpwd)){
            int update = loginService.UpdateToken(user_token);
            if(update > 0){
                return "true";
            }
        }
        return "false";
    }

    //免登录验证
    @RequestMapping(value = "detecttoken",params = {"token"})
    public String Detecttoken(String token,User_Token user_token,TokenAndWebTimeAPI tokenAndWebTimeAPI) throws IOException, ParseException {
        user_token = loginService.DetectToken(token);
        if(tokenAndWebTimeAPI.isAfterTime(user_token.getExpireDate())){
            int update = loginService.UpdateExpireDate(token,tokenAndWebTimeAPI.WebTime_ExpireTime());
            if (user_token.getFreeLogin().equals("true") && update > 0){
                return "true";
            }
        }
        return "false";
    }
}
