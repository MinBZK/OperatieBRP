/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;

public class NaamGebruikId implements java.io.Serializable {

    private char naamGebruik;
    private char geslacht;

    public Naamgebruik getNaamGebruik() {
        Naamgebruik naamgebruikObject =  Naamgebruik.EIGEN;

        if (String.valueOf(naamGebruik) != null) {
            String naamGebruikString = String.valueOf(naamGebruik).toUpperCase();
            if (naamGebruikString.equals("E")) {
                naamgebruikObject = Naamgebruik.EIGEN;
            } else if (naamGebruikString.equals("P")) {
                naamgebruikObject = Naamgebruik.PARTNER;
            } else if (naamGebruikString.equals("V")) {
                naamgebruikObject = Naamgebruik.EIGEN_PARTNER;
            } else if (naamGebruikString.equals("N")) {
                naamgebruikObject = Naamgebruik.PARTNER_EIGEN;
            } else {
                // moet gevuld worden, vandaar deze else
                naamgebruikObject = Naamgebruik.EIGEN;
            }
        }

        return naamgebruikObject;
    }

    public void setNaamGebruik(final Naamgebruik naamGebruikParam) {
        this.naamGebruik = naamGebruikParam.getCode().charAt(0);
    }

    public Geslachtsaanduiding getGeslacht() {
        Geslachtsaanduiding geslachtsaanduiding = Geslachtsaanduiding.ONBEKEND;

        if (String.valueOf(geslacht).equals("V")) {
            geslachtsaanduiding = Geslachtsaanduiding.VROUW;
        } else if (String.valueOf(geslacht).equals("M")) {
            geslachtsaanduiding = Geslachtsaanduiding.MAN;
        }

        return geslachtsaanduiding;
    }

    public void setGeslacht(final Geslachtsaanduiding geslacht) {
        this.geslacht = geslacht.getCode().charAt(0);
    }

}
