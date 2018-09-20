/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.beheer.kern.RedenVerliesNLNationaliteit;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieRedenBeeindigenNationaliteit}.
 */
public interface ConversieRedenBeeindigenNationaliteitMixIn {

    /**
     * Geeft LO3 reden be&euml;indigen Nationaliteit.
     *
     * @return Rubriek 6410 Reden Be&euml;indigen Nationaliteit.
     */
    @JsonProperty
    ConversieRedenBeeindigenNationaliteitAttribuut getRubriek6410RedenBeeindigenNationaliteit();

    /**
     * Geeft BRP reden verlies.
     *
     * @return Reden verlies.
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenVerliesConverter.class)
    RedenVerliesNLNationaliteit getRedenVerlies();

    /**
     * AanduidingInhoudingVermissingReisdocumentAttribuut converter.
     */
    class RedenVerliesConverter extends AbstractIdConverter<RedenVerliesNLNationaliteit> {
        /**
         * Constructor.
         */
        public RedenVerliesConverter() {
            super(RedenVerliesNLNationaliteit.class);
        }
    }
}
