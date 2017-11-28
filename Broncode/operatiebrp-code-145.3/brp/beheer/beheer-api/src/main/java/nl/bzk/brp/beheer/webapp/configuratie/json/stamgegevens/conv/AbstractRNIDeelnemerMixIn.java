/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer}.
 */
public abstract class AbstractRNIDeelnemerMixIn extends AbstractStamgegevenMixIn {

    /**
     * @return Rubriek 8811 Code RNI-deelnemer.
     */
    @JsonProperty("rubriek8811CodeRNIDeelnemer")
    abstract String getLo3CodeRNIDeelnemer();

    /**
     * @return partij
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = PartijConverter.class)
    abstract Partij getPartij();

    /**
     * Partij converter.
     */
    private static class PartijConverter extends AbstractIdConverter<Partij> {
        /**
         * Constructor.
         */
        PartijConverter() {
            super(Partij.class);
        }
    }
}
