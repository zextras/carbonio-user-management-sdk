// SPDX-FileCopyrightText: 2025 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@Testcontainers
public class UserManagementClientIT {

//  @Container
//  public ComposeContainer environment = new ComposeContainer(new File("docker-compose.yaml"))
////          .withLocalCompose(true)
//          ;

//  // We'll map container's 8080 to host 20000 so tests can target http://127.78.0.1:20000
//  @Container
//  @SuppressWarnings("resource")
//  private static final GenericContainer<?> WIREMOCK = new GenericContainer<>(
//      DockerImageName.parse("wiremock/wiremock:2.35.0")
//  )
//      .withExposedPorts(8080)
//      .withCommand("--port", "8080")
//      .withCreateContainerCmdModifier(cmd -> {
//          com.github.dockerjava.api.model.Ports portBindings = new com.github.dockerjava.api.model.Ports();
//          portBindings.bind(
//              com.github.dockerjava.api.model.ExposedPort.tcp(8080),
//              com.github.dockerjava.api.model.Ports.Binding.bindPort(20000)
//          );
//          com.github.dockerjava.api.model.HostConfig hostConfig = cmd.getHostConfig();
//          if (hostConfig == null) {
//            hostConfig = new com.github.dockerjava.api.model.HostConfig();
//          }
//          hostConfig.withPortBindings(portBindings);
//          cmd.withHostConfig(hostConfig);
//      })
//          ;

  private static final String BASE_URL = "http://127.0.0.1:20001";
  private static UserManagementClient client;

  @BeforeAll
  static void setUpAll() {
//    assertTrue(WIREMOCK.isRunning(), "WireMock container should be running");
    client = UserManagementClient.atURL(BASE_URL);
  }

  @Test
  void healthCheck_true() {
    assertTrue(client.healthCheck());
  }
}
