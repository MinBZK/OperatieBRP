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
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert de functie ALS(C,T,F). Als conditie C waar is, geeft de functie T terug, anders F.
 */
public final class FunctieALS implements Functieberekening {

    private static final Signatuur SIGNATUUR =
        new SimpeleSignatuur(ExpressieType.BOOLEAN, ExpressieType.ONBEKEND_TYPE, ExpressieType.ONBEKEND_TYPE);

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
        final ExpressieType result;
        final ExpressieType t1 = argumenten.get(1).getType(context);
        final ExpressieType t2 = argumenten.get(2).getType(context);
        if (t1.equals(t2) || !t1.equals(ExpressieType.NULL) && t2.equals(ExpressieType.NULL)) {
            result = t1;
        } else if (t1.equals(ExpressieType.NULL) && !t2.equals(ExpressieType.NULL)) {
            result = t2;
        } else {
            result = ExpressieType.ONBEKEND_TYPE;
        }
        return result;
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
        final Expressie conditie = argumenten.get(0).evalueer(context);
        if (conditie.isFout() || conditie.isNull(context)) {
            result = conditie;
        } else if (!getSignatuur().matchArgumenten(argumenten, context)) {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        } else if (conditie.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
            if (conditie.alsBoolean()) {
                result = argumenten.get(1).evalueer(context);
            } else {
                result = argumenten.get(2).evalueer(context);
            }
        } else if (conditie.getType(context) == ExpressieType.BOOLEAN) {
            result = NullValue.getInstance();
        } else {
            result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
        }
        return result;
    }

    @Override
    public boolean berekenBijOptimalisatie() {
        return true;
    }

    @Override
    public Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        Expressie resultaat;
        if (argumenten.get(0).isConstanteWaarde(ExpressieType.BOOLEAN)) {
            if (argumenten.get(0).alsBoolean()) {
                resultaat = argumenten.get(1);
            } else {
                resultaat = argumenten.get(2);
            }
        } else {
            resultaat = null;
        }
        return resultaat;
    }
}
