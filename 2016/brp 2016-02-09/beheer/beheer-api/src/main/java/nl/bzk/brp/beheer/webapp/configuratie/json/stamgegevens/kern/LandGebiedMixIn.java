/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ShortStamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2Attribuut;

/**
 * Mix-in for {@link nl.bzk.brp.model.beheer.kern.LandGebied}.
 */
public interface LandGebiedMixIn extends ShortStamgegevenMixIn {

    /** @return ISO3166-1-Alpha2 */
    @JsonProperty
    ISO31661Alpha2Attribuut getISO31661Alpha2();

}
