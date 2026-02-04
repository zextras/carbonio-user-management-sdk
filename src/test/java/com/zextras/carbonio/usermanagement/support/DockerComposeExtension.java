package com.zextras.carbonio.usermanagement.support;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DockerComposeExtension implements BeforeAllCallback, AfterAllCallback {

    private static ComposeContainer environment;

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception {
        environment = new ComposeContainer(
            DockerImageName.parse("docker:cli"),
            new File("docker/minimal/docker-compose.yaml")
        );
        environment.start();
        TimeUnit.SECONDS.sleep(30);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        environment.stop();
    }
}

