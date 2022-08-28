/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/28
 */

package io.jjong.security.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
public class HomeController {

  @GetMapping("/")
  public String main() {
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "loginForm";
  }

  @GetMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return "loginForm";
  }

  @GetMapping("/access-denied")
  public String accessDenied() {
    return "AccessDenied";
  }

  @PreAuthorize("hasAnyAuthority('ROLE_USER')")
  @GetMapping("/user-page")
  public String userPage() {
    return "UserPage";
  }

  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
  @GetMapping("/admin-page")
  public String adminPage() {
    return "AdminPage";
  }

  @GetMapping("/auth")
  @ResponseBody
  public Authentication auth() {
   return SecurityContextHolder.getContext().getAuthentication();
  }

}
