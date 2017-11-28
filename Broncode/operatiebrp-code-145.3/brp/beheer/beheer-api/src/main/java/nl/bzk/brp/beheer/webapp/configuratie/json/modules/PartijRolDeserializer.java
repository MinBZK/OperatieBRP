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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;

import org.springframework.stereotype.Component;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.brp.model.beheer.autaut.Dienstbundel}. Wordt via de
 * {@link DienstbundelModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class PartijRolDeserializer extends JsonDeserializer<PartijRol> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final PartijRol deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final Partij partij = entityManager.getReference(Partij.class, JsonUtils.getAsShort(node, PartijRolModule.PARTIJ));
        final Rol rol = EnumUtils.getAsEnum(Rol.class, JsonUtils.getAsInteger(node, PartijRolModule.ROL));

        final PartijRol partijRol = new PartijRol(partij, rol);
        partijRol.setId(JsonUtils.getAsInteger(node, PartijRolModule.ID));
        partijRol.setDatumIngang(JsonUtils.getAsInteger(node, PartijRolModule.DATUM_INGANG));
        partijRol.setDatumEinde(JsonUtils.getAsInteger(node, PartijRolModule.DATUM_EINDE));
        return partijRol;
    }
}
