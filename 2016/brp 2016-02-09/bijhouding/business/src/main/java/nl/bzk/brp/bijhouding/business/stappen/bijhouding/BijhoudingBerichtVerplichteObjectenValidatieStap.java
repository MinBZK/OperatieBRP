/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;


/**
 * Valideert of een bijhoudingsbericht de verplichte objecten bevat zoals een Actie en daar in een Root object. Als
 * dat niet zo is, resulteert dat in meldingen.
 */
@Component
public class BijhoudingBerichtVerplichteObjectenValidatieStap {

    private static final String PERSOON                   = "persoon";

    /**
     * Voert de stap uit.
     * @param bericht bijhoudingsbericht
     * @return resultaat
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht) {
        final Set<ResultaatMelding> resultaatMeldingen = new HashSet<>();
        // Een bijhoudingsbericht dient minimaal een actie te hebben
        final BerichtStandaardGroepBericht berichtStandaard = bericht.getStandaard();
        if (berichtStandaard.getAdministratieveHandeling() == null
            || CollectionUtils.isEmpty(berichtStandaard.getAdministratieveHandeling().getActies()))
        {
            resultaatMeldingen.add(ResultaatMelding.builder().withRegel(Regel.ACT0001).withAttribuutNaam("").build());

        } else {
            resultaatMeldingen.addAll(controleerOpMinimaalEenRootObjectPerActie(berichtStandaard));
        }

        return Resultaat.builder().withMeldingen(resultaatMeldingen).build();
    }

    private List<ResultaatMelding> controleerOpMinimaalEenRootObjectPerActie(final BerichtStandaardGroepBericht berichtStandaard) {
        final List<ResultaatMelding> resultaatMeldingen = new ArrayList<>();

        for (final ActieBericht actie : berichtStandaard.getAdministratieveHandeling().getActies()) {
            if (actie.getRootObject() == null) {
                resultaatMeldingen.add(ResultaatMelding.builder()
                    .withRegel(Regel.ACT0002)
                    .withMeldingTekst(String.format("%s: %s", Regel.ACT0002.getOmschrijving(), actie.getSoort().getWaarde().getNaam()))
                    .withReferentieID(actie.getCommunicatieID())
                    .withAttribuutNaam(bepaalAttribuut(actie)).build());
            }
        }

        return resultaatMeldingen;
    }

    /**
     * Retourneert het hoofd/root attribuut van de actie, op basis van diens soort.
     *
     * @param actie de actie
     * @return het hoofd/root attribuut
     */
    private String bepaalAttribuut(final Actie actie) {
        final String attribuut;

        switch (actie.getSoort().getWaarde()) {
            case REGISTRATIE_GEBOORTE:
            case REGISTRATIE_OUDER:
                attribuut = "relatie";
                break;
            case REGISTRATIE_NATIONALITEIT:
            case REGISTRATIE_ADRES:
                attribuut = PERSOON;
                break;
            default:
                attribuut = PERSOON;
        }
        return attribuut;
    }




}
