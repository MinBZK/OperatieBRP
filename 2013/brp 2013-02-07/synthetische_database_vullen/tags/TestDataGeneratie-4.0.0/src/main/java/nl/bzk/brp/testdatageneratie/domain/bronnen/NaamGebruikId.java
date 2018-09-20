/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.domain.bronnen;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.WijzeGebruikGeslachtsnaam;

public class NaamGebruikId implements java.io.Serializable {

    private char naamGebruik;
    private char geslacht;

    public WijzeGebruikGeslachtsnaam getNaamGebruik() {
        WijzeGebruikGeslachtsnaam wijzeGebruikGeslachtsnaam =  WijzeGebruikGeslachtsnaam.EIGEN;

        if (String.valueOf(naamGebruik) != null) {
            String naamGebruikString = String.valueOf(naamGebruik).toUpperCase();
            if (naamGebruikString.equals("E")) {
                wijzeGebruikGeslachtsnaam = WijzeGebruikGeslachtsnaam.EIGEN;
            } else if (naamGebruikString.equals("P")) {
                wijzeGebruikGeslachtsnaam = WijzeGebruikGeslachtsnaam.PARTNER;
            } else if (naamGebruikString.equals("V")) {
                wijzeGebruikGeslachtsnaam = WijzeGebruikGeslachtsnaam.EIGEN_PARTNER;
            } else if (naamGebruikString.equals("N")) {
                wijzeGebruikGeslachtsnaam = WijzeGebruikGeslachtsnaam.PARTNER_EIGEN;
            } else {
                // moet gevuld worden, vandaar deze else
                wijzeGebruikGeslachtsnaam = WijzeGebruikGeslachtsnaam.EIGEN;
            }
        }

        return wijzeGebruikGeslachtsnaam;
    }

    public void setNaamGebruik(final WijzeGebruikGeslachtsnaam naamGebruik) {
        this.naamGebruik = naamGebruik.getCode().charAt(0);
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
