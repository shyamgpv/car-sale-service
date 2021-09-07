package com.shyam.carsaleservice.secuirity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
       http.authorizeRequests()
               .antMatchers("/car/addListing","/car/deleteListing/*","/car/deleteListing/*").hasRole("ADMIN")
               .antMatchers("/car/get*/*").hasAnyRole("USER","ADMIN")
               .antMatchers("/","/car","/car/message").permitAll()
               .and().formLogin();
    }
}
