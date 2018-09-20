/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess;

import java.math.BigDecimal;

import nl.bzk.brp.poc.berichtenverwerker.model.Pers;


/**
 * Data Access Object voor het vinden, ophalen en aanpassen van {@link Pers
 * Persoon} gebonden data.
 */
public interface PersoonDAO {

    /**
     * Haalt de {@link Pers} op op basis van de opgegeven id.
     *
     * @param id id van de persoon.
     * @return de gevonden persoon.
     */
    Pers vindPersoonOpBasisVanId(long id);

    /**
     * Haalt de persoon die geidentificeerd wordt met de opgegeven {@code bsn} op.
     *
     * @param bsn BSN nummer van de persoon
     * @return de met BSN geidentificeerde persoon
     */
    Pers vindPersoonOpBasisVanBsn(BigDecimal bsn);

}
