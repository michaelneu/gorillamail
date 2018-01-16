package com.ecorp.gorillamail.services.external;

import com.ecorp.gorillamail.services.external.exceptions.AdRequestException;

public class AdServiceMock implements AdServiceIF {
    @Override
    public String requestBannerUrl() throws AdRequestException {
        throw new AdRequestException("unable to request banner url");
    }
}
