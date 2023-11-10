package com.nmviajes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nmviajes.app.servicio.JpaUserDetailsService;

/*VALIDA RUTAS	*/

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig {

/*	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);

	}*/
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*@Bean 
     public static BCryptPasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }
	
	 
	@Bean
    public UserDetailsService userDetailsService()throws Exception{
                
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build());
         manager.createUser(User
                    .withUsername("admin")
                    .password(passwordEncoder().encode("admin"))
                    .roles("ADMIN","USER")
                    .build());
        
        return manager;
    }*/
	 
	 @Autowired
     public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
     }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		
		http.csrf().disable().authorizeRequests()
				.antMatchers("/search_4/**","/search_2/**", "/logos/**", "/css/**", "/js/**", "/img/**", 
						"/", "/nosotros/**", "/oficinas/**", "/ofertas/**",
						"/iniciar_sesion/**", "/registro/**", "/ayuda/**", 
						"/armar_paquete/**","/registrarUsuario","/confirmar_email","/registrarConsulta",
						"/search",/*"/success_11",*/"/generar-informe","/paqueteHV")
				.permitAll()
				.anyRequest().authenticated()
				.and()
					.formLogin().loginPage("/iniciar_sesion").permitAll()
				.and()
					.logout().permitAll()
				.and()
					.exceptionHandling().accessDeniedPage("/error_403");

		return http.build();
	}

}

