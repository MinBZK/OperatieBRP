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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;

import org.springframework.stereotype.Component;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.ToegangLeveringsautorisatie} object te vertalen naar JSON. Wordt
 * via de {@link ToegangLeveringsAutorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class SoortActieBrongebruikSerializer extends JsonSerializer<SoortActieBrongebruik> {

    @Override
    public final void serialize(final SoortActieBrongebruik value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, SoortActieBrongebruikModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, SoortActieBrongebruikModule.SOORT_ACTIE, value.getSoortActieBrongebruikSleutel().getSoortActie().getId());
        JsonUtils.writeAsInteger(
            jgen,
            SoortActieBrongebruikModule.SOORT_ADMINISTRATIEVE_HANDELING,
            value.getSoortActieBrongebruikSleutel().getSoortAdministratieveHandeling().getId());
        JsonUtils.writeAsInteger(jgen, SoortActieBrongebruikModule.SOORT_DOCUMENT, value.getSoortActieBrongebruikSleutel().getSoortDocument().getId());
        JsonUtils.writeAsInteger(jgen, SoortActieBrongebruikModule.DATUM_AANVANG_GELDIGHEID, value.getDatumAanvangGeldigheid());
        JsonUtils.writeAsInteger(jgen, SoortActieBrongebruikModule.DATUM_EINDE_GELDIGHEID, value.getDatumEindeGeldigheid());
        jgen.writeEndObject();
    }
}
