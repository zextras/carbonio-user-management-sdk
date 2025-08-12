// SPDX-FileCopyrightText: 2025 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class UserManagementClientIT {

  // We'll map container's 8080 to host 20000 so tests can target http://127.78.0.1:20000
  @Container
  @SuppressWarnings("resource")
  private static final GenericContainer<?> WIREMOCK = new GenericContainer<>(
      DockerImageName.parse("wiremock/wiremock:2.35.0")
  )
      .withExposedPorts(8080)
      .withCommand("--port", "8080")
      .withCreateContainerCmdModifier(cmd -> {
          com.github.dockerjava.api.model.Ports portBindings = new com.github.dockerjava.api.model.Ports();
          portBindings.bind(
              com.github.dockerjava.api.model.ExposedPort.tcp(8080),
              com.github.dockerjava.api.model.Ports.Binding.bindPort(20000)
          );
          com.github.dockerjava.api.model.HostConfig hostConfig = cmd.getHostConfig();
          if (hostConfig == null) {
            hostConfig = new com.github.dockerjava.api.model.HostConfig();
          }
          hostConfig.withPortBindings(portBindings);
          cmd.withHostConfig(hostConfig);
      })
          ;

  private static final String BASE_URL = "http://127.78.0.1:20000";
  private static UserManagementClient client;

  @BeforeAll
  static void setUpAll() {
    assertTrue(WIREMOCK.isRunning(), "WireMock container should be running");
    WireMock.configureFor("127.78.0.1", 20000);
    client = UserManagementClient.atURL(BASE_URL);
  }

  @Test
  void healthCheck_true() {
    stubFor(get(urlEqualTo("/health/"))
            .willReturn(aResponse().withStatus(200)));

    assertTrue(client.healthCheck());
  }
}
