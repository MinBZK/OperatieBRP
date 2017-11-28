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

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.CharacterConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding}.
 */
public abstract class AbstractAangifteAdreshoudingMixIn extends AbstractStamgegevenMixIn {

    /**
     * Json property voor rubriek 7210.
     */
    private static final String RUBRIEK_7210_OMSCHRIJVING = "rubr7210omsvandeaangifteadre";

    /**
     * @return Rubriek 7210 Omschrijving van de aangifte adreshouding.
     */
    @JsonProperty(RUBRIEK_7210_OMSCHRIJVING)
    @JsonSerialize(using = ToStringSerializer.class)
    abstract char getLo3OmschrijvingAangifteAdreshouding();

    /**
     * @param lo3OmschrijvingAangifteAdreshouding
     *            Rubriek 7210 Omschrijving van de aangifte adreshouding.
     */
    @JsonProperty(RUBRIEK_7210_OMSCHRIJVING)
    @JsonDeserialize(converter = CharacterConverter.class)
    abstract void setLo3OmschrijvingAangifteAdreshouding(char lo3OmschrijvingAangifteAdreshouding);

    /**
     * @return aangever
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = AangeverConverter.class)
    abstract String getAangever();

    /**
     * @return reden wijziging verblijf
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenConverter.class)
    abstract RedenWijzigingVerblijf getRedenWijzigingVerblijf();

    /**
     * Aangever converter.
     */
    private static class AangeverConverter extends AbstractIdConverter<Aangever> {

        /**
         * Constructor.
         */
        AangeverConverter() {
            super(Aangever.class);
        }
    }

    /**
     * RedenWijzigingVerblijf converter.
     */
    private static class RedenConverter extends AbstractIdConverter<RedenWijzigingVerblijf> {
        /**
         * Constructor.
         */
        RedenConverter() {
            super(RedenWijzigingVerblijf.class);
        }
    }
}
