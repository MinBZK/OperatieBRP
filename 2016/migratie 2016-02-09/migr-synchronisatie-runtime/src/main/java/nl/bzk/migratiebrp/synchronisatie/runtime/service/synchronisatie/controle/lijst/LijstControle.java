/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;

/**
 * Controle op basis van een lijst van BRP personen.
 */
public interface LijstControle {

    /**
     * Controleer.
     *
     * @param lijst
     *            lijst
     * @return true, als aan de controle wordt voldaan, anders false
     */
    boolean controleer(List<BrpPersoonslijst> lijst);
}
