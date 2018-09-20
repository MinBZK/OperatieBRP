/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroep;


/**
 * Vorm van historie: alleen formeel. Immers, een persoonskaart is wel-of-niet geconverteerd, en een persoonskaart 'verhuist' niet mee (c.q.: de plaats van
 * een persoonskaart veranderd in principe niet).
 */
public final class PersoonPersoonskaartGroepBericht extends AbstractPersoonPersoonskaartGroepBericht implements
    PersoonPersoonskaartGroep
{

}
