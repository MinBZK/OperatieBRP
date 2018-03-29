/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

/**
 * Constanten voor communicatie tijdens plaatsen/verwijderen afnemersindicaties.
 */
public final class AfnemersIndicatieJbpmConstants {

    /**
     * Verwerken afnemersindicatie jbpm key voor persoonId.
     */
    public static final String PERSOON_BSN = "persoonBsn";

    /**
     * Af01 foutreden jbpm key.
     */
    public static final String AF0X_FOUTREDEN_KEY = "af0xFoutreden";

    /**
     * Af01 gemeente jbpm key.
     */
    public static final String AF0X_GEMEENTE_KEY = "af0xGemeente";

    /**
     * Af01 anummer jbpm key.
     */
    public static final String AF0X_ANUMMER_KEY = "af0xANummer";

    /**
     * Af01 foutreden B.
     */
    public static final String AF0X_FOUTREDEN_B = "B";

    /**
     * Af01 foutreden G.
     */
    public static final String AF0X_FOUTREDEN_G = "G";

    /**
     * Af01 foutreden I.
     */
    public static final String AF0X_FOUTREDEN_I = "I";

    /**
     * Af01 foutreden V.
     */
    public static final String AF0X_FOUTREDEN_V = "V";

    /**
     * Af01 foutreden U.
     */
    public static final String AF0X_FOUTREDEN_U = "U";

    /**
     * Af01 foutreden X.
     */
    public static final String AF0X_FOUTREDEN_X = "X";

    /**
     * Private constructor.
     */
    private AfnemersIndicatieJbpmConstants() {
        // Private constructor.
    }

}
