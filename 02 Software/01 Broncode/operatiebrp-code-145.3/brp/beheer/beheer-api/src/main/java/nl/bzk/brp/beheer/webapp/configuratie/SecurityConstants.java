/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie;

/**
 * De rollen gebruikt door de applicatie.
 */
public final class SecurityConstants {

    /**
     * ROL: Beheer van functionele applicatie gegevens.
     */
    public static final String ROL_GEGEVENS_BEHEER          = "BRP-GB";
    /**
     * ROL: Beheer van functionele applicatie gegevens.
     */
    public static final String ROL_SUPER_GEGEVENS_BEHEER    = "BRP-SGB";
    /**
     * ROL: Beheer van stamtabellen.
     */
    public static final String ROL_BEHEERDER_STAMTABELLEN   = "BRP-STMTAB";
    /**
     * ROL: Beheer van inhoudelijke gegevens (personen).
     */
    public static final String ROL_FUNCTIONEEL_BEHEER       = "BRP-FB";
    /**
     * ROL: Super beheer van inhoudelijke gegevens (personen).
     */
    public static final String ROL_SUPER_FUNCTIONEEL_BEHEER = "BRP-SFB";
    /**
     * ROL: Auditor rol.
     */
    public static final String ROL_AUDITOR                  = "BRP-AUDIT";
    /**
     * ROL: Technisch beheer.
     */
    public static final String ROL_TECHNISCH_BEHEER         = "BRP-TB";

    private SecurityConstants() {
        // Niet instantieerbaar
    }
}
