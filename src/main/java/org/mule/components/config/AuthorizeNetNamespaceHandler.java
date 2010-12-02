/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.components.config;

import org.mule.components.AuthorizeNet;
import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;

public class AuthorizeNetNamespaceHandler extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        registerPojo("config", AuthorizeNet.class);
    }
}