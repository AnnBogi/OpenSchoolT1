package ru.t1.OpenSchoolT1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import java.util.logging.Level;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());


    @Before("@annotation(ru.t1.OpenSchoolT1.service.LogCreateTask)")
    public void logBeforeCreateTask(JoinPoint joinPoint) {
        logger.info("Before creating a task. Method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(value = "@annotation(ru.t1.OpenSchoolT1.service.LogCreateTask)", returning = "result")
    public void logAfterCreateTask(JoinPoint joinPoint, Object result) {
        logger.info("After creating a task successfully: " + result);
    }

    @Before("@annotation(ru.t1.OpenSchoolT1.service.LogUpdateTask)")
    public void logBeforeUpdateTask(JoinPoint joinPoint) {
        logger.info("Before updating a task.  Method: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(value = "@annotation(ru.t1.OpenSchoolT1.service.LogUpdateTask)", throwing = "ex")
    public void  logExceptionDuringUpdate(JoinPoint joinPoint, Throwable ex) {
        try {
            logger.severe("Exception occurred during task update in method: " + joinPoint.getSignature().getName() + ": " + ex.getMessage());
            for (StackTraceElement element : ex.getStackTrace()) {
                logger.severe(element.toString());
            }
        } catch (Exception e) {
            logger.severe("Error in handling exception in the aspect: " + e.getMessage());
        }
    }

    @Before("@annotation(ru.t1.OpenSchoolT1.service.LogDeleteTask)")
    public void logBeforeDeleteTask(JoinPoint joinPoint) {
        logger.info("Before deleting a task. Method: " + joinPoint.getSignature().getName());
    }

    @After("@annotation(ru.t1.OpenSchoolT1.service.LogDeleteTask)")
    public void logAfterDeleteTask(JoinPoint joinPoint) {
        logger.info("After deleting a task.Method: " + joinPoint.getSignature().getName());
    }

    @Around("execution(* ru.t1.OpenSchoolT1.service.TaskService.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Around before method: " + joinPoint.getSignature().getName());
        Object result;
        try {
            result = joinPoint.proceed(); // Вызов метода
            logger.info("Around after method: " + joinPoint.getSignature().getName());
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, "Exception in method: " + joinPoint.getSignature().getName(), throwable);
            throw throwable;
        }
        return result;
    }
}



