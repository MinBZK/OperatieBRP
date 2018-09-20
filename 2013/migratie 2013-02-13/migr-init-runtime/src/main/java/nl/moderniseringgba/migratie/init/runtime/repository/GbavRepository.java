/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import nl.moderniseringgba.migratie.init.runtime.domein.ConversieResultaat;

/**
 * Repository voor het lezen van LO3-berichten uit de GBA-V database.
 */
public interface GbavRepository {

    /**
     * Zoekt in de GBA-V database naar LO3-berichten met de aangegeven ConversieResultaat status.
     * Deze berichten worden doorgegeven aan de verwerker.
     * Indien succesvol verwerkt dan wordt de status van het bericht in GBA-V bijgewerkt naar
     * de verwerkConversieResultaat.
     * 
     * @param zoekConversieResultaat
     *            de resultaat/status code voor de te selecteren berichten.
     * @param config
     *            configuratie properties.
     * @param verwerker
     *            om de berichten mee te verwerken.
     * @param batchGrootte
     *            de grootte van te verwerken batches
     * @throws ParseException
     *             als het parsen van de data uit het properties bestand mislukt is.
     */
    void verwerkLo3Berichten(
            ConversieResultaat zoekConversieResultaat,
            Properties config,
            Lo3BerichtVerwerker verwerker,
            int batchGrootte) throws ParseException;

    /**
     * Maakt als de tabel nog niet bestaat een initiele vulling tabel aan met daarin de berichten die naar de BRP
     * database gestuurd moeten worden.
     */
    void createAndFillInitVullingTable();

    /**
     * Sla een lg01 bericht op in de init vulling tabel. Niet alle kolommen worden hierbij gevuld.
     * 
     * @param lg01
     *            Het bericht in Lg01 formaat.
     * @param aNummer
     *            Het A-Nummer van de persoonslijst.
     * @param gemeenteVanInschrijving
     *            De gemeente van inschrijving van de persoonslijst. Mag Null zijn.
     * @param conversieResultaat
     *            De resultaat/status code om bij het bericht op te slaan.
     */
    void saveLg01(String lg01, Long aNummer, Integer gemeenteVanInschrijving, ConversieResultaat conversieResultaat);

    /**
     * Maak de initiele vulling tabel aan (zonder vulling) als deze nog niet bestaat.
     */
    void createInitVullingTable();

    /**
     * Update de status/resultaat van de conversie voor een groep A-Nummers.
     *
     * @param aNummers
     *            De A-Nummers
     * @param conversieResultaat
     *            De nieuwe status/resultaat waarde
     */
    void updateLo3BerichtStatus(List<Long> aNummers, ConversieResultaat conversieResultaat);
}
