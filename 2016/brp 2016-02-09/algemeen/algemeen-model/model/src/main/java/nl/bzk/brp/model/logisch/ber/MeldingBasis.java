/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Het optreden van een soort melding naar aanleiding van het controleren van een Regel.
 *
 * Vanuit het oogpunt van het bewaken van de kwaliteit van de gegevens in de BRP, en het kunnen garanderen van een
 * correcte werking van de BRP, worden inkomende berichten gecontroleerd door bepaalde wetmatigheden te controleren:
 * Regels. Als een Regel iets constateert, zal dat leiden tot een specifieke soort melding.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface MeldingBasis extends BrpObject {

    /**
     * Retourneert Regel van Melding.
     *
     * @return Regel.
     */
    RegelAttribuut getRegel();

    /**
     * Retourneert Soort van Melding.
     *
     * @return Soort.
     */
    SoortMeldingAttribuut getSoort();

    /**
     * Retourneert Melding van Melding.
     *
     * @return Melding.
     */
    MeldingtekstAttribuut getMelding();

}
