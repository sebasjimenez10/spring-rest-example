package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.PaymentResponse;
import com.example.model.Token;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Recipient;
import com.stripe.model.Transfer;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PaymentResponse> recieveToken( @RequestBody Token token){
		
		System.err.println("Token: " + token.getToken());
		
		PaymentResponse response = new PaymentResponse();
		response.setToken(token.getToken());
		
		String executePayment = null;
		
		try{
			executePayment = executePayment(token.getToken());
		}catch(Exception e){
			executePayment = "An error ocurred processing the payment. Reason: "
					+ e.getMessage();
		}
		
		response.setMessage(executePayment);
		
		return new ResponseEntity<PaymentResponse>(response, HttpStatus.OK);
		
	}
	
	private String executePayment(String token) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException{
		
		// Setting stripe test api_key
        Stripe.apiKey = "sk_test_d1o87gtp2OGsGXbBA9vMGnFW";
		
		Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("description", "Customer for test@example.com");
        customerParams.put("source", token); // obtained with Stripe.js

        Customer customer = Customer.create(customerParams);
        
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", 400);
        chargeParams.put("currency", "usd");
        chargeParams.put("customer", customer.getId());
        chargeParams.put("source", customer.getSources().getData().get(0).getId()); // obtained with Stripe.js
        chargeParams.put("description", "Charge for test@example.com");

        Charge.create(chargeParams);

        Map<String, Object> tokenParamsBank = new HashMap<String, Object>();
        Map<String, Object> bank_accountParams = new HashMap<String, Object>();
        bank_accountParams.put("country", "US");
        bank_accountParams.put("routing_number", "110000000");
        bank_accountParams.put("account_number", "000123456789");
        tokenParamsBank.put("bank_account", bank_accountParams);

        com.stripe.model.Token bankToken = com.stripe.model.Token.create(tokenParamsBank);
        
        Map<String, Object> recipientParams = new HashMap<String, Object>();
        recipientParams.put("name", "John Doe");
        recipientParams.put("type", "individual");
        recipientParams.put("email", "payee@example.com");
        recipientParams.put("bank_account", bankToken.getId());

        Recipient recipient = Recipient.create(recipientParams);
        
        Map<String, Object> transferParams = new HashMap<String, Object>();
        transferParams.put("amount", 400);
        transferParams.put("currency", "usd");
        transferParams.put("recipient", recipient.getId());
        transferParams.put("bank_account", recipient.getActiveAccount().getId());
        transferParams.put("description", "Transfer for test@example.com");

        Transfer.create(transferParams);
        
        return "Payment Processed Successfully";
	}
	
}
