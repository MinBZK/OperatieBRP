/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Het antwoord bericht voor registratie naam geslacht.
 */
public final class RegistreerNaamGeslachtAntwoordBericht extends BijhoudingAntwoordBericht {

    /**
     * Standaard constructor (die direct de soort van het bericht initialiseert/zet).
     */
    public RegistreerNaamGeslachtAntwoordBericht() {
        super(SoortBericht.BHG_NMG_REGISTREER_NAAM_GESLACHT_R);
    }

    /**
     * Functie t.b.v. Jibx.
     *
     * @return true of false.
     */
    public boolean isWijzigingGeslachtsnaam() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_GESLACHTSNAAM);
    }

    /**
     * Functie t.b.v. Jibx.
     *
     * @return true of false.
     */
    public boolean isWijzigingGeslachtsaanduiding() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_GESLACHTSAANDUIDING);
    }

    /**
     * Functie t.b.v. Jibx.
     *
     * @return true of false.
     */
    public boolean isWijzigingVoornaam() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_VOORNAAM);
    }

    /**
     * Functie t.b.v. Jibx.
     *
     * @return true of false.
     */
    public boolean isWijzigingNaamgebruik() {
        return isAdministratieveHandelingVanType(SoortAdministratieveHandeling.WIJZIGING_NAAMGEBRUIK);
    }
}
