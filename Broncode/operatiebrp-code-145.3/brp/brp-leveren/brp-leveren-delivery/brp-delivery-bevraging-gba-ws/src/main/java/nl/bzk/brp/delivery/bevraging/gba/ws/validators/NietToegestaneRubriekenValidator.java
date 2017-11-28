/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.WebserviceBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.migratiebrp.conversie.vragen.filter.AdHocZoekenFilter;

/**
 * Valideert de zoekcriteria op niet toegestane rubrieken.
 * @param <T> subtype van WebserviceBericht dat gevalideerd dient te worden
 */
public class NietToegestaneRubriekenValidator<T extends WebserviceBericht> implements Validator<T> {
    @Override
    public Optional<Antwoord> apply(final WebserviceBericht vraag) {
        AdHocZoekenFilter filter = new AdHocZoekenFilter();
        Collection<Integer> nietToegestaneRubrieken = filter.nietToegestaneRubrieken(vraag.getZoekCriteria());
        return nietToegestaneRubrieken.isEmpty()
                ? Optional.empty()
                : Optional.of(Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_018,
                        nietToegestaneRubrieken.stream().sorted().map(Object::toString).collect(Collectors.joining(", "))));
    }
}
