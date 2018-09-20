/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * Basis interface voor alle bedrijfsregels. Legt de link met de Regel enum instantie die de
 * bedrijfsregel implementeert.
 */
public interface RegelInterface {

    /**
     * Geeft de bijbehorende regel enum instantie terug.
     *
     * @return de bijbehorende regel enum instantie
     */
    Regel getRegel();

}
