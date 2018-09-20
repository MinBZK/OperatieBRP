/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * Mixin voor SoortPartij.
 */
public interface SoortPartijMixIn {

    /** @return id */
    @JsonProperty("iD")
    Short getId();

    /** @return naam */
    @JsonProperty("Naam")
    NaamEnumeratiewaardeAttribuut getNaam();

    /** @return datum ingang */
    @JsonProperty("Datum ingang")
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid();

    /** @return datum einde */
    @JsonProperty("Datum einde")
    DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid();
}
