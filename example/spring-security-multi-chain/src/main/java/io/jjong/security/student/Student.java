/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/30
 */

package io.jjong.security.student;

import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
public class Student {
  private String id;
  private String username;
  private Set<GrantedAuthority> role;
  private String teacherId;

  @Builder
  public Student(String id, String username, Set<GrantedAuthority> role, String teacherId) {
    this.id = id;
    this.username = username;
    this.role = role;
    this.teacherId = teacherId;
  }
}
