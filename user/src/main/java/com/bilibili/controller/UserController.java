package com.bilibili.controller;

import com.bilibili.pojo.dto.user.LoginDTO;
import com.bilibili.result.Result;
import com.bilibili.service.UserProfilesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserProfilesService userProfilesService;

    @PostMapping("/login")
    public Result<Void> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        String token = userProfilesService.login(loginDTO);
        response.setHeader("Authorization", "Bearer " + token);
        return Result.success();
    }
}
