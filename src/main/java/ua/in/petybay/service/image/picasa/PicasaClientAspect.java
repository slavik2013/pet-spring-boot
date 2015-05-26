package ua.in.petybay.service.image.picasa;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.in.petybay.util.FileUtil;

/**
 * Created by slavik on 22.04.15.
 */
@Aspect
@Component
public class PicasaClientAspect {

    @Autowired
    private GoogleCredential googleCredential ;


    @Around("allMethodsPointcut()")
    public Object picasaClientAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        System.out.println("Before invoking getName() method");
        Object value = null;
        boolean isOk = false;
        try {
            value = proceedingJoinPoint.proceed();
            isOk = true;
        } catch (Throwable e) {
            System.out.println("e = " + e);
            googleCredential.refreshToken();
            String newAccessToken = googleCredential.getAccessToken();
            FileUtil.saveAccessToken(newAccessToken);
        }
        if(!isOk) {
            try {
                value = proceedingJoinPoint.proceed();
            } catch (Throwable e) {
                System.out.println("e = " + e);
            }
        }

        System.out.println("After invoking getName() method");
        return value;
    }

    @Pointcut("within(ua.in.petybay.service.image.picasa.PicasaClient)")
    public void allMethodsPointcut(){}
}
