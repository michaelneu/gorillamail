package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
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

    @ElementCollection
    @CollectionTable(
        name = "Variables",
        joinColumns = @JoinColumn( name = "mail" )
    )
    @Getter
    private Set<Variable> variables = new HashSet<>();

    @ElementCollection
    @CollectionTable(
        name = "Headers",
        joinColumns = @JoinColumn( name = "mail" )
    )
    @Getter
    private Set<Header> headers = new HashSet<>();

    @ManyToOne
    @JoinColumn( name = "template" )
    @Getter
    @Setter
    private Template template = null;
}
