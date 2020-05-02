package com.zjx.Service;

import com.zjx.Entity.UserInfo;
import com.zjx.Entity.User_Token;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredService {
    public int SaveUserInfo(String U_LoginID,String U_NickName,String U_PassWord,String U_Email);
    public int SaveUserToken(User_Token user_token);
}
