package com.qq.frontservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.commonutils.JwtUtils;
import com.qq.commonutils.MD5;
import com.qq.frontservice.entity.Users;
import com.qq.frontservice.entity.vo.userLoginVo;
import com.qq.frontservice.entity.vo.userRegisterVo;
import com.qq.frontservice.mapper.UsersMapper;
import com.qq.frontservice.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qq.servicebase.exceptionhandler.QqException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qq
 * @since 2021-02-23
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Override
    public String login(userLoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 参数检验
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
            throw new QqException(20001, "请输入完整信息~");

        // 获取当前手机号的会员
        Users user = baseMapper.selectOne(new
                QueryWrapper<Users>().eq("mobile", mobile));

        // 如果用户不存在
        if (user == null)
            throw new QqException(20001, "手机号不存在~");

        // 校验密码，引用我们工具类中的MD5加密
        // 因为储存到数据库密码肯定加密的
        // 把输入的密码进行加密，再和数据库密码进行比较
        if (!MD5.encrypt(password).equals(user.getPassword()))
            throw new QqException(20001, "密码错误~");

        // 用户是否被封禁
        if(user.getIsDisabled()==1)
            throw new QqException(20001,"该用户已被封禁~");

        // 使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        return token;
    }

    @Override
    public void register(userRegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();

        //校验参数
        if(StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password)) {
            throw new QqException(20001,"请输入完整信息~");
        }

        // 查询数据库中是否存在相同的手机号码
        Users user = baseMapper.selectOne(new
                QueryWrapper<Users>().eq("mobile", mobile));
        // 如果手机号已存在
        if (user != null)
            throw new QqException(20001, "该手机号已存在~");

        // 属性赋值
        user = new Users();
        user.setNickname(nickname);
        user.setMobile(mobile);
        user.setPassword(MD5.encrypt(password)); // 对密码进行加密
        user.setIsDisabled(0);
        // 默认头像
        user.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJN" +
                "OTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        // 注册
        this.save(user);
    }

    @Override
    public Users getByOpenId(String openid) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        Users one = this.getOne(queryWrapper);
        return one;
    }
}
