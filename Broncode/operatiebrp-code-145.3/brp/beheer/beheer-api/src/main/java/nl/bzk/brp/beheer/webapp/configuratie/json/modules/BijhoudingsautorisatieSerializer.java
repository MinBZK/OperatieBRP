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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import org.springframework.stereotype.Component;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.Bijhoudingsautorisatie} object te vertalen naar JSON. Wordt via
 * de {@link BijhoudingsautorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class BijhoudingsautorisatieSerializer extends JsonSerializer<Bijhoudingsautorisatie> {

    @Override
    public final void serialize(final Bijhoudingsautorisatie value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, BijhoudingsautorisatieModule.ID, value.getId());
        JsonUtils.writeAsString(
                jgen,
                BijhoudingsautorisatieModule.INDICATIE_MODEL_AUTORISATIE,
                value.getIndicatieModelautorisatie(),
                BijhoudingsautorisatieModule.WAARDE_JA,
                BijhoudingsautorisatieModule.WAARDE_NEE);
        JsonUtils.writeAsString(jgen, BijhoudingsautorisatieModule.NAAM, value.getNaam());
        JsonUtils.writeAsInteger(jgen, BijhoudingsautorisatieModule.DATUM_INGANG, value.getDatumIngang());
        JsonUtils.writeAsInteger(jgen, BijhoudingsautorisatieModule.DATUM_EINDE, value.getDatumEinde());
        JsonUtils.writeAsString(
                jgen,
                BijhoudingsautorisatieModule.INDICATIE_GEBLOKKEERD,
                value.getIndicatieGeblokkeerd(),
                BijhoudingsautorisatieModule.WAARDE_JA,
                BijhoudingsautorisatieModule.WAARDE_NEE);
        jgen.writeEndObject();
    }

}
