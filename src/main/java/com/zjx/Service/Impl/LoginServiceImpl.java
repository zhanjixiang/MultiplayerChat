package com.zjx.Service.Impl;

import com.zjx.Dao.LoginDao;
import com.zjx.Entity.UserInfo;
import com.zjx.Entity.User_Token;
import com.zjx.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;

    @Override
    public String FindUserById(String id) {
        return loginDao.FindUserById(id);
    }

    @Override
    public int UpdateById(String id,int userstaticname) {
        return loginDao.UpdateById(id,userstaticname);
    }

    @Override
    public User_Token DetectToken(String Token) {
        return loginDao.DetectToken(Token);
    }

    @Override
    public int UpdateToken(User_Token user_token) {
        return loginDao.UpdateToken(user_token);
    }

    @Override
    public int UpdateExpireDate(String Token, String ExpireDate) {
        return loginDao.UpdateExpireDate(Token,ExpireDate);
    }
}
