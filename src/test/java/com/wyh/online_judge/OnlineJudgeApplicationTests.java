package com.wyh.online_judge;

import com.wyh.online_judge.docker_controller.Docker_c;
import com.wyh.online_judge.docker_controller.Docker_java;
import com.wyh.online_judge.mapper.*;
import com.wyh.online_judge.pojo.Problem;
import com.wyh.online_judge.tools.PrepareWorkDirectory;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootTest
class OnlineJudgeApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	SubmissionMapper submissionMapper;

	@Autowired
	Contest_userMapper contest_userMapper;

	@Autowired
	ProblemMapper problemMapper;

	private PrepareWorkDirectory tools = new PrepareWorkDirectory("1");

	@Test
	void contextLoads(){
		String password="123456";
		System.out.println(DigestUtils.md5Hex(password));
	}
}
