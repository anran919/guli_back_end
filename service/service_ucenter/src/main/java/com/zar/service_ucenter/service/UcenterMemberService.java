package com.zar.service_ucenter.service;

import com.zar.service_ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zar.service_ucenter.entity.vo.AuthVo;
import com.zar.service_ucenter.entity.vo.LoginVo;
import com.zar.service_ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zar
 * @since 2022-11-16
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    boolean register(RegisterVo vo);

    String login(LoginVo vo);

    AuthVo getAuth(String id);

    UcenterMember getByOpenid(String openid);
}
