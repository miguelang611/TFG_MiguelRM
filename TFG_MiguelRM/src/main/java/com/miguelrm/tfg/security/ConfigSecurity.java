package com.miguelrm.tfg.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration  
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

@Autowired
private DataSource dataSource;

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	//Le decimos que autentique contra el datasource de la BD
	
	auth.jdbcAuthentication().dataSource(dataSource)
	//Hacemos la select de SQL puro
	.usersByUsernameQuery("select email, password, enabled from Usuarios where email=?")
	//Qué tienes que recoger de aquí
	.authoritiesByUsernameQuery("select u.email, p.nombre from Usuario_Perfiles up " +  "inner join Usuarios u on u.email = up.email " +
	"inner join Perfiles p on p.id_perfil = up.id_Perfil " +  "where u.email = ?");

}
@Override
protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		// Los recursos estÃ¡ticos no requieren autenticaciÃ³n
//		.antMatchers(
//		"/bootstrap/**",  "/images/**",  "/tinymce/**",  "/logos/**").permitAll()
		
		// Asignar permisos a URLs por ROLES
		.antMatchers("/tipos/**").hasAnyAuthority("redactor","admin")
		.antMatchers("/gestion/eventos/**").hasAnyAuthority("organizador","admin")
		.antMatchers("/gestion/noticias/**").hasAnyAuthority("redactor","admin")
		.antMatchers("/app/perfiles/**").hasAnyAuthority("ADMINISTRADOR")
		
		// Las vistas pÃºblicas no requieren autenticaciÃ³n
		.antMatchers("/",
		"/login",
		"/search",
		"/eventos/**",
		"/noticias/**",
		"/resources/**"
		).permitAll()
		

		
		// Todas las demÃ¡s URLs de la AplicaciÃ³n requieren autenticaciÃ³n
		.anyRequest().authenticated()
		
		// El formulario de Login no requiere autenticacion
		.and().formLogin().permitAll();
		
	
}

}