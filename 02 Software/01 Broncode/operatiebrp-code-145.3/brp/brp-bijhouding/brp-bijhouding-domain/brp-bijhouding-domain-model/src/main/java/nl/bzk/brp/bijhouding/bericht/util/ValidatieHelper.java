/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.util;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Utility class die validatie methodes bevat.
 */
public final class ValidatieHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /*
     * Explicit private constructor.
     */
    private ValidatieHelper() {
        throw new AssertionError("Er mag geen instantie worden gemaakt van de ValidatieHelper class.");
    }

    /**
     * Valideer dat het gegeven object niet null is.
     * @param object het object
     * @param naam de naam die gebruikt wordt in de foutmelding
     * @throws IllegalArgumentException wanneer object null is
     */
    public static void controleerOpNullWaarde(final Object object, final String naam) {
        if (object == null) {
            throw new IllegalArgumentException(String.format("%s moet gevuld zijn", naam));
        }
    }

    /**
     * Vergelijkt een {@link String} waarde met een {@link Short} waarde. Als de {@link String} waarde niet numeriek is,
     * zal de methode false terug geven.
     * @param waardeA de {@link String} waarde
     * @param waardeB de {@link Short} waarde
     * @return true als waardeA een numeriek is en gelijk is aan waardeB, anders false.
     */
    public static boolean vergelijkStringMetShort(final String waardeA, final Short waardeB) {
        try {
            return waardeB.equals(Short.valueOf(waardeA));
        } catch (NumberFormatException e) {
            LOGGER.info(e.getMessage(), e);
        }
        return false;
    }

}
