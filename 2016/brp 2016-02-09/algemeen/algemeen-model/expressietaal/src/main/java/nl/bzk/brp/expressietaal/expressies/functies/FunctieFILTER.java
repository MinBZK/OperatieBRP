/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.ArrayList;
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
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert de functie FILTER(L,V,C). De functie geeft een lijst van waarden uit L terug die voldoen aan
 * conditie C.
 */
public final class FunctieFILTER implements Functieberekening {

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
        return ExpressieType.LIJST;
    }

    @Override
    public ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context) {
        final Expressie lijst = argumenten.get(0);
        ExpressieType type = ExpressieType.ONBEKEND_TYPE;
        if (lijst != null) {
            type = lijst.bepaalTypeVanElementen(context);
        }
        return type;
    }

    @Override
    public boolean evalueerArgumenten() {
        return false;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        Expressie result = null;
        final Expressie lijst = argumenten.get(0).evalueer(context);
        if (lijst.isNull(context)) {
            result = NullValue.getInstance();
        } else if (lijst.isFout()) {
            result = lijst;
        } else if (!getSignatuur().matchArgumenten(argumenten, context)) {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        } else {
            final Expressie conditie = argumenten.get(2);
            final String variabele = ((VariabeleExpressie) argumenten.get(1)).getIdentifier();
            final LijstExpressie waarden = (LijstExpressie) lijst;
            final List<Expressie> geselecteerdeWaarden = new ArrayList<Expressie>();
            final Context newContext = new Context(context);

            for (Expressie waarde : waarden.getElementen()) {
                newContext.definieer(variabele, waarde);
                final Expressie valueCondition = conditie.evalueer(newContext);
                if (valueCondition.isFout()) {
                    result = valueCondition;
                    break;
                } else if (valueCondition.isConstanteWaarde(ExpressieType.BOOLEAN, context)
                    && valueCondition.alsBoolean())
                {
                    geselecteerdeWaarden.add(waarde);
                }
            }
            if (result == null) {
                result = new LijstExpressie(geselecteerdeWaarden);
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
            result = new LijstExpressie();
        } else if (conditie.isConstanteWaarde()) {
            if (conditie.alsBoolean()) {
                result = lijst;
            } else {
                result = new LijstExpressie();
            }
        } else {
            result = pasToe(argumenten, context);
        }
        return result;
    }
}
