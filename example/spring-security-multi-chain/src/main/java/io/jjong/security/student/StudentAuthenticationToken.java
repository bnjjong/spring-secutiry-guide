/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/30
 */

package io.jjong.security.student;

import java.util.Collection;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * create on 2022/08/30. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 * <p> {@link } and {@link }관련 클래스 </p>
 *
 * @author Jongsang Han
 * @version 1.0
 * @see
 * @since 1.0
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentAuthenticationToken implements Authentication {
  private Student principal;
  private String credentials;
  private String details;
  @Setter
  private boolean authenticated;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return principal == null ? new HashSet<>() : principal.getRole();
  }

  @Override
  public String getName() {
    return principal == null ? "" : principal.getUsername();
  }

  @Builder
  public StudentAuthenticationToken(Student principal, String credentials, String details,
      boolean authenticated) {
    this.principal = principal;
    this.credentials = credentials;
    this.details = details;
    this.authenticated = authenticated;
  }
}
