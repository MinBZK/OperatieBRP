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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.util.EnumUtils;

import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Deserializer om Json object te vertalen naar {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik}.
 * Wordt via de {@link SoortActieBrongebruikModule} geregistreerd bij de
 * {@link nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper}.
 */
@Component
public class SoortActieBrongebruikDeserializer extends JsonDeserializer<SoortActieBrongebruik> {

    @PersistenceContext(unitName = RepositoryConfiguratie.PERSISTENCE_UNIT_MASTER)
    private EntityManager entityManager;

    @Override
    public final SoortActieBrongebruik deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        final ObjectCodec oc = jp.getCodec();
        final JsonNode node = oc.readTree(jp);

        final SoortActie soortActie = EnumUtils.getAsEnum(SoortActie.class, JsonUtils.getAsInteger(node, SoortActieBrongebruikModule.SOORT_ACTIE));
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
                EnumUtils.getAsEnum(
                        SoortAdministratieveHandeling.class,
                        JsonUtils.getAsInteger(node, SoortActieBrongebruikModule.SOORT_ADMINISTRATIEVE_HANDELING));
        final SoortDocument soortDocument =
                entityManager.find(SoortDocument.class, JsonUtils.getAsShort(node, SoortActieBrongebruikModule.SOORT_DOCUMENT));

        final SoortActieBrongebruikSleutel soortActieBrongebruikSleutel =
                new SoortActieBrongebruikSleutel(soortActie, soortAdministratieveHandeling, soortDocument);

        final SoortActieBrongebruik soortActieBrongebruik = new SoortActieBrongebruik(soortActieBrongebruikSleutel);

        soortActieBrongebruik.setId(JsonUtils.getAsShort(node, SoortActieBrongebruikModule.ID));
        soortActieBrongebruik.setDatumAanvangGeldigheid(JsonUtils.getAsInteger(node, SoortActieBrongebruikModule.DATUM_AANVANG_GELDIGHEID));
        soortActieBrongebruik.setDatumEindeGeldigheid(JsonUtils.getAsInteger(node, SoortActieBrongebruikModule.DATUM_EINDE_GELDIGHEID));

        return soortActieBrongebruik;
    }
}
