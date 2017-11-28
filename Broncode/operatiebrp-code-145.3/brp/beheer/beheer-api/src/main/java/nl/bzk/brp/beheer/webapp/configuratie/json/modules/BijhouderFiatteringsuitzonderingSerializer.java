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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import org.springframework.stereotype.Component;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.BijhouderFiatteringsuitzondering} object te vertalen naar JSON.
 * Wordt via de {@link BijhouderFiatteringsuitzonderingModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class BijhouderFiatteringsuitzonderingSerializer extends JsonSerializer<BijhouderFiatteringsuitzondering> {

    @Override
    public final void serialize(final BijhouderFiatteringsuitzondering value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, BijhouderFiatteringsuitzonderingModule.ID, value.getId());
        JsonUtils.writeAsInteger(jgen, BijhouderFiatteringsuitzonderingModule.BIJHOUDER, value.getBijhouder().getId());
        if (value.getBijhouderBijhoudingsvoorstel() != null) {
            JsonUtils.writeAsInteger(
                    jgen,
                    BijhouderFiatteringsuitzonderingModule.BIJHOUDER_BIJHOUDINGSVOORSTEL,
                    value.getBijhouderBijhoudingsvoorstel().getId());
        }
        JsonUtils.writeAsInteger(jgen, BijhouderFiatteringsuitzonderingModule.DATUM_INGANG, value.getDatumIngang());
        JsonUtils.writeAsInteger(jgen, BijhouderFiatteringsuitzonderingModule.DATUM_EINDE, value.getDatumEinde());
        JsonUtils.writeAsString(
                jgen,
                BijhouderFiatteringsuitzonderingModule.INDICATIE_GEBLOKKEERD,
                value.getIndicatieGeblokkeerd(),
                BijhouderFiatteringsuitzonderingModule.WAARDE_JA,
                BijhouderFiatteringsuitzonderingModule.WAARDE_NEE);
        JsonUtils.writeAsInteger(
                jgen,
                BijhouderFiatteringsuitzonderingModule.SOORT_ADMINISTRATIEVE_HANDELING,
                EnumUtils.getId(value.getSoortAdministratieveHandeling()));
        if (value.getSoortDocument() != null) {
            JsonUtils.writeAsInteger(jgen, BijhouderFiatteringsuitzonderingModule.SOORT_DOCUMENT, value.getSoortDocument().getId());
        }
        jgen.writeEndObject();
    }

}
