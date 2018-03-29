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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;

import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.ToegangBijhoudingsautorisatie}.
 * Wordt via de {@link ToegangBijhoudingsAutorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class ToegangBijhoudingsAutorisatieDeserializer extends JsonDeserializer<ToegangBijhoudingsautorisatie> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final ToegangBijhoudingsautorisatie deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Bijhoudingsautorisatie bijhoudingsautorisatie =
                entityManager.getReference(
                    Bijhoudingsautorisatie.class,
                    JsonUtils.getAsInteger(node, ToegangBijhoudingsAutorisatieModule.BIJHOUDINGSAUTORISATIE));

        final PartijRol partijRol =
                entityManager.getReference(PartijRol.class, JsonUtils.getAsInteger(node, ToegangBijhoudingsAutorisatieModule.GEAUTORISEERDE));

        final ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie = new ToegangBijhoudingsautorisatie(partijRol, bijhoudingsautorisatie);

        toegangBijhoudingsautorisatie.setId(JsonUtils.getAsInteger(node, ToegangBijhoudingsAutorisatieModule.ID));

        if (JsonUtils.getAsInteger(node, ToegangBijhoudingsAutorisatieModule.ONDERTEKENAAR) != null) {
            final Partij partij = entityManager.getReference(Partij.class, JsonUtils.getAsShort(node, ToegangBijhoudingsAutorisatieModule.ONDERTEKENAAR));
            toegangBijhoudingsautorisatie.setOndertekenaar(partij);
        }

        if (JsonUtils.getAsInteger(node, ToegangBijhoudingsAutorisatieModule.TRANSPORTEUR) != null) {
            final Partij partij = entityManager.getReference(Partij.class, JsonUtils.getAsShort(node, ToegangBijhoudingsAutorisatieModule.TRANSPORTEUR));
            toegangBijhoudingsautorisatie.setTransporteur(partij);
        }

        toegangBijhoudingsautorisatie.setDatumIngang(JsonUtils.getAsInteger(node, ToegangBijhoudingsAutorisatieModule.DATUM_INGANG));
        toegangBijhoudingsautorisatie.setDatumEinde(JsonUtils.getAsInteger(node, ToegangBijhoudingsAutorisatieModule.DATUM_EINDE));
        toegangBijhoudingsautorisatie.setAfleverpunt(JsonUtils.getAsString(node, ToegangBijhoudingsAutorisatieModule.AFLEVERPUNT));
        toegangBijhoudingsautorisatie.setIndicatieGeblokkeerd(
            JsonUtils.getAsBoolean(
                node,
                ToegangBijhoudingsAutorisatieModule.INDICATIE_GEBLOKKEERD,
                ToegangBijhoudingsAutorisatieModule.WAARDE_JA,
                Boolean.TRUE,
                null));

        return toegangBijhoudingsautorisatie;
    }
}
