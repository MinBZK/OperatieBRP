/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;


/**
 * De wijze waarop een Persoon betrokken is bij een Relatie.
 *
 * Er wordt expliciet onderscheid gemaakt tussen de Relatie enerzijds, en de Persoon die in de Relatie betrokken is
 * anderzijds. De koppeling van een Persoon en een Relatie gebeurt via Betrokkenheid. Zie ook overkoepelend verhaal.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public interface BetrokkenheidBasis extends ObjectType {

    /**
     * Retourneert Relatie van Betrokkenheid.
     *
     * @return Relatie.
     */
    Relatie getRelatie();

    /**
     * Retourneert Rol van Betrokkenheid.
     *
     * @return Rol.
     */
    SoortBetrokkenheid getRol();

    /**
     * Retourneert Persoon van Betrokkenheid.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

}
