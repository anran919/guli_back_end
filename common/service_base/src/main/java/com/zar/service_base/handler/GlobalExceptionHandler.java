package com.zar.service_base.handler;
import com.zar.commonUtils.R;
import com.zar.service_base.handler.exception.MyException;
import com.zar.service_base.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message("执行了全局处理异常");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message("执行了特定异常");
    }

    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e){
        e.printStackTrace();
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
