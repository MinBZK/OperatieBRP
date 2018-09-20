/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import java.util.List;

import nl.bzk.brp.domein.ber.Bericht;


/**
 * Repository voor de {@link Bericht} class.
 */
public interface BerichtRepository {

    /**
     * Zoek alle berichten.
     *
     * @return alle berichten.
     */
    List<Bericht> findAll();

    /**
     * Bewaar het meegegeven bericht.
     *
     * @param bericht Het bericht dat bewaard wordt.
     */
    void save(Bericht bericht);

    /**
     * Zoek het bericht met de meegegeven identifier.
     *
     * @param id De identifier van het gezochte bericht.
     * @return Het bericht met de meegegeven identifier.
     */
    Bericht findOne(Long id);
}
