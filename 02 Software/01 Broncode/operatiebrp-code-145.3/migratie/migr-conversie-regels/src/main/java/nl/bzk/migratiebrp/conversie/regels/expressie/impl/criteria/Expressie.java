/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria;

import java.util.List;

/**
 * Expressie interface.
 */
public interface Expressie {

    /**
     * Geef de voorwaarde of zoek opdracht als brp expressie.
     * @return brp expressie
     */
    String getBrpExpressie();

    /**
     * Geef de voorwaarde of zoekopdracht als een lijst met criteria terug.
     * @return lijst met criteria
     */
    List<Criterium> getCriteria();
}
