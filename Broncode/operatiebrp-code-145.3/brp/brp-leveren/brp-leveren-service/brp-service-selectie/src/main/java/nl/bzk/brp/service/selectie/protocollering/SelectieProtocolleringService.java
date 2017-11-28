/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.protocollering;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;

/**
 * Interface voor het aansturen van de selectie protocollering routine.
 */
public interface SelectieProtocolleringService {

    /**
     * Start de protocolleer selectie routine in nog niet gestart.
     */
    void start();

    /**
     * Stopt de protocolleer selectie routine.
     * Wacht niet tot daadwerkelijk gestopt.
     */
    void stop();

    /**
     * @return indicatie of de protocolleer selectie routine nog loopt.
     */
    boolean isRunning();

    /**
     * Pauzeert de verwerking van een gegeven taak.
     * @param selectietaak id van de {@link Selectietaak}
     */
    void pauzeerVerwerking(int selectietaak);
}
