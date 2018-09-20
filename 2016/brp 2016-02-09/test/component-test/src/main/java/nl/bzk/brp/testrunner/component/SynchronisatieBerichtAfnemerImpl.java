/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.List;
import java.util.concurrent.Executors;
import javax.jws.WebParam;
import javax.xml.ws.Endpoint;
import nl.bzk.brp.koppelvlak.synchronisatie.FiatteringNotificeerBijhoudingsplan;
import nl.bzk.brp.koppelvlak.synchronisatie.SynchronisatieVerwerkPersoon;
import nl.bzk.brp.levering.berichtverwerking.service.LvgSynchronisatieVerwerking;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.util.PoortManager;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;

@LogischeNaam("synchronisatieBerichtAfnemer")
final class SynchronisatieBerichtAfnemerImpl extends AbstractComponent implements SynchronisatieBerichtAfnemer {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int AANTAL_THREADS = 10;

    private final int mijnPoort = PoortManager.get().geefVrijePoort();
    private final LvgSynchronisatieVerwerkingImpl service;
    private final String                          url;
    private       Endpoint                        endpoint;

    protected SynchronisatieBerichtAfnemerImpl(final Omgeving omgeving) {
        super(omgeving);
        url = String.format("http://%s:%d/kennisgeving", omgeving.geefOmgevingHost(), mijnPoort);
        service = new LvgSynchronisatieVerwerkingImpl();
    }

    @Override
    protected void doStart() {
        endpoint = Endpoint.publish(url, service);
        endpoint.setExecutor(Executors.newFixedThreadPool(AANTAL_THREADS));
        LOGGER.info("SynchronisatieBerichtAfnemer luistert op poort: " + mijnPoort);
    }

    @Override
    protected void doStop() {
        endpoint.stop();
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(mijnPoort);
        return internePoorten;
    }

    @Override
    public int geefAantalOntvangenBerichten() {
        return service.getAantalOntvangenBerichten();
    }

    @Override
    public String geefURL() {
        return url;
    }


    @javax.jws.WebService(
        serviceName = "BrpBerichtVerwerkingService",
        portName = "lvgSynchronisatieVerwerking",
        targetNamespace = "http://www.bzk.nl/brp/levering/berichtverwerking/service",
        wsdlLocation = "wsdl/brp-berichtverwerking.wsdl",
        endpointInterface = "nl.bzk.brp.levering.berichtverwerking.service.LvgSynchronisatieVerwerking")

    private class LvgSynchronisatieVerwerkingImpl implements LvgSynchronisatieVerwerking {

        private int aantalOntvangenBerichten;

        public final int getAantalOntvangenBerichten() {
            return aantalOntvangenBerichten;
        }

        @Override
        public void verwerkBijhoudingsplan(
            @WebParam(partName = "fiaVerwerkNotificatiePersoon", name = "bhg_fiaNotificeerBijhoudingsplan", targetNamespace = "http://www.bzk.nl/brp/brp0200")
            final
            FiatteringNotificeerBijhoudingsplan fiaVerwerkNotificatiePersoon)
        {
            //
        }

        @Override
        public void verwerkPersoon(
            @WebParam(partName = "synVerwerkMutatiePersoon", name = "lvg_synVerwerkPersoon", targetNamespace = "http://www.bzk.nl/brp/brp0200") final
            SynchronisatieVerwerkPersoon synVerwerkMutatiePersoon)
        {
            LOGGER.info("Synchronisatiebericht ontvangen: " + aantalOntvangenBerichten);
            aantalOntvangenBerichten++;
        }
    }
}
