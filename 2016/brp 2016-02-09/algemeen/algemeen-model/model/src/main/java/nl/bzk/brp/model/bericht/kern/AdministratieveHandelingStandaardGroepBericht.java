/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingStandaardGroep;

/**
 * Deze groep is opgenomen om de BijhoudingsPlan onderop te kunnen sorteren. Omdat attributen per groep gesorteerd zijn,
 * was dit niet mogelijk als het attribuut in de Identiteitsgroep zat.
 *
 *
 *
 */
public class AdministratieveHandelingStandaardGroepBericht extends AbstractAdministratieveHandelingStandaardGroepBericht implements Groep,
        AdministratieveHandelingStandaardGroep, MetaIdentificeerbaar
{

}
