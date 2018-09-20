/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.business.jms.stap.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.business.jms.stap.AbstractBerichtVerwerkingsStap.StapResultaat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevMutAdmHandBerichtVerwerkerImpl implements LevMutAdmHandBerichtVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger(LevMutAdmHandBerichtVerwerkerImpl.class);

    private List<AbstractBerichtVerwerkingsStap> stappen;

    /**
     * {@inheritDoc}
     */
    @Override
    public void verwerkBericht(final LevMutAdmHandBerichtContext context) {
        int laatsteStapIndex = voerStappenUit(context);
        voerNaVerwerkingStappenUit(context, laatsteStapIndex);
    }

    /**
     * Voert de verschillende stappen uit, waarbij elke stap afzonderlijk wordt
     * aangeroepen met het bericht dat verwerkt dient te worden, en een lijst
     * waarin meldingen opgeslagen kunnen worden. De verwerking gaat door totdat
     * een stap retourneert dat de verwerking dient te stoppen, een exceptie
     * gooit of totdat alle stappen zijn doorlopen. De index van de laatst
     * verwerkte stap wordt geretourneerd.
     *
     * @param context   De context waarbinnen het bericht uitgevoerd wordt.
     * @return de index van de laatst verwerkte stap.
     */
    private int voerStappenUit(final LevMutAdmHandBerichtContext context) {
        int stapInUitvoering = -1;

        if (stappen == null) {
            LOGGER.warn("Geen stappen geconfigureerd om uit te voeren");
        } else {
            for (AbstractBerichtVerwerkingsStap stap : stappen) {
                stapInUitvoering++;
                //LOGGER.debug("Berichtverwerker start verwerking stap {} voor bericht {}", stap, context);
                try {
                    StapResultaat stapResultaat = stap.voerVerwerkingsStapUitVoorBericht(context);

                    if (stapResultaat == StapResultaat.STOP_VERWERKING) {
                        context.fout();
                        LOGGER.warn("Logische fout/stop in uitvoerende stap {} gedetecteerd.",
                                    stappen.get(stapInUitvoering).getClass().getSimpleName());
                        break;
                    }
                } catch (Throwable t) {
                    LOGGER.error(String.format("Onbekende fout in uitvoerende stap {} gedetecteerd.",
                                               stappen.get(stapInUitvoering).getClass().getSimpleName()), t);
                    context.fout();
                    break;
                }
            }
        }
        return stapInUitvoering;
    }

    /**
     * Voor alle stappen vanaf de opgegeven index en terug naar de eerste stap,
     * wordt de naverwerking van die stap uitgevoerd. Eventuele excepties in de
     * naverwerking van een stap worden gelogd en als fout toegevoegd aan het
     * bericht. Let wel dat als een exceptie optreedt in de naverwerking van een
     * stap, deze wordt afgevangen en de nog resterende naverwerkingsstappen nog
     * steeds worden uitgevoerd.
     *
     * @param context          De context waarbinnen het bericht verwerkt wordt.
     * @param laatsteStapIndex index van de als laatste uitgevoerde stap en waarmee qua
     *                         naverwerking dus begonnen
     */
    private void voerNaVerwerkingStappenUit(final LevMutAdmHandBerichtContext context, final int laatsteStapIndex) {
        for (int i = laatsteStapIndex; i >= 0; i--) {
            try {
                stappen.get(i).naVerwerkingsStapVoorBericht(context);
            } catch (Throwable t) {
                LOGGER.error(String.format("Onbekende fout in naverwerking van stap %d gedetecteerd.", i), t);
            }
        }
    }

    /**
     * Retourneert een onaanpasbare lijst van de stappen die deze service
     * doorloopt bij het verwerking van een bericht.
     *
     * @return de stappen.
     */
    public List<AbstractBerichtVerwerkingsStap> getStappen() {
        if (stappen == null) {
            return null;
        }
        return Collections.unmodifiableList(stappen);
    }

    /**
     * Zet de stappen die de service doorloopt in de verwerking van een bericht.
     *
     * @param stappen de stappen die de service doorloopt in de verwerking van een
     *                bericht.
     */
    public void setStappen(final List<AbstractBerichtVerwerkingsStap> stappen) {
        this.stappen = stappen;
    }
}
