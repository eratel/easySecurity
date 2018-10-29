package com.abhsy.easy.adapt;

import com.abhsy.easy.config.FeignConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({FeignConfig.class})
public @interface EnableSecurity
{

}
