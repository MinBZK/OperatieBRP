/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling;

import java.util.Map;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;

/**
 * De Interface PersoonPopulatieBepalingService waarin alle service-methoden bepalen of voor een leveringAutorisatie een persoon geleverd moet worden.
 */
@FunctionalInterface
public interface PopulatieBepalingService {

    /**
     * Bepaalt hoe de personen vallen ten opzichte van de populatie beschrijving van een leveringAutorisatie.
     * @param mutatiehandeling de mutatiehandeling
     * @param leveringAutorisatie de leveringAutorisatie
     * @return een mapping van persoonID - populatie positie
     * @throws ExpressieException de expressie exceptie
     */
    Map<Persoonslijst, Populatie> bepaalPersoonPopulatieCorrelatie(final Mutatiehandeling mutatiehandeling,
                                                                   final Autorisatiebundel leveringAutorisatie) throws ExpressieException;

}
