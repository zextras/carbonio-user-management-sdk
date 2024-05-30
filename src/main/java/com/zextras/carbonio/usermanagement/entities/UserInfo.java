// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zextras.carbonio.usermanagement.enumerations.UserStatus;
import com.zextras.carbonio.usermanagement.enumerations.UserType;

public class UserInfo {

  private UserId id;
  private String email;
  private String fullName;
  private String domain;
  @JsonProperty("userStatus")
  private UserStatus status;
  @JsonProperty("userType")
  private UserType type;

  public UserInfo() {}

  public UserInfo(
      UserId id, String email, String fullName, String domain, UserStatus userStatus, UserType userType) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.domain = domain;
    this.status = userStatus;
    this.type = userType;
  }

  public UserId getId() {
    return id;
  }

  public void setId(UserId id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus userStatus) {
    this.status = userStatus;
  }

  public UserType getType() {
    return type;
  }

  public void setType(UserType userType) {
    this.type = userType;
  }
}
