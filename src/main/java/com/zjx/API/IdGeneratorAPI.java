package com.zjx.API;

import org.junit.Test;

import java.util.Random;

public class IdGeneratorAPI {
    private Random random;
    private StringBuffer stringBuffer;

    // 用户 id 生成
    public String UseridIdGenerator(){
        random = new Random();
        stringBuffer = new StringBuffer();
        for (int i = 0 ;i<8;i++){
            if (i == 0){
                stringBuffer.append(random.nextInt(9)%9+1);
            }else{
                stringBuffer.append(random.nextInt(9));
            }
        }
        return stringBuffer.toString();
    }
    // 群 id 生成
    public String GroupIdGenerator(){
        random = new Random();
        stringBuffer = new StringBuffer();
        for (int i = 0 ;i<9;i++){
            if (i == 0){
                stringBuffer.append(random.nextInt(9)%9+1);
            }else{
                stringBuffer.append(random.nextInt(9));
            }
        }
        return stringBuffer.toString();
    }

}
