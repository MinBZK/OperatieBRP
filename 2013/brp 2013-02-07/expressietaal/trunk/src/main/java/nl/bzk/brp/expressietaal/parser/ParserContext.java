/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;

/**
 * Context voor het vertalen van expressies. De context houdt bij welke identifiers bekend zijn en welk type ze hebben.
 */
public class ParserContext {
    private final List<Identifier> identifiers;

    /**
     * Constructor.
     */
    public ParserContext() {
        identifiers = new ArrayList<Identifier>();
    }

    /**
     * Constructor.
     *
     * @param parentContext Context waarop de nieuwe context gebaseerd moet worden.
     */
    public ParserContext(final ParserContext parentContext) {
        this();
        identifiers.addAll(parentContext.identifiers);
    }

    /**
     * Voegt een identifier toe aan de context.
     *
     * @param identifier Toe te voegen identifier.
     */
    public void addIdentifier(final Identifier identifier) {
        identifiers.add(identifier);
    }

    /**
     * Zoekt de identifier met gegeven syntax op.
     *
     * @param syntax Te zoeken identifier.
     * @return Gevonden identifier, of NULL indien niet gevonden.
     */
    public Identifier lookupIdentifier(final String syntax) {
        Identifier result = null;
        Iterator<Identifier> iterator = identifiers.iterator();
        while (iterator.hasNext() && result == null) {
            Identifier current = iterator.next();
            if (current.getSyntax().equalsIgnoreCase(syntax)) {
                result = current;
            }
        }
        return result;
    }

    /**
     * Zoekt het type van de identifier met gegeven syntax.
     *
     * @param syntax Te zoeken identifier.
     * @return Type van de identifier, of NULL indien niet gevonden.
     */
    public ExpressieType lookupType(final String syntax) {
        Identifier identifier = lookupIdentifier(syntax);
        if (identifier != null) {
            return identifier.getType();
        } else {
            return null;
        }
    }
}
