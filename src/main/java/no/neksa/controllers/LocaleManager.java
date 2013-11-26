package no.neksa.controllers;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@SessionScoped
public class LocaleManager implements Serializable {
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public Locale getLocale() {
        return locale;
    }

    public void test() {
        setLocale(Locale.UK);
    }

    public void setLocale(final Locale newLocale) {
        locale = newLocale;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(newLocale);
    }
}

