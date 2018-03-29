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
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.sql.Timestamp;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRepository;
import org.springframework.stereotype.Component;

/**
 * Deserialiseer een inkomend vrij bericht object.
 */
@Component
final class VrijBerichtDeserializer extends JsonDeserializer<VrijBericht> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    private PartijRepository partijRepository;

    @Override
    public VrijBericht deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Integer id = JsonUtils.getAsInteger(node, "id");
        VrijBericht vrijBericht;
        if (id != null) {
            // Bij een bestaand vrij bericht alleen indicatieGelezen aanpassen.
            vrijBericht = entityManager.find(VrijBericht.class, id);
            final String ongelezen = JsonUtils.getAsString(node, "ongelezen");
            boolean gelezen;
            switch (ongelezen) {
                case "Nee":
                    gelezen = true;
                    break;
                case "Ja":
                default:
                    gelezen = false;
            }
            vrijBericht.setIndicatieGelezen(gelezen);
        } else {
            final Short soortvrijberId = JsonUtils.getAsShort(node, "soortvrijber");
            final String data = JsonUtils.getAsString(node, "data");
            final SoortVrijBericht soortVrijBericht = entityManager.getReference(SoortVrijBericht.class, soortvrijberId);
            vrijBericht =
                    new VrijBericht(SoortBerichtVrijBericht.STUUR_VRIJ_BERICHT, soortVrijBericht,
                            Timestamp.from(DatumUtil.nuAlsZonedDateTimeInNederland().toInstant()), data, null);
            final ArrayNode partijen = (ArrayNode) node.get("partijen");
            for (JsonNode partijNode : partijen) {
                final Short partijId = Short.valueOf(partijNode.asText());
                final VrijBerichtPartij vrijBerichtPartij = new VrijBerichtPartij(vrijBericht, partijRepository.findOne(partijId));
                vrijBericht.getVrijBerichtPartijen().add(vrijBerichtPartij);
            }
        }
        return vrijBericht;
    }

    @Inject
    public void setPartijRepository(final PartijRepository partijRepository) {
        this.partijRepository = partijRepository;
    }
}
