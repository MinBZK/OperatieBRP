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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandelingGedeblokkeerdeRegel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.springframework.stereotype.Component;

/**
 * AdministratieveHandelingGedeblokkeerdeRegel serializer.
 */
@Component
public class GedeblokkeerdeMeldingSerializer extends JsonSerializer<AdministratieveHandelingGedeblokkeerdeRegel> {

    @Override
    public final void serialize(final AdministratieveHandelingGedeblokkeerdeRegel value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(
                jgen,
                Element.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEREGEL_ADMINISTRATIEVEHANDELING.getElementNaam(),
                value.getAdministratieveHandeling().getId());
        JsonUtils.writeAsInteger(jgen, Element.GEDEBLOKKEERDEMELDING_IDENTITEIT.getElementNaam(), value.getId());

        final Regel regel = value.getRegel();
        JsonUtils.writeAsString(jgen, Element.REGEL_CODE.getElementNaam(), regel.getCode());
        JsonUtils.writeAsString(jgen, Element.REGEL_SOORTMELDINGNAAM.getElementNaam(), regel.getSoortMelding().getNaam());

        jgen.writeEndObject();
    }
}
