/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.Dictionary;
import java.util.Hashtable;

import nl.bzk.brp.expressietaal.parser.syntaxtree.ExpressieType;

/**
 * Factory voor een symbol table die gebaseerd is op het BMR.
 */
public abstract class BmrSymbolTableFactory {

    /**
     * Implementatie van SymbolTable.
     */
    private static class SymbolTableImpl implements SymbolTable {

        /**
         * Representeert symbolen. Een symbool is een naam die afgebeeld kan worden op een attribuut. Omdat voor
         * geïndexeerde attributen, zoals Adressen en Voornamen, dezelfde (sub)attributen kunnen voorkomen, moet ook
         * het 'overkoepelende' attribuut worden meegegeven.
         */
        private static class Symbol {
            private final String syntax;
            private final ExpressieType objectType;
            private final Attributes parentIndex;
            private final boolean wildcard;

            /**
             * Maakt een symbool met gegeven syntax.
             *
             * @param syntax      Syntax van het symbool.
             * @param objectType  Objecttype waartoe het symbool (attribuut) behoort.
             * @param parentIndex Geïndexeerde attribuut waartoe het symbool behoort of NULL, indien niet bestaat.
             * @param wildcard    TRUE als het symbool alle andere symbolen met dezelfde naam (maar
             *                    eventueel andere parent) moet matchen, anders FALSE.
             */
            public Symbol(final String syntax, final ExpressieType objectType, final Attributes parentIndex,
                          final boolean wildcard)
            {
                this.syntax = syntax;
                this.objectType = objectType;
                this.parentIndex = parentIndex;
                this.wildcard = wildcard;
            }

            @Override
            public boolean equals(final Object o) {
                if (o instanceof Symbol) {
                    Symbol other = (Symbol) o;
                    if (wildcard || other.wildcard) {
                        return syntax.equalsIgnoreCase(other.syntax);
                    } else {
                        return syntax.equalsIgnoreCase(other.syntax) && objectType == other.objectType
                                && parentIndex == other.parentIndex;
                    }
                } else {
                    return false;
                }
            }

            @Override
            public int hashCode() {
                return syntax.hashCode();
            }
        }

        private final Dictionary<Symbol, Attributes> table;

        /**
         * Constructor.
         */
        public SymbolTableImpl() {
            table = new Hashtable<Symbol, Attributes>();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addSymbol(final String naam, final Attributes attribute) {
            Symbol newSymbol = new Symbol(naam.toUpperCase(), attribute.getObjectType(),
                    attribute.getIndexedAttribute(), false);
            table.put(newSymbol, attribute);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Attributes lookupSymbol(final String naam, final ExpressieType objectType,
                                       final Attributes parentIndex)
        {
            Symbol lookup = new Symbol(naam.toUpperCase(), objectType, parentIndex, false);
            return table.get(lookup);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Attributes lookupSymbolWithWildcard(final String naam) {
            Symbol lookup = new Symbol(naam.toUpperCase(), null, null, true);
            return table.get(lookup);
        }
    }

    /**
     * Constructor. De constructor is private omdat het een static factory class is.
     */
    private BmrSymbolTableFactory() {

    }

    /**
     * Creeer de symbol table voor de attributen. Hierin zijn alle attributen en eventuele afkortingen opgenomen.
     *
     * @return Symbol table met attributen.
     */
    public static SymbolTable createSymbolTable() {
        SymbolTable result = new SymbolTableImpl();

        for (Attributes a : Attributes.values()) {
            result.addSymbol(a.getSyntax(), a);
        }

        result.addSymbol("identificatienummers.bsn", Attributes.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        result.addSymbol("bsn", Attributes.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER);
        result.addSymbol("identificatienummers.anummer", Attributes.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        result.addSymbol("anummer", Attributes.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);

        return result;
    }
}
