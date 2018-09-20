/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.PartijIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ShortStamgegevenMixIn;
import nl.bzk.brp.model.beheer.kern.Gemeente;
import nl.bzk.brp.model.beheer.kern.Partij;

/**
 * Mix-in for {@link nl.bzk.brp.model.beheer.kern.Gemeente}.
 */
public interface GemeenteMixIn extends ShortStamgegevenMixIn {

    /**
     * @return partij
     */
    @JsonProperty("Partij")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = PartijIdConverter.class)
    Partij getPartij();

    /**
     * @return voorzettende gemeente
     */
    @JsonProperty("Voortzettende gemeente")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = GemeenteConverter.class)
    Gemeente getVoortzettendeGemeente();

    /**
     * Gemeente converter.
     */
    class GemeenteConverter extends AbstractIdConverter<Gemeente> {
        /**
         * Constructor.
         */
        public GemeenteConverter() {
            super(Gemeente.class);
        }
    }
}
