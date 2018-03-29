/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamEnumMixIn;

/**
 * Mix-in for {@link nl.bzk.algemeenbrp.dal.domein.brp.enums.Element}.
 */
public abstract class AbstractElementMixIn extends AbstractIdNaamEnumMixIn {

    /** @return soort */
    @JsonProperty
    abstract SoortElement getSoort();

    /** @return element naam */
    @JsonProperty
    abstract String getElementNaam();

    /** @return object type */
    @JsonProperty
    abstract Element getObjecttype();

    /** @return groep */
    @JsonProperty
    abstract Element getGroep();

    /** @return volgnummer */
    @JsonProperty
    abstract Integer getVolgnummer();

    /** @return alias van */
    @JsonProperty
    abstract Element getAliasVan();

    /** @return expressie */
    @JsonProperty
    abstract String getExpressie();

    /** @return autorisatie */
    @JsonProperty
    abstract SoortElementAutorisatie getAutorisatie();

    /** @return tabel */
    abstract Element getTabel();

    /** @return identificatie database */
    abstract Long getIdentificatieDatabase();

    /** @return his tabel */
    @JsonProperty
    abstract Element getHisTabel();

    /** @return his identificatie database */
    @JsonProperty
    abstract Long getHisIdentifierDatabase();

    /** @return leveren als stamgegeven */
    @JsonProperty
    abstract Boolean getLeverenAlsStamgegeven();

}
