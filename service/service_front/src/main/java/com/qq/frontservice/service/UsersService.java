package com.qq.frontservice.service;

import com.qq.frontservice.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qq.frontservice.entity.vo.userLoginVo;
import com.qq.frontservice.entity.vo.userRegisterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
public interface UsersService extends IService<Users> {

    // 用户登录，登录成功返回token
    String login(userLoginVo loginVo);

    // 用户注册
    void register(userRegisterVo registerVo);

    // 根据微信id获取用户信息
    Users getByOpenId(String openid);
}
