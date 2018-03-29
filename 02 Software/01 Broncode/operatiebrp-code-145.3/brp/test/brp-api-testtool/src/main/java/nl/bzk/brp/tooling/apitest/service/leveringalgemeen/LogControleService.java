/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * Interface voor het controleren van waarden in de log
 */
public interface LogControleService extends Stateful {

    /**
     * Controleert of er een logevent plaatst heeft gevonden met de gegeven regelCode
     *
     * @param code een regelCode
     * @return indicatie of het logevent plaatsgevonden heeft
     */
    boolean heeftLogEventOntvangenMetCode(String code);

    /**
     * Controleert of er een logevent plaatst heeft gevonden met de gegeven message text (regex).
     *
     * @param messageTextRegex de message text regex
     * @return indicatie of het logevent plaatsgevonden heeft
     */
    boolean heeftLogEventOntvangenMetMessage(String logLevel, String messageTextRegex);
}
