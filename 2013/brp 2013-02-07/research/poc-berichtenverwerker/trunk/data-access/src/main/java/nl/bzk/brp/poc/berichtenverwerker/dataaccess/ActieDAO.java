/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess;

import nl.bzk.brp.poc.berichtenverwerker.model.Actie;


/**
 * Data Access Object voor het vinden, ophalen en opslaan van {@link Actie} instanties.
 */
public interface ActieDAO {

    /**
     * Haalt de {@link Actie} op op basis van de opgegeven id.
     *
     * @param id id van de actie.
     * @return de gevonden actie.
     */
    Actie vindActieOpBasisVanId(long id);

    /**
     * Voegt de (nieuwe) opgegeven {@link Actie} toe.
     *
     * @param actie de actie die moet worden opgeslagen.
     */
    void voegToeActie(Actie actie);

}
