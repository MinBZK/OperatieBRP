/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.testdatageneratie.domain.kern.Adellijketitel;

public class TitelPredikaat extends Bron {

    private String titel;

    public String getTitel() {
        return this.titel;
    }

    public void setTitel(final String titel) {
        this.titel = titel;
    }

    public Adellijketitel getAdellijketitel() {
        switch (titel.charAt(0)) {
            case 'B': return Adellijketitel.B;
            case 'G': return Adellijketitel.G;
            case 'M': return Adellijketitel.M;
            case 'P': return Adellijketitel.P;
            case 'R': return Adellijketitel.R;
            //jonkheer/vrouw
            default: return null;
        }
    }

}
