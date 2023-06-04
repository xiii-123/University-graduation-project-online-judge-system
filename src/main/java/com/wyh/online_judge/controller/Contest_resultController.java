package com.wyh.online_judge.controller;

import com.wyh.online_judge.mapper.Contest_userMapper;
import com.wyh.online_judge.pojo.Contest_result;
import com.wyh.online_judge.pojo.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Controller
public class Contest_resultController {
    @Autowired
    Contest_userMapper contest_userMapper;

    @RequestMapping("/contest_result")
    public String list(Model model, HttpSession session){
        int userId = Integer.parseInt(session.getAttribute("userId").toString());
        int userRole = Integer.parseInt(session.getAttribute("userRole").toString());
        List<Contest_result> contest_results;
        if (userRole==1) {
            contest_results = contest_userMapper.queryContest_userByUserId(userId);
        }else{
            contest_results = contest_userMapper.queryContest_userList();
        }
        model.addAttribute("contest_results",contest_results);
        return "contest_result/list";
    }

    @RequestMapping("queryContest_resultByKeyword")
    public String queryContest_resultByKeyword(String keyword ,Model model, HttpSession session){
        int userId = Integer.parseInt(session.getAttribute("userId").toString());
        int userRole = Integer.parseInt(session.getAttribute("userRole").toString());
        List<Contest_result> contest_results;
        if (userRole==1) {
            contest_results = contest_userMapper.queryContest_userByUserIdAndKeywords(userId,keyword);
        }else{
            contest_results = contest_userMapper.queryContest_userKeywords(keyword);
        }
        model.addAttribute("contest_results",contest_results);
        return "contest_result/list";
    }
}
