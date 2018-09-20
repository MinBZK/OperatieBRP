/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Bericht.TYPE;

/**
 * In het verleden kon je alleen maar aangeven hoeling een bericht duurt. dit is eigenlijk ook incl.de overhead.
 * En het gaat ook fout als we 'fout berichten terug komen.
 * Om tot een beter statistieken te komen, is deze 'Long' als duur nu uitgebreid met andere waarden.
 *
 */
public class DuurDto {
    private final long duurQuery;
    private Bericht.TYPE type;
    private Bericht.BERICHT bericht;
    private final Bericht.STATUS status;


    /**
     * Instantieert een nieuwe Duur dto.
     *
     * @param duurQuery duur query
     * @param type type
     * @param bericht bericht
     * @param status status
     */
    public DuurDto(final long duurQuery, final TYPE type, final BERICHT bericht, final Bericht.STATUS status) {
        this.duurQuery = duurQuery;
        this.type = type;
        this.bericht = bericht;
        this.status = status;
    }

    public long getDuurQuery() {
        return duurQuery;
    }

    public Bericht.TYPE getType() {
        return type;
    }

    public Bericht.BERICHT getBericht() {
        return bericht;
    }

    public Bericht.STATUS getStatus() {
        return status;
    }

    public void setType(final Bericht.TYPE type) {
        this.type = type;
    }

    public void setBericht(final Bericht.BERICHT bericht) {
        this.bericht = bericht;
    }
}
