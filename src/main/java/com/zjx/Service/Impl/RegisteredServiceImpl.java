package com.zjx.Service.Impl;

import com.zjx.Dao.RegisteredDao;
import com.zjx.Entity.User_Token;
import com.zjx.Service.RegisteredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisteredServiceImpl implements RegisteredService {
    @Autowired
    private RegisteredDao registeredDao;

    @Override
    public int SaveUserInfo(String U_LoginID, String U_NickName, String U_PassWord, String U_Email) {
        return registeredDao.SaveUserInfo(U_LoginID,U_NickName,U_PassWord,U_Email);
    }

    @Override
    public int SaveUserToken(User_Token user_token) {
        return registeredDao.SaveUserToken(user_token);
    }
}
