/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.jms;

import java.math.BigInteger;
import java.util.List;


/**
 * De Interface AdministratieveHandelingVerwerker, deze zorgt voor JMS activiteiten mbt administratieve handelingen.
 */
public interface AdministratieveHandelingVerwerker {

    /**
     * Plaats administratieve handelingen op JMS queue.
     *
     * @param onverwerkteAdministratieveHandelingen de onverwerkte administratieve handelingen
     */
    void plaatsAdministratieveHandelingenOpQueue(List<BigInteger> onverwerkteAdministratieveHandelingen);
}
