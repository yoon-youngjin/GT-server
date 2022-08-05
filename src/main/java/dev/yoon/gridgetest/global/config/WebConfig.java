package dev.yoon.gridgetest.global.config;

import dev.yoon.gridgetest.global.interceptor.AdminAuthorizationInterceptor;
import dev.yoon.gridgetest.global.interceptor.AuthenticationInterceptor;
import dev.yoon.gridgetest.global.resolver.UserPhoneArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@RequiredArgsConstructor
@EnableWebMvc
@Configuration
@ComponentScan("dev.yoon.gridgetest")
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    private final UserPhoneArgumentResolver userPhoneArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .excludePathPatterns("/api/users/login", "/api/users/oauth/login", "/api/users/auto-login",
                        "/api/users/oauth/sign-up", "/api/users/sign-up", "/api/users/sms/send",
                        "/api/users/duplicated/nickname", "/api/users/duplicated/phone",
                        "/api/users/sms/auth", "/api/health", "/api/token/reissue")
                .addPathPatterns("/api/**");

        registry.addInterceptor(adminAuthorizationInterceptor)
                .order(2)
                .addPathPatterns("/api/admin/**");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userPhoneArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name()
                );
    }

}