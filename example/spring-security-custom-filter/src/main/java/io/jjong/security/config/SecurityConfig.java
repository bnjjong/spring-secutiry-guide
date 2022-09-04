package io.jjong.security.config;

import io.jjong.security.student.StudentTokenProvider;
import io.jjong.security.teacher.TeacherTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true) // pre post로 권한 체크를 하겠다. <-- 이게 무슨 역활이지.?
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

  private final StudentTokenProvider studentTokenProvider;
  private final TeacherTokenProvider teacherTokenProvider;

  @Bean
  RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_TEACHER > ROLE_STUDENT"); // Admin 은 user 권한을 포함 한다.
    return roleHierarchy;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user1")
        .password(passwordEncoder().encode("1111"))
        .roles("USER")
        .build());
    manager.createUser(User.withUsername("admin")
        .password(passwordEncoder().encode("2222"))
        .roles("ADMIN")
        .build());
    return manager;
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(
        // 아래 path 목록이 허가 됨.
        // /css/**, /js/**, /images/**, /webjars/**, /favicon.*, /*/icon-*
        PathRequest.toStaticResources().atCommonLocations() // static resources.
    );
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
        AuthenticationManagerBuilder.class);
    // manger 를 provider로 등록.
    AuthenticationManager authenticationManager = authenticationManagerBuilder
        .authenticationProvider(studentTokenProvider)
        .authenticationProvider(teacherTokenProvider)
        .build();
    CustomLoginFilter filter = new CustomLoginFilter(authenticationManager);

    http
//        .csrf().disable()
        .authorizeRequests(request ->
            request.antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
        )
//        .formLogin(
//            login -> login.loginPage("/login") // 여기서 usernamePassworAuthenticationFilter가 동작을 하게 됨.
//                .permitAll()
//                .defaultSuccessUrl("/", false)
//                .failureUrl("/login-error")
//        )
        // UsernamePasswordAuthenticationFilter 위치에 filter로 대체 한다.
        .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout.logoutSuccessUrl("/"))
        .authenticationManager(authenticationManager)
        .exceptionHandling(
            e -> e.accessDeniedPage("/access-denied")
        )
    ;

    return http.build();
  }
}
