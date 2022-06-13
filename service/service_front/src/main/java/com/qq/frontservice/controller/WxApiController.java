package com.qq.frontservice.controller;

import com.google.gson.Gson;
import com.qq.commonutils.DateUtil;
import com.qq.commonutils.JwtUtils;
import com.qq.frontservice.client.StaClient;
import com.qq.frontservice.entity.Users;
import com.qq.frontservice.service.UsersService;
import com.qq.frontservice.utils.ConstantPropertiesUtil;
import com.qq.frontservice.utils.HttpClientUtils;
import com.qq.servicebase.exceptionhandler.QqException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

/**
 * 微信扫码登录
 */
// @CrossOrigin
@Controller//注意这里没有配置 @RestController，因为不用返回状态，而是进行页面跳转
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private StaClient staClient;

    /**
     * 获取扫描人信息，并向数据库添加数据
     * 扫描好二维码，微信回调到这个方法，这两个参数是地址后面跟着的
     *
     * @param code  类似于手机验证码，随机唯一值（临时票据）
     * @param state 原样传递（是由我们传入跳转到微信二维码地址中的state决定的）
     * @return
     */
    @GetMapping("/callback")
    public String callback(String code, String state) {
        try {
            /**
             * 1、根据code，请求微信提供的固定地址，获取到两个值
             * access_token：访问凭证
             * openid：每个微信的唯一标识
             */

            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";
            // 拼接三个参数：id，密钥和code
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code);
            // 请求拼接好的地址，得到返回结果
            // 使用工具类中的httpclient发送请求
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            // 由于返回结果是一串json格式的字符串，要从中获取需要的数据，可以转化成map
            Gson gson = new Gson();
            HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            // 获取需要的数据
            String accessToken = (String) accessTokenMap.get("access_token");
            String openid = (String) accessTokenMap.get("openid");

            /**
             * 2、获取用户数据
             */

            Users user = usersService.getByOpenId(openid);
            // 若数据库中无该微信数据，则先添加
            if (user == null) {

                /**
                 * 根据之前获取到的两个值，请求微信提供的固定地址，最终得到微信扫描人的信息
                 */

                // 访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                // 拼接参数
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                // 发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                // 将用户信息转化为map
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);

                // 添加数据
                user = new Users();
                user.setNickname((String) userInfoMap.get("nickname"));
                user.setOpenid(openid);
                user.setAvatar((String) userInfoMap.get("headimgurl"));
                usersService.save(user);
            }

            /**
             * 3、登录成功~，封装JWT令牌
             */

            user = usersService.getByOpenId(openid);
            // 生成jwt
            String token = JwtUtils.getJwtToken(user.getId(), user.getNickname());
            //存入cookie
            //CookieUtils.setCookie(request, response, "qq_edu_token", token);

            // 登录成功后，添加统计数据
            staClient.addLoginNum(DateUtil.formatDate(new Date()));

            //因为端口号不同存在跨域问题，cookie不能跨域，所以这里使用url传递值
            // 扫描并登录成功后重定向到首页
            return "redirect:http://localhost:3000?token=" + token;
        } catch (Exception e) {
            throw new QqException(20001, "微信扫码登录失败~");
        }
    }

    // 跳转到微信的扫描二维码
    @GetMapping("/login")
    public String getWxCode() {
        // 拼接微信开放平台授权baseUrl
        // 传统的拼接字符串方法，如果地址后面的参数过多，不太适合
//        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?appid"
//                + ConstantPropertiesUtil.WX_OPEN_APP_ID + "&response_type=code";

        // 要求传入的回调地址应该被URLEncoder编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {

        }

        // %s相当于？，代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 为%s赋值
        String url = String.format(baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID, // 从配置文件中读取的
                redirectUrl,
                "数学启蒙教育网站");

        // 重定向到微信开放平台的二维码
        return "redirect:" + url;
    }
}
