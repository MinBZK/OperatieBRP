/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut;
import nl.bzk.brp.model.beheer.kern.Aangever;
import nl.bzk.brp.model.beheer.kern.RedenWijzigingVerblijf;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieAangifteAdreshouding}.
 */
public interface ConversieAangifteAdreshoudingMixIn extends StamgegevenMixIn {

    /**
     * @return Rubriek 7210 Omschrijving van de aangifte adreshouding.
     */
    @JsonProperty
    LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut getRubriek7210OmschrijvingVanDeAangifteAdreshouding();

    /**
     * @return aangever
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = AangeverConverter.class)
    Aangever getAangever();

    /**
     * @return reden wijziging verblijf
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenConverter.class)
    RedenWijzigingVerblijf getRedenWijzigingVerblijf();

    /**
     * Aangever converter.
     */
    class AangeverConverter extends AbstractConverter<Integer, Aangever> {
        @Override
        public Aangever convert(final Integer value) {
            final Aangever aangever = new Aangever();
            aangever.setID(value.shortValue());
            return aangever;
        }
    }

    /**
     * RedenWijzigingVerblijf converter.
     */
    class RedenConverter extends AbstractIdConverter<RedenWijzigingVerblijf> {
        /**
         * Constructor.
         */
        public RedenConverter() {
            super(RedenWijzigingVerblijf.class);
        }
    }
}
