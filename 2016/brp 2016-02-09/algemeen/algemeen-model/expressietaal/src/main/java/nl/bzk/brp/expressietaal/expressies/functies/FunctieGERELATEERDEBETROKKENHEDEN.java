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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Representeert de functie GERELATEERDE_BETROKKENHEDEN(P,ROL,REL,ROLB). De functie geeft de betrokkenheden van
 * gerelateerden van persoon P in een lijst terug.
 * Als ROL niet leeg is, worden alleen relaties verwerkt waarin persoon P de rol ROL heeft.
 * Als REL niet leeg is, worden alleen gerelateerden verwerkt die via relatie met type REL worden bereikt.
 * Als ROLB niet leeg is, worden alleen gerelateerden verwerkt die een rol ROLB in hun betrokkenheid bij relatie REL
 * vervullen.
 */
public final class FunctieGERELATEERDEBETROKKENHEDEN implements Functieberekening {

    private static final Signatuur SIGNATUUR = new SignatuurOptie(
        new SimpeleSignatuur(),
        new SimpeleSignatuur(ExpressieType.PERSOON),
        new SimpeleSignatuur(ExpressieType.STRING),
        new SimpeleSignatuur(ExpressieType.PERSOON, ExpressieType.STRING),
        new SimpeleSignatuur(ExpressieType.STRING, ExpressieType.STRING),
        new SimpeleSignatuur(ExpressieType.PERSOON, ExpressieType.STRING, ExpressieType.STRING),
        new SimpeleSignatuur(ExpressieType.STRING, ExpressieType.STRING, ExpressieType.STRING),
        new SimpeleSignatuur(ExpressieType.PERSOON, ExpressieType.STRING, ExpressieType.STRING,
            ExpressieType.STRING)
    );

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        final int vierArgumenten = 4;
        final int drieArgumenten = 3;
        final int eersteArgument = 0;
        final int tweedeArgument = 1;
        final int derdeArgument = 2;
        final int vierdeArgument = 3;
        List<Expressie> result = null;

