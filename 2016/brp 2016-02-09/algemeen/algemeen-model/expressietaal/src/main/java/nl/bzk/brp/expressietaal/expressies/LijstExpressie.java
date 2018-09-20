/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import nl.bzk.brp.expressietaal.AbstractExpressie;
import nl.bzk.brp.expressietaal.Characters;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.operatoren.GelijkheidsoperatorExpressie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Representeert een lijstexpressie.
 */
public class LijstExpressie extends AbstractExpressie {

    private final List<Expressie> elementen;

    /**
     * Constructor. Maak een lege ongetypeerde lijst van expressies.
     */
    public LijstExpressie() {
        super();
        this.elementen = new ArrayList<>();
    }

    /**
     * Constructor. Maak een lijst gevuld met de meegeleverde expressies.
     *
     * @param initieleElementen Elementen van de lijst.
     */
    public LijstExpressie(final Iterable<? extends Expressie> initieleElementen) {
        super();
        elementen = new ArrayList<>();
        if (initieleElementen != null) {
            // Plaats alle initiële elementen in de lijst.
            for (final Expressie e : initieleElementen) {
                elementen.add(e);
            }
        }
    }

    @Override
    public final ExpressieType bepaalTypeVanElementen(final Context context) {
        ExpressieType elementType = ExpressieType.ONBEKEND_TYPE;
        if (!elementen.isEmpty()) {
            // Als alle elementen hetzelfde type hebben, is dat type het resultaat van deze functie.
            // Zo niet, dan is het type onbepaald (UNKNOWN).
            final ExpressieType type0 = elementen.get(0).getType(context);
            boolean same = true;
            for (final Expressie element : elementen) {
                same = element.getType(null) == type0;
                if (!same) {
                    break;
                }
            }

            if (same) {
                elementType = type0;
            }
        }

        return elementType;
    }

    @Override
    public final List<Expressie> getElementen() {
        return Collections.unmodifiableList(elementen);
    }

    @Override
    public final Expressie getElement(final int index) {
        Expressie element = NullValue.getInstance();
        if (index >= 0 && index < elementen.size()) {
            element = elementen.get(index);
        }
        return element;
    }

