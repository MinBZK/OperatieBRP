/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import nl.bzk.brp.beheer.webapp.view.BijhoudingsautorisatieSoortAdministratieveHandelingView;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Dienst serializer.
 */
@Component
public class BijhoudingsautorisatieSoortAdministratieveHandelingSerializer extends JsonSerializer<BijhoudingsautorisatieSoortAdministratieveHandelingView> {

    @Override
    public final void serialize(
            final BijhoudingsautorisatieSoortAdministratieveHandelingView value,
            final JsonGenerator jgen,
            final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, BijhoudingsautorisatieSoortAdministratieveHandelingModule.ID, value.getId());
        JsonUtils.writeAsInteger(
                jgen,
                BijhoudingsautorisatieSoortAdministratieveHandelingModule.BIJHOUDINGSAUTORISATIE,
                value.getBijhoudingsautorisatie().getId());
        JsonUtils.writeAsInteger(
                jgen,
                BijhoudingsautorisatieSoortAdministratieveHandelingModule.SOORT_ADMINISTRATIEVE_HANDELING,
                value.getSoortAdministratievehandeling().getId());
        JsonUtils.writeAsString(jgen, BijhoudingsautorisatieSoortAdministratieveHandelingModule.NAAM, value.getSoortAdministratievehandeling().getNaam());
        JsonUtils.writeAsString(
                jgen,
                BijhoudingsautorisatieSoortAdministratieveHandelingModule.ACTIEF,
                value.isActief(),
                BijhoudingsautorisatieSoortAdministratieveHandelingModule.WAARDE_JA,
                BijhoudingsautorisatieSoortAdministratieveHandelingModule.WAARDE_NEE);
        jgen.writeEndObject();
    }
}
