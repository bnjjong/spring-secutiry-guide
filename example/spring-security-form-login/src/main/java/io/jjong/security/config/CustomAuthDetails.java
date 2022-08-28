/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/28
 */

package io.jjong.security.config;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

/**
 * create on 2022/08/28. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
@Component
public class CustomAuthDetails implements AuthenticationDetailsSource<HttpServletRequest, RequestCustomInfo> {

  @Override
  public RequestCustomInfo buildDetails(HttpServletRequest request) {
    return RequestCustomInfo.builder()
        .remoteIp(request.getRemoteAddr())
        .sessionId(request.getSession().getId())
        .loginTime(LocalDateTime.now())
        .build();
  }
}
