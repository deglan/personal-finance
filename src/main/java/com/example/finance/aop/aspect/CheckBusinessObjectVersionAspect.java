package com.example.finance.aop.aspect;

import com.example.finance.exception.model.BackendException;
import com.example.finance.model.dto.BudgetDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.UUID;

@Aspect
@AllArgsConstructor
@Component
public class CheckBusinessObjectVersionAspect {

    private final ApplicationContext applicationContext;

    @Pointcut("@annotation(com.example.finance.aop.annotation.CheckBusinessObjectVersion)")
    public void checkBusinessObjectVersionAnnotation() {
    }

    @SneakyThrows
    @Before("checkBusinessObjectVersionAnnotation()")
    public void validateBusinessObjectVersion(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        Object body = args[1];

        Field versionField = body.getClass().getDeclaredField("businessObjectVersion");
        versionField.setAccessible(true);
        int businessObjectVersion = (int) versionField.get(body);
        if (businessObjectVersion > 0) {
            throw new BackendException("businessObjectVersion cannot be less than zero");
        }
    }
}
