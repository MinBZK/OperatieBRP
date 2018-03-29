/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.Dienstbundel} object te vertalen naar JSON. Wordt via de
 * {@link DienstbundelModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class DienstbundelSerializer extends JsonSerializer<Dienstbundel> {

    @Override
    public final void serialize(final Dienstbundel value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, DienstbundelModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, DienstbundelModule.LEVERINGSAUTORISATIE, value.getLeveringsautorisatie().getId());
        JsonUtils.writeAsString(jgen, DienstbundelModule.NAAM, value.getNaam());
        JsonUtils.writeAsInteger(jgen, DienstbundelModule.DATUM_INGANG, value.getDatumIngang());
        JsonUtils.writeAsInteger(jgen, DienstbundelModule.DATUM_EINDE, value.getDatumEinde());
        JsonUtils.writeAsString(jgen, DienstbundelModule.NADERE_POPULATIE_BEPERKING, value.getNaderePopulatiebeperking());
        JsonUtils.writeAsString(
                jgen,
                DienstbundelModule.INDICATIE_NADERE_POPULATIE_BEPERKING_GECONVERTEERD,
                value.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() == null ? Boolean.TRUE : Boolean.FALSE,
                DienstbundelModule.WAARDE_JA,
                DienstbundelModule.WAARDE_NEE);
        JsonUtils.writeAsString(jgen, DienstbundelModule.TOELICHTING, value.getToelichting());
        JsonUtils.writeAsString(
                jgen,
                DienstbundelModule.INDICATIE_GEBLOKKEERD,
                value.getIndicatieGeblokkeerd(),
                DienstbundelModule.WAARDE_JA,
                DienstbundelModule.WAARDE_NEE);
        jgen.writeEndObject();
    }

}
