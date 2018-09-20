/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;


/**
 * Vorm van historie: beiden.
 * Motivatie: net als bijvoorbeeld de Samengestelde naam kan een individuele geslachtsnaamcomponent (bijv. die met
 * volgnummer 1 voor persoon X) in de loop van de tijd veranderen, dus nog los van eventuele registratiefouten.
 * Er is dus ��k sprake van materi�le historie.
 * RvdP 17 jan 2012.
 *
 *
 *
 */
public class PersoonGeslachtsnaamcomponentStandaardGroepBericht extends
        AbstractPersoonGeslachtsnaamcomponentStandaardGroepBericht implements
        PersoonGeslachtsnaamcomponentStandaardGroep
{

}
