package com.ecorp.gorillamail.services.external;

import com.ecorp.bank.service.Account;
import com.ecorp.bank.service.AccountingService;
import com.ecorp.bank.service.AccountingServiceService;
import com.ecorp.bank.service.Customer;
import com.ecorp.bank.service.TransactionException_Exception;
import com.ecorp.bank.service.TransactionRequest;
import com.ecorp.gorillamail.entities.BillingInformation;
import com.ecorp.gorillamail.services.external.exceptions.DebitException;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.xml.ws.WebServiceRef;

@ApplicationScoped
@Alternative
public class DebitService implements DebitServiceIF {
    private static final long CUSTOMER_NUMBER = 31,
                              ACCOUNT_NUMBER = 100;
    
    private Account gorillamailAccount;
    private Customer gorillamailCustomer;
    
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/im-lamport_8080/ecorp-bank/AccountingService.wsdl")
    private AccountingServiceService accountingServiceRef;
    private AccountingService accountingService;
    
    @PostConstruct
    public void postConstruct() {
        accountingService = accountingServiceRef.getAccountingServicePort();
        
        gorillamailAccount = new Account();
        gorillamailAccount.setId(ACCOUNT_NUMBER);
        
        gorillamailCustomer = new Customer();
        gorillamailCustomer.setId(CUSTOMER_NUMBER);
    }
    
    @Override
    public void requestDebit(BigDecimal amount, BillingInformation billingInfo, String reference) throws DebitException {
        final Account sourceAccount = new Account();
        sourceAccount.setId(billingInfo.getAccountNumber());
        
        final TransactionRequest transactionRequest = new TransactionRequest();
        
        transactionRequest.setCustomer(gorillamailCustomer);
        transactionRequest.setFrom(sourceAccount);
        transactionRequest.setTo(gorillamailAccount);
        transactionRequest.setAmount(amount);
        transactionRequest.setReference(reference);
        
        try {
            accountingService.requestDebit(transactionRequest);
        } catch (TransactionException_Exception ex) {
            throw new DebitException(ex);
        }
    }
}
