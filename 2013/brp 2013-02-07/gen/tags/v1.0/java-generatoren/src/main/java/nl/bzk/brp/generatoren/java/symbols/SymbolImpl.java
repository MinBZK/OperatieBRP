/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.Attribuut;

/**
 * Symbolen voor de symbol table. De symbolen zijn afgeleid van attributen en worden afgebeeld op een basistype.
 */
public class SymbolImpl implements Symbol {
    private final String attribuutNaam;
    private final String syntax;
    private final String type;
    private final String groep;
    private final Attribuut bmrAttribuut;
    private final Symbol parentIndex;
    private final JavaAccessPath javaAccessPath;

    /**
     * Creëert een symbool met gegeven syntax dat wordt afgebeeld op het gegeven type en tot de gegeven groep
     * behoort.
     *
     * @param attribuutNaam  Naam van het symbool in de enumeratie.
     * @param syntax         Syntax van het symbool in expressies.
     * @param type           Type van het symbool.
     * @param groep          Groep waartoe het symbool behoort.
     * @param bmrAttribuut   Attribuut uit het BMR waarmee het symbool naar verwijst.
     * @param parentIndex    Geïndiceerde symbool/attribuut waar het symbool onderdeel van uitmaakt.
     * @param javaAccessPath Pad van getters naar het attribuut in Java.
     */
    public SymbolImpl(final String attribuutNaam, final String syntax, final String type, final String groep,
                      final Attribuut bmrAttribuut, final Symbol parentIndex, final JavaAccessPath javaAccessPath)
    {
        this.attribuutNaam = attribuutNaam;
        this.syntax = syntax;
        this.type = type;
        this.groep = groep;
        this.bmrAttribuut = bmrAttribuut;
        this.parentIndex = parentIndex;
        this.javaAccessPath = javaAccessPath;
    }

    @Override
    public String getEnumAttribuutNaam() {
        return attribuutNaam;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getExpressieType() {
        return type;
    }

    @Override
    public String getExpressieTypeGroep() {
        return groep;
    }

    @Override
    public Attribuut getBmrAttribuut() {
        return bmrAttribuut;
    }

    @Override
    public Symbol getParentSymbol() {
        return parentIndex;
    }

    @Override
    public JavaAccessPath getJavaAccessPath() {
        return javaAccessPath;
    }

    @Override
    public String getGetterJavaDoc() {
        return String.format("Attribuut %s.%nBMR-attribuut '%s' van objecttype '%s'",
                getEnumAttribuutNaam(), getBmrAttribuut().getNaam(), getBmrAttribuut().getObjectType().getNaam());
    }

    @Override
    public String getGetterClassName() {
        return JavaGeneratieUtil.camelCase((attribuutNaam.replace('_', ' ')).toLowerCase()).replaceAll(" ",
                "") + "Getter";
    }
}
