package com.wyh.online_judge.controller;

import com.wyh.online_judge.mapper.SubmissionMapper;
import com.wyh.online_judge.pojo.Problem;
import com.wyh.online_judge.pojo.Rank;
import com.wyh.online_judge.pojo.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class RankController {
    @Autowired
    SubmissionMapper submissionMapper;

    @RequestMapping("/rank")
    public String list(Model model){
        Collection<Rank> ranks = submissionMapper.getRank();
        model.addAttribute("ranks",ranks);
        return "rank/list";
    }
}
