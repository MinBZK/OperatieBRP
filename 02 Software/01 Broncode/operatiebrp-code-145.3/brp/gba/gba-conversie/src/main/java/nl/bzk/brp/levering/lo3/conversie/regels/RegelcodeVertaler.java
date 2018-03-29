/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.regels;

import java.util.List;
import java.util.Optional;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Vertaalt BRP regelcodes naar Lo3 foutcodes.
 * @param <T> type van de het resultaat
 */
public interface RegelcodeVertaler<T> {

    /**
     * Bepaal de foutcode uit een lijst van meldingen.
     * @param meldingen lijst van meldingen
     * @return geeft een foutcode character terug
     */
    Optional<T> bepaalFoutcode(List<Melding> meldingen);
}
