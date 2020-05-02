package com.zjx.Dao;

public interface ForgetPasswordDao {
    //检测邮箱
    public String CheckEmail(String userid);
    //修改密码
    public int UpdatePassword(String id,String password,String email);
}
