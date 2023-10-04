package com.example.onism.Utils;



import com.example.onism.services.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDatailsService(){
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDatailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return  http.csrf().disable()
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/bootstrap-5.3.0-alpha3-dist/css/**", "/bootstrap-5.3.0-alpha3-dist/js/**", "/", "/register", "/error","/images/**","/cart")
                        .permitAll()
                        .requestMatchers("/invoices/view","/sanPhams/edit", "/sanPhams/delete", "/sanPhams/add")
                        .hasAnyAuthority("ADMIN", "QUANLY")
                        .requestMatchers("/sanPhams/add-to-cart")
                        .hasAnyAuthority("USER")
                        .requestMatchers("/nhanViens")
                        .hasAnyAuthority("QUANLY")
                        .requestMatchers("/sanPhams","/sanPhams/search", "/sanPhams/sp")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                ).rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400).userDetailsService(userDatailsService())
                ).exceptionHandling(exceptionHandling-> exceptionHandling.accessDeniedPage("/403")).build();

    }


}

