/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import org.springframework.stereotype.Component;

/**
 * Maakt json object aan voor een VrijBerichtListView object.
 */
@Component
public final class VrijBerichtPartijSerializer extends JsonSerializer<VrijBerichtPartij> {

    @Override
    public void serialize(final VrijBerichtPartij vrijBerichtPartij, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        JsonUtils.writeAsInteger(jgen, "id", ObjectUtils.getWaarde(vrijBerichtPartij, "id"));
        JsonUtils.writeAsString(jgen, "partijCode", ObjectUtils.getWaarde(vrijBerichtPartij, "partij.code"));
        JsonUtils.writeAsString(jgen, "partijNaam", ObjectUtils.getWaarde(vrijBerichtPartij, "partij.naam"));
        JsonUtils.writeAsString(jgen, "soortPartij", ObjectUtils.getWaarde(vrijBerichtPartij, "partij.soortPartij.naam"));

        jgen.writeEndObject();
    }

}
