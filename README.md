# Discovery Bank Assignment 
(Bank Balance and Dispensing System)

## Notes

This is an updated version of the original version done. I have added a webservice font-end to the back-end calls been made, and some general changes have been made.


### Building / Running
A. Execute the below command to build the jar file with maven 

    mvn clean install

and once the code builds successfully - in the target folder you can execute the following to run the code:

	java -jar disc-bank-assignment-ws-0.0.1-SNAPSHOT.jar

OR

B. Running the code as a sprint boot app via maven - execute the following command
 
	mvn spring-boot:run

	
### Testing

Swagger has been added to the code to be able to easily test the webservice. Once the code is running (as per steps above), you can go to the following url, to get the swagger UI interface, where calls can be executed:

	http://localhost:8080/swagger-ui.html

## Use Cases

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