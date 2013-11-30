package no.neksa.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import no.neksa.persistence.ProtocolDao;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class ProtocolsTest {
    private static final String  WARE = "ware";
    private static final float   PRICE = 1234.23f;
    private static final int     PRICE_AS_INTEGER = 123423;
    private static final Date    DATE = new Date();
    private static final Integer EXPECTED_AMOUNT_OWED = 41141;
    private static final Float   EXPTECTED_AMOUNT_OWED_USER_3 = -309.88f;
    private static final Float   EXPTECTED_AMOUNT_OWED_USER_2 = 964.65f;
    private static final User    USER_1 = new User("username1", "fullName1", "hash1");
    private static final User    USER_2 = new User("username2", "fullName2", "hash2");
    private static final User    USER_3 = new User("username3", "fullName3", "hash3");
    private static final User[]  SELECTED_DEBTORS = { USER_1, USER_2, USER_3 };
    private static final List<Protocol> protocolList = Arrays.asList(
        new Protocol("ware1", 0, 32989, new Date(), USER_1, USER_3),
        new Protocol("ware2", 0,   232, new Date(), USER_1, USER_3),
        new Protocol("ware4", 0, 93232, new Date(), USER_2, USER_1),
        new Protocol("ware5", 0,  3233, new Date(), USER_2, USER_1),
        new Protocol("ware3", 0,  2233, new Date(), USER_3, USER_1));

    @Test
    public void overviewShouldCalculateWhatLoggedInUserOwes() {
        final Users users = mock(Users.class);
        when(users.getLoggedInUser()).thenReturn(USER_1);
        when(users.getAllUsers()).thenReturn(Arrays.asList(USER_1, USER_2, USER_3));
        final ProtocolDao protocolDao = mock(ProtocolDao.class);
        when(protocolDao.getAllProtocols(USER_1)).thenReturn(protocolList);
        final Protocols protocols = new Protocols(protocolDao, users);
        final Map<User, Float> overview = protocols.getProtocolOverview();

        assertEquals(EXPTECTED_AMOUNT_OWED_USER_2, overview.get(USER_2));
        assertEquals(EXPTECTED_AMOUNT_OWED_USER_3, overview.get(USER_3));
    }

    @Test
    public void shouldCreateThreeProtocolsWhenThreeUsersOweMoney() {
        final ProtocolDao protocolDao = mock(ProtocolDao.class);
        final ArgumentCaptor<Protocol> captor = ArgumentCaptor.forClass(Protocol.class);
        final Users users = mock(Users.class);
        when(users.findCachedUser(USER_1.getUsername())).thenReturn(USER_1);
        when(users.findCachedUser(USER_2.getUsername())).thenReturn(USER_2);
        when(users.findCachedUser(USER_3.getUsername())).thenReturn(USER_3);
        when(users.getLoggedInUser()).thenReturn(USER_1);
        final Protocols protocols = new Protocols(protocolDao, users);

        protocols.addProtocols(WARE, PRICE, DATE, SELECTED_DEBTORS);
        verify(protocolDao, times(2)).addNewProtocol(captor.capture());

        final Protocol exptectedProtocol1 =
                new Protocol(WARE, PRICE_AS_INTEGER, EXPECTED_AMOUNT_OWED, DATE, USER_1, USER_2);
        final Protocol exptectedProtocol2 =
                new Protocol(WARE, PRICE_AS_INTEGER, EXPECTED_AMOUNT_OWED, DATE, USER_1, USER_3);

        final Protocol protocol1 = captor.getAllValues().get(0);
        final Protocol protocol2 = captor.getAllValues().get(1);

        assertEquals(exptectedProtocol1, protocol1);
        assertEquals(exptectedProtocol2, protocol2);
    }
}
