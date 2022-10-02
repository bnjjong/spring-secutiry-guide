package io.jjong.security.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@Slf4j
@RequiredArgsConstructor
public class SampleApi {

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

  @GetMapping("/auth")
  public Authentication auth() {
    return SecurityContextHolder.getContext()
        .getAuthentication();
  }
}
