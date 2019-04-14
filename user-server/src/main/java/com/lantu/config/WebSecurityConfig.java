package com.lantu.config;

import com.alibaba.fastjson.JSON;
import com.lantu.entity.RespBean;
import com.lantu.service.UserService;
import com.lantu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Trig
 * @create 2019-04-13 0:03
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    // Spring Security内置了很多了PasswordEncoder
    // 这里我选择自定义 PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            // 对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Util.getMD5String(charSequence.toString());
            }
            // 验证密码是否正确
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return encode(charSequence).equals(s);
            }
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().formLogin()
                /*.loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")*/
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=UTF-8");
                        RespBean respBean = RespBean.builder().status("success").msg("登录成功！").build();
                        PrintWriter out = resp.getWriter();
                        // out.write("{\"status\":\"success\",\"msg\":\"登录成功!\"}");
                        out.write(JSON.toJSONString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        RespBean respBean = RespBean.builder().status("error").msg("登录失败！").build();
                        PrintWriter out = resp.getWriter();
                        // out.write("{\"status\":\"error\",\"msg\":\"登录失败!\"}");
                        out.write(JSON.toJSONString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll().and().csrf().disable();

    }

}
