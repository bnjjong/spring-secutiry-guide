/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/09/04
 */

package io.jjong.security.config;

import io.jjong.security.student.Student;
import io.jjong.security.student.StudentAuthenticationToken;
import io.jjong.security.teacher.Teacher;
import io.jjong.security.teacher.TeacherAuthenticationToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * create on 2022/09/04. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

  /**
   * 상위 클래스에서 정상 동작하게 하기 위해 {@code AuthenticationManager} 주입
   *
   * @param authenticationManager
   */
  public CustomLoginFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  /**
   * token 을 생성하는 곳.
   *
   * @param request from which to extract parameters and perform the authentication
   * @param response the response, which may be needed if the implementation has to do a
   * redirect as part of a multi-stage authentication process (such as OpenID).
   * @return
   * @throws AuthenticationException
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    // UsernamePasswordAuthenticationFilter#attemptAuthentication 내용 복사
    String username = obtainUsername(request);
    username = (username != null) ? username.trim() : "";
    String password = obtainPassword(request);
    password = (password != null) ? password : "";
    String type = request.getParameter("type");
    if (type == null || !type.equals("teacher")) {
      // student
      StudentAuthenticationToken token = StudentAuthenticationToken.builder()
          .principal(Student.builder().id(username).build())
          .credentials(password)
          .build();
      return this.getAuthenticationManager().authenticate(token);
    } else {
      // teacher
      TeacherAuthenticationToken token = TeacherAuthenticationToken.builder()
          .principal(Teacher.builder().id(username).build())
          .credentials(password)
          .build();
      return this.getAuthenticationManager().authenticate(token);
    }


  }
}
