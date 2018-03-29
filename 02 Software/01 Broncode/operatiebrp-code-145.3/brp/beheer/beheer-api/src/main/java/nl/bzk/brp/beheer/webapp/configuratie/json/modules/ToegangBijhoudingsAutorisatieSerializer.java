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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;

import org.springframework.stereotype.Component;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.ToegangBijhoudingsautorisatie} object te vertalen naar JSON.
 * Wordt via de {@link ToegangBijhoudingsAutorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class ToegangBijhoudingsAutorisatieSerializer extends JsonSerializer<ToegangBijhoudingsautorisatie> {

    @Override
    public final void serialize(final ToegangBijhoudingsautorisatie value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, ToegangBijhoudingsAutorisatieModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, ToegangBijhoudingsAutorisatieModule.BIJHOUDINGSAUTORISATIE, value.getBijhoudingsautorisatie().getId());

        if (value.getGeautoriseerde() != null) {
            JsonUtils.writeAsInteger(jgen, ToegangBijhoudingsAutorisatieModule.GEAUTORISEERDE, value.getGeautoriseerde().getId());
        }
        if (value.getOndertekenaar() != null) {
            JsonUtils.writeAsInteger(jgen, ToegangBijhoudingsAutorisatieModule.ONDERTEKENAAR, value.getOndertekenaar().getId());
        }
        if (value.getTransporteur() != null) {
            JsonUtils.writeAsInteger(jgen, ToegangBijhoudingsAutorisatieModule.TRANSPORTEUR, value.getTransporteur().getId());
        }
        JsonUtils.writeAsInteger(jgen, ToegangBijhoudingsAutorisatieModule.DATUM_INGANG, value.getDatumIngang());
        JsonUtils.writeAsInteger(jgen, ToegangBijhoudingsAutorisatieModule.DATUM_EINDE, value.getDatumEinde());
        JsonUtils.writeAsString(jgen, ToegangBijhoudingsAutorisatieModule.AFLEVERPUNT, value.getAfleverpunt());
        JsonUtils.writeAsString(
                jgen,
                ToegangBijhoudingsAutorisatieModule.INDICATIE_GEBLOKKEERD,
                value.getIndicatieGeblokkeerd(),
                ToegangBijhoudingsAutorisatieModule.WAARDE_JA,
                ToegangBijhoudingsAutorisatieModule.WAARDE_NEE);
        jgen.writeEndObject();
    }
}
