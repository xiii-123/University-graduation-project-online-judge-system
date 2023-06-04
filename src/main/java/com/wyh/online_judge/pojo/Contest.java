package com.wyh.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contest {
    private Integer id;
    private String sponsor;
    private String title;
    private String description;
    private String problems;
    private Date start_time;
    private Date end_time;
}
