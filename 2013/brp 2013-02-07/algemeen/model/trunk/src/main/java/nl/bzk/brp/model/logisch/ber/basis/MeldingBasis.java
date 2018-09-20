/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.ObjectType;


/**
 * Het optreden van een soort melding naar aanleiding van het controleren van een Regel.
 *
 * Vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP, en het kunnen garanderen van een
 * correcte werking van de BRP, worden inkomende berichten gecontroleerd door bepaalde wetmatigheden te controleren:
 * Regels. Als een Regel iets constateerd, zal dat leiden tot een specifieke soort melding, en zal bekend zijn welk
 * attribuut of welke attributen daarbij het probleem veroorzaken.
 *
 *
 *
 */
public interface MeldingBasis extends ObjectType {

    /**
     * Retourneert Regel van Melding.
     *
     * @return Regel.
     */
    Regel getRegel();

    /**
     * Retourneert Soort van Melding.
     *
     * @return Soort.
     */
    SoortMelding getSoort();

    /**
     * Retourneert Melding van Melding.
     *
     * @return Melding.
     */
    Meldingtekst getMelding();

    /**
     * Retourneert Attribuut van Melding.
     *
     * @return Attribuut.
     */
    Element getAttribuut();

}
