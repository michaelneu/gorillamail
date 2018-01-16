package com.ecorp.gorillamail.services.external;

import com.ecorp.gorillamail.entities.BillingInformation;
import com.ecorp.gorillamail.services.external.exceptions.DebitException;
import java.math.BigDecimal;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
public class DebitServiceMock implements DebitServiceIF {
    @Override
    public void requestDebit(BigDecimal amount, BillingInformation billingInfo, String reference) throws DebitException {
        throw new DebitException("unable to process debit request");
    }
}
