/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.springframework.stereotype.Service;

/**
 * Deze class implementeert de services die geleverd worden voor de conversie, van LO3 rubrieken en voorwaarderegels
 * naar BRP expressies.
 */
@Service
public interface ConverteerNaarExpressieService {

    /**
     * Converteert een lijst van sleutelrubrieken naar een attenderingscriterium.
     * @param autorisatieRubrieken lijst van lo3 rubrieken
     * @param herkomst herkomst
     * @return attenderings criterium
     */
    String converteerSleutelRubrieken(final String autorisatieRubrieken, final Lo3Herkomst herkomst);

    /**
     * Converteert een voorwaarderegel naar een expressie.
     * @param lo3Voorwaarderegel String met de lo3 voorwaarderegel
     * @return expressie
     */
    String converteerVoorwaardeRegel(final String lo3Voorwaarderegel);
}
