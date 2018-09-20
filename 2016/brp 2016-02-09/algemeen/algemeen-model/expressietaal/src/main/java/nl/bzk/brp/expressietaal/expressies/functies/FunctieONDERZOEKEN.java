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
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SignatuurOptie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.util.FilterUtils;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Onderzoek;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Representeert de functie ONDERZOEKEN(P). De functie geeft de onderzoeken van
 * van persoon P in een lijst terug.
 */
public final class FunctieONDERZOEKEN implements Functieberekening {

    private static final Signatuur SIGNATUUR = new SignatuurOptie(
        new SimpeleSignatuur(),
        new SimpeleSignatuur(ExpressieType.PERSOON),
        new SimpeleSignatuur(ExpressieType.STRING)
    );

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        final int eersteArgument = 0;
        List<Expressie> result = null;

        final List<Expressie> volledigeArgumenten = new ArrayList<>();
        Expressie persoonArgument = null;

        if (argumenten.isEmpty()) {
            persoonArgument = new VariabeleExpressie(ExpressieTaalConstanten.DEFAULT_OBJECT);
        } else if (argumenten.size() == 1) {
            persoonArgument = argumenten.get(eersteArgument);
        } else {
            result = argumenten;
        }

        if (persoonArgument != null) {
            volledigeArgumenten.add(persoonArgument);

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
        return ExpressieType.ONDERZOEK;
    }

    @Override
    public boolean evalueerArgumenten() {
        return true;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {

        Expressie result = null;
        if (argumenten != null && !argumenten.isEmpty()) {
            final Expressie argument = argumenten.get(0);
            final BrpObject rootObject = ((BrpObjectExpressie) argument).getBrpObject();
            if (rootObject instanceof Persoon) {
                result = bepaalOnderzoeken((Persoon) rootObject);
            } else if (rootObject instanceof PersoonHisVolledig) {
                result = bepaalOnderzoeken((PersoonHisVolledig) rootObject);
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
     * @return Alle gerelateerde betrokkenheden die voldoen aan de zoekparameters.
     */
    private Expressie bepaalOnderzoeken(final Persoon persoon)
    {
        final List<Expressie> values = new ArrayList<>();
        for (final Onderzoek onderzoek : FilterUtils.getOnderzoeken(persoon)) {
            values.add(new BrpObjectExpressie(onderzoek, ExpressieType.ONDERZOEK));
        }
        return new LijstExpressie(values);
    }

    /**
     * Bepaal de onderzoeken van een PersoonHisVolledig-object.
     *
     * @param persoon                        Persoon.
     * @return Alle onderzoeken binnen een persoon.
     */
    private Expressie bepaalOnderzoeken(final PersoonHisVolledig persoon)
    {
        final List<Expressie> values = new ArrayList<>();
        for (final OnderzoekHisVolledig onderzoek : FilterUtils.getOnderzoeken(persoon)) {
            values.add(new BrpObjectExpressie(onderzoek, ExpressieType.ONDERZOEK));
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

