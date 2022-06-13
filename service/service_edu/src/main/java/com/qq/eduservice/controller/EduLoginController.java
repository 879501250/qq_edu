package com.qq.eduservice.controller;

import com.qq.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin // 解决跨域问题的方式一
@RequestMapping("/eduservice/user")
public class EduLoginController {

    // 管理员登录
    @PostMapping("/login")
    public Result userLogin() {
        return Result.success().data("token","admin");
    }

    // 获取管理员信息（名字，头像）
    @GetMapping("/getInfo")
    public Result getInfo() {
        return Result.success().data("name", "qq").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    // 登出
    @PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }
}
