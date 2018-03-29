/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker.persoonsbeelden;

import java.util.Collection;
import nl.bzk.brp.domain.internbericht.selectie.SelectieVerwerkTaakBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;

/**
 * PersoonsBeeldenService.
 */
public interface PersoonsBeeldenService {

    /**
     * @param selectieTaak selectieTaak
     * @return een gevulde queue met persoonsbeelden
     * @throws SelectieException SelectieException
     */
    Collection<Persoonslijst> maakPersoonsBeelden(SelectieVerwerkTaakBericht selectieTaak) throws SelectieException;
}
