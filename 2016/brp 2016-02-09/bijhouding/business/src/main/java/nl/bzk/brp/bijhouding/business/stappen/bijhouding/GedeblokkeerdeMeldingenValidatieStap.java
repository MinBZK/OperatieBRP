/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bericht.kern.GedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import org.springframework.stereotype.Component;

/**
 * Deze stap checkt of de eventueel aanwezige gedeblokkeerde meldingen wel valide zijn.
 */
@Component
public class GedeblokkeerdeMeldingenValidatieStap {

    private static final String REGEL_CODE = "regelCode";

    @Inject
    private BijhoudingRegelService bijhoudingRegelService;

    /**
     * Voer de stap uit.
     * @param bericht het bijhoudingsbericht
     * @return de lijst van meldingen
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht) {
        final Set<ResultaatMelding> resultaatMeldingen = new HashSet<>();

        final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen =
            bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen();
        if (gedeblokkeerdeMeldingen != null) {
            for (final AdministratieveHandelingGedeblokkeerdeMeldingBericht admHandMelding : gedeblokkeerdeMeldingen) {
                final GedeblokkeerdeMeldingBericht gedeblokkeerdeMelding = admHandMelding.getGedeblokkeerdeMelding();
                // Check: een gedeblokkeerde melding moet een code bevatten.
                final RegelAttribuut regelAttribuut = gedeblokkeerdeMelding.getRegel();
                if (regelAttribuut == null) {
                    resultaatMeldingen.add(ResultaatMelding.builder()
                        .withMeldingTekst("Code is verplicht voor overrule.")
                        .withReferentieID(gedeblokkeerdeMelding.getCommunicatieID())
                        .withAttribuutNaam(REGEL_CODE)
                        .build());
                } else {
                    final Regel regel = regelAttribuut.getWaarde();
                    final RegelParameters regelParameters = bijhoudingRegelService.getRegelParametersVoorRegel(regel);
                    // Check: de regel parameters moeten bekend zijn.
                    if (regelParameters == null) {
                        resultaatMeldingen.add(ResultaatMelding.builder()
                            .withMeldingTekst("Overrule bevat onbekende code: " + regel.getCode() + ".")
                            .withReferentieID(gedeblokkeerdeMelding.getCommunicatieID())
                            .withAttribuutNaam(REGEL_CODE)
                            .build());
                    } else if (regelParameters.getSoortMelding() != SoortMelding.DEBLOKKEERBAAR) {
                        // Check: een gedeblokkeerde melding moet van toepassing zijn op een regel die gedeblokkeerd mag worden.
                        resultaatMeldingen.add(ResultaatMelding.builder()
                            .withMeldingTekst(String.format("code %s mag niet overruled worden.", regel.getCode()))
                            .withReferentieID(gedeblokkeerdeMelding.getCommunicatieID())
                            .withAttribuutNaam(REGEL_CODE)
                            .build());
                    }
                }
            }
        }

        return Resultaat.builder().withMeldingen(resultaatMeldingen).build();
    }

}
