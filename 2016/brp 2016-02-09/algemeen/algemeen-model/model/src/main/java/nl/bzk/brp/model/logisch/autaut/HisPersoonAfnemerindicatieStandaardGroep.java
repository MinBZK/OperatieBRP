/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.autaut;

import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.FormeleHistorie;

/**
 * Interface voor voorkomens van Standaard
 *
 */
public interface HisPersoonAfnemerindicatieStandaardGroep extends FormeleHistorie, FormeelVerantwoordbaar<nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst>,
    HisPersoonAfnemerindicatieStandaardGroepBasis
{

}
