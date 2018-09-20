/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.domein.repository;

import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.ber.persistent.PersistentBericht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link Bericht} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface BerichtRepository extends JpaRepository<PersistentBericht, Long> {

    /**
     * Haalt de uitgaande bericht op die hoort bij een bepaalde ingaande bericht.
     *
     * @param id id van de ingaande bericht.
     * @return Bericht
     */
    @Query("select ber from PersistentBericht ber where ber.antwoordOp.id = ?1")
    Bericht zoekUitgaandBericht(Long id);
}
