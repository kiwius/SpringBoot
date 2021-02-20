package springSecurityFormLogin.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("member/new").permitAll() // "/member/new"는 권한 없이 접근이 가능.
                .antMatchers("/admin").hasRole("ADMIN") // "admin"요청은 "ADMIN" 권한을 가진 사용자만 접근가능.
                .anyRequest().authenticated() //그 외의 모든 요청은 인증된 사용자만 접근가능.
                .and()
                .formLogin().defaultSuccessUrl("./kiwius").permitAll()// FormLogin과 logout 기능 사용가능, login이 성공하면 "/kiwius" url을 요청
                .and()
                .logout();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/templates/**");
    }
}