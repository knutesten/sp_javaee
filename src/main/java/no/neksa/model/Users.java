package no.neksa.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import no.neksa.authentication.Authentication;
import no.neksa.authentication.SHA256;
import no.neksa.persistence.UserDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Users {
    private List<User> users;
    @Inject
    private Authentication authentication;
    @Inject
    private UserDao userDao;
    @Inject
    private SHA256 sha256;

    @PostConstruct
    public void populateUserList() {
        users = userDao.getAllUsers();
    }

    public Map<String, User> getUserMap() {
        final Map<String, User> userMap = new LinkedHashMap<>();
        // Place logged in user first
        final User loggedInUser = findCachedUser(authentication.getLoggedInUsername());
        userMap.put(loggedInUser.getFullName(), loggedInUser);
        for (final User user : users) {
            if (!user.equals(loggedInUser))
                userMap.put(user.getFullName(), user);
        }
        return userMap;
    }

    public User findCachedUser(final String username) {
        for (final User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User getLoggedInUser() {
        return findCachedUser(authentication.getLoggedInUsername());
    }

    public void changeUserPassword(final String newPassword) {
        final String hash = sha256.generatePassword(newPassword);
        if (hash != null) {
            final User loggedInUser = getLoggedInUser();
            loggedInUser.setHash(hash);
            userDao.changePassword(loggedInUser);
        }
    }
}
