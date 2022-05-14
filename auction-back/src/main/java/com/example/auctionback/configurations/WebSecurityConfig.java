package com.example.auctionback.configurations;

import com.example.auctionback.security.MainAuthFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final MainAuthFilter mainAuthFilter;

    public WebSecurityConfig(MainAuthFilter mainAuthFilter) {
        this.mainAuthFilter = mainAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().disable()
                .sessionManagement().disable()
                .authorizeHttpRequests()
                    .antMatchers("/auction/all").permitAll()
                    .antMatchers("/auction/{id}").permitAll()
                    .antMatchers("/registration/signup").permitAll()
                    .antMatchers("/registration/signin").permitAll()
                .anyRequest().authenticated()
                .and()
                    .addFilterAfter(
                            mainAuthFilter.setRequireAuthMatcher(
                                    new AndRequestMatcher(new AntPathRequestMatcher("/**"))
                            ), UsernamePasswordAuthenticationFilter.class
                    );

//                .and()
//                    //Настройка для входа в систему
//                    .formLogin()
//                    .loginPage("/registration/signin")
//                    //Перенарпавление на главную страницу после успешного входа
//                    .successHandler(new MySimpleUrlAuthenticationSuccessHandler())
//                .and()
//                    .logout()
//                    .logoutSuccessUrl("/auction/all");
    }
}
