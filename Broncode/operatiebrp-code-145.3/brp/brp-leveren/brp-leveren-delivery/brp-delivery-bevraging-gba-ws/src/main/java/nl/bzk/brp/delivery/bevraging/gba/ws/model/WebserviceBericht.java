/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Gezamenlijk type voor webservice berichten.
 */
public interface WebserviceBericht {
    /**
     * Retourneert een lijst van zoekrubrieken.
     * @return een lijst van zoekrubrieken
     */
    List<String> getZoekRubrieken();

    /**
     * Retourneert een lijst van gevraagde rubrieken.
     * @return een lijst van gevraagde rubrieken
     */
    List<String> getGevraagdeRubrieken();

    /**
     * Retourneert een lijst van zoek criteria.
     * @return een lijst van zoek criteria
     */
    List<Lo3CategorieWaarde> getZoekCriteria();

    /**
     * Retourneert de soort dienst.
     * @return soort dienst
     */
    SoortDienst getSoortDienst();
}
