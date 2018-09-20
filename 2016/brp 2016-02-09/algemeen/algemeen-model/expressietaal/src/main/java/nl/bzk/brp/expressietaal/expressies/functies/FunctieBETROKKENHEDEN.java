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
import nl.bzk.brp.expressietaal.EvaluatieFoutCode;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieTaalConstanten;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.FoutExpressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SignatuurOptie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.expressietaal.util.FilterUtils;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Representeert de functie BETROKKENHEDEN(P,ROL). De functie geeft de betrokkenheden van
 * van persoon P in een lijst terug.
 * Als ROL niet leeg is, worden alleen relaties verwerkt waarin persoon P de rol ROL heeft.
 */
public final class FunctieBETROKKENHEDEN implements Functieberekening {

    private static final Signatuur SIGNATUUR = new SignatuurOptie(
        new SimpeleSignatuur(),
        new SimpeleSignatuur(ExpressieType.PERSOON),
        new SimpeleSignatuur(ExpressieType.STRING),
        new SimpeleSignatuur(ExpressieType.PERSOON, ExpressieType.STRING),
        new SimpeleSignatuur(ExpressieType.STRING, ExpressieType.STRING)
    );

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        final int eersteArgument = 0;
        final int tweedeArgument = 1;
        List<Expressie> result = null;

        final List<Expressie> volledigeArgumenten = new ArrayList<>();
        Expressie persoonArgument = null;
        Expressie rolArgument = new StringLiteralExpressie("");

        if (argumenten.isEmpty()) {
            persoonArgument = new VariabeleExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT);
        } else if (argumenten.size() == 1) {
            if (checkArgumenten(argumenten, ExpressieType.PERSOON)
                || checkArgumenten(argumenten, ExpressieType.ONBEKEND_TYPE))
            {
                persoonArgument = argumenten.get(eersteArgument);
            } else if (checkArgumenten(argumenten, ExpressieType.STRING)) {
                persoonArgument = new VariabeleExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT);
                rolArgument = argumenten.get(eersteArgument);
            } else if (argumenten.size() == 2) {
                persoonArgument = argumenten.get(eersteArgument);
                rolArgument = argumenten.get(tweedeArgument);
            }
        } else {
            result = argumenten;
        }

        if (persoonArgument != null) {
            volledigeArgumenten.add(persoonArgument);
            volledigeArgumenten.add(rolArgument);

            result = volledigeArgumenten;
        }
        return result;
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
        return ExpressieType.BETROKKENHEID;
    }

    @Override
    public boolean evalueerArgumenten() {
        return true;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        final int eersteArgument = 0;
        final int tweedeArgument = 1;

        Expressie result = null;
        final Expressie argument = argumenten.get(eersteArgument);

        SoortBetrokkenheid soortBetrokkenheid = null;

        final String rolStr = argumenten.get(tweedeArgument).alsString();
        if (rolStr.length() > 0) {
            try {
                soortBetrokkenheid = SoortBetrokkenheid.valueOf(rolStr);
            } catch (final IllegalArgumentException e) {
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        }

        if (result == null) {
            final BrpObject rootObject = ((BrpObjectExpressie) argument).getBrpObject();
            if (rootObject instanceof Persoon) {
                result = bepaalBetrokkenheden((Persoon) rootObject, soortBetrokkenheid);
            } else if (rootObject instanceof PersoonHisVolledig) {
                result = bepaalBetrokkenheden((PersoonHisVolledig) rootObject, soortBetrokkenheid);
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
        return null;
    }

    /**
     * Bepaal de gerelateerde betrokkenheden van een Persoon-object.
     *
     * @param persoon                        Persoon.
     * @param soortBetrokkenheid             Rol van de persoon in de relatie, of NULL.
     * @return Alle gerelateerde betrokkenheden die voldoen aan de zoekparameters.
     */
    private Expressie bepaalBetrokkenheden(final Persoon persoon, final SoortBetrokkenheid soortBetrokkenheid)
    {
        final List<Expressie> values = new ArrayList<>();
        final Collection<? extends Betrokkenheid> betrokkenheden = FilterUtils.getBetrokkenheden(persoon, soortBetrokkenheid);

        for (final Betrokkenheid gerelateerdeBetrokkenheid : betrokkenheden) {
            values.add(new BrpObjectExpressie(gerelateerdeBetrokkenheid, ExpressieType.BETROKKENHEID));
        }
        return new LijstExpressie(values);
    }

    /**
     * Bepaal de gerelateerde betrokkenheden van een PersoonHisVolledig-object.
     *
     * @param persoon                        Persoon.
     * @param soortBetrokkenheid             Rol van de persoon in de relatie, of NULL.
     * @return Alle gerelateerde betrokkenheden die voldoen aan de zoekparameters.
     */
    private Expressie bepaalBetrokkenheden(final PersoonHisVolledig persoon, final SoortBetrokkenheid soortBetrokkenheid)
    {
        final List<Expressie> values = new ArrayList<>();
        final Collection<? extends BetrokkenheidHisVolledig> gerelateerdeBetrokkenheden =
            FilterUtils.getBetrokkenheden(persoon, soortBetrokkenheid);

        for (final BetrokkenheidHisVolledig gerelateerdeBetrokkenheid : gerelateerdeBetrokkenheden) {
            values.add(new BrpObjectExpressie(gerelateerdeBetrokkenheid, ExpressieType.BETROKKENHEID));
        }
        return new LijstExpressie(values);
    }

    /**
     * Controleert of het type van een expressie (meegegeven als argument) voldoet aan het verwachte type. Indien het
     * type van de expressie onbekend (UNKNOWN) is, kan dat niet worden vastgesteld. Dit geldt ook voor gevallen
     * waarin de waarde van de expressie onbekend (NULL) is.
     *
     * @param verwachtType      Argumenttype dat verwacht wordt.
     * @param gevondenExpressie Expressie meegegeven als argument.
     * @param context           De bekende symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er
     *                          geen context
     *                          is.
     * @return TRUE als het type voldoet, anders FALSE.
     */
    private static boolean checkType(final ExpressieType verwachtType, final Expressie gevondenExpressie,
                                     final Context context)
    {
        return gevondenExpressie.getType(context).equals(verwachtType)
            || gevondenExpressie.getType(context).equals(ExpressieType.ONBEKEND_TYPE)
            || gevondenExpressie.isNull(context);
    }

    /**
     * Controleert of de lijst met argumenten bestaat uit argumenten van bepaalde types.
     *
     * @param argumenten     Lijst met argumenten.
     * @param verwachteTypes De verwachte types in de argumenten.
     * @return TRUE als er precies voldoende argumenten zijn die daarnaast voldoen aan de verwachte types.
     */
    private static boolean checkArgumenten(final List<Expressie> argumenten,
                                             final ExpressieType... verwachteTypes)
    {
        boolean result = false;
        if (argumenten != null && argumenten.size() == verwachteTypes.length) {
            int i = 0;
            boolean mismatch = false;
            while (i < verwachteTypes.length && !mismatch) {
                mismatch = !checkType(verwachteTypes[i], argumenten.get(i), null);
                i++;
            }
            result = !mismatch;
        }
        return result;
    }
}

