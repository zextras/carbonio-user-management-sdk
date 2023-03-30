// SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

public class UserInfo {

  private UserId id;
  private String email;
  private String fullName;
  private String domain;

  public UserInfo() {}

  public UserInfo(
    UserId id,
    String email,
    String fullName,
    String domain
  ) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.domain = domain;
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
}
