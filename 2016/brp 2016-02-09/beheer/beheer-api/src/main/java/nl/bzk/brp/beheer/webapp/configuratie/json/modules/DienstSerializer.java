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
import nl.bzk.brp.model.beheer.autaut.Dienst;

/**
 * Dienst serializer.
 */
public class DienstSerializer extends JsonSerializer<Dienst> {

    @Override
    public final void serialize(final Dienst value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.ID, value.getID());
        //JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.ABONNEMENT, value.getAbonnement().getID());
        //JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.CATALOGUSOPTIE, EnumUtils.ordinal(value.getCatalogusOptie()));
        //JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.NADERE_POPULATIE_BEPERKING, AttribuutUtils.getWaarde(value.getNaderePopulatiebeperking()));
        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.ATTENDERINGSCRITERIUM, AttribuutUtils.getWaarde(value.getAttenderingscriterium()));
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.DATUM_INGANG, AttribuutUtils.getWaarde(value.getDatumIngang()));
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.DATUM_EINDE, AttribuutUtils.getWaarde(value.getDatumEinde()));
        //JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.TOESTAND, EnumUtils.ordinal(value.getToestand()));
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.EERSTE_SELECTIEDATUM, AttribuutUtils.getWaarde(value.getEersteSelectiedatum()));
        JsonUtils.writeAsInteger(jgen, LeveringsautorisatieModule.SELECTIEPERIODE_IN_MAANDEN, AttribuutUtils.getWaarde(value.getSelectieperiodeInMaanden()));
//        JsonUtils.writeAsString(
//            jgen,
//            LeveringsautorisatieModule.INDICATIE_NADERE_POPULATIEBEPERKING_VOLLEDIG_GECONVERTEERD,
//            AttribuutUtils.getWaarde(value.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd()),
//            LeveringsautorisatieModule.WAARDE_NEE,
//            LeveringsautorisatieModule.WAARDE_JA);
//        JsonUtils.writeAsString(jgen, LeveringsautorisatieModule.TOELICHTING, AttribuutUtils.getWaarde(value.getToelichting()));

        jgen.writeEndObject();
    }
}
