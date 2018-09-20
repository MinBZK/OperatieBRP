/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.cache;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;

/**
 * PartijCache. Cache voor partij tabel.
 */
public interface PartijCache {

    /**
     *
     */
    void herlaad();

    /**
     * Herlaad via jmx. Managed operation.
     */
    void herlaadViaJmx();

    /**
     * Geeft de partij met code.
     *
     * @param code de partij code
     * @return de partij
     */
    Partij geefPartij(int code);
}
