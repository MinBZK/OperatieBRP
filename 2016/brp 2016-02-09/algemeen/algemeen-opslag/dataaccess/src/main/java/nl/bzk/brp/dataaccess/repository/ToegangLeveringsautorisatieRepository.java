/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;


/**
 * De interface voor de methoden die met toegang leveringsautorisaties te maken hebben.
 */
public interface ToegangLeveringsautorisatieRepository {

    /**
     * Haalt een toegang leveringsautorisatie op via een toegang leveringsautorisatie-id.
     * <p/>
     * Gebruikt nu nog niet de cache. Aanroeper zit in algemeen webservices. De caches moeten verplaatst worden van levering kern naar algemeen.
     *
     * @param id de id
     * @return de leveringsautorisatie
     */
    ToegangLeveringsautorisatie haalToegangLeveringsautorisatieOp(Integer id);

    /**
     * Haalt alle ToegangLeveringsautorisaties op (voor cache).
     *
     * @return lijst met alle toegangLeveringsautorisaties
     */
    List<ToegangLeveringsautorisatie> haalAlleToegangLeveringsautorisatieOp();

    /**
     * Haalt alle leveringsautorisaties op (voor cache).
     *
     * @return lijst met alle leveringsautorisaties
     */
    List<Leveringsautorisatie> haalAlleLeveringsautorisatieOp();

    /**
     * Haalt toegangleveringautorisaties op adhv de partijcode
     * @param partijCode de geautoriseerde partij
     * @return een lijst toegangleveringautorisaties
     */
    List<ToegangLeveringsautorisatie> geefToegangLeveringsautorisaties(Integer partijCode);
}
