/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamDatumEnumMixIn;

/**
 * Mix-in for {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied}.
 */
public abstract class AbstractLandGebiedMixIn extends AbstractIdNaamDatumEnumMixIn {

    /**
     * @return code
     */
    @JsonProperty("code")
    abstract Short getCode();

    /**
     * @return ISO3166-1-Alpha2
     */
    @JsonProperty
    @JsonDeserialize(using = Iso31661Alpha2Deserializer.class)
    abstract String getIso31661Alpha2();

    private static class Iso31661Alpha2Deserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
            JsonNode node = jsonParser.readValueAsTree();
            if (node.asText().isEmpty()) {
                return null;
            }
            return node.asText();
        }
    }

}
