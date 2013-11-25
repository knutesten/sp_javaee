package no.neksa.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import no.neksa.persistence.ProtocolDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Protocols {
    @Inject
    private ProtocolDao protocolDao;
    @Inject
    private Users users;

    public Protocols() {}

    public Protocols(final ProtocolDao protocolDao, final Users users) {
        this.protocolDao = protocolDao;
        this.users = users;
    }

    public void clearDebt(final float amountToPayBack, final User userToPayBack) {
        final Protocol newProtocol = new Protocol("{clear}", 0, convertFloatToInteger(amountToPayBack), new Date(),
                users.getLoggedInUser(), userToPayBack);
        protocolDao.addNewProtocol(newProtocol);
    }

    public Map<User, Float> getProtocolOverview() {
        final Map<User, Integer> overviewInteger = new LinkedHashMap<>();
        final User loggedInUser = users.getLoggedInUser();
        final List<Protocol> protocolList = protocolDao.getAllProtocols(loggedInUser);

        final List<User> allUsers = users.getAllUsers();
        for (final User user : allUsers) {
            if (!user.equals(loggedInUser)) {
                overviewInteger.put(user, 0);
            }
        }

        for (final Protocol protocol : protocolList) {
            final User buyer  = protocol.getBuyer();
            final User debtor = protocol.getDebtor();
            final Integer amountOwed = protocol.getAmountOwed();
            if (buyer.equals(loggedInUser)) {
                final Integer currentDebt = overviewInteger.get(debtor);
                overviewInteger.put(debtor, currentDebt - amountOwed);
            } else {
                final Integer currentDebt = overviewInteger.get(buyer);
                overviewInteger.put(buyer, currentDebt + amountOwed);
            }
        }

        final Map<User, Float> overviewFloat = new LinkedHashMap<>();
        for (final Map.Entry<User, Integer> entry : overviewInteger.entrySet())
            overviewFloat.put(entry.getKey(), convertIntegerToFloat(entry.getValue()));

        return overviewFloat;
    }

    public List<Map.Entry<User, Float>> getProtocolOverviewAsList() {
        return new ArrayList<>(getProtocolOverview().entrySet());
    }

    public List<Protocol> getAllProtocolsForLoggedInUser() {
        return protocolDao.getAllProtocols(users.getLoggedInUser());
    }

    public void addProtocols(final String ware, final float price, final Date date, final User[] selectedDebtors) {
        final int  amountOwed     = calculateAmountOwedForEachDebtor(price, selectedDebtors.length);
        final int  priceAsInteger = convertFloatToInteger(price);
        final User loggedInUser   = users.getLoggedInUser();

        for (final User debtor : selectedDebtors) {
            if (!debtor.equals(loggedInUser)) {
                final Protocol newProtocol = new Protocol(ware, priceAsInteger, amountOwed, date, loggedInUser, debtor);
                protocolDao.addNewProtocol(newProtocol);
            }
        }
    }

    private int calculateAmountOwedForEachDebtor(final float price, final int numberOfDebtors) {
        return Math.round((price / numberOfDebtors) * 100);
    }

    private int convertFloatToInteger(final float price) {
        return Math.round(price * 100);
    }

    public float convertIntegerToFloat(final int integerValue) {
        return integerValue / 100f;
    }
}
