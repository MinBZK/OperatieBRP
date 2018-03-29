/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.expressie;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * De interface voor de Expressie service. Deze service biedt methoden die te maken hebben met expressies, zoals het evalueren van een expressie en het
 * opvragen van expressies bij een leveringsautorisatie en het combineren van populatiebperkingen voor specifieke diensten.
 */
public interface ExpressieService {

    /**
     * Bepaalt of het attenderingsCriterium aangeeft dat attributen zijn gewijzigd.
     * @param oud oude persoonsbeeeld
     * @param nieuw nieuwe persoonsbeeld
     * @param expressie de expressie van het attenderingsCriterium
     * @return {@code true} als het attenderingsCriterium aangeeft dat attributen zijn gewijzigd
     */
    boolean bepaalPersoonGewijzigd(Persoonslijst oud, Persoonslijst nieuw, Expressie expressie);

    /**
     * Geeft de populatiebeperking op basis van de levering autorisatie.
     * @param autorisatiebundel de autorisatiegegevens
     * @return populatiebeperking expresie
     * @throws ExpressieException de expressie exceptie
     */
    Expressie geefPopulatiebeperking(final Autorisatiebundel autorisatiebundel) throws ExpressieException;

    /**
     * @param expressieParsed expressieParsed
     * @param persoonslijst persoonslijst
     * @param datumStartSelectie datumStartSelectie
     * @param selectieLijst selectieLijst
     * @return boolean resultaat van de expressie evaluatie
     */
    Boolean evalueerMetSelectieDatumEnSelectielijst(Expressie expressieParsed, Persoonslijst persoonslijst,
                                                    int datumStartSelectie, SelectieLijst selectieLijst);

    /**
     * Geeft het attenderingscriterium op basis van de levering autorisatie.
     * @param autorisatiebundel de autorisatiegegevens
     * @return de expressie
     */
    Expressie geefAttenderingsCriterium(Autorisatiebundel autorisatiebundel);

    /**
     * @param expressie expressie
     * @param persoon persoon
     * @return het resultaat van de expressie evaluatie
     */
    Boolean evalueer(Expressie expressie, Persoonslijst persoon);
}
