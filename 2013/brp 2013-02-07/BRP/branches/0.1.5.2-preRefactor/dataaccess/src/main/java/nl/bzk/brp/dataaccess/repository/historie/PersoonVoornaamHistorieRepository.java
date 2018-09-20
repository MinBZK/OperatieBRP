/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.historie;

import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonVoornaam;


/**
 * Repository om de historie op te slaan van PersoonVoornaam.
 *
 */
public interface PersoonVoornaamHistorieRepository {

    /**
     * Slaat de historie gegevens op voor PersoonVoornaam. Deze methode bevat de nodige logica om de C-laag en de D-laag
     * te bewerken.
     *
     * @param voornaam Persoon voornaam van de A-laag
     * @param actie De actie die leidt tot de aanpassingen in de C/D-Laag.
     * @param datumAanvangGeldigheid datum van aanvang geldigheid
     * @param datumEindeGeldigheid datum van einde geldigheid
     */
    void opslaanHistorie(final PersistentPersoonVoornaam voornaam, final PersistentActie actie,
                         final Integer datumAanvangGeldigheid, final Integer datumEindeGeldigheid);
}
