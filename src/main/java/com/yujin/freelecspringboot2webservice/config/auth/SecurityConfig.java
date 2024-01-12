package com.yujin.freelecspringboot2webservice.config.auth;

import com.yujin.freelecspringboot2webservice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정들을 활성화시켜 준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해동 옵션들을 disable gksek.
                .and()
                    .authorizeRequests()    // URL 별 권한 관리를 설정하는 옵션의 시작점이다. authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 잇다.
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())    // 권한 관리 대상을 지정하는 옵션이다. URL, HTTP 메소드별로 관리가 가능하다. "/" 등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었다. "/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하다.
                    .anyRequest().authenticated()   // 설정된 값들 이외 나머지 URL들을 나타낸다. 여기서는 authenticated()을 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용한다.
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  // 로그아웃 기능에 대한 여러 설정의 진입점으로 로그아웃 성공 시 / 주소로 이동한다.
                .and()
                    .oauth2Login()  // OAuth 2 로그인 기능에 대한 여러 설정의 진입점이다.
                        .userInfoEndpoint() // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
                            .userService(customOAuth2UserService);  // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다. 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.

    }
}
