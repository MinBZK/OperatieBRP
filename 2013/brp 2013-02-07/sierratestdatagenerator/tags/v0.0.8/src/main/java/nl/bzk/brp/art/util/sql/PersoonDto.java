/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.art.util.sql;
/**
 * .
 *
 */
public class PersoonDto {
    private final Integer id;
    private final Integer bsn;
    private final Long anr;

    /**
     * .
     * @param id .
     * @param bsn .
     * @param anr .
     */
    public PersoonDto(final Integer id, final Object bsn, final Object anr) {
        this.id = id;
        this.bsn = (Integer) bsn;
        this.anr = (Long) anr;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBsn() {
        return bsn;
    }


    public Long getAnr() {
        return anr;
    }

}
