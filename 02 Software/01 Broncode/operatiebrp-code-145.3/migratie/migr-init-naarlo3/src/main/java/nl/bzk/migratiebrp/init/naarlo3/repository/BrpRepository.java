/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3.repository;

/**
 * De repository die het mogelijk maakt om te querien in de BRP database.
 */
public interface BrpRepository {

    /**
     * Zoekt in de BRP naar ingeschreven personen en geef elk gevonden A-Nummer door aan de verwerker.
     * @param verwerker de verwerker voor de A-Nummers
     * @param batchGrootte de grootte van te verwerken batches
     */
    void verwerkANummersVanIngeschrevenPersonen(ANummerVerwerker verwerker, int batchGrootte);
}
