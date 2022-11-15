package com.zar.sms.controller;

import com.zar.service_base.handler.exception.MyException;
import com.zar.sms.service.SmsService;
import com.zar.utils.R;
import com.zar.utils.RandomUtil;
import io.swagger.annotations.Api;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Api(description = "发送短信")
@RestController
@CrossOrigin
@RequestMapping("/service_sms/sms")
public class SmsController {
    @Resource
    private SmsService smsService;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @GetMapping("send/{phone}")
    public R sendSmsCode(@PathVariable String phone) {
//        1. 先从redis中取.如果没有再发送
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
//       2. 如果redis中不存在,使用阿里元发送
        if (StringUtils.isEmpty(phone)) {
            throw new MyException(20001, "手机号码为空!");
        }
        code = RandomUtil.getFourBitRandom();
        boolean isSuccess = smsService.sendSmsCode(phone, code);
        if (isSuccess) {
            //redis 存储 验证码
            /**
             * phone 手机号
             * code 验证码
             * 5 时长
             * TimeUnit.MINUTES 单位,分钟
             * 如果报错,参考: https://stackoverflow.com/questions/19581059/misconf-redis-is-configured-to-save-rdb-snapshots
             */
            redisTemplate.opsForValue().set(phone, code, 5,TimeUnit.MINUTES);
            return R.ok().message("发送成功!");
        } else {
            return R.error().message("发送失败!");
        }
    }

}
