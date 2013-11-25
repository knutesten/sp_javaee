package no.neksa.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class PropertyProducer {
    public static final String CREATE_PROTOCOLS_TABLE     = "create_protocols_table";
    public static final String GET_ROW_COUNT              = "get_row_count";
    public static final String CREATE_USER_TABLE          = "create_users_table";
    public static final String INSERT_USER                = "insert_user";
    public static final String FIND_USER                  = "find_user";
    public static final String GET_ALL_USERS              = "get_all_users";
    public static final String UPDATE_HASH                = "update_hash";
    public static final String GET_ALL_PROTOCOLS_FOR_USER = "get_all_protocols_for_user";
    public static final String INSERT_PROTOCOL            = "insert_protocol";

    public static final String DATABASE_JNDI     = "database_jndi";
    public static final String DATABASE_USERNAME = "database_username";
    public static final String DATABASE_PASSWORD = "database_password";
    public static final String DATABASE_MOCK_URL = "database_mock_url";

    public static final String VALIDATION_PASSWORDS_NOT_EQUAL =
            "no.neksa.controllers.PasswordController.password.EqualPasswords.message";
    public static final String VALIDATION_AMOUNT_MORE_THAN_YOU_OWE =
            "no.neksa.controllers.ClearDebtController.amountToPayBack.NotMoreThanYouOwe.message";

    public static final String CLEAR_DEBT_DESCRIPTION = "clear.description";
    public static final String CONFIRM_DEBT_CLEARED = "clear.confirm";
    public static final String CONFRIM_PASSWORD_CHANGED = "password.confirm";
    public static final String CONFIRM_PROTOCOL_CREATED = "create.confirm";

    @Produces @Sql
    public Properties createSqlProperties() throws IOException {
        return getProperties("sql.properties");
    }

    @Produces @DatabaseProperties
    public Properties createDatabaseProperties() throws IOException {
        return getProperties("database.properties");
    }

    @Produces @ValidationMessageBundle
    public ResourceBundle createValidationResourceBundle() {
        return ResourceBundle.getBundle("ValidationMessages");
    }

    @Produces @I18nBundle
    public ResourceBundle createI18nResourceBundle() {
        return ResourceBundle.getBundle("i18n");
    }

    private Properties getProperties(final String file) throws IOException {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
        final Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
