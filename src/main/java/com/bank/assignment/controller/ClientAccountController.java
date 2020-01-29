package com.bank.assignment.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.assignment.ClientAccount;
import com.bank.assignment.ClientAccountService;
import com.bank.assignment.CurrencyAccount;
import com.bank.assignment.DispensingNotes;

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
		List<CurrencyAccount> accounts = clientAccountService.findCurrencyAccountsByClient(clientId, timestamp);
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
		List<DispensingNotes> dispensingNotes = clientAccountService.withdrawCash(atmId, clientId, accountNumber, requiredAmount, timestamp);
// NEED TO DO PROPER ERROR HANDLING HERE _ THEIR ARE MULTIPLE SCENARIOS TO CATER FOR _ IMPACTS RETURN OBJECT		
		if (dispensingNotes.isEmpty()) {
			log.debug("No accounts to display");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts to display");
		} else {
			log.debug("Returning dispensingNotes with size="+dispensingNotes.size());
		    return ResponseEntity.ok(dispensingNotes);
		}
	}
}
