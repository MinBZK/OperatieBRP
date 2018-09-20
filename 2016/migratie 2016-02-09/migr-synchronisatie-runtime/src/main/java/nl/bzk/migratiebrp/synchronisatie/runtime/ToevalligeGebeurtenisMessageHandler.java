/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime;

import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.brp.AbstractBrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.brp.generated.GroepBerichtResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeBerichtResultaatBijhouding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjecttypeMelding;
import nl.bzk.migratiebrp.bericht.model.brp.generated.VerwerkingsresultaatNaamS;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.runtime.exception.ServiceException;
import nl.bzk.migratiebrp.synchronisatie.runtime.util.MessageId;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;

/**
 * Message handler voor BRP afnemerindicatie antwoorden. Deze worden 'vertaald' en doorgestuurd naar ISC.
 */
public final class ToevalligeGebeurtenisMessageHandler extends AbstractMessageHandler implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Logger VERKEER_LOG = LoggerFactory.getBerichtVerkeerLogger();

    @Override
    public void onMessage(final Message message) {
        try (final MDCCloser verwerkingCloser = MDC.startVerwerking()) {
            VERKEER_LOG.info("[Start verwerken binnenkomend bericht ...");
            final String correlatieReferentie = message.getStringProperty(JMSConstants.CORRELATIE_REFERENTIE);

            try (MDCCloser correlatieCloser = MDC.put(MDCVeld.SYNC_CORRELATIE_REFERENTIE, correlatieReferentie)) {
                VERKEER_LOG.info("Lees bericht inhoud ...");
                final String berichtInhoud = bepaalBerichtInhoud(message);

                LOG.info("CorrelatieId=" + correlatieReferentie + " inhoud: " + berichtInhoud);

                VERKEER_LOG.info("Parse bericht inhoud ...");
                final Object vertaaldBericht = BrpBerichtFactory.SINGLETON.getBericht(berichtInhoud);

                SyncBericht resultaat;
                if (vertaaldBericht instanceof nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht) {
                    final nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht brpOngeldigBericht =
                            (nl.bzk.migratiebrp.bericht.model.brp.impl.OngeldigBericht) vertaaldBericht;
                    resultaat = new OngeldigBericht(brpOngeldigBericht.getBericht(), brpOngeldigBericht.getMelding());
                    resultaat.setMessageId(MessageId.generateSyncMessageId());
                    resultaat.setCorrelationId(correlatieReferentie);
                } else {
                    VERKEER_LOG.info("Verwerk bericht inhoud ...");
                    resultaat = verwerkAntwoord((AbstractBrpBericht) vertaaldBericht, correlatieReferentie);
                }

                VERKEER_LOG.info("Versturen antwoord");
                stuurAntwoord(resultaat);
                VERKEER_LOG.info("Gereed (antwoord verstuurd)");
            }
            LOG.info(FunctioneleMelding.SYNC_TOEVALLIGEGEBEURTENIS_VERWERKT);

        } catch (final
            JMSException
            | BerichtLeesException e)
        {
            LOG.error("Er is een fout opgetreden bij het lezen van een binnenkomend bericht. Er is geen antwoord gestuurd.", e);
            VERKEER_LOG.info("Gereed (bericht lees exceptie)", e);
            throw new ServiceException(e);
        }
    }

    private SyncBericht verwerkAntwoord(final AbstractBrpBericht brpAntwoordBericht, final String correlatieId) {
        SyncBericht resultaat;

        final ObjecttypeBerichtResultaatBijhouding antwoordBijhouding = (ObjecttypeBerichtResultaatBijhouding) brpAntwoordBericht.getInhoud();
        if (antwoordBijhouding.getResultaat() == null
            || antwoordBijhouding.getResultaat().getValue() == null
            || antwoordBijhouding.getResultaat().getValue().getVerwerking() == null)
        {
            final String foutmelding;
            if (antwoordBijhouding.getMeldingen() != null && !antwoordBijhouding.getMeldingen().getValue().getMelding().isEmpty()) {
                foutmelding = antwoordBijhouding.getMeldingen().getValue().getMelding().get(0).getMelding().getValue().getValue();
            } else {
                foutmelding = "(geen melding uit BRP)";
            }
            throw new ServiceException("Geen verwerkingsresultaat uit BRP: " + foutmelding);
        } else {
            final VerwerkToevalligeGebeurtenisAntwoordBericht antwoordBericht = new VerwerkToevalligeGebeurtenisAntwoordBericht();
            resultaat = antwoordBericht;

            if (VerwerkingsresultaatNaamS.FOUTIEF.equals(antwoordBijhouding.getResultaat().getValue().getVerwerking().getValue().getValue())) {
                try {
                    final FoutredenType foutreden = bepaalFoutcode(antwoordBijhouding);
                    antwoordBericht.setStatus(StatusType.FOUT);
                    antwoordBericht.setFoutreden(foutreden);
                    if (FoutredenType.B.equals(foutreden)) {
                        antwoordBericht.setGemeentecode(
                            antwoordBijhouding.getMeldingen().getValue().getMelding().get(0).getMelding().getValue().getValue());
                    }

                } catch (final OnbekendFouteRegel e) {
                    throw new ServiceException(e);
                }
            } else {
                antwoordBericht.setStatus(StatusType.OK);
            }
        }
        resultaat.setMessageId(MessageId.generateSyncMessageId());
        resultaat.setCorrelationId(correlatieId);
        return resultaat;
    }

    private FoutredenType bepaalFoutcode(final ObjecttypeBerichtResultaatBijhouding antwoord) throws OnbekendFouteRegel {
        FoutredenType foutcode = null;

        final GroepBerichtResultaat resultaat = antwoord.getResultaat().getValue();
        if (resultaat != null && VerwerkingsresultaatNaamS.FOUTIEF.equals(resultaat.getVerwerking().getValue().getValue())) {
            final List<ObjecttypeMelding> meldingenLijst = antwoord.getMeldingen().getValue().getMelding();
            final String regel = antwoord.getMeldingen().getValue().getMelding().get(0).getRegelCode().getValue().getValue();
            switch (regel) {
                case "GEBLOKKEERD":
                    foutcode = FoutredenType.B;
                    break;

                case "BRAL9003":
                    foutcode = FoutredenType.O;
                    break;

                case "NIET ACTUEEL":
                    foutcode = FoutredenType.N;
                    break;
                default:
                    final StringBuilder meldingen = new StringBuilder();
                    final Iterator<ObjecttypeMelding> meldingIterator = meldingenLijst.iterator();
                    while (meldingIterator.hasNext()) {
                        meldingen.append(meldingIterator.next());
                        if (meldingIterator.hasNext()) {
                            meldingen.append(",");
                        }
                    }
                    throw new OnbekendFouteRegel(meldingen.toString());
            }
        }

        return foutcode;
    }

    /**
     * Onbekend fout.
     */
    public static final class OnbekendFouteRegel extends Exception {
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         *
         * @param regel
         *            regel
         */
        OnbekendFouteRegel(final String regel) {
            super("BRP bedrijfsregel " + regel + " kan niet worden gemapped als fout.");
        }
    }
}
