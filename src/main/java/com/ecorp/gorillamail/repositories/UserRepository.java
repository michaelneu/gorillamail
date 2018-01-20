package com.ecorp.gorillamail.repositories;

import com.ecorp.gorillamail.entities.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class UserRepository extends Repository<User> {
    public List<User> findByEmail(String email) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("email", email);

        return query(User.QUERY_FIND_BY_EMAIL, parameters);
    }
}
