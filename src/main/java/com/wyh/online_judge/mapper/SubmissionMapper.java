package com.wyh.online_judge.mapper;

import com.wyh.online_judge.pojo.Rank;
import com.wyh.online_judge.pojo.Submission;
import com.wyh.online_judge.pojo.Submission_problem;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;
@Mapper
@Resource
public interface SubmissionMapper {
    //查询所有记录
    public List<Submission> querySubmissionList();
    //根据用户名和题目关键字查询记录
    public List<Submission> querySubmissionByUserNameAndProblem(String userName, String problemName);
    //根据用户名ID
    public List<Submission_problem> querySubmissionByUserId(int user_id);
    //根据用户名ID和关键字
    public List<Submission_problem> querySubmissionByUserIdAndKeyword(int user_id, String keyword);
    //获取记录的条目数量
    public Integer getNum();
    //添加记录
    public int addSubmission(Submission submission);
    //获取排行榜
    List<Rank> getRank();
    //根据用户id删除记录
    int deleteSubmissionByUserId(int user_Id);
    //根据题目id删除题目
    int deleteSubmissionByProblemId(int problem_id);
}
