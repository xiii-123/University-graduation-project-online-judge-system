package com.wyh.online_judge.controller;

import com.wyh.online_judge.mapper.ProblemMapper;
import com.wyh.online_judge.mapper.SubmissionMapper;
import com.wyh.online_judge.pojo.Problem;
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
public class ProblemController {
    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    SubmissionMapper submissionMapper;

    @RequestMapping("/problems")
    public String list(Model model){
        Collection<Problem> problems = problemMapper.queryProblemList();
        model.addAttribute("problems",problems);
        return "problem/list";
    }

    @RequestMapping("/toProblemAddPage")
    public String toAddPage(){
        return "problem/add";
    }

    @PostMapping("/addProblem")
    public String addProblem(Problem problem){
        //添加操作
        problemMapper.addProblem(problem);
        return "redirect:/problems";
    }

    //去题目修改页面
    @GetMapping("/toProblemUpdatePage/{id}")
    public String toUpdatePage(@PathVariable("id") Integer id, Model model){
        //查出原来的数据
        Problem problem = problemMapper.queryProblemById(id);
        model.addAttribute("problem",problem);

        return "problem/update";
    }
    //修改信息
    @PostMapping("/updateProblem")
    public String updateProblem(Problem problem){
        //添加操作
        problemMapper.updateProblem(problem);
        return "redirect:/problems";
    }
    //删除题目
    @GetMapping("/deleteProblem/{id}")
    public String deleteProblem(@PathVariable("id") Integer id){
        submissionMapper.deleteSubmissionByProblemId(id);
        problemMapper.deleteProblem(id);
        return "redirect:/problems";
    }
    @PostMapping("queryProblemsByKeyword")
    public String queryProblemsByKeyword(String keyword, Model model){
        List<Problem> problems = problemMapper.queryProblemsByKeyword(keyword);
        model.addAttribute("problems",problems);
        return "problem/list";
    }
}
