package com.ecorp.gorillamail.services.external;

import com.ecorp.gorillamail.services.external.exceptions.AdRequestException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
public class AdServiceMock implements AdServiceIF {
    @Override
    public String requestBannerUrl() throws AdRequestException {
        throw new AdRequestException("unable to request banner url");
    }
}
