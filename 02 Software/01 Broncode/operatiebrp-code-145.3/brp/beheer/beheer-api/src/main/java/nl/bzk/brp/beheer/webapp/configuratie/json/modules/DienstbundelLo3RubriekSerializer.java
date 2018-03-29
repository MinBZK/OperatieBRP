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

import nl.bzk.brp.beheer.webapp.view.DienstbundelLo3RubriekView;

import org.springframework.stereotype.Component;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek} object te vertalen naar JSON. Wordt via
 * de {@link DienstbundelLo3RubriekModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class DienstbundelLo3RubriekSerializer extends JsonSerializer<DienstbundelLo3RubriekView> {

    @Override
    public final void serialize(final DienstbundelLo3RubriekView value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, DienstbundelLo3RubriekModule.ID, value.getId());

        JsonUtils.writeAsString(
            jgen,
            DienstbundelLo3RubriekModule.ACTIEF,
            value.isActief(),
            DienstbundelLo3RubriekModule.WAARDE_JA,
            DienstbundelLo3RubriekModule.WAARDE_NEE);

        JsonUtils.writeAsInteger(jgen, DienstbundelLo3RubriekModule.DIENSTBUNDEL, value.getDienstbundel().getId());

        JsonUtils.writeAsInteger(jgen, DienstbundelLo3RubriekModule.RUBRIEK, value.getLo3Rubriek().getId());
        JsonUtils.writeAsString(jgen, DienstbundelLo3RubriekModule.RUBRIEK_NAAM, value.getLo3Rubriek().getNaam());

        jgen.writeEndObject();
    }

}
