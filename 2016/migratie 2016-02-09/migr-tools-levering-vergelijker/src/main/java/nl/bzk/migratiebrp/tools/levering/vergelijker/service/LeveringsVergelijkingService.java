/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.service;

import java.util.List;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatInhoud;

/**
 * Interface voor het inhoudelijk controleren van twee leveringberichten.
 */
public interface LeveringsVergelijkingService {

    /**
     * Controleert of de meegegeven berichten inhoudelijk gelijk zijn aan elkaar. Indien de berichten inhoudelijk niet
     * gelijk zijn aan elkaar, wordt er per categorie aangegeven wat de verschillen zijn. Zijn beide berichten
     * inhoudelijk gelijk aan elkaar, dan wordt er een lege lijst teruggegeven.
     *
     * @param gbaBericht
     *            Het leveringbericht uit GBA-V.
     * @param brpBericht
     *            Het corresponderende leveringbericht uit BRP.
     * @return Lijst met daarin vergelijkingresultaten met per categorie de gevonden afwijkingen.
     */
    List<LeveringsVergelijkingResultaatInhoud> vergelijkInhoudLeveringsBerichten(String gbaBericht, String brpBericht);

    /**
     * Controleert of de koppen van de meegegeven berichten inhoudelijk gelijk zijn aan elkaar. Indien de koppen
     * inhoudelijk niet gelijk zijn aan elkaar, wordt er per header aangegeven wat de verschillen zijn. Zijn beide
     * berichten inhoudelijk gelijk aan elkaar, dan wordt er een lege lijst teruggegeven.
     *
     * @param gbaBericht
     *            Het leveringbericht uit GBA-V.
     * @param brpBericht
     *            Het corresponderende leveringbericht uit BRP.
     * @return Komma gescheiden lijst van de gevonden afwijkende koppen.
     */
    String vergelijkKopLeveringsBerichten(String gbaBericht, String brpBericht);

}
