// SPDX-FileCopyrightText: 2023 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

import com.zextras.carbonio.usermanagement.enumerations.UserStatus;
import com.zextras.carbonio.usermanagement.enumerations.UserType;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/** Contains all the information (for now only a subset of them) of a Carbonio user. */
public class UserMyself {

  private UserId id;
  private String email;
  private String fullName;
  private String domain;
  private UserStatus status;
  private UserType type;
  private Locale locale;
  private Map<String, String> carbonioAttributes;

  public UserMyself(){ }

  public UserMyself(
      UserId id, String email, String fullName, String domain, UserStatus status, Locale locale, UserType type, Map<String, String> carbonioAttributes) {
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.domain = domain;
    this.status = status;
    this.locale = locale;
    this.type = type;
    this.carbonioAttributes = carbonioAttributes;
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

  public void setType(UserType type) {
    this.type = type;
  }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public Map<String, String> getCarbonioAttributes() {
    return carbonioAttributes;
  }

  public void setCarbonioAttributes(Map<String, String> carbonioAttributes) {
    this.carbonioAttributes = carbonioAttributes;
  }
}
