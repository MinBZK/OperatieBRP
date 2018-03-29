/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.Collection;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;

/**
 * Repository voor leveringsautorisaties.
 */
public interface LeveringsautorisatieRepository {

    /**
     * Vind alle leveringsautorisaties voor een gegeven partijrol.
     * @param partijRol de partijrol
     * @return leveringsautorisaties
     */
    List<Leveringsautorisatie> findLeveringsautorisatiesVoorPartij(PartijRol partijRol);

    /**
     * @param leveringsautorisatie De leveringsautorisatie die opgeslagen moet worden.
     * @return De opgeslagen leveringsautorisatie.
     */
    Leveringsautorisatie saveLeveringsautorisatie(Leveringsautorisatie leveringsautorisatie);

    /**
     * Geeft alle gba toegang leveringsautorisaties.
     * @return Alle gba toegang leveringsautorisaties
     */
    Collection<Leveringsautorisatie> geefAlleGbaLeveringsautorisaties();

    /**
     * Geeft de lijst van toegang leveringsautorisaties voor de betreffende partij op de gegeven ingangsdatum.
     * @param partijRol De partijRol waarvoor de toegang leveringsautorisatie wordt opgehaald.
     * @param datumIngang De ingangsdatum van de toegang leveringsautorisatie
     * @return De opgehaalde lijst van toegang leveringsautorisaties
     */
    List<ToegangLeveringsAutorisatie> getToegangLeveringsautorisatieByPartijEnDatumIngang(PartijRol partijRol, Integer datumIngang);
}
