package com.bank.assignment;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ATMRepository {

	@Select("SELECT am.atm_id, ata.count, dn.value, dn.denomination_type_code "+
			"		FROM atm am, atm_allocation ata, denomination dn " + 
			"WHERE " + 
			"   am.atm_id = ata.atm_id " +
			"   AND ata.denomination_id = dn.denomination_id " +
			"   AND am.atm_id = #{atmId} " +
			" ORDER BY value DESC")
	 @Results(value = {
		      @Result(property = "atmId", column = "atm_id"),
		      @Result(property = "count", column = "count"),
		      @Result(property = "value", column = "value"),
		      @Result(property = "denominationType", column = "denomination_type_code")
		   })
	public List<Allocation> findATMAllocation(@Param("atmId") Long atmId);

}
