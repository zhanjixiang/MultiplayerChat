package com.zjx.Service;

import org.springframework.stereotype.Repository;

@Repository
public interface ForgetPwdService {
    //检测邮箱
    public String CheckEmail(String userid);
    //修改密码
    public int UpdatePassword(String id,String password,String email);

}
