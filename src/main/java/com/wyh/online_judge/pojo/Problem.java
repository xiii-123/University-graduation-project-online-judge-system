package com.wyh.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class Problem {
    private Integer id;
    private String title;
    private String description;
    private String declaration_c;
    private String declaration_cpp;
    private String declaration_java;
    private String test_set;
    private String main_c;
    private String main_cpp;
    private String main_java;
    private String sample_input_1;
    private String sample_output_1;
    private String sample_input_2;
    private String sample_output_2;
    private String sample_input_3;
    private String sample_output_3;
    private String time_limit;
    private String memory_limit;
    private Integer difficulty;
    private String tags;
    private Date create_time;
    private Date update_time;

    public Problem(Integer id, String title, String description, String declaration_c, String declaration_java, String test_set, String main_c, String main_java, String sample_input_1, String sample_output_1, String sample_input_2, String sample_output_2, String sample_input_3, String sample_output_3, String time_limit, String memory_limit, Integer difficulty, String tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.declaration_c = declaration_c;
        this.declaration_java = declaration_java;
        this.test_set = test_set;
        this.main_c = main_c;
        this.main_java = main_java;
        this.sample_input_1 = sample_input_1;
        this.sample_output_1 = sample_output_1;
        this.sample_input_2 = sample_input_2;
        this.sample_output_2 = sample_output_2;
        this.sample_input_3 = sample_input_3;
        this.sample_output_3 = sample_output_3;
        this.time_limit = time_limit;
        this.memory_limit = memory_limit;
        this.difficulty = difficulty;
        this.tags = tags;
        this.create_time = new Date();
    }
}

