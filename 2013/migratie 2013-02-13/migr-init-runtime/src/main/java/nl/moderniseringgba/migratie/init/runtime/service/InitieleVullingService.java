/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime.service;

import java.text.ParseException;
import java.util.Properties;

/**
 * 
 * De service voor het versturen van LO3-berichten.
 * 
 */
public interface InitieleVullingService {

    /**
     * Leest de LO3 berichten uit de GBA-V database in batches van een bepaalde grootte en verstuur deze berichten naar
     * de synchronisatie service.
     * 
     * @param config
     *            Properties bestand met data voor query.
     * @throws ParseException
     *             Gooit een parseException als een datum oid niet geparsed kan worden.
     */
    void leesLo3BerichtenEnVerstuur(Properties config) throws ParseException;

    /**
     * Maakt de initiele vulling tabel aan op basis van de spg (gbav) database.
     * 
     * @param config
     *            Properties bestand met data voor query.
     * @throws ParseException
     *             Gooit een parseException als een datum oid niet geparsed kan worden.
     */
    void createInitieleVullingTable(Properties config) throws ParseException;

    /**
     * Maak de initiele vulling tabel aan op basis van de excel bestanden.
     * 
     * @param config
     *            Properties bestand met data voor query.
     * @param excelFolder
     *            De locatie van de excel bestanden.
     */
    void vulBerichtenTabelExcel(Properties config, String excelFolder);
}
