package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@NoArgsConstructor
@Entity
@Table( name = "Organizations" )
public class Organization extends AbstractLongEntity {
    public static final String QUERY_FIND_BY_USER = "find_by_user";
    private static final long serialVersionUID = 0L;

    @Column( nullable = false )
    @Getter
    @Setter
    private String name;

    @Column( nullable = false )
    @Getter
    @Setter
    private BillingInformation billingInformation = new BillingInformation();

    @Fetch( FetchMode.SELECT )
    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
      name = "UsersToOrganizations",
      joinColumns = @JoinColumn(name = "organization"),
      inverseJoinColumns = @JoinColumn(name = "user")
    )
    @Getter
    private Set<User> users = new HashSet<>();

    @Fetch( FetchMode.SELECT )
    @OneToMany( mappedBy = "organization", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
    @Getter
    @Setter
    private Set<Template> templates = new HashSet<>();

    public Organization(String name, BillingInformation billingInformation) {
        setName(name);
        setBillingInformation(billingInformation);
    }
}
