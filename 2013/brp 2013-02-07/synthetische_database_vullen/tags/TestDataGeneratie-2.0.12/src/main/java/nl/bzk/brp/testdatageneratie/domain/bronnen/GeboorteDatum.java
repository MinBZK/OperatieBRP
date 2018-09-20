/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

public class GeboorteDatum extends Bron {

    private int datum;

    public int getDatum() {
        return datum;
    }

    public void setDatum(final int datum) {
        this.datum = datum;
    }

}
