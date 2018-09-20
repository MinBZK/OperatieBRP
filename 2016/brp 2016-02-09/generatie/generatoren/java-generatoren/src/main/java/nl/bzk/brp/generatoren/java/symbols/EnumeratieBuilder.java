/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.Collection;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaSymbolEnum;
import nl.bzk.brp.generatoren.java.model.JavaSymbolGroepEnum;
import nl.bzk.brp.generatoren.java.model.JavaType;

/**
 * Builder voor een java-enumeratie voor de attributen uit het BMR die gebruikt worden als symbolen in de
 * expressietaal.
 */
public final class EnumeratieBuilder {

    /**
     * Constructor. Private voor utility class.
     */
    private EnumeratieBuilder() {
    }

    /**
     * Genereert een java-enumeratie voor de attributen uit het BMR die gebruikt worden als symbolen in de
     * expressietaal.
     *
     * @param symbols Symbols waarvoor een enumeratie gemaakt moet worden.
     * @return Representatie van de java-enumeratie.
     */
    public static JavaSymbolEnum genereerEnumeratieVoorSymbols(final Collection<Symbol> symbols) {
        final JavaSymbolEnum javaEnum = new JavaSymbolEnum(SymbolTableConstants.ATTRIBUTES_ENUM_NAAM, SymbolTableConstants.SYMBOLS_PACKAGE);

        for (final Symbol symbol : symbols) {
            final String javaDoc = symbol.getEnumeratieJavaDoc();
            final EnumeratieWaarde enumWaarde = new EnumeratieWaarde(symbol.getEnumNaam(), javaDoc + ".");

            // Parameter: syntax
            enumWaarde.voegConstructorParameterToe(symbol.getSyntax(), true);

            // Parameter: isIndexed
            if (symbol.isIndexed()) {
                enumWaarde.voegConstructorParameterToe("true", false);
            } else {
                enumWaarde.voegConstructorParameterToe("false", false);
            }

            // Parameter: type
            enumWaarde.voegConstructorParameterToe(SymbolTableConstants.EXPRESSIETYPE_KLASSENAAM + "." + symbol.getExpressieType(), false);

            // Parameter: parentType
            enumWaarde.voegConstructorParameterToe(SymbolTableConstants.EXPRESSIETYPE_KLASSENAAM + "." + symbol.getParentExpressieType(), false);

            // Parameter: parentAttribute
            if (symbol.getParentSymbol() != null) {
                enumWaarde.voegConstructorParameterToe(SymbolTableConstants.ATTRIBUTES_ENUM_NAAM + "." + symbol.getParentSymbol().getEnumNaam(), false);
                javaEnum.voegExtraImportsToe(SymbolTableConstants.ATTRIBUTEGETTER_JAVATYPE);
            }

            // Parameter: getter
            enumWaarde.voegConstructorParameterToe("new " + SymbolTableConstants.SOLVERS_PACKAGE + "." + symbol.getGetterClassName() + "()", false);

            javaEnum.voegExtraImportsToe(SymbolTableConstants.EXPRESSIE_JAVATYPE, SymbolTableConstants.EXPRESSIETYPE_JAVATYPE,
                                         SymbolTableConstants.ROOTOBJECT_JAVATYPE, SymbolTableConstants.ATTRIBUUT_JAVATYPE, JavaType.LIST);

            javaEnum.voegEnumeratieWaardeToe(enumWaarde);
        }
        return javaEnum;
    }

    /**
     * Genereert een java-enumeratie voor de attributen uit het BMR die gebruikt worden als symbolen in de
     * expressietaal.
     *
     * @param symbols Symbols waarvoor een enumeratie gemaakt moet worden.
     * @return Representatie van de java-enumeratie.
     */
    public static JavaSymbolGroepEnum genereerEnumeratieVoorGroepSymbols(final Collection<Symbol> symbols) {
        final JavaSymbolGroepEnum javaEnum = new JavaSymbolGroepEnum(SymbolTableConstants.GROEPEN_ENUM_NAAM, SymbolTableConstants.SYMBOLS_PACKAGE);
        javaEnum.voegExtraImportsToe(SymbolTableConstants.GROEPGETTER_JAVATYPE);

        for (final Symbol symbol : symbols) {
            final String javaDoc = symbol.getEnumeratieJavaDoc();
            final EnumeratieWaarde enumWaarde = new EnumeratieWaarde(symbol.getEnumNaam(), javaDoc + ".");

            // Parameter: syntax
            enumWaarde.voegConstructorParameterToe(symbol.getSyntax(), true);

            // Parameter: isIndexed
            if (symbol.isIndexed()) {
                enumWaarde.voegConstructorParameterToe("true", false);
            } else {
                enumWaarde.voegConstructorParameterToe("false", false);
            }

            // Parameter: type
            enumWaarde.voegConstructorParameterToe(SymbolTableConstants.EXPRESSIETYPE_KLASSENAAM + "." + symbol.getExpressieType(), false);

            // Parameter: parentAttribute
            if (symbol.getParentSymbol() != null) {
                enumWaarde.voegConstructorParameterToe(SymbolTableConstants.GROEPEN_ENUM_NAAM + "." + symbol.getParentSymbol().getEnumNaam(), false);
            }

            // Parameter: getter
            enumWaarde.voegConstructorParameterToe("new " + SymbolTableConstants.SOLVERS_PACKAGE + "." + symbol.getGetterClassName() + "()", false);

            javaEnum.voegExtraImportsToe(SymbolTableConstants.EXPRESSIE_JAVATYPE, SymbolTableConstants.EXPRESSIETYPE_JAVATYPE,
                                         SymbolTableConstants.ROOTOBJECT_JAVATYPE, SymbolTableConstants.GROEP_JAVATYPE, JavaType.LIST);

            javaEnum.voegEnumeratieWaardeToe(enumWaarde);
        }
        return javaEnum;
    }
}
