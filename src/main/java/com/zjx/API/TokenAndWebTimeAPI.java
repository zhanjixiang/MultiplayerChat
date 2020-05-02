package com.zjx.API;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/*
 *生成Token的值
 * 生成当前时间
 * 生成过期时间（3天过期）
 * 判断是否过期
 */
public class TokenAndWebTimeAPI {
    /*
     *生成Token值
     */
    public String TokenGenerator(){
        Random random = new Random();
        StringBuffer token = new StringBuffer();
        for(int i = 0; i < 15 ; i++){
            boolean TF = random.nextBoolean();
            //char
            if(TF){
                int suiji = random.nextInt(57)%58+65;    //字母验证码
                if(suiji >90 && suiji <97){
                    i--;
                }else{
                    char temp = (char) suiji;
                    token.append(temp+" ");
                }

            }else {
                int suiji = random.nextInt(9);     //数字验证码
                token.append(suiji+" ");
            }
        }
        return token.toString().replaceAll(" ","");
    }
    /*
     *输出当前 long 类型 的时间
     */
    private long nowtime() throws IOException {
        URL url = new URL("http://www.baidu.com");
        URLConnection urlConnection = url.openConnection();
        long datetime = urlConnection.getDate();
        return datetime;
    }
    /*
     *输出当前时间
     */
    public String WebTime_NowDate() throws IOException {
        Date date = new Date(nowtime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
    /*
     *输出过期时间
     */
    public String WebTime_ExpireTime() throws ParseException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date ExpireDate = new Date(nowtime()+(1000*60*60*24*3));
        return simpleDateFormat.format(ExpireDate);
    }
    /*
    *判断是否过期
    */
    public boolean isAfterTime(String ExpireDate) throws ParseException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Expiretime = simpleDateFormat.parse(ExpireDate);
        Date NowDate = new Date(nowtime());
        if(Expiretime.after(NowDate)) {
            return true;
        }
        return false;
    }
}
