package com.wyh.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission_problem {
    private Integer index;
    private String problem;
    private Integer language;
    private String code;
    private String result;
    private Date submit_time;
}
