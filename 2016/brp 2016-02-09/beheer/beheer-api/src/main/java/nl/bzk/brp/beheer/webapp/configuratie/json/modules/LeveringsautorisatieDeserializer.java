/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import nl.bzk.brp.beheer.webapp.util.AttribuutUtils;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie}. Wordt via de
 * {@link LeveringsautorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
public class LeveringsautorisatieDeserializer extends JsonDeserializer<Leveringsautorisatie> {

    @Override
    public final Leveringsautorisatie deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie();

        leveringsautorisatie.setID(JsonUtils.getAsInteger(node, LeveringsautorisatieModule.ID));
        leveringsautorisatie.setNaam(AttribuutUtils.getAsAttribuut(
            NaamEnumeratiewaardeAttribuut.class, JsonUtils.getAsString(node, LeveringsautorisatieModule.NAAM)));
        leveringsautorisatie.setPopulatiebeperking(AttribuutUtils.getAsAttribuut(
            PopulatiebeperkingAttribuut.class,
            JsonUtils.getAsString(node, LeveringsautorisatieModule.POPULATIE_BEPERKING)));
        leveringsautorisatie.setProtocolleringsniveau(EnumUtils.getAsEnum(
            Protocolleringsniveau.class,
            JsonUtils.getAsInteger(node, LeveringsautorisatieModule.PROTOCOLLERINGSNIVEAU)));
        leveringsautorisatie.setDatumIngang(AttribuutUtils.getAsAttribuut(
            DatumAttribuut.class,
            JsonUtils.getAsInteger(node, LeveringsautorisatieModule.DATUM_INGANG)));
        leveringsautorisatie.setDatumEinde(AttribuutUtils.getAsAttribuut(
            DatumAttribuut.class,
            JsonUtils.getAsInteger(node, LeveringsautorisatieModule.DATUM_EINDE)));
        leveringsautorisatie.setIndicatieAliasSoortAdministratieveHandelingLeveren(AttribuutUtils.getAsAttribuut(
            JaNeeAttribuut.class,
            JsonUtils.getAsBoolean(node,
                    LeveringsautorisatieModule.INDICATIE_ALIAS_LEVEREN,
                    LeveringsautorisatieModule.WAARDE_JA,
                    Boolean.TRUE,
                    Boolean.FALSE)));
        leveringsautorisatie.setIndicatiePopulatiebeperkingVolledigGeconverteerd(AttribuutUtils.getAsAttribuut(
            NeeAttribuut.class,
            JsonUtils.getAsNee(node, LeveringsautorisatieModule.INDICATIE_POPULATIEBEPERKING_VOLLEDIG_GECONVERTEERD, LeveringsautorisatieModule.WAARDE_NEE)));
        leveringsautorisatie.setToelichting(AttribuutUtils.getAsAttribuut(
            OnbeperkteOmschrijvingAttribuut.class,
            JsonUtils.getAsString(node, LeveringsautorisatieModule.TOELICHTING)));

        return leveringsautorisatie;
    }
}
