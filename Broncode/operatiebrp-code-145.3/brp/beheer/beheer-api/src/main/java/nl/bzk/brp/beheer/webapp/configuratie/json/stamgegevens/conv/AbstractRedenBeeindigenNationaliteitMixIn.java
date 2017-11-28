/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingNationaliteit}.
 */
public abstract class AbstractRedenBeeindigenNationaliteitMixIn extends AbstractStamgegevenMixIn {

    /**
     * Geeft LO3 reden be&euml;indigen Nationaliteit.
     *
     * @return Rubriek 6410 Reden Be&euml;indigen Nationaliteit.
     */
    @JsonProperty("rubriek6410RedenBeeindigenNationaliteit")
    abstract String getRedenBeeindigingNationaliteit();

    /**
     * Geeft BRP reden verlies.
     *
     * @return Reden verlies.
     */
    @JsonProperty("redenVerlies")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenVerliesNLNationaliteitConverter.class)
    abstract RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit();

    /**
     * AanduidingInhoudingVermissingReisdocument converter.
     */
    private static class RedenVerliesNLNationaliteitConverter extends AbstractIdConverter<RedenVerliesNLNationaliteit> {
        /**
         * Constructor.
         */
        RedenVerliesNLNationaliteitConverter() {
            super(RedenVerliesNLNationaliteit.class);
        }
    }
}
