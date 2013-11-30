package no.neksa.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import no.neksa.authentication.Authentication;
import no.neksa.model.Protocols;
import no.neksa.model.User;
import no.neksa.model.Users;
import no.neksa.properties.I18nBundle;
import no.neksa.properties.PropertyProducer;
import no.neksa.properties.ValidationMessageBundle;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class ClearDebtController {
    @NotNull(message = "{no.neksa.controllers.ClearDebtController.userToPayBack.NotNull.message}")
    private User userToPayBack;
    @NotNull(message = "{no.neksa.controllers.ClearDebtController.amountToPayBack.NotNull.message}")
    @DecimalMin(value = "0", message = "{no.neksa.controllers.ClearDebtController.amountToPayBack.DecimalMin.message}")
    private Float amountToPayBack;

    @Inject
    private Authentication authentication;
    @Inject
    private Protocols protocols;
    @Inject
    private Users users;
    @Inject
    @ValidationMessageBundle
    private ResourceBundle validationBundle;
    @Inject
    @I18nBundle
    private ResourceBundle i18nBundle;
    @Inject
    private Messages messages;

    private Map<User, Float> debtOverview;
    private Map<String, User> usersOwed;

    @PostConstruct
    public void populateDebtOverviewMap() {
        debtOverview = protocols.getProtocolOverview();
        usersOwed = createUsersOwedMap();
        initializeSelector();
    }

    public void clearDebt() {
        if (amountToPayBackIsEqualOrLessThanAmountOwed()){
            protocols.clearDebt(amountToPayBack, userToPayBack);
            messages.addMessage(i18nBundle.getString(PropertyProducer.CONFIRM_DEBT_CLEARED));
        } else {
            messages.addMessage(validationBundle.getString(PropertyProducer.VALIDATION_AMOUNT_MORE_THAN_YOU_OWE));
        }

        // Refresh
        populateDebtOverviewMap();
    }

    private boolean amountToPayBackIsEqualOrLessThanAmountOwed() {
        return amountToPayBack <= getAmountOwedSelectedUser();
    }

    private void initializeSelector() {
        if (!usersOwed.isEmpty()) {
            userToPayBack = usersOwed.get(usersOwed.keySet().iterator().next());
        }
    }

    private Map<String, User> createUsersOwedMap() {
        final Map<String, User> usersOwed = new LinkedHashMap<>();
        final List<Map.Entry<User, Float>> overview = protocols.getProtocolOverviewAsList();
        for (final Map.Entry<User, Float> entry : overview) {
            if (entry.getValue() > 0) {
                usersOwed.put(entry.getKey().getFullName(), entry.getKey());
            }
        }

        return usersOwed;
    }

    private Float getAmountOwedSelectedUser() {
        if(userToPayBack == null) {
            return null;
        }
        return debtOverview.get(userToPayBack);
    }

    public Map<String, User> getUsersOwed() {
        return usersOwed;
    }

    public void setAmountToPayBack(final Float amountToPayBack) {
        this.amountToPayBack = amountToPayBack;
    }

    public Float getAmountToPayBack() {
        return getAmountOwedSelectedUser();
    }

    public User getUserToPayBack() {
        return userToPayBack;
    }

    public void setUserToPayBack(final User userToPayBack) {
        this.userToPayBack = userToPayBack;
    }
}
