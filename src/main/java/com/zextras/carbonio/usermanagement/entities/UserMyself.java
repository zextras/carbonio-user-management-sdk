// SPDX-FileCopyrightText: 2023 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zextras.carbonio.usermanagement.enumerations.UserType;

import java.util.Locale;

/** Contains all the information (for now only a subset of them) of a Carbonio user. */
public class UserMyself {

  private UserId id;
  private String email;
  private String fullName;
  private String domain;
  private Locale locale;
  @JsonProperty("userType")
  private UserType type;

  public UserMyself() {}

  public UserMyself(
      UserId id, String email, String fullName, String domain, Locale locale, UserType userType) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.domain = domain;
    this.locale = locale;
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

  public Locale getLocale() {
    return locale;
  }

  public UserType getType() {
    return type;
  }

  public void setType(UserType userType) {
    this.type = userType;
  }
}
