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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;

import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.Bijhoudingsautorisatie}. Wordt via
 * de {@link LeveringsautorisatieModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class BijhouderFiatteringsuitzonderingDeserializer extends JsonDeserializer<BijhouderFiatteringsuitzondering> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final BijhouderFiatteringsuitzondering deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final PartijRol partijRolBijhouder =
                entityManager.getReference(PartijRol.class, JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.BIJHOUDER));

        final BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering = new BijhouderFiatteringsuitzondering(partijRolBijhouder);
        bijhouderFiatteringsuitzondering.setId(JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.ID));

        if (JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.BIJHOUDER_BIJHOUDINGSVOORSTEL) != null) {

            final PartijRol partijRolBijhouderBijhoudingsvoorstel =
                    entityManager.getReference(
                        PartijRol.class,
                        JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.BIJHOUDER_BIJHOUDINGSVOORSTEL));
            bijhouderFiatteringsuitzondering.setBijhouderBijhoudingsvoorstel(partijRolBijhouderBijhoudingsvoorstel);
        }

        bijhouderFiatteringsuitzondering.setDatumIngang(JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.DATUM_INGANG));
        bijhouderFiatteringsuitzondering.setDatumEinde(JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.DATUM_EINDE));
        bijhouderFiatteringsuitzondering.setIndicatieGeblokkeerd(
            JsonUtils.getAsBoolean(
                node,
                BijhouderFiatteringsuitzonderingModule.INDICATIE_GEBLOKKEERD,
                BijhouderFiatteringsuitzonderingModule.WAARDE_JA,
                Boolean.TRUE,
                null));
        bijhouderFiatteringsuitzondering.setSoortAdministratieveHandeling(
            EnumUtils.getAsEnum(
                SoortAdministratieveHandeling.class,
                JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.SOORT_ADMINISTRATIEVE_HANDELING)));
        if (JsonUtils.getAsInteger(node, BijhouderFiatteringsuitzonderingModule.SOORT_DOCUMENT) != null) {
            final SoortDocument soortDocument =
                    entityManager.getReference(SoortDocument.class, JsonUtils.getAsShort(node, BijhouderFiatteringsuitzonderingModule.SOORT_DOCUMENT));
            bijhouderFiatteringsuitzondering.setSoortDocument(soortDocument);
        }
        return bijhouderFiatteringsuitzondering;
    }
}
