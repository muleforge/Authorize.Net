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

import org.mule.components.AuthorizeNet;


public class Response
{
    private String responseString;
    private ResponseCode responseCode;
    private String responseSubCode;
    private String responseReasonCode;
    private String responseReasonText;
    private String authorizationCode;
    private String avsResponse;
    private String transactionId;
    private String invoiceNumber;
    private String description;
    private String amount;
    private String method;
    private String type;
    private String customerId;
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;
    private String fax;
    private String email;
    private String shipFirstName;
    private String shipLastName;
    private String shipCompany;
    private String shipAddress;
    private String shipCity;
    private String shipState;
    private String shipZipCode;
    private String shipCountry;
    private String tax;
    private String duty;
    private String freight;
    private Boolean taxExempt;
    private String poNumber;
    private String md5Hash;
    private String cardCodeResponse;
    private String cardAuthVerificationResponse;
    private String accountNumber;
    private String cardType;
    private String splitTenderId;
    private String requestedAmount;
    private String balanceOnCard;

    public Response(String responseString)
    {
        setResponseString(responseString);
    }

    /**
     * This method will parse the response string and set all the appropriate values in the object
     * @param responseString the response string as described in the AuthorizeNET AIM Guide
     */
    public void setResponseString(String responseString)
    {
        this.responseString = responseString;
        String[] values;

        if (AuthorizeNet.DEFAULT_X_DELIM_CHAR == "|")
        {
            values = responseString.split("\\" + AuthorizeNet.DEFAULT_X_DELIM_CHAR);
        }
        else
        {
            values = responseString.split(AuthorizeNet.DEFAULT_X_DELIM_CHAR);
        }

        responseCode = (values.length > 0) ? ResponseCode.get(values[0]) : null;
        responseSubCode = getValueFromAray(values, 1);
        responseReasonCode = getValueFromAray(values, 2);
        responseReasonText = getValueFromAray(values, 3);
        authorizationCode = getValueFromAray(values, 4);
        avsResponse = getValueFromAray(values, 5);
        transactionId = getValueFromAray(values, 6);
        invoiceNumber = getValueFromAray(values, 7);
        description = getValueFromAray(values, 8);
        amount = getValueFromAray(values, 9);
        method = getValueFromAray(values, 10);
        type = getValueFromAray(values, 11);
        customerId = getValueFromAray(values, 12);
        firstName = getValueFromAray(values, 13);
        lastName = getValueFromAray(values, 14);
        company = getValueFromAray(values, 15);
        address = getValueFromAray(values, 16);
        city = getValueFromAray(values, 17);
        state = getValueFromAray(values, 18);
        zipCode = getValueFromAray(values, 19);
        country = getValueFromAray(values, 20);
        phone = getValueFromAray(values, 21);
        fax = getValueFromAray(values, 22);
        email = getValueFromAray(values, 23);
        shipFirstName = getValueFromAray(values, 24);
        shipLastName = getValueFromAray(values, 25);
        shipCompany = getValueFromAray(values, 26);
        shipAddress = getValueFromAray(values, 27);
        shipCity = getValueFromAray(values, 28);
        shipState = getValueFromAray(values, 29);
        shipZipCode = getValueFromAray(values, 30);
        shipCountry = getValueFromAray(values, 31);
        tax = getValueFromAray(values, 32);
        duty = getValueFromAray(values, 33);
        freight = getValueFromAray(values, 34);
        taxExempt = (values.length > 35) ? Boolean.getBoolean(values[35]) : null;
        poNumber = getValueFromAray(values, 36);
        md5Hash = getValueFromAray(values, 37);
        cardCodeResponse = getValueFromAray(values, 38);
        cardAuthVerificationResponse = getValueFromAray(values, 39);
        accountNumber = getValueFromAray(values, 41);
        cardType = getValueFromAray(values, 42);
        splitTenderId = getValueFromAray(values, 43);
        requestedAmount = getValueFromAray(values, 44);
        balanceOnCard = getValueFromAray(values, 45);
    }

