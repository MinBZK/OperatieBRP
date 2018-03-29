/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3CategorieMelding;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdCodeEnumMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3SoortMelding}.
 */
public abstract class AbstractLo3SoortMeldingMixIn extends AbstractIdCodeEnumMixIn {

    /** @return omschrijving */
    @JsonProperty("omschrijving")
    abstract String getOmschrijving();

    /** @return categorie melding */
    @JsonProperty
    abstract Lo3CategorieMelding getCategorieMelding();
}
