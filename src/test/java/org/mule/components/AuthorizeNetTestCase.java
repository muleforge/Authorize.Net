/*
 * $Id: $
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.components;

import org.junit.*;
import org.mule.components.model.Response;
import org.mule.components.model.ResponseCode;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthorizeNetTestCase
{
    AuthorizeNet authorizeNet;
    private Random rand;

    private static final String CREDIT_CARD = "370000000000002";
    private static final String EXP_DATE = "12/12";

    @Before
    public void init()
    {
        authorizeNet = new AuthorizeNet("", "", true);
        rand = new Random()
    }

    @Test
    public void testAuthorizeAndCapture() throws Exception
    {
        Response response = authorizeNet.authorizationAndCapture(new BigDecimal(rand.nextInt(200)), CREDIT_CARD, EXP_DATE);
        assertNotNull(response);
        assertTrue(response.getResponseCode() == ResponseCode.APPROVED);
    }

    @Test
    public void testPriorAuthorizeAndCapture() throws Exception
    {
        Response capture = authorizeNet.authorizationOnly(new BigDecimal(rand.nextInt(200)), CREDIT_CARD, EXP_DATE);
        assertNotNull(capture);
        assertTrue(capture.getResponseCode() == ResponseCode.APPROVED);

        Response authorize = authorizeNet.priorAuthorizationAndCapture(capture.getTransactionId(), new BigDecimal(capture.getAmount()));
        assertNotNull(authorize);
        assertTrue(authorize.getResponseCode() == ResponseCode.APPROVED);
    }
}
