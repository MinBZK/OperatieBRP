/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import nl.bzk.brp.model.logisch.BRPActie;


/**
 * Factory class voor het instantieren van {@link ActieUitvoerder},
 * instanties zijn op basis van de uit te voeren {@link BRPActie}.
 */
public interface ActieFactory {

    /**
     * Haal uit te voeren actie op.
     * @param actie de actie die uitgevoerd moet worden van de betreffende bericht
     * @return de uit te voeren Actie
     */
    ActieUitvoerder getActieUitvoerder(BRPActie actie);
}
