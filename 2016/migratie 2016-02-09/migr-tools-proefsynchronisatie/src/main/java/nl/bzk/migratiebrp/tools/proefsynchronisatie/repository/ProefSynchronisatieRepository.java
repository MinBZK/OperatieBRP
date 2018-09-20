/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.repository;

import java.util.List;

import nl.bzk.migratiebrp.tools.proefsynchronisatie.domein.ProefSynchronisatieBericht;
import nl.bzk.migratiebrp.tools.proefsynchronisatie.verwerker.BerichtVerwerker;

/**
 * Repository voor het lezen van LO3-berichten uit de GBA-V database.
 */
public interface ProefSynchronisatieRepository {

    /**
     * Laad vulling proefsynchronisatie tabel met berichten die naar ISC gestuurd moeten worden.
     *
     * @param datumVanaf
     *            De datum vanaf wanneer berichten worden meegenomen.
     * @param datumTot
     *            De datum tot wanneer berichten worden meegenomen.
     */
    void laadInitProefSynchronisatieBerichtenTabel(String datumVanaf, String datumTot);

    /**
     * Verwerkt de proefsynchronisatieberichten die in de proefsychronisatiebericht tabel staan.
     *
     * @param verwerker
     *            om de berichten mee te verwerken.
     * @param batchGrootte
     *            de grootte van te verwerken batches
     * @param wachtPeriode
     *            de tijd die er gewacht wordt voordat er een nieuwe batch wordt verwerkt.
     */
    void verwerkProefSynchronisatieBericht(final BerichtVerwerker<ProefSynchronisatieBericht> verwerker, final int batchGrootte, final long wachtPeriode);

    /**
     * Update de status van de verwerkte proefsynchronisatieberichten.
     *
     * @param proefSynchronisatieBerichtIds
     *            De ids van de verwerkte proefsynchronisatieberichten.
     * @return True indien alle berichten zijn geupdated, false in andere gevallen.
     */
    boolean updateProefSynchronisatieBerichtStatus(List<Long> proefSynchronisatieBerichtIds);

}
