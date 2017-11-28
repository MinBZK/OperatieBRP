/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.List;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * GevondenZoekPersoonFilterService.
 */
@FunctionalInterface
interface GevondenZoekPersoonFilterService {
    /**
     * @param autorisatiebundel autorisatiebundel
     * @param persoonsgegevens persoonsgegevens
     * @return gefilterde persoonsgegevens set
     */
    List<Persoonslijst> filterPersoonslijst(Autorisatiebundel autorisatiebundel, List<Persoonslijst> persoonsgegevens);
}
