/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.PartijIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RNIDeelnemerAttribuut;
import nl.bzk.brp.model.beheer.kern.Partij;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieRNIDeelnemer}.
 */
public interface ConversieRNIDeelnemerMixIn extends StamgegevenMixIn {

    /**
     * @return Rubriek 8811 Code RNI-deelnemer.
     */
    @JsonProperty
    LO3RNIDeelnemerAttribuut getRubriek8811CodeRNIDeelnemer();

    /**
     * @return partij
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = PartijIdConverter.class)
    Partij getPartij();

}
