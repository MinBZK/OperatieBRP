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
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;

/**
 * Representeert de functie PLATTE_LIJST(E). De functie levert een platte lijst van elementen van E. Als E geen lijst
 * is, is het resultaat {E}; anders worden de elementen van de lijst platgeslagen.
 */
public final class FunctiePLATTELIJST implements Functieberekening {

    private static final Signatuur SIGNATUUR = new SimpeleSignatuur(ExpressieType.LIJST);

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
        final Expressie expressie = argumenten.get(0);
        ExpressieType type = ExpressieType.ONBEKEND_TYPE;
        if (expressie != null) {
            type = expressie.bepaalTypeVanElementen(context);
        }
        return type;
    }

    @Override
    public boolean evalueerArgumenten() {
        return true;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        final Expressie argument = argumenten.get(0);
        return new LijstExpressie(maakPlatteLijst(argument));
    }

    @Override
    public boolean berekenBijOptimalisatie() {
        return true;
    }

    @Override
    public Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        return null;
    }

    /**
     * Geeft de expressie als 'platgeslagen' lijst terug.
     * Als de expressie E geen lijst is, is het resultaat {E}.
     * Als de expressie E een lijst {A1, A2, ..., An} is, is het resultaat een concatenatie van de platgeslagen
     * elementen Ai.
     *
     * @param expressie De expressie die platgeslagen moet worden.
     * @return Platgeslagen expressie.
     */
    private static List<Expressie> maakPlatteLijst(final Expressie expressie) {
        final List<Expressie> platteLijst = new ArrayList<Expressie>();
        if (expressie.isLijstExpressie()) {
            for (Expressie element : expressie.getElementen()) {
                final List<Expressie> elementLijst = maakPlatteLijst(element);
                platteLijst.addAll(elementLijst);
            }
        } else {
            platteLijst.add(expressie);
        }
        return platteLijst;
    }
}
