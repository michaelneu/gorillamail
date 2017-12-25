package com.ecorp.gorillamail.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table( name = "Users" )
@NamedQueries({
    @NamedQuery(name = User.QUERY_FIND_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email"),
})
public class User extends AbstractLongEntity {
    public static final String QUERY_FIND_BY_EMAIL = "find_by_email";
    private static final long serialVersionUID = 0L;

    @Column( nullable = false )
    @Getter
    @Setter
    private String name;

    @Column( nullable = false )
    @Getter
    @Setter
    private String email;

    @Column( nullable = false )
    @Getter
    @Setter
    private String password;

    @ManyToMany
    @JoinTable(
      name = "UsersToOrganizations",
      joinColumns = @JoinColumn(name = "user"),
      inverseJoinColumns = @JoinColumn(name = "organization")
    )
    @Getter
    private Set<Organization> organizations = new HashSet<>();

    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
    }
}
