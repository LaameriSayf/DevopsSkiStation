package tn.esprit.ouday_oueslati_4TWIN5.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Before("execution(* tn.esprit.ouday_oueslati_4TWIN5.services.*.*(..))")
    public void MethodEntry(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("ajouter {} avec succés ", name);
    }
    @After("execution(* tn.esprit.ouday_oueslati_4TWIN5.services.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("notre methode  {}  avec succés ", name);
    }
}
