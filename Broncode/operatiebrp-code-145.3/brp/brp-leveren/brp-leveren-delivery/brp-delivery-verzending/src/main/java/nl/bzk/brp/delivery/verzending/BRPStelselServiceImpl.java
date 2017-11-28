/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.google.common.collect.Maps;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.WebServiceException;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.cache.PartijCache;
import nl.bzk.brp.service.dalapi.ToegangBijhoudingautorisatieRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/**
 * De stap die berichten verzendt in BRP-formaat.
 * <p>
 * Voor bedrijfsregel R1985 is ook nog http configuratie te vinden in het configuratiebestand 'brp-verzending-webservice.xml'.
 */
@Component
@Bedrijfsregel(Regel.R1985)
@Bedrijfsregel(Regel.R1997)
final class BRPStelselServiceImpl implements Verzending.BrpStelselService {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String VERZENDEN_IN_BRP_FORMAAT = "Verzenden in BRP formaat";
    private static final String VERSTUUR_BERICHT_NAAR_ENDPOINT = "Start verzenden bericht naar endpoint '{}'";
    private static final String BERICHT_IS_NIET_GEVONDEN_OP_DE_CONTEXT_EN_NIET_VERSTUURD_ENDPOINT
            = "Bericht is niet gevonden op de context en niet verstuurd! Endpoint: ";

    @Inject
    private ToegangBijhoudingautorisatieRepository toegangBijhoudingautorisatieRepository;
    @Inject
    private PartijCache partijCache;
    @Inject
    private Verzending.VerwerkPersoonWebServiceClient verzendingWebServiceClientVerwerkPersoon;
    @Inject
    private Verzending.VerwerkBijhoudingsNotificatieWebServiceClient verzendingWebServiceClientVerwerkBijhoudingsNotificatie;
    @Inject
    private Verzending.VerwerkVrijBerichtWebServiceClient verzendingVerwerkVrijBerichtWebServiceClient;

    private BRPStelselServiceImpl() {
    }

    /**
     * Verzendt het BRP bericht.
     * @param berichtGegevens jms bericht
     */
    @Override
    public void verzendSynchronisatieBericht(final SynchronisatieBerichtGegevens berichtGegevens) {
        LOGGER.debug(VERZENDEN_IN_BRP_FORMAAT);
        final String endpointUrl = berichtGegevens.getBrpEndpointURI();
        Assert.notNull(endpointUrl, "Endpoint URL leeg");
        final String leveringBericht = berichtGegevens.getArchiveringOpdracht().getData();
        if (leveringBericht != null) {
            LOGGER.info(VERSTUUR_BERICHT_NAAR_ENDPOINT, endpointUrl);

            // Versturen van request
            final Source request = new StreamSource(new StringReader(leveringBericht));
            LOGGER.info("Synchronisatiebericht wordt verstuurd naar endpoint '{}'", endpointUrl);
            MDC.voerUit(zetMDCMDCVeld(berichtGegevens), () -> {
                try {
                    verzendingWebServiceClientVerwerkPersoon.verstuurRequest(request, endpointUrl);
                } catch (final WebServiceException e) {
                    throw new VerzendExceptie(String.format("Het is niet gelukt om het bericht te verzenden voor toegang leveringsautorisatie %1$d : %2$s",
                            berichtGegevens.getProtocolleringOpdracht().getToegangLeveringsautorisatieId(), leveringBericht), e);
                } finally {
                    verwijderMDCVelden();
                }
            });

            String naamSynchronisatie = "";
            if (berichtGegevens.getProtocolleringOpdracht().getSoortSynchronisatie() != null) {
                naamSynchronisatie = berichtGegevens.getProtocolleringOpdracht().getSoortSynchronisatie().getNaam();
            }
            LOGGER.info("Aantal geleverde personen in '{}' is [{}]", naamSynchronisatie,
                    berichtGegevens.getArchiveringOpdracht().getTeArchiverenPersonen().size());
            MDC.remove(LeveringVeld.MDC_PERSONEN_GELEVERD);
            MDC.remove(LeveringVeld.MDC_BERICHT_SOORT);
        } else {
            throw new VerzendExceptie(BERICHT_IS_NIET_GEVONDEN_OP_DE_CONTEXT_EN_NIET_VERSTUURD_ENDPOINT + endpointUrl);
        }
    }

