/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/30
 */

package io.jjong.security.teacher;

import java.util.HashMap;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class TeacherTokenProvider implements AuthenticationProvider, InitializingBean {

  private HashMap<String, Teacher> teacherDB = new HashMap<>();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if(authentication instanceof UsernamePasswordAuthenticationToken){
      UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
      if(teacherDB.containsKey(token.getName())){
        return getAuthenticationToken(token.getName());
      }
      return null;
    }
    TeacherAuthenticationToken token = (TeacherAuthenticationToken) authentication;
    String id = token.getPrincipal().getId();
    if (teacherDB.containsKey(id)) {
      return getAuthenticationToken(id);
    }
    // 내가 처리할 수 없는 Authentication 은 null로 넘겨야 함.
    return null;
  }

  private TeacherAuthenticationToken getAuthenticationToken(String id) {
    Teacher teacher = teacherDB.get(id);
    return TeacherAuthenticationToken.builder()
        .principal(teacher)
        .details(teacher.getUsername())
        .authenticated(true)
        .build();
  }

  @Override
  public boolean supports(Class<?> authentication) {
    // 해당하는 토큰으로 받을 경우에 검증을 진행.
    return authentication == TeacherAuthenticationToken.class ||
        authentication == UsernamePasswordAuthenticationToken.class;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Set.of(
        new Teacher("hong", "gildong", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))),
        new Teacher("choi", "donggu", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER"))),
        new Teacher("oh", "jihye", Set.of(new SimpleGrantedAuthority("ROLE_TEACHER")))
    ).forEach( s -> teacherDB.put(s.getId(), s));
  }
}
