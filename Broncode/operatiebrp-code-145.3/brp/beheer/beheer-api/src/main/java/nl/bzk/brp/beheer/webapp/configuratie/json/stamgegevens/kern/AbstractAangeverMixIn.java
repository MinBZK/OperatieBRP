/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamOmschrijvingEnumMixIn;

/**
 * Mix in voor {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument}.
 */
public abstract class AbstractAangeverMixIn extends AbstractIdNaamOmschrijvingEnumMixIn {

    /** @return code. */
    @JsonProperty("code")
    @JsonSerialize(using = ToStringSerializer.class)
    abstract char getCode();
}
