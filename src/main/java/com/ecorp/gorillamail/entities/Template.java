package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "Templates" )
public class Template extends AbstractLongEntity {
    private static final long serialVersionUID = 0L;

    @Column( nullable = false )
    @Getter
    @Setter
    private String name;

    @Column( nullable = false )
    @Lob
    @Getter
    @Setter
    private String body;

    @XmlTransient
    @ManyToOne
    @JoinColumn( name = "organization" )
    @Getter
    @Setter
    private Organization organization = null;

    @XmlTransient
    @Fetch( FetchMode.SELECT )
    @OneToMany( mappedBy = "template", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
    @Getter
    private Set<Mail> mails = new HashSet<>();

    @XmlTransient
    @Fetch( FetchMode.SELECT )
    @OneToMany( mappedBy = "template", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
    @Getter
    private Set<ExternalResource> links = new HashSet<>();

    public Template(String name, String body, Organization organization) {
        setName(name);
        setBody(body);
        setOrganization(organization);
    }
}
