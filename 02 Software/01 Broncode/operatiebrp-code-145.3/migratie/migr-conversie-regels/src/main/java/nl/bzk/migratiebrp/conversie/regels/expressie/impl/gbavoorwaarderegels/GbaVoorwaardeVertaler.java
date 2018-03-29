/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;

/**
 * interface voor vertalen van GBA voorwaarde.
 */
@FunctionalInterface
public interface GbaVoorwaardeVertaler {

    /**
     * Verwerk de voorwaarde.
     * @return vertaalde expressie
     * @throws GbaVoorwaardeOnvertaalbaarExceptie in voorwaarde niet te vertalen is
     */
    Expressie verwerk() throws GbaVoorwaardeOnvertaalbaarExceptie;


}
