/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * De service voor het versturen van LO3-berichten.
 */
public interface InitieleVullingService {

    /**
     * Leest de LO3 synchronisatie berichten uit de GBA-V database in batches van een bepaalde
     * grootte en verstuur deze berichten naar de synchronisatie service.
     * @return indicatie of er nog berichten verwerkt moeten worden
     * @throws ParseException bij fouten
     */
    boolean synchroniseerPersonen() throws ParseException;

    /**
     * Laad de initiele vulling tabel op basis van de spg (gbav) database.
     */
    void laadInitieleVullingTable();

    /**
     * Maak de initiele vulling tabel aan op basis van de excel bestanden.
     * @param excelFolder folder met excel bestanden
     */
    void vulBerichtenTabelExcel(String excelFolder);

    /**
     * Laad de initiele vulling tabel voor de autorisatieregels op basis van de spg (gbav) database.
     */
    void laadInitAutorisatieRegelTabel();

    /**
     * Leest de autorisatie berichten uit de GBA-V database en verstuur deze berichten naar de
     * synchronisatie service.
     * @return indicatie of er nog berichten verwerkt moeten worden
     */
    boolean synchroniseerAutorisaties();

    /**
     * Laad de initiele vulling tabel voor de afnemersindicaties op basis van de spg (gbav)
     * database.
     */
    void laadInitAfnemersIndicatieTabel();

    /**
     * Leest de afnemersindicatie berichten uit de GBA-V database en verstuur deze berichten naar de
     * synchronisatie service.
     * @return indicatie of er nog berichten verwerkt moeten worden
     */
    boolean synchroniseerAfnemerIndicaties();

    /**
     * Laad de initiele vulling tabel voor de protocollering op basis van de spg (gbav) database.
     */
    void laadInitProtocolleringTabel();

    /**
     * Laad de initiele vulling tabel voor de protocollering op basis van de spg (gbav) database.
     * @param vanafDatum datum vanaf wanneer protocollering data geladen dient te worden
     */
    void laadInitProtocolleringTabel(LocalDateTime vanafDatum);

    /**
     * Leest de protocollering berichten uit de GBA-V database en verstuurt deze berichten naar de
     * synchronisatie service.
     * @return indicatie of er nog berichten verwerkt moeten worden
     */
    boolean synchroniseerProtocollering();

    /**
     * Zet het aantal persoon berichten per commit.
     * @param batchPersoon batch size
     */
    void setBatchPersoon(final Integer batchPersoon);

    /**
     * Zet het aantal autorisatie berichten per commit.
     * @param batchAutorisatie batch size
     */
    void setBatchAutorisatie(final Integer batchAutorisatie);

    /**
     * Zet het aantal afnemersindicatie berichten per commit.
     * @param batchAfnemersindicatie batch size
     */
    void setBatchAfnemersindicatie(final Integer batchAfnemersindicatie);

    /**
     * Zet het aantal protocollering berichten per commit.
     * @param batchProtocollering batch size
     */
    void setBatchProtocollering(final Integer batchProtocollering);

    /**
     * Zet het aantal protocolleringen per bericht.
     * @param aantalProtocollering aantal
     */
    void setAantalProtocollering(final Integer aantalProtocollering);
}
