/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.SynchronisatieBerichtService;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;

/**
 * Verwerk een synchronisatie bericht.
 */
public final class SynchronisatieMessageHandler extends AbstractMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger VERKEER_LOG = LoggerFactory.getBerichtVerkeerLogger();

    private SynchronisatieBerichtService<SyncBericht, SyncBericht>[] berichtServices;

    /*
     * ******************************************** Bean properties ***********************************************
     */

    /**
     * Sets the sync bericht services.
     *
     * @param syncBerichtServices
     *            the sync bericht services
     */
    public void setSyncBerichtServices(final SynchronisatieBerichtService<SyncBericht, SyncBericht>... syncBerichtServices) {
        berichtServices = syncBerichtServices;
    }

    /*
     * **************************************** MessageListener methods ********************************************
     */
    @Override
    public void onMessage(final Message message) {
        try (final MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            LoggingContext.reset();
            SynchronisatieLogging.init();

            VERKEER_LOG.info("Start verwerken binnenkomend bericht ...");
            final String berichtReferentie = bepaalBerichtReferentie(message);
            try (final MDCCloser berichtReferentieCloser = MDC.put(MDCVeld.SYNC_BERICHT_REFERENTIE, berichtReferentie)) {
                SyncBericht antwoord;

                VERKEER_LOG.info("Lees bericht inhoud ...");
                final String berichtInhoud = bepaalBerichtInhoud(message);
                VERKEER_LOG.info("Parse bericht inhoud ...");
                final SyncBericht bericht = SyncBerichtFactory.SINGLETON.getBericht(berichtInhoud);
                bericht.setMessageId(berichtReferentie);

                VERKEER_LOG.info("Verwerk bericht ...");
                antwoord = verwerkBericht(bericht);

                if (antwoord != null) {
                    VERKEER_LOG.info("Versturen antwoord: {}", antwoord);
                    stuurAntwoord(antwoord);
                    VERKEER_LOG.info("Gereed (antwoord verstuurd)");
                } else {
                    VERKEER_LOG.info("Gereed (geen te versturen antwoord)");
                }
            }
            LOG.info(FunctioneleMelding.SYNC_VERZOEK_VERWERKT);
        } catch (final BerichtLeesException ble) {
            LOG.error("Er is een fout opgetreden bij het lezen van een binnenkomend bericht.", ble);
            VERKEER_LOG.info("Gereed (bericht lees exceptie)", ble);
            throw new ServiceException(ble);
        }
    }

    /**
     * Handle the message.
     *
     * @param bericht
     *            input
     * @return output
     */
    // Note: package protected for test.
    SyncBericht verwerkBericht(final SyncBericht bericht) {
        SyncBericht antwoord = null;
        boolean berichtIsVerwerkt = false;

        for (final SynchronisatieBerichtService<SyncBericht, SyncBericht> berichtService : berichtServices) {
            if (berichtService.getVerzoekType().isAssignableFrom(bericht.getClass())) {
                try {
                    VERKEER_LOG.info("Het bericht wordt verwerkt door: {}", berichtService.getServiceNaam());
                    antwoord = berichtService.verwerkBericht(bericht);
                } catch (final
                    OngeldigePersoonslijstException
                    | BerichtSyntaxException e)
                {
                    /*
                     * Catch exception voor het robuust afhandelen van exceptions op applicatie interface niveau
                     */
                    LOG.error(
                        String.format(
                            "Er is een fout opgetreden bij het verwerken van het bericht (ID: %s; type: %s).",
                            bericht.getMessageId(),
                            bericht.getBerichtType()),
                        e);
                    throw new ServiceException(e);
                }
                berichtIsVerwerkt = true;
                break;
            }
        }

        if (!berichtIsVerwerkt) {
            LOG.error("Onbekend bericht ..." + bericht.getMessageId());
            throw new ServiceException("[Bericht {}]: Onbekend bericht type: " + bericht.getBerichtType());
        }

        return antwoord;
    }

    /*
     * ******************************************** Private methods ************************************************
     */

    private String bepaalBerichtReferentie(final Message message) throws BerichtLeesException {
        try {
            final String result = message.getStringProperty(JMSConstants.BERICHT_REFERENTIE);
            if (result == null || "".equals(result)) {
                throw new BerichtLeesException("De property '" + JMSConstants.BERICHT_REFERENTIE + "' in het JMS bericht is leeg.");
            }
            return result;
        } catch (final JMSException e) {
            throw new BerichtLeesException("Er kon geen waarde gevonden worden voor de property '"
                    + JMSConstants.BERICHT_REFERENTIE
                    + "' in het JMS bericht door een fout:", e);
        }
    }
}
