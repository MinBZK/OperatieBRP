/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorieEntiteit;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.springframework.stereotype.Repository;

/**
 * Interface die de juiste afhandeling van historie regelt in de C en D Laag voor objecttypen.
 * @param <T> C/D-Laag object waarvoor de historie wordt geregeld.
 * @param <Y> A laag object waarvoor de historie wordt geregeld.
 */
@Repository
public interface ObjectTypeHistorieRepository<T extends AbstractMaterieleEnFormeleHistorieEntiteit, Y> {

    /**
     * Regelt het persisteren van de historie op basis van het aangepaste/aangemaakte A-Laag object.
     * @param aLaagObject Het aangepaste of aangemaakte A laag object.
     * @param persoon De persoon waar alles om draait.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid Datum aanvang geldigheid van het nieuwe/aangepaste A-Laag record.
     * @param datumEindeGeldigheid Datum einde geldigheid van het nieuwe/aangepaste A-Laag record.
     */
    void persisteerHistorie(final Y aLaagObject,
                            final PersistentPersoon persoon,
                            final PersistentActie actie,
                            final Integer datumAanvangGeldigheid,
                            final Integer datumEindeGeldigheid);
}
