package no.neksa.persistence;

import static org.junit.Assert.*;
import static no.neksa.properties.PropertyProducer.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import no.neksa.logic.Protocol;
import no.neksa.logic.User;
import no.neksa.logic.Users;
import no.neksa.properties.PropertyProducer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class ProtocolDaoTest {
    private static final User USER_1 = new User("user1", "user1", "hash");
    private static final User USER_2 = new User("user2", "user2", "hash");
    private static final User USER_3 = new User("user3", "user3", "hash");
    private static final User USER_4 = new User("user4", "user4", "hash");
    private static final User USER_5 = new User("user5", "user5", "hash");
    private static final User USER_6 = new User("user6", "user6", "hash");
    private static Properties sql;
    private static Database database;
    private static ProtocolDao protocolDao;

    @BeforeClass
    public static void beforeClass() throws SQLException, IOException {
        sql = new PropertyProducer().createSqlProperties();
        database = new DatabaseMock();
        final Users users = mock(Users.class);
        when(users.findCachedUser(anyString())).thenReturn(USER_5, USER_3, USER_5, USER_3,USER_5,USER_3);
        protocolDao = new ProtocolDaoImpl(database, sql, users);

        final Connection connection = database.createConnection();
        connection.createStatement().execute(sql.getProperty(CREATE_PROTOCOLS_TABLE));
        connection.close();
    }

    @Test
    public void shouldReturnPlusThreeProtocolsWhenThreeProtocolsAreAdded() throws SQLException {
        final int rowCountBefore = getRowCount();
        protocolDao.addNewProtocol(new Protocol("ware1", 3233, 223, new Date(), USER_1, USER_2));
        protocolDao.addNewProtocol(new Protocol("ware2", 3233, 232, new Date(), USER_1, USER_2));
        protocolDao.addNewProtocol(new Protocol("ware3", 3233, 232, new Date(), USER_2, USER_1));
        assertEquals(rowCountBefore + 3, getRowCount());
    }

    @Test
    public void shouldReturnAllProtocolsForAGivenUser() {
        protocolDao.addNewProtocol(new Protocol("ware1", 323,  232, new Date(), USER_3, USER_4));
        protocolDao.addNewProtocol(new Protocol("ware2", 3244, 232, new Date(), USER_4, USER_3));
        protocolDao.addNewProtocol(new Protocol("ware4", 23,   232, new Date(), USER_5, USER_6));
        protocolDao.addNewProtocol(new Protocol("ware5", 2343, 233, new Date(), USER_6, USER_4));
        final Protocol protocol =  new Protocol("ware3", 3233, 233, new Date(), USER_3, USER_5);
        protocolDao.addNewProtocol(protocol);

        final List<Protocol> protocols = protocolDao.getAllProtocols(USER_3);
        assertEquals(3, protocols.size());

        assertEquals(protocol, protocols.get(0));
    }

    private int getRowCount() throws SQLException {
        final Connection connection = database.createConnection();
        final ResultSet resultSet = connection.createStatement().executeQuery(sql.getProperty(GET_ROW_COUNT));
        resultSet.next();
        final int count = resultSet.getInt("1");
        resultSet.close();
        connection.close();
        return count;
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
