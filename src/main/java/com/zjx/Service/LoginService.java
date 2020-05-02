package com.zjx.Service;

import com.zjx.Entity.UserInfo;
import com.zjx.Entity.User_Token;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginService {
    public String FindUserById(String id);
    public int UpdateById(String id,int userstaticname);
    public User_Token DetectToken(String Token);
    public int UpdateToken(User_Token user_token);
    public int UpdateExpireDate(String Token ,String ExpireDate);
}
