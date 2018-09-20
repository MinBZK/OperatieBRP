/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersieDatamodelAttribuut;

/**
 * Mix-in for {@link nl.bzk.brp.model.algemeen.aanpasbaarstamgegeven.kern.Expressie}.
 */
public interface ExpressieMixIn extends StamgegevenMixIn {

    /** @return versie expressietaal */
    @JsonProperty
    NaamEnumeratiewaardeAttribuut getVersieExpressietaal();

    /** @return versie datamodel */
    @JsonProperty
    VersieDatamodelAttribuut getVersieDatamodel();

    /** @return expressietekst */
    @JsonProperty
    ExpressietekstAttribuut getExpressietekst();
}
