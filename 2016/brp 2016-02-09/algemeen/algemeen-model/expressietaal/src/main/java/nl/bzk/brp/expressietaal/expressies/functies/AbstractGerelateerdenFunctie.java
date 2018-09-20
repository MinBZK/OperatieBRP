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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Representeert een functie die een lijst van via een relatie betrokken personen in een bepaalde rol teruggeeft.
 */
public abstract class AbstractGerelateerdenFunctie implements Functieberekening {

    private static final Signatuur SIGNATUUR =
        new SignatuurOptie(new SimpeleSignatuur(ExpressieType.PERSOON), new SimpeleSignatuur());


    /**
     * Geeft het soort relatie waarover de functie gaat.
     */
    private final SoortRelatie  soortRelatie;
    /**
     * Geeft de rol van de persoon voor wie de functie wordt uitgevoerd.
     */
    private final SoortBetrokkenheid betrokkenheid;
    /**
     * Geeft de rol van de gerelateerde persoon.
     */
    private final SoortBetrokkenheid gerelateerdeBetrokkenheid;

    /**
     * constructor.
     * @param soortRelatie het soort relatie waarover de functie gaat.
     * @param betrokkenheid de rol van de persoon voor wie de functie wordt uitgevoerd.
     * @param gerelateerdeBetrokkenheid de rol van de gerelateerde persoon.
     */
    protected AbstractGerelateerdenFunctie(final SoortRelatie soortRelatie, final SoortBetrokkenheid betrokkenheid,
        final SoortBetrokkenheid gerelateerdeBetrokkenheid)
    {
        this.soortRelatie = soortRelatie;
        this.betrokkenheid = betrokkenheid;
        this.gerelateerdeBetrokkenheid = gerelateerdeBetrokkenheid;
    }


    @Override
    public final List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        List<Expressie> volledigeArgumenten;
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
    public final ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.PERSOON;
    }

    @Override
    public final boolean evalueerArgumenten() {
        return true;
    }

    @Override
    public final Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        final Expressie result;
        final Expressie argument = argumenten.get(0);
        final BrpObject rootObject = ((BrpObjectExpressie) argument).getBrpObject();
        if (rootObject instanceof Persoon) {
            final Persoon persoon = (Persoon) ((BrpObjectExpressie) argument).getBrpObject();
            result = pasToeOpPersoon(persoon);
        } else if (rootObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig persoon = (PersoonHisVolledig) ((BrpObjectExpressie) argument).getBrpObject();
            result = pasToeOpPersoonHisVolledig(persoon);
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
     * @param persoon Persoon waarop functie toegepast moet worden.
     * @return Resultaat van de functie.
     */
    private Expressie pasToeOpPersoon(final Persoon persoon) {
        final List<Expressie> values = new ArrayList<>();
        final Collection<Betrokkenheid> gerelateerdeBetrokkenheden = FilterUtils.getGerelateerdeBetrokkenheden(
            persoon, betrokkenheid, soortRelatie, gerelateerdeBetrokkenheid);

        for (Betrokkenheid b : gerelateerdeBetrokkenheden) {
            if (b.getPersoon() != null) {
                values.add(new BrpObjectExpressie(b.getPersoon(), ExpressieType.PERSOON));
            }
        }

        return new LijstExpressie(values);
    }

    /**
     * Past de functie toe op een PersoonHisVolledig-object.
     *
     * @param persoon Persoon waarop functie toegepast moet worden.
     * @return Resultaat van de functie.
     */
    private Expressie pasToeOpPersoonHisVolledig(final PersoonHisVolledig persoon) {
        final List<Expressie> values = new ArrayList<>();
        final Collection<BetrokkenheidHisVolledig> gerelateerdeBetrokkenheden =
            FilterUtils.getGerelateerdeBetrokkenheden(persoon, betrokkenheid, soortRelatie,
                gerelateerdeBetrokkenheid);

        for (BetrokkenheidHisVolledig b : gerelateerdeBetrokkenheden) {
            if (b.getPersoon() != null) {
                values.add(new BrpObjectExpressie(b.getPersoon(), ExpressieType.PERSOON));
            }
        }

        return new LijstExpressie(values);
    }
}
