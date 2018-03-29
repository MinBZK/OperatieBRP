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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import nl.bzk.brp.beheer.webapp.view.BijhoudingsautorisatieSoortAdministratieveHandelingView;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.IOException;

/**
 * bijhoudingsautorisatieSoortAdministratieveHandeling deserializer.
 */
@Component
public class BijhoudingsautorisatieSoortAdministratieveHandelingDeserializer extends JsonDeserializer<BijhoudingsautorisatieSoortAdministratieveHandelingView> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final BijhoudingsautorisatieSoortAdministratieveHandelingView deserialize(
            final JsonParser jp,
            final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Bijhoudingsautorisatie bijhoudingsautorisatie =
                entityManager.getReference(Bijhoudingsautorisatie.class, JsonUtils.getAsInteger(node,
                        BijhoudingsautorisatieSoortAdministratieveHandelingModule.BIJHOUDINGSAUTORISATIE));

        final SoortAdministratieveHandeling soortAdministratieveHandeling = EnumUtils.getAsEnum(SoortAdministratieveHandeling.class,
                JsonUtils.getAsInteger(node, BijhoudingsautorisatieSoortAdministratieveHandelingModule.SOORT_ADMINISTRATIEVE_HANDELING));

        final nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling bijhoudingsautorisatieSoortAdministratieveHandeling;
        if (JsonUtils.getAsInteger(node, BijhoudingsautorisatieSoortAdministratieveHandelingModule.ID) != null) {
            bijhoudingsautorisatieSoortAdministratieveHandeling =
                    entityManager.getReference(
                            nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling.class,
                            JsonUtils.getAsInteger(node, BijhoudingsautorisatieSoortAdministratieveHandelingModule.ID));

        } else {
            bijhoudingsautorisatieSoortAdministratieveHandeling =
                    new nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling(
                            bijhoudingsautorisatie,
                            soortAdministratieveHandeling);
        }

        final Boolean actief =
                JsonUtils.getAsBoolean(
                        node,
                        DienstbundelGroepAttribuutModule.ACTIEF,
                        DienstbundelGroepAttribuutModule.WAARDE_JA,
                        Boolean.TRUE,
                        Boolean.FALSE);

        return new BijhoudingsautorisatieSoortAdministratieveHandelingView(bijhoudingsautorisatieSoortAdministratieveHandeling, actief);
    }
}
