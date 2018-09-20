/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.repository.historie;

import nl.bzk.copy.dataaccess.repository.historie.GroepHistorieRepository;
import nl.bzk.copy.model.groep.operationeel.AbstractBetrokkenheidOuderlijkGezagGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.BetrokkenheidOuderlijkGezagHisModel;
import nl.bzk.copy.model.objecttype.operationeel.BetrokkenheidModel;


/**
 * Repository voor betrokkenheid ouderlijk gezag.
 */
public interface BetrokkenheidOuderlijkgezagHistorieRepository extends
    GroepHistorieRepository<BetrokkenheidModel, AbstractBetrokkenheidOuderlijkGezagGroep,
    BetrokkenheidOuderlijkGezagHisModel>
{

}
