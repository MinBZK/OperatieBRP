/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.CharacterConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument}.
 */
public abstract class AbstractAanduidingInhoudingVermissingReisdocumentMixIn extends AbstractStamgegevenMixIn {

    /**
     * Rubriek 35.70.
     */
    private static final String RUBRIEK_3570 = "rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument";

    /**
     * @return Rubriek 3570 Aanduiding inhouding dan wel vermissing Nederlands reisdocument.
     */
    @JsonProperty(RUBRIEK_3570)
    @JsonSerialize(using = ToStringSerializer.class)
    abstract char getLo3AanduidingInhoudingOfVermissingReisdocument();

    /**
     * @param lo3AanduidingInhoudingOfVermissingReisdocument
     *            De lo3 aanduiding voor de inhouding of vermissing van het reisdocument.
     */
    @JsonProperty(RUBRIEK_3570)
    @JsonDeserialize(converter = CharacterConverter.class)
    abstract void setLo3AanduidingInhoudingOfVermissingReisdocument(char lo3AanduidingInhoudingOfVermissingReisdocument);

    /**
     * @return aanduiding inhouding/vermissing reisdocument
     */
    @JsonProperty("aanduidingInhoudingVermissingReisdocument")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = AanduidingConverter.class)
    abstract AanduidingInhoudingOfVermissingReisdocument getAanduidingInhoudingOfVermissingReisdocument();

    /**
     * AanduidingInhoudingVermissingReisdocument converter.
     */
    private static class AanduidingConverter extends AbstractIdConverter<AanduidingInhoudingOfVermissingReisdocument> {

        /**
         * Constructor.
         */
        AanduidingConverter() {
            super(AanduidingInhoudingOfVermissingReisdocument.class);
        }
    }
}
