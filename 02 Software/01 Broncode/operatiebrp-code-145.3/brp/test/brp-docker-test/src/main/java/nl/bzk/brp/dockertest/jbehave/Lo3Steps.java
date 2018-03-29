/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import javax.jms.JMSException;
import org.jbehave.core.annotations.Then;

public class Lo3Steps {

    /**
     * Controleert of er een bericht op de GBALeveringen queue is gezet.
     */
    @Then("is er een LO3 levering gedaan")
    public void thenIsErEenLO3leveringGedaan() throws JMSException {
        JBehaveState.get().asynchroonBericht().assertLo3Levering();
    }
}
