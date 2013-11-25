package no.neksa.controllers;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.neksa.logic.Protocol;
import no.neksa.logic.Protocols;
import no.neksa.logic.User;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */
@Named
@RequestScoped
public class OverviewController {
    @Inject
    private Protocols protocols;

    private List<Protocol> protocolList;
    private List<Map.Entry<User, Float>> protocolOverviewList;

    public List<Map.Entry<User, Float>> getOverview() {
        if (protocolOverviewList == null) {
            protocolOverviewList = protocols.getProtocolOverviewAsList();
        }
        return protocolOverviewList;
    }

    public List<Protocol> getProtocolList() {
        if (protocolList == null) {
            protocolList = protocols.getAllProtocolsForLoggedInUser();
        }
        return protocolList;
    }
}
