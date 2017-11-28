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
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.Bijhoudingsautorisatie}. Wordt via
 * de {@link LeveringsautorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class BijhoudingsautorisatieDeserializer extends JsonDeserializer<Bijhoudingsautorisatie> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final Bijhoudingsautorisatie deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Boolean indicatieModelautorisatie =
                JsonUtils.getAsBoolean(
                        node,
                        BijhoudingsautorisatieModule.INDICATIE_MODEL_AUTORISATIE,
                        BijhoudingsautorisatieModule.WAARDE_JA,
                        Boolean.TRUE,
                        Boolean.FALSE);
        final Bijhoudingsautorisatie bijhoudingsautorisatie = new Bijhoudingsautorisatie(indicatieModelautorisatie);

        bijhoudingsautorisatie.setId(JsonUtils.getAsInteger(node, BijhoudingsautorisatieModule.ID));
        bijhoudingsautorisatie.setNaam(JsonUtils.getAsString(node, BijhoudingsautorisatieModule.NAAM));
        bijhoudingsautorisatie.setDatumIngang(JsonUtils.getAsInteger(node, BijhoudingsautorisatieModule.DATUM_INGANG));
        bijhoudingsautorisatie.setDatumEinde(JsonUtils.getAsInteger(node, BijhoudingsautorisatieModule.DATUM_EINDE));
        bijhoudingsautorisatie.setIndicatieGeblokkeerd(
                JsonUtils.getAsBoolean(node, BijhoudingsautorisatieModule.INDICATIE_GEBLOKKEERD, BijhoudingsautorisatieModule.WAARDE_JA, Boolean.TRUE, null));

        return bijhoudingsautorisatie;
    }
}
