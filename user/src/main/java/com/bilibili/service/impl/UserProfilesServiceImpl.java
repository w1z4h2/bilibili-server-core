package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.context.pojo.UserInfo;
import com.bilibili.exception.LoginException;
import com.bilibili.pojo.dto.user.LoginDTO;
import com.bilibili.pojo.entity.user.UserProfiles;
import com.bilibili.service.UserProfilesService;
import com.bilibili.mapper.UserProfilesMapper;
import com.bilibili.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @description 针对表[user_profiles(用户基本信息表)]的数据库操作Service实现
 * @createDate 2024-11-10 16:29:49
 */
@Service
@AllArgsConstructor
public class UserProfilesServiceImpl extends ServiceImpl<UserProfilesMapper, UserProfiles> implements UserProfilesService {

    @Override
    public String login(LoginDTO loginDTO) {
        UserProfiles userProfiles = lambdaQuery()
                .eq(UserProfiles::getAccount, loginDTO.getAccount())
                .eq(UserProfiles::getPassword, loginDTO.getPassword())
                .one();
        if (userProfiles == null) {
            throw new LoginException();
        }

        Long userId = userProfiles.getId();
        return JwtUtil.generateToken(UserInfo.builder()
                .userId(userId)
                .build());
    }
}




