/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.ToegangLeveringsautorisatie} object te vertalen naar JSON. Wordt
 * via de {@link ToegangLeveringsAutorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class ToegangLeveringsAutorisatieSerializer extends JsonSerializer<ToegangLeveringsAutorisatie> {

    @Override
    public final void serialize(final ToegangLeveringsAutorisatie value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, ToegangLeveringsAutorisatieModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, ToegangLeveringsAutorisatieModule.LEVERINGSAUTORISATIE, value.getLeveringsautorisatie().getId());

        if (value.getGeautoriseerde() != null) {
            JsonUtils.writeAsInteger(jgen, ToegangLeveringsAutorisatieModule.GEAUTORISEERDE, value.getGeautoriseerde().getId());
        }
        if (value.getOndertekenaar() != null) {
            JsonUtils.writeAsInteger(jgen, ToegangLeveringsAutorisatieModule.ONDERTEKENAAR, value.getOndertekenaar().getId());
        }
        if (value.getTransporteur() != null) {
            JsonUtils.writeAsInteger(jgen, ToegangLeveringsAutorisatieModule.TRANSPORTEUR, value.getTransporteur().getId());
        }
        JsonUtils.writeAsInteger(jgen, ToegangLeveringsAutorisatieModule.DATUM_INGANG, value.getDatumIngang());
        JsonUtils.writeAsInteger(jgen, ToegangLeveringsAutorisatieModule.DATUM_EINDE, value.getDatumEinde());
        JsonUtils.writeAsString(jgen, ToegangLeveringsAutorisatieModule.NADERE_POPULATIE_BEPERKING, value.getNaderePopulatiebeperking());
        JsonUtils.writeAsString(jgen, ToegangLeveringsAutorisatieModule.AFLEVERPUNT, value.getAfleverpunt());
        JsonUtils.writeAsString(
                jgen,
                ToegangLeveringsAutorisatieModule.INDICATIE_GEBLOKKEERD,
                value.getIndicatieGeblokkeerd(),
                ToegangLeveringsAutorisatieModule.WAARDE_JA,
                ToegangLeveringsAutorisatieModule.WAARDE_NEE);
        jgen.writeEndObject();
    }
}
