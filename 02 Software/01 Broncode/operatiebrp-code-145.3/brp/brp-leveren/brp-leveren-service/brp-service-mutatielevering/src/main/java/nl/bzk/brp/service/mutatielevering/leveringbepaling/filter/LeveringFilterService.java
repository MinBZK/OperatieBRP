/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import java.util.Map;
import java.util.Set;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Service die de personen die niet geleverd hoeven te worden filterd.
 */
public interface LeveringFilterService {

    /**
     * Filtert de personen die niet geleverd moeten worden uit de populatie-map.
     * @param autorisatiebundel De autorisatiebundel.
     * @param mogelijkTeLeverenPersonen De populatie-map.
     * @return de set van te leveren persoonsgegevens
     * @throws ExpressieException de expressie exceptie
     */

    Set<Persoonslijst> bepaalTeLeverenPersonen(Autorisatiebundel autorisatiebundel, Map<Persoonslijst, Populatie> mogelijkTeLeverenPersonen)
            throws ExpressieException;

}
