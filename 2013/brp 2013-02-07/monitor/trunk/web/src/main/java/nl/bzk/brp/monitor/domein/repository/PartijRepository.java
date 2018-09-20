/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.domein.repository;

import java.util.List;

import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.kern.persistent.PersistentPartij;
import nl.bzk.brp.monitor.domein.BerichtenPerPartij;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersistentPartij} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface PartijRepository extends JpaRepository<PersistentPartij, Integer> {

    /**
     * Selecteer de telling van berichten voor elke partij en de tijd van het laatste bericht.
     *
     * @return Lijst met BerichtenPerPartij.
     */
    @Query("select new nl.bzk.brp.monitor.domein.BerichtenPerPartij(p.id, p.naam, count(ber) as aantal, max(ber.datumTijdOntvangst) as tijd) from PersistentPartij p left join p.doelbindingen db left join db.abonnementen ab left join ab.leveringen lev left join lev.gebaseerdOp ber group by p.id")
    List<BerichtenPerPartij> zoekAantalBerichtenPerPartij();

    /**
     * Haalt lijst van berichten op voor de opgegeven partij.
     *
     * @param pageable op te geven hoeveel resultaten er teruggegeven moet worden
     * @param id partij id
     * @return een Lijst met een Map waarin de partij is opgeslagen onder de sleutel "partij" en het bericht onder de
     *         sleutel "bericht"
     */
    @Query("select ber from PersistentPartij p left join p.doelbindingen db left join db.abonnementen ab left join ab.leveringen lev left join lev.gebaseerdOp ber where p.id = ?1 order by ber.datumTijdOntvangst desc")
    List<Bericht> haalOpBerichtenVoorPartij(Pageable pageable, int id);

    /**
     * Telt het totaal aantal inkomende berichten van de betreffende partij.
     *
     * @param id partij id
     * @return aantal berichten
     */
    @Query("select count(ber) from PersistentPartij p left join p.doelbindingen db left join db.abonnementen ab left join ab.leveringen lev left join lev.gebaseerdOp ber where p.id = ?1")
    Long haalOpAantalBerichtenVoorPartij(int id);

}
