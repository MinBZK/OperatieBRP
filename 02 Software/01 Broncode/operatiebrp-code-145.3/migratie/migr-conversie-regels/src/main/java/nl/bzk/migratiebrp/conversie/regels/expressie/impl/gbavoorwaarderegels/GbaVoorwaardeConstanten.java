/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

/**
 * Constanten gebruikt door de gbavoorwaarde regels.
 */
final class GbaVoorwaardeConstanten {

    /**
     * Scheidings character gba voorwaarde regel.
     */
    public static final String SPLIT_CHARACTER = " ";
    /**
     * Rubriek deel van de voorwaarderegel.
     */
    public static final int DEEL_RUBRIEK = 0;
    /**
     * Operator deel van de voorwaarderegel.
     */
    public static final int DEEL_OPERATOR = 1;
    /**
     * Restdeel van de voorwaarderegel.
     */
    public static final int DEEL_REST = 2;
    /**
     * Aantal delen van een gba voorwaarde regel.
     */
    public static final int DEEL_AANTAL = 3;

    private GbaVoorwaardeConstanten() {
        // prevent instantiation
    }
}
