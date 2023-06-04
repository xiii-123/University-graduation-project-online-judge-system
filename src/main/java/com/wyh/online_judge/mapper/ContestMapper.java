package com.wyh.online_judge.mapper;

import com.wyh.online_judge.pojo.Contest;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Resource
public interface ContestMapper {
    //获取所有竞赛
    public List<Contest> queryContestList();
    //根据ID获取竞赛
    public Contest queryContestById(int id);
    //根据用户名和关键字获取竞赛
    public List<Contest> queryContestsBySponsorAndKeyword(String sponsor, String keyword);
    //添加竞赛
    public int addContest(Contest contest);
    //更新竞赛
    public int updateContest(Contest contest);
    //删除竞赛
    public int deleteContest(int id);


}
