/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.interfaces.gen;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.BetrokkenheidOuderlijkGezagGroep;
import nl.bzk.brp.model.groep.interfaces.usr.BetrokkenheidOuderschapGroep;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import nl.bzk.brp.model.objecttype.interfaces.usr.Relatie;
import nl.bzk.brp.model.objecttype.statisch.SoortBetrokkenheid;

/**
 * Interface voor objecttype betrokkenheid.
 */
public interface BetrokkenheidBasis extends ObjectType {

    /**
     * Retourneert de rol van de betrokkene.
     * @return Soort betrokkenheid.
     */
    SoortBetrokkenheid getRol();

    /**
     * Retourneert de relatie waartoe deze betrokkenheid hoort.
     * @return Relatie.
     */
    Relatie getRelatie();

    /**
     * Retourneert de betrokken persoon.
     * @return Persoon.
     */
    Persoon getBetrokkene();

    /**
     * Retourneert ouderlijk gezag groep.
     * @return Ouderlijk gezag groep.
     */
    BetrokkenheidOuderlijkGezagGroep getBetrokkenheidOuderlijkGezag();

    /**
     * Retourneert ouderschap groep.
     * @return Ouderschap groep.
     */
    BetrokkenheidOuderschapGroep getBetrokkenheidOuderschap();

}
