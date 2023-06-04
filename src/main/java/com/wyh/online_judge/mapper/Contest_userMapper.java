package com.wyh.online_judge.mapper;

import com.wyh.online_judge.pojo.Contest_result;
import com.wyh.online_judge.pojo.Contest_user;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Resource
public interface Contest_userMapper {
    //查询所有记录
    List<Contest_result> queryContest_userList();
    //根据用户ID和关键字查询记录
    List<Contest_result> queryContest_userByUserIdAndKeywords(Integer user_id, String keywords);
    //根据关键字查询记录
    List<Contest_result> queryContest_userKeywords(String keywords);
    //根据用户ID
    List<Contest_result> queryContest_userByUserId(Integer user_id);
    //增加记录
    int addContest_user(Contest_user contest_user);
    //根据用户id删除竞赛记录
    int deleteContest_userByUserId(int user_id);
}
