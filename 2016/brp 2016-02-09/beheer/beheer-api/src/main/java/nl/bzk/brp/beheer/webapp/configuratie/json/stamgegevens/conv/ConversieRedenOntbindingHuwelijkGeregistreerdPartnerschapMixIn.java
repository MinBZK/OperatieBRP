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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut;
import nl.bzk.brp.model.beheer.kern.RedenEindeRelatie;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap} .
 */
public interface ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschapMixIn extends StamgegevenMixIn {

    /**
     * @return Rubriek 0741 Reden ontbinding huwelijk/geregistreerd partnerschap.
     */
    @JsonProperty
    LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut getRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap();

    /**
     * @return reden einde relatie
     */
    @JsonProperty
    @JsonSerialize(using = IdSerializer.class)
    @JsonDeserialize(converter = RedenEindeRelatieConverter.class)
    RedenEindeRelatie getRedenEindeRelatie();

    /**
     * RedenEindeRelatie converter.
     */
    class RedenEindeRelatieConverter extends AbstractIdConverter<RedenEindeRelatie> {
        /**
         * Constructor.
         */
        public RedenEindeRelatieConverter() {
            super(RedenEindeRelatie.class);
        }
    }

}
