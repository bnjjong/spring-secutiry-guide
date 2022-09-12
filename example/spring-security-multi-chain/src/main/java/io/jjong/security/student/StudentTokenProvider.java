/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/30
 */

package io.jjong.security.student;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
public class StudentTokenProvider implements AuthenticationProvider, InitializingBean {

  private HashMap<String, Student> studentDB = new HashMap<>();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if(authentication instanceof UsernamePasswordAuthenticationToken){
      UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
      if(studentDB.containsKey(token.getName())){
        return getAuthenticationToken(token.getName());
      }
      return null;
    }

    // CustomFilter 에서 StudentAuthenticationToken 으로 넘겨 줌.
    StudentAuthenticationToken token = (StudentAuthenticationToken) authentication;
    String id = token.getPrincipal().getId();
    if (studentDB.containsKey(id)) {
      return getAuthenticationToken(id);
    }

    // 내가 처리할 수 없는 Authentication 은 null로 넘겨야 함.
    return null;
  }

  private StudentAuthenticationToken getAuthenticationToken(String id) {
    Student student = studentDB.get(id);
    return StudentAuthenticationToken.builder()
        .principal(student)
        .details(student.getUsername())
        .authenticated(true)
        .build();
  }


  /**
   * 모바일 security config 추가로 인해 UsernamePasswordAuthenticationToken 추가
   * @param authentication
   * @return
   */
  @Override
  public boolean supports(Class<?> authentication) {
    // 해당하는 토큰으로 받을 경우에 검증을 진행.
    return authentication == StudentAuthenticationToken.class ||
        authentication == UsernamePasswordAuthenticationToken.class;
  }

  public List<Student> myStudents(String teacherId) {
    return studentDB.values().stream()
        .filter(s -> s.getTeacherId().equals(teacherId))
        .collect(Collectors.toList());
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Set.of(
        new Student("han", "jongsang", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")),
            "hong"),
        new Student("jo", "sungmin", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "hong"),
        new Student("kim", "jongseo", Set.of(new SimpleGrantedAuthority("ROLE_STUDENT")), "hong")
    ).forEach( s -> studentDB.put(s.getId(), s));
  }
}
