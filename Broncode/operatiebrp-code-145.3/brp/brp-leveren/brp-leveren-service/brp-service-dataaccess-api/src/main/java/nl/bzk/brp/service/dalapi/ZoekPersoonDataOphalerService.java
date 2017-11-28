/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import java.util.Set;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;

/**
 * ZoekPersoonDataOphalerService.
 */
public interface ZoekPersoonDataOphalerService {

    /**
     * Zoekt in actueel.
     * @param zoekCriteria zoekCriteria
     * @param maxResults maxResults
     * @return gevonde personen
     * @throws QueryNietUitgevoerdException query fout
     */
    List<Long> zoekPersonenActueel(Set<ZoekCriterium> zoekCriteria, int maxResults) throws QueryNietUitgevoerdException;


    /**
     * Zoekt historisch.
     * @param zoekCriteria zoekCriteria
     * @param materieelPeilmoment materieelPeilmoment
     * @param peilperiode peilperiode
     * @param maxResults maxResults
     * @return gevonde personen
     * @throws QueryNietUitgevoerdException query fout
     */
    List<Long> zoekPersonenHistorisch(Set<ZoekCriterium> zoekCriteria, Integer materieelPeilmoment, final boolean peilperiode, int maxResults)
            throws QueryNietUitgevoerdException;
}
