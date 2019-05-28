package com.education.simple.configuration;//package com.education.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //Line 21 → password encoder reference implemented in WebMvcConfig.java
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //Line 24 → data source implemented out of the box by Spring Boot.
    // We only need to provide the database information in the application.properties file (please see the reference below).

    @Autowired
    private DataSource dataSource;
    //Lines 27 and 30 → Reference to user and role queries stored in application.properties file
    @Value("${spring.queries.users-query}")
    private String usersQuery;
    //Lines from 33 to 41 → AuthenticationManagerBuilder provides a mechanism to get a user
    // based on the password encoder, data source, user query and role query.
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Autowired
    @Qualifier("my")
    UserDetailsService userDatailsService;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(this.dataSource);
        return db;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDatailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    // Lines from 44 to 61 → Here we define the antMatchers to provide access based on the role(s)
    // (lines 48 to 51), the parameters for the login process (lines 55 to 56),
    // the success login page(line 53), the failure login page(line 53), and the logout page (line 58).
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("email_login")
                .passwordParameter("password_login")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling();
//                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //Lines from 64 to 68 → Due we have implemented Spring Security we need to let Spring knows that our resources
        // folder can be served skipping the antMatchers defined.
        web
                .ignoring()
                .antMatchers(
//                        "/test",
                        "/registration/**",
                        "/service/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/images/**");
    }
}
