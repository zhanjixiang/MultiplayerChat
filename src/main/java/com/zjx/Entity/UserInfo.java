package com.zjx.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private String U_LoginID;
    private String U_NickName;
    private String U_PassWord;
    private String U_SignaTure;
    private String U_Sex;
    private String U_Telephone;
    private String U_Name;
    private String U_Email;
    private int U_Age;
    private String U_Avatar;
    private String U_SchoolTag;
    private int U_UserStateName;
}
