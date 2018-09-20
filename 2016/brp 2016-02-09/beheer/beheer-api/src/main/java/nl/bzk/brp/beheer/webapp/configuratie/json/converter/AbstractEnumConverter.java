/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

/**
 * Abstract converter (uses unknown types).
 *
 * @param <O> OUT
 */
public abstract class AbstractEnumConverter<O extends Enum<?>> implements Converter<Integer, O> {

    private final Class<O> enumClazz;

    /**
     * Constructor.
     *
     * @param enumClazz enum klasse
     */
    protected AbstractEnumConverter(final Class<O> enumClazz) {
        this.enumClazz = enumClazz;
    }

    @Override
    public final O convert(final Integer input) {
        return enumClazz.getEnumConstants()[input];
    }

    @Override
    public final JavaType getInputType(final TypeFactory typeFactory) {
        return TypeFactory.unknownType();
    }

    @Override
    public final JavaType getOutputType(final TypeFactory typeFactory) {
        return TypeFactory.unknownType();
    }
}
