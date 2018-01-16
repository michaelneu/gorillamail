package com.ecorp.gorillamail.services.external;

import com.ecorp.gorillamail.services.external.exceptions.AdRequestException;

public interface AdServiceIF {
    String requestBannerUrl() throws AdRequestException;
}
