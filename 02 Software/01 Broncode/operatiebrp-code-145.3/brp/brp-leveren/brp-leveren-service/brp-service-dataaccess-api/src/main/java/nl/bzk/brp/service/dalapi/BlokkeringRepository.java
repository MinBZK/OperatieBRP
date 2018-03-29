/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;

/**
 * DAL tbv Blokkeringen op master
 */
public interface BlokkeringRepository {

    /**
     * Geeft een lijst {@link Blokkering} objecten
     * @return een de lijst met alle blokkeringen
     */
    List<Blokkering> getAlleBlokkeringen();

    /**
     * Zoek voor het berteffende a-nummer de bijbehorende blokkering
     * @param aNummer het a-nummer waarop wordt gezocht
     * @return de blokkering voor het betreffende a-nummer of null indien er geen blokkering is
     */
    Blokkering getBlokkeringOpANummer(String aNummer);

}
