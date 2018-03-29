/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Webservice Antwoord.
 */
public interface WebserviceAntwoord {
    /**
     * Mapt de lijst van persoonslijsten naar een lijst van geconverteerde en gefilterde lijst van Lo3CategorieWaarde.
     * @param rubrieken lijst van gevraagde rubrieken
     * @return een lijst van geconverteerde en gefilterde lijst van Lo3CategorieWaarde
     */
    List<List<Lo3CategorieWaarde>> rubrieken(List<String> rubrieken);
}
