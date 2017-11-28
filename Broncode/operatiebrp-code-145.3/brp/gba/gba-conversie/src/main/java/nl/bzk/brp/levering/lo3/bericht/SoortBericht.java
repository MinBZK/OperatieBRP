/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;

/**
 * Soort LO3 levering bericht.
 */
public enum SoortBericht {

    /**
     * Ag01: Volledigbericht na handmatig plaatsen afnemersindicatie.
     */
    AG01("Ag01", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Ag11: Volledigbericht na automatisch plaatsen afnemersindicatie.
     */
    AG11("Ag11", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Ag21: Volledigbericht voor conditioneel leveren (attendering).
     */
    AG21("Ag21", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Ag31: Foutherstelbericht/Fallback voor mutatielevering.
     */
    AG31("Ag31", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Gv01: Mutatiebericht.
     */
    GV01("Gv01", SoortSynchronisatie.MUTATIE_BERICHT),

    /**
     * Gv02: Mutatiebericht bij infrastructurele wijziging.
     */
    GV02("Gv02", SoortSynchronisatie.MUTATIE_BERICHT),

    /**
     * Ha01: Ad hoc persoonsantwoord.
     */
    HA01("Ha01", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Ng01: Signaal afvoeren PL.
     */
    NG01("Ng01", SoortSynchronisatie.MUTATIE_BERICHT),

    /**
     * Sf01: Selectie PL foutbericht.
     */
    SF01("Sf01", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Sv01: Selectie PL.
     */
    SV01("Sv01", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Sv11: Selectie geen PL.
     */
    SV11("Sv11", SoortSynchronisatie.VOLLEDIG_BERICHT),

    /**
     * Wa11: A-nummer wijziging.
     */
    WA11("Wa11", SoortSynchronisatie.MUTATIE_BERICHT),

    /**
     * Xa01: Ad hoc adresantwoord
     */
    XA01("Xa01", SoortSynchronisatie.VOLLEDIG_BERICHT);

    private final String berichtNummer;
    private final SoortSynchronisatie soortSynchronisatie;

    /**
     * Constructor.
     * @param soortBericht soort bericht
     * @param soortSynchronisatie soort synchronisatie
     */
    SoortBericht(final String soortBericht, final SoortSynchronisatie soortSynchronisatie) {
        this.berichtNummer = soortBericht;
        this.soortSynchronisatie = soortSynchronisatie;
    }

    /**
     * Geef soort bericht.
     * @return soort bericht
     */
    public String getBerichtNummer() {
        return berichtNummer;
    }

    /**
     * Geef soort synchronisatie.
     * @return soort synchronisatie
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

}
