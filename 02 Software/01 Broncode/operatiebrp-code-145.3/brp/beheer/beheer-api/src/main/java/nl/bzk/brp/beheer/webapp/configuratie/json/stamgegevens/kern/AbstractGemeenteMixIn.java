/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamEnumMixIn;

/**
 * Mix-in for {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente}.
 */
public abstract class AbstractGemeenteMixIn extends AbstractIdNaamEnumMixIn {

    /** @return code */
    @JsonProperty("code")
    abstract Short getCode();

    /** @return datum aanvang geldigheid */
    @JsonProperty("datumAanvangGeldigheid")
    abstract Integer getDatumAanvangGeldigheid();

    /** @return datum einde geldigheid */
    @JsonProperty("datumEindeGeldigheid")
    abstract Integer getDatumEindeGeldigheid();

    /**
     * @return partij
     */
    @JsonProperty("partij")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = PartijConverter.class)
    abstract Partij getPartij();

    /**
     * @return voorzettende gemeente
     */
    @JsonProperty("voortzettendeGemeente")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = GemeenteConverter.class)
    abstract Gemeente getVoortzettendeGemeente();

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

    /**
     * Gemeente converter.
     */
    private static class GemeenteConverter extends AbstractIdConverter<Gemeente> {
        /**
         * Constructor.
         */
        GemeenteConverter() {
            super(Gemeente.class);
        }
    }
}
