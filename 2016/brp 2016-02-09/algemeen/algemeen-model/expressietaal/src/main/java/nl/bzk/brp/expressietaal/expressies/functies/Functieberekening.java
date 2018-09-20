/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;

/**
 * Interface voor functiespeciek gedrag.
 */
public interface Functieberekening {

    /**
     * Vult de argumenten voor een aanroep aan met eventueel weggelaten default argumenten.
     *
     * @param argumenten Argumenten uit de aanroep.
     * @return Argumenten uit de aanroep aangevuld met default argumenten.
     */
    List<Expressie> vulDefaultArgumentenIn(List<Expressie> argumenten);

    /**
     * Geeft de signatuur van de functie.
     *
     * @return Signatuur van de functie;
     */
    Signatuur getSignatuur();

    /**
     * Geeft het type van het resultaat van de functie, gegeven de argumenten uit de aanroep.
     *
     * @param argumenten Argumenten van de aanroep.
     * @param context    De gedefinieerde identifiers.
     * @return Type van het resultaat van de functie.
     */
    ExpressieType getType(List<Expressie> argumenten, Context context);

    /**
     * Indien de functie een lijst teruggeeft, geeft deze methode het type van de elementen of ExpressieType.UNKNOWN
     * als het type niet te bepalen is of als de elementen een verschillend type hebben.
     * Als de functie geen lijst teruggeeft, geeft deze methode ExpressieType.UNKNOWN.
     *
     * @param argumenten Argumenten van de aanroep.
     * @param context    De gedefinieerde identifiers.
     * @return Type van de elementen uit het resultaat van de aanroep of ExpressieType.UNKNOWN.
     */
    ExpressieType getTypeVanElementen(List<Expressie> argumenten, Context context);

    /**
     * Geeft TRUE als de argumenten van de functie geëvalueerd moeten worden, voordat de functie berekend wordt. Dit is
     * by default het geval.
     *
     * @return TRUE als argumenten geëvalueerd moeten worden.
     */
    boolean evalueerArgumenten();

    /**
     * Berekent de functie op basis van de meegegeven gereduceerde argumenten (expressies). Indien argumenten
     * geëvalueerd worden voordat de functie wordt toegepast (evalueerArgumenten() == true), gaat de
     * functie ervan uit dat de argumenten voldoen aan de signatuur van de functie.
     *
     * @param argumenten Argumenten van de functieaanroep.
     * @param context    Gedefinieerde identifiers.
     * @return Resultaat van het de functie of null, indien fout.
     */
    Expressie pasToe(List<Expressie> argumenten, Context context);

    /**
     * Geeft TRUE als de functie bij het optimaliseren berekend mag worden; anders FALSE.
     *
     * @return TRUE als de functie bij het optimaliseren berekend mag worden; anders FALSE.
     */
    boolean berekenBijOptimalisatie();

    /**
     * Geeft, indien mogelijk, een geoptimaliseerde versie van een functieaanroep gegeven een aantal argumenten terug.
     * Als er geen alternatieve, geoptimaliseerde versie mogelijk is, is het resultaat NULL.
     *
     * @param argumenten Argumenten van de functieaanroep.
     * @param context    Gedefinieerde identifiers.
     * @return Geoptimaliseerde versie van de aanroep of NULL.
     */
    Expressie optimaliseer(List<Expressie> argumenten, Context context);
}
