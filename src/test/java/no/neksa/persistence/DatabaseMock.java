package no.neksa.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import no.neksa.properties.PropertyProducer;

import static no.neksa.properties.PropertyProducer.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */

public class DatabaseMock implements Database {
    @Override
    public Connection createConnection() throws SQLException {
        try {
            final Properties dbProperties = new PropertyProducer().createDatabaseProperties();
            final String url = dbProperties.getProperty(DATABASE_MOCK_URL);
            return DriverManager.getConnection(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
