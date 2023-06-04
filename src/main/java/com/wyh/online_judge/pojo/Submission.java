package com.wyh.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Submission {
    private Integer id;
    private Integer user_id;
    private Integer problem_id;
    private Integer language;
    private String code;
    private String result;
    private String time_used;
    private String memory_used;
    private Date submit_time;

    public Submission(Integer id, Integer user_id, Integer problem_id, Integer language, String code) {
        this.id = id;
        this.user_id = user_id;
        this.problem_id = problem_id;
        this.language = language;
        this.code = code;
        this.submit_time = new Date();
    }
}
