/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.Collection;
import java.util.List;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;

/**
 * VerwerkPersoonExecutorService.
 */
interface VerwerkPersoonExecutorService {
    /**
     * @param selectieTaak selectieTaak
     * @param personen personen
     * @param autorisatiebundels autorisatiebundels
     * @return verwerkte personen
     */
    Collection<VerwerkPersoonResultaat> verwerkPersonen(final SelectieVerwerkTaakBericht selectieTaak,
                                                        final Collection<Persoonslijst> personen,
                                                        final List<SelectieAutorisatiebundel> autorisatiebundels) throws SelectieException;
}
