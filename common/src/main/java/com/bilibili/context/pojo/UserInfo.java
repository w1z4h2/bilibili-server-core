package com.bilibili.context.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {

    /**
     * 用户ID
     */
    private Long userId;

    public static final String USER_ID = "userId";
}
