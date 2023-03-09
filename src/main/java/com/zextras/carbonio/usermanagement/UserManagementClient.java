// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zextras.carbonio.usermanagement.entities.UserId;
import com.zextras.carbonio.usermanagement.entities.UserInfo;
import com.zextras.carbonio.usermanagement.exceptions.InternalServerError;
import com.zextras.carbonio.usermanagement.exceptions.UnAuthorized;
import com.zextras.carbonio.usermanagement.exceptions.UserNotFound;
import io.vavr.control.Try;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * An HTTP client that allows to execute HTTP requests to an UserManagement service. This is the
 * main and the only class needed to call the UserManagement APIs.
 */
public class UserManagementClient {

  private final String validateTokenEndpoint   = "/auth/token/";
  private final String getUsersEndpoint        = "/users/";
  private final String getUsersByIdEndpoint    = "/users/id/";
  private final String getUsersByEmailEndpoint = "/users/email/";
  private final String healthEndpoint          = "/health/";
  private final String userManagementURL;

  UserManagementClient(String userManagementURL) {
    this.userManagementURL = userManagementURL;
  }

  /**
   * Creates a new instance of the {@link UserManagementClient}.
   *
   * @param url is a {@link String} representing the user management url used to communicate with
   * the UserManagement service. The expected url form must be as follows:
   * <code>protocol://ip:port</code> (for example <code>http://127.0.0.1:8080</code>).
   *
   * @return an instance of the {@link UserManagementClient}.
   */
  public static UserManagementClient atURL(String url) {
    return new UserManagementClient(url);
  }

  /**
   * Creates a new instance of the {@link UserManagementClient}.
   *
   * @param protocol is a {@link String} representing the protocol used to communicate with the
   * UserManagement service (for example: <code>http</code>).
   * @param domain is a {@link String} representing the domain used to communicate with the
   * UserManagement service (for example: <code>127.0.0.1</code>).
   * @param port is an {@link Integer} representing the port used to communicate with the
   * UserManagement service.
   *
   * @return an instance of the {@link UserManagementClient}.
   */
  public static UserManagementClient atURL(
    String protocol,
    String domain,
    Integer port
  ) {
    return new UserManagementClient(protocol + "://" + domain + ":" + port);
  }

