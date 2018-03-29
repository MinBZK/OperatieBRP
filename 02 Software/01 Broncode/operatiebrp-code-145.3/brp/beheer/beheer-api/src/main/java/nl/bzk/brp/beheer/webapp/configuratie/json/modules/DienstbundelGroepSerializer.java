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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import org.springframework.stereotype.Component;

/**
 * Dienst serializer.
 */
@Component
public class DienstbundelGroepSerializer extends JsonSerializer<DienstbundelGroep> {

    @Override
    public final void serialize(final DienstbundelGroep value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, DienstbundelGroepModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, DienstbundelGroepModule.DIENSTBUNDEL, value.getDienstbundel().getId());
        JsonUtils.writeAsString(jgen, DienstbundelGroepModule.GROEP, value.getGroep().getNaam());
        JsonUtils.writeAsInteger(jgen, DienstbundelGroepModule.GROEP_ID, value.getGroep().getId());
        JsonUtils.writeAsString(
            jgen,
            DienstbundelGroepModule.INDICATIE_FORMELE_HISTORIE,
            value.getIndicatieFormeleHistorie(),
            LeveringsautorisatieModule.WAARDE_JA,
            LeveringsautorisatieModule.WAARDE_NEE);
        JsonUtils.writeAsString(
            jgen,
            DienstbundelGroepModule.INDICATIE_MATERIELE_HISTORIE,
            value.getIndicatieMaterieleHistorie(),
            LeveringsautorisatieModule.WAARDE_JA,
            LeveringsautorisatieModule.WAARDE_NEE);
        JsonUtils.writeAsString(
            jgen,
            DienstbundelGroepModule.INDICATIE_VERANTWOORDING,
            value.getIndicatieVerantwoording(),
            LeveringsautorisatieModule.WAARDE_JA,
            LeveringsautorisatieModule.WAARDE_NEE);
        jgen.writeEndObject();
    }
}
