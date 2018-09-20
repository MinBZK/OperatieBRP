/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import javax.validation.constraints.NotNull;

import nl.bzk.brp.model.gedeeld.SoortIndicatie;


/**
 * Indicaties bij een persoon.
 */
public class PersoonIndicatie {

    private Persoon        persoon;

    @NotNull
    private SoortIndicatie soort;

    private Boolean        waarde;

    public Persoon getPersoon() {
        return this.persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public SoortIndicatie getSoort() {
        return this.soort;
    }

    public void setSoort(final SoortIndicatie soort) {
        this.soort = soort;
    }

    public Boolean getWaarde() {
        return this.waarde;
    }

    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }
}