    private String getValueFromAray(String[] array, int index)
    {
        return (array.length > index) ? array[index] : null;
    }

    public ResponseCode getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode)
    {
        this.responseCode = responseCode;
    }

    public String getResponseReasonCode()
    {
        return responseReasonCode;
    }

    public void setResponseReasonCode(String responseReasonCode)
    {
        this.responseReasonCode = responseReasonCode;
    }

    public String getResponseReasonText()
    {
        return responseReasonText;
    }

    public void setResponseReasonText(String responseReasonText)
    {
        this.responseReasonText = responseReasonText;
    }

    public String getAuthorizationCode()
    {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode)
    {
        this.authorizationCode = authorizationCode;
    }

    public String getAvsResponse()
    {
        return avsResponse;
    }

    public void setAvsResponse(String avsResponse)
    {
        this.avsResponse = avsResponse;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getShipFirstName()
    {
        return shipFirstName;
    }

    public void setShipFirstName(String shipFirstName)
    {
        this.shipFirstName = shipFirstName;
    }

    public String getShipLastName()
    {
        return shipLastName;
    }

    public void setShipLastName(String shipLastName)
    {
        this.shipLastName = shipLastName;
    }

    public String getShipCompany()
    {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany)
    {
        this.shipCompany = shipCompany;
    }

    public String getShipAddress()
    {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress)
    {
        this.shipAddress = shipAddress;
    }

    public String getShipCity()
    {
        return shipCity;
    }

    public void setShipCity(String shipCity)
    {
        this.shipCity = shipCity;
    }

    public String getShipState()
    {
        return shipState;
    }

    public void setShipState(String shipState)
    {
        this.shipState = shipState;
    }

    public String getShipZipCode()
    {
        return shipZipCode;
    }

    public void setShipZipCode(String shipZipCode)
    {
        this.shipZipCode = shipZipCode;
    }

    public String getShipCountry()
    {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry)
    {
        this.shipCountry = shipCountry;
    }

    public String getTax()
    {
        return tax;
    }

    public void setTax(String tax)
    {
        this.tax = tax;
    }

    public String getDuty()
    {
        return duty;
    }

    public void setDuty(String duty)
    {
        this.duty = duty;
    }

    public String getFreight()
    {
        return freight;
    }

    public void setFreight(String freight)
    {
        this.freight = freight;
    }

    public boolean isTaxExempt()
    {
        return taxExempt;
    }

    public void setTaxExempt(boolean taxExempt)
    {
        this.taxExempt = taxExempt;
    }

    public String getPoNumber()
    {
        return poNumber;
    }

    public void setPoNumber(String poNumber)
    {
        this.poNumber = poNumber;
    }

    public String getMd5Hash()
    {
        return md5Hash;
    }

    public void setMd5Hash(String md5Hash)
    {
        this.md5Hash = md5Hash;
    }

    public String getCardCodeResponse()
    {
        return cardCodeResponse;
    }

    public void setCardCodeResponse(String cardCodeResponse)
    {
        this.cardCodeResponse = cardCodeResponse;
    }

    public String getCardAuthVerificationResponse()
    {
        return cardAuthVerificationResponse;
    }

    public void setCardAuthVerificationResponse(String cardAuthVerificationResponse)
    {
        this.cardAuthVerificationResponse = cardAuthVerificationResponse;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getSplitTenderId()
    {
        return splitTenderId;
    }

    public void setSplitTenderId(String splitTenderId)
    {
        this.splitTenderId = splitTenderId;
    }

    public String getRequestedAmount()
    {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount)
    {
        this.requestedAmount = requestedAmount;
    }

    public String getBalanceOnCard()
    {
        return balanceOnCard;
    }

    public void setBalanceOnCard(String balanceOnCard)
    {
        this.balanceOnCard = balanceOnCard;
    }
}
