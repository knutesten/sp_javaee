package no.neksa.converters;

import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import no.neksa.controllers.LocaleManager;
import no.neksa.model.Protocols;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@FacesConverter("currencyConverter")
public class CurrencyConverter implements Converter {
    @Inject
    private Protocols protocols;
    @Inject
    private LocaleManager localeManager;

    @Override
    public Object getAsObject(final FacesContext facesContext, final UIComponent uiComponent, final String s) {
        return null;
    }

    @Override
    public String getAsString(final FacesContext facesContext, final UIComponent uiComponent, final Object o) {
        final float floatValue;
        if (o instanceof Integer)
            floatValue = protocols.convertIntegerToFloat((int)o);
        else
            floatValue = (float) o;
        return getFormattedNumber(floatValue);
    }

    private String getFormattedNumber(final Float value) {
        final Locale selectedLocale = localeManager.getLocale();
        final Currency currency = Currency.getInstance(selectedLocale);
        final Formatter formatter = new Formatter(selectedLocale);

        formatter.format("%,.2f %s", value, currency.getSymbol());
        return formatter.toString();
    }
}
