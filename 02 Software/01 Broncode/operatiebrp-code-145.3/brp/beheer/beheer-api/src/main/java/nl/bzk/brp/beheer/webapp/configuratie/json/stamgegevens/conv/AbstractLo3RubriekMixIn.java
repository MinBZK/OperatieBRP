/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Lo3Rubriek Mix-in.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public abstract class AbstractLo3RubriekMixIn {

    /** @return naam */
    @JsonProperty("id")
    abstract Integer getId();

    /** @return naam */
    @JsonProperty("naam")
    abstract String getNaam();
}
