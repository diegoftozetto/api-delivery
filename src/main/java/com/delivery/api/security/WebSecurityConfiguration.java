package com.delivery.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.httpBasic()
//			.and()
//			.authorizeRequests()
//				.antMatchers("/utils/**").permitAll()
//				.anyRequest().authenticated()
//			.and()
//				.sessionManagement()
//					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
//				.csrf().disable();
		
		http
		.authorizeRequests()
			.antMatchers("/utils/**").permitAll()
			.anyRequest().authenticated()
		.and()
			.oauth2ResourceServer()
				.opaqueToken();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		String cryptPassword = passwordEncoder.encode("1234");
//		
//		auth
//			.inMemoryAuthentication()
//			.withUser("diego")
//			.password(cryptPassword)
//			.roles("ADMIN");
//	}	
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
//	@Bean
//	@Override
//	protected UserDetailsService userDetailsService() {
//		return super.userDetailsService();
//	}

}
