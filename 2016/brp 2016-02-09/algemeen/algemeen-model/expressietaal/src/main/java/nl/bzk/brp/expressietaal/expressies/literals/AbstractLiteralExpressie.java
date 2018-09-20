/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.AbstractExpressie;

/**
 * Representeert literal (constante) expressies.
 */
abstract class AbstractLiteralExpressie extends AbstractExpressie {
    @Override
    public final boolean isConstanteWaarde() {
        // Per definitie true.
        return true;
    }

    @Override
    public final Expressie evalueer(final Context context) {
        // De evaluatie van een constante is - per definitie - gelijk aan zichzelf.
        return this;
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_LITERAL;
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    @Override
    public final ExpressieType bepaalTypeVanElementen(final Context context) {
        return getType(context);
    }

    @Override
    public final int aantalElementen() {
        return 1;
    }

    @Override
    public final Iterable<Expressie> getElementen() {
        return maakLijstMetExpressie();
    }

    @Override
    public final Expressie getElement(final int index) {
        return getElementUitNietLijstExpressie(index);
    }

    @Override
    public final boolean bevatOngebondenVariabele(final String id) {
        return false;
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        // Aan literal expressies valt niets meer te optimaliseren.
        return this;
    }

}
