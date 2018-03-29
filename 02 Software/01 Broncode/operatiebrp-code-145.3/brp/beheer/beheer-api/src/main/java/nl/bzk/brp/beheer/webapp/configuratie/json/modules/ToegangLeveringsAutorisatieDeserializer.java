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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.ToegangLeveringsautorisatie}.
 * Wordt via de {@link ToegangLeveringsAutorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class ToegangLeveringsAutorisatieDeserializer extends JsonDeserializer<ToegangLeveringsAutorisatie> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final ToegangLeveringsAutorisatie deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Leveringsautorisatie leveringsautorisatie =
                entityManager.getReference(
                    Leveringsautorisatie.class,
                    JsonUtils.getAsInteger(node, ToegangLeveringsAutorisatieModule.LEVERINGSAUTORISATIE));

        final PartijRol partijRol =
                entityManager.getReference(PartijRol.class, JsonUtils.getAsInteger(node, ToegangLeveringsAutorisatieModule.GEAUTORISEERDE));

        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);

        toegangLeveringsautorisatie.setId(JsonUtils.getAsInteger(node, ToegangLeveringsAutorisatieModule.ID));

        if (JsonUtils.getAsInteger(node, ToegangLeveringsAutorisatieModule.ONDERTEKENAAR) != null) {
            final Partij partij = entityManager.getReference(Partij.class, JsonUtils.getAsShort(node, ToegangLeveringsAutorisatieModule.ONDERTEKENAAR));
            toegangLeveringsautorisatie.setOndertekenaar(partij);
        }

        if (JsonUtils.getAsInteger(node, ToegangLeveringsAutorisatieModule.TRANSPORTEUR) != null) {
            final Partij partij = entityManager.getReference(Partij.class, JsonUtils.getAsShort(node, ToegangLeveringsAutorisatieModule.TRANSPORTEUR));
            toegangLeveringsautorisatie.setTransporteur(partij);
        }

        toegangLeveringsautorisatie.setDatumIngang(JsonUtils.getAsInteger(node, ToegangLeveringsAutorisatieModule.DATUM_INGANG));
        toegangLeveringsautorisatie.setDatumEinde(JsonUtils.getAsInteger(node, ToegangLeveringsAutorisatieModule.DATUM_EINDE));
        toegangLeveringsautorisatie.setNaderePopulatiebeperking(JsonUtils.getAsString(node, ToegangLeveringsAutorisatieModule.NADERE_POPULATIE_BEPERKING));
        toegangLeveringsautorisatie.setAfleverpunt(JsonUtils.getAsString(node, ToegangLeveringsAutorisatieModule.AFLEVERPUNT));
        toegangLeveringsautorisatie.setIndicatieGeblokkeerd(
            JsonUtils.getAsBoolean(
                node,
                ToegangLeveringsAutorisatieModule.INDICATIE_GEBLOKKEERD,
                ToegangLeveringsAutorisatieModule.WAARDE_JA,
                Boolean.TRUE,
                null));

        return toegangLeveringsautorisatie;
    }
}
