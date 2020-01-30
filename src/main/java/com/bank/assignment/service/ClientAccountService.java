package com.bank.assignment.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.assignment.entity.Allocation;
import com.bank.assignment.entity.ClientAccount;
import com.bank.assignment.repository.ATMRepository;
import com.bank.assignment.repository.ClientAccountRepository;
import com.bank.assignment.response.CurrencyAccountResponse;
import com.bank.assignment.response.DispensingNotesResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientAccountService {
	
	@Autowired
	ClientAccountRepository clientAccountRepo;

	@Autowired
	ATMRepository atmRepository;
	
	public List<ClientAccount> findTransactionalAccountByClient(Long clientId, Long timestamp) {
		return clientAccountRepo.findTransactionalAccountsByClient(clientId);
	}
	
	public List<CurrencyAccountResponse> findCurrencyAccountsByClient(Long clientId, Long timestamp) {
		List<ClientAccount> accounts = clientAccountRepo.findCurrencyAccountsByClient(clientId);
		
		List<CurrencyAccountResponse> result = new ArrayList<>();
		for (ClientAccount account: accounts) {
			CurrencyAccountResponse currencyAccount =  new CurrencyAccountResponse(); 
			currencyAccount.setAccountNumber(account.getClientAccountNumber());
			currencyAccount.setCurrency(account.getCurrencyCode().getCurrencyCode());
			currencyAccount.setCurrencyBalance(account.getDisplayBalance());
			currencyAccount.setConversionRate(account.getCurrencyCode().getRate());
			
			BigDecimal randBalance = account.getDisplayBalance();
			if (account.getCurrencyCode().getConversionIndicator().equals("/")) {
				randBalance = account.getDisplayBalance().multiply(BigDecimal.ONE.divide(account.getCurrencyCode().getRate(), 2, RoundingMode.HALF_UP));
			} else if (account.getCurrencyCode().getConversionIndicator().equals("*")) {
				randBalance = account.getDisplayBalance().multiply(BigDecimal.ONE.multiply(account.getCurrencyCode().getRate()));
			}
			currencyAccount.setRandBalance(randBalance);
			result.add(currencyAccount);
		}
		
		return result;
	}
	
	public Map<String, Object> withdrawCash(Long atmId, Long clientId, String accountNumber, BigDecimal requiredAmount, Long timestamp) {
		
		List<DispensingNotesResponse> dispenseNotes = new ArrayList<>();
		Map<String, Object> dispenseNotesResponse = new HashMap();
		boolean validationPassed = true;
		
		// Determine current balance and get other information
		ClientAccount clientAccount = clientAccountRepo.findCurrentBalance(accountNumber);
		log.info("clientAccount= " + clientAccount);
		
		// Determine if the requested amount is available for withdrawal based on balance and limit rules
		boolean canWithdraw = false;
		if (requiredAmount.compareTo(clientAccount.getDisplayBalance()) >= 0) {
		
			BigDecimal negativeTenThousand = new BigDecimal("-10000");
			if (clientAccount.getAccountTypeCode().getAccountTypeCode().equals("CHQ") 
					&& (clientAccount.getDisplayBalance().subtract(requiredAmount).compareTo(negativeTenThousand)>=0 )) {
				canWithdraw = true;
			}
			
		} else {
			canWithdraw = true;
		}
		
		log.info("Client can Withdraw: " + canWithdraw);
		if (canWithdraw) {
			
			List<Allocation> atmAllocations = atmRepository.findATMAllocation(atmId);
			if (atmAllocations.size() == 0) {
				log.info("ATM information not found");
				dispenseNotesResponse.put("error", "ATM not registered or unfunded");
				validationPassed = false;
			}
			BigDecimal allocationNoteTotalValue = BigDecimal.ZERO;
			for (Allocation atmAllocation : atmAllocations) {			
				if (atmAllocation.getDenomination().getDenominationType().getDenominationTypeCode().equals("N")) {
					BigDecimal denominationTotalValue = new BigDecimal(atmAllocation.getTotalValueForDenomination());
					allocationNoteTotalValue = allocationNoteTotalValue.add(denominationTotalValue);
				}
			}

			if (validationPassed) {
				if ((allocationNoteTotalValue.compareTo(requiredAmount) >= 0)) {
					log.info("Enough notes available from ATM " + atmId + " for value of " + requiredAmount);

					// Work out Notes to dispense
					int amountStillRequired = requiredAmount.intValue();

					for (Allocation atmAllocation : atmAllocations) {
						int noteAmount = atmAllocation.getDenomination().getValue();
						int dispenseCount = (int) Math.floor(amountStillRequired / noteAmount);

						if (dispenseCount > 0) {
							if (dispenseCount > atmAllocation.getCount()) {
								dispenseCount = atmAllocation.getCount();
							}
							amountStillRequired -= dispenseCount * noteAmount;
						}
						atmAllocation.setToDispense(dispenseCount);
						dispenseNotes.add(new DispensingNotesResponse(noteAmount, dispenseCount));
					}

					// Update clients balance in db
					clientAccountRepo.updateBalance(accountNumber,
							clientAccount.getDisplayBalance().subtract(requiredAmount));
					// Not in the assignment request - but ideally we should also be updating the notes in the
					// ATM as well - update call to the db table atm_allocation

					dispenseNotesResponse.put("success", dispenseNotes);

				} else {
					log.info("The ATM does not have enough money to dispense for the required amount");
					dispenseNotesResponse.put("error",
							"Amount not available, would you like to draw " + allocationNoteTotalValue);
					validationPassed = false;
				}
			}

		} else {
			log.info("Client does not have sufficient funds to withdraw");
			dispenseNotesResponse.put("error", "Insufficient funds");
			validationPassed = false;
		}
		
		return dispenseNotesResponse;
	}
	
}
