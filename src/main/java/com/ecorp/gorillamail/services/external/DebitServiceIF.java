package com.ecorp.gorillamail.services.external;

import com.ecorp.gorillamail.entities.BillingInformation;
import com.ecorp.gorillamail.services.external.exceptions.DebitException;
import java.math.BigDecimal;

public interface DebitServiceIF {
    void requestDebit(BigDecimal amount, BillingInformation billingInfo, String reference) throws DebitException;
}
