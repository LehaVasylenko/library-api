package com.example.demo.aspect;

import com.example.demo.mapper.ExceptionStatusMapper;
import com.example.demo.services.KafkaProducerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * demo
 * Author: Vasylenko Oleksii
 * Date: 17.06.2024
 */
@Slf4j
@Aspect
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ControllerLoggingAspect {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Before("execution(* (@LoggableController *).*(..)) && args(body)")
    public void logBefore(JoinPoint joinPoint, Object body) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        String requestBody = wrappedRequest.getContentAsString();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Map<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            data.put("user", authentication.getName()); // or use authentication.getPrincipal() if needed
        } else {
            data.put("user", "anonymousUser");
        }
        data.put("time", LocalDateTime.now());
        data.put("method", request.getMethod());
        data.put("path", request.getRequestURI());
        data.put("requestBody", body);

        request.setAttribute("requestData", data);
    }

    @AfterReturning(pointcut = "execution(* (@LoggableController *).*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) request.getAttribute("requestData");
        if (data != null) {
            data.put("responsebody", result);
            kafkaProducerService.sendMessageTelegram(data);
            log.info(data.toString());
        }
    }

    @AfterThrowing(pointcut = "execution(* (@LoggableController *).*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) request.getAttribute("requestData");
        if (data != null) {
            data.put("responsebody", ex.getMessage());
            HttpStatus status = ExceptionStatusMapper.determineHttpStatus(ex);
            data.put("statuscode", status.value());
            kafkaProducerService.sendMessageTelegram(data);
            log.info(data.toString());
        }
    }
}
