/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service;

import java.text.ParseException;

/**
 *
 * De service voor het versturen van LO3-berichten.
 *
 */
public interface InitieleVullingService {

    /**
     * Leest de LO3 synchronisatie berichten uit de GBA-V database in batches van een bepaalde grootte en verstuur deze
     * berichten naar de synchronisatie service.
     *
     * @return indicatie of er nog berichten verwerkt moeten worden
     * @throws ParseException
     *             bij fouten
     */
    boolean synchroniseerPersonen() throws ParseException;

    /**
     * Laad de initiele vulling tabel op basis van de spg (gbav) database.
     *
     * @throws ParseException
     *             bij fouten
     */
    void laadInitieleVullingTable() throws ParseException;

    /**
     * Maak de initiele vulling tabel aan op basis van de excel bestanden.
     *
     * @param excelFolder
     *            folder met excel bestanden
     */
    void vulBerichtenTabelExcel(String excelFolder);

    /**
     * Laad de initiele vulling tabel voor de autorisatieregels op basis van de spg (gbav) database.
     */
    void laadInitAutorisatieRegelTabel();

    /**
     * Leest de autorisatie berichten uit de GBA-V database en verstuur deze berichten naar de synchronisatie service.
     * 
     * @return indicatie of er nog berichten verwerkt moeten worden
     */
    boolean synchroniseerAutorisaties();

    /**
     * Laad de initiele vulling tabel voor de afnemersindicaties op basis van de spg (gbav) database.
     */
    void laadInitAfnemersIndicatieTabel();

    /**
     * Leest de afnemersindicatie berichten uit de GBA-V database en verstuur deze berichten naar de synchronisatie
     * service.
     * 
     * @return indicatie of er nog berichten verwerkt moeten worden
     */
    boolean synchroniseerAfnemerIndicaties();

    /**
     * Zet de batchsize.
     * 
     * @param batchSize
     *            batchsize
     */
    void setBatchSize(Integer batchSize);
}
