package com.wyh.online_judge.controller;

import com.wyh.online_judge.mapper.SubmissionMapper;
import com.wyh.online_judge.pojo.Rank;
import com.wyh.online_judge.pojo.Submission_problem;
import com.wyh.online_judge.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;

@Controller
public class SubmissionController {
    @Autowired
    SubmissionMapper submissionMapper;

    @RequestMapping("/submissions")
    public String list(Model model, HttpSession session){
        int user_id = Integer.parseInt(session.getAttribute("userId").toString());
        Collection<Submission_problem> submissions = submissionMapper.querySubmissionByUserId(user_id);
        model.addAttribute("submissions",submissions);
        return "submission/list";
    }
    @PostMapping("querySubmissionByUserIdAndKeyword")
    public String queryProblemsByKeyword(String keyword, Model model, HttpSession session){
        int user_id = Integer.parseInt(session.getAttribute("userId").toString());
        List<Submission_problem> submissions = submissionMapper.querySubmissionByUserIdAndKeyword(user_id,keyword);
        model.addAttribute("submissions",submissions);
        return "submission/list";
    }
}
