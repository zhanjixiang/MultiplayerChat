package com.zjx.Service.Impl;

import com.zjx.Dao.ChatRoomDao;
import com.zjx.Entity.*;
import com.zjx.Service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomDao chatRoomDao;

    @Override
    public UserInfo InquireUserInfo(String token) {
        return chatRoomDao.InquireUserInfo(token);
    }

    @Override
    public int UpdateUserInfo(UserInfo userInfo) {
        return chatRoomDao.UpdateUserInfo(userInfo);
    }

    @Override
    public int UpdateAvatar(String token, String avatar_address) {
        return chatRoomDao.UpdateAvatar(token,avatar_address);
    }

    @Override
    public int UpdateStateName(String id, int userstaticname) {
        return chatRoomDao.UpdateStateName(id, userstaticname);
    }

    @Override
    public List<Friends> GetFriendList(String token) {
        return chatRoomDao.GetFriendList(token);
    }

    @Override
    public List<Friends> GetFriendID(String userid) {
        return chatRoomDao.GetFriendID(userid);
    }

    @Override
    public List<GoupListInfo> GetGroupList(String UG_UserID) {
        return chatRoomDao.GetGroupList(UG_UserID);
    }

    @Override
    public int AddFriend(Friends friends) {
        return chatRoomDao.AddFriend(friends);
    }

    @Override
    public int DeleteFriend(String userid, String friendid) {
        return chatRoomDao.DeleteFriend(userid, friendid);
    }

    @Override
    public int JoinGroupChat(User_GroupsToUser user_groupsToUser) {
        return chatRoomDao.JoinGroupChat(user_groupsToUser);
    }

    @Override
    public int LeaveGroupChat(String userid, String groupid) {
        return chatRoomDao.LeaveGroupChat(userid, groupid);
    }

    @Override
    public int AddGroupChat(User_Groups user_groups) {
        return chatRoomDao.AddGroupChat(user_groups);
    }

    @Override
    public int DeleteGroupChat(String U_Gid) {
        return chatRoomDao.DeleteGroupChat(U_Gid);
    }

    @Override
    public int FriendMsgSave(Messages messages) {
        return chatRoomDao.FriendMsgSave(messages);
    }

    @Override
    public List<Messages> InquireFriendMessage(String M_FromUserID, String M_ToUserID) {
        return chatRoomDao.InquireFriendMessage(M_FromUserID, M_ToUserID);
    }

    @Override
    public int FriendMsgDelete(String M_FromUserID, String M_ToUserID, String M_Time) {
        return chatRoomDao.FriendMsgDelete(M_FromUserID, M_ToUserID, M_Time);
    }

    @Override
    public int GroupMsgSave(User_GroupsMSGContent user_groupsMSGContent) {
        return chatRoomDao.GroupMsgSave(user_groupsMSGContent);
    }

    @Override
    public List<User_GroupsMSGContent> InquireGroupMessage(String GM_ID, String GM_FromID) {
        return chatRoomDao.InquireGroupMessage(GM_ID, GM_FromID);
    }

    @Override
    public int GroupMsgDelete(String GM_ID, String GM_FromID, String GM_CreateTime) {
        return chatRoomDao.GroupMsgDelete(GM_ID, GM_FromID, GM_CreateTime);
    }

    @Override
    public List<String> InquireGroupUserId(String groupid) {
        return chatRoomDao.InquireGroupUserId(groupid);
    }

}
