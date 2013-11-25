package no.neksa.logic;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class User {
    private final String username;
    private final String fullName;
    private String hash;

    public User(final String username, final String fullName, final String hash) {
        this.fullName = fullName;
        this.username = username;
        this.hash = hash;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof User) {
            final User that = (User) object;
            return  username.equals(that.username) &&
                    fullName.equals(that.fullName) &&
                    hash.equals(that.hash);
        }
        return false;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}
