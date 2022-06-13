package com.qq.frontservice.controller;

import com.qq.commonutils.DateUtil;
import com.qq.commonutils.JwtUtils;
import com.qq.commonutils.Result;
import com.qq.frontservice.client.StaClient;
import com.qq.frontservice.entity.Users;
import com.qq.frontservice.entity.vo.userLoginVo;
import com.qq.frontservice.entity.vo.userRegisterVo;
import com.qq.frontservice.service.UsersService;
import com.qq.servicebase.exceptionhandler.QqException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p>
 * 用户操作前端控制器
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/frontservice/user")
// @CrossOrigin
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private StaClient staClient;

    // 用户登录，登录成功返回token
    @PostMapping("/login")
    public Result login(@RequestBody userLoginVo loginVo) {
        try {
            String token = usersService.login(loginVo);

            // 登录成功后，添加统计数据
            staClient.addLoginNum(DateUtil.formatDate(new Date()));

            return Result.success().data("token", token);
        } catch (QqException e) { // 如果登录时报错，返回异常信息
            return Result.error().message(e.getMsg());
        }
    }

    // 用户注册
    @PostMapping("/register")
    public Result register(@RequestBody userRegisterVo registerVo) {
        try {
            usersService.register(registerVo);

            // 注册成功后，添加统计数据
            staClient.addRegisterNum(DateUtil.formatDate(new Date()));

            return Result.success();
        } catch (QqException e) { // 如果注册时报错，返回异常信息
            return Result.error().message(e.getMsg());
        }
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            Users user = usersService.getById(memberId);
            return Result.success().data("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QqException(20001, "获取用户信息失败~");
        }
    }
}

