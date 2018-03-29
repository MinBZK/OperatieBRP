/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;

/**
 * Zoek persoon op basis van zoekcriteria.
 */
public interface AdHocZoekVerzoekBericht {

    /**
     * Geeft de partijCode op het bericht terug.
     * @return De partijCode op het bericht.
     */
    String getPartijCode();

    /**
     * Geeft de gevraagde rubrieken op het bericht terug.
     * @return De gevraagde rubrieken op het bericht.
     */
    List<String> getGevraagdeRubrieken();

    /**
     * Geeft de soort dienst op het bericht terug.
     * @return De soort dienst op het bericht.
     */
    SoortDienstType getSoortDienst();

}