    @Override
    public final ExpressieType getType(final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public final Expressie evalueer(final Context context) {
        Expressie resultaat = null;
        final List<Expressie> newElements = new ArrayList<>();
        for (final Expressie element : getElementen()) {
            final Expressie evalElement = element.evalueer(context);
            if (evalElement.isFout()) {
                resultaat = evalElement;
                break;
            }
            newElements.add(evalElement);
        }
        if (resultaat == null) {
            resultaat = new LijstExpressie(newElements);
        }
        return resultaat;
    }

    @Override
    public final int getPrioriteit() {
        return PRIORITEIT_LITERAL;
    }

    @Override
    public final boolean isConstanteWaarde() {
        boolean alleElementenConstant = true;
        // Een lijst is een 'constante waarde' als alle elementen constant zijn; anders niet.
        for (final Expressie element : getElementen()) {
            if (!element.isConstanteWaarde()) {
                alleElementenConstant = false;
                break;
            }
        }
        return alleElementenConstant;
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    @Override
    public final boolean alsBoolean() {
        // Default boolean-waarde voor een expressie is FALSE.
        return false;
    }

    @Override
    public final int alsInteger() {
        // Default integer-waarde voor een expressie is 0.
        return 0;
    }

    @Override
    public final long alsLong() {
        return 0L;
    }

    @Override
    public final String alsString() {
        return stringRepresentatie();
    }

    @Override
    public final Attribuut getAttribuut() {
        return null;
    }

    @Override
    public final Groep getGroep() {
        return null;
    }

    @Override
    public final int aantalElementen() {
        return elementen.size();
    }

    @Override
    public final boolean bevatOngebondenVariabele(final String id) {
        boolean variabeleGevonden = false;
        // Een lijst bevat de ongebonden variabele id als die variabele ongebonden voorkomt in een van de
        // element-expressies.
        for (final Expressie element : getElementen()) {
            if (element.bevatOngebondenVariabele(id)) {
                variabeleGevonden = true;
                break;
            }
        }
        return variabeleGevonden;
    }

    @Override
    public final Expressie optimaliseer(final Context context) {
        // Het optimaliseren van een lijst bestaat uit het optimaliseren van de elementen.
        final List<Expressie> newElements = new ArrayList<>();
        for (final Expressie element : getElementen()) {
            newElements.add(element.optimaliseer(context));
        }
        return new LijstExpressie(newElements);
    }

    @Override
    protected final String stringRepresentatie() {
        final StringBuilder builder = new StringBuilder();
        builder.append(Characters.LIJST_START);
        boolean first = true;
        for (final Expressie element : getElementen()) {
            if (!first) {
                builder.append(Characters.LIJST_SCHEIDINGSTEKEN);
                builder.append(' ');
            }
            builder.append(element.toString());
            first = false;
        }
        builder.append(Characters.LIJST_EIND);
        return builder.toString();
    }

    /**
     * Sorteert de elementen van de lijst met behulp van de comparator.
     *
     * @param comparator Comparator voor sortering.
     * @param recursief  TRUE als de elementen van de lijst ook gesorteerde moeten worden (recursief), anders FALSE.
     */
    public final void sorteer(final Comparator<Expressie> comparator, final boolean recursief) {
        if (recursief) {
            for (final Expressie e : elementen) {
                if (e instanceof LijstExpressie) {
                    ((LijstExpressie) e).sorteer(comparator, recursief);
                }
            }
        }
        Collections.sort(elementen, comparator);
    }

    /**
     * Mogelijke resultaten van de vergelijkingsfunctie vergelijkLijsten.
     */
    public enum Vergelijkingsresultaat {
        /**
         * De lijsten hebben dezelfde elementen en zijn dus 'gelijk'.
         */
        GELIJK,
        /**
         * Alle elementen uit de eerste lijst komen voor in de tweede lijst; de tweede lijst heeft elementen die niet voorkomen in de eerste.
         */
        KLEINER,
        /**
         * Alle elementen uit de tweede lijst komen voor in de eerste lijst; de eerste lijst heeft elementen die niet voorkomen in de tweede.
         */
        GROTER,
        /**
         * Beide lijsten hebben elementen die niet in de andere lijst voorkomen. De lijsten heten 'ongelijk'.
         */
        ONGELIJK
    }

    /**
     * Vergelijkt twee lijsten, waarbij lijsten ongeordend zijn.
     *
     * @param lijst1               De eerste lijst.
     * @param lijst2               De tweede lijst.
     * @param vergelijkNullWaarden Moeten null-waarden als 'gewone' waarden vergeleken worden? Als TRUE dan levert (A = NULL) ONWAAR op; als FALSE dan
     *                             levert (A = NULL) NULL op.
     * @param context              De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen context is.
     * @return Als de lijsten gelijk zijn (dwz precies dezelfde elementen bevatten) is het resultaat GELIJK, anders als alle elementen van lijst1 in lijst2
     * zitten is het resultaat KLEINER, als alle elementen van lijst2 in lijst1 zitten is het resultaat GROTER, in andere gevallen is het resultaat
     * ONGELIJK (kenmerk van deze laatste situatie is dat de doorsnede van de twee lijsten niet leeg is).
     */
    public static Vergelijkingsresultaat vergelijkLijsten(final Expressie lijst1, final Expressie lijst2,
        final boolean vergelijkNullWaarden, final Context context)
    {
        final List<Expressie> lijstElementen2 = new ArrayList<>();
        for (final Expressie e : lijst2.getElementen()) {
            lijstElementen2.add(e);
        }
        boolean elementNietInLijst2 = false;

        for (final Expressie element1 : lijst1.getElementen()) {
            Expressie gevondenElement = null;
            for (final Expressie element2 : lijstElementen2) {
                boolean elementenGelijk = vergelijkNullWaarden && element1.isNull(context) && element2.isNull(context);

                if (!elementenGelijk) {
                    elementenGelijk = new GelijkheidsoperatorExpressie(element1, element2).evalueer(context).alsBoolean();
                }

                if (elementenGelijk) {
                    gevondenElement = element2;
                    break;
                }
            }

            if (gevondenElement != null) {
                lijstElementen2.remove(gevondenElement);
            } else {
                elementNietInLijst2 = true;
            }
        }

        return vergelijkResultaten(lijstElementen2, elementNietInLijst2);
    }

    private static Vergelijkingsresultaat vergelijkResultaten(final List<Expressie> lijstElementen2, final boolean elementNietInLijst2) {
        final Vergelijkingsresultaat resultaat;
        if (elementNietInLijst2) {
            // Er is een element in lijst1 dat niet in lijst2 voorkomt.
            if (lijstElementen2.isEmpty()) {
                // Alle elementen uit lijst2 zitten in lijst1. Lijst1 is dus groter dan lijst2.
                resultaat = Vergelijkingsresultaat.GROTER;
            } else {
                // Er zijn elementen uit lijst2 die niet in lijst1 voorkomen.
                resultaat = Vergelijkingsresultaat.ONGELIJK;
            }
        } else {
            if (lijstElementen2.isEmpty()) {
                // Alle elementen uit lijst1 zitten in lijst2 en er zitten geen elementen in lijst2 die niet in lijst1
                // zitten.
                resultaat = Vergelijkingsresultaat.GELIJK;
            } else {
                // Alle elementen uit lijst1 zitten in lijst2. Maar lijst2 heeft nog meer elementen.
                resultaat = Vergelijkingsresultaat.KLEINER;
            }
        }
        return resultaat;
    }

    /**
     * Vergelijkt twee lijsten, waarbij lijsten ongeordend zijn.
     *
     * @param lijst1  De eerste lijst.
     * @param lijst2  De tweede lijst.
     * @param context De gedefinieerde symbolische namen (identifiers) afgebeeld op hun waarde; of NULL als er geen context is.
     * @return Als de lijsten gelijk zijn (dwz precies dezelfde elementen bevatten) is het resultaat GELIJK, anders als alle elementen van lijst1 in lijst2
     * zitten is het resultaat KLEINER, als alle elementen van lijst2 in lijst1 zitten is het resultaat GROTER, in andere gevallen is het resultaat
     * ONGELIJK (kenmerk van deze laatste situatie is dat de doorsnede van de twee lijsten niet leeg is).
     */
    public static Vergelijkingsresultaat vergelijkLijsten(final Expressie lijst1, final Expressie lijst2,
        final Context context)
    {
        return vergelijkLijsten(lijst1, lijst2, true, context);
    }

}
