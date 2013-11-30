package no.neksa.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import no.neksa.properties.DatabaseProperties;
import static no.neksa.properties.PropertyProducer.*;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Default
@Singleton
public class DatabaseImpl implements Database {
    @Inject
    @DatabaseProperties
    private Properties dbProperties;
    private DataSource dataSource;

    @PostConstruct
    public void initialize() {
        try {
            final String jndi = dbProperties.getProperty(DATABASE_JNDI);
            dataSource = (DataSource) new InitialContext().lookup(jndi);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
    }

    @Override
    public Connection createConnection() throws SQLException {
        final String username = dbProperties.getProperty(DATABASE_USERNAME);
        final String password = dbProperties.getProperty(DATABASE_PASSWORD);
        return dataSource.getConnection();
    }
}
