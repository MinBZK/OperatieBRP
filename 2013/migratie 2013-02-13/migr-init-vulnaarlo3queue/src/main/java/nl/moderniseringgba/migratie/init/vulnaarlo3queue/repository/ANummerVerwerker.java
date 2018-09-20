/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.vulnaarlo3queue.repository;

import java.util.concurrent.Callable;

/**
 * Interface voor het verwerken van A-Nummers middels een Callable. A-nummers worden toegevoegd aan een batch middels
 * addANummer(), en de batch wordt dan verstuurd door call() aan te roepen.
 */
public interface ANummerVerwerker extends Callable<Void> {

    /**
     * Voeg een A-Nummer toe om te verwerken.
     * 
     * @param aNummer
     *            het aNummer
     */
    void addANummer(long aNummer);
}
