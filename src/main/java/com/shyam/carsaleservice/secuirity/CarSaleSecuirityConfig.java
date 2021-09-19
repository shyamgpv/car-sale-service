package com.shyam.carsaleservice.secuirity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shyam.carsaleservice.response.ResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

//2 users "admin" and "user" with 2 User Roles "ADMIN" and "USER"

@EnableWebSecurity
public class CarSaleSecuirityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //config to auth
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("$2a$12$STuJIuKp/P3APKspTLGgQuGInkjmdqeASAJB63yna9BoWX6iR8WAO") //BCryptPasswordEncoder
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("$2a$12$wFipgdMtcjaEpqSaff0rl.g838PmBv.OrE.YwJBvFX6VOzzYq74ti") //BCryptPasswordEncoder
                .roles("USER");


    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable().authorizeRequests()
               .antMatchers("/car/addListing","/car/addListings","/car/updateListing/*","/car/deleteListing/*","/car/admin").hasRole("ADMIN") //most restrictive pages to least restrictive
               .antMatchers("/car/get*/*","/car/user").hasAnyRole("USER","ADMIN")
               .antMatchers("/","/car","/car/message").permitAll()
               .and().formLogin()
               .loginProcessingUrl("/user_login").successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        // run custom logics upon successful login

                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        String username = userDetails.getUsername();

                        System.out.println("The user " + username + " has logged in.");
                        ObjectMapper JsonUtil = new ObjectMapper();
                        //response.sendRedirect(request.getContextPath());
                        response.setContentType("application/json");
                        response.getWriter().append(JsonUtil.writeValueAsString(ResponseHandler.generateResponse("The user " + username + " has logged in.", HttpStatus.OK, null)));
                        response.setStatus(200);
                    }
                }) //shows "user logged in" message
               .failureHandler(new AuthenticationFailureHandler() {
                   @Override
                   public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                       ObjectMapper JsonUtil = new ObjectMapper();
                       httpServletResponse.setContentType("application/json");
                       httpServletResponse.getWriter().append(JsonUtil.writeValueAsString(ResponseHandler.generateResponse("Authentication failure", HttpStatus.UNAUTHORIZED, null)));
                      httpServletResponse.setStatus(401);

                   }

               }) //shows "user logged in" message
                 .and()
                 .logout().logoutUrl("/logout");
    }
    @Bean
    public HttpFirewall configHttpFirewall(){
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET","POST")); //firewall config to pass only get and post
        return firewall;
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){ //custom messaging for access denied
        return (request,response,ex)->{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ServletOutputStream out = response.getOutputStream();
            new ObjectMapper().writeValue(out, new MyCustomErrorDTO(403,"Access denied by the system",false));
            out.flush();
        };
    }
}
