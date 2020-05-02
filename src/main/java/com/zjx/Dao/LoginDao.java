package com.zjx.Dao;

import com.zjx.Entity.UserInfo;
import com.zjx.Entity.User_Token;

import java.util.List;

public interface LoginDao {
      public String FindUserById(String id);
      public int UpdateById(String id,int Userstaticname);
      public User_Token DetectToken(String Token);
      public int UpdateToken(User_Token user_token);
      public int UpdateExpireDate(String Token ,String ExpireDate);
}
