/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractEnumConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.EnumSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingRedenOpschortingBijhoudingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieRedenOpschorting}.
 */
public interface ConversieRedenOpschortingMixIn extends StamgegevenMixIn {

    /** @return Rubriek 6720 Omschrijving reden opschorting bijhouding. */
    @JsonProperty
    LO3OmschrijvingRedenOpschortingBijhoudingAttribuut getRubriek6720OmschrijvingRedenOpschortingBijhouding();

    /** @return nadere bijhoudingsaard */
    @JsonProperty
    @JsonSerialize(using = EnumSerializer.class)
    @JsonDeserialize(converter = NadereBijhoudingsaardConverter.class)
    NadereBijhoudingsaard getNadereBijhoudingsaard();

    /**
     * NadereBijhoudingsaard converter.
     */
    class NadereBijhoudingsaardConverter extends AbstractEnumConverter<NadereBijhoudingsaard> {
        /**
         * Constructor.
         */
        protected NadereBijhoudingsaardConverter() {
            super(NadereBijhoudingsaard.class);
        }
    }

}
