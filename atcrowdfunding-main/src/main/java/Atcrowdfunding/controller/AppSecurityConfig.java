
package Atcrowdfunding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		http.authorizeRequests()
			.antMatchers("/" , "/index" , "/index.html" , "/static/**").permitAll()//首页和静态资源授权所有人都可以访问
			.anyRequest().authenticated();//其他请求都需要认证
		//设置和登录相关的配置
		http.formLogin()
			.loginPage("/login.html").permitAll() //指定登录页面 并且授权所有人都可以访问[否则访问未授权页面时跳向登录页面 死循环]
			.usernameParameter("loginacct")  //指定登录请求参数获取账号的 key
			.passwordParameter("userpswd")  //指定登录请求参数 密码的key
			.loginProcessingUrl("/doLogin")//指定springsecurity处理登录请求的url，必须和登录页面提交的post方式的登录请求url一样
			.defaultSuccessUrl("/main.html");//指定登录成功后跳转的页面地址
		//禁用CSRF
		http.csrf().disable();
	}
	@Autowired
	UserDetailsService userDetailsService;
	//@Autowired
    //PasswordEncoder passwordEncoder;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);

		auth.userDetailsService(userDetailsService).passwordEncoder(new Md5PasswordEncoder());
	}
}

