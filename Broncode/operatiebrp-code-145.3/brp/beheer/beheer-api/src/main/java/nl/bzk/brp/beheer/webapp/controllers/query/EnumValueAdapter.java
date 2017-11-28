/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;

/**
 * Enum value adapter.
 *
 * @param <T>
 *            enum type
 */
public final class EnumValueAdapter<T extends Enumeratie> implements ValueAdapter<T> {

    private final Map<Integer, T> result = new HashMap<>();

    /**
     * Constructor.
     *
     * @param enumClazz
     *            enum
     */
    public EnumValueAdapter(final Class<T> enumClazz) {

        for (final T sge : enumClazz.getEnumConstants()) {
            result.put(sge.getId(), sge);
        }
    }

    @Override
    public T adaptValue(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        }

        return result.get(Integer.parseInt(value));
    }
}
