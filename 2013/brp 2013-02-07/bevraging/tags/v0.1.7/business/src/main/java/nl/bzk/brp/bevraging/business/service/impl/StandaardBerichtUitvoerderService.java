/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import static nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap.STOP_VERWERKING;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService;
import nl.bzk.brp.bevraging.business.service.exception.BerichtUitvoerderServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.stereotype.Service;


/**
 * Standaard service implementatie voor het uitvoeren van een bericht. Deze implementatie voert sequentieel de
 * verschillende (standaard) stappen uit die doorlopen moeten worden voor alle berichten. Een van deze stappen is de
 * werkelijke uitvoering van het bericht, waarbij de overige stappen zaken regelen als protocollering, autorisatie etc.
 */
@Service
public class StandaardBerichtUitvoerderService implements BerichtUitvoerderService {

    private static final Logger          LOGGER = LoggerFactory.getLogger(StandaardBerichtUitvoerderService.class);

    private List<BerichtVerwerkingsStap> stappen;

    /**
     * {@inheritDoc} <br/>
     * Hiervoor wordt elke afzonderlijke stap uitgevoerd, tot verwerking eindigt (functioneel of exceptioneel), waarna
     * daarna voor elke uitgevoerde stap de naverwerking wordt aangeroepen.
     */
    @Override
    public final <T extends BerichtAntwoord> T voerBerichtUit(final BerichtVerzoek<T> verzoek,
            final BerichtContext context)
    {
        if (verzoek == null || context == null) {
            LOGGER.error("Kan bericht niet uitvoeren daar verzoek en/of context null is. "
                + "(bericht: {0} - context: {1})", verzoek, context);
            throw new BerichtUitvoerderServiceException("Verzoek en/of context is null.");
        }

        T antwoord = maakStandaardLeegAntwoord(verzoek);
        if (!isBerichtCorrectGeinitialiseerd(context)) {
            LOGGER.error("Kan bericht niet uitvoeren daar deze niet correct is geinitialiseerd. "
                + "(bericht: {0} - context: {1})", verzoek, context);
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                    BerichtVerwerkingsFoutZwaarte.SYSTEEM, "Bericht niet correct geinitialiseerd door het systeem."));
        } else {
            int laatsteStapIndex = voerStappenUit(verzoek, context, antwoord);
            voerNaVerwerkingStappenUit(laatsteStapIndex, verzoek, context, antwoord);
        }

        return antwoord;
    }

    /**
     * Instantieert een leeg {@link BerichtAntwoord} welke van toepassing is voor het opgegeven {@link BerichtVerzoek}.
     *
     * @param <T> Type van het antwoordbericht behorende bij het verzoek.
     * @param verzoek het verzoek waar een lege antwoord instantie voor moet worden gecreeerd.
     * @return een voor het verzoek geldend, leeg antwoord object.
     */
    private <T extends BerichtAntwoord> T maakStandaardLeegAntwoord(final BerichtVerzoek<T> verzoek) {
        try {
            return verzoek.getAntwoordClass().newInstance();
        } catch (IllegalAccessException e) {
            LOGGER.error("Kan geen antwoord bericht instantieren voor verzoek {0}", verzoek.getClass().getSimpleName());
            throw new BerichtUitvoerderServiceException(e);
        } catch (InstantiationException e) {
            LOGGER.error("Kan geen antwoord bericht instantieren voor verzoek {0}", verzoek.getClass().getSimpleName());
            throw new BerichtUitvoerderServiceException(e);
        }
    }

    /**
     * Controleert of de context correct is geinitialiseerd: de context dient voorzien te zijn van een partij id en een
     * bericht id.
     *
     * @param context de context die wordt gecontroleerd.
     * @return een indicatie of het bericht correct is geinitialiseerd of niet.
     */
    private boolean isBerichtCorrectGeinitialiseerd(final BerichtContext context)
    {
        return context.getPartijId() != null && context.getIngaandBerichtId() != null;
    }

    /**
     * Voert de verschillende stappen uit, waarbij elke stap afzonderlijk wordt aangeroepen met het bericht verzoek dat
     * verwerkt dient te worden, de context waarbinnen het verwerkt wordt en het antwoord. De verwerking gaat door
     * totdat een stap retourneert dat de verwerking dient te stoppen, een exceptie gooit of totdat alle stappen zijn
     * doorlopen. De index van de laatst verwerkte stap wordt geretourneerd.
     *
     * @param verzoek het verzoek dat dient verwerkt te worden.
     * @param context de context waarin het verzoek wordt uitgevoerd.
     * @param antwoord het reeds geinitialiseerde antwoord object wat gevuld wordt door de verschillende stappen.
     * @param <T> Type van het antwoordbericht behorende bij het verzoek.
     * @return de index van de laatst verwerkte stap.
     */
    private <T extends BerichtAntwoord> int voerStappenUit(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        int stapInUitvoering = -1;

        if (stappen == null) {
            LOGGER.warn("Geen stappen geconfigureerd om uit te voeren");
        } else {
            for (BerichtVerwerkingsStap stap : stappen) {
                stapInUitvoering++;
                try {
                    if (stap.voerVerwerkingsStapUitVoorBericht(verzoek, context, antwoord) == STOP_VERWERKING) {
                        LOGGER.warn("Logische fout/stop in uitvoerende stap {} gedetecteerd.", stapInUitvoering);
                        if (context.getBusinessTransactionStatus() != null) {
                            context.getBusinessTransactionStatus().setRollbackOnly();
                        }
                        break;
                    }
                } catch (Throwable t) {
                    if (isDatabaseTimeOutException(t)) {
                        antwoord.voegFoutToe(new BerichtVerwerkingsFout(
                                BerichtVerwerkingsFoutCode.BRPE0009_BEPALEN_RESULTAAT_DUURDE_TE_LANG,
                                BerichtVerwerkingsFoutZwaarte.WAARSCHUWING,
                                "Resultaat kon niet binnen de opgegeven tijd worden bepaald."));
                    } else {
                        antwoord.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                                BerichtVerwerkingsFoutZwaarte.SYSTEEM, "Onbekende Fout opgetreden"));
                    }

                    LOGGER.error("Onbekende fout in uitvoerende stap {} gedetecteerd.", stapInUitvoering, t);
                    if (context.getBusinessTransactionStatus() != null) {
                        context.getBusinessTransactionStatus().setRollbackOnly();
                    }
                    break;
                }
            }
        }
        return stapInUitvoering;
    }

    /**
     * Bepaalt of een Throwable veroorzaakt is door een time out in de database.
     * @param t De throwable waarvoor het bepaald wordt.
     * @return true in geval er een time out is opgetreden in de database.
     */
    private boolean isDatabaseTimeOutException(final Throwable t) {
        return t instanceof HibernateJdbcException
                && "57014".equals(((HibernateJdbcException) t).getSQLException().getSQLState());
    }

    /**
     * Voor alle stappen vanaf de opgegeven index en terug naar de eerste stap, wordt de naverwerking van die stap
     * uitgevoerd. Eventuele excepties in de naverwerking van een stap worden gelogd en als fout toegevoegd aan het
     * bericht. Let wel dat als een exceptie optreedt in de naverwerking van een stap, deze wordt afgevangen en de
     * nog resterende naverwerkingsstappen nog steeds worden uitgevoerd.
     *
     * @param laatsteStapIndex index van de als laatste uitgevoerde stap en waarmee qua naverwerking dus begonnen
     *            moet worden.
     * @param verzoek het verzoek dat dient verwerkt te worden.
     * @param context de context waarin het verzoek wordt uitgevoerd.
     * @param antwoord het reeds geinitialiseerde antwoord object wat gevuld wordt door de verschillende stappen.
     * @param <T> Type van het antwoordbericht behorende bij het verzoek.
     */
    private <T extends BerichtAntwoord> void voerNaVerwerkingStappenUit(final int laatsteStapIndex,
            final BerichtVerzoek<T> verzoek, final BerichtContext context, final T antwoord)
    {
        for (int i = laatsteStapIndex; i >= 0; i--) {
            try {
                stappen.get(i).naVerwerkingsStapVoorBericht(verzoek, context);
            } catch (Throwable t) {
                antwoord.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                        BerichtVerwerkingsFoutZwaarte.INFO, "Onbekende Fout opgetreden"));
                StandaardBerichtUitvoerderService.LOGGER.error(
                        "Onbekende fout in naverwerking van stap {} gedetecteerd.", i, t);
            }
        }
    }

    /**
     * Retourneert een onaanpasbare lijst van de stappen die deze service doorloopt bij het verwerking van een bericht.
     *
     * @return de stappen.
     */
    public List<BerichtVerwerkingsStap> getStappen() {
        if (stappen == null) {
            return null;
        }
        return Collections.unmodifiableList(stappen);
    }

    /**
     * Zet de stappen die de service doorloopt in de verwerking van een bericht.
     *
     * @param stappen de stappen die de service doorloopt in de verwerking van een bericht.
     */
    public void setStappen(final List<BerichtVerwerkingsStap> stappen) {
        this.stappen = stappen;
    }

}
