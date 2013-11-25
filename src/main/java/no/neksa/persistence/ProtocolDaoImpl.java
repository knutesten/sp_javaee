package no.neksa.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.inject.Inject;

import no.neksa.logic.Protocol;
import no.neksa.logic.User;
import no.neksa.logic.Users;
import no.neksa.properties.I18nBundle;
import no.neksa.properties.Sql;

import static no.neksa.properties.PropertyProducer.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class ProtocolDaoImpl implements ProtocolDao {
    @Inject
    private Database database;

    @Inject
    private Users users;

    @Inject @Sql
    private Properties sql;

    @Inject
    @I18nBundle
    ResourceBundle resourceBundle;

    public ProtocolDaoImpl() {}

    public ProtocolDaoImpl(final Database database, final Properties sql, final Users users) {
        this.database = database;
        this.sql = sql;
        this.users = users;
    }

    public List<Protocol> getAllProtocols(final User user) {
        final List<Protocol> protocols = new ArrayList<>();
        try (Connection connection = database.createConnection()) {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(sql.getProperty(GET_ALL_PROTOCOLS_FOR_USER));
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getUsername());


            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Protocol protocol = getProtocolFromResultSet(resultSet);
                protocols.add(protocol);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return protocols;
    }

    private Protocol getProtocolFromResultSet(final ResultSet resultSet) throws SQLException {
        String         ware      = resultSet.getString("ware");
        if (ware.equals("{clear}")) {
            ware = resourceBundle.getString(CLEAR_DEBT_DESCRIPTION);
        }
        final int      price     = resultSet.getInt("price");
        final int      amoutOwed = resultSet.getInt("amount_owed");
        final Date     date      = new Date(resultSet.getLong("date"));
        final User     debtor    = users.findCachedUser(resultSet.getString("debtor"));
        final User     buyer     = users.findCachedUser(resultSet.getString("buyer"));
        return new Protocol(ware, price, amoutOwed, date, buyer, debtor);
    }

    public void addNewProtocol(final Protocol newProtocol) {
        try (Connection connection = database.createConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql.getProperty(INSERT_PROTOCOL));
            preparedStatement.setString(1, newProtocol.getWare());
            preparedStatement.setInt(   2, newProtocol.getPrice());
            preparedStatement.setInt(   3, newProtocol.getAmountOwed());
            preparedStatement.setLong(  4, newProtocol.getDate().getTime());
            preparedStatement.setString(5, newProtocol.getDebtor().getUsername());
            preparedStatement.setString(6, newProtocol.getBuyer().getUsername());

            preparedStatement.execute();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
