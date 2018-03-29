/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Mixin voor een enum met id, naam en datum ingang/einde.
 */
public abstract class AbstractIdNaamDatumEnumMixIn extends AbstractIdNaamEnumMixIn {

    /** @return datum ingang */
    @JsonProperty("datumAanvangGeldigheid")
    abstract Integer getDatumAanvangGeldigheid();

    /** @return datum einde */
    @JsonProperty("datumEindeGeldigheid")
    abstract Integer getDatumEindeGeldigheid();
}
