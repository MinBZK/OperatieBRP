/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting}.
 */
public abstract class AbstractRedenOpschortingMixIn extends AbstractStamgegevenMixIn {

    /** @return Rubriek 6720 Omschrijving reden opschorting bijhouding. */
    @JsonProperty("rubr6720omsrdnopschortingbij")
    abstract char getLo3OmschrijvingRedenOpschorting();

    /** @return nadere bijhoudingsaard */
    @JsonProperty("naderebijhaard")
    @JsonSerialize(using = NadereBijhoudingsaardSerializer.class)
    @JsonDeserialize(converter = NadereBijhoudingsaardConverter.class)
    abstract NadereBijhoudingsaard getRedenOpschorting();

    /**
     * Inner class voor het serialiseren van Stelsel.
     */
    private static class NadereBijhoudingsaardSerializer extends JsonSerializer<NadereBijhoudingsaard> {

        @Override
        public final void serialize(final NadereBijhoudingsaard value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.getId());
        }
    }

    /**
     * RedenOpschorting converter.
     */
    private static class NadereBijhoudingsaardConverter extends AbstractEnumConverter<NadereBijhoudingsaard> {
        /**
         * Constructor.
         */
        protected NadereBijhoudingsaardConverter() {
            super(NadereBijhoudingsaard.class);
        }
    }
}
