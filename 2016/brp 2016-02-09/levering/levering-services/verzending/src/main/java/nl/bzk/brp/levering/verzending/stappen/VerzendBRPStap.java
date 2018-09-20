/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import java.io.StringReader;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.excepties.VerzendExceptie;
import nl.bzk.brp.levering.verzending.service.impl.VerwerkContext;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.webservice.kern.interceptor.ArchiveringBericht;
import nl.bzk.brp.webservice.kern.interceptor.BerichtArchiveringUitInterceptor;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.ServiceImpl;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;


/**
 * De stap die berichten verzendt in BRP-formaat.
 *
 * Voor bedrijfsregel R1985 is ook nog http configuratie te vinden in het configuratiebestand 'ws.xml'.

 * @brp.bedrijfsregel R1985
 * @brp.bedrijfsregel R1997
 */
@Component
@Regels({ Regel.R1985, Regel.R1997 })
public class VerzendBRPStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String              WSDL_BESTAND  = "/wsdl/brp-berichtverwerking.wsdl";
    private static final String              NAMESPACE_URI = "http://www.bzk.nl/brp/levering/berichtverwerking/service";
    private static final String              TRUE          = "true";

    private static final QName               SERVICE_NAME  = new QName(NAMESPACE_URI, "BrpBerichtVerwerkingService");
    private static final QName               PORT_NAME     = new QName(NAMESPACE_URI, "lvgSynchronisatieVerwerking");
    private static final String VERZENDEN_IN_BRP_FORMAAT = "Verzenden in BRP formaat";
    private static final String VERSTUUR_BERICHT_NAAR_ENDPOINT = "Start verzenden bericht naar endpoint '{}'";
    private static final String BERICHT_IS_NIET_GEVONDEN_OP_DE_CONTEXT_EN_NIET_VERSTUURD_ENDPOINT
        = "Bericht is niet gevonden op de context en niet verstuurd! Endpoint: {}";
