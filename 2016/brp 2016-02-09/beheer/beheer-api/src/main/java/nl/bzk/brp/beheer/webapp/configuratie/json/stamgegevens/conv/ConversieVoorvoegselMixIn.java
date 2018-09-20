/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;

/**
 * Mix-in voor {@link nl.bzk.brp.model.beheer.conv.ConversieVoorvoegsel}.
 */
public interface ConversieVoorvoegselMixIn extends StamgegevenMixIn {

    /** @return rubriek 02.30 */
    @JsonProperty
    LO3VoorvoegselAttribuut getRubriek0231Voorvoegsel();

    /** @return voorvoegsel */
    @JsonProperty
    NaamEnumeratiewaardeAttribuut getVoorvoegsel();

    /** @return scheidingsteken */
    @JsonProperty
    ScheidingstekenAttribuut getScheidingsteken();

}
