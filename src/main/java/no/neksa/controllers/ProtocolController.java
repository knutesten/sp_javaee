package no.neksa.controllers;

import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import no.neksa.model.Protocols;
import no.neksa.model.User;
import no.neksa.model.Users;
import no.neksa.properties.I18nBundle;
import no.neksa.properties.PropertyProducer;
import no.neksa.validation.SelectedDebtors;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class ProtocolController {
    @NotNull(message = "{no.neksa.controllers.ProtocolController.price.NotNull.message}")
    @DecimalMin(value = "0", message = "{no.neksa.controllers.ProtocolController.price.DecimalMin.message}")
    private Float price;
    @Size(min = 1, max = 255, message = "{no.neksa.controllers.ProtocolController.ware.Size.message}")
    private String ware;
    @Past(message = "{no.neksa.controllers.ProtocolController.Past.message}")
    private Date date = new Date();
    @SelectedDebtors
    private User[] selectedDebtors;

    @Inject
    @I18nBundle
    private ResourceBundle i18nBundle;
    @Inject
    private Messages messages;
    @Inject
    private Users users;
    @Inject
    private Protocols protocols;

    public Map<String, User> getUserMap() {
        return users.getUserMap();
    }

    public void createProtocol() {
        protocols.addProtocols(ware, price, date, selectedDebtors);
        messages.addMessage(i18nBundle.getString(PropertyProducer.CONFIRM_PROTOCOL_CREATED));
        clearForm();
    }

    private void clearForm() {
        price = null;
        ware = null;
        date = new Date();
        selectedDebtors = null;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(final Float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getWare() {
        return ware;
    }

    public void setWare(final String ware) {
        this.ware = ware;
    }
    public User[] getSelectedDebtors() {
        return selectedDebtors;
    }

    public void setSelectedDebtors(final User[] selectedDebtors) {
        this.selectedDebtors = selectedDebtors;
    }
}
