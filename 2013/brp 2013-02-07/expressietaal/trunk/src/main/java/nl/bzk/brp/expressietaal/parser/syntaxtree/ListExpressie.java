/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.expressietaal.symbols.Characters;

/**
 * Representeert een lijstexpressie.
 */
public class ListExpressie extends AbstractExpressie {

    private final Collection<Expressie> elements;
    private final ExpressieType elementType;

    /**
     * Constructor. Maak een lege ongetypeerde lijst van expressies.
     */
    public ListExpressie() {
        this.elements = new ArrayList<Expressie>();
        this.elementType = ExpressieType.UNKNOWN;
    }

    /**
     * Constructor. Maak een getypeerde lijst van expressies.
     *
     * @param elementType Type van de elementen.
     */
    public ListExpressie(final ExpressieType elementType) {
        this.elements = new ArrayList<Expressie>();
        this.elementType = elementType;
    }

    /**
     * Constructor. Maak een lijst gevuld met de meegeleverde expressies.
     *
     * @param elements Elementen van de lijst.
     */
    public ListExpressie(final List<Expressie> elements) {
        this.elements = elements;
        if (elements.size() == 0) {
            elementType = ExpressieType.UNKNOWN;
        } else {
            ExpressieType type0 = elements.get(0).getType();
            boolean same = true;
            Iterator<Expressie> iterator = elements.iterator();
            while (same && iterator.hasNext()) {
                Expressie element = iterator.next();
                same = (element.getType() == type0);
            }
            if (same) {
                elementType = type0;
            } else {
                elementType = ExpressieType.UNKNOWN;
            }
        }
    }

    public final Collection<Expressie> getElements() {
        return elements;
    }

    @Override
    public final boolean isVariabele() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isAttribuut() {
        return false;
    }

    @Override
    public boolean isRootObject() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isLijstExpressie() {
        return true;
    }

    @Override
    public final ExpressieType getTypeElementen() {
        return elementType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        String result = String.valueOf(Characters.LIJST_START);

        boolean first = true;

        for (Expressie element : elements) {
            if (!first) {
                result = result.concat(String.valueOf(Characters.ELEMENT_SCHEIDINGSTEKEN) + " ");
            }
            result = result.concat(element.alsLeesbareString());
            first = false;
        }

        result += String.valueOf(Characters.LIJST_EIND);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        String result = String.valueOf(Characters.LIJST_START);

        boolean first = true;

        for (Expressie element : elements) {
            if (!first) {
                result = result.concat(String.valueOf(Characters.ELEMENT_SCHEIDINGSTEKEN));
            }
            result = result.concat(element.alsFormeleString());
            first = false;
        }

        result += String.valueOf(Characters.LIJST_EIND);
        return result;
    }

    @Override
    public boolean isConstanteWaarde() {
        for (Expressie element : elements) {
            if (!element.isConstanteWaarde()) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie optimaliseer() {
        List<Expressie> newElements = new ArrayList<Expressie>();

        for (Expressie element : elements) {
            newElements.add(element.optimaliseer());
        }

        return new ListExpressie(newElements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final EvaluatieResultaat evalueer(final Context context) {
        List<Expressie> newElements = new ArrayList<Expressie>();
        for (Expressie element : elements) {
            EvaluatieResultaat evalElement = element.evalueer(context);
            if (!evalElement.succes()) {
                return new EvaluatieResultaat(evalElement.getFout().getFoutCode());
            }
            newElements.add(evalElement.getExpressie());
        }
        return new EvaluatieResultaat(new ListExpressie(newElements));
    }

    @Override
    public final boolean includes(final Attributes attribuut) {
        for (Expressie element : elements) {
            if (element.includes(attribuut)) {
                return true;
            }
        }
        return false;
    }
}
