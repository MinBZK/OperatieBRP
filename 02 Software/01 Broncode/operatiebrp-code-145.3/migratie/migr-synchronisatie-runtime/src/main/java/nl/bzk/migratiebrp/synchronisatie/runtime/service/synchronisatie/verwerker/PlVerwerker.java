/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker;

import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleUitkomst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Persoonslijst verwerker.
 */
public interface PlVerwerker {

    /**
     * Verwerk een persoonslijst.
     * @param context verwerkingscontext
     * @return antwoord
     */
    ControleUitkomst controle(final VerwerkingsContext context);

}
