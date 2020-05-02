package com.zjx.Service;

import com.zjx.Entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomService {
    //   显示用户信息
    public UserInfo InquireUserInfo(String token);
    //   修改用户信息
    public int UpdateUserInfo(UserInfo userInfo);
    //   修改用户头像
    public int UpdateAvatar(String token,String avatar_address);
    //   修改登录状态
    public int UpdateStateName(String id,int userstaticname);
    //   获取朋友信息
    public List<Friends> GetFriendList(String token);
    //    获取好友账号
    public List<Friends> GetFriendID(String userid);
    //    查询群列表
    public List<GoupListInfo> GetGroupList(String UG_UserID);
    //   添加好友
    public int AddFriend(Friends friends);
    //   删除好友
    public int DeleteFriend(String userid,String friendid);
    //   加入群聊
    public int JoinGroupChat(User_GroupsToUser user_groupsToUser);
    //   离开群聊
    public int LeaveGroupChat(String userid,String groupid);
    //   新建群聊
    public int AddGroupChat(User_Groups user_groups);
    //   删除群聊
    public int DeleteGroupChat(String U_Gid);
    //   好友消息保存
    public int FriendMsgSave(Messages messages);
    //   好友消息查询
    public List<Messages> InquireFriendMessage(String M_FromUserID,String M_ToUserID);
    //   好友消息删除
    public int FriendMsgDelete(String M_FromUserID,String M_ToUserID,String M_Time);
    //   群聊消息保存
    public int GroupMsgSave(User_GroupsMSGContent user_groupsMSGContent);
    //   群消息查询
    public List<User_GroupsMSGContent> InquireGroupMessage(String GM_ID,String GM_FromID);
    //   群聊消息删除
    public int GroupMsgDelete(String GM_ID,String GM_FromID,String GM_CreateTime);
    //  群里的用户名
    public List<String> InquireGroupUserId(String groupid);
}
