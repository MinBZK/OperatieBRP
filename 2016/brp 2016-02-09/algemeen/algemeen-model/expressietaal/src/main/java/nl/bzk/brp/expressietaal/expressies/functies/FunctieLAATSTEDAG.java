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
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SignatuurOptie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.util.DatumUtils;

/**
 * Representeert de functies LAATSTE_DAG(J) en LAATSTE_DAG(J, M). De functie geeft de datum van de
 * laatste dag in jaar J respectievelijk van de laatste dag in maand M van jaar J.
 */
public final class FunctieLAATSTEDAG implements Functieberekening {

    private static final Signatuur SIGNATUUR =
        new SignatuurOptie(
            new SimpeleSignatuur(ExpressieType.GETAL),
            new SimpeleSignatuur(ExpressieType.GETAL, ExpressieType.GETAL));

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
        return ExpressieType.DATUM;
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
        Expressie resultaat;
        final int jaar = argumenten.get(0).alsInteger();
        if (argumenten.size() == 1) {
            resultaat = new DatumLiteralExpressie(DatumUtils.laatsteDagInJaar(jaar));
        } else {
            final int maand = argumenten.get(1).alsInteger();
            if (maand >= DatumUtils.JANUARI && maand <= DatumUtils.DECEMBER) {
                resultaat = new DatumLiteralExpressie(DatumUtils.laatsteDagInMaand(jaar, maand));
            } else {
                resultaat = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE,
                    String.format("Invalide maandnummer (%d)", maand));
            }
        }
        return resultaat;
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
