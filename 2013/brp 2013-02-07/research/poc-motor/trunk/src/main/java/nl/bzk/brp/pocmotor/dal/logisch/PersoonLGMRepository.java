/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.logisch;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Object voor het ophalen en wijzigen van {@link Persoon} instanties.
 */
public interface PersoonLGMRepository extends JpaRepository<Persoon, Long> {

    /**
     * Haalt de persoon op op basis van zijn/haar BSN. Merk op dat deze methode fouten op kan leveren als er geen
     * persoon gevonden wordt met het opgegeven BSN, of als er meer personen worden gevonden met het opgegeven BSN (wat
     * theoretisch mogelijk is).
     *
     * @param bsn het BSN van de persoon die opgehaald dient te worden.
     * @return de persoon die geidentificeerd wordt met het opgegeven BSN.
     */
    Persoon findByIdentificatienummersBurgerservicenummer(final Burgerservicenummer bsn);

}
