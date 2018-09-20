/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;

/**
 *
 */
public class PlatBetrokkenheidSerializer extends JsonSerializer<Set<BetrokkenheidModel>> {
    /**
     *
     */
    public PlatBetrokkenheidSerializer() {
        super();
    }

    @Override
    public void serialize(final Set<BetrokkenheidModel> value, final JsonGenerator jgen,
                          final SerializerProvider provider)
            throws IOException, JsonProcessingException
    {
        jgen.writeStartArray();

        Iterator<BetrokkenheidModel> betrokkenheidIterator = value.iterator();

        while (betrokkenheidIterator.hasNext()) {
            BetrokkenheidModel model = betrokkenheidIterator.next();
            jgen.writeStartObject();
            jgen.writeNumberField("id", model.getId());
            jgen.writeObjectField("rol", model.getRol());

            // Betrokkenheid -> Persoon
//            jgen.writeObjectFieldStart("betrokkene");
//            jgen.writeNumberField("id", model.getBetrokkene().getId());
//            jgen.writeEndObject();

            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
