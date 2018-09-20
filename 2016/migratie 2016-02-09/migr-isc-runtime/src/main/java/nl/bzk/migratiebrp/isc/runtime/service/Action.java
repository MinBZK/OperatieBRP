/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.service;

import nl.bzk.migratiebrp.isc.runtime.message.Message;

/**
 * Actie.
 */
public interface Action {

    /**
     * Zet kanaal.
     *
     * @param kanaal
     *            kanaal
     */
    void setKanaal(String kanaal);

    /**
     * Verwerk.
     *
     * @param message
     *            message
     * @return false, als de verwerking van acties gestopt moet worden, anders true
     */
    boolean verwerk(Message message);

}
