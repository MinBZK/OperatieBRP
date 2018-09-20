/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.ExpressieType;

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
    void voegToe(final String naam, final ExpressieAttribuut attribute);

    /**
     * Voeg een symbol toe aan de symbol table.
     *
     * @param naam      Naam van het symbol.
     * @param attribute De groep waarop de string wordt afgebeeld.
     */
    void voegToe(final String naam, final ExpressieGroep attribute);

    /**
     * Zoek een symbol, dat behoort tot een geïndexeerd attribuut, op basis van een naam.
     *
     * @param naam       Syntax van het te zoeken symbool.
     * @param parentType Objecttype waartoe het te zoeken symbool (attribuut) behoort.
     * @return Het gevonden symbol, anders null.
     */
    ExpressieAttribuut zoekSymbool(final String naam, final ExpressieType parentType);

    /**
     * Zoek een symbol, dat behoort tot een geïndexeerde groep, op basis van een naam.
     *
     * @param naam       Syntax van het te zoeken symbool.
     * @param type Objecttype waartoe het te zoeken symbool (groep) behoort.
     * @return Het gevonden symbol, anders null.
     */
    ExpressieGroep zoekGroepSymbool(final String naam, final ExpressieType type);
}
