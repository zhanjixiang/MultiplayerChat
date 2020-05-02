package com.zjx.Dao;

import com.zjx.Entity.User_Token;

public interface RegisteredDao {
    public int SaveUserInfo(String U_LoginID,String U_NickName,String U_PassWord,String U_Email);
    public int SaveUserToken(User_Token user_token);
}
