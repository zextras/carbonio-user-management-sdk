// SPDX-FileCopyrightText: 2025 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

    @Test
    void constructorAndGetterShouldWork() {
        UserId id = new UserId("abc-123");
        assertEquals("abc-123", id.getUserId());
    }

    @Test
    void setterShouldUpdateValue() {
        UserId id = new UserId();
        assertNull(id.getUserId());
        id.setUserId("xyz");
        assertEquals("xyz", id.getUserId());
    }
}
