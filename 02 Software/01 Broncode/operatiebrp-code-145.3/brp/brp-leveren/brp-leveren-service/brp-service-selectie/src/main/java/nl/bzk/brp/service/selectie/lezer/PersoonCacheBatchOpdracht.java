/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

/**
 * PersoonCacheBatchOpdracht.
 */
final class PersoonCacheBatchOpdracht {

    private boolean stop;
    private long minIdPersoonCache;
    private long maxIdPersoonCache;

    /**
     * Gets min id persoon cache.
     * @return the min id persoon cache
     */
    public long getMinIdPersoonCache() {
        return minIdPersoonCache;
    }

    /**
     * Sets min id persoon cache.
     * @param minIdPersoonCache the min id persoon cache
     */
    public void setMinIdPersoonCache(long minIdPersoonCache) {
        this.minIdPersoonCache = minIdPersoonCache;
    }

    /**
     * Gets max id persoon cache.
     * @return the max id persoon cache
     */
    public long getMaxIdPersoonCache() {
        return maxIdPersoonCache;
    }

    /**
     * Sets max id persoon cache.
     * @param maxIdPersoonCache the max id persoon cache
     */
    public void setMaxIdPersoonCache(long maxIdPersoonCache) {
        this.maxIdPersoonCache = maxIdPersoonCache;
    }

    /**
     * Is stop boolean.
     * @return the boolean
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * Sets stop.
     * @param stop the stop
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
