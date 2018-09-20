/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;

/**
 * Soort LO3 levering bericht.
 */
public enum SoortBericht {

    /** Ag01: Volledigbericht na handmatig plaatsen afnemersindicatie. */
    AG01("Ag01", SoortSynchronisatie.VOLLEDIGBERICHT),
    /** Ag11: Volledigbericht na automatisch plaatsen afnemersindicatie. */
    AG11("Ag11", SoortSynchronisatie.VOLLEDIGBERICHT),
    /** Ag21: Volledigbericht voor conditioneel leveren (attendering). */
    AG21("Ag21", SoortSynchronisatie.VOLLEDIGBERICHT),
    /** Ag31: Foutherstelbericht/Fallback voor mutatielevering. */
    AG31("Ag31", SoortSynchronisatie.VOLLEDIGBERICHT),
    /** Gv01: Mutatiebericht. */
    GV01("Gv01", SoortSynchronisatie.MUTATIEBERICHT),
    /** Gv02: Mutatiebericht bij infrastructurele wijziging. */
    GV02("Gv02", SoortSynchronisatie.MUTATIEBERICHT),
    /** Ng01: Signaal afvoeren PL. */
    NG01("Ng01", SoortSynchronisatie.MUTATIEBERICHT),
    /** Wa11: A-nummer wijziging. */
    WA11("Wa11", SoortSynchronisatie.MUTATIEBERICHT);

    private final String soortBericht;
    private final SoortSynchronisatie soortSynchronisatie;

    /**
     * Constructor.
     *
     * @param soortBericht soort bericht
     * @param soortSynchronisatie soort synchronisatie
     */
    private SoortBericht(final String soortBericht, final SoortSynchronisatie soortSynchronisatie) {
        this.soortBericht = soortBericht;
        this.soortSynchronisatie = soortSynchronisatie;
    }

    /**
     * Geef soort bericht.
     *
     * @return soort bericht
     */
    public String getSoortBericht() {
        return soortBericht;
    }

    /**
     * Geef soort synchronisatie.
     *
     * @return soort synchronisatie
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

}
