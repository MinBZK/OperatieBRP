/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaatImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Standaard implementatie voor de BerichtVerwerker van BRP request-reply berichten.
 *
 * @param <B> Type bericht dat wordt verwerkt.
 * @param <C> Type berichtcontext
 * @param <T> Type verwerkingsresultaat.
 */
@Service
public abstract class AbstractBerichtVerwerker<B extends BerichtBericht, C extends BerichtContext,
        T extends BerichtVerwerkingsResultaatImpl>
        implements BerichtVerwerker<B, C, T>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBerichtVerwerker.class);
    private static final String ONBEKENDE_FOUT_OPGETREDEN = "Onbekende fout opgetreden";

    @Inject
    private BerichtResultaatFactory berichtResultaatFactory;

    private List<Stap<B, C, T>> stappen;

    /**
     * {@inheritDoc}
     */
    @Override
    public T verwerkBericht(final B bericht, final C context) {
        LOGGER.debug("Berichtverwerker start verwerking bericht: {} {}", bericht.getSoort(), bericht);

        final T berichtResultaat = berichtResultaatFactory.creeerBerichtResultaat(bericht, context);
        valideerBerichtOpVerplichteObjecten(bericht, berichtResultaat);

        if (!berichtResultaat.bevatStoppendeFouten()) {
            final int laatsteStapIndex = voerStappenUit(bericht, context, berichtResultaat);
            voerNaVerwerkingStappenUit(bericht, context, berichtResultaat, laatsteStapIndex);
        }


        return berichtResultaat;
    }

    /**
     * Valideert of een bericht de verplichte objecten bevat voor het type van de berichten. Als dat niet zo is,
     * wordt er een melding toegevoegd aan het resultaat en wordt het resultaat gemarkeerd als foutief.
     *
     * @param bericht          het bericht dat gevalideerd dient te worden.
     * @param berichtResultaat het bericht resultaat waar eventuele meldingen aan toegevoegd dienen te worden en dat
     *                         op 'foutief' gezet wordt als het bericht niet valide is.
     */
    protected abstract void valideerBerichtOpVerplichteObjecten(final B bericht,
                                                                final T berichtResultaat);

    /**
     * Voert de verschillende stappen uit, waarbij elke stap afzonderlijk wordt aangeroepen met het bericht dat
     * verwerkt dient te worden, en een lijst waarin meldingen opgeslagen kunnen worden. De verwerking gaat door
     * totdat een stap retourneert dat de verwerking dient te stoppen, een exceptie gooit of totdat alle stappen zijn
     * doorlopen. De index van de laatst verwerkte stap wordt geretourneerd.
     *
     * @param bericht   Bericht dat verwerkt dient te worden.
     * @param context   De context waarbinnen het bericht uitgevoerd wordt.
     * @param resultaat Resultaat met lijst van meldingen waarin elke stap meldingen kwijt kan.
     * @return de index van de laatst verwerkte stap.
     */
    private int voerStappenUit(final B bericht, final C context,
                               final T resultaat)
    {
        int stapInUitvoering = -1;

        if (stappen == null) {
            LOGGER.warn("Geen stappen geconfigureerd om uit te voeren");
        } else {
            for (final Stap<B, C, T> stap : stappen) {
                stapInUitvoering++;
                LOGGER.debug("Start verwerking stap {} voor bericht {}", stap.getClass().getSimpleName(),
                    bericht.getClass().getSimpleName());
                try {
                    boolean stapResultaat = stap.voerStapUit(bericht, context, resultaat);
                    stapResultaat = correctiesNaStap(stapResultaat, bericht, context, resultaat);

                    if (stapResultaat == Stap.STOPPEN
                            || resultaat.bevatVerwerkingStoppendeFouten())
                    {
                        LOGGER.warn("Logische fout/stop in uitvoerende stap {} gedetecteerd.", stapInUitvoering);
                        for (Melding m : resultaat.getMeldingen()) {
                            LOGGER.warn("{}:{}", m.getRegel().getCode(), m.getMeldingTekst());
                        }
                        break;
                    }
                } catch (Throwable t) {
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, ONBEKENDE_FOUT_OPGETREDEN));
                    LOGGER.error("Onbekende fout in uitvoerende stap ({}) {} gedetecteerd.", stap.getClass().getSimpleName(), stapInUitvoering, t);
                    break;
                }
            }
        }
        return stapInUitvoering;
    }


    /**
     * Voert correcties uit na stap.
     *
     * @param stapResultaat status van het resultaat na uitvoer stappen.
     * @param bericht       inkomend bericht.
     * @param context       alle objecten gedefinieerd in bericht.
     * @param resultaat     resultaat na verwerken stappen.
     * @return .
     */
    protected boolean correctiesNaStap(final boolean stapResultaat, final B bericht,
                                       final C context, final T resultaat)
    {
        // standaard, doen we niets mee.
        return stapResultaat;
    }

    /**
     * Voor alle stappen vanaf de opgegeven index en terug naar de eerste stap, wordt de naverwerking van die stap
     * uitgevoerd. Eventuele excepties in de naverwerking van een stap worden gelogd en als fout toegevoegd aan het
     * bericht. Let wel dat als een exceptie optreedt in de naverwerking van een stap, deze wordt afgevangen en de
     * nog resterende naverwerkingsstappen nog steeds worden uitgevoerd.
     *
     * @param bericht          Bericht dat verwerkt wordt.
     * @param context          De context waarbinnen het bericht verwerkt wordt.
     * @param resultaat        Resultaat met lijst van meldingen waarin elke naverwerkingsstap meldingen kwijt kan.
     * @param laatsteStapIndex index van de als laatste uitgevoerde stap en waarmee qua naverwerking dus begonnen
     */
    private void voerNaVerwerkingStappenUit(final B bericht, final C context,
                                            final T resultaat, final int laatsteStapIndex)
    {
        for (int i = laatsteStapIndex; i >= 0; i--) {
            try {
                stappen.get(i).voerNabewerkingStapUit(bericht, context, resultaat);
            } catch (Exception e) {
                resultaat.voegMeldingToe(
                        new Melding(SoortMelding.FOUT, Regel.ALG0001, ONBEKENDE_FOUT_OPGETREDEN)
                );
                LOGGER.error("Onbekende fout in naverwerking van stap {} gedetecteerd.", i, e);
            }
        }
    }


    /**
     * Retourneert een onaanpasbare lijst van de stappen die deze service doorloopt bij het verwerking van een bericht.
     *
     * @return de stappen.
     */
    public List<Stap<B, C, T>> getStappen() {
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
    public void setStappen(final List<Stap<B, C, T>> stappen) {
        this.stappen = stappen;
    }


    @Override
    public T verwerk(final B onderwerp, final C context) {
        return verwerkBericht(onderwerp, context);
    }
}
