/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * Administratieve handeling serializer.
 */
public class AdministratieveHandelingSerializer extends JsonSerializer<AdministratieveHandelingModel> {

    private final SimpleDateFormat sdf = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);

    @Override
    public final void serialize(final AdministratieveHandelingModel value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        SerializerUtils.writeId(jgen, value.getID());
        JsonUtils.writeAsInteger(jgen, ElementEnum.ADMINISTRATIEVEHANDELING_SOORTNAAM.getElementNaam(),
                ObjectUtils.<Integer>getWaarde(value, "soort.waarde.ordinal"));
        JsonUtils.writeAsInteger(jgen, ElementEnum.ADMINISTRATIEVEHANDELING_PARTIJCODE.getElementNaam(),
                ObjectUtils.<Short>getWaarde(value, "partij.waarde.iD"));
        JsonUtils.writeAsInteger(jgen, ElementEnum.ADMINISTRATIEVEHANDELING_PARTIJCODE.getElementNaam() + ElementEnum.PARTIJ_CODE.getElementNaam(),
                ObjectUtils.<Integer>getWaarde(value, "partij.waarde.code.waarde"));
        JsonUtils.writeAsString(
                jgen,
                ElementEnum.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getElementNaam(),
                ObjectUtils.getGeformateerdeWaarde(value, "tijdstipRegistratie.waarde", sdf));
        JsonUtils.writeAsString(
                jgen,
                ElementEnum.ADMINISTRATIEVEHANDELING_LEVERING_TIJDSTIP.getElementNaam(),
                ObjectUtils.getGeformateerdeWaarde(value, "levering.tijdstipLevering.waarde", sdf));
        JsonUtils.writeAsString(
                jgen,
                ElementEnum.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getElementNaam(),
                ObjectUtils.<String>getWaarde(value, "toelichtingOntlening.waarde"));
        jgen.writeEndObject();
    }

}
