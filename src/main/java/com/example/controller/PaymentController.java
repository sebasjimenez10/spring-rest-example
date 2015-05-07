package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Token;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Token> recieveToken( @RequestBody Token token){
		
		System.err.println("Token: " + token.getToken());
		return new ResponseEntity<Token>(token, HttpStatus.OK);
		
	}
	
}
