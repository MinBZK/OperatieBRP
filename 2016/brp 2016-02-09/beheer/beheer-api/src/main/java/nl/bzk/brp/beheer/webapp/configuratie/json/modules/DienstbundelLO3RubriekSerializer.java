/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek} object te vertalen naar JSON. Wordt via
 * de {@link DienstbundelLO3RubriekModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
public class DienstbundelLO3RubriekSerializer extends JsonSerializer<DienstbundelLO3Rubriek> {

    @Override
    public final void serialize(final DienstbundelLO3Rubriek value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, DienstbundelLO3RubriekModule.ID, value.getID());
        if (value.getDienstbundel() != null) {
            JsonUtils.writeAsInteger(jgen, DienstbundelLO3RubriekModule.DIENSTBUNDEL, value.getDienstbundel().getID());
        }
        if (value.getRubriek() != null) {
            JsonUtils.writeAsInteger(jgen, DienstbundelLO3RubriekModule.RUBRIEK, value.getRubriek().getID());
        }
        jgen.writeEndObject();
    }

}
