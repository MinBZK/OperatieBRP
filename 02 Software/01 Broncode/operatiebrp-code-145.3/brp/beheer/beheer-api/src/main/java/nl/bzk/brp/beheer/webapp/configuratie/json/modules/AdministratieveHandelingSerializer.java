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
import java.text.SimpleDateFormat;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import org.springframework.stereotype.Component;

/**
 * Administratieve handeling serializer.
 */
@Component
public class AdministratieveHandelingSerializer extends JsonSerializer<AdministratieveHandeling> {

    private final SimpleDateFormat sdf = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);

    @Override
    public final void serialize(final AdministratieveHandeling value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, "id", value.getId());
        JsonUtils.writeAsInteger(jgen, Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getElementNaam(), ObjectUtils.<Integer>getWaarde(value, "soort.id"));
        JsonUtils.writeAsInteger(jgen, Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getElementNaam(), ObjectUtils.<Short>getWaarde(value, "partij.id"));
        JsonUtils.writeAsString(
            jgen,
            Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getElementNaam() + Element.PARTIJ_CODE.getElementNaam(),
            ObjectUtils.getWaarde(value, "partij.code"));
        JsonUtils.writeAsString(
            jgen,
            Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getElementNaam(),
            ObjectUtils.getGeformateerdeWaarde(value, "datumTijdRegistratie", sdf));
        JsonUtils.writeAsString(
            jgen,
            Element.ADMINISTRATIEVEHANDELING_LEVERING_TIJDSTIP.getElementNaam(),
            ObjectUtils.getGeformateerdeWaarde(value, "datumTijdLevering", sdf));
        JsonUtils.writeAsString(
            jgen,
            Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getElementNaam(),
            ObjectUtils.<String>getWaarde(value, "toelichtingOntlening"));
        jgen.writeEndObject();
    }

}
