/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpnameNationaliteit}.
 */
public abstract class AbstractRedenOpnameNationaliteitMixIn extends AbstractStamgegevenMixIn {

    /**
     * Geeft de LO3 reden opname nationaliteit.
     *
     * @return Rubriek 6310 Reden Opname Nationaliteit.
     */
    @JsonProperty("rubriek6310RedenOpnameNationaliteit")
    abstract String getRedenOpnameNationaliteit();

    /**
     * Geeft de BRP reden verkrijging.
     *
     * @return Reden verkrijging.
     */
    @JsonProperty("redenVerkrijging")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenVerkrijgingConverter.class)
    abstract RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit();

    /**
     * AanduidingInhoudingVermissingReisdocument converter.
     */
    private static class RedenVerkrijgingConverter extends AbstractIdConverter<RedenVerkrijgingNLNationaliteit> {
        /**
         * Constructor.
         */
        RedenVerkrijgingConverter() {
            super(RedenVerkrijgingNLNationaliteit.class);
        }
    }
}
