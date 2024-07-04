package com.example.finance.aop.aspect;

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

import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@AllArgsConstructor
@Component
public class ItemWithIdMustExistAspect {

    private final ApplicationContext applicationContext;

    @Pointcut("@annotation(com.example.finance.aop.annotation.ItemWithIdMustExist)")
    public void mustExistAnnotation() {

    }

    @SneakyThrows
    @Before("mustExistAnnotation()")
    public void validateIfObjectExist(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ItemWithIdMustExist annotation = method.getAnnotation(ItemWithIdMustExist.class);
        Object[] args = joinPoint.getArgs();
        UUID uuid = (UUID) args[0];

        Object serviceBean = applicationContext.getBean(annotation.serviceClass());
        Class<?> serviceClass = serviceBean.getClass();
        Method checkIfExistByIdMethod = serviceClass.getMethod(annotation.checkExistByIdMethodName(), UUID.class);
        boolean isExist = (boolean) checkIfExistByIdMethod.invoke(serviceBean, uuid);
        if (!isExist) {
            throw new BackendException("Item does not exist");
        }
    }
//
//    @After("mustExistAnnotation()")
//    public void afterMethod() {
//
//    }
//
//    @AfterReturning("mustExistAnnotation()")
//    public void afterReturningMethod() {
//
//    }
//
//    @AfterThrowing("mustExistAnnotation()")
//    public void afterThrowingMethod() {
//
//    }
//
//    @SneakyThrows
//    @Around("mustExistAnnotation()")
//    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
//        // Logika przed wywołaniem metody
//        Object proceed = proceedingJoinPoint.proceed();
//        // after method logic
//        return proceed;
//    }
}
