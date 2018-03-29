/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis;

import java.util.List;
import java.util.Map;

/**
 * Service voor het muteren van persoonBlobs.
 */
public interface BlobMutatieService {

    /**
     * Zet 1 of meerdere afnemerindicaties op een persoon.
     * @param params waardentabel
     */
    void zetAfnemerindicatiesOpPersoon(final List<Map<String, String>> params);

    /**
     * Verwijder alle afnemerindicaties van een persoon.
     * @param params waardentabel
     */
    void verwijderAlleAfnemerindicatiesVanPersoon(final List<Map<String, String>> params);

    /**
     * Wijzigt tijdstip laatste wijziging GBA systematiek v/e persoon (groep Afgeleid Administratief)
     * @param params waardentabel
     */
    void wijzigTijdstipLaatsteWijzigingGBASystematiekVanPersoon(final List<Map<String, String>> params);

    /**
     * Pas een attribuut in een blob aan met een nieuwe waarde.
     * @param bsn de bsn van de persoon
     * @param attribuut het attribuut van de persoon dat moet worden aangepast
     * @param waarde de nieuwe waarde
     */
    void pasBlobVoorPersoonEnAttribuutAanMetWaarde(String bsn, String attribuut, String waarde);
}
