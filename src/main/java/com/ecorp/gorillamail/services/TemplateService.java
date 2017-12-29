package com.ecorp.gorillamail.services;

import com.ecorp.gorillamail.common.qualifiers.OptionTemplate;
import com.ecorp.gorillamail.entities.ExternalResource;
import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.repositories.TemplateRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;

@RequestScoped
public class TemplateService {
    private static final Pattern PATTERN_LINK = Pattern.compile("(<a\\s+(?:[^>]*?\\s+)?href=([\"']))(.*?)(\\2)");

    @Inject
    private TemplateRepository templates;

    @Inject
    @OptionTemplate
    private Logger logger;

    public Template createTemplate(Organization organization) {
        Template template = new Template("New template", "<html>\n\t<head>\n\t<style>\n\t\t* {\n\t\tfont-family: sans-serif;\n\t\t}\n\t</style>\n\t</head>\n\t<body>\n\t<h1>Template</h1>\n\t<p>Start typing your template here...</p>\n\t</body>\n</html>", organization);

        template = templates.persist(template);
        template.setOrganization(organization);

        return saveTemplate(template);
    }

    private List<String> extractUrlsFromTemplate(Template template) {
        final List<String> urls = new ArrayList<>();
        final Matcher matcher = PATTERN_LINK.matcher(template.getBody());

        while (matcher.find()) {
            urls.add(matcher.group(3));
        }

        return urls;
    }

    public Template saveTemplate(Template template) {
        final List<String> urls = extractUrlsFromTemplate(template);

        for (String url : urls) {
            boolean alreadyContainsUrl = false;

            for (ExternalResource resource : template.getLinks()) {
                if (url.equals(resource.getOriginalUrl())) {
                    alreadyContainsUrl = true;
                    break;
                }
            }

            if (!alreadyContainsUrl) {
                final String shortenedUrl = UUID.randomUUID().toString();
                final ExternalResource link = new ExternalResource(url, shortenedUrl);

                link.setTemplate(template);
                template.getLinks().add(link);
            }
        }

        return templates.update(template);
    }
}
