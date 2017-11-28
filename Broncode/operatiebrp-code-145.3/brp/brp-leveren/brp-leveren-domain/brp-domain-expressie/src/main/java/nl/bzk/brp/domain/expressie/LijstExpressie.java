/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Representeert een lijstexpressie.
 */
public final class LijstExpressie implements Expressie, Iterable<Expressie> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private List<Expressie> elementen = Lists.newArrayList();

    /**
     * Constructor. Maak een lege ongetypeerde lijst van expressies.
     */
    public LijstExpressie() {
        super();
    }

    /**
     * Constructor. Maak een lijst gevuld met de meegeleverde expressies.
     * @param initieleElementen Elementen van de lijst.
     */
    public LijstExpressie(final Iterable<? extends Expressie> initieleElementen) {
        if (initieleElementen != null) {
            // Plaats alle initiële elementen in de lijst.
            for (final Expressie e : initieleElementen) {
                elementen.add(e);
            }
        }
    }

    /**
     * Geeft de expressie als 'platgeslagen' lijst terug.
     * Als de expressie E geen lijst is, is het resultaat {E}.
     * Als de expressie E een lijst {A1, A2, ..., An} is, is het resultaat een concatenatie van de platgeslagen
     * elementen Ai.
     * @return Platgeslagen expressie.
     */
    public LijstExpressie alsPlatteLijst() {
        boolean bevatLijst = false;
        for (Expressie expressie : elementen) {
            if (expressie instanceof LijstExpressie) {
                bevatLijst = true;
                break;
            }
        }
        if (!bevatLijst) {
            return this;
        }

        final List<Expressie> expressieList = Lists.newArrayList();
        for (Expressie expressie : elementen) {
            if (expressie instanceof LijstExpressie) {
                final LijstExpressie flatten = expressie.alsLijst().alsPlatteLijst();
                for (Expressie expressie1 : flatten) {
                    expressieList.add(expressie1);
                }
            } else {
                expressieList.add(expressie);
            }
        }
        this.elementen = expressieList;
        return this;
    }


    /**
     * Geeft de elementen waaruit de expressie bestaat. Als het een lijst is, zijn het de individuele elementen van die
     * lijst, anders de expressie zelf.
     * @return Elementen van de expressie.
     */
    public List<Expressie> getElementen() {
        return Collections.unmodifiableList(elementen);
    }

    /**
     * Geeft het element op de gegeven index terug. Het eerste element heeft index 0.
     * Als de expressie geen lijst is, is het eerste element (index 0) de expressie zelf.
     * @param index Index van element.
     * @return Element of NULL als de index buiten bereik van de lijst is.
     */
    public Expressie getElement(final int index) {
        if (index >= 0 && index < elementen.size()) {
            return elementen.get(index);
        }
        throw new ExpressieRuntimeException("Index bestaat niet: " + index);
    }

    /**
     * Geeft het aantal elementen waar de expressie uit bestaat. Indien de expressie geen lijst is, is het resultaat
     * altijd 1.
     * @return Aantal elementen.
     */
    public int size() {
        return elementen.size();
    }

    /**
     * @return indicatie of de lijst leeg is
     */
    public boolean isEmpty() {
        return elementen.isEmpty();
    }

    @Override
    public Iterator<Expressie> iterator() {
        return elementen.iterator();
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public Expressie evalueer(final Context context) {
        final List<Expressie> newElements = new ArrayList<>();
        for (final Expressie element : elementen) {
            final Expressie evalElement = element.evalueer(context);
            newElements.add(evalElement);
        }
        final Expressie resultaat = new LijstExpressie(newElements);
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("resultaat {}", resultaat);
        }
        return resultaat;
    }

    @Override
    public Prioriteit getPrioriteit() {
        return Prioriteit.PRIORITEIT_LITERAL;
    }

    @Override
    public boolean isConstanteWaarde() {
        boolean alleElementenConstant = true;
        // Een lijst is een 'constante waarde' als alle elementen constant zijn; anders niet.
        for (final Expressie element : elementen) {
            if (!element.isConstanteWaarde()) {
                alleElementenConstant = false;
                break;
            }
        }
        return alleElementenConstant;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("{");
        boolean first = true;
        for (final Expressie element : elementen) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(element.toString());
            first = false;
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * @return de enkele waarde van de lijst
     */
    public Expressie geefEnkeleWaarde() {
        final LijstExpressie lijstExpressie = alsPlatteLijst();
        if (lijstExpressie.elementen.isEmpty()) {
            throw new ExpressieRuntimeException("De lijst bevat geen expressies");
        } else if (lijstExpressie.elementen.size() > 1) {
            throw new ExpressieRuntimeException(String.format("De lijst bevat %d waarden terwijl er één waarde verwacht wordt.",
                    lijstExpressie.elementen.size()));
        }
        return lijstExpressie.getElement(0);
    }

    /**
     * @return indicatie of de waarden van de lijst een gelijk {@link ExpressieType} hebben
     */
    public boolean isHomogeen() {
        if (!isEmpty()) {
            final ExpressieType expressieType = this.iterator().next().getType(null);
            for (Expressie expressie : this) {
                if (expressieType != expressie.getType(null)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Maakt een lijstexpressie met de gegeven waarden, indien dat
     * geen lijst is. Indien het wel een lijst is wordt deze als zodanig
     * teruggegeven.
     * @param expressie een expressie
     * @return een {@link LijstExpressie}
     */
    public static LijstExpressie ensureList(final Expressie expressie) {
        final LijstExpressie lijst;
        if (!(expressie instanceof LijstExpressie)) {
            lijst = new LijstExpressie(Collections.singleton(expressie));
        } else {
            lijst = expressie.alsLijst();
        }
        return lijst;
    }
}
