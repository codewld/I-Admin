package pers.codewld.iadmin.security.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.codewld.iadmin.common.model.enums.ResultCode;
import pers.codewld.iadmin.common.model.vo.ResultVO;
import pers.codewld.iadmin.security.filter.JWTVerifyFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Spring Security 配置类
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JWTVerifyFilter jwtVerifyFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 访问控制
        http
                .authorizeRequests()
                .antMatchers("/**/login/**").permitAll()
                .anyRequest().authenticated();

        // 未登录异常
        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> returnResult(response, ResultVO.result(ResultCode.UNAUTHORIZED)));

        // 未授权异常
        http
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> returnResult(response, ResultVO.result(ResultCode.FORBIDDEN)));

        // 禁用跨站请求保护
        http
                .csrf()
                .disable();

        // 开启跨域
        http
                .cors();

        // 关闭session
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 禁用默认的登录
        http
                .formLogin()
                .disable();

        // 禁用默认的登出
        http
                .logout()
                .disable();

        // JWT校验过滤器
        http
                .addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        // 忽略静态资源
        web
                .ignoring()
                .antMatchers("/**/*.js")
                .antMatchers("/**/*.css")
                .antMatchers("/**/*.ico");

        // 忽略Swagger
        web
                .ignoring()
                .antMatchers("/**/swagger-ui/**")
                .antMatchers("/**/swagger-resources/**")
                .antMatchers("/**/api-docs/**");
    }

    /**
     * 返回结果
     */
    private void returnResult(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        writer.println(mapper.writeValueAsString(resultVO));
    }

}