package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.ExternalResource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class ExternalResourceRepository extends Repository<ExternalResource> {
    public ExternalResource findByShortUrl(String url) throws EntityNotFoundException {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("url", url);

        final List<ExternalResource> resources = query(ExternalResource.QUERY_BY_SHORT_URL, parameters);
        
        if (resources.size() == 0) {
            throw new EntityNotFoundException("no such short url");
        }

        return resources.get(0);
    }

    public ExternalResource findByOriginalUrl(String url) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("url", url);

        final List<ExternalResource> resources = query(ExternalResource.QUERY_BY_ORIGINAL_URL, parameters);

        return resources.get(0);
    }
}
