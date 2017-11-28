/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.brp.beheer.webapp.configuratie.json.converter.AbstractIdConverter;
import nl.bzk.brp.beheer.webapp.configuratie.json.serializer.IdSerializer;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOntbindingHuwelijkPartnerschap} .
 */
public abstract class AbstractRedenOntbindingHuwelijkPartnerschapMixIn extends AbstractStamgegevenMixIn {

    /**
     * @return Rubriek 0741 Reden ontbinding huwelijk/geregistreerd partnerschap.
     */
    @JsonProperty("rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap")
    abstract String getLo3RedenOntbindingHuwelijkGp();

    /**
     * @return reden einde relatie
     */
    @JsonProperty("redenEindeRelatie")
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenEindeRelatieConverter.class)
    abstract RedenBeeindigingRelatie getRedenBeeindigingRelatie();

    /**
     * RedenEindeRelatie converter.
     */
    private static class RedenEindeRelatieConverter extends AbstractIdConverter<RedenBeeindigingRelatie> {
        /**
         * Constructor.
         */
        RedenEindeRelatieConverter() {
            super(RedenBeeindigingRelatie.class);
        }
    }

}
