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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingGedeblokkeerdeMeldingModel;

/**
 * AdministratieveHandelingGedeblokkeerdeMelding serializer.
 */
public class GedeblokkeerdeMeldingSerializer extends JsonSerializer<AdministratieveHandelingGedeblokkeerdeMeldingModel> {

    @Override
    public final void serialize(final AdministratieveHandelingGedeblokkeerdeMeldingModel value,
                                final JsonGenerator jgen,
                                final SerializerProvider provider) throws IOException
    {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen,
                ElementEnum.ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_ADMINISTRATIEVEHANDELING.getElementNaam(),
                ObjectUtils.<Integer>getWaarde(value, "administratieveHandeling.iD"));
        JsonUtils.writeAsInteger(jgen,
                ElementEnum.GEDEBLOKKEERDEMELDING_IDENTITEIT.getElementNaam(),
                ObjectUtils.<Integer>getWaarde(value, "gedeblokkeerdeMelding.iD"));
        writeRegel(value, jgen);

        //value.getGedeblokkeerdeMelding().getRegel().getWaarde().getPropertyMeldingPaden();

        JsonUtils.writeAsString(jgen, ElementEnum.GEDEBLOKKEERDEMELDING_MELDING.getElementNaam(), ObjectUtils.<String>getWaarde(value, "gedeblokkeerdeMelding.melding.waarde"));
        jgen.writeEndObject();
    }

    private void writeRegel(final AdministratieveHandelingGedeblokkeerdeMeldingModel value, final JsonGenerator jgen) throws IOException {
        JsonUtils.writeAsString(jgen, ElementEnum.REGEL_SOORTNAAM.getElementNaam(), ObjectUtils.<String>getWaarde(value, "gedeblokkeerdeMelding.regel.waarde.soort.naam"));
        JsonUtils.writeAsString(jgen, ElementEnum.REGEL_CODE.getElementNaam(), ObjectUtils.<String>getWaarde(value, "gedeblokkeerdeMelding.regel.waarde.code"));
        JsonUtils.writeAsString(jgen, ElementEnum.REGEL_OMSCHRIJVING.getElementNaam(), ObjectUtils.<String>getWaarde(value, "gedeblokkeerdeMelding.regel.waarde.omschrijving"));
        JsonUtils.writeAsString(jgen, ElementEnum.REGEL_SPECIFICATIE.getElementNaam(), ObjectUtils.<String>getWaarde(value, "gedeblokkeerdeMelding.regel.waarde.specificatie"));
    }
}
