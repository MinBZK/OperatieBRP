/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.List;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.VariabeleExpressie;
import nl.bzk.brp.domain.expressie.signatuur.Signatuur;

/**
 * Abstracte implementatie van de functieberekening interface.
 */
abstract class AbstractFunctie implements Functie {

    private final Signatuur signatuur;

    /**
     * Constructor voor de functie.
     * @param signatuur de signatuur van de functie
     */
    AbstractFunctie(final Signatuur signatuur) {
        this.signatuur = signatuur;
    }

    @Override
    public final Signatuur getSignatuur() {
        return signatuur;
    }

    @Override
    public final String getKeyword() {
        return getClass().getAnnotation(FunctieKeyword.class).value();
    }

    /**
     * Doet een assert dat de signatuur van de functie correct is.
     * @param argumenten de argumeten van de functie.
     * @param context de context
     */
    void assertSignatuurCorrect(final List<Expressie> argumenten,
                                final Context context) {
        signatuur.assertSignatuurCorrect(argumenten, context, getKeyword());
    }

    /**
     * Evalueer lijst met expressie, waarbij afhankelijk van {@param alleElementenVoldoen} minstens één element OF alle elementen uit lijst L voldoet aan
     * conditie C, waarbij variabele V steeds de waarde van een element uit L aanneemt
     */
    Expressie evalueerLijst(final List<Expressie> argumenten, final Context context, boolean alleElementenVoldoen) {
        final Expressie result;
        final Expressie lijst = argumenten.get(0).evalueer(context);
        if (lijst.isNull()) {
            result = NullLiteral.INSTANCE;
        } else if (((LijstExpressie) lijst).isEmpty()) {
            result = alleElementenVoldoen ? BooleanLiteral.WAAR : BooleanLiteral.ONWAAR;
        } else {
            result = evalueerGevuldeLijst(argumenten, context, alleElementenVoldoen);
        }
        return result;
    }

    private Expressie evalueerGevuldeLijst(final List<Expressie> argumenten, final Context context, boolean alleElementenVoldoen) {
        final Expressie result;
        final Expressie lijst = argumenten.get(0).evalueer(context);
        assertSignatuurCorrect(argumenten, context);
        final Expressie conditie = argumenten.get(2);
        final String variabele = ((VariabeleExpressie) argumenten.get(1)).getIdentifier();
        final Context newContext = new Context(context);

        boolean booleanResult = false;
        boolean onbepaaldResultaat = false;

        for (final Expressie value : lijst.alsLijst()) {
            newContext.definieer(variabele, value);
            final Expressie valueCondition = conditie.evalueer(newContext);
            if (valueCondition.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
                booleanResult = valueCondition.alsBoolean();
            } else if (!valueCondition.isConstanteWaarde() || valueCondition.isNull()) {
                onbepaaldResultaat = true;
            }
            if (alleElementenVoldoen != booleanResult) {
                break;
            }
        }

        if (onbepaaldResultaat) {
            result = NullLiteral.INSTANCE;
        } else {
            result = BooleanLiteral.valueOf(booleanResult);
        }
        return result;
    }
}
