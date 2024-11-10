package com.bilibili.service;

import com.bilibili.pojo.dto.user.LoginDTO;
import com.bilibili.pojo.entity.user.UserProfiles;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description 针对表[user_profiles(用户基本信息表)]的数据库操作Service
 * @createDate 2024-11-10 16:29:49
 */
public interface UserProfilesService extends IService<UserProfiles> {

    String login(LoginDTO loginDTO);
}
