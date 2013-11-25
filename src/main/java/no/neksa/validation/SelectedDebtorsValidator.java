package no.neksa.validation;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.neksa.logic.User;
import no.neksa.logic.Users;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */

@ApplicationScoped
public class SelectedDebtorsValidator implements ConstraintValidator<SelectedDebtors, User[]> {
    @Inject
    private Users users;

    @Override
    public void initialize(final SelectedDebtors constraintAnnotation) {
    }

    @Override
    public boolean isValid(final User[] value, final ConstraintValidatorContext context) {
        final User loggedInUser = users.getLoggedInUser();
        for (final User user : value)
            if(!user.equals(loggedInUser))
                return true;
        return false;
    }
}
