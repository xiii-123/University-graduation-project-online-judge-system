package com.wyh.online_judge.mapper;

import com.wyh.online_judge.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Resource
public interface ProblemMapper {
    //查询所有题目
    List<Problem> queryProblemList();
    //根据ID查询题目
    Problem queryProblemById(int id);
    //根据关键字查找题目
    List<Problem> queryProblemsByKeyword(String str);
    //根据标签查找题目
    List<Problem> queryProblemByTag(String str);
    //增加题目
    int addProblem(Problem user);
    //更新题目信息
    int updateProblem(Problem user);
    //删除题目
    int deleteProblem(int id);
}
