/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.signatuur;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.BiPredicate;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;

/**
 * Signatuur voor functies en operatoren.
 * Controleert of de gegeven argumenten voldoen aan de signatuur. Geeft TRUE terug als de argumenten voldoen,
 * anders FALSE.
 */
@FunctionalInterface
public interface Signatuur extends BiPredicate<List<Expressie>, Context>{

    /**
     * Assert dat de argumenten voldoen aan de signatuur.
     *
     * @param argumenten     de argumenten.
     * @param context        de context
     * @param functieKeyword keyword van de functie
     */
    default void assertSignatuurCorrect(final List<Expressie> argumenten, final Context context, final String functieKeyword) {
        if (!test(argumenten, context)) {

            final List<ExpressieType> typen = Lists.newArrayListWithCapacity(argumenten.size());
            for (Expressie expressie : argumenten) {
                typen.add(expressie.getType(context));
            }

            throw new SignatuurIncorrectException(String.format("De functie %s kan niet overweg met de argumenten: %s",
                    functieKeyword, typen));
        }
    }
}
