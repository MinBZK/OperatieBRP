/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.EnumParser;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;

/**
 * Abstract converter (uses unknown types).
 *
 * @param <O>
 *            OUT
 */
public abstract class AbstractEnumConverter<O extends Enumeratie> implements Converter<Number, O> {

    private final EnumParser<O> enumParser;

    /**
     * Constructor.
     *
     * @param enumClazz
     *            enum klasse
     */
    protected AbstractEnumConverter(final Class<O> enumClazz) {
        enumParser = new EnumParser<>(enumClazz);
    }

    @Override
    public final O convert(final Number input) {
        if (input == null) {
            return null;
        }
        return enumParser.parseId(input.intValue());
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
