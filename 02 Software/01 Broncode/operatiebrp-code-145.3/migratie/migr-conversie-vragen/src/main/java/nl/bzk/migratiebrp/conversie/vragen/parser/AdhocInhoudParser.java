/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.vragen.parser;

import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.vragen.filter.PuntAdresFilter;

/**
 * Inhoud parser voor ad hoc verzoeken.
 */
public interface AdhocInhoudParser {
    /**
     * Parse de identificerende gegevens naar een lijst van Lo3CategorieWaarde en vervang eventueel een punt adres.
     * @param identificerendeGegevensAlsLo3BerichtInhoud identificerende gegevens
     * @return lijst van Lo3CategorieWaarde
     */
    static List<Lo3CategorieWaarde> parse(final String identificerendeGegevensAlsLo3BerichtInhoud) {
        try {
            return Lo3Inhoud.parseInhoud(identificerendeGegevensAlsLo3BerichtInhoud)
                    .stream()
                    .map(PuntAdresFilter::replaceInCategorieWaarde)
                    .collect(Collectors.toList());
        } catch (final BerichtSyntaxException ex) {
            // Het bericht is in het ISC al geparsed, dus de syntax parsen zou geen exception moeten opleveren.
            throw new IllegalArgumentException(ex);
        }
    }
}
