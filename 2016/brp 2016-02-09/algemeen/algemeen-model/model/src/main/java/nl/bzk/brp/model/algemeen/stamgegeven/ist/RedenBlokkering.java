/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ist;

/**
 * De mogelijke redenen om een persoonverwerking te blokkeren.
 */
public enum RedenBlokkering {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy"),
    /**
     * Verhuizend van LO3 naar BRP.
     */
    VERHUIZEND_VAN_L_O3_NAAR_B_R_P("Verhuizend van LO3 naar BRP"),
    /**
     * Verhuizend van BRP naar LO3 GBA.
     */
    VERHUIZEND_VAN_B_R_P_NAAR_L_O3_G_B_A("Verhuizend van BRP naar LO3 GBA"),
    /**
     * Verhuizend van BRP naar LO3 RNI.
     */
    VERHUIZEND_VAN_B_R_P_NAAR_L_O3_R_N_I("Verhuizend van BRP naar LO3 RNI");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor RedenBlokkering
     */
    private RedenBlokkering(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Reden blokkering.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

}
