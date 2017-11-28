/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;

import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie}. Wordt via
 * de {@link LeveringsautorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class LeveringsautorisatieDeserializer extends JsonDeserializer<Leveringsautorisatie> {

    @Override
    public final Leveringsautorisatie deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Stelsel stelsel = EnumUtils.getAsEnum(Stelsel.class, JsonUtils.getAsInteger(node, LeveringsautorisatieModule.STELSEL));
        final Boolean indicatieModelautorisatie =
                JsonUtils.getAsBoolean(
                    node,
                    LeveringsautorisatieModule.INDICATIE_MODEL_AUTORISATIE,
                    LeveringsautorisatieModule.WAARDE_JA,
                    Boolean.TRUE,
                    Boolean.FALSE);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(stelsel, indicatieModelautorisatie);
        leveringsautorisatie.setId(JsonUtils.getAsInteger(node, LeveringsautorisatieModule.ID));
        leveringsautorisatie.setNaam(JsonUtils.getAsString(node, LeveringsautorisatieModule.NAAM));
        leveringsautorisatie.setProtocolleringsniveau(
            EnumUtils.getAsEnum(Protocolleringsniveau.class, JsonUtils.getAsInteger(node, LeveringsautorisatieModule.PROTOCOLLERINGSNIVEAU)));
        leveringsautorisatie.setIndicatieAliasSoortAdministratieveHandelingLeveren(
            JsonUtils.getAsBoolean(
                node,
                LeveringsautorisatieModule.INDICATIE_ALIAS_LEVEREN,
                LeveringsautorisatieModule.WAARDE_JA,
                Boolean.TRUE,
                Boolean.FALSE));
        leveringsautorisatie.setDatumIngang(JsonUtils.getAsInteger(node, LeveringsautorisatieModule.DATUM_INGANG));
        leveringsautorisatie.setDatumEinde(JsonUtils.getAsInteger(node, LeveringsautorisatieModule.DATUM_EINDE));
        leveringsautorisatie.setPopulatiebeperking(JsonUtils.getAsString(node, LeveringsautorisatieModule.POPULATIE_BEPERKING));
        leveringsautorisatie.setToelichting(JsonUtils.getAsString(node, LeveringsautorisatieModule.TOELICHTING));
        leveringsautorisatie.setIndicatieGeblokkeerd(
            JsonUtils.getAsBoolean(node, LeveringsautorisatieModule.INDICATIE_GEBLOKKEERD, LeveringsautorisatieModule.WAARDE_JA, Boolean.TRUE, null));

        return leveringsautorisatie;
    }
}
