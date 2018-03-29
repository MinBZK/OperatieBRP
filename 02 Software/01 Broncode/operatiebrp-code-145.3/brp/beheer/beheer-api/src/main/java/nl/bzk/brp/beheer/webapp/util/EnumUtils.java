/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.util;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;

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
     * @param enumClass
     *            enumeratie klasse
     * @param id
     *            id
     * @param <T>
     *            enum type
     * @return enumeratie waarde
     */
    public static <T extends Enumeratie> T getAsEnum(final Class<T> enumClass, final Integer id) {
        if (id == null) {
            return null;
        }

        final Map<Integer, T> result = new HashMap<>();
        for (final T sge : enumClass.getEnumConstants()) {
            result.put(sge.getId(), sge);
        }
        return result.get(id);
    }

    /**
     * Geef ordinal van een willekeurige enum of null indien enum null is.
     *
     * @param waarde
     *            enum waarde
     * @return ordinal van enum of null
     */
    public static Integer getId(final Enumeratie waarde) {
        Integer result = null;
        if (waarde != null) {
            result = waarde.getId();
        }
        return result;
    }
}
