package no.neksa.controllers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Messages {
    public void addMessage(final String message) {
        final FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
    }
}
