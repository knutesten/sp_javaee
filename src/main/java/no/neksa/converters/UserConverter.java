package no.neksa.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import no.neksa.logic.User;
import no.neksa.logic.Users;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@FacesConverter("userConverter")
public class UserConverter implements Converter {
    @Inject
    private Users users;

    @Override
    public Object getAsObject(final FacesContext facesContext, final UIComponent uiComponent, final String username) {
        return users.findCachedUser(username);
    }

    @Override
    public String getAsString(final FacesContext facesContext, final UIComponent uiComponent, final Object object) {
        final User user = (User) object;
        return  user.getUsername();
    }
}
