/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;

/**
 * Interface die de juiste afhandeling van historie regelt in de C en D Laag voor groepen.
 * @param <T> A-Laag object waarvoor de historie wordt geregeld. (Het object type)
 */
public interface GroepHistorieRepository<T extends AbstractDynamischObjectType> {

    /**
     * Regelt het persisteren van de historie op basis van het aangepaste/aangemaakte persoon object.
     * @param objectType Object type uit de A-Laag
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid Datum aanvang geldigheid van het nieuwe/aangepaste A-Laag record.
     * @param datumEindeGeldigheid Datum einde geldigheid van het nieuwe/aangepaste A-Laag record.
     */
    void persisteerHistorie(final T objectType,
                            final ActieModel actie,
                            final Datum datumAanvangGeldigheid,
                            final Datum datumEindeGeldigheid);
}
