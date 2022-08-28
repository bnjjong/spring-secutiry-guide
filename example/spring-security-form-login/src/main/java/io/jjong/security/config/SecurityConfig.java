package io.jjong.security.config;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true) // pre post로 권한 체크를 하겠다.
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
  private final CustomAuthDetails customAuthDetails;

  @Bean
  RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER"); // Admin 은 user 권한을 포함 한다.
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
    http
        .authorizeRequests(request -> {
              request
                  .antMatchers("/").permitAll()
                  .anyRequest().authenticated()
              ;
            }
        )
        .formLogin( // DefaultLoginPageGeneratingFilter 가 적용이 되고 동작 되게 되어 있다.
            login -> login.loginPage("/login")
                .permitAll() // 이걸 안하면 무한 루프에 빠진다.
                .defaultSuccessUrl("/", false)
                .failureUrl("/login-error")
                .authenticationDetailsSource(customAuthDetails) // details source를 이용해서 필요한 정보를 담아 둘 수 있다.
        )
        .logout(logout -> logout.logoutSuccessUrl("/"))
        .exceptionHandling(
            exception -> exception.accessDeniedPage("/access-denied")
        )
    ;

    return http.build();
  }
}
