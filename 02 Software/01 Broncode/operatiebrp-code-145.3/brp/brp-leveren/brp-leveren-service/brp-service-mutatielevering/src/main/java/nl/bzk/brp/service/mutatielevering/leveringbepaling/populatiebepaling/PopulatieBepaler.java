/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.populatiebepaling;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Bepaler die kan bepalen hoe de positie van een een persoon is ten opzichte van een populatie beschreven door een {@link Expressie}.
 */
@FunctionalInterface
interface PopulatieBepaler {

    /**
     * Bepaal de positie van een persoon ten opzichte van een populatie.
     * @param persoon de persoon waarvan de waarde wordt bepaald
     * @param populatiebeperking de populatiebeperking als expressie
     * @param leveringsautorisatie de leveringsautorisatie waarvoor de populatie wordt bepaald
     * @return waar de persoon valt ten opzichte van de populatie
     */
    Populatie bepaalInUitPopulatie(Persoonslijst persoon, Expressie populatiebeperking, Leveringsautorisatie leveringsautorisatie);


}
