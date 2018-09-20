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
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingGedeblokkeerdeMeldingBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;


/**
 * In deze stap wordt gecontroleerd dat er geen gedeblokkeerde meldingen 'over' zijn. Deze stap moet zo geconfigureerd
 * worden dat hij uitgevoerd wordt na alle stappen waarin een melding aan het resultaat toegevoegd zou kunnen worden en
 * gedeblokkeerd zouden kunnen worden.
 */
@Component
public class GedeblokkeerdeMeldingenOverschotControleStap {

    /**
     * Er mogen geen gedeblokkeerde meldingen overblijven, dwz: alle gedeblokkeerde meldingen die in het bericht
     * zijn meegegeven, moeten ook daadwerkelijk een melding gedeblokkeerd hebben.
     *
     * @param bericht   het bericht
     * @return meldinglijst
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht) {
        final Set<ResultaatMelding> resultaatMeldingen = new HashSet<>();

        final List<String> codesVanOvergeblevenGedeblokkeerdeMeldingen = new ArrayList<>();

        final List<AdministratieveHandelingGedeblokkeerdeMeldingBericht> gedeblokkeerdeMeldingen =
            bericht.getAdministratieveHandeling().getGedeblokkeerdeMeldingen();
        if (gedeblokkeerdeMeldingen != null) {
            for (final AdministratieveHandelingGedeblokkeerdeMeldingBericht melding : gedeblokkeerdeMeldingen) {
                codesVanOvergeblevenGedeblokkeerdeMeldingen.add(
                    melding.getGedeblokkeerdeMelding().getRegel().getWaarde().getCode());
            }
            if (CollectionUtils.isNotEmpty(codesVanOvergeblevenGedeblokkeerdeMeldingen)) {
                // Voeg een (fout)melding toe over 'overgebleven' gedeblokkeerde meldingen.
                resultaatMeldingen.add(ResultaatMelding.builder()
                    .withRegel(Regel.BRBY9904)
                    .withMeldingTekst("Er zijn gedeblokkeerde meldingen opgegeven die niet van toepassing zijn: "
                        + codesVanOvergeblevenGedeblokkeerdeMeldingen + ".")
                    // Gebruik het communicatie id van de eerste 'overschot'
                    // gedeblokkeerde melding in deze foutmelding.
                    .withReferentieID(gedeblokkeerdeMeldingen.get(0).getGedeblokkeerdeMelding().getCommunicatieID())
                    .withAttribuutNaam("regelCode")
                    .build());
                // Maak de lijst van gedeblokkeerde meldingen leeg, ten teken dat we nu alles afgehandeld hebben.
                gedeblokkeerdeMeldingen.clear();
            }
        }

        return Resultaat.builder().withMeldingen(resultaatMeldingen).build();
    }

}
