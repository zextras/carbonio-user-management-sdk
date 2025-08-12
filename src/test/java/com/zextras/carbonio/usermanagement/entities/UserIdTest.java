// SPDX-FileCopyrightText: 2025 Zextras <https://www.zextras.com>
//
// SPDX-License-Identifier: AGPL-3.0-only

package com.zextras.carbonio.usermanagement.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserIdTest {

    @Test
    void setterShouldUpdateValue() {
        UserId id = new UserId("test-123");
        assertEquals("test-123", id.getUserId());
        id.setUserId("test-456");
        assertEquals("test-456", id.getUserId());
    }
}
