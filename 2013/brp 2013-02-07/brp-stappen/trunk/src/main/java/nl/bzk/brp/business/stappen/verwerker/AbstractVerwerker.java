/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.verwerker;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.AbstractStappenResultaat;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Add documentation
 */
public abstract class AbstractVerwerker<O extends ObjectType, C extends StappenContext, R extends StappenResultaat,T extends Stap<O,C,R>> implements StappenVerwerker<O, C, R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractVerwerker.class);

    protected List<T> stappen;

    public List<T> getStappen() {
        if (stappen == null) {
            return null;
        }
        return Collections.unmodifiableList(stappen);
    }

    public void setStappen(List<T> nieuweStappen) {
        this.stappen = nieuweStappen;
    }

    @Override
    public R verwerk(final O onderwerp,final C context)
    {
        LOGGER.debug("Verwerker start verwerking object: {}", onderwerp);

        R resultaat = creeerResultaat(onderwerp, context);
        valideer(onderwerp, resultaat);

        if (resultaat.isSuccesvol()) {
            int laatsteStapIndex = voerStappenUit(onderwerp, context, resultaat);
            voerNaVerwerkingStappenUit(onderwerp, context, resultaat, laatsteStapIndex);
        }

        return resultaat;
    }


    /**
     * Voert de verschillende stappen uit, waarbij elke stap afzonderlijk wordt aangeroepen met het bericht dat
     * verwerkt dient te worden, en een lijst waarin meldingen opgeslagen kunnen worden. De verwerking gaat door
     * totdat een stap retourneert dat de verwerking dient te stoppen, een exceptie gooit of totdat alle stappen zijn
     * doorlopen. De index van de laatst verwerkte stap wordt geretourneerd.
     *
     * @param onderwerp Object dat verwerkt dient te worden.
     * @param context De context waarbinnen de stappen uitgevoerd wordt.
     * @param resultaat Resultaat met lijst van meldingen waarin elke stap meldingen kwijt kan.
     * @return de index van de laatst verwerkte stap.
     */
    protected int voerStappenUit(final O onderwerp, final C context, final R resultaat)
    {
        int stapInUitvoering = -1;

        if (stappen == null) {
            LOGGER.warn("Geen stappen geconfigureerd om uit te voeren");
        } else
        {
            for (T stap : stappen) {
                stapInUitvoering++;

                //final AbstractStap stap = (AbstractStap) berichtStap;
                LOGGER.debug("Verwerker start verwerking stap {} voor bericht {}", stap, onderwerp);
                try {
                    boolean stapResultaat = stap.voerStapUit(onderwerp, context, resultaat);
                    stapResultaat = correctiesNaStap(stapResultaat, onderwerp, context, resultaat);

                    if (stapResultaat == AbstractStap.STOPPEN || resultaat
                            .isFoutief())
                    {
                        LOGGER.warn("Logische fout/stop in uitvoerende stap {} gedetecteerd.",
                                    stapInUitvoering);
                        for (Melding m : ((AbstractStappenResultaat)resultaat).getMeldingen()) {
                            LOGGER.warn("{}:{}",m.getCode(), m.getOmschrijving());
                        }
                        break;
                    }
                } catch (Throwable t) {
                    resultaat.isFoutief();
                    ((AbstractStappenResultaat)resultaat).voegMeldingToe(new Melding(SoortMelding.FOUT,
                                                                                     MeldingCode.ALG0001,
                                                                                     "Onbekende fout opgetreden"));
                    LOGGER.error(String.format("Onbekende fout in uitvoerende stap %d gedetecteerd.",
                                               stapInUitvoering), t);
                    break;
                }
            }
        }
        return stapInUitvoering;
    }

    /**
     * .
     * @param stapResultaat .
     * @param bericht .
     * @param context .
     * @param resultaat .
     * @return .
     */
    protected boolean correctiesNaStap(final boolean stapResultaat, final O bericht,
                                       final C context, final R resultaat)
    {
        // standaard, doen we niets.
        return stapResultaat;
    }

    /**
     * Valideer of het onderwerp verwerkt kan worden.
     * @param onderwerp
     * @param resultaat
     */
    protected abstract  void valideer(final O onderwerp, final R resultaat);

    /**
     * Voer bewerkingen uit na het voltooien van de stap.
     * @param onderwerp
     * @param context
     * @param resultaat
     * @param laatsteStapIndex
     */
    protected abstract void voerNaVerwerkingStappenUit(final O onderwerp, final C context, final R resultaat,
                                                                                    final int laatsteStapIndex);

    /**
     * Maak een resultaat object aan waarin basis zaken van de context en onderwerp worden overgenomen.
     * @param onderwerp
     * @param context
     * @return
     */
    protected abstract R creeerResultaat(final O onderwerp, final C context);

}
