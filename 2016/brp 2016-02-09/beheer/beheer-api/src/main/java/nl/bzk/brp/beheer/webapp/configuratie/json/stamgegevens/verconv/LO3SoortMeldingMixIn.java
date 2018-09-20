/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StringEnumMixIn;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3CategorieMelding;

/**
 * Mix-in voor {@link nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMelding}.
 */
public interface LO3SoortMeldingMixIn extends StringEnumMixIn {

    /** @return categorie melding */
    @JsonProperty
    LO3CategorieMelding getCategorieMelding();
}
