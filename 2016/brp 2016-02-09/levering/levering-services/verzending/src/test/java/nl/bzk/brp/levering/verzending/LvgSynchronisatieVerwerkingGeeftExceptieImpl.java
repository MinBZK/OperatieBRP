/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending;

import javax.jws.WebParam;
import javax.xml.ws.WebServiceException;
import nl.bzk.brp.koppelvlak.synchronisatie.FiatteringNotificeerBijhoudingsplan;
import nl.bzk.brp.koppelvlak.synchronisatie.SynchronisatieVerwerkPersoon;
import nl.bzk.brp.levering.berichtverwerking.service.LvgSynchronisatieVerwerking;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

/**
 * Deze klasse biedt toegang tot de webservice(s) van de afnemers.
 */
@javax.jws.WebService(
    serviceName = "BrpBerichtVerwerkingServiceMetException",
    portName = "lvgSynchronisatieVerwerkingMetException",
    targetNamespace = "http://www.bzk.nl/brp/levering/berichtverwerking/service",
    wsdlLocation = "brp-berichtverwerking.wsdl",
    endpointInterface = "nl.bzk.brp.levering.berichtverwerking.service.LvgSynchronisatieVerwerking")

public class LvgSynchronisatieVerwerkingGeeftExceptieImpl implements LvgSynchronisatieVerwerking {

    private static final Logger LOG =
        LoggerFactory.getLogger();

    private int aantalOntvangenBerichten;

    @Override
    public void verwerkBijhoudingsplan(@WebParam(partName = "fiaVerwerkNotificatiePersoon", name = "bhg_fiaNotificeerBijhoudingsplan",
        targetNamespace = "http://www.bzk.nl/brp/brp0200") final FiatteringNotificeerBijhoudingsplan fiaVerwerkNotificatiePersoon)
    {
        LOG.info("Executing operation notificatie");
        LOG.info("Verwerken notificatie: {}", fiaVerwerkNotificatiePersoon);

        aantalOntvangenBerichten++;

        throw new WebServiceException("Fout: de verwerking is mislukt!");
    }

    @Override
    public final void verwerkPersoon(@WebParam(partName = "synVerwerkMutatiePersoon",
        name = "lvg_synVerwerkPersoon", targetNamespace = "http://www.bzk.nl/brp/brp0200") final SynchronisatieVerwerkPersoon synchronisatieVerwerkPersoon)
    {
        LOG.info("Executing operation kennisgeving met exception");
        LOG.info("Verwerken kennisgeving en geeft exceptie: {}", synchronisatieVerwerkPersoon);

        aantalOntvangenBerichten++;

        throw new WebServiceException("Fout: de verwerking is mislukt!");
    }

    public final int getAantalOntvangenBerichten() {
        return aantalOntvangenBerichten;
    }

}
