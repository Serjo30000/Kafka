package com.apiService.apiMovieReviews.util;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageRes {
    private String msg;
    private String timeStamp;

    public MessageRes(String msg) {
        this.msg = msg;
        this.timeStamp = LocalDateTime.now().toString();
    }

}
