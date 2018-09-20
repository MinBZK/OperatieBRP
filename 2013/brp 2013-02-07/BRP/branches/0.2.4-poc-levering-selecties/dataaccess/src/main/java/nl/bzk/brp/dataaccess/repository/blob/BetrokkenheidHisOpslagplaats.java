/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.blob;

import java.util.List;

import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.pojo.BetrokkenheidHisModel;

/**
 */
public interface BetrokkenheidHisOpslagplaats {

    /**
     * Lees de historie van een betrokkenheid.
     *
     * @param betrokkenheid
     * @return
     */
    BetrokkenheidHisModel leesHistorie(BetrokkenheidModel betrokkenheid);

    /**
     * Lees de historie van betrokkenheden van een Persoon.
     *
     * @param persoon
     * @return
     */
    List<BetrokkenheidHisModel> leesHistorie(PersoonModel persoon);
}
