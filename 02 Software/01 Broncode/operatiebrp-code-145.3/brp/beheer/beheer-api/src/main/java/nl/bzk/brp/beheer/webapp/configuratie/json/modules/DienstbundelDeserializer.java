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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.Dienstbundel}. Wordt via de
 * {@link DienstbundelModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class DienstbundelDeserializer extends JsonDeserializer<Dienstbundel> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final Dienstbundel deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Leveringsautorisatie leveringsautorisatie =
                entityManager.getReference(Leveringsautorisatie.class, JsonUtils.getAsInteger(node, DienstbundelModule.LEVERINGSAUTORISATIE));

        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setLeveringsautorisatie(leveringsautorisatie);
        dienstbundel.setId(JsonUtils.getAsInteger(node, DienstbundelModule.ID));
        dienstbundel.setNaam(JsonUtils.getAsString(node, DienstbundelModule.NAAM));
        dienstbundel.setDatumIngang(JsonUtils.getAsInteger(node, DienstbundelModule.DATUM_INGANG));
        dienstbundel.setDatumEinde(JsonUtils.getAsInteger(node, DienstbundelModule.DATUM_EINDE));
        dienstbundel.setNaderePopulatiebeperking(JsonUtils.getAsString(node, DienstbundelModule.NADERE_POPULATIE_BEPERKING));
        dienstbundel.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(
            JsonUtils.getAsBoolean(
                node,
                DienstbundelModule.INDICATIE_NADERE_POPULATIE_BEPERKING_GECONVERTEERD,
                DienstbundelModule.WAARDE_NEE,
                Boolean.FALSE,
                null));
        dienstbundel.setToelichting(JsonUtils.getAsString(node, DienstbundelModule.TOELICHTING));
        dienstbundel.setIndicatieGeblokkeerd(
            JsonUtils.getAsBoolean(node, DienstbundelModule.INDICATIE_GEBLOKKEERD, DienstbundelModule.WAARDE_JA, Boolean.TRUE, null));

        return dienstbundel;
    }

}
