/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.administratievehandeling.integratie;

import nl.bzk.brp.business.stappen.AbstractStappenResultaat;



public class TestResultaat extends AbstractStappenResultaat {

    private Boolean foutief = false;

    private Boolean succesvol = false;

    private Boolean bevatStoppendeFouten = false;

    public void setFoutief(final Boolean foutief) {
        this.foutief = foutief;
    }

    public void setSuccesvol(final Boolean succesvol) {
        this.succesvol = succesvol;
    }

    public void setBevatStoppendeFouten(final Boolean bevatStoppendeFouten) {
        this.bevatStoppendeFouten = bevatStoppendeFouten;
    }

    @Override
    public boolean isFoutief() {
        return foutief;
    }

    @Override
    public boolean isSuccesvol() {
        return succesvol;
    }

    @Override
    public boolean bevatStoppendeFouten() {
        return bevatStoppendeFouten;
    }

}
