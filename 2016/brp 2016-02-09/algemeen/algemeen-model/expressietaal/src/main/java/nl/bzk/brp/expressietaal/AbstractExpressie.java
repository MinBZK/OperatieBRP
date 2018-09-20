/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Implementatie van Expressie-interface als abstract class. De meeste methoden uit Expressie zijn voorzien van een default implementatie. In gevallen dat
 * die voor een specifieke subclass niet geldt of voldoet, geeft de subclass een alternatieve implementatie.
 */
public abstract class AbstractExpressie implements Expressie {

    /**
     * Standaard prioriteit (precedence/volgorde) van literals in een expressie.
     */
    protected static final int PRIORITEIT_LITERAL = 120;
    /**
     * Standaard prioriteit (precedence/volgorde) van gelijkheidsoperator in een expressie.
     */
    protected static final int PRIORITEIT_GELIJKHEIDSOPERATOR = 60;
    /**
     * Standaard prioriteit (precedence/volgorde) van vergelijkingsoperator in een expressie.
     */
    protected static final int PRIORITEIT_VERGELIJKINGSOPERATOR = 70;
    /**
     * Standaard prioriteit (precedence/volgorde) van logische 'EN' operator in een expressie.
     */
    protected static final int PRIORITEIT_LOGISCHE_EN = 30;
    /**
     * Standaard prioriteit (precedence/volgorde) van logische 'OF' operator in een expressie.
     */
    protected static final int PRIORITEIT_LOGISCHE_OF = 20;
    /**
     * Standaard prioriteit (precedence/volgorde) van logische 'NIET' operator in een expressie.
     */
    protected static final int PRIORITEIT_LOGISCHE_NIET = 80;
    /**
     * Standaard prioriteit (precedence/volgorde) van '+' operator in een expressie.
     */
    protected static final int PRIORITEIT_PLUS = 90;
    /**
     * Standaard prioriteit (precedence/volgorde) van '-' operator  in een expressie.
     */
    protected static final int PRIORITEIT_MINUS = 90;
    /**
     * Standaard prioriteit (precedence/volgorde) van 'inverse' operator in een expressie.
     */
    protected static final int PRIORITEIT_INVERSE = 100;
    /**
     * Standaard prioriteit (precedence/volgorde) van 'bevat' operator in een expressie.
     */
    protected static final int PRIORITEIT_BEVAT = 40;
    /**
     * Standaard prioriteit (precedence/volgorde) van 'waarbij' operator in een expressie.
     */
    protected static final int PRIORITEIT_WAARBIJ = 0;

    @Override
    public abstract ExpressieType getType(final Context context);

    @Override
    public abstract Expressie evalueer(final Context context);

    @Override
    public abstract int getPrioriteit();

    @Override
    public final boolean isFout() {
        return false;
    }

    @Override
    public abstract boolean isConstanteWaarde();

    @Override
    public final boolean isConstanteWaarde(final ExpressieType expressieType) {
        return isConstanteWaarde(expressieType, null);
    }

    @Override
    public final boolean isConstanteWaarde(final ExpressieType expressieType, final Context context) {
        return !isFout() && isConstanteWaarde() && getType(context) == expressieType;
    }

    @Override
    public final boolean isNull() {
        return isNull(null);
    }

    @Override
    public final boolean isNull(final Context context) {
        return getType(context) == ExpressieType.NULL;
    }

    @Override
    public abstract boolean isVariabele();

    @Override
    public final boolean isLijstExpressie() {
        return getType(null) == ExpressieType.LIJST;
    }

    @Override
    public abstract ExpressieType bepaalTypeVanElementen(final Context context);

    @Override
    public abstract int aantalElementen();

    @Override
    public abstract Iterable<? extends Expressie> getElementen();

    @Override
    public abstract Expressie getElement(final int index);

    @Override
    public abstract boolean alsBoolean();

    @Override
    public abstract int alsInteger();

    @Override
    public abstract long alsLong();

    @Override
    public abstract String alsString();

    @Override
    public abstract Attribuut getAttribuut();

    @Override
    public abstract Groep getGroep();

    @Override
    public abstract boolean bevatOngebondenVariabele(final String id);

    @Override
    public abstract Expressie optimaliseer(final Context context);

    @Override
    public final String toString() {
        return stringRepresentatie();
    }

    /**
     * Geeft een stringrepresentatie van de expressie.
     *
     * @return Een stringrepresentatie van de expressie
     */
    protected abstract String stringRepresentatie();

    /**
     * Geeft een Expressie-object terug, ook als het argument null is. Als het argument null (pointer) is, is het resultaat een NULL-expressie
     * (Expressie).
     *
     * @param expressie Expressie of null.
     * @return Expressie-object.
     */
    protected static Expressie veiligeExpressie(final Expressie expressie) {
        final Expressie veilig;
        if (expressie == null) {
            veilig = NullValue.getInstance();
        } else {
            veilig = expressie;
        }
        return veilig;
    }

    /**
     * Standaard implementatie voor het ophalen van een element uit een expressie die geen lijst is.
     *
     * @param index Index van het gevraagde element.
     * @return Element of NULL-expressie.
     */
    protected final Expressie getElementUitNietLijstExpressie(final int index) {
        final int indexEersteElement = 0;
        final Expressie element;
        if (index == indexEersteElement) {
            element = this;
        } else {
            element = NullValue.getInstance();
        }
        return element;
    }

    /**
     * Maakt een lijst met de expressie als element.
     *
     * @return Lijst met de expressie als element.
     */
    protected final Iterable<Expressie> maakLijstMetExpressie() {
        final List<Expressie> elementen = new ArrayList<>();
        elementen.add(this);
        return Collections.unmodifiableList(elementen);
    }
}
