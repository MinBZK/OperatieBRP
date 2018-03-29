/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Persoonslijst controle.
 */
public interface PlControle {

    /**
     * Controleer.
     * @param context verwerkingscontext
     * @param dbPersoonslijst de gevonden persoonslijst in de database
     * @return true, als de controle slaagt
     */
    boolean controleer(VerwerkingsContext context, BrpPersoonslijst dbPersoonslijst);
}
