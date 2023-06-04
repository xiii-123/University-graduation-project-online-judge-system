package com.wyh.online_judge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyh.online_judge.docker_controller.Docker_c;
import com.wyh.online_judge.docker_controller.Docker_cpp;
import com.wyh.online_judge.docker_controller.Docker_java;
import com.wyh.online_judge.mapper.ProblemMapper;
import com.wyh.online_judge.mapper.SubmissionMapper;
import com.wyh.online_judge.pojo.Problem;
import com.wyh.online_judge.pojo.Submission;
import com.wyh.online_judge.tools.PrepareWorkDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class PracticeController {
    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private SubmissionMapper submissionMapper;

    @RequestMapping("/practice")
    public String list(Model model){
        Collection<Problem> problems = problemMapper.queryProblemList();
        model.addAttribute("problems",problems);
        return "practice/list";
    }

    @RequestMapping("/toPracticePage/{id}")
    public String toPracticePage(@PathVariable("id") Integer id, Model model){
        //查出原来的数据
        Problem problem = problemMapper.queryProblemById(id);
        model.addAttribute("problem",problem);
        return "practice/do";
    }

    @RequestMapping("/judgment")
    @ResponseBody
    public String judgment(@RequestParam("problem_id")String problem_id, @RequestParam("language")String language, @RequestParam("code")String code, HttpSession session) throws IOException, InterruptedException {
        //查出各种数据
        ////通过session查出userId
        String userId = session.getAttribute("userId").toString();
        ObjectMapper mapper = new ObjectMapper();
        //获取submission表单的条目
        int num = submissionMapper.getNum()+1;
        //查出problem
        Problem problem = problemMapper.queryProblemById(Integer.parseInt(problem_id));
        //new 一个submission对象
        Submission submission = new Submission(num,Integer.parseInt(userId),Integer.parseInt(problem_id),Integer.parseInt(language),code);
        //准备工作环境
        int result;
        Map<String,String> map = new HashMap<>();
        PrepareWorkDirectory tools = new PrepareWorkDirectory(num+"");
        if (language.equals("1")){
            tools.prepareWorkDirectory_c(code,problem.getMain_c(),problem.getTest_set());
            //运行docker
            Docker_c docker = new Docker_c("D:\\test_docker\\"+num);
            result = docker.judgement();
            //结果放入map，转为json字符串
            map.put("result",result+"");
            map.put("compile_error",docker.getCompile_error());
            map.put("stderr",docker.getError_stream());
            map.put("stdout",docker.getConsole_output());
        }
        else if (language.equals("2")) {
            tools.prepareWorkDirectory_cpp(code,problem.getMain_cpp(),problem.getTest_set());
            //运行docker
            Docker_cpp docker = new Docker_cpp("D:\\test_docker\\"+num);
            result = docker.judgement();
            //结果放入map，转为json字符串
            map.put("result",result+"");
            map.put("compile_error",docker.getCompile_error());
            map.put("stderr",docker.getError_stream());
            map.put("stdout",docker.getConsole_output());
        }
        else {
            tools.prepareWorkDirectory_java(code,problem.getMain_java(),problem.getTest_set());
            //运行docker
            Docker_java docker = new Docker_java("D:\\test_docker\\"+num);
            result = docker.run_java();
            //结果放入map，转为json字符串
            map.put("result",result+"");
            map.put("compile_error",docker.getCompile_error());
            map.put("stderr",docker.getError_stream());
            map.put("stdout",docker.getConsole_output());
        }

        //提交submission
        submission.setTime_used("100");
        submission.setMemory_used("100");
        submission.setResult(result+"");
        submissionMapper.addSubmission(submission);

        String s = mapper.writeValueAsString(map);

        // 返回JSON响应
        return s;
    }


    @PostMapping("queryPracticesByKeyword")
    public String queryProblemsByKeywords(String keyword, Model model){
        List<Problem> problems = problemMapper.queryProblemsByKeyword(keyword);
        model.addAttribute("problems",problems);
        return "practice/list";
    }

}
