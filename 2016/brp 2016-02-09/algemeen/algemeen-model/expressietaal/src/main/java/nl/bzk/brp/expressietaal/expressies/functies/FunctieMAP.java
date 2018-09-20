/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.LijstMapSignatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;

/**
 * Representeert de functie MAP(L,V,E). De functie geeft een lijst van waarden terug, die ontstaat door de expressie E
 * uit te voeren voor elk element uit lijst L.
 */
public final class FunctieMAP implements Functieberekening {

    private static final Signatuur SIGNATUUR = new LijstMapSignatuur();

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
        final Expressie expressie = argumenten.get(2);
        ExpressieType type = ExpressieType.ONBEKEND_TYPE;
        if (expressie != null) {
            type = expressie.bepaalTypeVanElementen(context);
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
        if (lijst.isFout() || lijst.isNull(context)) {
            result = lijst;
        } else {
            final Expressie expressie = argumenten.get(2);
            final String variabele = ((VariabeleExpressie) argumenten.get(1)).getIdentifier();
            final List<Expressie> berekendeWaarden = new ArrayList<Expressie>();
            final Context newContext = new Context(context);

            for (Expressie waarde : lijst.getElementen()) {
                newContext.definieer(variabele, waarde);
                final Expressie berekendeWaarde = expressie.evalueer(newContext);
                if (berekendeWaarde.isFout()) {
                    result = berekendeWaarde;
                    break;
                }
                berekendeWaarden.add(berekendeWaarde);
            }
            if (result == null) {
                result = new LijstExpressie(berekendeWaarden);
            } else {
                result = NullValue.getInstance();
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
        } else if (!lijst.isConstanteWaarde() || !variabele.isVariabele()) {
            result = null;
        } else if (lijst.aantalElementen() == 0) {
            result = new LijstExpressie();
        } else {
            result = pasToe(argumenten, context);
        }
        return result;
    }
}
