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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;

import org.springframework.stereotype.Component;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.ToegangLeveringsautorisatie} object te vertalen naar JSON. Wordt
 * via de {@link ToegangLeveringsAutorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class VoorvoegselSerializer extends JsonSerializer<Voorvoegsel> {

    @Override
    public final void serialize(final Voorvoegsel value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, VoorvoegselModule.ID, value.getId());
        JsonUtils.writeAsString(jgen, VoorvoegselModule.VOORVOEGSEL, value.getVoorvoegselSleutel().getVoorvoegsel());
        JsonUtils.writeAsString(jgen, VoorvoegselModule.SCHEIDINGSTEKEN, String.valueOf(value.getVoorvoegselSleutel().getScheidingsteken()));
        jgen.writeEndObject();
    }
}