//    private static final QName               PORT_NAME     = new QName(NAMESPACE_URI, "bhgNotificatieVerwerking");

    @Inject
    private BerichtArchiveringUitInterceptor archiveringOutInterceptor;

    /**
     * Verzendt het BRP bericht.
     *
     * @param berichtContext bericht context
     * @throws Exception mogelijke exceptie
     */
    @Profiled(tag = "VerzendBRPStap", logFailuresSeparately = true, level = "DEBUG")
    public void process(final BerichtContext berichtContext) throws Exception {
        LOGGER.debug(VERZENDEN_IN_BRP_FORMAAT);

        final long start = System.currentTimeMillis();
        final Dispatch<Source> webserviceClient = getCachedWebserviceClient(berichtContext);
        // Identifier van archiveringsbericht
        final BerichtModel berichtModel = berichtContext.getBerichtArchiefModel();
        final Map<String, Object> requestContext = webserviceClient.getRequestContext();
        requestContext.put(ArchiveringBericht.BERICHT_ARCHIVERING_UIT_ID, berichtModel.getID());
        final String endpointUrl = berichtContext.getBrpAfleverURI();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

        final SynchronisatieBerichtGegevens berichtGegevens = berichtContext.getSynchronisatieBerichtGegevens();

        final String leveringBericht = berichtContext.getBerichtXML();
        try {
            zetMDCMDCVeld(berichtContext);
            logSettings(requestContext.entrySet());

            if (leveringBericht != null) {
                LOGGER.info(VERSTUUR_BERICHT_NAAR_ENDPOINT, endpointUrl);

                final Source request = new StreamSource(new StringReader(leveringBericht));

                final String tijdstipRegistratie =
                    berichtGegevens.getAdministratieveHandelingTijdstipRegistratie().toString();

                // Versturen van request
                webserviceClient.invoke(request);

                LOGGER.info("Bericht verstuurd naar endpoint '{}' voor administratieve handeling '{}' met tijdstip registratie {}",
                            endpointUrl, berichtGegevens.getAdministratieveHandelingId(), tijdstipRegistratie);

                MDC.put(MDCVeld.MDC_PERSONEN_GELEVERD, String.valueOf(berichtGegevens.getGeleverdePersoonsIds().size()));
                if (berichtGegevens.getSoortDienst() != null) {
                    MDC.put(MDCVeld.MDC_DIENST_CATEGORIE, berichtGegevens.getSoortDienst().getNaam());
                }
                if (berichtGegevens.getSoortSynchronisatie() != null) {
                    MDC.put(MDCVeld.MDC_BERICHT_SOORT, berichtGegevens.getSoortSynchronisatie().getWaarde().getNaam());
                }

                String naamSynchronisatie = "";
                if (berichtGegevens.getSoortSynchronisatie() != null) {
                    naamSynchronisatie = berichtGegevens.getSoortSynchronisatie().getWaarde().getNaam();
                }
                LOGGER.info("Aantal geleverde personen in '{}' is [{}]", naamSynchronisatie, berichtGegevens
                    .getGeleverdePersoonsIds().size());
                MDC.remove(MDCVeld.MDC_PERSONEN_GELEVERD);
                MDC.remove(MDCVeld.MDC_BERICHT_SOORT);
                berichtContext.getVerwerkContext().addVerzendTijd(System.currentTimeMillis() - start);
            } else {
                throw new VerzendExceptie(BERICHT_IS_NIET_GEVONDEN_OP_DE_CONTEXT_EN_NIET_VERSTUURD_ENDPOINT + endpointUrl);
            }

        } catch (final Exception e) {
            //LOGGER.warn("Fout bij het verzenden", e);
            throw new VerzendExceptie(String.format("Het is niet gelukt om het bericht te verzenden voor toegang leveringsautorisatie %1$d : %2$s",
                berichtGegevens.getToegangLeveringsautorisatieId(), leveringBericht), e);
        } finally {
            verwijderMDCVelden();
        }
    }

    /**
     * Verzendt het BRP bericht.
     *
     * @param berichtContext bericht context
     * @throws Exception mogelijke exceptie
     */
    @Profiled(tag = "VerzendBRPStap", logFailuresSeparately = true, level = "DEBUG")
    public void processNotificatie(final BerichtContext berichtContext) throws Exception {
        LOGGER.debug(VERZENDEN_IN_BRP_FORMAAT);

        final Dispatch<Source> webserviceClient = getCachedWebserviceClient(berichtContext);
        final Map<String, Object> requestContext = webserviceClient.getRequestContext();
        final String endpointUrl = berichtContext.getBrpAfleverURI();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

        final String notificatieBericht = berichtContext.getBerichtXML();
        try {
            if (notificatieBericht != null) {
                LOGGER.info(VERSTUUR_BERICHT_NAAR_ENDPOINT, endpointUrl);

                final Source request = new StreamSource(new StringReader(notificatieBericht));

                // Versturen van request
                webserviceClient.invoke(request);

                LOGGER.info("Einde verzenden bericht naar endpoint '{}'", endpointUrl);
            } else {
                throw new VerzendExceptie(BERICHT_IS_NIET_GEVONDEN_OP_DE_CONTEXT_EN_NIET_VERSTUURD_ENDPOINT + endpointUrl);
            }

        } catch (final Exception e) {
            //LOGGER.warn("Fout bij het verzenden", e);
            throw new VerzendExceptie(String.format(
                "Het is niet gelukt om het notificatiebericht te verzenden: %1$s", notificatieBericht), e);
        }
    }

    /**
     * Initialiseert de verzending dmv het opzetten van de service via de WSDL. JAX-WS client
     * proxies zijn kostbaar om aan te maken en niet threadsafe.
     * Een mogelijke optimalisatie is het eager aanmaken van de clients, zie TEAMBRP-2867
     */
    @Regels({ Regel.R1985, Regel.R1997 })
    private Dispatch<Source> getCachedWebserviceClient(final BerichtContext berichtContext) {
        final VerwerkContext verwerkContext = berichtContext.getVerwerkContext();
        if (verwerkContext.getCachedWebserviceClient() == null) {
            final Bus bus = new SpringBusFactory().createBus();
            bus.getOutInterceptors().add(archiveringOutInterceptor);
            final ServiceImpl service = new ServiceImpl(bus, getClass().getResource(WSDL_BESTAND), SERVICE_NAME, null);
            final Dispatch<Source> cachedWebserviceClient = service.createDispatch(PORT_NAME, Source.class, Service.Mode.PAYLOAD);
            cachedWebserviceClient.getRequestContext().put("schema-validation-enabled", TRUE);
            cachedWebserviceClient.getRequestContext().put("thread.local.request.context", TRUE);
            verwerkContext.setCachedWebserviceClient(cachedWebserviceClient);
        }
        return verwerkContext.getCachedWebserviceClient();
    }

    /**
     * Verwijder de waarden van de eerder gezette MDC velden.
     */
    private void verwijderMDCVelden() {
        MDC.remove(MDCVeld.MDC_LEVERINGAUTORISATIEID);
        MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
        MDC.remove(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING);
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     *  @param berichtContext het te verwerken bericht
     */
    private void zetMDCMDCVeld(final BerichtContext berichtContext) throws JMSException {
        MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID, berichtContext.getLeveringsautorisatieId());
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, berichtContext.getOntvangendePartijCode());
        MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING, String.valueOf(berichtContext.getSynchronisatieBerichtGegevens().getAdministratieveHandelingId()));
    }

    /**
     * Log de settings.
     *
     * @param entries De set met settings.
     */
    private void logSettings(final Set<Map.Entry<String, Object>> entries) {
        final StringBuilder settings = new StringBuilder();
        for (final Map.Entry entry : entries) {
            settings.append("[");
            settings.append(entry.getKey().toString());
            settings.append("=");
            settings.append(entry.getValue().toString());
            settings.append("],");
        }
        LOGGER.debug(settings.toString());
    }

}
