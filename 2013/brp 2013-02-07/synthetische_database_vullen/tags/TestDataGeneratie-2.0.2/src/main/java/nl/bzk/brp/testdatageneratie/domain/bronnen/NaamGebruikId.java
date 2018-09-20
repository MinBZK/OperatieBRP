/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.testdatageneratie.domain.kern.Geslachtsaand;
import nl.bzk.brp.testdatageneratie.domain.kern.Wijzegebruikgeslnaam;

public class NaamGebruikId implements java.io.Serializable {

    private char naamGebruik;
    private char geslacht;

    public Wijzegebruikgeslnaam getNaamGebruik() {
        try {
            return Wijzegebruikgeslnaam.valueOf(String.valueOf(naamGebruik));
        }
        catch (RuntimeException e) {
            return null;
        }
    }

    public void setNaamGebruik(final Wijzegebruikgeslnaam naamGebruik) {
        this.naamGebruik = naamGebruik.name().charAt(0);
    }

    public Geslachtsaand getGeslacht() {
        return Geslachtsaand.valueOf(String.valueOf(geslacht));
    }

    public void setGeslacht(final Geslachtsaand geslacht) {
        this.geslacht = geslacht.getCode().charAt(0);
    }

}
