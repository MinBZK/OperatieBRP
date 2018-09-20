/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

/**
 * Interface voor alle enumeraties.
 */
public interface Enumeratie {
    /**
     * Geeft het id terug van een enumeratie.
     * 
     * @return Het technische database-ID van een enumeratiewaarde.
     */
    int getId();

    /**
     * Geeft de code weer van een enumeratie.
     * 
     * @return de functionele code van een enumeratiewaarde.
     */
    String getCode();

    /**
     * @return true als deze Enumeratie een code heeft, anders false.
     */
    boolean heeftCode();
}
