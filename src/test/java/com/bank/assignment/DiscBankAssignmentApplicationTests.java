package com.bank.assignment;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DiscBankAssignmentApplicationTests {

	@Autowired
	private ClientAccountRepository clientAccRepo;
	
	@Autowired
	private ClientAccountService clientAccService;
	
	@Autowired
	private ATMRepository atmRepository;	
	
	@Test
	void contextLoads() {
	}

	@Test
	public void testUseCase1_repo() {
		log.info(" - - - - - - - - - - - - - - - - - - ");
		log.info("UseCase4.2.1 - data from repository");
		List<ClientAccount> accounts = clientAccRepo.findTransactionalAccountsByClient(new Long(1));
		log.info("accounts.size()="+accounts.size());
		for (ClientAccount account : accounts) {
			log.info("account = " + account);
		}
	}
	
	@Test
	public void testUseCase1_service() {
		log.info(" - - - - - - - - - - - - - - - - - - ");
		log.info("UseCase4.2.1 - data from service");
		List<ClientAccount> accounts = clientAccService.findTransactionalAccountByClient(new Long(1),new Long(1));
		log.info("accounts.size()="+accounts.size());
		for (ClientAccount account : accounts) {
			log.info("account = " + account.toString());
		}
	}

	
	
	@Test
	public void testUseCase2_repo() {
		log.info(" - - - - - - - - - - - - - - - - - - ");
		log.info("UseCase4.2.2 - data from repository");
		List<ClientAccount> accounts = clientAccRepo.findCurrencyAccountsByClient(new Long(2));
		log.info("accounts.size()="+accounts.size());
		for (ClientAccount account : accounts) {
			log.info("account = " + account.toString());
		}
	}
	
	@Test
	public void testUseCase2_service() {
		log.info(" - - - - - - - - - - - - - - - - - - ");
		log.info("UseCase4.2.2 - data from service");
		List<CurrencyAccount> accounts = clientAccService.findCurrencyAccountsByClient(new Long(2), new Long(1));
		log.info("accounts.size()="+accounts.size());
		for (CurrencyAccount account : accounts) {
			log.info("account = " + account);
		}
	}
	
	
	@Test
	public void testUseCase3_repo_balance() {
		log.info(" - - - - - - - - - - - - - - - - - - ");
		log.info("UseCase4.2.3 - data from repository: balance");
		String accountNumber = "4067342946";
		ClientAccount clientAccount = clientAccRepo.findCurrentBalance(accountNumber);
		log.info("balance="+clientAccount.getDisplayBalance());
		log.info("for accountNumber:"+clientAccount.getClientAccountNumber());
	}
	
	@Test
	public void testUseCase3_allocation() {
		log.info(" - - - - - - - - - - - - - - - - - - ");
		log.info("UseCase4.2.3 - data from repository: allocation");
		List<Allocation> atmAllocations = atmRepository.findATMAllocation(new Long(1));
		log.info("atmAllocations.size()="+atmAllocations.size());
		for (Allocation atmAllocation : atmAllocations) {
			log.info("atmAllocation = " + atmAllocation);
		}
	}
	
	@Test
	public void testUseCase3_withdrawCash() {
		log.info(" - - - - - - - - - - - - - - - - - - ");
		log.info("UseCase4.2.3 - service Call to withdraw cash");
		Long atmId = new Long(1);
		Long clientId =  new Long(1);
		String accountNumber = "4067342946";
		BigDecimal requiredAmount = new BigDecimal("1180");
		clientAccService.withdrawCash(atmId, clientId, accountNumber, requiredAmount,new Long(1));
	}
	
}
