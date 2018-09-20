/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Set;

import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;

/**
 * Mixin die extra mapping configureert voor {@link BetrokkenheidModel}.
 */
public class RelatieModelMixin {
    @JsonSerialize(using = BetrokkenheidModelJsonSerializer.class)
    @JsonDeserialize(contentAs = BetrokkenheidModel.class)
    private Set<BetrokkenheidModel> betrokkenheden;
}
