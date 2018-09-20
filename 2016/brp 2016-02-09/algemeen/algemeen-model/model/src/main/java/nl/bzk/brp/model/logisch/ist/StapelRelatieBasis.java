/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * Het relateren van een stapel aan een relatie.
 *
 * Een stapel heeft normaliter betrekking op één relatie. Uitzonderingen hierop zijn omzettingen van huwelijk in een
 * geregistreerd partnerschap (die leiden tot twee relaties) en stapels met alleen onjuiste voorkomens (die leiden tot
 * géén relaties). Omdat één stapel altijd behoort tot (de persoonslijst van) één persoon, kunnen meerdere stapels
 * behoren tot één relatie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface StapelRelatieBasis extends BrpObject {

    /**
     * Retourneert Stapel van Stapel \ Relatie.
     *
     * @return Stapel.
     */
    Stapel getStapel();

    /**
     * Retourneert Relatie van Stapel \ Relatie.
     *
     * @return Relatie.
     */
    Relatie getRelatie();

}
