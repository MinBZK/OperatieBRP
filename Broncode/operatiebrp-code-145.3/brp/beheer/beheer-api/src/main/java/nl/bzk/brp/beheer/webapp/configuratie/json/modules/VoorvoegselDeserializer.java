/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;

import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel}. Wordt via
 * de {@link VoorvoegselModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class VoorvoegselDeserializer extends JsonDeserializer<Voorvoegsel> {

    @Override
    public final Voorvoegsel deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Character scheidingsteken = JsonUtils.getAsCharacter(node, VoorvoegselModule.SCHEIDINGSTEKEN);
        final String voorvoegsel = JsonUtils.getAsString(node, VoorvoegselModule.VOORVOEGSEL);

        final VoorvoegselSleutel voorvoegselSleutel = new VoorvoegselSleutel(scheidingsteken, voorvoegsel);

        final Voorvoegsel voorvoegselObject = new Voorvoegsel(voorvoegselSleutel);

        voorvoegselObject.setId(JsonUtils.getAsShort(node, VoorvoegselModule.ID));

        return voorvoegselObject;
    }
}
