/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/30
 */

package io.jjong.security.student;

import java.util.HashMap;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * create on 2022/08/30. create by IntelliJ IDEA.
 *
 * <p> Token을 관리 하는 Student manager. </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
@Component
public class StudentTokenProvider implements AuthenticationProvider, InitializingBean {

  private HashMap<String, Student> studentDB = new HashMap<>();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // CustomFilter 에서 StudentAuthenticationToken 으로 넘겨 줌.
    StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;
    String id = token.getPrincipal().getId();
    if (studentDB.containsKey(id)) {
      Student student = studentDB.get(id);
      return StudentAuthenticationToken.builder()
          .principal(student)
          .details(student.getUsername())
          .authenticated(true)
          .build();
    }
    // 내가 처리할 수 없는 Authentication 은 null로 넘겨야 함.
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    // 해당하는 토큰으로 받을 경우에 검증을 진행.
    return authentication == StudentAuthenticationToken.class;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Set.of(
        new Student("han", "jongsang", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
        new Student("jo", "sungmin", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT"))),
        new Student("kim", "jongseo", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")))
    ).forEach( s -> studentDB.put(s.getId(), s));
  }
}
