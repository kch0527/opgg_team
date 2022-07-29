package opgg.opgg.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customMemberDetailService;

    /**
     * BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체이다.
     * 비밀번호를 암호화해서 사용할 수 있도록 Bean으로 등록한다.
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 에서 모든 인증처리는 AuthenticationManager 를 통해 이루어지는데,
     * AuthenticationManager 를 생성하기 위해서 AuthenticationManagerBuilder 를 사용해 생성한다.
     * 로그인 인증을 위해 MyUserDetailsService 에서 UserDetailsService 를 implements 하여 loadUserByUsername() 메소드를 구현했다.
     * 그리고 AuthenticationManager 에게 어떤 해쉬로 암호화했는지 알려주기 위해 passwordEncoder 를 사용했다.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customMemberDetailService).passwordEncoder(encoder());
    }

    /**
      * 인증을 무시할 경로를 설정해준다.
      * static 하위 폴더는 무조건 접근이 가능해야하기 때문에 인증을 무시하게 해 주었다.
      */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers("/assets/**", "/css/**", "/dist/**", "/js/**", "/plugins/**",
                "/favicon.ico", "/resources/**", "/error");
        //"/favicon.ico", "/resources/**", "/error" 붙혀서 오류 해결
    }

    /**
     * HttpSecurity 를 통해 HTTP 요청에 대한 보안을 구성할 수 있다.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //HttpServletRequest 에 따라 접근을 제한
        //antMatchers()메서드로 특정 경로를 지정, permitAll(), hasRole()메서드로 접근 설정
        http.authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/user/myinfo").hasRole("USER")
                    .antMatchers("/","/search", "/home","/auth/**")
                    .permitAll()
                    //그 외의 경로는 인증된 사용자만 접근 가능
                     .anyRequest()
                     .authenticated()
                .and()
                        .formLogin()
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
                    .and()
                        .logout()
                        //로그아웃 안되던거 아래거 추가해서 해결
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true);
    }
}
