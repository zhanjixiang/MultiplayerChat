package com.zjx.Service.Impl;

import com.zjx.Dao.ForgetPasswordDao;
import com.zjx.Service.ForgetPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgetPwdServiceImpl implements ForgetPwdService {

    @Autowired
    private ForgetPasswordDao forgetPasswordDao;

    @Override
    public String CheckEmail(String userid) {
        return forgetPasswordDao.CheckEmail(userid);
    }

    @Override
    public int UpdatePassword(String id, String password,String email) {
        return forgetPasswordDao.UpdatePassword(id,password,email);
    }
}
