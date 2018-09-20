/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import java.util.List;

/**
 * Repository die met bestanden kan omgaan.
 */
public interface BestandRepository {

    /**
     * Schrijft de regels weg.
     * @param name de naam van het bestand waarheen de regels gaan
     * @param regels de regels om weg te schrijven
     */
    void schrijfRegels(String name, List<String> regels);
}
