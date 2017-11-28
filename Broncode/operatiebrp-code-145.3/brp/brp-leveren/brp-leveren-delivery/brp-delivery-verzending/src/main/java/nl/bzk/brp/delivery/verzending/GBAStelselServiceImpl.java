/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDC;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.internbericht.vrijbericht.VrijBerichtGegevens;
import nl.bzk.brp.gba.domain.levering.LeveringQueue;
import nl.bzk.brp.gba.domain.notificatie.NotificatieQueue;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.algemeen.logging.LeveringVeld;
import nl.bzk.brp.service.cache.PartijCache;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


/**
 * De stap die berichten verzendt in LO3-formaat.
 */
@Component
final class GBAStelselServiceImpl implements Verzending.GBAStelselService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private PartijCache partijCache;
    private JmsTemplate lo3JmsTemplate;

    /**
     * Constructor.
     * @param partijCache partij cache
     * @param lo3JmsTemplate jms template
     */
    @Inject
    public GBAStelselServiceImpl(final PartijCache partijCache, final JmsTemplate lo3JmsTemplate) {
        this.partijCache = partijCache;
        this.lo3JmsTemplate = lo3JmsTemplate;
    }

    /**
     * Verzendt het Lo3-bericht.
     * @param berichtGegevens jms bericht
     */
    @Override
    public void verzendLo3Bericht(final SynchronisatieBerichtGegevens berichtGegevens) {
        LOGGER.debug("Verzenden in LO3 formaat");
        LOGGER.debug("Afnemer:{}", berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId());

        final String leveringBericht = berichtGegevens.getArchiveringOpdracht().getData();
        if (leveringBericht != null) {
            // Zet op de queue naar ISC
            MDC.voerUit(zetMDCMDCVeld(berichtGegevens), () -> {
                try {
                    lo3JmsTemplate.send(LeveringQueue.NAAM.getQueueNaam(), session -> new MessageBuilder(leveringBericht, session)
                            .withArchivering(berichtGegevens.getArchiveringOpdracht())
                            .withProtocollering(berichtGegevens.getProtocolleringOpdracht())
                            .build());
                } catch (final JmsException e) {
                    throw new VerzendExceptie(String.format(
                            "Het is niet gelukt om het bericht op de ISC queue te plaatsen voor leveringsautorisatie"
                                    + " %1$d : %2$s", berichtGegevens.getArchiveringOpdracht().getLeveringsAutorisatieId(), leveringBericht), e);
                } finally {
                    verwijderMDCVelden();
                }
            });
        } else {
            throw new VerzendExceptie("Bericht is niet gevonden op de context en niet op de queue voor ISC "
                    + "geplaatst! Destination: {}" + LeveringQueue.NAAM.getQueueNaam());
        }
    }

    @Override
    public void verzendVrijBericht(final VrijBerichtGegevens berichtGegevens) {
        LOGGER.debug("Verzenden LO3 vrijbericht");
        String vrijbericht = berichtGegevens.getArchiveringOpdracht().getData();

        try {
            lo3JmsTemplate.send(
                    NotificatieQueue.NAAM.getQueueNaam(),
                    session -> new MessageBuilder(vrijbericht, session)
                            .withArchivering(berichtGegevens.getArchiveringOpdracht())
                            .build());
        } catch (final JmsException e) {
            throw new VerzendExceptie(String.format(
                    "Het is niet gelukt om het bericht op de ISC queue te plaatsen voor vrijbericht met nummer %s",
                    berichtGegevens.getArchiveringOpdracht().getReferentienummer()), e);
        }
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     * @param berichtGegevens jms bericht t.b.v. verzending
     */
    private Map<String, String> zetMDCMDCVeld(final SynchronisatieBerichtGegevens berichtGegevens) {
        final Map<String, String> mdcMap = Maps.newHashMap();
        mdcMap.put(LeveringVeld.MDC_LEVERINGAUTORISATIEID.getVeld(), String.valueOf(berichtGegevens.getArchiveringOpdracht().getLeveringsAutorisatieId()));
        mdcMap.put(LeveringVeld.MDC_PARTIJ_ID.getVeld(), String.valueOf(berichtGegevens.getArchiveringOpdracht().getOntvangendePartijId()));
        if (berichtGegevens.getProtocolleringOpdracht() != null) {
            mdcMap.put(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING.getVeld(),
                    String.valueOf(berichtGegevens.getProtocolleringOpdracht().getAdministratieveHandelingId()));
        }
        return mdcMap;
    }

    /**
     * Verwijder de waarden van de eerder gezette MDC velden.
     */
    private void verwijderMDCVelden() {
        MDC.remove(LeveringVeld.MDC_LEVERINGAUTORISATIEID);
        MDC.remove(LeveringVeld.MDC_PARTIJ_ID);
        MDC.remove(LeveringVeld.MDC_ADMINISTRATIEVE_HANDELING);
    }

    private class MessageBuilder {
        private Message message;

        MessageBuilder(final String inhoud, final Session session) throws JMSException {
            this.message = session.createTextMessage(inhoud);
        }

        MessageBuilder withArchivering(final ArchiveringOpdracht archiveringOpdracht) throws JMSException {
            this.message.setStringProperty("voaRecipient", bepaalPartijCode(archiveringOpdracht.getOntvangendePartijId()));

            if (archiveringOpdracht.getReferentienummer() != null) {
                this.message.setStringProperty("iscBerichtReferentie", archiveringOpdracht.getReferentienummer());
            }
            if (archiveringOpdracht.getCrossReferentienummer() != null) {
                this.message.setStringProperty("iscCorrelatieReferentie", archiveringOpdracht.getCrossReferentienummer());
            }
            return this;
        }

        MessageBuilder withProtocollering(final ProtocolleringOpdracht protocolleringOpdracht) throws JMSException {
            if (protocolleringOpdracht.getAdministratieveHandelingId() != null) {
                message.setStringProperty("administratieveHandelingId", protocolleringOpdracht.getAdministratieveHandelingId().toString());
            }
            return this;
        }

        Message build() throws JMSException {
            message.setStringProperty("iscReferentie", UUID.randomUUID().toString());
            return message;
        }

        private String bepaalPartijCode(final Short partijId) {
            if (partijId == null || partijCache.geefPartijMetId(partijId) == null) {
                throw new VerzendExceptie(
                        String.format("Partij met id %1$d kon niet worden opgehaald uit de cache. Het bericht kan daarom niet worden verzonden.", partijId));
            }
            return partijCache.geefPartijMetId(partijId).getCode();
        }
    }
}
