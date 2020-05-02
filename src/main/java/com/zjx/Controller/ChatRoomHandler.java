package com.zjx.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjx.API.Address;
import com.zjx.API.IdGeneratorAPI;
import com.zjx.API.TokenAndWebTimeAPI;
import com.zjx.Entity.*;
import com.zjx.Service.ChatRoomService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/chatroom")
public class ChatRoomHandler {
    @Autowired
    private ChatRoomService chatRoomService;
//  通过 token 返回 userinfo 的值
    @RequestMapping(value = "/inquireuserinfo",params = {"token"})
    public JSON InquireUserInfo(String token, UserInfo userInfo) {
        userInfo = chatRoomService.InquireUserInfo(token);
        userInfo.setU_PassWord(null);
        JSON json = (JSON) JSON.toJSON(userInfo);
        System.out.println("json : " + json);
        return json;
    }
//  修改 user info 的值
    @RequestMapping(value = "/updateuserinfo" ,params = {"username","uname","sex","phone","realname","email","age","school","signature"})
    public String UpdateUserInfo(String username,String uname,String sex,String phone,String realname,String email,String age,String school,String signature,UserInfo userInfo){
        userInfo.setU_LoginID(username);
        userInfo.setU_NickName(uname);
        if(sex.equals("man")){
            userInfo.setU_Sex("男");
        }else if(sex.equals("female")){
            userInfo.setU_Sex("女");
        }
        userInfo.setU_Telephone(phone);
        userInfo.setU_Name(realname);
        userInfo.setU_Email(email);
        userInfo.setU_Age(Integer.parseInt(age));
        userInfo.setU_SchoolTag(school);
        userInfo.setU_SignaTure(signature);
        if(username.equals("") || username == null){
            return "false";
        }else{
            int update = chatRoomService.UpdateUserInfo(userInfo);
            System.out.println("update :: "+ update);
            if (update > 0){
                return "true";
            }
        }
        return "false";
    }
//  修改 头像的 路径
    @PostMapping(value = "/avatarimgupload")
    public String AvatarImgUpload(MultipartFile file , HttpServletRequest request, @RequestParam(value = "token")String token,Address address){
        String path = request.getServletContext().getRealPath("AvatarFile");
        if (token == null || token.equals("")){
            return "false";
        }else{
            if (file.getSize() > 0) {
                String filename = file.getOriginalFilename();
                File fileimg = new File(path, filename);
                try {
                    file.transferTo(fileimg);
                    int update = chatRoomService.UpdateAvatar(token,address.getAvatarAddress(filename));
                    if (update > 0){
                        return "true";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "false";
    }
//  修改登录状态
    @RequestMapping(value = "/updatestatename", params = {"userid"})
    public void UpdateStateName(String userid){
        int update = chatRoomService.UpdateStateName(userid, 0);
    }
//  获取 friend 的信息
    @RequestMapping(value = "/friendlist",params = {"token"})
    public JSON FriendList(String token){
        List<Friends> friends = chatRoomService.GetFriendList(token);

        JSON json = (JSON) JSON.toJSON(friends);
        System.out.println(json);
        return json;
    }
//  查询群列表
    @RequestMapping(value = "/getgrouplist",params = {"userid"})
    public JSON GetGroupList(String userid){
        List<GoupListInfo> goupListInfos  = chatRoomService.GetGroupList(userid);
        JSON json = (JSON) JSON.toJSON(goupListInfos);
        System.out.println("json"+json);
        return json;
    }
//  添加好友
    @RequestMapping(value = "/addfriend" ,params = {"userid","friendid","f_name","friendgroupname"})
    public String AddFriend(String userid,String friendid,String f_name,String friendgroupname,Friends friends){
        friends.setF_UserID(userid);
        friends.setF_FriendID(friendid);
        friends.setF_Name(f_name);
        friends.setF_FriendGroupsName(friendgroupname);
        int insert = chatRoomService.AddFriend(friends);
        if(insert >0){
            return "true";
        }
        return "false";
    }
//  删除好友
    @RequestMapping(value = "/deletefriend" ,params = {"userid","friendid"})
    public String DeleteFriend(String userid,String friendid){
        int delete = chatRoomService.DeleteFriend(userid, friendid);
        if(delete >0){
            return "true";
        }
        return "false";
    }
//  加入群聊
    @RequestMapping(value = "/joingroupchat" ,params = {"userid","groupid","groupnick"})
    public String JoinGroupChat(String userid, String groupid, String groupnick, User_GroupsToUser user_groupsToUser) throws IOException {
        String createtime = new TokenAndWebTimeAPI().WebTime_NowDate();
        user_groupsToUser.setUG_UserID(userid);
        user_groupsToUser.setUG_GroupID(groupid);
        user_groupsToUser.setUG_GroupNick(groupnick);
        user_groupsToUser.setUG_CreateTime(createtime);
        int insert = chatRoomService.JoinGroupChat(user_groupsToUser);
        if(insert >0){
            return "true";
        }
        return "false";
    }
//   离开群聊
    @RequestMapping(value = "/leavegroupchat" ,params = {"userid","groupid"})
    public String LeaveGroupChat(String userid,String groupid){
        int delete = chatRoomService.LeaveGroupChat(userid, groupid);
        if(delete >0){
            return "true";
        }
        return "false";
    }
//  新建群聊
    @RequestMapping(value = "/addgroupchat" ,params = {"groupname","userid","notice","intro"})
    public String AddGroupChat(String groupname, String userid, String notice, String intro, User_Groups user_groups) throws IOException {
        user_groups.setUG_ID(new IdGeneratorAPI().GroupIdGenerator());
        user_groups.setUG_Name(groupname);
        user_groups.setUG_AdminID(userid);
        user_groups.setUG_Notice(notice);
        user_groups.setUG_Intro(intro);
        user_groups.setUG_CreateTime(new TokenAndWebTimeAPI().WebTime_NowDate());
        int insert = chatRoomService.AddGroupChat(user_groups);
        if(insert >0){
            return "true";
        }
        return "false";
    }
//  删除群聊
    @RequestMapping(value = "/deletegroupchat" ,params = {"groupid","userid"})
    public String DeleteGroupChat(String groupid,String userid){
        
        int delete = chatRoomService.DeleteGroupChat(groupid);
        if(delete >0){
            return "true";
        }
        return "false";
    }
//  好友消息保存
    @RequestMapping(value = "/friendmsgsave")
    public String FriendMsgSave( @RequestParam(value = "fromuserid")String fromuserid,@RequestParam(value = "touserid") String touserid,Messages messages,MultipartFile file , HttpServletRequest request,Address address) throws IOException {
        String msgtime = new TokenAndWebTimeAPI().WebTime_NowDate();
        MassageWebSocket socket = new MassageWebSocket();
        String path = request.getServletContext().getRealPath("File");
        String filename = null;
        if (file.getSize() > 0) {
            filename = fromuserid+file.getOriginalFilename();
            File fileimg = new File(path, filename);
            if(fileimg.exists()){
                fileimg.delete();
            }
            try {
                file.transferTo(fileimg);
                String msg = "PointFile:" + touserid +":"+fromuserid+":"+ address.getFileAddress(filename);
                socket.onMessage(msg);
                messages.setM_FromUserID(fromuserid);
                messages.setM_ToUserID(touserid);
                messages.setM_PostMessages(address.getFileAddress(filename));
                messages.setM_Time(msgtime);
                int insert = chatRoomService.FriendMsgSave(messages);
                if(insert >0){
                    return "true";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "false";
    }
//  好友消息查询
    @RequestMapping(value = "/inquirefriendmessage" ,params = {"fromuserid","touserid"})
    public JSON InquireFriendMessage(String fromuserid,String touserid){
        List<Messages> list = chatRoomService.InquireFriendMessage(fromuserid, touserid);
        JSON json = (JSON) JSON.toJSON(list);
        System.out.println("FriendMessage json:"+json);
        return json;
    }
//  好友消息删除
    @RequestMapping(value = "/friendmsgdelete" ,params = {"fromuserid","touserid","msgtime"})
    public String FriendMsgDelete(String fromuserid,String touserid,String msgtime){
        int delete = chatRoomService.FriendMsgDelete(fromuserid, touserid,msgtime);
        if(delete >0){
            return "true";
        }
        return "false";
    }
//  群聊消息保存
    @RequestMapping(value = "/groupmsgsave")
    public String GroupMsgSave(@RequestParam("groupid")String groupid,@RequestParam("fromuserid")String fromuserid, @RequestParam("fromname")String fromname,User_GroupsMSGContent msgContent,MultipartFile file , HttpServletRequest request,Address address) throws IOException {
        String msgtime = new TokenAndWebTimeAPI().WebTime_NowDate();
        MassageWebSocket socket = new MassageWebSocket();
        String path = request.getServletContext().getRealPath("File");
        String filename = null;
        if (file.getSize() > 0) {
            filename = fromuserid+file.getOriginalFilename();
            File fileimg = new File(path, filename);
            if(fileimg.exists()){
                fileimg.delete();
            }
            try {
                file.transferTo(fileimg);
                String msg = "GroupFile:" + groupid +":"+ fromname+":"+fromuserid+":"+address.getFileAddress(filename);
                socket.onMessage(msg);
                msgContent.setGM_ID(groupid);
                msgContent.setGM_Content(address.getFileAddress(filename));
                msgContent.setGM_FromID(fromuserid);
                msgContent.setGM_FromUName(fromname);
                msgContent.setGM_CreateTime(msgtime);
                int insert = chatRoomService.GroupMsgSave(msgContent);
                if(insert >0){
                    return "true";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "false";
    }
//  群消息查询
    @RequestMapping(value = "/inquiregroupmessage" ,params = {"groupid","fromuserid"})
    public JSON InquireGroupMessage(String groupid,String fromuserid){
        List<User_GroupsMSGContent> list = chatRoomService.InquireGroupMessage(groupid, fromuserid);
        JSON json = (JSON) JSON.toJSON(list);
        System.out.println(json);
        return json;
    }
//  群聊消息删除
    @RequestMapping(value = "/groupmsgdelete" ,params = {"groupid","fromuserid","msgtime"})
    public String GroupMsgDelete(String groupid,String fromuserid,String msgtime){
        int delete = chatRoomService.FriendMsgDelete(groupid, fromuserid,msgtime);
        if(delete >0){
            return "true";
        }
        return "false";
    }
//  文件下载
    @GetMapping(value = "/downloadfile/{filename}")
    public void DownloadFile(@PathVariable(value = "filename") String filename, HttpServletRequest request, HttpServletResponse response){
        if(filename != null){
            String path = request.getServletContext().getRealPath("File");
            File file = new File(path,filename);
            if (file.exists()){
                response.setContentType("application/forc-download");
                response.setHeader("Content-Disposition","attachment;filename"+filename);
                OutputStream outputStream = null;
                try {
                    outputStream = response.getOutputStream();
                    outputStream.write(FileUtils.readFileToByteArray(file));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }







}
