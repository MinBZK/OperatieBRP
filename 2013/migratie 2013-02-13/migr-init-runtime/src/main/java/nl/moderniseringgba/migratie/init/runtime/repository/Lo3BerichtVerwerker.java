/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime.repository;

import java.util.concurrent.Callable;

/**
 * Interface voor het verwerken van Lo3Berichten middels een Callable. Lo3Berichten worden toegevoegd aan een batch 
 * middels addLo3Bericht(), en de batch wordt dan verstuurd door call() aan te roepen.
 */
public interface Lo3BerichtVerwerker extends Callable<Void> {

    /**
     * Voeg een Lo3Bericht toe om te verwerken.
     * 
     * @param lo3Bericht
     *            het Lo3 bericht.
     * @param aNummer
     *            het aNummer voor het Lo3 bericht.
     */
    void addLo3Bericht(String lo3Bericht, long aNummer);
}
