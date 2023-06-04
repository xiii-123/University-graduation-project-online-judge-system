package com.wyh.online_judge.controller;

import com.wyh.online_judge.mapper.ContestMapper;
import com.wyh.online_judge.pojo.Contest;
import com.wyh.online_judge.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContestController {
    //自动注入 ContestMapper
    @Autowired
    private ContestMapper contestMapper;

    //查询所有竞赛
    @GetMapping("/contests")
    public String list(Model model) {
        List<Contest> contests = contestMapper.queryContestList();
        model.addAttribute("contests", contests);
        return "contest/list";
    }

    //添加竞赛页面
    @GetMapping("/toContestAddPage")
    public String toAddPage() {
        return "contest/add";
    }

    //添加竞赛
    @PostMapping("/addContest")
    public String addContest(Contest contest) {
        contestMapper.addContest(contest);
        return "redirect:/contests";
    }

    //去竞赛修改页面
    @GetMapping("/toContestUpdatePage/{id}")
    public String toUpdatePage(@PathVariable("id") Integer id, Model model) {
        Contest contest = contestMapper.queryContestById(id);
        model.addAttribute("contest", contest);
        return "contest/update";
    }

    //修改竞赛信息
    @PostMapping("/updateContest")
    public String updateContest(Contest contest) {
        contestMapper.updateContest(contest);
        return "redirect:/contests";
    }

    //删除竞赛
    @GetMapping("/deleteContest/{id}")
    public String deleteContest(@PathVariable("id") Integer id) {
        contestMapper.deleteContest(id);
        return "redirect:/contests";
    }

    //查询竞赛详情
    @GetMapping("/contest/{id}")
    public String contestDetail(@PathVariable("id") Integer id, Model model) {
        Contest contest = contestMapper.queryContestById(id);
        model.addAttribute("contest", contest);
        return "contest/detail";
    }

    @PostMapping("queryContestsBySponsorAndKeyword")
    public String queryProblemsByKeywords(String sponsor,String keyword, Model model){
        List<Contest> contests = contestMapper.queryContestsBySponsorAndKeyword(sponsor, keyword);
        model.addAttribute("contests",contests);
        return "contest/list";
    }


}