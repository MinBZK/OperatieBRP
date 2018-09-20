/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.acties;

import java.util.List;

import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;


/**
 * Interface voor de uitvoering van een actie.
 */
public interface ActieUitvoerder {

    /**
     * Voert de actie uit.
     * @param actie de actie die uitgevoerd dient te worden.
     * @param huidigeSituatie De huidige situatie zoals in de BRP
     */
    void voerUit(final BRPActie actie, final List<RootObject> huidigeSituatie);

}
