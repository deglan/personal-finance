package com.example.finance.aop.aspect;

import com.example.finance.aop.annotation.CheckUuid;
import com.example.finance.aop.annotation.ItemWithIdMustExist;
import com.example.finance.exception.model.BackendException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@AllArgsConstructor
@Component
public class CheckUuidAspect {

    private final ApplicationContext applicationContext;

    @Pointcut("@annotation(com.example.finance.aop.annotation.CheckUuid)")
    public void checkUuidInBodyAndPath() {
    }

    @SneakyThrows
    @Before("checkUuidInBodyAndPath()")
    public void validateUuidInBodyAndPath(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CheckUuid annotation = method.getAnnotation(CheckUuid.class);

        String primaryKeyField = annotation.primaryKey();

        Object[] args = joinPoint.getArgs();
        UUID pathUuid = (UUID) args[0];
        Object body = args[1];

        Field uuidField = body.getClass().getDeclaredField(primaryKeyField);
        uuidField.setAccessible(true);
        UUID bodyUuid = (UUID) uuidField.get(body);
        if (!pathUuid.equals(bodyUuid)) {
            throw new BackendException("UUID in URL does not match UUID in body");
        }
    }
}
