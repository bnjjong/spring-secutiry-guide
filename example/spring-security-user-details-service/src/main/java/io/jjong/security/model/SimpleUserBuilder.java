package io.jjong.security.model;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * create on 2022/10/02. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 *
 * @author Jongsang Han(henry)
 * @version 1.0
 * @see
 * @since 1.0
 */
@Component
public class SimpleUserBuilder {
  private PasswordEncoder passwordEncoder;

  public UserDetails getUserDetails() {
    User.UserBuilder builder = User.withUsername("Henry");
    return builder
        .password("1234")
        .authorities("READ", "WRITE")
        // 인코딩을 지정할 경우 UserDetails 인스턴스 생성 시 인코딩을 수행한다.
        .passwordEncoder(p -> passwordEncoder.encode(p))
        .accountExpired(false)
        .disabled(false)
        .build();
  }
}
