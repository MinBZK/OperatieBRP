/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import com.google.common.base.Optional;

/**
 * De PersoonLevering service. Verantwoordelijk voor het leveren van personen.
 */
public interface PersoonLeveringService {

    /**
     * Lever een persoon op basis van de persoon id.
     * @param id de ID van een persoon
     * @return optional met een waarde die het resultaat aangeeft, of
     * {@link com.google.common.base.Optional#absent()} als het is fout gegaan
     */
    Optional<Boolean> leverPersoon(Integer id);

    /**
     * Lever een persoon op basis van de {@link byte[]} representatie
     * @param data de data van een persoon
     * @return optional met een waarde die het resultaat aangeeft, of
     * {@link com.google.common.base.Optional#absent()} als het is fout gegaan
     */
    Optional<Boolean> leverPersoon(byte[] data);
}
