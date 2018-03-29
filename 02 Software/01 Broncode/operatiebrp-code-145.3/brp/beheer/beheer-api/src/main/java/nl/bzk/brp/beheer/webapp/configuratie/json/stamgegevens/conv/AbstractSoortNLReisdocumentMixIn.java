/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument}.
 */
public abstract class AbstractSoortNLReisdocumentMixIn extends AbstractStamgegevenMixIn {

    /**
     * @return Rubriek 3511 Nederlands reisdocument.
     */
    @JsonProperty("rubriek3511NederlandsReisdocument")
    abstract String getLo3NederlandsReisdocument();

    /**
     * @return soort nederlands reisdocument
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = SoortNederlandsReisdocumentConverter.class)
    abstract SoortNederlandsReisdocument getSoortNederlandsReisdocument();

    /**
     * SoortNederlandsReisdocument converter.
     */
    private static class SoortNederlandsReisdocumentConverter extends AbstractIdConverter<SoortNederlandsReisdocument> {
        /**
         * Constructor.
         */
        protected SoortNederlandsReisdocumentConverter() {
            super(SoortNederlandsReisdocument.class);
        }
    }
}
