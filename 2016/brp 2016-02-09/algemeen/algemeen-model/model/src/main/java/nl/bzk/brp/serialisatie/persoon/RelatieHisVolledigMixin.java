/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Set;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;


/**
 * Mixin die extra mapping configureert voor {@link nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl} .
 */
@SuppressWarnings("unused")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_ARRAY, use = JsonTypeInfo.Id.CLASS)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iD", scope = RelatieHisVolledigImpl.class)
public final class RelatieHisVolledigMixin {

    @JsonProperty
    private Set<BetrokkenheidHisVolledigImpl> betrokkenheden;

}
