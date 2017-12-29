package com.ecorp.gorillamail.beans.converters;

import com.ecorp.gorillamail.entities.User;
import com.ecorp.gorillamail.repositories.UserRepository;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.enterprise.inject.spi.CDI;

@RequestScoped
@FacesConverter( "com.ecorp.gorillamail.beans.converters.DeleteUserDropdownConverter" )
public class DeleteUserDropdownConverter implements Converter {
    private UserRepository users;

    public DeleteUserDropdownConverter() {
        users = CDI.current().select(UserRepository.class).get();
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String email) {
        if (email == null || "".equals(email)) {
            return null;
        }

        try {
            List<User> found = users.findByEmail(email);

            if (found.size() == 0) {
                return null;
            }

            return found.get(0);
        } catch (RuntimeException exception) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object user) {
        if (user == null || !(user instanceof User)) {
            return "";
        }

        return ((User)user).getEmail();
    }
}
