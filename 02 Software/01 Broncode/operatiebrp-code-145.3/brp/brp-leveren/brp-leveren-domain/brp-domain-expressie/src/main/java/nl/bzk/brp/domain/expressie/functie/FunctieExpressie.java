/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.NonLiteralExpressie;
import nl.bzk.brp.domain.expressie.Prioriteit;
import nl.bzk.brp.domain.expressie.NullLiteral;

/**
 * De {@link FunctieExpressie} is verantwoordelijk voor het uitvoeren van {@link Functie} aanroepen.
 *
 * Expressie voor functieaanroepen.
 */
public final class FunctieExpressie implements NonLiteralExpressie {

    private final List<Expressie> argumenten;
    private final Functie functie;

    /**
     * Constructor.
     *
     * @param functieNaam Naam van van de functie.
     * @param aArgumenten Argumenten voor de aanroep.
     */
    public FunctieExpressie(final String functieNaam, final List<Expressie> aArgumenten) {
        functie = FunctieFactory.geefFunctie(functieNaam);
        argumenten = functie.init(aArgumenten);
        functie.getSignatuur().assertSignatuurCorrect(argumenten, null, functie.getKeyword());
    }

    @Override
    public ExpressieType getType(final Context context) {
        return functie.getType(argumenten, context);
    }

    @Override
    public Expressie evalueer(final Context context) {
        Expressie result = null;
        final List<Expressie> argumentenVoorEvaluatie;

        /* Controleer eerst of de argumenten voldoen aan de signatuur. Bereken daarna eventueel de argumenten.
           En pas de functie toe.
         */
        functie.getSignatuur().assertSignatuurCorrect(argumenten, context, functie.getKeyword());

        if (functie.evalueerArgumenten()) {
            /* De argumenten van de functieaanroep moeten/mogen eerst geëvalueerd worden, voordat de functie
               wordt toegepast. Als een van de argumenten NULL (onbekend) is, dan wordt de functie niet
               toegepast en is het resultaat NULL.
             */
            argumentenVoorEvaluatie = new LinkedList<>();
            for (final Expressie argument : argumenten) {
                final Expressie argResult = argument.evalueer(context);
                if (argResult.isNull() || !argResult.isConstanteWaarde()) {
                    result = NullLiteral.INSTANCE;
                    break;
                } else {
                    argumentenVoorEvaluatie.add(argResult);
                }
            }
        } else {
            argumentenVoorEvaluatie = argumenten;
        }

        if (result == null) {
            result = functie.evalueer(argumentenVoorEvaluatie, context);
        }

        return result;
    }

    @Override
    public Prioriteit getPrioriteit() {
        //De prioriteit van een functie is gelijk aan die van literals.
        return Prioriteit.PRIORITEIT_LITERAL;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(functie.getKeyword());
        result.append('(');
        boolean eersteArgument = true;
        for (final Expressie argument : argumenten) {
            if (!eersteArgument) {
                result.append(", ");
            }
            eersteArgument = false;
            result.append(argument.toString());
        }
        result.append(')');
        return result.toString();
    }
}