    /**
     * Verzendt het bijhoudingsnotificatiebericht.
     * @param berichtGegevens bijhoudingsnotificatie berichtGegevens
     */
    @Override
    public void verzendBijhoudingsNotificatieBericht(final BijhoudingsplanNotificatieBericht berichtGegevens) {
        LOGGER.debug(VERZENDEN_IN_BRP_FORMAAT);

        final String leveringBericht = berichtGegevens.getVerwerkBijhoudingsplanBericht();
        final Set<String> uniekeAfleverpunten = geefUniekeAfleverpuntenVoorBijhoudingsPartij(berichtGegevens.getOntvangendePartijId().toString());

        if (uniekeAfleverpunten.isEmpty()) {
            LOGGER.debug(
                    "Er worden geen notificatieberichten verzonden : er zijn geen afleverpunten gevonden voor partij '{}'",
                    berichtGegevens.getOntvangendePartijId().toString());
        }

        for (String afleverpunt : uniekeAfleverpunten) {
            if (leveringBericht != null) {
                LOGGER.info(VERSTUUR_BERICHT_NAAR_ENDPOINT, afleverpunt);
                final Source request = new StreamSource(new StringReader(leveringBericht));
                MDC.voerUit(zetMDCMDCVeld(berichtGegevens), () -> {
                    try {
                        verzendingWebServiceClientVerwerkBijhoudingsNotificatie.verstuurRequest(request, afleverpunt);
                        LOGGER.info("Bijhoudingsnotificatiebericht wordt verstuurd naar endpoint '{}'", afleverpunt);
                    } catch (final WebServiceException e) {
                        LOGGER.warn("Fout bij het verzenden", e);
                        throw new VerzendExceptie(String.format("Het is niet gelukt om het notificatiebericht te verzenden voor partij "
                                + "%1$d : %2$s", berichtGegevens.getOntvangendePartijId(), leveringBericht), e);
                    } finally {
                        verwijderMDCVelden();
                    }
                });
            } else {
                throw new VerzendExceptie(BERICHT_IS_NIET_GEVONDEN_OP_DE_CONTEXT_EN_NIET_VERSTUURD_ENDPOINT + afleverpunt);
            }

        }
    }

    @Override
    public void verzendVrijBericht(final VrijBerichtGegevens berichtGegevens) {
        LOGGER.debug("Verzenden Vrij Bericht");
        final String endpointUrl = berichtGegevens.getBrpEndpointURI();
        Assert.notNull(endpointUrl, "Endpoint URL leeg");
        final String leveringBericht = berichtGegevens.getArchiveringOpdracht().getData();

        if (leveringBericht != null) {
            LOGGER.info(VERSTUUR_BERICHT_NAAR_ENDPOINT, endpointUrl);
            // Versturen van request
            final Source request = new StreamSource(new StringReader(leveringBericht));
            LOGGER.info("Vrij bericht wordt verstuurd naar endpoint '{}'", endpointUrl);
            MDC.voerUit(zetMDCMDCVeld(berichtGegevens), () -> {
                try {
                    verzendingVerwerkVrijBerichtWebServiceClient.verstuurRequest(request, endpointUrl);
                } catch (final WebServiceException e) {
                    throw new VerzendExceptie(String.format("Het is niet gelukt om het vrij bericht te verzenden voor partij %1$d : %2$s",
                            berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId(), leveringBericht), e);
                } finally {
                    verwijderMDCVelden();
                }
            });
        } else {
            throw new VerzendExceptie(BERICHT_IS_NIET_GEVONDEN_OP_DE_CONTEXT_EN_NIET_VERSTUURD_ENDPOINT + endpointUrl);
        }
    }


