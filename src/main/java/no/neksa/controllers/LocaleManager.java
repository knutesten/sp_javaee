package no.neksa.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private final List<Locale> supportedLocales = new ArrayList<>();
    {
        final Iterator<Locale> localeIterator =
                FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        while (localeIterator.hasNext())
            supportedLocales.add(localeIterator.next());
    }

    public Locale getLocale() {
        return locale;
    }

//    private int i = 0;
//    public void switchLocale() {
//        setLocale(supportedLocales.get(++i % supportedLocales.size()));
//    }

    public void setLocale(final Locale newLocale) {
        locale = newLocale;
        FacesContext.getCurrentInstance().getViewRoot().setLocale(newLocale);
    }
}

