package com.wyh.online_judge.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Contest_user {
    Integer id;
    Integer contest_id;
    Integer user_id;
    Integer record;
    Date submit_time;

    public Contest_user(Integer contest_id, Integer user_id, Integer record) {
        this.contest_id = contest_id;
        this.user_id = user_id;
        this.record = record;
        this.submit_time = new Date();
    }
}
