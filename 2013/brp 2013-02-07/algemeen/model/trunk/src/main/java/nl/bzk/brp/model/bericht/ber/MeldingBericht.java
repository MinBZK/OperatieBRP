/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.bericht.ber.basis.AbstractMeldingBericht;
import nl.bzk.brp.model.logisch.ber.Melding;


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
public class MeldingBericht extends AbstractMeldingBericht implements Melding {

}
