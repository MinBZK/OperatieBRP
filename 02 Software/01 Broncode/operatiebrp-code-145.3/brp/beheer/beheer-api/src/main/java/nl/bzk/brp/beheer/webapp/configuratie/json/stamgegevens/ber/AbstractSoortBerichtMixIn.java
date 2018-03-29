/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ber;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.BurgerzakenModule;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Koppelvlak;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractEnumMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht}.
 */
public abstract class AbstractSoortBerichtMixIn extends AbstractEnumMixIn {

    /** @return identifier **/
    @JsonProperty("identifier")
    abstract String getIdentifier();

    /** @return burgerzakenmodule */
    @JsonProperty("module")
    abstract BurgerzakenModule getModule();

    /** @return koppelvlak */
    @JsonProperty("koppelvlak")
    abstract Koppelvlak getKoppelvlak();

    /** @return datum aanvang geldigheid */
    @JsonProperty("datumAanvangGeldigheid")
    abstract Integer getDatumAanvangGeldigheid();

    /** @return datum einde geldigheid */
    @JsonProperty("datumEindeGeldigheid")
    abstract Integer getDatumEindeGeldigheid();
}
