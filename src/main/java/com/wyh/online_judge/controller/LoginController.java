package com.wyh.online_judge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyh.online_judge.mapper.SubmissionMapper;
import com.wyh.online_judge.mapper.UserMapper;
import com.wyh.online_judge.pojo.Rank;
import com.wyh.online_judge.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    SubmissionMapper submissionMapper;

    @RequestMapping("/user/toRegister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("user/userExist")
    public String userExist(@RequestParam("username")String username) throws JsonProcessingException {
        int result;
        if (userMapper.queryUserByUserName(username)==null)
            result=0;
        else
            result=1;
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = new HashMap<>();
        map.put("result",result+"");
        System.out.println(result);
        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/user/register")
    public String register(@RequestParam("username")String username, @RequestParam("password") String password, @RequestParam("email") String email, Model model){
        User user = userMapper.queryUserByUserName(username);
        if (user == null){
            userMapper.addUser(new User(username,DigestUtils.md5Hex(password),email,1));
            return "redirect:index.html";
        }else{
            model.addAttribute("msg","用户名已存在，请更改用户名");
            return "register";
        }
    }

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session){
        User user = userMapper.queryUserByUserName(username);
        if (user != null && user.getPassword().equals(DigestUtils.md5Hex(password))){
            session.setAttribute("loginUser",username);
            session.setAttribute("userId",user.getId());
            session.setAttribute("userRole",user.getRole());
            Collection<Rank> ranks = submissionMapper.getRank();
            model.addAttribute("ranks",ranks);
            return "rank/list";
        }
        else{
            model.addAttribute("msg","用户名或密码错误");
            return "index";
        }
    }
    @RequestMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index.html";
    }
}
