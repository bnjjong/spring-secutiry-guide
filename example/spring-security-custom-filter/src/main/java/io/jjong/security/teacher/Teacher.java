/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/09/04
 */

package io.jjong.security.teacher;

import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

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
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Teacher {

  private String id;
  private String username;
  private Set<GrantedAuthority> role;

  @Builder
  public Teacher(String id, String username, Set<GrantedAuthority> role) {
    this.id = id;
    this.username = username;
    this.role = role;
  }
}
