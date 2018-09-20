/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

/**
 * Enum value adapter.
 *
 * @param <T> enum type
 */
public final class EnumValueAdapter<T extends Enum<?>> implements ValueAdapter<T> {

    private final Class<T> enumClazz;

    /**
     * Constructor.
     *
     * @param enumClazz enum
     */
    public EnumValueAdapter(final Class<T> enumClazz) {
        this.enumClazz = enumClazz;
    }

    @Override
    public T adaptValue(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        }

        return enumClazz.getEnumConstants()[Integer.parseInt(value)];
    }
}
