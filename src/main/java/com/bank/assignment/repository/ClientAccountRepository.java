package com.bank.assignment.repository;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bank.assignment.entity.ClientAccount;

@Mapper
public interface ClientAccountRepository {

	@Select("SELECT ca.client_account_number, at.description, ca.display_balance " + 
			"		FROM client_account ca, account_type at " + 
			"WHERE " + 
			"	ca.account_type_code = at.account_type_code " + 
			"	AND at.transactional = 1 " + 
			"   AND ca.client_id = #{clientId}" + 
			"ORDER BY ca.display_balance DESC")
	 @Results(value = {
		      @Result(property = "clientAccountNumber", column = "client_account_number"),
		      @Result(property = "accountTypeCode.description", column = "description"),
		      @Result(property = "displayBalance", column = "display_balance")
		   })
	public List<ClientAccount> findTransactionalAccountsByClient(@Param("clientId") Long clientId);
	
	@Select("SELECT ca.client_account_number, ca.currency_code, ca.display_balance, ccr.conversion_indicator, ccr.rate" +
			"		FROM client_account ca, currency_conversion_rate ccr	" +
			" WHERE " +
				" ca.account_type_code = 'CFCA' " +
				" AND ca.currency_code = ccr.currency_code " +
			  " AND ca.client_id = #{clientId} ")
	 @Results(value = {
		      @Result(property = "clientAccountNumber", column = "client_account_number"),
		      @Result(property = "currencyCode.currencyCode", column = "currency_code"),
		      @Result(property = "displayBalance", column = "display_balance"),
		      @Result(property = "currencyCode.conversionIndicator", column = "conversion_indicator"),
		      @Result(property = "currencyCode.rate", column = "rate")
		   })	
	public List<ClientAccount> findCurrencyAccountsByClient(@Param("clientId") Long clientId);

	@Select("SELECT ca.display_balance, ca.client_account_number, at.account_type_code, at.description " + 
			" FROM client_account ca, account_type at " + 
			" WHERE " +
			" ca.account_type_code = at.account_type_code " +
			" AND at.transactional = 1 " +
			" AND ca.client_account_number = #{clientAccountNumber} ")
	 @Results(value = {
			  @Result(property = "clientAccountNumber", column = "client_account_number"), 
			  @Result(property = "displayBalance", column = "display_balance"),
			  @Result(property = "accountTypeCode.accountTypeCode", column = "account_type_code"),
			  @Result(property = "accountTypeCode.description", column = "description")
		   })	
	public ClientAccount findCurrentBalance(@Param("clientAccountNumber") String clientAccountNumber);
	
	
	@Update("UPDATE client_account SET display_balance = #{newBalance}" +
			" WHERE client_account_number = #{clientAccountNumber} " )
	public int updateBalance(@Param("clientAccountNumber") String clientAccountNumber, @Param("newBalance") BigDecimal newBalance);
	
}
