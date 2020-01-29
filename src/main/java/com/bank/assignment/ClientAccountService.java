package com.bank.assignment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<CurrencyAccount> findCurrencyAccountsByClient(Long clientId, Long timestamp) {
		List<ClientAccount> accounts = clientAccountRepo.findCurrencyAccountsByClient(clientId);
		
		List<CurrencyAccount> result = new ArrayList<>();
		for (ClientAccount account: accounts) {
			CurrencyAccount currencyAccount =  new CurrencyAccount(); 
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
	
	public List<DispensingNotes> withdrawCash(Long atmId, Long clientId, String accountNumber, BigDecimal requiredAmount, Long timestamp) {
		
		List<DispensingNotes> dispenseNotes = new ArrayList<>();
		
		// Determine current balance and get other information
		ClientAccount clientAccount = clientAccountRepo.findCurrentBalance(accountNumber);
		
		log.info("Old Balance: " + clientAccount.getDisplayBalance());
		// Determine if the requested amount is available for withdrawal based on balance and limit rules
		boolean canWithdraw = false;
		if (requiredAmount.compareTo(clientAccount.getDisplayBalance()) > 0) {
		
			BigDecimal negativeTenThousand = new BigDecimal("-10000");
			if (clientAccount.getAccountTypeCode().getAccountTypeCode().equals("CHQ") 
					&& (clientAccount.getDisplayBalance().subtract(requiredAmount).compareTo(negativeTenThousand)>0 )) {
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
			}
			BigDecimal allocationNoteTotalValue = BigDecimal.ZERO;
			for (Allocation atmAllocation : atmAllocations) {
				if (atmAllocation.getDenominationType().equals("N")) {
					BigDecimal denominationTotalValue = new BigDecimal(atmAllocation.getCount() * atmAllocation.getValue());
					allocationNoteTotalValue = allocationNoteTotalValue.add(denominationTotalValue);
				}
			}
			
			if (allocationNoteTotalValue.compareTo(requiredAmount) > 0) {
				log.info("Enough notes availble from ATM " + atmId + " for value of " + requiredAmount );
				
				// Work out Notes to dispense
				int amountStillRequired = requiredAmount.intValue();
				
				for (Allocation atmAllocation : atmAllocations) {
	                int noteAmount = atmAllocation.getValue();
	                int dispenseCount = (int) Math.floor(amountStillRequired / noteAmount);

	                if (dispenseCount > 0 )
	                {
	                	if (dispenseCount > atmAllocation.getCount()) { 
	                		dispenseCount = atmAllocation.getCount();
	                	}
	                	amountStillRequired -= dispenseCount * noteAmount;
	                } 
	                atmAllocation.setToDispense(dispenseCount);
	                dispenseNotes.add(new DispensingNotes(noteAmount, dispenseCount));
				}
								
				// Update balance in db
				clientAccountRepo.updateBalance(accountNumber, clientAccount.getDisplayBalance().subtract(requiredAmount));
				
				ClientAccount clientAccount2 = clientAccountRepo.findCurrentBalance(accountNumber);
				log.info("New Balance: " + clientAccount2.getDisplayBalance());
				
				for (DispensingNotes dispenseNote : dispenseNotes) {
					log.info("dispenseNote = " + dispenseNote);
				}	
				
			} else {
				log.info("The ATM does not have enough money to dispense for the required amount");
			}
			
		} else {
			log.info("Client does not have sufficient funds to withdraw");
		}
		
		return dispenseNotes;
	}
	
}
