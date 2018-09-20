/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.brm;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.BaseEnumMixIn;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

/**
 * Mix-in voor {@link nl.bzk.brp.model.algemeen.stamgegeven.brm.RegelSoortBericht}.
 */
public interface RegelSoortBerichtMixIn extends BaseEnumMixIn {

    /**
     * @return Regel.
     */
    @JsonProperty
    Regel getRegel();

    /**
     * @return Soort bericht.
     */
    @JsonProperty
    SoortBericht getSoortBericht();
}
