package com.ecorp.gorillamail.services.mail;

import com.ecorp.gorillamail.entities.ExternalResource;
import com.ecorp.gorillamail.entities.Header;
import com.ecorp.gorillamail.entities.Mail;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.Variable;
import com.ecorp.gorillamail.repositories.ExternalResourceRepository;
import com.ecorp.gorillamail.repositories.TemplateRepository;
import com.ecorp.gorillamail.services.exceptions.MailCompilerException;
import com.github.jknack.handlebars.Handlebars;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MailCompiler {
    private static final String SERVER = "im-lamport:8080",
                                URL_REWRITE_BASE = "http://" + SERVER + "/gorillamail/redirect.xhtml?target=";

    private static final Pattern PATTERN_LINK = Pattern.compile("(<a\\s+(?:[^>]*?\\s+)?href=([\"']))(.*?)(\\2)");
    private Handlebars handlebars = new Handlebars();

    @Inject
    private TemplateRepository templates;

    @Inject
    private ExternalResourceRepository resources;

    private String findFrom(Set<Header> headers) {
        Optional<Header> result = headers.stream()
                                         .filter(h -> h != null && "from".equals(h.getName().toLowerCase()))
                                         .findFirst();

        if (result.isPresent()) {
            return result.get()
                         .getValue();
        }

        return "noreply@gorillamail.space";
    }

    private String findTo(Set<Header> headers) throws MailCompilerException {
        Optional<Header> result = headers.stream()
                                         .filter(h -> h != null && "to".equals(h.getName().toLowerCase()))
                                         .findFirst();

        if (result.isPresent()) {
            return result.get()
                         .getValue();
        }

        throw new MailCompilerException("no receipient defined");
    }

    private String findSubject(Set<Header> headers) throws MailCompilerException {
        Optional<Header> result = headers.stream()
                                         .filter(h -> h != null && "subject".equals(h.getName().toLowerCase()))
                                         .findFirst();

        if (result.isPresent()) {
            return result.get()
                         .getValue();
        }

        throw new MailCompilerException("no subject defined");
    }

    private String interpolate(String text, Set<Variable> variables) throws MailCompilerException {
        Map<String, Object> context = new HashMap<>();

        for (Variable variable : variables) {
            context.put(variable.getName(), variable.getValue());
        }

        try {
            return handlebars.compileInline(text)
                             .apply(context);
        } catch (Exception exception) {
            throw new MailCompilerException("error compiling text", exception);
        }
    }

    private Template resolveTemplate(Template template) throws MailCompilerException {
        try {
            final Template resolvedTemplate = templates.findById(template.getId());

            return resolvedTemplate;
        } catch (Exception exception) {
            throw new MailCompilerException("can't resolve template body", exception);
        }
    }

    private String rewriteUrls(String body) {
        final StringBuilder bodyBuilder = new StringBuilder();
        final Matcher matcher = PATTERN_LINK.matcher(body);
        int previousEndPosition = 0;

        while (matcher.find()) {
            bodyBuilder.append(body.substring(previousEndPosition, matcher.start()));
            bodyBuilder.append(matcher.group(1));

            final String originalUrl = matcher.group(3);
            final ExternalResource link = resources.findByOriginalUrl(originalUrl);

            bodyBuilder.append(URL_REWRITE_BASE + link.getShortenedUrl());

            bodyBuilder.append(matcher.group(4));
            previousEndPosition = matcher.end();
        }

        bodyBuilder.append(body.substring(previousEndPosition, body.length()));

        return bodyBuilder.toString();
    }

    public CompiledMail compileMail(Mail mail) throws MailCompilerException {
        final Template template = mail.getTemplate();

        if (template == null) {
            throw new IllegalArgumentException("expected mail template to not be null");
        }

        final Template resolvedTemplate = resolveTemplate(template);
        final String from = findFrom(mail.getHeaders()),
                     to = findTo(mail.getHeaders()),
                     subject = findSubject(mail.getHeaders()),
                     interpolatedSubject = interpolate(subject, mail.getVariables()),
                     body = resolvedTemplate.getBody(),
                     rewrittenBody = rewriteUrls(body),
                     interpolatedBody = interpolate(rewrittenBody, mail.getVariables());

        return new CompiledMail(from, to, interpolatedSubject, interpolatedBody);
    }
}
