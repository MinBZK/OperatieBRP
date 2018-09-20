/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.expressietaal.symbols.Characters;

/**
 * Representeert een geïndiceerd attribuut (zonder index).
 */
public class UnqualifiedIndexedAttribuutExpressie extends AbstractLiteralExpressie {

    private final String object;
    private final Attributes indexedAttribuut;

    /**
     * Constructor. Creëert een attribuutverwijzing voor een gegeven object.
     *
     * @param object           De naam van het object.
     * @param indexedAttribuut Het attribuutpad van het benoemde geïndiceerde attribuut.
     */
    public UnqualifiedIndexedAttribuutExpressie(final String object, final Attributes indexedAttribuut) {
        this.object = object.toUpperCase();
        this.indexedAttribuut = indexedAttribuut;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        return String.format("%s.%s%c%c",
                object, indexedAttribuut.getSyntax(), Characters.INDEX_START, Characters.INDEX_EIND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        return String.format("$[%s<%s>.%s%c%c]",
                object, indexedAttribuut.getObjectType().getNaam(), indexedAttribuut.getSyntax(),
                Characters.INDEX_START, Characters.INDEX_EIND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.INDEXED;
    }

    @Override
    public final boolean isRootObject() {
        return false;
    }

    public String getObject() {
        return object;
    }

    public Attributes getIndexedAttribuut() {
        return indexedAttribuut;
    }
}
