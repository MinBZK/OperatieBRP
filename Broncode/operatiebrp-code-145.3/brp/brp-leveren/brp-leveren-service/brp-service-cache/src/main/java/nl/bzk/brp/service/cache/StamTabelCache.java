/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import nl.bzk.brp.domain.algemeen.StamtabelGegevens;

/**
 * StamTabelCache. Cache voor stam tabel.
 */
public interface StamTabelCache {

    /**
     * geef stamgegevens.
     * @param tabelNaam tabelNaam
     * @return lijst met synchroniseerbare stamgegevens
     */
    StamtabelGegevens geefSynchronisatieStamgegevensUitRepository(String tabelNaam);

    /**
     * Herlaad de stamtabellen.
     * @return de cache entry
     */
    CacheEntry herlaad();
}
