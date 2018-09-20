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
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SignatuurOptie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;

/**
 * Functie FunctieISOPGESCHORT(P) die test of de registratie van een persoon opgeschort is.
 */
public final class FunctieISOPGESCHORT implements Functieberekening {

    private static final Signatuur SIGNATUUR =
        new SignatuurOptie(new SimpeleSignatuur(ExpressieType.PERSOON), new SimpeleSignatuur());

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        List<Expressie> volledigeArgumenten;
        if (argumenten.isEmpty()) {
            volledigeArgumenten = new ArrayList<Expressie>();
            volledigeArgumenten.add(new VariabeleExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT));
        } else {
            volledigeArgumenten = argumenten;
        }
        return volledigeArgumenten;
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
        return true;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        // Het is nog niet duidelijk hoe we 'opgeschort' moeten bepalen.
        return BooleanLiteralExpressie.ONWAAR;
    }

    @Override
    public boolean berekenBijOptimalisatie() {
        return true;
    }

    @Override
    public Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        return null;
    }
}
