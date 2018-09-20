/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesTelling;

/**
 * De Proces tellingen Repository.
 */
public interface ProcesTellingenRepository {

    /**
     * Slaat het meegegeven tellingProces op de database.
     * 
     * @param tellingProces
     *            de tellingProces entiteit die moet worden opgeslagen in de database
     * @return de tellingProces entiteit die opgeslagen is in de database
     */
    ProcesTelling save(ProcesTelling tellingProces);

    /**
     * Haalt de telling voor de meegegeven procesnaam op.
     * 
     * @param procesnaam
     *            De procesnaam waarover de telling gaat.
     * @param kanaal
     *            Het kanaal waarover de telling gaat.
     * @param berichtType
     *            Het kanaal waarover de telling gaat.
     * @param datum
     *            De datum waarover de telling gaat.
     * 
     * @return De telling voor de meegegeven procesnaam en datum.
     * 
     */
    ProcesTelling haalProcesTellingOp(String procesnaam, String kanaal, String berichtType, Timestamp datum);
}
