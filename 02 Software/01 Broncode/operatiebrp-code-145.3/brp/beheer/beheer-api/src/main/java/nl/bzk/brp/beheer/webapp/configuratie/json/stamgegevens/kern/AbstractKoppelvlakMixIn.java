/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamEnumMixIn;

/**
 * Mix-in for {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Koppelvlak}.
 */
public abstract class AbstractKoppelvlakMixIn extends AbstractIdNaamEnumMixIn {

    /** @return Stelsel. */
    @JsonProperty("stelsel")
    @JsonSerialize(using = StelselSerializer.class)
    public abstract Stelsel getStelsel();

    /**
     * Inner class voor het serialiseren van Stelsel.
     */
    private static class StelselSerializer extends JsonSerializer<Stelsel> {
        @Override
        public final void serialize(final Stelsel value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
            jgen.writeString(value.getNaam());
        }
    }
}
