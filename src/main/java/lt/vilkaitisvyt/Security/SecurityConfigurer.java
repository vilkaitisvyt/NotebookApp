package lt.vilkaitisvyt.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import lt.vilkaitisvyt.Filter.JwtRequestFilter;
import lt.vilkaitisvyt.Service.MyUserDetailsService;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.userDetailsService(myUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.csrf().disable();
		http.authorizeRequests()
							.antMatchers("/authenticate").permitAll()
							.antMatchers("/login").permitAll()
							.antMatchers("/").permitAll()
							.antMatchers("/registrationPage").permitAll()
							.antMatchers("/register").permitAll()
							.antMatchers("/h2-console/**").permitAll()
							.antMatchers("/css/**").permitAll()
							.antMatchers("/img/**").permitAll()	
							.antMatchers("/favicon.ico").permitAll()	
							.anyRequest().authenticated()
//							.and().formLogin().loginPage("/login")
//							.defaultSuccessUrl("/", true)
//							.loginProcessingUrl("/authenticate")
//							.failureHandler(authenticationFailureHandler())
//							.permitAll()
							.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public AuthenticationFailureHandler authenticationFailureHandler() {		
//	    ExceptionMappingAuthenticationFailureHandler failureHandler = new ExceptionMappingAuthenticationFailureHandler();	    
//	    Map<String, String> failureUrlMap = new HashMap<>();	    
//	    failureUrlMap.put(ExpiredJwtException.class.getName(), "/");
//	    failureUrlMap.put(UsernameNotFoundException.class.getName(), "/");
//	    failureHandler.setExceptionMappings(failureUrlMap);
//	    return failureHandler;
//	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowSemicolon(true);
	    return firewall;
	}
}
