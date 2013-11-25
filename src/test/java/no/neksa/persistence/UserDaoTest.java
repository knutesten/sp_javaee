package no.neksa.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static no.neksa.properties.PropertyProducer.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import no.neksa.logic.User;
import no.neksa.properties.PropertyProducer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class UserDaoTest {
    private static Properties sql;
    private static UserDao userDao;

    private static final User USER_1 = new User("username1", "fullName1", "hash1");
    private static final User USER_2 = new User("username2", "fullName2", "hash2");

    @BeforeClass
    public static void beforeClass() throws SQLException, IOException {
        sql = new PropertyProducer().createSqlProperties();
        final Database database = new DatabaseMock();
        userDao = new UserDaoImpl(database, sql);

        final Connection connection = database.createConnection();
        connection.createStatement().execute(sql.getProperty(CREATE_USER_TABLE));
        insertUser(connection, USER_1);
        insertUser(connection, USER_2);
        connection.close();
    }

    private static void insertUser(final Connection connection, final User user) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement(sql.getProperty(INSERT_USER));
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getFullName());
        preparedStatement.setString(3, user.getHash());
        preparedStatement.execute();
    }

    @Test
    public void shouldReturnCorrectUserBasedOnUsername() {
        final User dbUser = userDao.getUser(USER_1.getUsername());
        assertEquals(USER_1, dbUser);
    }

    @Test
    public void shouldGetAllUsersWhenGetAllUsersIsCalled() {
        final List<User> users = userDao.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void changePasswordShouldUpdateHashAndSalt() {
        final User user2WithChangedPassword = mock(User.class);
        when(user2WithChangedPassword.getFullName()).thenReturn(USER_2.getFullName());
        when(user2WithChangedPassword.getUsername()).thenReturn(USER_2.getUsername());
        when(user2WithChangedPassword.getHash()).thenReturn("newHash");

        userDao.changePassword(user2WithChangedPassword);
        final User updatedUser = userDao.getUser(USER_2.getUsername());

        assertEquals(USER_2.getUsername(), updatedUser.getUsername());
        assertEquals(USER_2.getFullName(), updatedUser.getFullName());
        assertEquals("newHash", updatedUser.getHash());
    }

    @AfterClass
    public static void afterClass() {
        try {
            DriverManager.getConnection("jdbc:derby:memory:testDB;drop=true");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }
}
