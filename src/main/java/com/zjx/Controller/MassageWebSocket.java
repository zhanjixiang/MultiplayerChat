package com.zjx.Controller;

import com.zjx.API.TokenAndWebTimeAPI;
import com.zjx.Entity.Friends;
import com.zjx.Entity.Messages;
import com.zjx.Entity.User_GroupsMSGContent;
import com.zjx.Service.ChatRoomService;
import com.zjx.Service.Impl.ChatRoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/webchatroom/{userId}")
public class MassageWebSocket {
    private Session session;
    private String userName;              //账号；

    private static ChatRoomService chatRoomService ;

    private static volatile int onlineCount = 0;

    private static ConcurrentHashMap<String,MassageWebSocket> webSocketMap = new ConcurrentHashMap<String, MassageWebSocket>();

    @Autowired
    public void setChatService(ChatRoomService chatRoomService) {   //在webSocket中使用ssm框架中的service类
        this.chatRoomService = chatRoomService;
    }

    @OnOpen
    public void onopen(Session session, @PathParam(value = "userId") String userName){
        this.session = session;
        addOnlineCount();
        System.out.println("连接数："+getOnlineCount());
        this.userName = userName;
        System.out.println("this.username:"+this.userName);
        webSocketMap.put(this.userName,this);
        System.out.println(webSocketMap.keySet());
        chatRoomService.UpdateStateName(userName,1);
        List<Friends> frienglist = chatRoomService.GetFriendID(this.userName);
        for(Friends temp : frienglist){
//            System.out.println("tempid: "+temp.getF_FriendID()+"   tempname : "+temp.getF_Name()+"   tempgn: "+temp.getF_FriendGroupsName());
            if(webSocketMap.containsKey(temp.getF_FriendID())){
                webSocketMap.get(temp.getF_FriendID()).sendMessage("Service Message :"+"好友:"+temp+"已上线！");
            }
        }

    }
    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);
        String Time = null;
        try {
            Time = new TokenAndWebTimeAPI().WebTime_NowDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(message.indexOf("PointPersonal:") == 0) {
            String friendmsg[] = message.split(":",3);
            String friendid = null,msg = null;
            int i = 0;
            for(String temp : friendmsg){
                i++;
                if (i == 2 ) {
                    friendid = temp;
                }else if (i == 3){
                    msg = temp;
                }
            }
            PointToSend(friendid,msg,Time);
        }else if(message.indexOf("PointGroup:") == 0){
            String groupmsg[] = message.split(":",4);
            String groupid = null,usernick = null,msg = null;
            int i = 0;
            for(String temp : groupmsg){
                if (i == 1){
                    groupid = temp;
                }else if( i == 2){
                    usernick = temp;
                }else if(i == 3){
                    msg = temp;
                }
                i++;
            }
            GroupToSend(groupid,usernick,msg,Time);;
        }else if(message.indexOf("PointFile:") == 0){
            String friendfile[] = message.split(":",4);
            String friendid = null,fromuserid = null,msg = null;
            int i = 0;
            for (String temp : friendfile){
                if ( i == 1){
                    friendid = temp;
                }else if( i == 2){
                    fromuserid = temp;
                }else if( i == 3){
                    msg = temp;
                }
                i++;
            }
            if (webSocketMap.containsKey(friendid)) {
                webSocketMap.get(friendid).sendMessage(Time + "</br>"+ msg);
                webSocketMap.get(fromuserid).sendMessage(fromuserid+ Time + "</br>" + msg);
            }else{
                webSocketMap.get(fromuserid).sendMessage("Service Message :"+"好友已离线，消息已转为留言！");
            }
        }else if(message.indexOf("GroupFile:") == 0){
            String groupmsg[] = message.split(":",5);
            String groupid = null,usernick = null,fromuserid=null,msg = null;
            int i = 0 ;
            for(String temp : groupmsg){
                if ( i == 1){
                    groupid = temp;
                }else if(i == 2){
                    usernick = temp;
                }else if(i == 3) {
                    fromuserid = temp;
                }else if(i == 4){
                    msg = temp;
                }
                i++;
            }
            List<String> keys = chatRoomService.InquireGroupUserId(groupid);
            for (String key : keys) {
                if (!key.equals(fromuserid)) {
                    if(webSocketMap.containsKey(key)){
                        webSocketMap.get(key).sendMessage(Time + "</br>"+usernick+ "</br>" + msg);
                    }
                }else {
                    webSocketMap.get(fromuserid).sendMessage(fromuserid+ Time + "</br>"+usernick+ "</br>" + msg);
                }
            }
        }else {
            this.sendMessage("Service Message :"+"消息发送出错");
        }
//        this.sendMessage(message);
//        GroupToSend(message);

    }

//    指向性发送
    public void PointToSend(String friendid, String message,String Time)  {
        Messages messages = new Messages();
        messages.setM_FromUserID(userName);
        messages.setM_ToUserID(friendid);
        messages.setM_PostMessages(message);
        messages.setM_Time(Time);
        int save = chatRoomService.FriendMsgSave(messages);
        if (webSocketMap.containsKey(friendid)) {
            System.out.println(friendid);
//            chatRoomService.UpdateStateName(friendid,1);
            System.out.println(webSocketMap.keySet() + "  friendid:" + friendid + "   uesrid:" + userName);
            webSocketMap.get(friendid).sendMessage(Time + "</br>" + message);
            this.sendMessage(userName+Time + "</br>" + message);

        } else {
            int updatestatename = chatRoomService.UpdateStateName(friendid,0);
            this.sendMessage("Service Message :"+"好友已离线，消息已转为留言！");
        }
    }

//    群体性发送
    public void GroupToSend(String groupid,String usernick, String message,String Time){
        User_GroupsMSGContent groupsMSGContent = new User_GroupsMSGContent();
        groupsMSGContent.setGM_ID(groupid);
        groupsMSGContent.setGM_FromUName(usernick);
        groupsMSGContent.setGM_Content(message);
        groupsMSGContent.setGM_FromID(userName);
        groupsMSGContent.setGM_CreateTime(Time);
        int insert = chatRoomService.GroupMsgSave(groupsMSGContent);
        List<String> keys = chatRoomService.InquireGroupUserId(groupid);
        for (String key : keys) {
            if (!key.equals(this.userName)) {
                if(webSocketMap.containsKey(key)){
                    webSocketMap.get(key).sendMessage(Time + "</br>"+usernick+ "</br>" + message);
                }
            }else{
                this.sendMessage(userName+ Time + "</br>"+usernick+ "</br>" + message);
            }
        }

    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        this.session.getAsyncRemote().sendText(message);
    }

    @OnError
    public void onerror(Session session, Throwable throwable){
        chatRoomService.UpdateStateName(userName,0);
        System.out.println("出错");
        throwable.printStackTrace();
    }
    @OnClose
    public void close(){
        chatRoomService.UpdateStateName(userName,0);
        webSocketMap.remove(userName);  //从map中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        MassageWebSocket.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        MassageWebSocket.onlineCount--;
    }
}
