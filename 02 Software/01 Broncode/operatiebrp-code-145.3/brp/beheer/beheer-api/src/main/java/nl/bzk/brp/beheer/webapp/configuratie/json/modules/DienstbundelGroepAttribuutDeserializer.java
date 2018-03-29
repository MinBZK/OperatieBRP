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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;
import nl.bzk.brp.beheer.webapp.view.DienstbundelGroepAttribuutView;
import org.springframework.stereotype.Component;

/**
 * Dienst deserializer.
 */
@Component
public class DienstbundelGroepAttribuutDeserializer extends JsonDeserializer<DienstbundelGroepAttribuutView> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final DienstbundelGroepAttribuutView deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final DienstbundelGroep dienstbundelGroep =
                entityManager.getReference(DienstbundelGroep.class, JsonUtils.getAsInteger(node, DienstbundelGroepAttribuutModule.DIENSTBUNDEL_GROEP));
        final Element attribuut = EnumUtils.getAsEnum(Element.class, JsonUtils.getAsInteger(node, DienstbundelGroepAttribuutModule.ATTRIBUUT));
        final nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut dienstbundelGroepAttribuut;

        if (JsonUtils.getAsInteger(node, DienstbundelGroepAttribuutModule.ID) != null) {
            dienstbundelGroepAttribuut =
                    entityManager.getReference(
                            nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut.class,
                            JsonUtils.getAsInteger(node, DienstbundelGroepAttribuutModule.ID));

        } else {
            dienstbundelGroepAttribuut = new nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut(dienstbundelGroep, attribuut);
        }

        final Boolean actief =
                JsonUtils.getAsBoolean(
                        node,
                        DienstbundelGroepAttribuutModule.ACTIEF,
                        DienstbundelGroepAttribuutModule.WAARDE_JA,
                        Boolean.TRUE,
                        Boolean.FALSE);

        return new DienstbundelGroepAttribuutView(dienstbundelGroepAttribuut, actief);
    }
}
