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
import org.mule.api.lifecycle.Initialisable;
import org.mule.components.model.Response;

import java.math.BigDecimal;

public class AuthorizeNet implements Initialisable
{

    public static final String DEFAULT_X_DELIM_CHAR = "|";

    public static final String TEST_GATEWAY = "https://test.authorize.net/gateway/transact.dll";
    public static final String GATEWAY = "https://secure.authorize.net/gateway/transact.dll";

    public String merchantLogin;
    public String merchantTransactionKey;
    public Boolean testMode;

    private String gatewayURL;
    private Client client;

    public String getMerchantLogin()
    {
        return merchantLogin;
    }

    public void setMerchantLogin(String merchantLogin)
    {
        this.merchantLogin = merchantLogin;
    }

    public String getMerchantTransactionKey()
    {
        return merchantTransactionKey;
    }

    public void setMerchantTransactionKey(String merchantTransactionKey)
    {
        this.merchantTransactionKey = merchantTransactionKey;
    }

    public Boolean getTestMode()
    {
        return testMode;
    }

    public void setTestMode(Boolean testMode)
    {
        this.testMode = testMode;
    }

    public AuthorizeNet()
    {
        testMode = false;
    }

    public void initialise()
    {
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

    /**
     * This is the most common type of credit card transaction and is the default payment gateway transaction type.
     * The amount is sent for authorization, and if approved, is automatically submitted for settlement.
     *
     * @param amount     Up to 15 digits with a decimal point (no dollar symbol) Ex. 8.95 This is the total amount
     *                   and must include tax, shipping, and any other charges. The amount can either be hard-coded
     *                   or posted to a script.
     * @param cardNumber Between 13 and 16 digits without spaces. When x_type=CREDIT, only the last four digits are
     *                   required. This is sensitive cardholder information and must be stored securely and in
     *                   accordance with the Payment Card Industry (PCI) Data Security Standard. For more information
     *                   about PCI, please see the Developer Security Best Practices White Paper at
     *                   http://www.authorize.net/files/developerbestpractices.pdf.
     * @param expDate    This is sensitive cardholder information and must be stored securely and in accordance with
     *                   the Payment Card Industry (PCI) Data Security Standard.
     * @return Response object which contains the response code from calling the service and any errors.
     */
    public Response authorizationAndCapture(BigDecimal amount, String cardNumber, String expDate)
    {
        //init();
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

    /**
     * This transaction type is sent for authorization only. The transaction will not be sent for settlement until
     * the credit card transaction type Prior Authorization and Capture (see definition below) is submitted, or the
     * transaction is submitted for capture manually in the Merchant Interface. For more information about capturing
     * Authorization Only transactions in the Merchant Interface, see the Merchant Integration Guide
     * at http://www.authorize.net/support/merchant/.
     * <p/>
     * If action for the Authorization Only transaction is not taken on the payment gateway within 30 days, the
     * authorization expires and is no longer available for capture. A new Authorization Only transaction would then
     * have to be submitted to obtain a new authorization code.
     * <p/>
     * Merchants can submit Authorization Only transactions if they want to verify the availability of funds on the
     * customer's credit card before finalizing the transaction. This transaction type can also be submitted if the
     * merchant does not currently have an item in stock or wants to review orders before shipping goods.
     *
     * @param amount     Up to 15 digits with a decimal point (no dollar symbol) Ex. 8.95 This is the total amount
     *                   and must include tax, shipping, and any other charges. The amount can either be hard-coded
     *                   or posted to a script.
     * @param cardNumber Between 13 and 16 digits without spaces. When x_type=CREDIT, only the last four digits are
     *                   required. This is sensitive cardholder information and must be stored securely and in
     *                   accordance with the Payment Card Industry (PCI) Data Security Standard. For more information
     *                   about PCI, please see the Developer Security Best Practices White Paper at
     *                   http://www.authorize.net/files/developerbestpractices.pdf.
     * @param expDate    This is sensitive cardholder information and must be stored securely and in accordance with
     *                   the Payment Card Industry (PCI) Data Security Standard.
     * @return Response Object
     */
    public Response authorizationOnly(BigDecimal amount, String cardNumber, String expDate)
    {
        //init();
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

    /**
     * This transaction type is used to complete an Authorization Only transaction that was successfully authorized
     * through the payment gateway.
     *
     * @param transactionId The payment gateway assigned transaction ID of an original transaction
     * @param amount        Up to 15 digits with a decimal point (no dollar symbol) Ex. 8.95 This is the total amount
     *                      and must include tax, shipping, and any other charges. The amount can either be hard-coded
     *                      or posted to a script.
     * @return Response Object
     */
    public Response priorAuthorizationAndCapture(String transactionId, BigDecimal amount)
    {
        //init();
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_amount", amount);
        formData.add("x_trans_id", transactionId);
        formData.add("x_type", "PRIOR_AUTH_CAPTURE");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }

    /**
     * This transaction type is used to complete a previously authorized transaction that was not originally
     * submitted through the payment gateway or that requires voice authorization.
     *
     * @param authenticationCode The authorization code of an original transaction not authorized on the payment gateway
     * @param amount             Up to 15 digits with a decimal point (no dollar symbol) Ex. 8.95 This is the total amount
     *                           and must include tax, shipping, and any other charges. The amount can either be hard-coded
     *                           or posted to a script.
     * @param cardNumber         Between 13 and 16 digits without spaces. When x_type=CREDIT, only the last four digits are
     *                           required. This is sensitive cardholder information and must be stored securely and in
     *                           accordance with the Payment Card Industry (PCI) Data Security Standard. For more information
     *                           about PCI, please see the Developer Security Best Practices White Paper at
     *                           http://www.authorize.net/files/developerbestpractices.pdf.
     * @param expDate            This is sensitive cardholder information and must be stored securely and in accordance with
     *                           the Payment Card Industry (PCI) Data Security Standard.
     * @return Response Object
     */
    public Response captureOnly(String authenticationCode, BigDecimal amount, String cardNumber, String expDate)
    {
        //init();
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_exp_date", expDate);
        formData.add("x_card_num", cardNumber);
        formData.add("x_amount", amount);
        formData.add("x_auth_code", authenticationCode);
        formData.add("x_type", "CAPTURE_ONLY");

        String response = getGatewayResource()
                .post(ClientResponse.class, formData)
                .getEntity(String.class);

        return new Response(response);
    }

    /**
     * This transaction type is used to refund a customer for a transaction that was originally processed and
     * successfully settled through the payment gateway.
     *
     * @param amount        Up to 15 digits with a decimal point (no dollar symbol) Ex. 8.95 This is the total amount
     *                      and must include tax, shipping, and any other charges. The amount can either be hard-coded
     *                      or posted to a script.
     * @param cardNumber    Between 13 and 16 digits without spaces. When x_type=CREDIT, only the last four digits are
     *                      required. This is sensitive cardholder information and must be stored securely and in
     *                      accordance with the Payment Card Industry (PCI) Data Security Standard. For more information
     *                      about PCI, please see the Developer Security Best Practices White Paper at
     *                      http://www.authorize.net/files/developerbestpractices.pdf.
     * @param expDate       This is sensitive cardholder information and must be stored securely and in accordance with
     *                      the Payment Card Industry (PCI) Data Security Standard.
     * @param transactionId The payment gateway-assigned transaction ID of an original transaction
     * @return Response Object
     */
    public Response credit(BigDecimal amount, String cardNumber, String expDate, String transactionId)
    {
        //init();
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

    /**
     * This transaction type can be used to cancel either an original transaction that is not yet settled, or an entire
     * order composed of more than one transaction.  A void prevents the transaction or order from being sent for
     * settlement. A Void can be submitted against any other transaction type.
     *
     * @param transactionId The payment gateway-assigned transaction ID of an original transaction
     * @return Response Object
     */
    public Response voidTransaction(String transactionId)
    {
        //init();
        MultivaluedMapImpl formData = getBaseMap();
        formData.add("x_trans_id", transactionId);
        formData.add("x_type", "VOID");

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

}