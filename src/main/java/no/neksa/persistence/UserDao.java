package no.neksa.persistence;

import java.util.List;

import no.neksa.logic.User;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public interface UserDao {
    public List<User> getAllUsers();
    public User getUser(final String username);
    public void changePassword(final User user);
}
