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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.view.DienstbundelLo3RubriekView;
import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek}. Wordt via
 * de {@link nl.bzk.brp.beheer.webapp.configuratie.json.modules.DienstbundelLo3RubriekModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class DienstbundelLo3RubriekDeserializer extends JsonDeserializer<DienstbundelLo3RubriekView> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final DienstbundelLo3RubriekView deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Dienstbundel dienstbundel =
                entityManager.getReference(Dienstbundel.class, JsonUtils.getAsInteger(node, DienstbundelLo3RubriekModule.DIENSTBUNDEL));
        final Lo3Rubriek rubriek = entityManager.getReference(Lo3Rubriek.class, JsonUtils.getAsInteger(node, DienstbundelLo3RubriekModule.RUBRIEK));
        final nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek dienstbundelLO3Rubriek;
        if (JsonUtils.getAsInteger(node, DienstbundelLo3RubriekModule.ID) != null) {
            dienstbundelLO3Rubriek =
                    entityManager.getReference(
                        nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek.class,
                        JsonUtils.getAsInteger(node, DienstbundelLo3RubriekModule.ID));

        } else {
            dienstbundelLO3Rubriek = new nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek(dienstbundel, rubriek);
        }
        final Boolean actief = JsonUtils.getAsBoolean(node, DienstbundelLo3RubriekModule.ACTIEF, "Ja", Boolean.TRUE, Boolean.FALSE);
        final DienstbundelLo3RubriekView viewRubriek = new DienstbundelLo3RubriekView(dienstbundelLO3Rubriek, actief);
        dienstbundelLO3Rubriek.setId(JsonUtils.getAsInteger(node, DienstbundelLo3RubriekModule.ID));

        return viewRubriek;

    }
}
