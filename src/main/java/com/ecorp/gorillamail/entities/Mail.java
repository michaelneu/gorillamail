package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Entity
@Table( name = "Mails" )
public class Mail extends AbstractLongEntity {
    private static final long serialVersionUID = 0L;

    @Column( nullable = false )
    @Getter
    @Setter
    private boolean ad;

    @ElementCollection( fetch = FetchType.EAGER )
    @CollectionTable(
        name = "Variables",
        joinColumns = @JoinColumn( name = "mail" )
    )
    @Getter
    private Set<Variable> variables = new HashSet<>();

    @ElementCollection( fetch = FetchType.EAGER )
    @CollectionTable(
        name = "Headers",
        joinColumns = @JoinColumn( name = "mail" )
    )
    @Getter
    private Set<Header> headers = new HashSet<>();

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "template" )
    @Getter
    @Setter
    private Template template = null;
}
