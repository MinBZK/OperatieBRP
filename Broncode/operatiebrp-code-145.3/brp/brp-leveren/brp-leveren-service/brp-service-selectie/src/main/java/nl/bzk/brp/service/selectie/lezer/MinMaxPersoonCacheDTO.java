/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer;

/**
 * MinMaxPersoonCacheDTO.
 */
public final class MinMaxPersoonCacheDTO {

    private long minId;
    private long maxId;

    /**
     * Gets min id.
     * @return the min id
     */
    public long getMinId() {
        return minId;
    }

    /**
     * Sets min id.
     * @param minId the min id
     */
    public void setMinId(long minId) {
        this.minId = minId;
    }

    /**
     * Gets max id.
     * @return the max id
     */
    public long getMaxId() {
        return maxId;
    }

    /**
     * Sets max id.
     * @param maxId the max id
     */
    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }
}
