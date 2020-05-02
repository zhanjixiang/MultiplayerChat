package com.zjx.API;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Address {
    private String address = null;
    //获取本机IP地址
    private String getIpaddress(){
        String ip = null;
        try {
            InetAddress net = InetAddress.getLocalHost();
            ip = net.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    //获取上传头像文件地址
    public String getAvatarAddress(String avatarname){
        address = "http://"+getIpaddress()+":8080/MultiplayerChat/AvatarFile/"+avatarname;
        return address;
    }

    //获取上传文件地址
    public String getFileAddress(String filename){
        address = "http://"+getIpaddress()+":8080/MultiplayerChat/File/"+filename;
        return address;
    }

}
