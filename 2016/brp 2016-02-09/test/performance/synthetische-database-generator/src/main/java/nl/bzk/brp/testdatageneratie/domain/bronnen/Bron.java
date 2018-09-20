/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import java.io.Serializable;


public class Bron implements Serializable {

    private int aantal;
    private int van;
    private int totEnMet;

    public int getAantal() {
        return this.aantal;
    }

    public void setAantal(final int aantal) {
        this.aantal = aantal;
    }

    public int getVan() {
        return this.van;
    }

    public void setVan(final int van) {
        this.van = van;
    }

    public int getTotEnMet() {
        return this.totEnMet;
    }

    public void setTotEnMet(final int totEnMet) {
        this.totEnMet = totEnMet;
    }

}
