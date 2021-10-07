package org.generation.blogPessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * Carrega dados específicos do usuário
	 */
	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * Manda os dados do usuário
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	/**
	 * Irá codificar a senha
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Liberar endpoints
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors()
		.and().csrf().disable();
	}
	
}
