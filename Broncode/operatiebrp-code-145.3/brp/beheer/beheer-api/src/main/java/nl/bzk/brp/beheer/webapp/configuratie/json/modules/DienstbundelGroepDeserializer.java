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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import org.springframework.stereotype.Component;

/**
 * Dienst deserializer.
 */
@Component
public class DienstbundelGroepDeserializer extends JsonDeserializer<DienstbundelGroep> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final DienstbundelGroep deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Dienstbundel dienstbundel =
                entityManager.getReference(Dienstbundel.class, JsonUtils.getAsInteger(node, DienstbundelGroepModule.DIENSTBUNDEL));
        final Element groep = EnumUtils.getAsEnum(Element.class, JsonUtils.getAsInteger(node, DienstbundelGroepModule.GROEP_ID));

        dienstbundel.setId(JsonUtils.getAsInteger(node, DienstbundelGroepModule.DIENSTBUNDEL));

        final Boolean indicatieFormeleHistorie =
                JsonUtils.getAsBoolean(
                    node,
                    DienstbundelGroepModule.INDICATIE_FORMELE_HISTORIE,
                    DienstbundelGroepModule.WAARDE_JA,
                    Boolean.TRUE,
                    Boolean.FALSE);
        final Boolean indicatieMaterieleHistorie =
                JsonUtils.getAsBoolean(
                    node,
                    DienstbundelGroepModule.INDICATIE_MATERIELE_HISTORIE,
                    DienstbundelGroepModule.WAARDE_JA,
                    Boolean.TRUE,
                    Boolean.FALSE);
        final Boolean indicatieVerantwoording =
                JsonUtils.getAsBoolean(
                    node,
                    DienstbundelGroepModule.INDICATIE_VERANTWOORDING,
                    DienstbundelGroepModule.WAARDE_JA,
                    Boolean.TRUE,
                    Boolean.FALSE);
        final DienstbundelGroep dienstbundelGroep =
                new DienstbundelGroep(dienstbundel, groep, indicatieFormeleHistorie, indicatieMaterieleHistorie, indicatieVerantwoording);
        dienstbundelGroep.setId(JsonUtils.getAsInteger(node, DienstbundelGroepModule.ID));

        return dienstbundelGroep;
    }
}
