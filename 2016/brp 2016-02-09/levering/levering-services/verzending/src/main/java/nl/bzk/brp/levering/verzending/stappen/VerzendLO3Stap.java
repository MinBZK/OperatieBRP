/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.levering.verzending.excepties.VerzendExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import org.perf4j.aop.Profiled;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;


/**
 * De stap die berichten verzendt in LO3-formaat.
 */
@Component
public class VerzendLO3Stap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private JmsTemplate lo3AfnemersJmsTemplate;

    /**
     * Verzendt het LO3 bericht.
     *
     * @param berichtContext bericht context
     * @throws Exception mogelijke exceptie
     */
    @Profiled(tag = "VerzendLO3Stap", logFailuresSeparately = true, level = "DEBUG")
    public void process(final BerichtContext berichtContext) throws Exception {
        LOGGER.debug("Verzenden in LO3 formaat");

        plaatsLeveringBerichtOpQueue(berichtContext);
    }

    /**
     * Plaatst het levering bericht op de queue voor ISC.
     *
     * @param berichtContext berichtContext
     */
    private void plaatsLeveringBerichtOpQueue(final BerichtContext berichtContext) throws JMSException {
        final Integer afnemerCode = Integer.valueOf(berichtContext.getOntvangendePartijCode());
        LOGGER.debug("Afnemer:{}", afnemerCode);

        final SynchronisatieBerichtGegevens berichtMetaData = berichtContext.getSynchronisatieBerichtGegevens();

        zetMDCMDCVeld(berichtContext);
        final String leveringBericht = berichtContext.getBerichtXML();

        try {
            if (leveringBericht != null) {
                // Zet op de queue naar ISC
                final MessageCreator jmsBerichtMetAfnemer = new MessageCreator() {

                    @Override
                    public final Message createMessage(final Session session) throws JMSException {
                        final Message message = session.createTextMessage(leveringBericht);
                        message.setStringProperty("voaRecipient", String.valueOf(afnemerCode));
                        if (berichtMetaData.getAdministratieveHandelingId() != null) {
                            message.setStringProperty("administratieveHandelingId", berichtMetaData.getAdministratieveHandelingId().toString());
                        }
                        if (berichtMetaData.getStuurgegevens().getCrossReferentienummer() != null) {
                            message.setStringProperty("referentienummer", berichtMetaData.getStuurgegevens().getCrossReferentienummer().getWaarde());
                        }

                        LOGGER.info("Zet bericht op de queue voor afnemer {}", String.valueOf(afnemerCode));
                        return message;
                    }
                };

                try {
                    lo3AfnemersJmsTemplate.send(jmsBerichtMetAfnemer);
                } catch (final JmsException e) {
                    throw new VerzendExceptie(String.format(
                        "Het is niet gelukt om het bericht op de ISC queue te plaatsen voor toegang leveringsautorisatie"
                            + " %1$d : %2$s", berichtMetaData.getToegangLeveringsautorisatieId(), leveringBericht), e);
                }
            } else {
                throw new VerzendExceptie("Bericht is niet gevonden op de context en niet op de queue voor ISC "
                    + "geplaatst! Destination: {}" + lo3AfnemersJmsTemplate.getDefaultDestinationName());
            }
        } finally {
            verwijderMDCVelden();
        }
    }

    /**
     * Zet MDC logging waarden in MDC velden.
     *  @param berichtContext de bericht context
     */
    private void zetMDCMDCVeld(final BerichtContext berichtContext) throws JMSException
    {
        MDC.put(MDCVeld.MDC_LEVERINGAUTORISATIEID, berichtContext.getLeveringsautorisatieId());
        MDC.put(MDCVeld.MDC_PARTIJ_CODE, berichtContext.getOntvangendePartijCode());
        MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING, String.valueOf(berichtContext.getSynchronisatieBerichtGegevens().getAdministratieveHandelingId()));
    }

    /**
     * Verwijder de waarden van de eerder gezette MDC velden.
     */
    private void verwijderMDCVelden() {
        MDC.remove(MDCVeld.MDC_LEVERINGAUTORISATIEID);
        MDC.remove(MDCVeld.MDC_PARTIJ_CODE);
        MDC.remove(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING);
    }
}
