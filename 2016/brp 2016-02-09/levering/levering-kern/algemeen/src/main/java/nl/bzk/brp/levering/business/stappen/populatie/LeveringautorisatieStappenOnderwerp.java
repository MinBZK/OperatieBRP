/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.populatie;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.basis.BrpObject;


/**
 * Het onderwerp voor de leveringautorisatie-stappen.
 */
public interface LeveringautorisatieStappenOnderwerp extends BrpObject {


    /**
     * Geeft een leveringsautorisatie cache element.
     *
     * @return Het leveringsautorisatie cache element.
     */
    Leveringinformatie getLeveringinformatie();

    /**
     * Geeft de administratieve handeling ID terug.
     *
     * @return de ID
     */
    Long getAdministratieveHandelingId();

    /**
     * @return
     */
    Stelsel getStelsel();

}
