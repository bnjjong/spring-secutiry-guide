package io.jjong.security;

import io.jjong.security.dao.UserRepository;
import io.jjong.security.model.User;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
@Component
@Profile({"local", "dev"})
@RequiredArgsConstructor
@Slf4j
public class InitData {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() throws Exception {
    User user1 = new User("jongsang", passwordEncoder.encode("12345"), "ADMIN");
    User user2 = new User("henry", passwordEncoder.encode("12345"), "OPERATOR");

    userRepository.save(user1);
    userRepository.save(user2);
  }
}
