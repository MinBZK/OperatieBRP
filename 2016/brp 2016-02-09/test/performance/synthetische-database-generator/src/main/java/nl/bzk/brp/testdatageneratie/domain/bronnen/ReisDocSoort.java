/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.testdatageneratie.domain.kern.Srtnlreisdoc;

public class ReisDocSoort extends Bron {

    private String soort;

    public String getSoort() {
        return soort;
    }

    public void setSoort(final String soort) {
        this.soort = soort;
    }

    public Srtnlreisdoc getSrtnlreisdoc() {
        if ("..".equals(soort)) {
            return Srtnlreisdoc.DD;
        } else {
            return Srtnlreisdoc.valueOf(soort);
        }
    }

}
