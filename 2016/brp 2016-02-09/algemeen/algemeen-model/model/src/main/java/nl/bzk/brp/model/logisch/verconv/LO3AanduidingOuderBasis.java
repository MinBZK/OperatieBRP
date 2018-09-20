/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.Ouder;

/**
 * Aanduiding welke BRP Ouder Betrokkenheid als Ouder 1/2 in LO3 berichten geleverd wordt.
 *
 * Deze mapping zorgt voor een consistente opbouw van LO3 berichten. De inhoud van deze tabel wordt bepaald op basis van
 * een GBA bijhouding of tijdens het (eerste keer) leveren van een LO3 Bericht.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface LO3AanduidingOuderBasis extends BrpObject {

    /**
     * Retourneert Ouder van LO3 Aanduiding Ouder.
     *
     * @return Ouder.
     */
    Ouder getOuder();

    /**
     * Retourneert Soort van LO3 Aanduiding Ouder.
     *
     * @return Soort.
     */
    LO3SoortAanduidingOuderAttribuut getSoort();

}
