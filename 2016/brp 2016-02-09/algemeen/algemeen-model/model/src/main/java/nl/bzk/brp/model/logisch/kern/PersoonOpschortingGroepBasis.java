/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschortingAttribuut;
import nl.bzk.brp.model.basis.Groep;


/**
 * 1. Vorm van historie: een reden opschorting wordt normaliter met terugwerkende kracht vastgelegd. Conform bijhoudingsverantwoordelijkheid heeft de
 * materi�le tijdslijn (dus) betekenis. In tegenstelling tot bijhoudingsverantwoordelijkheid is de business case voor het expliciet vastleggen van de datum
 * ingang van een reden opschorting wat minder hard: wellicht zou de BRP ook toe kunnen met alleen de formele tijdslijn. Echter, er zijn allerlei regels
 * rondom datum ingang van opschorting, zoals rondom overlijden, �n dit gegeven wordt al van oudsher vastgelegd in LO 3.x. Om die reden is het gegeven
 * gehandhaaft.
 */
public interface PersoonOpschortingGroepBasis extends Groep {

    /**
     * Retourneert Reden opschorting bijhouding van Opschorting.
     *
     * @return Reden opschorting bijhouding.
     */
    RedenOpschortingAttribuut getRedenOpschortingBijhouding();

}
