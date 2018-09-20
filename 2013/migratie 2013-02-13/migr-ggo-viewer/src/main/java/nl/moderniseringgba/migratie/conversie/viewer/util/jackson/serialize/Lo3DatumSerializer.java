/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util.jackson.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;

/**
 * Deze class is nodig om tijdens de JSON serialisatie de waarde van een Lo3Datum te wijzigen.
 * 
 */
public class Lo3DatumSerializer extends JsonSerializer<Lo3Datum> {

    @Override
    public final void serialize(final Lo3Datum value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        jgen.writeString(value.getDatum() == 0 ? "00000000" : "" + value.getDatum());
    }

    @Override
    public final Class<Lo3Datum> handledType() {
        return Lo3Datum.class;
    }

}
