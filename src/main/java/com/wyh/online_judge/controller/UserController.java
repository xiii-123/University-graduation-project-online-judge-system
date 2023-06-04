package com.wyh.online_judge.controller;

import com.wyh.online_judge.mapper.Contest_userMapper;
import com.wyh.online_judge.mapper.SubmissionMapper;
import com.wyh.online_judge.mapper.UserMapper;
import com.wyh.online_judge.pojo.Problem;
import com.wyh.online_judge.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class UserController {
    //自动注入UserMapper
    @Autowired
    private UserMapper userMapper;
    @Autowired
    SubmissionMapper submissionMapper;
    @Autowired
    Contest_userMapper contest_userMapper;
    //跳转到表单并且传入数据
    @RequestMapping("/users")
    public String list(Model model){
        Collection<User> users = userMapper.queryUserList();
        model.addAttribute("users",users);
        return "user/list";
    }


    //跳转到添加用户页面
    @RequestMapping("/toUserAddPage")
    public String toAddPage(){
        return "user/add";
    }
    //添加用户
    @PostMapping("/addUser")
    public String addUser(User user){
        //添加操作
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userMapper.addUser(user);
        return "redirect:/users";
    }

    //去用户修改页面
    @GetMapping("/toUserUpdatePage/{id}")
    public String toUpdatePage(@PathVariable("id") Integer id, Model model){
        //查出原来的数据
        User user = userMapper.queryUserById(id);
        model.addAttribute("user",user);

        return "user/update";
    }
    //修改信息
    @PostMapping("/updateUser")
    public String updateUser(User user){
        //添加操作
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userMapper.updateUser(user);
        return "redirect:/users";
    }
    //删除用户
    @GetMapping("/deleUser/{id}")
    public String deleUser(@PathVariable("id") Integer id){
        submissionMapper.deleteSubmissionByUserId(id);
        contest_userMapper.deleteContest_userByUserId(id);
        userMapper.deleteUser(id);
        return "redirect:/users";
    }
    @PostMapping("queryUsersByKeyword")
    public String queryProblemsByKeyword(String keyword, Model model){
        List<User> users = userMapper.queryUsersByKeyword(keyword);
        model.addAttribute("users",users);
        return "user/list";
    }
}
