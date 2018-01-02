package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table( name = "Organizations" )
@NamedQueries({
    @NamedQuery(name = Organization.QUERY_FIND_BY_USER, query = "SELECT o FROM Organization o JOIN FETCH o.users u WHERE u.id = :id"),
})
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

    @ManyToMany
    @JoinTable(
      name = "UsersToOrganizations",
      joinColumns = @JoinColumn(name = "organization"),
      inverseJoinColumns = @JoinColumn(name = "user")
    )
    @Getter
    private Set<User> users = new HashSet<>();

    @OneToMany( mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true )
    @Getter
    @Setter
    private Set<Template> templates = new HashSet<>();

    public Organization(String name, BillingInformation billingInformation) {
        setName(name);
        setBillingInformation(billingInformation);
    }
}
