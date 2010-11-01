/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.components.model;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode
{
    APPROVED("1"),
    DECLINED("2"),
    ERROR("3"),
    HELD_FOR_REVIEW("4");

    private final String value;

    private static final Map<String, ResponseCode> lookup = new HashMap<String, ResponseCode>();
    static
    {
        for (ResponseCode rc : ResponseCode.values())
        {
            lookup.put(rc.toString(), rc);
        }
    }

    private ResponseCode(String value)
    {
        this.value = value;
    }

    public static ResponseCode get(String value) {
        return lookup.get(value);
    }

    public String toString()
    {
        return value;
    }
}
