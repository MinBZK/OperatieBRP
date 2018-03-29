/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import java.time.LocalDateTime;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;

/**
 * Repository voor het lezen van Protocollering gegevens uit de GBA-V en BRP databases.
 */
public interface ProtocolleringRepository {

    /**
     * Laad de initiele vulling protocollering tabel met de berichten die naar de BRP database
     * gestuurd moeten worden.
     */
    void laadInitVullingTable();

    /**
     * Laad de initiele vulling protocollering tabel met de berichten die naar de BRP database
     * gestuurd moeten worden.
     * @param vanafDatumTijd datum vanaf wanneer protocollering data geladen dient te worden (optioneel)
     */
    void laadInitVullingTable(LocalDateTime vanafDatumTijd);

    /**
     * Zoekt in de initiele vulling tabel naar protocollering data met de aangegeven
     * ConversieResultaat status. Deze regels worden doorgegeven aan de verwerker.
     * @param zoekConversieResultaat de resultaat/status code voor de te selecteren berichten.
     * @param verwerker om de berichten mee te verwerken
     * @param batchGrootte de grootte van te verwerken batches
     * @param protocolleringSize aantal protocolleringen per bericht
     * @return indicatie of er nog mee te verwerken berichten zijn
     */
    boolean verwerk(ConversieResultaat zoekConversieResultaat, BerichtVerwerker<ProtocolleringBericht> verwerker, int batchGrootte, int protocolleringSize);

    /**
     * Update het conversie resultaat van een lijst van activiteiten.
     * @param activiteitIds lijst van activiteit ids
     * @param conversieResultaat conversie resultaat
     */
    void updateStatussen(List<Long> activiteitIds, ConversieResultaat conversieResultaat);
}
