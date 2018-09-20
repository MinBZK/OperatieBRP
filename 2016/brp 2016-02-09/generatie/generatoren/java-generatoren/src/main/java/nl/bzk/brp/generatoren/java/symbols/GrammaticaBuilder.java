/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;

/**
 * Builder voor een deel van de ANTLR-grammatica voor de expressietaal. Alleen het gedeelte dat afhankelijk is van de
 * BMR-attributen en BMR-groepen wordt hier gegenereerd.
 */
public final class GrammaticaBuilder {

    private static final String PAD_SEPARATOR = System.getProperty("file.separator");

    private static final String GRAMMATICA_FILENAAM = "BRPAttributen.g4";
    private static final String GRAMMATICA_FILENAAM_GROEPEN = "BRPGroepen.g4";

    /**
     * Constructor. Private voor utility class.
     */
    private GrammaticaBuilder() {
    }

    /**
     * Genereert een deel van de ANTLR-grammatica van de expressietaal.
     *
     * @param symbols               Symbols waarvoor de grammatica gemaakt moet worden.
     * @param generatorConfiguratie Configuratie voor de generator.
     * @param packagePad            Het package-pad waarbinnen het grammaticabestand geschreven moet worden.
     * @throws FileNotFoundException Gooit FileNotFoundException als het niet gelukt is om het grammaticabestand te
     *                               maken.
     */
    public static void genereerGrammatica(final Collection<Symbol> symbols,
                                          final GeneratorConfiguratie generatorConfiguratie,
                                          final String packagePad) throws FileNotFoundException
    {
        final StringBuilder bestandNaam = maakBestandNaam(GRAMMATICA_FILENAAM, packagePad, generatorConfiguratie.getPad());
        new File(bestandNaam.toString());
        final PrintWriter writer = new PrintWriter(bestandNaam.toString());
        final List<String> keywords = maakKeywordsVanSymbols(symbols);

        // Speciale gevallen: de alternatieve namen voor attributen, zoals afkortingen
        keywords.add("identificatienummers.bsn");
        keywords.add("bsn");
        keywords.add("identificatienummers.anummer");
        keywords.add("anummer");

        writer.println("grammar BRPAttributen;");
        writer.println("attribute_path :");
        vulFileMetKeywords(writer, keywords);
    }

    /**
     * Genereert een deel van de ANTLR-grammatica van de expressietaal.
     *
     * @param symbols               Symbols waarvoor de grammatica gemaakt moet worden.
     * @param generatorConfiguratie Configuratie voor de generator.
     * @param packagePad            Het package-pad waarbinnen het grammaticabestand geschreven moet worden.
     * @throws FileNotFoundException Gooit FileNotFoundException als het niet gelukt is om het grammaticabestand te
     *                               maken.
     */
    public static void genereerGrammaticaGroepen(final Collection<Symbol> symbols,
                                                 final GeneratorConfiguratie generatorConfiguratie,
                                                 final String packagePad) throws FileNotFoundException
    {
        final StringBuilder bestandNaam = maakBestandNaam(GRAMMATICA_FILENAAM_GROEPEN, packagePad, generatorConfiguratie.getPad());
        new File(bestandNaam.toString());
        final PrintWriter writer = new PrintWriter(bestandNaam.toString());
        final List<String> keywords = maakKeywordsVanSymbols(symbols);

        writer.println("grammar BRPGroepen;");
        writer.println("groep_path :");
        vulFileMetKeywords(writer, keywords);
    }

    /**
     * Maak keywords van symbols.
     *
     * @param symbols de symbols
     * @return list met keywords
     */
    private static List<String> maakKeywordsVanSymbols(final Collection<Symbol> symbols) {
        final List<String> keywords = new ArrayList<>();
        for (final Symbol symbol : symbols) {
            if (!keywords.contains(symbol.getSyntax())) {
                keywords.add(symbol.getSyntax());
            }
        }
        return keywords;
    }

    /**
     * Vul file met keywords.
     *
     * @param writer de writer
     * @param keywords de keywords
     */
    private static void vulFileMetKeywords(final PrintWriter writer, final List<String> keywords) {
        boolean first = true;
        for (final String keyword : keywords) {
            if (first) {
                writer.print("   ");
                first = false;
            } else {
                writer.print(" | ");
            }
            writer.println("'" + keyword + "'");
        }
        writer.println(" ;");
        writer.close();
    }

    private static StringBuilder maakBestandNaam(final String grammaticaFilenaam, final String packagePad, final String pad) {
        final StringBuilder bestandNaam = new StringBuilder();
        bestandNaam.append(pad);
        bestandNaam.append(PAD_SEPARATOR);
        if (packagePad != null) {
            bestandNaam.append(packagePad.replace(".", PAD_SEPARATOR));
            bestandNaam.append(PAD_SEPARATOR);
        }
        bestandNaam.append(grammaticaFilenaam);
        return bestandNaam;
    }
}
