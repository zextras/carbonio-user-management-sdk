// SPDX-FileCopyrightText: 2025 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement;

import com.zextras.carbonio.usermanagement.support.DockerComposeExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(DockerComposeExtension.class)
public class UserManagementClientIT {

  private static final String BASE_URL = "http://127.0.0.1:20001";
  private static UserManagementClient client;

  @BeforeAll
  static void setUpAll() {
    client = UserManagementClient.atURL(BASE_URL);
  }

  @Test
  void healthCheck() {
    assertTrue(client.healthCheck());
  }

  @Test
  void validateUserTokenWithInvalidCarbonioToken() {
    var result = client.validateUserToken("invalid-token");
    assertFalse(result.isSuccess());
  }

  @Test
  void getUserMyselfWithInvalidCookie() {
    var result = client.getUserMyself("invalid-cookie");
    assertFalse(result.isSuccess());
  }

  @Test
  void getUserByEmailWithInvalidCookie() {
    var result = client.getUserByEmail("invalid-cookie", "user@carbonio.localhost");
    assertFalse(result.isSuccess());
  }

}
