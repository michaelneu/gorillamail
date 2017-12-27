package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Getter
    @Setter
    private String body;

    @ManyToOne
    @JoinColumn( name = "organization" )
    @Getter
    @Setter
    private Organization organization = null;

    @OneToMany( mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true )
    @Getter
    private Set<Mail> mails = new HashSet<>();

    public Template(String name, String body, Organization organization) {
        setName(name);
        setBody(body);
        setOrganization(organization);
    }
}
