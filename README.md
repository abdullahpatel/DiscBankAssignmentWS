# Discovery Bank Assigment

## Notes

### Building / Runnng

    mvn clean install

### Testing

    mvn test
	Run using ??? and can test http://localhost:8080/swagger-ui.html

## Use Cases

* Only columns in the database needed for the assignment was coded into java - did not create unnecessary code at this stage
* Error messages shown to user will be handled by the Controller/UI layer

### 4.2.1

* See code 

### 4.2.2

* See code

### 4.2.3

* See code

### 4.2.4
The SQL Script created for this use case is:

	SELECT ca.client_id, cl.surname, ca.client_account_number, at.description, ca.display_balance 
	FROM client_account ca, client cl, account_type at
	WHERE ca.display_balance IN (select max(display_balance) as max_balance
	   	         from client_account
    	        group by client_id)
	AND ca.client_id = cl.client_id   
	AND ca.account_type_code = at.account_type_code
	ORDER BY client_id asc;

(Above solution is not 100% ideal because if two accounts have the same max display_balance it may return extra/incorrect information)


### 4.2.5
The SQL Script created for this use case is:


	select cl.title || ' ' || cl.name || ' ' || cl.surname as full_name, 'Balance - Transactional' as balance_type, sum(ca.display_balance) as Balance
	from client cl, client_account ca, account_type at
	where cl.CLIENT_ID = ca.CLIENT_ID
	and ca.ACCOUNT_TYPE_CODE = at.ACCOUNT_TYPE_CODE
	and at.TRANSACTIONAL = 1
	Group by cl.client_id, cl.title || ' ' || cl.name || ' ' || cl.surname
	
	UNION ALL
	
	select cl.title || ' ' || cl.name || ' ' || cl.surname as full_name, 'Balance - Loan' as balance_type, sum(ca.display_balance) as Balance
	from client cl, client_account ca, account_type at
	where cl.CLIENT_ID = ca.CLIENT_ID
	and ca.ACCOUNT_TYPE_CODE = at.ACCOUNT_TYPE_CODE
	and at.TRANSACTIONAL = 0
	Group by cl.client_id, cl.title || ' ' || cl.name || ' ' || cl.surname
	
	UNION ALL
	
	select cl.title || ' ' || cl.name || ' ' || cl.surname as full_name, 'Net Position' as balance_type, sum(ca.display_balance) as Balance
	from client cl, client_account ca, account_type at
	where cl.CLIENT_ID = ca.CLIENT_ID
	and ca.ACCOUNT_TYPE_CODE = at.ACCOUNT_TYPE_CODE
	Group by cl.client_id, cl.title || ' ' || cl.name || ' ' || cl.surname
	
	Order by full_name, balance_type ASC;


[Note that just the SQL above was created and no code calling these SQL has been created]