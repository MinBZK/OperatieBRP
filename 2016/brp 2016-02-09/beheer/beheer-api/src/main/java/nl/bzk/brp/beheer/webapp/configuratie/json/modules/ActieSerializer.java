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
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 */
public class ActieSerializer extends JsonSerializer<ActieModel> {

    private final SimpleDateFormat sdf = new SimpleDateFormat(AdministratieveHandelingModule.DATUM_TIJD_FORMAAT);

    @Override
    public final void serialize(final ActieModel value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        SerializerUtils.writeId(jgen, value.getID());
        JsonUtils.writeAsInteger(jgen, ElementEnum.ACTIE_SOORTNAAM.getElementNaam(), ObjectUtils.<Integer>getWaarde(value, "soort.waarde.ordinal"));
        JsonUtils.writeAsString(
            jgen,
            ElementEnum.ACTIE_TIJDSTIPREGISTRATIE.getElementNaam(),
            ObjectUtils.getGeformateerdeWaarde(value, "tijdstipRegistratie.waarde", sdf));
        JsonUtils.writeAsInteger(
            jgen,
            ElementEnum.ACTIE_DATUMONTLENING.getElementNaam(),
            ObjectUtils.<Integer>getWaarde(value, "datumOntlening.waarde"));
        jgen.writeEndObject();
    }
}
