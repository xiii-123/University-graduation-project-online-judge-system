package com.wyh.online_judge.mapper;

import com.wyh.online_judge.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    //查询所有用户
    List<User> queryUserList();
    //根据ID查询用户
    User queryUserById(int id);
    //根据用户名查询用户
    User queryUserByUserName(String username);
    //根据关键字模糊查询用户
    List<User> queryUsersByKeyword(String keyword);
    //增加用户
    int addUser(User user);
    //更新用户信息
    int updateUser(User user);
    //删除用户
    int deleteUser(int id);


}
