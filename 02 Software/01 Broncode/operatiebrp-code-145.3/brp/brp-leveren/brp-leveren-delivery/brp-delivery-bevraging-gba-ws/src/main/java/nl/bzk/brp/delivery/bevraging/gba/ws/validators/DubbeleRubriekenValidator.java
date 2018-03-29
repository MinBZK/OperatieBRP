/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Valideert de rubrieken op dubbele waarden.
 */
public interface DubbeleRubriekenValidator {
    /**
     * Bepaalt dubbele rubrieken.
     * @param rubrieken lijst van rubrieken
     * @return lijst van rubrieken die dubbel voorkomen
     */
    static Set<Integer> bepaalDubbele(final List<String> rubrieken) {
        return rubrieken.stream()
                .filter(waarde -> Collections.frequency(rubrieken, waarde) > 1)
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
    }
}
