/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

/**
 * CacheEntry.
 */
public final class CacheEntry {

    private String cacheNaam;
    private Object data;

    /**
     * @param cacheNaam cacheNaam
     * @param data data
     */
    public CacheEntry(final String cacheNaam, final Object data) {
        this.cacheNaam = cacheNaam;
        this.data = data;
    }

    public String getCacheNaam() {
        return cacheNaam;
    }

    public Object getData() {
        return data;
    }
}
