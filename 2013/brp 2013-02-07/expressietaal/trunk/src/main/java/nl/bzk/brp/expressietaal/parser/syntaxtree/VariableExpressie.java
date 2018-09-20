/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.model.RootObject;

/**
 * Representeert een variabele (in een expressie).
 */
public class VariableExpressie extends AbstractNonLiteralExpressie {

    private final String identifier;
    private final ExpressieType type;

    /**
     * Constructor.
     *
     * @param identifier Naam van de variabele.
     * @param type       Type van de (waarde van de) variabele.
     */
    public VariableExpressie(final String identifier, final ExpressieType type) {
        this.identifier = identifier.toUpperCase();
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public ExpressieType getType() {
        return type;
    }

    @Override
    public String alsLeesbareString() {
        return identifier;
    }

    @Override
    public String alsFormeleString() {
        return String.format("%%%s<%s>", identifier, type.getNaam());
    }

    @Override
    public boolean isVariabele() {
        return true;
    }

    @Override
    public boolean isAttribuut() {
        return false;
    }

    @Override
    public boolean isLijstExpressie() {
        return false;
    }

    @Override
    public ExpressieType getTypeElementen() {
        return ExpressieType.UNKNOWN;
    }

    @Override
    public Expressie optimaliseer() {
        return this;
    }

    @Override
    public EvaluatieResultaat evalueer(final Context context) {
        RootObject value = context.get(identifier);
        if (value != null) {
            return new EvaluatieResultaat(new RootObjectExpressie(value, getType()));
        } else {
            return new EvaluatieResultaat(this);
        }
    }

    @Override
    public final boolean includes(final Attributes attribuut) {
        return false;
    }
}
