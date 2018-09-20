/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.historie;

import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Interface die de juiste afhandeling van historie regelt in de C en D Laag voor groepen.
 *
 * @param <T> A-Laag object waarvoor de historie wordt geregeld. (Het object type)
 */
public interface GroepFormeleHistorieRepository<T extends AbstractDynamischObjectType> {

    /**
     * Regelt het persisteren van de historie op basis van het aangepaste/aangemaakte persoon object.
     *
     * @param objectType Object type uit de A-Laag
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     */
    void persisteerHistorie(final T objectType,
        final ActieModel actie);
}
