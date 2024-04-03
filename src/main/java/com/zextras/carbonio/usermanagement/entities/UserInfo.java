// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

import com.zextras.carbonio.usermanagement.enumerations.Status;

public class UserInfo {

  private UserId id;
  private String email;
  private String fullName;
  private String domain;
  private Status status;

  public UserInfo() {}

  public UserInfo(UserId id, String email, String fullName, String domain, Status status) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.domain = domain;
    this.status = status;
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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
