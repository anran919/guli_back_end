package com.zar.service_ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zar.service_base.handler.exception.MyException;
import com.zar.service_ucenter.entity.UcenterMember;
import com.zar.service_ucenter.entity.vo.AuthVo;
import com.zar.service_ucenter.entity.vo.LoginVo;
import com.zar.service_ucenter.entity.vo.RegisterVo;
import com.zar.service_ucenter.mapper.UcenterMemberMapper;
import com.zar.service_ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zar.utils.JwtUtils;
import com.zar.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zar
 * @since 2022-11-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {


    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean register(RegisterVo vo) {
        String mobile = vo.getMobile();
        String password = vo.getPassword();
        String nickname = vo.getNickname();
        String code = vo.getCode();

        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(code)){
            throw new MyException(20001,"参数不完整!");
        }

//        校验验证码
        String rCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(rCode)){
            throw new MyException(20001,"验证码错误");
        }

//        查询是否存在相同的手机号码
        int count = this.count(new QueryWrapper<UcenterMember>().lambda().eq(true, UcenterMember::getMobile, mobile));
        if (count>0){
            throw new MyException(20001,"该手机号已经注册!");
        }

//        添加信息到数据库

        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://zar-edu.oss-cn-hangzhou.aliyuncs.com/avatar/20111116.png");
        boolean save = this.save(member);
        return save;
    }

    @Override
    public String login(LoginVo vo) {
        String mobile = vo.getMobile();
        String password = vo.getPassword();
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new MyException(2001,"登录失败!");
        }
        UcenterMember member = this.getOne(new QueryWrapper<UcenterMember>().lambda().eq(!StringUtils.isEmpty(mobile), UcenterMember::getMobile, mobile));
        if (mobile==null){
            throw new MyException(20001,"手机号未注册!");
        }

        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new MyException(20001,"密码错误!");
        }

        if (member.getIsDisabled()){
            throw new MyException(20001,"用户被禁用!");
        }

        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public AuthVo getAuth(String id) {
        UcenterMember member = this.getById(id);
        AuthVo vo = new AuthVo();
        BeanUtils.copyProperties(member,vo);
        return vo;
    }
}
