package no.neksa.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import static no.neksa.properties.PropertyProducer.*;

import no.neksa.logic.User;
import no.neksa.properties.Sql;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class UserDaoImpl implements UserDao {
    @Inject
    private Database database;

    @Inject @Sql
    private Properties sql;

    public UserDaoImpl() {}

    public UserDaoImpl(final Database database, final Properties sql) {
        this.database = database;
        this.sql = sql;
    }

    @Override
    public List<User> getAllUsers() {
        final List<User> users = new ArrayList<>();

        try (Connection connection = database.createConnection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(sql.getProperty(GET_ALL_USERS));

            while (resultSet.next()) {
                final User user = getUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return users;
    }

    @Override
    public User getUser(final String username) {
        try (Connection connection = database.createConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql.getProperty(FIND_USER));
            preparedStatement.setString(1, username);

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    private User getUserFromResultSet(final ResultSet resultSet) throws SQLException {
        final String username = resultSet.getString("username");
        final String fullName = resultSet.getString("full_name");
        final String hash     = resultSet.getString("hash");

        return new User(username, fullName, hash);
    }

    @Override
    public void changePassword(final User user) {
        try (Connection connection = database.createConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql.getProperty(UPDATE_HASH));
            preparedStatement.setString(1, user.getHash());
            preparedStatement.setString(2, user.getUsername());

            preparedStatement.execute();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
