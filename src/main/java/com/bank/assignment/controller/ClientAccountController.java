package com.bank.assignment.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.assignment.entity.ClientAccount;
import com.bank.assignment.response.CurrencyAccountResponse;
import com.bank.assignment.service.ClientAccountService;

import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ClientAccountController {
	
	@Autowired
	ClientAccountService clientAccountService;

	public static final String REQUEST_PARAM_CLIENT_ID = "clientId";
	public static final String REQUEST_PARAM_TIMESTAMP = "timestamp";
	public static final String REQUEST_PARAM_ATM_ID = "atmId";
	public static final String REQUEST_PARAM_ACCOUNT_NUMBER = "accountNumber";
	public static final String REQUEST_PARAM_REQUIRED_AMOUNT = "requiredAmount";
	
	@GetMapping(value = "/clientTransactionalAccounts")
	public ResponseEntity<?> getClientTransactionalAccounts(
			@ApiParam(value = "Client id for which their transactional account(s) will be retrieved", required = true) 
				@RequestParam(REQUEST_PARAM_CLIENT_ID) Long clientId,
			@ApiParam(value = "Timestamp in milliseconds of clients interaction", required = true) 
				@RequestParam(REQUEST_PARAM_TIMESTAMP) Long timestamp) {
		log.debug("getClientTransactionalAccount() STARTING");	
		List<ClientAccount> accounts = clientAccountService.findTransactionalAccountByClient(clientId, timestamp);
		if (accounts.isEmpty()) {
			log.debug("No accounts to display");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts to display");
		} else {
			log.debug("Returning accounts with size="+accounts.size());
		    return ResponseEntity.ok(accounts);
		}
	}
	
	@GetMapping(value = "/clientCurrencyAccounts")
	public ResponseEntity<?> getClientCurrencyAccounts(
			@ApiParam(value = "Client id for which their currency account(s) will be retrieved", required = true)
				@RequestParam(REQUEST_PARAM_CLIENT_ID) Long clientId,
			@ApiParam(value = "Timestamp in milliseconds of clients interaction", required = true)				
		      @RequestParam(REQUEST_PARAM_TIMESTAMP) Long timestamp) {
		log.debug("getClientCurrencyAccounts() STARTING");	
		List<CurrencyAccountResponse> accounts = clientAccountService.findCurrencyAccountsByClient(clientId, timestamp);
		if (accounts.isEmpty()) {
			log.debug("No accounts to display");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts to display");
		} else {
			log.debug("Returning accounts with size="+accounts.size());
		    return ResponseEntity.ok(accounts);
		}
	}
	
	@GetMapping(value = "/clientWithdrawCash")
	public ResponseEntity<?> getClientWithdrawCash(
			@ApiParam(value = "Client id for the client who wants to withdraw cash", required = true)
				@RequestParam(REQUEST_PARAM_CLIENT_ID) Long clientId,
			@ApiParam(value = "ATM id for the atm machine in which the client is withdrawing cash", required = true)				
				@RequestParam(REQUEST_PARAM_ATM_ID) Long atmId,
			@ApiParam(value = "Client account number from which to withdraw cash", required = true)
				@RequestParam(REQUEST_PARAM_ACCOUNT_NUMBER) String accountNumber,
			@ApiParam(value = "Required amount of cash clients request to withdraw", required = true)				
				@RequestParam(REQUEST_PARAM_REQUIRED_AMOUNT) BigDecimal requiredAmount,
			@ApiParam(value = "Timestamp in milliseconds of clients interaction", required = true)
				@RequestParam(REQUEST_PARAM_TIMESTAMP) Long timestamp) {
		log.debug("clientWithdrawCash() STARTING");	
		Map<String, Object> dispensingNotes = clientAccountService.withdrawCash(atmId, clientId, accountNumber, requiredAmount, timestamp);
		
		if (dispensingNotes.isEmpty()) {
			log.debug("Unknown error has occurred");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error has occurred");
		} else {
			Entry<String, Object> element = dispensingNotes.entrySet().iterator().next();
			if ("error".equals(element.getKey())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(element.getValue());
			} else {
				return ResponseEntity.ok(element);	
			}	    
		}
	}
}
