<!--
SPDX-FileCopyrightText: 2022 Zextras <https://www.zextras.com>

SPDX-License-Identifier: AGPL-3.0-only
-->

<div align="center">
  <h1>Carbonio User Management SDK 🚀 </h1>
</div>

<div align="center">

Official SDK for Zextras Carbonio User Management service

[![Contributors][contributors-badge]][contributors]
[![Activity][activity-badge]][activity]
[![License][license-badge]](COPYING)
[![Project][project-badge]][project]
[![Twitter][twitter-badge]][twitter]

</div>

## Dependency installation 🏁

```xml
<dependency>
    <groupId>com.zextras.carbonio.user-management</groupId>
    <artifactId>carbonio-user-management-sdk</artifactId>
    <version>0.3.0</version>
</dependency>
```

## Usage 📈

```java
package com.zextras.carbonio.usermanagement;

import com.zextras.carbonio.usermanagement.entities.UserId;
import com.zextras.carbonio.usermanagement.entities.UserInfo;
import java.util.Optional;
import java.util.UUID;

public class App {

  public static void main(String[] args) throws Exception {
    Optional<UserId> userId = UserManagementClient
      .atURL("http://127.78.0.1:20000")
      .validateUserToken("fake-carbonio-user-token")
      .map(Optional::of)
      .getOrElseThrow(failure -> new Exception(failure));

    String cookie = "ZM_AUTH_TOKEN=fake-carbonio-user-token";

    Optional<UserInfo> userInfoByUUID = UserManagementClient
      .atURL("http", "127.78.0.1", 20000)
      .getUserByUUID(cookie, UUID.fromString("fake-user-UUID"))
      .map(Optional::of)
      .getOrElseThrow(failure -> new Exception(failure));

    Optional<UserInfo> userInfoByEmail = UserManagementClient
      .atURL("http://127.78.0.1:20000")
      .getUserByEmail(cookie, "user@example.com")
      .map(Optional::of)
      .getOrElseThrow(failure -> new Exception(failure));
  }
}
```

## License

Official SDK for Zextras Carbonio User Management service.

Released under the AGPL-3.0-only license as specified here: [COPYING](COPYING).

Copyright (C) 2022 Zextras <https://www.zextras.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

See [COPYING](COPYING) file for the project license details

See [THIRDPARTIES](THIRDPARTIES) file for other licenses details

### Copyright notice

All non-software material (such as, for example, names, images, logos, sounds) is owned by Zextras
s.r.l. and is licensed under [CC-BY-NC-SA](https://creativecommons.org/licenses/by-nc-sa/4.0/).

Where not specified, all source files owned by Zextras s.r.l. are licensed under AGPL-3.0-only


[contributors-badge]: https://img.shields.io/github/contributors/zextras/carbonio-user-management-sdk "Contributors"

[contributors]: https://github.com/zextras/carbonio-user-management-sdk/graphs/contributors "Contributors"

[activity-badge]: https://img.shields.io/github/commit-activity/m/zextras/carbonio-user-management-sdk "Activity"

[activity]: https://github.com/zextras/carbonio-user-management-sdk/pulse "Activity"

[license-badge]: https://img.shields.io/badge/license-AGPL-blue.svg

[project-badge]: https://img.shields.io/badge/project-carbonio-informational "Project Carbonio"

[project]: https://www.zextras.com/carbonio/ "Project Carbonio"

[twitter-badge]: https://img.shields.io/twitter/follow/zextras?style=social&logo=twitter "Follow on Twitter"

[twitter]: https://twitter.com/intent/follow?screen_name=zextras "Follow Zextras on Twitter"