  /**
   * Allows to validate the token of a Carbonio user.
   *
   * @param carbonioUserToken is a {@link String} representing a <code>ZM_AUTH_TOKEN</code> of a
   * Carbonio user.
   *
   * @return a {@link Try#success} containing an {@link UserId} representing the related Carbonio
   * user id if the token is valid. <p />It returns a {@link Try#failure} containing an {@link
   * UnAuthorized} throwable if the token is not valid. <p />It returns a {@link Try#failure}
   * containing an {@link InternalServerError} throwable if something goes wrong during the API
   * call.
   */
  public Try<UserId> validateUserToken(String carbonioUserToken) {

    CloseableHttpClient httpClient = HttpClients.createMinimal();

    HttpGet request = new HttpGet(userManagementURL + validateTokenEndpoint + carbonioUserToken);

    try {
      CloseableHttpResponse response = httpClient.execute(request);

      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        String bodyResponse = IOUtils.toString(
          response.getEntity().getContent(),
          StandardCharsets.UTF_8
        );

        return Try.success(new ObjectMapper().readValue(bodyResponse, UserId.class));
      }

      return Try.failure(new UnAuthorized());
    } catch (IOException exception) {
      return Try.failure(new InternalServerError(exception));
    }
  }

  /**
   * Allows to retrieve a list of Carbonio users by {@link UUID} if they exist.
   *
   * @param cookie    is a {@link String} representing the requester cookie. (It is necessary to
   *                  have only the ZM_AUTH_TOKEN cookie).
   * @param usersUUID is a {@link List} of {@link UUID}s of the Carbonio users to retrieve.
   * @return a {@link Try#success} containing a {@link List} of {@link UserInfo} representing
   * the related Carbonio users if they exist. <p />It returns a {@link Try#failure} containing
   * an {@link UserNotFound} throwable the cookie is not valid. <p />It returns a
   * {@link Try#failure} containing an {@link InternalServerError} throwable if something goes
   * wrong during the API call.
   */
  public Try<List<UserInfo>> getUsers(
    String cookie,
    List<UUID> usersUUID
  ) {
    CloseableHttpClient httpClient = HttpClients.createMinimal();

    AtomicReference<String> getRequest = new AtomicReference<>(userManagementURL + getUsersEndpoint + "?userIds=");
    usersUUID.forEach(userUUID -> getRequest.updateAndGet(v -> v + userUUID));
    HttpGet request = new HttpGet(getRequest.get());
    request.setHeader("Cookie", cookie);

    try {
      CloseableHttpResponse response = httpClient.execute(request);

      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        String bodyResponse = IOUtils.toString(
          response.getEntity().getContent(),
          StandardCharsets.UTF_8
        );

        return Try.success(new ObjectMapper().readValue(bodyResponse, new TypeReference<>() {}));
      }

      return Try.failure(
        new UserNotFound("The cookie: \"" + cookie + "\" is not valid")
      );
    } catch (IOException exception) {
      return Try.failure(new InternalServerError(exception));
    }
  }

  /**
   * Allows to retrieve a specific Carbonio user by {@link UUID} if it exists.
   *
   * @param cookie is a {@link String} representing the requester cookie. (It is necessary to have
   * only the ZM_AUTH_TOKEN cookie).
   * @param userUUID is a {@link UUID} of the Carbonio user to retrieve.
   *
   * @return a {@link Try#success} containing an {@link UserInfo} representing the related Carbonio
   * user if the user exists. <p />It returns a {@link Try#failure} containing an {@link
   * UserNotFound} throwable if the user does not exist or the cookie is not valid. <p />It returns
   * a {@link Try#failure} containing an {@link InternalServerError} throwable if something goes
   * wrong during the API call.
   */
  public Try<UserInfo> getUserByUUID(
    String cookie,
    UUID userUUID
  ) {
    CloseableHttpClient httpClient = HttpClients.createMinimal();

    HttpGet request = new HttpGet(userManagementURL + getUsersByIdEndpoint + userUUID);
    request.setHeader("Cookie", cookie);

    try {
      CloseableHttpResponse response = httpClient.execute(request);

      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        String bodyResponse = IOUtils.toString(
          response.getEntity().getContent(),
          StandardCharsets.UTF_8
        );

        return Try.success(new ObjectMapper().readValue(bodyResponse, UserInfo.class));
      }

      return Try.failure(
        new UserNotFound("The user with uuid: \"" + userUUID + "\" was not found")
      );
    } catch (IOException exception) {
      return Try.failure(new InternalServerError(exception));
    }
  }

  /**
   * Allows to retrieve a specific Carbonio user by email if it exists.
   *
   * @param cookie is a {@link String} representing the requester cookie. (It is necessary to have
   * only the ZM_AUTH_TOKEN cookie).
   * @param userEmail is a {@link String} representing the email of the Carbonio user to retrieve.
   *
   * @return a {@link Try#success} containing an {@link UserInfo} representing the related Carbonio
   * user if the user exists. <p />It returns a {@link Try#failure} containing an {@link
   * UserNotFound} throwable if the user does not exist or the cookie is not valid. <p />It returns
   * a {@link Try#failure} containing an {@link InternalServerError} throwable if something goes
   * wrong during the API call.
   */
  public Try<UserInfo> getUserByEmail(
    String cookie,
    String userEmail
  ) {
    CloseableHttpClient httpClient = HttpClients.createMinimal();

    HttpGet request = new HttpGet(userManagementURL + getUsersByEmailEndpoint + userEmail);
    request.setHeader("Cookie", cookie);

    try {
      CloseableHttpResponse response = httpClient.execute(request);

      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        String bodyResponse = IOUtils.toString(
          response.getEntity().getContent(),
          StandardCharsets.UTF_8
        );

        return Try.success(new ObjectMapper().readValue(bodyResponse, UserInfo.class));
      }

      return Try.failure(
        new UserNotFound("The user with email: \"" + userEmail + "\" was not found")
      );
    } catch (IOException exception) {
      return Try.failure(new InternalServerError(exception));
    }
  }

  /**
   * Allows to check the health of the User Management service.
   *
   * @return a <code>boolean</code> that is true if the service is up & running, false otherwise.
   */
  public boolean healthCheck() {
    CloseableHttpClient httpClient = HttpClients.createMinimal();

    HttpGet request = new HttpGet(userManagementURL + healthEndpoint);

    try {
      CloseableHttpResponse response = httpClient.execute(request);
      return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    } catch (IOException exception) {
      return false;
    }
  }
}
