package com.zar.sms.service;

public interface SmsService {
    boolean sendSmsCode(String phone, String code);
}
