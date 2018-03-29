/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository;

import java.util.List;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatKop;

/**
 * DAO interface voor leveringvergelijking resultaten.
 */
public interface LeveringsVergelijkingResultaatKopRepository {

    /**
     * Sla het resultaat van de leveringvergelijking op.
     * @param leveringsVergelijkingResultaatKop Het op te slaan vergelijkingResultaat.
     * @return Het gepersisteerde vergelijkingresultaat.
     */
    LeveringsVergelijkingResultaatKop opslaanLeveringsVergelijkingKopResultaat(LeveringsVergelijkingResultaatKop leveringsVergelijkingResultaatKop);

    /**
     * Sla de resultaten van de leveringvergelijkingen op.
     * @param leveringsVergelijkingResultatenKop De op te slaan vergelijkingResultaten.
     * @return De gepersisteerde vergelijkingresultaten.
     */
    List<LeveringsVergelijkingResultaatKop> opslaanLeveringsVergelijkingKopResultaten(
            List<LeveringsVergelijkingResultaatKop> leveringsVergelijkingResultatenKop);

}
