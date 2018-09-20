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
import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import org.springframework.stereotype.Component;


/**
 * In deze stap worden de regels uitgevoerd die voor de verwerking van een bericht
 * uitgevoerd moeten worden.
 */
@Component
public class VoorBerichtRegelStap {

    @Inject
    private BijhoudingRegelService bijhoudingRegelService;

    @Inject
    private MeldingFactory meldingFactory;

    /**
     * Voer de stap uit.
     * @param bericht het bijhoudingsbericht
     * @return resultaat
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht) {
        final Set<ResultaatMelding> meldingen = new HashSet<>();

        final List<VoorBerichtRegel> bedrijfsregels = bijhoudingRegelService.getVoorBerichtRegels(bericht.getSoort().getWaarde());

        for (final VoorBerichtRegel regel : bedrijfsregels) {
            final List<BerichtIdentificeerbaar> overtredendeObjecten = regel.voerRegelUit(bericht);
            for (BerichtIdentificeerbaar berichtIdentificeerbaar : overtredendeObjecten) {
                meldingen.add(meldingFactory.maakResultaatMelding(regel.getRegel(), berichtIdentificeerbaar, null));
            }
        }

        return Resultaat.builder().withMeldingen(meldingen).build();
    }

}
