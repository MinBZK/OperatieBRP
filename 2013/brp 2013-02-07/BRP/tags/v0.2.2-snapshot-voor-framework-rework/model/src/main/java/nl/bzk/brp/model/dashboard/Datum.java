/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

/**
 * Datum die nullen mag bevatten.
 */
public class Datum {

    private int decimalen;

    public Datum() {
    }

    public Datum(final int decimalen) {
        this.decimalen = decimalen;
    }

    public int getDecimalen() {
        return decimalen;
    }

    public void setDecimalen(final int decimalen) {
        this.decimalen = decimalen;
    }

}
