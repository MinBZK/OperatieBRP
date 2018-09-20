/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;

/**
 * Beeldt een string (naam) af op een attribuut. De naam is afkomstig uit een te parsen string.
 */
public interface SymbolTable {
    /**
     * Voeg een symbol toe aan de symbol table.
     *
     * @param naam      Naam van het symbol.
     * @param attribute Het attribuut waarop de string wordt afgebeeld.
     */
    void addSymbol(final String naam, final Attributes attribute);

    /**
     * Zoek een symbol, dat behoort tot een geïndexeerd attribuut, op basis van een naam.
     *
     * @param naam        Syntax van het te zoeken symbool.
     * @param objectType  Objecttype waartoe het te zoeken symbool (attribuut) behoort.
     * @param parentIndex Attribuut waartoe het symbool behoort.
     * @return Het gevonden symbol, anders null.
     */
    Attributes lookupSymbol(final String naam, final ExpressieType objectType, final Attributes parentIndex);

    /**
     * Zoek een symbol op basis van een naam.
     *
     * @param naam Syntax van het te zoeken symbol.
     * @return Het gevonden symbol, anders null.
     */
    Attributes lookupSymbolWithWildcard(final String naam);
}
