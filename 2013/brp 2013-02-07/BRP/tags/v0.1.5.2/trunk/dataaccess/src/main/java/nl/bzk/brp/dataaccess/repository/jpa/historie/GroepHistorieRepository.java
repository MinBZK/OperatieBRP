/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.historie;

import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.springframework.stereotype.Repository;

/**
 * Interface die de juiste afhandeling van historie regelt in de C en D Laag voor groepen.
 */
@Repository
public interface GroepHistorieRepository {

    /**
     * Regelt het persisteren van de historie op basis van het aangepaste/aangemaakte persoon object.
     * @param persoon De persoon waarin de groep is aangepast/aangemaakt.
     * @param actie De actie die geleid heeft tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid Datum aanvang geldigheid van het nieuwe/aangepaste A-Laag record.
     * @param datumEindeGeldigheid Datum einde geldigheid van het nieuwe/aangepaste A-Laag record.
     */
    void persisteerHistorie(final PersistentPersoon persoon,
                            final PersistentActie actie,
                            final Integer datumAanvangGeldigheid,
                            final Integer datumEindeGeldigheid);
}
