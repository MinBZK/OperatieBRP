/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.xml.namespace.QName;
import nl.bzk.brp.utils.XmlUtils;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import org.junit.experimental.categories.Category;
import nl.bzk.brp.logging.Logger;
import org.w3c.dom.Document;

@Category(OverslaanBijInMemoryDatabase.class)
public abstract class AbstractBijhoudingServiceIntegrationTest extends AbstractIntegrationTest {

    private static final Integer ZENDENDE_PARTIJ_CODE = 101;

    private final String wsdlUrlBijhouding = "http://localhost:%s/brp/BijhoudingService/%s?wsdl";

    private final Map</*XPath naar een objectSleutel*/String, /*PersoonID*/Integer> objectSleutelXpathsEnPersoonIds
        = new HashMap<>();

    @Inject
    private ObjectSleutelService objectSleutelService;

    @Override
    final URL getWsdlUrl() throws MalformedURLException {
        return new URL(String.format(wsdlUrlBijhouding, jettyPort, getWsdlPortType()));
    }

    @Override
    QName getServiceQName() {
        return new QName("http://www.bzk.nl/brp/bijhouding/service",
                "BijhoudingService");
    }

    @Override
    QName getPortName() {
        return new QName("http://www.bzk.nl/brp/bijhouding/service", getWsdlPortType());
    }

    abstract Logger getLogger();

    abstract String getWsdlPortType();

    @Override
    protected final void bewerkRequestDocumentVoorVerzending(final Document document) {
        // Let op voor dezelfde persoon die in meerdere acties zit moeten we één sleutel gebruiken en niet meerdere
        // aanmaken.
        final Map<Integer, String> persoonIdNaarObjectSleutel = new HashMap<>();
        for (String xpathNaarObjectSleutel : objectSleutelXpathsEnPersoonIds.keySet()) {
            final Integer persoonID = objectSleutelXpathsEnPersoonIds.get(xpathNaarObjectSleutel);
            String objectSleutel;
            if (persoonIdNaarObjectSleutel.get(persoonID) != null) {
                objectSleutel = persoonIdNaarObjectSleutel.get(persoonID);
            } else {
                objectSleutel = objectSleutelService.genereerObjectSleutelString(
                        persoonID, ZENDENDE_PARTIJ_CODE);
                persoonIdNaarObjectSleutel.put(persoonID, objectSleutel);
            }
            XmlUtils.vervangAttribuutWaarde(xpathNaarObjectSleutel, objectSleutel, document);
        }
    }

    protected final void voegObjectSleutelXPathMetBijbehorendePersoonIdToe(final String xpathNaarObjectSleutel,
                                                                     final Integer persoonId)
    {
        objectSleutelXpathsEnPersoonIds.put(xpathNaarObjectSleutel, persoonId);
    }
}
