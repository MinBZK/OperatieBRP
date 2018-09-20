/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.util;

/**
 * Enum utils.
 */
public final class EnumUtils {

    private EnumUtils() {
        // Niet instantieerbaar
    }

    /**
     * Geef een enumeratie waarde obv de klasse en de ordinal.
     *
     * @param enumClass enumeratie klasse
     * @param ordinalValue ordinal
     * @param <T> enum type
     * @return enumeratie waarde
     */
    public static <T extends Enum<?>> T getAsEnum(final Class<T> enumClass, final Integer ordinalValue) {
        return ordinalValue == null ? null : enumClass.getEnumConstants()[ordinalValue];
    }

    /**
     * Geef ordinal van een willekeurige enum of null indien enum null is.
     *
     * @param waarde enum waarde
     * @return ordinal van enum of null
     */
    public static Integer ordinal(final Enum<?> waarde) {
        Integer result = null;
        if (waarde != null) {
            result = waarde.ordinal();
        }
        return result;
    }
}
