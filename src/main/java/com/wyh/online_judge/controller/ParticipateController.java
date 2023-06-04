package com.wyh.online_judge.controller;

import com.wyh.online_judge.docker_controller.Docker_c;
import com.wyh.online_judge.docker_controller.Docker_java;
import com.wyh.online_judge.mapper.ContestMapper;
import com.wyh.online_judge.mapper.Contest_userMapper;
import com.wyh.online_judge.mapper.ProblemMapper;
import com.wyh.online_judge.mapper.SubmissionMapper;
import com.wyh.online_judge.pojo.Contest;
import com.wyh.online_judge.pojo.Contest_user;
import com.wyh.online_judge.pojo.Problem;
import com.wyh.online_judge.pojo.Submission;
import com.wyh.online_judge.tools.PrepareWorkDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class ParticipateController {
    @Autowired
    ContestMapper contestMapper;

    @Autowired
    ProblemMapper problemMapper;

    @Autowired
    SubmissionMapper submissionMapper;

    @Autowired
    Contest_userMapper contest_userMapper;

    @RequestMapping("/participate")
    public String list(Model model){
        Collection<Contest> contests = contestMapper.queryContestList();
        model.addAttribute("contests",contests);
        return "participate/list";
    }

    @RequestMapping("/toParticipatePage/{id}")
    public String toParticipatePage(@PathVariable("id") Integer id, Model model){
        //查出原来的数据
        Contest contest = contestMapper.queryContestById(id);
        List<String> list = new ArrayList<String>(Arrays.asList(contest.getProblems().trim().split("\\s*,\\s*")));
        Collection<Problem> problems = new ArrayList<>();
        for (String i : list){
            problems.add(problemMapper.queryProblemById(Integer.parseInt(i)));
        }
        model.addAttribute("problems",problems);
        model.addAttribute("contest_id",id);
        return "participate/do";
    }

    @PostMapping("/submitPaper")
    public String submitPaper(@RequestBody List<List<String>> data, HttpSession session) throws IOException, InterruptedException {
        List<String> problem_ids = data.get(0);
        List<String> languages = data.get(1);
        List<String> codes = data.get(2);
        int contest_id = Integer.parseInt(data.get(3).get(0));
        int num = problem_ids.size();
        int index = submissionMapper.getNum()+1;
        int result=0;
        int perScore = 100/num;
        int userId = Integer.parseInt(session.getAttribute("userId").toString());

        for (int i = 0; i < num; i++){
            Problem problem = problemMapper.queryProblemById(Integer.parseInt(problem_ids.get(i)));
            PrepareWorkDirectory tools = new PrepareWorkDirectory(index+"");
            if (languages.get(i).equals("1") || languages.get(i).equals("2")){
                tools.prepareWorkDirectory_c(codes.get(i),problem.getMain_c(),problem.getTest_set());
                //运行docker
                Docker_c docker = new Docker_c("D:\\test_docker\\"+index);
                if (docker.judgement() == 2)
                    result += perScore;
            }
            else {
                tools.prepareWorkDirectory_java(codes.get(i),problem.getMain_java(),problem.getTest_set());
                //运行docker
                Docker_java docker = new Docker_java("D:\\test_docker\\"+index);
                if (docker.run_java() == 2)
                    result += perScore;
            }
            index++;
        }
        contest_userMapper.addContest_user(new Contest_user(contest_id,userId,result));
        return "participate/list";

    }
    @PostMapping("queryParticipatesBySponsorAndKeyword")
    public String queryProblemsByKeywords(String sponsor,String keyword, Model model){
        List<Contest> contests = contestMapper.queryContestsBySponsorAndKeyword(sponsor, keyword);
        model.addAttribute("contests",contests);
        return "participate/list";
    }
}
