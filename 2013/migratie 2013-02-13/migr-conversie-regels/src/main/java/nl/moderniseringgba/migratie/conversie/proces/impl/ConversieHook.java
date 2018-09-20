/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.impl;

import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;

/**
 * Hook tijdens de conversie service.
 */
public interface ConversieHook {

    /**
     * Registratie *NA* een stap is uitgevoerd.
     * 
     * @param stap
     *            stap
     * @param persoonslijst
     *            persoonslijst
     */
    void stap(ConversieStap stap, Persoonslijst persoonslijst);
}
