/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.VraagPLWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;

/**
 * Valideert de zoekcriteria op niet toegestane rubrieken.
 */
public class NietToegestaneOpvragenPLRubriekenValidator implements Validator<VraagPLWebserviceVraagBericht> {
    @Override
    public Optional<Antwoord> apply(final VraagPLWebserviceVraagBericht vraag) {
        final List<String> nietToegestaneRubrieken = vraag.getZoekRubrieken().stream().filter(this::nietToegestaan).collect(Collectors.toList());

        return nietToegestaneRubrieken.isEmpty()
                ? Optional.empty()
                : Optional.of(Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_018,
                        nietToegestaneRubrieken.stream().sorted().map(Object::toString).collect(Collectors.joining(", "))));
    }

    private boolean nietToegestaan(String rubriek) {
        return !("010110".equals(rubriek) || "010120".equals(rubriek));
    }
}
