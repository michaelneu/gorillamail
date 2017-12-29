package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.Organization;
import com.ecorp.gorillamail.entities.Template;
import com.ecorp.gorillamail.entities.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class UserRepository extends Repository<User> {
    private void fetchTemplates(Organization organization) {
        for (Template template : organization.getTemplates()) {
            // only fetch, don't do anything
        }
    }

    private void fetchUsers(Organization organization) {
        for (User user : organization.getUsers()) {
            // only fetch, don't do anything
        }
    }

    private void fetchOrganizations(User user) {
        for (Organization organization : user.getOrganizations()) {
            fetchTemplates(organization);
            fetchUsers(organization);
        }
    }

    @Override
    public User findById(long id) {
        final User user = super.findById(id);

        fetchOrganizations(user);

        return user;
    }

    public List<User> findByEmail(String email) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("email", email);

        final List<User> users = query(User.QUERY_FIND_BY_EMAIL, parameters);

        for (User user : users) {
            fetchOrganizations(user);
        }

        return users;
    }
}
