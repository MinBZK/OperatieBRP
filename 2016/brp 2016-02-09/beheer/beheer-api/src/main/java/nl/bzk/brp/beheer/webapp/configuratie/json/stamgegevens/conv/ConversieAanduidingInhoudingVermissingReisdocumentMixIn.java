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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut;
import nl.bzk.brp.model.beheer.kern.AanduidingInhoudingVermissingReisdocument;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieAanduidingInhoudingVermissingReisdocument}.
 */
public interface ConversieAanduidingInhoudingVermissingReisdocumentMixIn extends StamgegevenMixIn {

    /**
     * @return Rubriek 3570 Aanduiding inhouding dan wel vermissing Nederlands reisdocument.
     */
    @JsonProperty
    LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut getRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument();

    /**
     * @return aanduiding inhouding/vermissing reisdocument
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = AanduidingConverter.class)
    AanduidingInhoudingVermissingReisdocument getAanduidingInhoudingVermissingReisdocument();

    /**
     * AanduidingInhoudingVermissingReisdocumentAttribuut converter.
     */
    class AanduidingConverter extends AbstractIdConverter<AanduidingInhoudingVermissingReisdocument> {

        /**
         * Constructor.
         */
        public AanduidingConverter() {
            super(AanduidingInhoudingVermissingReisdocument.class);
        }
    }
}
