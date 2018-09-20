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
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3NederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.beheer.kern.SoortNederlandsReisdocument;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieSoortNLReisdocument}.
 */
public interface ConversieSoortNLReisdocumentMixIn extends StamgegevenMixIn {

    /**
     * @return Rubriek 3511 Nederlands reisdocument.
     */
    @JsonProperty
    LO3NederlandsReisdocumentAttribuut getRubriek3511NederlandsReisdocument();

    /**
     * @return soort nederlands reisdocument
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = SoortNederlandsReisdocumentConverter.class)
    SoortNederlandsReisdocument getSoortNederlandsReisdocument();

    /**
     * SoortNederlandsReisdocument converter.
     */
    class SoortNederlandsReisdocumentConverter extends AbstractIdConverter<SoortNederlandsReisdocument> {
        /**
         * Constructor.
         */
        protected SoortNederlandsReisdocumentConverter() {
            super(SoortNederlandsReisdocument.class);
        }
    }
}
