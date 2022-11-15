package com.zar.service_base.handler;
import com.zar.utils.R;
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

    /**
     * 特定异常,ArithmeticException 0不能为除数的异常
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message("执行了特定异常,0不能为除数");
    }


    /**
     * 自定义异常,需要手动抛出
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e){
        e.printStackTrace();
        log.error(e.getMessage());
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
