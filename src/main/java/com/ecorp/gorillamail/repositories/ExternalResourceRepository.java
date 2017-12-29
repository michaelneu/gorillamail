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
    private void fetchResource(ExternalResource resource) {
        resource.getVisitors().size();
    }

    public ExternalResource findByShortUrl(String url) throws EntityNotFoundException {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("url", url);

        final List<ExternalResource> resources = query(ExternalResource.QUERY_BY_SHORT_URL, parameters);
        
        if (resources.size() == 0) {
            throw new EntityNotFoundException("no such short url");
        }

        final ExternalResource resource = resources.get(0);

        fetchResource(resource);

        return resource;
    }
}
