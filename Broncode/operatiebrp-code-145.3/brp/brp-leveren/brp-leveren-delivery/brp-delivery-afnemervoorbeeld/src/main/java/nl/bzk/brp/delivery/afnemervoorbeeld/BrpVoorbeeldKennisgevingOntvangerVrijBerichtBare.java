/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemervoorbeeld;

import javax.inject.Inject;
import javax.jws.soap.SOAPBinding;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;


/**
 * De implementatie van de webservice die vrije berichten ontvangt van de BRP.
 */
@WebServiceProvider(wsdlLocation = "wsdl/berichtverwerking.wsdl", serviceName = "BrpBerichtVerwerkingService", portName = "vrbVrijBerichtVerwerking")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@ServiceMode(value = Service.Mode.PAYLOAD)
public class BrpVoorbeeldKennisgevingOntvangerVrijBerichtBare implements Provider<DOMSource> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private QueuePersister queuePersister;
    private DatabasePersister databasePersister;

    /**
     * Constructor.
     * @param queuePersister queue persister
     * @param databasePersister database persister
     */
    @Inject
    public BrpVoorbeeldKennisgevingOntvangerVrijBerichtBare(QueuePersister queuePersister, DatabasePersister databasePersister) {
        this.queuePersister = queuePersister;
        this.databasePersister = databasePersister;
    }

    @Override
    public DOMSource invoke(final DOMSource request) {
        LOGGER.info("Synchronisatiebericht ontvangen");
        final String requestXML = DOMSourceToString.toString(request);
        queuePersister.persistVrijBericht(requestXML);
        databasePersister.persistVrijBericht(requestXML);
        return new DOMSource();
    }
}
