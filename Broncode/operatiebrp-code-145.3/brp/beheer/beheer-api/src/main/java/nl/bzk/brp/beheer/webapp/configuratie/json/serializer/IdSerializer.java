/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;

/**
 * Partij serializer.
 */
public class IdSerializer extends JsonSerializer<Object> {

    @Override
    public final void serialize(final Object value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeNumber(((Entiteit) value).getId().shortValue());
    }
}
