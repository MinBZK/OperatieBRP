/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SignatuurOptie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.util.FilterUtils;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.Relatie;

/**
 * Representeert een functie die een lijst van een gespecificeerde soort relaties van een persoon teruggeeft.
 */
public abstract class AbstractRelatieFunctie implements Functieberekening {

    private static final Signatuur SIGNATUUR =
        new SignatuurOptie(new SimpeleSignatuur(ExpressieType.PERSOON), new SimpeleSignatuur());

    @Override
    public final List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        final List<Expressie> volledigeArgumenten;
        if (argumenten.isEmpty()) {
            volledigeArgumenten = new ArrayList<>();
            volledigeArgumenten.add(new VariabeleExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT));
        } else {
            volledigeArgumenten = argumenten;
        }
        return volledigeArgumenten;
    }

    @Override
    public final Signatuur getSignatuur() {
        return SIGNATUUR;
    }

    @Override
    public final ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public abstract ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context);

    @Override
    public final boolean evalueerArgumenten() {
        return true;
    }

    @Override
    public final Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        final Expressie result;
        final Expressie rootObject = argumenten.get(0);

        if (((BrpObjectExpressie) rootObject).getBrpObject() instanceof Persoon) {
            result = pasToeOpPersoon((Persoon) ((BrpObjectExpressie) rootObject).getBrpObject(), argumenten, context);
        } else if (((BrpObjectExpressie) rootObject).getBrpObject() instanceof PersoonHisVolledig) {
            result = pasToeOpPersoonHisVolledig((PersoonHisVolledig) ((BrpObjectExpressie) rootObject).getBrpObject(),
                argumenten, context);
        } else {
            result = NullValue.getInstance();
        }
        return result;
    }

    @Override
    public final boolean berekenBijOptimalisatie() {
        return true;
    }

    @Override
    public final Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        return null;
    }

    /**
     * Past de functie toe op een Persoon-object.
     *
     * @param persoon    Persoon waarop functie toegepast moet worden.
     * @param argumenten De argumenten van de functieaanroep.
     * @param context    Gedefinieerde identifiers.
     * @return Resultaat van de functie.
     */
    private Expressie pasToeOpPersoon(final Persoon persoon, final List<Expressie> argumenten, final Context context) {
        final List<Expressie> values = new ArrayList<>();

        final Collection<Relatie> relaties = FilterUtils.getRelaties(persoon, null, getSoortRelatie());
        for (final Relatie relatie : relaties) {
            values.add(new BrpObjectExpressie(relatie, getTypeVanElementen(argumenten, context)));
        }
        return new LijstExpressie(values);
    }

    /**
     * Past de functie toe op een PersoonHisVolledig-object.
     *
     * @param persoon    Persoon waarop functie toegepast moet worden.
     * @param argumenten De argumenten van de functieaanroep.
     * @param context    Gedefinieerde identifiers.
     * @return Resultaat van de functie.
     */
    private Expressie pasToeOpPersoonHisVolledig(final PersoonHisVolledig persoon, final List<Expressie> argumenten,
                                                 final Context context)
    {
        final List<Expressie> values = new ArrayList<Expressie>();

        final Collection<RelatieHisVolledig> relaties = FilterUtils.getRelaties(persoon, null, getSoortRelatie());
        for (final RelatieHisVolledig relatie : relaties) {
            values.add(new BrpObjectExpressie(relatie, getTypeVanElementen(argumenten, context)));
        }
        return new LijstExpressie(values);
    }

    /**
     * Geeft de soort relatie die geselecteerd moet worden of NULL indien alle relaties geselecteerd moeten worden.
     *
     * @return TRUE als de functie betrekking heeft op relaties van het gegeven soort.
     */
    protected abstract SoortRelatie getSoortRelatie();
}
