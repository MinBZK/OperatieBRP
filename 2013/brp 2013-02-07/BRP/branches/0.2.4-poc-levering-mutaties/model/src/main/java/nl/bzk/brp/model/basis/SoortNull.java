/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Enum voor soort null waarde.
 */
public enum SoortNull {

    /** De waarde wordt niet ondersteund. **/
    NIET_ONDERSTEUND("nietOndersteund"),
    /** Niet geautoriseerd om de waarde te zien. **/
    NIET_GEAUTORISEERD("nietGeautoriseerd"),
    /** Geen waarde. **/
    GEEN_WAARDE("geenWaarde"),
    /** Waarde is niet bekend. **/
    WAARDE_ONBEKEND("waardeOnbekend"),
    /** ? **/
    VASTGESTELD_ONBEKEND("vastgesteldOnbekend");

    private final String waarde;

    /**
     * Constructor.
     * @param waarde De soort null waarde als String.
     */
    private SoortNull(final String waarde) {
        this.waarde = waarde;
    }
}
