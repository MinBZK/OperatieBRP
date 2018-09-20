/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.serialization;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;

/**
 * Mapping voor de {@link nl.bzk.brp.model.objecttype.operationeel.PersoonModel} class.
 */
@JsonIgnoreProperties({"handler"})
public interface PersoonModelMixin extends GebruikAttributenMixin {

    @JsonIgnore
    public BetrokkenheidModel getKindBetrokkenHeid();

    @JsonIgnore public Set<BetrokkenheidModel> getKindBetrokkenheden();

    @JsonIgnore public Set<BetrokkenheidModel> getOuderBetrokkenheden();

    @JsonIgnore public Set<BetrokkenheidModel> getPartnerBetrokkenHeden();

    @JsonIgnore public Boolean isOverleden();
}
