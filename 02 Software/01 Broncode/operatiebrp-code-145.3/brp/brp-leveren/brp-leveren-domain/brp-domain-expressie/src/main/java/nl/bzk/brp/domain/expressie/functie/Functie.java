/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.List;
import javax.annotation.concurrent.ThreadSafe;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.signatuur.Signatuur;

/**
 * Interface voor functiespecifiek gedrag.
 */
@ThreadSafe
public interface Functie {

    /**
     * Vult de argumenten voor een aanroep aan met eventueel weggelaten default argumenten en controleert parse time de argumenten.
     * @param argumenten Argumenten uit de aanroep.
     * @return Argumenten uit de aanroep aangevuld met default argumenten.
     */
    default List<Expressie> init(List<Expressie> argumenten) {
        return argumenten;
    }

    /**
     * @param argumenten argumenten
     * @param index index
     * @param <T> argument expressie type
     * @return argument expressie
     */
    default <T extends Expressie> T getArgument(List<Expressie> argumenten, int index) {
        return (T) argumenten.get(index);
    }

    /**
     * Geeft de signatuur van de functie.
     * @return Signatuur van de functie;
     */
    Signatuur getSignatuur();

    /**
     * Geeft het type van het resultaat van de functie, gegeven de argumenten uit de aanroep.
     * @param argumenten Argumenten van de aanroep.
     * @param context De gedefinieerde identifiers.
     * @return Type van het resultaat van de functie.
     */
    ExpressieType getType(List<Expressie> argumenten, Context context);

    /**
     * Berekent de functie op basis van de meegegeven gereduceerde argumenten (expressies). Indien argumenten geëvalueerd worden voordat de functie wordt
     * toegepast (evalueerArgumenten() == true), gaat de functie ervan uit dat de argumenten voldoen aan de signatuur van de functie.
     * @param argumenten Argumenten van de functieaanroep.
     * @param context Gedefinieerde identifiers.
     * @return Resultaat van het de functie of null, indien fout.
     */
    Expressie evalueer(List<Expressie> argumenten, Context context);

    /**
     * Geeft TRUE als de argumenten van de functie geëvalueerd moeten worden, voordat de functie berekend wordt. Dit is by default het geval.
     * @return TRUE als argumenten geëvalueerd moeten worden.
     */
    default boolean evalueerArgumenten() {
        return true;
    }

    /**
     * @return keyword van de expressie
     */
    String getKeyword();

}
