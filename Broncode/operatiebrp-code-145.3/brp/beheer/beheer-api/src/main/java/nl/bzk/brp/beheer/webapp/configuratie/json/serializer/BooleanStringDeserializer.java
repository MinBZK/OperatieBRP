/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * Partij serializer.
 */
public class BooleanStringDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public final Boolean deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {

        final String value = jp.getValueAsString();
        return value == null ? null : "Ja".equals(value) ? Boolean.TRUE : Boolean.FALSE;
    }
}
