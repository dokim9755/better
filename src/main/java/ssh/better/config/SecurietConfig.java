package ssh.better.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ssh.better.config.auth.LoginService;

@Configuration
@EnableWebSecurity
public class SecurietConfig extends WebSecurityConfigurerAdapter {
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/resources/**")
			.antMatchers("/css/**")
			.antMatchers("/js/**")
			.antMatchers("/img/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/", "/login", "/signup", "/posts", "/posts/**/").permitAll()
			.anyRequest().authenticated()
			
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/")
			.failureUrl("/false") // 로그인 실패할 경우 url 수정하기
			.usernameParameter("userId")
			.passwordParameter("userPwd")
			.loginProcessingUrl("/login")
//			.successHandler(new AuthenticationSuccessHandler() {
//				@Override
//				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//						Authentication authentication) throws IOException, ServletException {
//					System.out.println("Authentication: " + authentication.getName());
//					response.sendRedirect("/");
//				}
//			})
//			.failureHandler(new AuthenticationFailureHandler() {
//				@Override
//				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//						AuthenticationException exception) throws IOException, ServletException {
//					System.out.println("exception : >> " + exception.getMessage());
//					response.sendRedirect("/login");
//				}
//			})
			.permitAll()
		.and()
			.logout();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginService);
	}
	
}
