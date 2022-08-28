/*
 * Created By dogfootmaster@gmail.com on 2022
 * This program is free software
 *
 * @author <a href=“mailto:dogfootmaster@gmail.com“>Jongsang Han</a>
 * @since 2022/08/28
 */

package io.jjong.security.config;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCustomInfo {
  private String remoteIp;
  private String sessionId;
  private LocalDateTime loginTime;

  @Builder
  public RequestCustomInfo(String remoteIp, String sessionId, LocalDateTime loginTime) {
    this.remoteIp = remoteIp;
    this.sessionId = sessionId;
    this.loginTime = loginTime;
  }
}
