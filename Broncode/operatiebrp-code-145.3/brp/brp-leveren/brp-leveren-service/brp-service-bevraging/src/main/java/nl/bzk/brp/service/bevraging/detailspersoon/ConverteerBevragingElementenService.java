/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import java.util.Set;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.AttribuutElement;

/**
 * ConverteerBevragingElementenService.
 */
@FunctionalInterface
interface ConverteerBevragingElementenService {
    /**
     * @param bevragingElementen bevragingElementen
     * @param autorisatiebundel autorisatiebundel
     * @return set van geconverteerde elementen
     * @throws ConverteerBevragingVerzoekElementException fout bij converteren naar metaattributen
     */
    Set<AttribuutElement> converteerBevragingElementen(Set<String> bevragingElementen, Autorisatiebundel autorisatiebundel)
            throws ConverteerBevragingVerzoekElementException;
}
