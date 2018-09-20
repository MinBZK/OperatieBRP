/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.Datumdeel;
import nl.bzk.brp.expressietaal.util.DatumUtils;

/**
 * Representeert de functie DATUM(J,M,D). De functie maakt een datum van drie individuele getallen (voor
 * respectievelijk jaar, maand en dag).
 */
public final class FunctieDATUM implements Functieberekening {

    private static final Signatuur SIGNATUUR =
        new SimpeleSignatuur(ExpressieType.GETAL, ExpressieType.GETAL, ExpressieType.GETAL);

    private static final int MAANDEN_IN_JAAR = 12;

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
        final Expressie jaarArgument = argumenten.get(0);
        final Expressie maandArgument = argumenten.get(1);
        final Expressie dagArgument = argumenten.get(2);
        final int jaar = jaarArgument.alsInteger();
        final int maand = maandArgument.alsInteger();
        final int dag = dagArgument.alsInteger();
        return maakDatum(jaar, maand, dag);
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
     * Maakt een datum van een gegeven jaartal, maand en dag. De functie maakt een correcte datum van de argumenten.
     * Dit betekent dat als bijvoorbeeld het dagnummer kleiner is dan 1 of groter dan het aantal dagen in een bepaalde
     * maand, maand en jaartal worden aangepast, zodat een correcte datum ontstaat.
     *
     * @param jaarInvoer  Jaartal.
     * @param maandInvoer Maand.
     * @param dagInvoer   Dag.
     * @return Datumexpressie met correcte datum.
     */
    public static DatumLiteralExpressie maakDatum(final int jaarInvoer, final int maandInvoer, final int dagInvoer) {
        int jaar = jaarInvoer;
        int maand = maandInvoer;
        int dag = dagInvoer;

        while (maand < 1) {
            jaar--;
            maand += MAANDEN_IN_JAAR;
        }

        if (maand > MAANDEN_IN_JAAR) {
            jaar += (maand - 1) / MAANDEN_IN_JAAR;
            maand = (maand - 1) % MAANDEN_IN_JAAR + 1;
        }

        while (dag < 1) {
            maand--;
            if (maand < 1) {
                jaar--;
                maand += MAANDEN_IN_JAAR;
            }
            dag = dag + DatumUtils.dagenInMaand(jaar, maand);
        }

        while (dag > DatumUtils.dagenInMaand(jaar, maand)) {
            dag = dag - DatumUtils.dagenInMaand(jaar, maand);
            maand++;
            if (maand > MAANDEN_IN_JAAR) {
                jaar++;
                maand -= MAANDEN_IN_JAAR;
            }
        }

        return new DatumLiteralExpressie(new Datumdeel(jaar), new Datumdeel(maand), new Datumdeel(dag));
    }
}
