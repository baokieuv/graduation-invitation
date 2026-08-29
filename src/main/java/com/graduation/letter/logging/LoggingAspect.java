package com.graduation.letter.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    // Targets all methods within classes annotated with @RestController
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("▶ START [{}]: Arguments = {}", methodName, Arrays.toString(args));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            // Proceed with the actual method execution
            Object result = joinPoint.proceed();

            stopWatch.stop();

            // Serialize result to JSON for cleaner log reading (optional, can just use result.toString())
            String jsonResult = result != null ? objectMapper.writeValueAsString(result) : "null";

            log.info("◀ END [{}]: Executed in {} ms | Response = {}", methodName, stopWatch.getTotalTimeMillis(), jsonResult);

            return result;
        } catch (Exception e) {
            stopWatch.stop();
            log.error("✖ ERROR [{}]: Failed after {} ms | Cause = {}", methodName, stopWatch.getTotalTimeMillis(), e.getMessage());
            throw e;
        }
    }
}