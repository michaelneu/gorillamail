package com.ecorp.gorillamail.services.mail;

import com.ecorp.gorillamail.entities.Header;
import com.ecorp.gorillamail.entities.Mail;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.Variable;
import com.ecorp.gorillamail.repositories.TemplateRepository;
import com.ecorp.gorillamail.services.exceptions.MailCompilerException;
import com.github.jknack.handlebars.Handlebars;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MailCompiler {
    private Handlebars handlebars = new Handlebars();

    @Inject
    private TemplateRepository templates;

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

    private String resolveTemplateBody(Template template) throws MailCompilerException {
        if (template.getBody() != null) {
            return template.getBody();
        }

        try {
            final Template resolvedTemplate = templates.findById(template.getId());

            return resolvedTemplate.getBody();
        } catch (Exception exception) {
            throw new MailCompilerException("can't resolve template body", exception);
        }
    }

    public CompiledMail compileMail(Mail mail) throws MailCompilerException {
        final Template template = mail.getTemplate();

        if (template == null) {
            throw new IllegalArgumentException("expected mail template to not be null");
        }

        final String from = findFrom(mail.getHeaders()),
                     to = findTo(mail.getHeaders()),
                     subject = findSubject(mail.getHeaders()),
                     interpolatedSubject = interpolate(subject, mail.getVariables()),
                     body = resolveTemplateBody(template),
                     interpolatedBody = interpolate(body, mail.getVariables());

        return new CompiledMail(from, to, interpolatedSubject, interpolatedBody);
    }
}
