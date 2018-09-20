/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/** Standaard implementatie van de BerichtVerwerker. */
@Service
public class BerichtVerwerkerImpl implements BerichtVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtVerwerkerImpl.class);

    @Inject
    private BerichtResultaatFactory berichtResultaatFactory;

    private List<AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>> stappen;

    /** {@inheritDoc} */
    @Override
    public <T extends BerichtResultaat> T verwerkBericht(final BRPBericht bericht, final BerichtContext context) {
        LOGGER.debug("Berichtverwerker start verwerking bericht: {}", bericht);

        T berichtResultaat = berichtResultaatFactory.creeerBerichtResultaat(bericht, context);
        valideerBerichtOpVerplichteObjecten(bericht, berichtResultaat);

        if (berichtResultaat.getResultaatCode() == ResultaatCode.GOED) {
            int laatsteStapIndex = voerStappenUit(bericht, context, berichtResultaat);
            voerNaVerwerkingStappenUit(bericht, context, berichtResultaat, laatsteStapIndex);
        }

        return berichtResultaat;
    }

    /**
     * Valideert of een bericht de verplichte objecten bevat voor het type van de berichten. Als dat niet zo is,
     * wordt er een melding toegevoegd aan het resultaat en wordt het resultaat gemarkeerd als foutief.
     *
     * @param bericht het bericht dat gevalideerd dient te worden.
     * @param berichtResultaat het bericht resultaat waar eventuele meldingen aan toegevoegd dienen te worden en dat
     * op 'foutief' gezet wordt als het bericht niet valide is.
     */
    private void valideerBerichtOpVerplichteObjecten(final BRPBericht bericht,
        final BerichtResultaat berichtResultaat)
    {
        if (bericht instanceof AbstractBijhoudingsBericht) {
            valideerBijhoudingsBerichtOpVerplichteObjecten((AbstractBijhoudingsBericht) bericht, berichtResultaat);
        }
    }

    /**
     * Valideert of een bijhoudings bericht de verplichte objecten bevat zoals een Actie en daar in een Root object.
     * Als dat niet zo is, wordt er een melding toegevoegd aan het resultaat en wordt het resultaat gemarkeerd als
     * foutief.
     *
     * @param bericht het bericht dat gevalideerd dient te worden.
     * @param berichtResultaat het bericht resultaat waar eventuele meldingen aan toegevoegd dienen te worden en dat
     * op 'foutief' gezet wordt als het bericht niet valide is.
     */
    private void valideerBijhoudingsBerichtOpVerplichteObjecten(final AbstractBijhoudingsBericht bericht,
        final BerichtResultaat berichtResultaat)
    {
        // Een bijhoudings bericht dient minimaal een actie te hebben
        if (bericht.getBrpActies() == null || bericht.getBrpActies().isEmpty()) {
            berichtResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ACT0001));
            berichtResultaat.markeerVerwerkingAlsFoutief();
        } else {
            // Elke actie in een bericht dient minimaal een root object te hebben
            for (Actie actie : bericht.getBrpActies()) {
                if (actie.getRootObjecten() == null || actie.getRootObjecten().isEmpty()) {
                    berichtResultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ACT0002,
                        String.format("%s: %s", MeldingCode.ACT0002.getOmschrijving(),
                            actie.getSoort().getNaam()),
                            (Identificeerbaar) actie, bepaalAttribuut(actie)));
                    berichtResultaat.markeerVerwerkingAlsFoutief();
                }
            }
        }
    }

    /**
     * Retourneert het hoofd/root attribuut van de actie, op basis van diens soort..
     * @param actie de actie
     * @return het hoofd/root attribuut
     */
    private String bepaalAttribuut(final Actie actie) {
        final String attribuut;

        switch(actie.getSoort()) {
            case AANGIFTE_GEBOORTE:
            case ERKENNING_GEBOORTE:
                attribuut = "relatie";
                break;
            case REGISTRATIE_NATIONALITEIT:
            case VERHUIZING:
                attribuut = "persoon";
                break;
            default:
                attribuut = "persoon";
        }
        return attribuut;
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
                    boolean stapResultaat = stap.voerVerwerkingsStapUitVoorBericht(bericht, context, resultaat);

                    if (stapResultaat == AbstractBerichtVerwerkingsStap.STOP_VERWERKING || resultaat
                        .bevatVerwerkingStoppendeFouten())
                    {
                        resultaat.markeerVerwerkingAlsFoutief();
                        LOGGER.warn("Logische fout/stop in uitvoerende stap {} gedetecteerd.",
                            stapInUitvoering);
                        break;
                    }
                } catch (Throwable t) {
                    resultaat.markeerVerwerkingAlsFoutief();
                    resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR,
                        MeldingCode.ALG0001, "Onbekende fout opgetreden"));
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
                resultaat.markeerVerwerkingAlsFoutief();
                resultaat.voegMeldingToe(
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001, "Onbekende fout opgetreden")
                );
                LOGGER.error(String.format("Onbekende fout in naverwerking van stap %d gedetecteerd.", i), t);
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
