/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;

/**
 * JaNeeAttribuut serializer.
 */
public class DatumEvtDeelsOnbekendAttribuutSerializer extends JsonSerializer<DatumEvtDeelsOnbekendAttribuut> {

    @Override
    public final void serialize(final DatumEvtDeelsOnbekendAttribuut value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeNumber(value.getWaarde());
    }
}
