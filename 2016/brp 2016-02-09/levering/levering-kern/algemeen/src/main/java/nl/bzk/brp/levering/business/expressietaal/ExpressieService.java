/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.expressietaal;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * De interface voor de Expressie service. Deze service biedt methoden die te maken hebben met expressies, zoals het evalueren van een expressie en het
 * opvragen van expressies bij een leveringsautorisatie en het combineren van populatiebperkingen voor specifieke diensten.
 */
public interface ExpressieService {
    /**
     *
     */
    String EXPRESSIE_ALLES_LEVEREN = "WAAR";

    /**
     * Evalueert een expressie tegen een persoonhisvolledig object.
     *
     * @param expressie De expressie
     * @param persoon   De persoon
     * @return expressie resultaat
     * @throws ExpressieExceptie de expressie exceptie
     */
    Expressie evalueer(Expressie expressie, PersoonHisVolledig persoon) throws ExpressieExceptie;

    /**
     * Evalueert een expressie tegen een persoon object.
     *
     * @param expressie De expressie
     * @param persoon   De persoon
     * @return expressie resultaat
     * @throws ExpressieExceptie de expressie exceptie
     */
    Expressie evalueer(Expressie expressie, Persoon persoon) throws ExpressieExceptie;

    /**
     * Maakt een expressie voor de gegevensfilter. Hiermee kunnen alle elementen afgelopen worden.
     *
     * @param dienst De dienst.
     * @param rol    De rol van de leveringautorisatie
     * @return De geparsde expressie.
     * @throws ExpressieExceptie de expressie exceptie
     */
    Expressie geefAttributenFilterExpressie(Dienst dienst, Rol rol) throws ExpressieExceptie;

    /**
     * Geeft de populatiebeperking op basis van de levering autorisatie.
     *
     * @param populatieBeperking                                populatieBeperking
     * @param naderePopulatieBepalingDienstbundel               naderePopulatieBepalingDienstbundel
     * @param naderePopulatieBepalingToegangleveringsautoriatie naderePopulatieBepalingToegangleveringsautoriatie
     * @return
     * @throws ExpressieExceptie de expressie exceptie
     */
    Expressie geefPopulatiebeperking(final String populatieBeperking, final String naderePopulatieBepalingDienstbundel,
        final String naderePopulatieBepalingToegangleveringsautoriatie) throws ExpressieExceptie;

    /**
     * Geeft het attenderingscriterium op basis van de levering autorisatie.
     *
     * @param leveringAutorisatie de levering autorisatie
     * @return de expressie
     * @throws ExpressieExceptie de expressie exceptie
     */
    Expressie geefAttenderingsCriterium(Leveringinformatie leveringAutorisatie) throws ExpressieExceptie;
}
