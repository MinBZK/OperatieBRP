/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.Dictionary;
import java.util.Hashtable;
import nl.bzk.brp.expressietaal.ExpressieType;

/**
 * Factory voor een symbol table die gebaseerd is op het BMR.
 */
public final class BmrSymbolTable {

    private static final SymbolTable INSTANCE = createSymbolTable();

    /**
     * Constructor. De constructor is private omdat het een static factory class is.
     */
    private BmrSymbolTable() {
    }

    /**
     * Geeft de symbol table gebaseerd op het BMR (singleton).
     *
     * @return Symbol table voor expressies.
     */
    public static SymbolTable getInstance() {
        return INSTANCE;
    }

    /**
     * Creeer de symbol table voor de attributen. Hierin zijn alle attributen en eventuele afkortingen opgenomen.
     *
     * @return Symbol table met attributen.
     */
    private static SymbolTable createSymbolTable() {
        final SymbolTable result = new SymbolTableImpl();

        for (final ExpressieAttribuut a : ExpressieAttribuut.values()) {
            result.voegToe(a.getSyntax(), a);
        }

        for (final ExpressieGroep expressieGroep : ExpressieGroep.values()) {
            result.voegToe(expressieGroep.getSyntax(), expressieGroep);
        }

        // Voor enkele attributen is er een verkorte en/of alternatieve syntax beschikbaar. Deze uitzonderingen
        // worden hieronder gedefinieerd.
        result.voegToe("identificatienummers.bsn", ExpressieAttribuut.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        result.voegToe("bsn", ExpressieAttribuut.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        result.voegToe("identificatienummers.anummer",
            ExpressieAttribuut.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        result.voegToe("anummer", ExpressieAttribuut.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);

        return result;
    }

    /**
     * Implementatie van SymbolTable.
     */
    private static class SymbolTableImpl implements SymbolTable {

        private final Dictionary<Symbol, ExpressieAttribuut> table;
        private final Dictionary<Symbol, ExpressieGroep> tableGroepen;

        /**
         * Constructor.
         */
        public SymbolTableImpl() {
            table = new Hashtable<>();
            tableGroepen = new Hashtable<>();
        }

        @Override
        public void voegToe(final String naam, final ExpressieAttribuut attribute) {
            final Symbol newSymbol = new Symbol(naam.toUpperCase(), attribute.getParentType());
            table.put(newSymbol, attribute);
        }

        @Override
        public void voegToe(final String naam, final ExpressieGroep groep) {
            final Symbol newSymbol = new Symbol(naam.toUpperCase(), groep.getType());
            tableGroepen.put(newSymbol, groep);
        }

        @Override
        public ExpressieAttribuut zoekSymbool(final String naam, final ExpressieType parentType) {
            final Symbol lookup = new Symbol(naam.toUpperCase(), parentType);
            return table.get(lookup);
        }

        @Override
        public ExpressieGroep zoekGroepSymbool(final String naam, final ExpressieType type) {
            final Symbol lookup = new Symbol(naam.toUpperCase(), type);
            return tableGroepen.get(lookup);
        }

        /**
         * Representeert symbolen. Een symbool is een naam die afgebeeld kan worden op een attribuut. Omdat voor geïndexeerde attributen, zoals Adressen en
         * Voornamen, dezelfde (sub)attributen kunnen voorkomen, moet ook het 'overkoepelende' attribuut worden meegegeven.
         */
        private static class Symbol {
            private final String        syntax;
            private final ExpressieType parentType;

            /**
             * Maakt een symbool met gegeven syntax.
             *
             * @param aSyntax     Syntax van het symbool.
             * @param aParentType Objecttype waartoe het symbool (attribuut) behoort.
             */
            public Symbol(final String aSyntax, final ExpressieType aParentType) {
                this.syntax = aSyntax;
                this.parentType = aParentType;
            }

            @Override
            public boolean equals(final Object o) {
                boolean gelijk = false;
                if (o instanceof Symbol) {
                    final Symbol other = (Symbol) o;
                    gelijk = syntax.equalsIgnoreCase(other.syntax)
                        && (parentType == other.parentType || parentType == ExpressieType.ONBEKEND_TYPE
                        || other.parentType == ExpressieType.ONBEKEND_TYPE);
                }
                return gelijk;
            }

            @Override
            public int hashCode() {
                return syntax.hashCode();
            }
        }
    }
}
