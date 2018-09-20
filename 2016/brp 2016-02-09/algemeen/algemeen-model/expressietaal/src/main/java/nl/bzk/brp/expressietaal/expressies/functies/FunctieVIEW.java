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
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Representeert de functie VIEW(P,D). De functie geeft een Persoon-object op een bepaald (materieel) historische
 * datum.
 */
public final class FunctieVIEW implements Functieberekening {

    private static final Signatuur SIGNATUUR = new SimpeleSignatuur(ExpressieType.PERSOON, ExpressieType.DATUM);

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
        return ExpressieType.PERSOON;
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
        final Expressie result;
        final Expressie persoonArgument = argumenten.get(0);
        final Expressie datumArgument = argumenten.get(1);

        final BrpObject brpObject = ((BrpObjectExpressie) persoonArgument).getBrpObject();
        if (brpObject instanceof Persoon) {
            result = persoonArgument;
        } else if (brpObject instanceof PersoonHisVolledig) {
            final int datum = datumArgument.alsInteger();
            result = new BrpObjectExpressie(new PersoonView((PersoonHisVolledig) brpObject,
                DatumTijdAttribuut.nu(), new DatumAttribuut(datum)), ExpressieType.PERSOON);
        } else {
            result = NullValue.getInstance();
        }

        return result;
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
