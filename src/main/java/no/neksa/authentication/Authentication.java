package no.neksa.authentication;

import java.security.Principal;

import javax.faces.context.FacesContext;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Authentication {
    public String getLoggedInUsername() {
        final Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return principal.getName();
    }
}
