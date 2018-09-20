/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;

/**
 * TODO: Add documentation
 */
public abstract class PlatteSerializatieMixin {

    @JsonSerialize(using = PlatBetrokkenheidSerializer.class)
    @JsonDeserialize(contentAs = BetrokkenheidModel.class)
    private Set<BetrokkenheidModel> betrokkenheden;
}
