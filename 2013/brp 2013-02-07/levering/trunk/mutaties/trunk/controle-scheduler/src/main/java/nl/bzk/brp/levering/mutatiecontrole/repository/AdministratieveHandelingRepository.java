/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.repository;

import java.math.BigInteger;
import java.util.List;


/**
 * Interface voor de repository voor toegang tot administratieve handeling data (BRP).
 */
public interface AdministratieveHandelingRepository {

    /**
     * Haalt onverwerkte administratieve handelingen op. Deze administratieve handelingen zijn nog niet verwerkt nav hun
     * mutatie aangezien zij een tijdstempel missen.
     *
     * @return Een lijst van meldingen als die er zijn.
     */
    List<BigInteger> haalOnverwerkteAdministratieveHandelingenOp();

}
