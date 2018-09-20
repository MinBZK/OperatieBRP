/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;

/**
 * Representeert expressies.
 */
public abstract class AbstractExpressie implements Expressie {

    @Override
    public abstract ExpressieType getType();

    @Override
    public final String toString() {
        return alsLeesbareString();
    }

    @Override
    public abstract String alsLeesbareString();

    @Override
    public abstract String alsFormeleString();

    @Override
    public abstract boolean isConstanteWaarde();

    @Override
    public abstract boolean isAttribuut();

    @Override
    public abstract boolean isRootObject();

    @Override
    public abstract boolean isLijstExpressie();

    @Override
    public abstract ExpressieType getTypeElementen();

    @Override
    public abstract Expressie optimaliseer();

    @Override
    public abstract EvaluatieResultaat evalueer(Context context);

    @Override
    public abstract boolean includes(Attributes attribuut);
}
