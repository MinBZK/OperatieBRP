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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.beheer.kern.RedenVerkrijgingNLNationaliteit;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieRedenOpnameNationaliteit}.
 */
public interface ConversieRedenOpnameNationaliteitMixIn {

    /**
     * Geeft de LO3 reden opname nationaliteit.
     *
     * @return Rubriek 6310 Reden Opname Nationaliteit.
     */
    @JsonProperty
    LO3RedenOpnameNationaliteitAttribuut getRubriek6310RedenOpnameNationaliteit();

    /**
     * Geeft de BRP reden verkrijging.
     *
     * @return Reden verkrijging.
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenVerkrijgingConverter.class)
    RedenVerkrijgingNLNationaliteit getRedenVerkrijging();

    /**
     * AanduidingInhoudingVermissingReisdocumentAttribuut converter.
     */
    class RedenVerkrijgingConverter extends AbstractIdConverter<RedenVerkrijgingNLNationaliteit> {
        /**
         * Constructor.
         */
        protected RedenVerkrijgingConverter() {
            super(RedenVerkrijgingNLNationaliteit.class);
        }
    }
}
