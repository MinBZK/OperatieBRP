/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import java.util.List;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.service.bericht.SyncNaarBrpBericht;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;

/**
 * Repository voor het lezen van LO3-berichten uit de GBA-V database.
 */
public interface PersoonRepository {

    /**
     * Zoekt in de GBA-V database naar LO3-berichten met de aangegeven ConversieResultaat status.
     * Deze berichten worden doorgegeven aan de verwerker. Indien succesvol verwerkt dan wordt de
     * status van het bericht in GBA-V bijgewerkt naar de verwerkConversieResultaat.
     * @param zoekConversieResultaat de resultaat/status code voor de te selecteren berichten.
     * @param verwerker om de berichten mee te verwerken.
     * @param batchGrootte de grootte van te verwerken batches
     * @return Indicatie of er nog mee te verwerken berichten zijn
     */
    boolean verwerkLo3Berichten(ConversieResultaat zoekConversieResultaat, BerichtVerwerker<SyncNaarBrpBericht> verwerker, int batchGrootte);

    /**
     * Laad de initiele vulling tabel met de berichten die naar de BRP database gestuurd moeten
     * worden.
     */
    void laadInitVullingTable();

    /**
     * Sla een lg01 bericht op in de init vulling tabel. Niet alle kolommen worden hierbij gevuld.
     * @param lg01 Het bericht in Lg01 formaat.
     * @param aNummer Het A-Nummer van de persoonslijst.
     * @param gemeenteVanInschrijving De gemeente van inschrijving van de persoonslijst. Mag Null zijn.
     * @param conversieResultaat De resultaat/status code om bij het bericht op te slaan.
     */
    void saveLg01(String lg01, String aNummer, String gemeenteVanInschrijving, ConversieResultaat conversieResultaat);

    /**
     * Maak de initiele vulling tabel aan (zonder vulling) als deze nog niet bestaat.
     */
    void createInitVullingTable();

    /**
     * Update de status/resultaat van de conversie voor een groep A-Nummers.
     * @param aNummers De A-Nummers
     * @param conversieResultaat De nieuwe status/resultaat waarde
     */
    void updateLo3BerichtStatus(List<String> aNummers, ConversieResultaat conversieResultaat);
}
