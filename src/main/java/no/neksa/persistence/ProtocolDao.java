package no.neksa.persistence;

import java.util.List;

import no.neksa.model.Protocol;
import no.neksa.model.User;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
public interface ProtocolDao {
    public List<Protocol> getAllProtocols(final User user);
    public void addNewProtocol(final Protocol newProtocol);
}
