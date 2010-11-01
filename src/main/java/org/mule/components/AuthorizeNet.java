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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.mule.components.model.Response;

import java.math.BigDecimal;

public class AuthorizeNet
{

    public static final String DEFAULT_X_DELIM_CHAR = "|";

    public static final String TEST_GATEWAY = "https://test.authorize.net/gateway/transact.dll";
    public static final String GATEWAY = "https://secure.authorize.net/gateway/transact.dll";

    private String merchantLogin;
    private String merchantTransactionKey;
    private String gatewayURL;
    private Client client;

    public AuthorizeNet(String merchantLogin, String merchantTransactionKey)
    {
        this(merchantLogin, merchantTransactionKey, false);
    }

    public AuthorizeNet(String merchantLogin, String merchantTransactionKey, Boolean testMode)
    {
        this.merchantLogin = merchantLogin;
        this.merchantTransactionKey = merchantTransactionKey;
        client = Client.create();

        if (testMode)
        {
            gatewayURL = TEST_GATEWAY;
        } else
        {
            gatewayURL = GATEWAY;
        }

    }

    protected MultivaluedMapImpl getBaseMap()
    {
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("x_login", merchantLogin);
        formData.add("x_tran_key", merchantTransactionKey);
        formData.add("x_delim_char", "|");
        formData.add("x_delim_data", "TRUE");
        formData.add("x_relay_response", "FALSE");

        return formData;
    }

    public Response authorizationAndCapture(BigDecimal amount, String cardNumber, String expDate)
    {
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_amount", amount);
        formData.add("x_exp_date", expDate);
        formData.add("x_card_num", cardNumber);
        formData.add("x_type", "AUTH_CAPTURE");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }

    public Response authorizationOnly(BigDecimal amount, String cardNumber, String expDate)
    {
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_amount", amount);
        formData.add("x_exp_date", expDate);
        formData.add("x_card_num", cardNumber);
        formData.add("x_type", "AUTH_ONLY");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }

    public Response priorAuthorizationAndCapture(String transactionId, BigDecimal amount)
    {
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_amount", amount);
        formData.add("x_trans_id", transactionId);
        formData.add("x_type", "PRIOR_AUTH_CAPTURE");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }

    private WebResource.Builder getGatewayResource()
    {
        return this.client
                .resource(gatewayURL)
                .type("application/x-www-form-urlencoded");
    }

    public Response captureOnly(String authenticationCode, BigDecimal amount, String cardNumber, String expDate)
    {
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_exp_date", expDate);
        formData.add("x_card_num", cardNumber);
        formData.add("x_amount", amount);
        formData.add("x_type", "CAPTURE_ONLY");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }

    public Response credit(BigDecimal amount, String cardNumber, String expDate, String transactionId)
    {
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_exp_date", expDate);
        formData.add("x_card_num", cardNumber);
        formData.add("x_amount", amount);
        formData.add("x_trans_id", transactionId);
        formData.add("x_type", "CREDIT");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }

    public Response voidTransaction(String transactionId)
    {
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_trans_id", transactionId);
        formData.add("x_type", "VOID");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }
}