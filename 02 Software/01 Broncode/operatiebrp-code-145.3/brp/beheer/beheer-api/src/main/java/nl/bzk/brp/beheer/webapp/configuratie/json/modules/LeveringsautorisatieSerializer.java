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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;

import org.springframework.stereotype.Component;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie} object te vertalen naar JSON. Wordt via de
 * {@link LeveringsautorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class LeveringsautorisatieSerializer extends JsonSerializer<Leveringsautorisatie> {

    @Override
    public final void serialize(final Leveringsautorisatie value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.STELSEL, EnumUtils.getId(value.getStelsel()));
        JsonUtils.writeAsString(
            jgen,
            LeveringsautorisatieModule.INDICATIE_MODEL_AUTORISATIE,
            value.getIndicatieModelautorisatie(),
            LeveringsautorisatieModule.WAARDE_JA,
            LeveringsautorisatieModule.WAARDE_NEE);
        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.NAAM, value.getNaam());
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.PROTOCOLLERINGSNIVEAU, EnumUtils.getId(value.getProtocolleringsniveau()));
        JsonUtils.writeAsString(
            jgen,
            LeveringsautorisatieModule.INDICATIE_ALIAS_LEVEREN,
            value.getIndicatieAliasSoortAdministratieveHandelingLeveren(),
            LeveringsautorisatieModule.WAARDE_JA,
            LeveringsautorisatieModule.WAARDE_NEE);
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.DATUM_INGANG, value.getDatumIngang());
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.DATUM_EINDE, value.getDatumEinde());
        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.POPULATIE_BEPERKING, value.getPopulatiebeperking());
        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.TOELICHTING, value.getToelichting());
        JsonUtils.writeAsString(
            jgen,
            LeveringsautorisatieModule.INDICATIE_GEBLOKKEERD,
            value.getIndicatieGeblokkeerd(),
            LeveringsautorisatieModule.WAARDE_JA,
            LeveringsautorisatieModule.WAARDE_NEE);
        jgen.writeEndObject();
    }

}
