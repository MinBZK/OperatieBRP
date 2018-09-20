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
import nl.bzk.brp.beheer.webapp.util.AttribuutUtils;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;

/**
 * Serializer om {@link nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie} object te vertalen naar JSON. Wordt via de
 * {@link LeveringsautorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
public class LeveringsautorisatieSerializer extends JsonSerializer<Leveringsautorisatie> {

    @Override
    public final void serialize(final Leveringsautorisatie value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.ID, value.getID());
        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.NAAM, AttribuutUtils.getWaarde(value.getNaam()));
        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.POPULATIE_BEPERKING, AttribuutUtils.getWaarde(value.getPopulatiebeperking()));
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.PROTOCOLLERINGSNIVEAU, EnumUtils.ordinal(value.getProtocolleringsniveau()));
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.DATUM_INGANG, AttribuutUtils.getWaarde(value.getDatumIngang()));
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.DATUM_EINDE, AttribuutUtils.getWaarde(value.getDatumEinde()));
        JsonUtils.writeAsString(
            jgen,
            LeveringsautorisatieModule.INDICATIE_ALIAS_LEVEREN,
            AttribuutUtils.getWaarde(value.getIndicatieAliasSoortAdministratieveHandelingLeveren()),
            LeveringsautorisatieModule.WAARDE_JA,
            LeveringsautorisatieModule.WAARDE_NEE);
        JsonUtils.writeAsString(
            jgen,
            LeveringsautorisatieModule.INDICATIE_POPULATIEBEPERKING_VOLLEDIG_GECONVERTEERD,
            AttribuutUtils.getWaarde(value.getIndicatiePopulatiebeperkingVolledigGeconverteerd()),
            LeveringsautorisatieModule.WAARDE_NEE,
            LeveringsautorisatieModule.WAARDE_JA);
        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.TOELICHTING, AttribuutUtils.getWaarde(value.getToelichting()));
        jgen.writeEndObject();
    }

}
