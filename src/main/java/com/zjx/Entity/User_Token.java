package com.zjx.Entity;

import lombok.Data;

@Data
public class User_Token {
    private String U_LoginID;
    private String Token;
    private String ExpireDate;
    private String FreeLogin;
}
