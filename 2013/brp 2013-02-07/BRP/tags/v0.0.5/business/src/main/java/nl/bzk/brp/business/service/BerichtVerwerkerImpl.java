/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/** Standaard implementatie van de BerichtVerwerker. */
@Service
public class BerichtVerwerkerImpl implements BerichtVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtVerwerkerImpl.class);

    private List<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>> stappen;

    /** {@inheritDoc} */
    @Transactional
    @Override
    public void verwerkBericht(final BRPBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat)
    {
        LOGGER.debug("Berichtverwerker start verwerking bericht: {}", bericht);

        int laatsteStapIndex = voerStappenUit(bericht, context, resultaat);
        voerNaVerwerkingStappenUit(bericht, context, resultaat, laatsteStapIndex);

    }

    /**
     * Voert de verschillende stappen uit, waarbij elke stap afzonderlijk wordt aangeroepen met het bericht dat
     * verwerkt dient te worden, en een lijst waarin meldingen opgeslagen kunnen worden. De verwerking gaat door
     * totdat een stap retourneert dat de verwerking dient te stoppen, een exceptie gooit of totdat alle stappen zijn
     * doorlopen. De index van de laatst verwerkte stap wordt geretourneerd.
     *
     * @param bericht Bericht dat verwerkt dient te worden.
     * @param context De context waarbinnen het bericht uitgevoerd wordt.
     * @param resultaat Resultaat met lijst van meldingen waarin elke stap meldingen kwijt kan.
     * @return de index van de laatst verwerkte stap.
     */
    private int voerStappenUit(final BRPBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat)
    {
        int stapInUitvoering = -1;

        if (stappen == null) {
            LOGGER.warn("Geen stappen geconfigureerd om uit te voeren");
        } else {
            for (AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> stap : stappen) {
                stapInUitvoering++;
                LOGGER.debug("Berichtverwerker start verwerking stap {} voor bericht {}", stap, bericht);
                try {
                    if (stap.voerVerwerkingsStapUitVoorBericht(bericht, context, resultaat)
                        == AbstractBerichtVerwerkingsStap.STOP_VERWERKING)
                    {
                        LOGGER.warn("Logische fout/stop in uitvoerende stap {} gedetecteerd.",
                            stapInUitvoering);
                        break;
                    }
                } catch (Throwable t) {
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR,
                        MeldingCode.ALG0001, "Onbekende Fout opgetreden"));
                    LOGGER.error(String.format("Onbekende fout in uitvoerende stap %d gedetecteerd.",
                        stapInUitvoering), t);
                    break;
                }
            }
        }
        return stapInUitvoering;
    }

    /**
     * Voor alle stappen vanaf de opgegeven index en terug naar de eerste stap, wordt de naverwerking van die stap
     * uitgevoerd. Eventuele excepties in de naverwerking van een stap worden gelogd en als fout toegevoegd aan het
     * bericht. Let wel dat als een exceptie optreedt in de naverwerking van een stap, deze wordt afgevangen en de
     * nog resterende naverwerkingsstappen nog steeds worden uitgevoerd.
     *
     * @param bericht Bericht dat verwerkt wordt.
     * @param context De context waarbinnen het bericht verwerkt wordt.
     * @param resultaat Resultaat met lijst van meldingen waarin elke naverwerkingsstap meldingen kwijt kan.
     * @param laatsteStapIndex index van de als laatste uitgevoerde stap en waarmee qua naverwerking dus begonnen
     */
    private void voerNaVerwerkingStappenUit(final BRPBericht bericht, final BerichtContext context,
        final BerichtResultaat resultaat, final int laatsteStapIndex)
    {
        for (int i = laatsteStapIndex; i >= 0; i--) {
            try {
                stappen.get(i).naVerwerkingsStapVoorBericht(bericht, context, resultaat);
            } catch (Throwable t) {
                resultaat.voegMeldingToe(
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, "Onbekende Fout opgetreden")
                );
                LOGGER.error(String.format("Onbekende fout in naverwerking van stap %d gedetecteerd.", i));
            }
        }
    }

    /**
     * Retourneert een onaanpasbare lijst van de stappen die deze service doorloopt bij het verwerking van een bericht.
     *
     * @return de stappen.
     */
    public List<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>> getStappen() {
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
    public void setStappen(final List<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>> stappen) {
        this.stappen = stappen;
    }
}
