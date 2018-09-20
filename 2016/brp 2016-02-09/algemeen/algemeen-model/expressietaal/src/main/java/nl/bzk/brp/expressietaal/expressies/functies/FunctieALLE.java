/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.ExistentieleFunctieSignatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert de functie ALLE(L,V,C). De functie geeft true terug als alle elementen uit lijst L voldoen aan
 * conditie C, waarbij variabele V steeds de waarde van een element uit L aanneemt.
 */
public final class FunctieALLE implements Functieberekening {

    private static final Signatuur SIGNATUUR = new ExistentieleFunctieSignatuur();

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        return argumenten;
    }

    @Override
    public Signatuur getSignatuur() {
        return SIGNATUUR;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.ONBEKEND_TYPE;
    }

    @Override
    public boolean evalueerArgumenten() {
        return false;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        final Expressie result;
        final Expressie lijst = argumenten.get(0).evalueer(context);
        if (lijst.isNull(context)) {
            result = NullValue.getInstance();
        } else if (lijst.isFout()) {
            result = lijst;
        } else if (lijst.aantalElementen() == 0) {
            result = BooleanLiteralExpressie.WAAR;
        } else if (!getSignatuur().matchArgumenten(argumenten, context)) {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        } else {
            final Expressie conditie = argumenten.get(2);
            final String variabele = ((VariabeleExpressie) argumenten.get(1)).getIdentifier();
            final LijstExpressie values = (LijstExpressie) lijst;
            final Context newContext = new Context(context);

            boolean booleanResult = false;
            boolean onbepaaldResultaat = false;

            for (Expressie value : values.getElementen()) {
                newContext.definieer(variabele, value);
                final Expressie valueCondition = conditie.evalueer(newContext);
                if (valueCondition.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
                    booleanResult = valueCondition.alsBoolean();
                } else if (!valueCondition.isConstanteWaarde() || valueCondition.isNull(context)) {
                    onbepaaldResultaat = true;
                }
                if (!booleanResult) {
                    break;
                }
            }

            if (onbepaaldResultaat) {
                result = NullValue.getInstance();
            } else {
                result = BooleanLiteralExpressie.getExpressie(booleanResult);
            }
        }
        return result;
    }

    @Override
    public boolean berekenBijOptimalisatie() {
        return true;
    }

    @Override
    public Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        final Expressie result;
        final Expressie lijst = argumenten.get(0);
        final Expressie variabele = argumenten.get(1);
        final Expressie conditie = argumenten.get(2);

        if (lijst.isNull(context) || conditie.isNull(context)) {
            result = NullValue.getInstance();
        } else if (!lijst.isConstanteWaarde() || !variabele.isVariabele()
            || conditie.getType(context) != ExpressieType.BOOLEAN)
        {
            result = null;
        } else if (lijst.aantalElementen() == 0) {
            result = BooleanLiteralExpressie.WAAR;
        } else if (conditie.isConstanteWaarde()) {
            result = conditie;
        } else {
            result = pasToe(argumenten, context);
        }
        return result;
    }
}
