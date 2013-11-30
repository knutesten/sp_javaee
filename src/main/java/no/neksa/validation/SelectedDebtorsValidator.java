package no.neksa.validation;

import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
//import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.neksa.model.User;
//import no.neksa.model.Users;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */

@ApplicationScoped
public class SelectedDebtorsValidator implements ConstraintValidator<SelectedDebtors, User[]> {
    // Does not work with glassfish for some reason.
//    @Inject
//    private Users users;

    @Override
    public void initialize(final SelectedDebtors constraintAnnotation) {
    }

    @Override
    public boolean isValid(final User[] value, final ConstraintValidatorContext context) {
        final String loggedInUsername =
                FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        for (final User user : value)
            if(!user.getUsername().equals(loggedInUsername))
                return true;
        return false;
    }
}
