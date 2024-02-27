package xavr.todolist.security;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.stream.Stream;

@Component
@Aspect
public class AuthenticationAspects {

    private final AuthenticationUtils authenticationUtils;

    @Autowired
    public AuthenticationAspects(AuthenticationUtils authenticationUtils) {
        this.authenticationUtils = authenticationUtils;
    }

    @Pointcut("execution(@xavr.todolist.annotations.Authenticational *  *(..))")
    public void annotatedByAuthentication() {
    }

    @Around("annotatedByAuthentication()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = findRequest(joinPoint);
        if (request == null) {
            throw new RuntimeException("Method annotated @Authentication is not have HttpServletRequest argument");
        }
        Object target = joinPoint.getTarget();
        return authenticationUtils.performAfterAuthentication(request, (userId) -> {
            try {
                Field userIdFiled = target.getClass().getDeclaredField("userId");
                userIdFiled.setAccessible(true);
                userIdFiled.set(target, userId);
                ResponseEntity<Object> result = (ResponseEntity<Object>) joinPoint.proceed();

                userIdFiled.set(target, null);
                userIdFiled.setAccessible(false);

                return result;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new RuntimeException("Class with method annotated @Authentication must have userID field");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

        });
    }

    private HttpServletRequest findRequest(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request =
                (HttpServletRequest) Stream.of(joinPoint.
                                getArgs())
                        .filter(arg -> HttpServletRequest.class.isAssignableFrom(arg.getClass()))
                        .findFirst()
                        .orElse(null);

        return request;
    }
}
