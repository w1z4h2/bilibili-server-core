package com.bilibili.pojo.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bilibili.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户基本信息表
 * @TableName user_profiles
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="user_profiles")
@Data
public class UserProfiles extends BaseEntity {
    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;
}