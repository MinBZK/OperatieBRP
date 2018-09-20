/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.autorisatie;

import java.util.Collection;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;

/**
 * Interface class voor alle operaties die nodig zijn voor autorisaties voor de DAL.
 */
public interface AutorisatieService {
    /**
     * Geef de lijst met alle actieve gba autorisaties.
     *
     * @return De lijst met alle actieve gba autorisaties.
     */

    Collection<Leveringsautorisatie> geefAlleGbaAutorisaties();

    /**
     * Slaat een autorisatie (partij, toegang leveringsautorisatie, leveringsautorisaties, dienstbundels en diensten) op
     * in de BRP database.
     *
     * @param brpAutorisatie
     *            De autorisatie in het BRP-migratiemodelformaat.
     * @return de opgeslagen abonnementen
     */
    List<ToegangLeveringsAutorisatie> persisteerAutorisatie(final BrpAutorisatie brpAutorisatie);

    /**
     * Vraag een autorisatie op uit de BRP database voor de gegeven partijCode, naam en ingangsDatumRegel, deze moet
     * bestaan anders treedt er een fout op.
     *
     * @param partijCode
     *            de partij waarvoor we de autorisatie opzoeken
     * @param naam
     *            de naam van het autorisatiebesluit
     * @param ingangsDatumRegel
     *            de ingangsdatum van de autorisatietabelregel
     * @return De autorisatie in het BRP-migratiemodelformaat.
     * @throws IllegalStateException
     *             als de autorisatie niet gevonden kan worden
     */
    BrpAutorisatie bevraagAutorisatie(final Integer partijCode, String naam, final Integer ingangsDatumRegel);

}
