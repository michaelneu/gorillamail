package com.ecorp.gorillamail.services.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CompiledMail {
    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String to;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String body;
}
