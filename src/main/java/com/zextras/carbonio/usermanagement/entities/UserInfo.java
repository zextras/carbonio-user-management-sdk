// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

import com.zextras.carbonio.usermanagement.enumerations.UserStatus;
import com.zextras.carbonio.usermanagement.enumerations.UserType;

public class UserInfo {

  private UserId id;
  private String email;
  private String fullName;
  private String domain;
  private UserStatus userStatus;
  private UserType userType;

  public UserInfo() {}

  public UserInfo(
      UserId id, String email, String fullName, String domain, UserStatus userStatus, UserType userType) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.domain = domain;
    this.userStatus = userStatus;
    this.userType = userType;
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
    return userStatus;
  }

  public void setStatus(UserStatus userStatus) {
    this.userStatus = userStatus;
  }

  public UserType getType() {
    return userType;
  }

  public void setType(UserType userType) {
    this.userType = userType;
  }
}
