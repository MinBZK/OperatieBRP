/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * SelectieBepaalLeverenService.
 */
interface SelectieBepaalLeverenService {
    /**
     * @param persoonslijst persoonslijst
     * @param autorisatiebundel autorisatiebundel
     * @param selectieStartDatum de startdatum van de actuele selectierun
     * @param selectierunId selectierunId
     * @param lijstGebruiken lijstGebruiken
     * @param selectieTaakId selectieTaakId
     * @return in selectie
     */
    boolean inSelectie(Persoonslijst persoonslijst, Autorisatiebundel autorisatiebundel, final int selectieStartDatum, final Integer selectierunId,
                       final boolean lijstGebruiken, final Integer selectieTaakId);
}
