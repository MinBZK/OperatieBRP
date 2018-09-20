/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.verwerker;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Abstracte basis verwerker waarin alle stappen worden doorgelopen om te verwerken.
 *
 * @param <O> Type Objecttype waar deze stap voor wordt uitgevoerd
 * @param <C> Type Context waarbinnen de stap wordt uitgevoerd
 * @param <R> Type Resultaat waar deze stap eventuele resultaten en meldingen in opslaat
 * @param <T> Type stappen die kunnen worden uitgevoerd voor objectType {@link O}, in context {@link C} met resultaat
 *            {@link R}
 */
public abstract class AbstractVerwerker<O extends BrpObject, C extends StappenContext, R extends StappenResultaat, T extends Stap<O, C, R>>
        implements StappenVerwerker<O, C, R>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private List<T>             stappen;

    /**
     * Geef de lijst van geconfigureerde stappen terug met het type T.
     *
     * @return de lijst van stappen
     */
    public final List<T> getStappen() {
        if (stappen == null) {
            return null;
        }
        return Collections.unmodifiableList(stappen);
    }

    /**
     * Zet de lijst van stappen voor deze verwerker.
     *
     * @param nieuweStappen stappen om te zetten
     */
    public final void setStappen(final List<T> nieuweStappen) {
        this.stappen = nieuweStappen;
    }

    @Override
    public R verwerk(final O onderwerp, final C context) {
        LOGGER.debug("Verwerker start verwerking object: {}", onderwerp);

        final R resultaat = creeerResultaat(onderwerp, context);

        if (resultaat.isSuccesvol()) {
            final int laatsteStapIndex = voerStappenUit(onderwerp, context, resultaat);
            voerNaVerwerkingStappenUit(onderwerp, context, resultaat, laatsteStapIndex);
        }

        return resultaat;
    }

    /**
     * Voert de verschillende stappen uit, waarbij elke stap afzonderlijk wordt aangeroepen met het bericht dat verwerkt
     * dient te worden, en een lijst
     * waarin meldingen opgeslagen kunnen worden. De verwerking gaat door totdat een stap retourneert dat de verwerking
     * dient te stoppen, een exceptie
     * gooit of totdat alle stappen zijn doorlopen. De index van de laatst verwerkte stap wordt geretourneerd.
     *
     * @param onderwerp Object dat verwerkt dient te worden
     * @param context De context waarbinnen de stappen uitgevoerd wordt
     * @param resultaat Resultaat met lijst van meldingen waarin elke stap meldingen kwijt kan
     * @return de index van de laatst verwerkte stap
     */
    @SuppressWarnings({ "PMD.AvoidCatchingGenericException", "checkstyle:illegalcatch" })
    // REDEN: Aansturing van het stappen mechanisme. Als er in een van de stappen een onverwachte fout optreedt, dient
    // dit hier afgehandeld te worden.
    protected final int voerStappenUit(final O onderwerp, final C context, final R resultaat)
    {
        int stapInUitvoering = -1;

        if (stappen == null) {
            LOGGER.warn("Geen stappen geconfigureerd om uit te voeren");
        } else {

            LOGGER.debug("Begin verwerking van {} stappen", stappen.size());

            for (final T stap : stappen) {
                stapInUitvoering++;

                LOGGER.debug("Verwerker start verwerking stap {} voor bericht {}", stap.getClass().getSimpleName(), onderwerp.getClass().getSimpleName());
                try {
                    boolean stapResultaat = stap.voerStapUit(onderwerp, context, resultaat);
                    stapResultaat = correctiesNaStap(stapResultaat, onderwerp, context, resultaat);

                    if (stapResultaat == Stap.STOPPEN) {
                        break;
                    } else if (resultaat.isFoutief()) {
                        LOGGER.warn("Logische fout/stop in uitvoerende stap {} gedetecteerd.", stapInUitvoering);
                        for (final Melding m : resultaat.getMeldingen()) {
                            LOGGER.warn("{}:{}", m.getRegel(), m.getMeldingTekst());
                        }
                        break;
                    }
                } catch (final Throwable t) {
                    // Hier is bewust een catch opgenomen van Throwable om te voorkomen dat onverwachte excepties
                    // kunnen worden doorgegooid. Er wordt een melding voor aangemaakt, die het proces netjes stopped
                    // met een algemene foutmelding.

                    resultaat
                            .voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Onbekende fout opgetreden"));
                    LOGGER.error("Onbekende fout in uitvoerende stap {} gedetecteerd.", stapInUitvoering, t);
                    break;
                }
            }
        }
        return stapInUitvoering;
    }

    /**
     * Voer correcties uit na de stap naar aanleiding van het eerdere stapResultaat.
     *
     * @param stapResultaat Resultaat van de uitvoer van de stap
     * @param bericht onderwerp van de stap
     * @param context de context waarmee de stap wordt uitgevoerd
     * @param resultaat het resultaat waarin de meldingen worden opgenomen
     * @return een boolean die aangeeft of de correcties na de stap succesvol zijn uitgevoerd (true) of niet (false)
     */
    protected boolean
            correctiesNaStap(final boolean stapResultaat, final O bericht, final C context, final R resultaat)
    {
        // standaard, doen we niets.
        return stapResultaat;
    }

    /**
     * Voor alle stappen vanaf de opgegeven index en terug naar de eerste stap, wordt de naverwerking van die stap
     * uitgevoerd. Eventuele excepties in de
     * naverwerking van een stap worden gelogd en als fout toegevoegd aan het bericht. Let wel dat als een exceptie
     * optreedt in de naverwerking van een
     * stap, deze wordt afgevangen en de nog resterende naverwerkingsstappen nog steeds worden uitgevoerd.
     *
     * @param onderwerp Het onderwerp waarmee de stap verwerking wordt gedaan
     * @param context De context waarbinnen het bericht verwerkt wordt
     * @param resultaat Resultaat met lijst van meldingen waarin elke naverwerkingsstap meldingen kwijt kan
     * @param laatsteStapIndex de index van de laatste uitgeveorde stap waarna de 'terugweg' wordt ingezet
     */
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    protected final void voerNaVerwerkingStappenUit(final O onderwerp, final C context, final R resultaat,
            final int laatsteStapIndex)
    {
        LOGGER.debug("Begin naverwerking stappen");
        for (int i = laatsteStapIndex; i >= 0; i--) {
            try {
                stappen.get(i).voerNabewerkingStapUit(onderwerp, context, resultaat);
            } catch (final Exception e) {
                // Hier is bewust een catch opgenomen van Exception om te voorkomen dat onverwachte excepties
                // kunnen worden doorgegooid. Er wordt een melding voor aangemaakt, die het proces netjes
                // stopt met een algemene foutmelding.
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                        "Onbekende fout opgetreden in naverwerkingstap"));
                LOGGER.error("Onbekende fout in naverwerking van stap {} gedetecteerd.", i, e);
            }
        }
    }

    /**
     * Maak een resultaat object aan waarin basis zaken van de context en onderwerp worden overgenomen.
     *
     * @param onderwerp Het onderwerp van de stap met de gegevens waarop de stap moet worden uitgevoerd
     * @param context Het context object waarin gegevens tussen stappen worden uitgewisseld
     * @return Een nieuw resultaat object voor een stap
     */
    protected abstract R creeerResultaat(final O onderwerp, final C context);

}
