package com.wyh.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contest_result {
    String username;
    String contest_name;
    String record;
    Date submit_time;
}
