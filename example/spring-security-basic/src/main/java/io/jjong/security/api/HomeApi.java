/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/21
 */

package io.jjong.security.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2022/08/21. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
@RestController
public class HomeApi {

  @GetMapping("/")
  public String index() {
    return "hello";
  }

  @GetMapping("/auth")
  public Authentication auth() {
    return SecurityContextHolder.getContext()
        .getAuthentication();
  }

  @PreAuthorize("hasAnyAuthority('ROLE_USER')")
  @GetMapping("/user")
  public SecurityMessage user() {
    return new SecurityMessage(SecurityContextHolder.getContext()
        .getAuthentication(), "User info");
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
  public SecurityMessage admin() {
    return new SecurityMessage(SecurityContextHolder.getContext()
        .getAuthentication(), "Admin info");
  }

}
