package io.jjong.security.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
//@Entity
//@Getter
//@ToString
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLegacy // implements UserDetails
{
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private Long id;
//  private String username;
//  private String password;
//  private String authority;
//
//
//  public UserLegacy(String username, String password, String authority) {
//    this.username = username;
//    this.password = password;
//    this.authority = authority;
//  }
//
//  @Override
//  public Collection<? extends GrantedAuthority> getAuthorities() {
//    return List.of(() -> this.authority);
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return true;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return true;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return true;
//  }
}
