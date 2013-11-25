package no.neksa.persistence;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public interface Database {
    public Connection createConnection() throws SQLException;
}
