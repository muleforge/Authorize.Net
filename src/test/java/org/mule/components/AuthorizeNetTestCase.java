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
import org.mule.api.MuleMessage;
import org.mule.components.model.Response;
import org.mule.components.model.ResponseCode;
import org.mule.tck.FunctionalTestCase;
import org.mule.module.client.MuleClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AuthorizeNetTestCase extends FunctionalTestCase
{
    private Random rand;

    private static final String CREDIT_CARD = "370000000000002";
    private static final String EXP_DATE = "12/12";

    public AuthorizeNetTestCase()
    {
        String merchantLogin = System.getProperty("merchantLogin");
        String merchantTransactionKey = System.getProperty("merchantTransactionKey");

        if ((merchantLogin == null) || (merchantTransactionKey == null))
        {
            throw new RuntimeException("Invalid configuration. Make sure to set the merchantLogin and merchantTransactionKey on the command line");
        }

        rand = new Random();
    }

    protected AuthorizeNet init()
    {
        AuthorizeNet auth = new AuthorizeNet();

        auth.merchantLogin = System.getProperty("merchantLogin");
        auth.merchantTransactionKey = System.getProperty("merchantTransactionKey");
        auth.testMode = true;

        return auth;
    }

    public void testAuthorizeAndCapture() throws Exception
    {
        AuthorizeNet auth = init();
        auth.initialise();

        Response response = auth.authorizationAndCapture(new BigDecimal(rand.nextInt(200)), CREDIT_CARD, EXP_DATE);
        assertNotNull(response);
        assertTrue(response.getResponseCode() == ResponseCode.APPROVED);
    }

    public void testPriorAuthorizeAndCapture() throws Exception
    {

        AuthorizeNet auth = init();
        auth.initialise();

        Response capture = auth.authorizationOnly(new BigDecimal(rand.nextInt(200)), CREDIT_CARD, EXP_DATE);
        assertNotNull(capture);
        assertTrue(capture.getResponseCode() == ResponseCode.APPROVED);

        Response authorize = auth.priorAuthorizationAndCapture(capture.getTransactionId(), new BigDecimal(capture.getAmount()));
        assertNotNull(authorize);
        assertTrue(authorize.getResponseCode() == ResponseCode.APPROVED);
    }

    @Override
    protected String getConfigResources()
    {
        return "auth-conf.xml";
    }

    public void testAuthorizeAndCaptureConfigFile() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        BigDecimal amount = new BigDecimal(rand.nextInt(200));
        MuleMessage result = client.send("vm://authorizeAndCapture", amount, null);
        assertNotNull(result);

        Response authorize = (Response) result.getPayload();
        assertTrue(authorize.getResponseCode() == ResponseCode.APPROVED);
    }

    public void testAuthorizeAndCaptureTwoStepsConfigFile() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        BigDecimal amount = new BigDecimal(rand.nextInt(200));
        MuleMessage result = client.send("vm://authorizationOnly", amount, null);
        assertNotNull(result);

        Response authorize = (Response) result.getPayload();
        assertTrue(authorize.getResponseCode() == ResponseCode.APPROVED);


        HashMap<String, String> payload = new HashMap<String, String>();
        payload.put("amount", amount.toString());
        payload.put("transactionId", authorize.getTransactionId());
        result = client.send("vm://priorAuthorizationAndCapture", payload, null);
        assertNotNull(result);

        authorize = (Response) result.getPayload();
        assertTrue(authorize.getResponseCode() == ResponseCode.APPROVED);
    }

    public void testConfigError() throws Exception
    {
        MuleClient client = new MuleClient(muleContext);
        BigDecimal amount = new BigDecimal(rand.nextInt(200));
        MuleMessage result = client.send("vm://configError", amount, null);
        assertNotNull(result);

        Response authorize = (Response) result.getPayload();
        assertTrue(authorize.getResponseCode() == ResponseCode.ERROR);
    }
}