    /**
     * Bepaalt alle toegangbijhoudingsautorisaties voor een partij en geeft een set van unieke afleverpunten terug.
     * @param partijId partij id
     * @return set van unieke afleverpunten.
     */
    private Set<String> geefUniekeAfleverpuntenVoorBijhoudingsPartij(final String partijId) {
        final Set<String> uniekeAfleverpunten = new HashSet<>();
        final Partij partij = partijCache.geefPartijMetId(Short.parseShort(partijId));
        final List<ToegangBijhoudingsautorisatie> toegangBijhoudingsautorisatiesLijst = toegangBijhoudingautorisatieRepository.findByGeautoriseerde(partij);
        for (ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie : toegangBijhoudingsautorisatiesLijst) {
            uniekeAfleverpunten.add(toegangBijhoudingsautorisatie.getAfleverpunt());
        }
        return uniekeAfleverpunten;
    }

    /**
     * Verwijder de waarden van de eerder gezette MDC velden.
     */
    private void verwijderMDCVelden() {
        MDC.remove(LeveringVeld.MDC_LEVERINGAUTORISATIEID);
        MDC.remove(LeveringVeld.MDC_PARTIJ_ID);
        MDC.remove(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING);
        MDC.remove(LeveringVeld.MDC_BIJHOUDINGSAUTORISATIE_ID);
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     * @param berichtGegevens jmsVerzendingbericht
     */
    private Map<String, String> zetMDCMDCVeld(final SynchronisatieBerichtGegevens berichtGegevens) {
        final Map<String, String> mdcMap = Maps.newHashMap();

        if (berichtGegevens.getArchiveringOpdracht().getData() != null) {
            mdcMap.put(LeveringVeld.MDC_PERSONEN_GELEVERD.getVeld(),
                    String.valueOf(berichtGegevens.getArchiveringOpdracht().getTeArchiverenPersonen().size()));
            if (berichtGegevens.getSoortDienst() != null) {
                mdcMap.put(LeveringVeld.MDC_DIENST_CATEGORIE.getVeld(), berichtGegevens.getSoortDienst().getNaam());
            }
            if (berichtGegevens.getProtocolleringOpdracht().getSoortSynchronisatie() != null) {
                mdcMap.put(LeveringVeld.MDC_BERICHT_SOORT.getVeld(), berichtGegevens.getProtocolleringOpdracht().getSoortSynchronisatie().getNaam());
            }
        }

        mdcMap.put(LeveringVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), String.valueOf(berichtGegevens.getArchiveringOpdracht().getLeveringsAutorisatieId()));
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), String.valueOf(berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId()));
        mdcMap.put(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING.getVeld(),
                berichtGegevens.getProtocolleringOpdracht() == null ? null
                        : String.valueOf(berichtGegevens.getProtocolleringOpdracht().getAdministratieveHandelingId()));
        return mdcMap;
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     * @param berichtGegevens jmsVerzendingbericht
     */
    private Map<String, String> zetMDCMDCVeld(final BijhoudingsplanNotificatieBericht berichtGegevens) {
        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), String.valueOf(berichtGegevens.getOntvangendePartijCode()));
        mdcMap.put(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING.getVeld(), String.valueOf(berichtGegevens.getAdministratieveHandelingId()));
        return mdcMap;
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     * @param berichtGegevens jmsVerzendingbericht
     */
    private Map<String, String> zetMDCMDCVeld(final VrijBerichtGegevens berichtGegevens) {
        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_BERICHT_SOORT.getVeld(), SoortBericht.VRB_VRB_VERWERK_VRIJ_BERICHT.getIdentifier());
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), String.valueOf(berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId()));
        return mdcMap;

    }
}
