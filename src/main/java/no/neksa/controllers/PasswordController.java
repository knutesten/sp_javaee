package no.neksa.controllers;

import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

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
public class PasswordController {
    @Size(min = 8, message = "{no.neksa.controllers.PasswordController.password.Size.message}")
    private String password1;
    private String password2;
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

    public void changePassword() {
        if (passwordAreEqual()){
            users.changeUserPassword(password1);
            messages.addMessage(i18nBundle.getString(PropertyProducer.CONFRIM_PASSWORD_CHANGED));
        } else {
            messages.addMessage(validationBundle.getString(PropertyProducer.VALIDATION_PASSWORDS_NOT_EQUAL));
        }

        clearForm();
    }

    private boolean passwordAreEqual() {
        return password1.equals(password2);
    }

    private void clearForm() {
        password1 = null;
        password2 = null;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(final String password2) {
        this.password2 = password2;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(final String password1) {
        this.password1 = password1;
    }
}
