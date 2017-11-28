/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractStamgegevenMixIn;

/**
 * Mix-in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel}.
 */
public abstract class AbstractVoorvoegselMixIn extends AbstractStamgegevenMixIn {

    /** @return rubriek 02.30 */
    @JsonProperty("lo3Voorvoegsel")
    abstract String getLo3Voorvoegsel();

    /** @return voorvoegsel */
    @JsonProperty
    abstract String getVoorvoegsel();

    /** @return scheidingsteken */
    @JsonProperty
    abstract String getScheidingsteken();

}
