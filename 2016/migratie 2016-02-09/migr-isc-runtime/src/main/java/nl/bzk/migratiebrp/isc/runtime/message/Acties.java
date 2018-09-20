/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.message;

/**
 * Acties.
 */
public final class Acties {

    /** Actie: foutafhandeling gestart. */
    public static final String ACTIE_FOUTAFHANDELING_GESTART = "foutafhandelingGestart";
    /** Actie: proces gestart. */
    public static final String ACTIE_PROCES_GESTART = "procesGestart";
    /** Actie: proces vervolgd. */
    public static final String ACTIE_PROCES_VERVOLGD = "procesVervolgd";
    /** Actie: herhaling genegeerd. */
    public static final String ACTIE_HERHALING_GENEGEERD = "herhalingGenegeerd";
    /** Actie: herhaling beantwoord. */
    public static final String ACTIE_HERHALING_BEANTWOORD = "herhalingBeantwoord";

    private Acties() {
    }
}
