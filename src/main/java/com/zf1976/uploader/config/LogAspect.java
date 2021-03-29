package com.zf1976.uploader.config;

import com.zf1976.uploader.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.zf1976.uploader.utils.LogUtils.logToFile;

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 日志切面
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(throwing = "ex", pointcut = "execution(* cn.attackme.myuploader.*.*.*(..)))")
    public void logPoint(JoinPoint joinPoint, Throwable ex) {
        LogUtils.logToFile(joinPoint,ex);
    }
}