        if (argumenten.size() <= vierArgumenten) {
            final List<Expressie> volledigeArgumenten = new ArrayList<Expressie>();
            Expressie persoonArgument = null;
            Expressie rolArgument = new StringLiteralExpressie("");
            Expressie relatieTypeArgument = new StringLiteralExpressie("");
            Expressie rolGerelateerdeArgument = new StringLiteralExpressie("");

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
                }
            } else if (argumenten.size() == 2) {
                if (checkArgumenten(argumenten, ExpressieType.PERSOON, ExpressieType.STRING)
                    || checkArgumenten(argumenten, ExpressieType.ONBEKEND_TYPE, ExpressieType.STRING))
                {
                    persoonArgument = argumenten.get(eersteArgument);
                    rolArgument = argumenten.get(tweedeArgument);
                } else if (checkArgumenten(argumenten, ExpressieType.STRING, ExpressieType.STRING)) {
                    persoonArgument = new VariabeleExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT);
                    rolArgument = argumenten.get(eersteArgument);
                    relatieTypeArgument = argumenten.get(tweedeArgument);
                }

            } else if (argumenten.size() == drieArgumenten) {
                if (checkArgumenten(argumenten, ExpressieType.PERSOON, ExpressieType.STRING,
                    ExpressieType.STRING))
                {
                    persoonArgument = argumenten.get(eersteArgument);
                    rolArgument = argumenten.get(tweedeArgument);
                    relatieTypeArgument = argumenten.get(derdeArgument);
                } else if (checkArgumenten(argumenten, ExpressieType.STRING, ExpressieType.STRING,
                    ExpressieType.STRING))
                {
                    persoonArgument = new VariabeleExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT);
                    rolArgument = argumenten.get(eersteArgument);
                    relatieTypeArgument = argumenten.get(tweedeArgument);
                    rolGerelateerdeArgument = argumenten.get(derdeArgument);
                }
            } else if (checkArgumenten(argumenten, ExpressieType.PERSOON, ExpressieType.STRING, ExpressieType.STRING,
                ExpressieType.STRING))
            {
                persoonArgument = argumenten.get(eersteArgument);
                rolArgument = argumenten.get(tweedeArgument);
                relatieTypeArgument = argumenten.get(derdeArgument);
                rolGerelateerdeArgument = argumenten.get(vierdeArgument);
            }

            if (persoonArgument != null) {
                volledigeArgumenten.add(persoonArgument);
                volledigeArgumenten.add(rolArgument);
                volledigeArgumenten.add(relatieTypeArgument);
                volledigeArgumenten.add(rolGerelateerdeArgument);

                result = volledigeArgumenten;
            }
        } else {
            result = argumenten;
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
        final int derdeArgument = 2;
        final int vierdeArgument = 3;

        Expressie result = null;
        final Expressie argument = argumenten.get(eersteArgument);

        SoortBetrokkenheid soortBetrokkenheid = null;
        SoortRelatie soortRelatie = null;
        SoortBetrokkenheid soortBetrokkenheidGerelateerde = null;

        String rolStr = argumenten.get(tweedeArgument).alsString();
        if (rolStr.length() > 0) {
            try {
                soortBetrokkenheid = SoortBetrokkenheid.valueOf(rolStr);
            } catch (IllegalArgumentException e) {
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        }

        final String soortRelatieStr = argumenten.get(derdeArgument).alsString();
        if (soortRelatieStr.length() > 0) {
            try {
                soortRelatie = SoortRelatie.valueOf(soortRelatieStr);
            } catch (IllegalArgumentException e) {
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        }

        rolStr = argumenten.get(vierdeArgument).alsString();
        if (rolStr.length() > 0) {
            try {
                soortBetrokkenheidGerelateerde = SoortBetrokkenheid.valueOf(rolStr);
            } catch (IllegalArgumentException e) {
                result = new FoutExpressie(EvaluatieFoutCode.INCORRECTE_EXPRESSIE);
            }
        }

        if (result == null) {
            final BrpObject rootObject = ((BrpObjectExpressie) argument).getBrpObject();
            if (rootObject instanceof Persoon) {
                result = bepaalGerelateerdeBetrokkenheden((Persoon) rootObject, soortBetrokkenheid,
                    soortRelatie,
                    soortBetrokkenheidGerelateerde);
            } else if (rootObject instanceof PersoonHisVolledig) {
                result = bepaalGerelateerdeBetrokkenheden((PersoonHisVolledig) rootObject, soortBetrokkenheid,
                    soortRelatie, soortBetrokkenheidGerelateerde);
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
     * @param soortRelatie                   Soort relatie, of NULL.
     * @param soortBetrokkenheidGerelateerde Rol van de gerelateerde in de relatie, of NULL.
     * @return Alle gerelateerde betrokkenheden die voldoen aan de zoekparameters.
     */
    private Expressie bepaalGerelateerdeBetrokkenheden(final Persoon persoon,
                                                       final SoortBetrokkenheid soortBetrokkenheid,
                                                       final SoortRelatie soortRelatie,
                                                       final SoortBetrokkenheid soortBetrokkenheidGerelateerde)
    {
        final List<Expressie> values = new ArrayList<Expressie>();
        final Collection<? extends Betrokkenheid> gerelateerdeBetrokkenheden =
            FilterUtils.getGerelateerdeBetrokkenheden(persoon, soortBetrokkenheid, soortRelatie,
                soortBetrokkenheidGerelateerde);

        for (Betrokkenheid gerelateerdeBetrokkenheid : gerelateerdeBetrokkenheden) {
            values.add(new BrpObjectExpressie(gerelateerdeBetrokkenheid, ExpressieType.BETROKKENHEID));
        }
        return new LijstExpressie(values);
    }

    /**
     * Bepaal de gerelateerde betrokkenheden van een PersoonHisVolledig-object.
     *
     * @param persoon                        Persoon.
     * @param soortBetrokkenheid             Rol van de persoon in de relatie, of NULL.
     * @param soortRelatie                   Soort relatie, of NULL.
     * @param soortBetrokkenheidGerelateerde Rol van de gerelateerde in de relatie, of NULL.
     * @return Alle gerelateerde betrokkenheden die voldoen aan de zoekparameters.
     */
    private Expressie bepaalGerelateerdeBetrokkenheden(final PersoonHisVolledig persoon,
                                                       final SoortBetrokkenheid soortBetrokkenheid,
                                                       final SoortRelatie soortRelatie,
                                                       final SoortBetrokkenheid soortBetrokkenheidGerelateerde)
    {
        final List<Expressie> values = new ArrayList<Expressie>();
        final Collection<? extends BetrokkenheidHisVolledig> gerelateerdeBetrokkenheden =
            FilterUtils.getGerelateerdeBetrokkenheden(persoon, soortBetrokkenheid, soortRelatie,
                soortBetrokkenheidGerelateerde);

        for (BetrokkenheidHisVolledig gerelateerdeBetrokkenheid : gerelateerdeBetrokkenheden) {
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
    protected static boolean checkArgumenten(final List<Expressie> argumenten,
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

