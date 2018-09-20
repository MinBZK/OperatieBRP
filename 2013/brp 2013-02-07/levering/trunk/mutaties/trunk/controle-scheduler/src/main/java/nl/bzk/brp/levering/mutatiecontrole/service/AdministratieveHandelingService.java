/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatiecontrole.service;

/**
 * De Interface AdministratieveHandelingService, dit bevat de methoden die betrekking hebben op de Administratieve
 * Handelingen.
 */
public interface AdministratieveHandelingService {

    /**
     * Ruimt onverwerkte administratieve handelingen op, door hun ID's op een JMS Queue te plaatsen. Vervolgens worden
     * deze Administratieve Handelingen in kwestie verwerkt.
     */
    void plaatsOnverwerkteAdministratieveHandelingenOpQueue();
}
