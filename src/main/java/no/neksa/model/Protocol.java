package no.neksa.model;

import java.util.Date;
import java.util.Formatter;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public class Protocol {
    private final String ware;
    private final int price;
    private final int amountOwed;
    private final Date date;
    private final User debtor;
    private final User buyer;

    public Protocol(final String ware, final int price, final int amountOwed,
                    final Date date, final User buyer, final User debtor) {
        this.ware = ware;
        this.price = price;
        this.date = date;
        this.debtor = debtor;
        this.buyer = buyer;
        this.amountOwed = amountOwed;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Protocol) {
            final Protocol that = (Protocol) object;
            return ware.equals(that.ware)         &&
                    price == that.price           &&
                    amountOwed == that.amountOwed &&
                    date.equals(that.date)        &&
                    debtor.equals(that.debtor)    &&
                    buyer.equals(that.buyer);
        }

        return false;
    }

    @Override
    public String toString() {
        return new Formatter().format("{ware=%s, price=%d, amountOwed=%d date=%s, buyer=%s, debtor=%s}", ware,
                price, amountOwed, date.toString().split("T")[0], buyer.getUsername(), debtor.getUsername()).toString();
    }

    public String getWare() {
        return ware;
    }

    public int getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public User getDebtor() {
        return debtor;
    }

    public User getBuyer() {
        return buyer;
    }

    public int getAmountOwed() {
        return amountOwed;
    }
}
