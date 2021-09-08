package com.shyam.carsaleservice.secuirity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@EnableWebSecurity
public class CarSaleSecuirityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //config to auth
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("$2a$12$STuJIuKp/P3APKspTLGgQuGInkjmdqeASAJB63yna9BoWX6iR8WAO")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("$2a$12$wFipgdMtcjaEpqSaff0rl.g838PmBv.OrE.YwJBvFX6VOzzYq74ti")
                .roles("USER");


    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable().authorizeRequests()
               .antMatchers("/car/addListing","/car/deleteListing/*","/car/deleteListing/*","/car/admin").hasRole("ADMIN")
               .antMatchers("/car/get*/*","/car/user").hasAnyRole("USER","ADMIN")
               .antMatchers("/","/car","/car/message").permitAll()
               .and().formLogin()
               .loginProcessingUrl("/user_login");
    }
    @Bean
    public HttpFirewall configHttpFirewall(){
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET","POST"));
        return firewall;
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return (request,response,ex)->{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ServletOutputStream out = response.getOutputStream();
            new ObjectMapper().writeValue(out, new MyCustomErrorDTO(403,"Access denied by the system",false));
            out.flush();
        };
    }
}
