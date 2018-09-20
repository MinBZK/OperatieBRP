/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import com.google.common.collect.Iterables;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaSymbolGroepEnum;
import nl.bzk.brp.generatoren.java.symbols.EnumeratieBuilder;
import nl.bzk.brp.generatoren.java.symbols.ExpressieGroepMapBuilder;
import nl.bzk.brp.generatoren.java.symbols.GrammaticaBuilder;
import nl.bzk.brp.generatoren.java.symbols.StandaardGroepSymbol;
import nl.bzk.brp.generatoren.java.symbols.Symbol;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.VwElement;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Generator die de symbol table en getters voor groepen in BRP-expressies genereert.
 */
@Component("symbolTableGroepJavaGenerator")
public class SymbolTableGroepGenerator extends AbstractJavaGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SymbolTableGroepGenerator.class);
    private List<Symbol> symbols;

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        // Haalt elementen op van soort "Groep".
        final List<VwElement> vwGroepen = getBmrDao().getVwElementenVanSoort("G");
        this.symbols = new ArrayList<>();
        final Map<Integer, VwElement> vwElementen = getBmrDao().getVwElementenAlsMap();
        final Map<Integer, ObjectType> objectTypes = getBmrDao().getObjectTypesAlsMap();
        final Map<Integer, Groep> groepen = getBmrDao().getGroepenAlsMap();

        final List<String> toegevoegdeEnumSyntaxes = new ArrayList<>();
        // Verzamel alle symbolen voor de relevante objecttypen.
        for (final VwElement vwGroep : vwGroepen) {
            final VwElement vwObjectType = vwElementen.get(vwGroep.getObjecttype());
            final ObjectType objectType = objectTypes.get(vwObjectType.getId());
            final Groep groep = groepen.get(vwGroep.getId());

            // Enkel de groepen die betrekking hebben op een persoon (uitgezonderd de 1 op n relaties zoals voornamen/adressen/verificaties/etc)
            if (objectType != null && groep != null
                && StringUtils.lowerCase(vwGroep.getIdentifier()).startsWith("persoon")
                && !StringUtils.lowerCase(vwGroep.getIdentifier()).equals("persoon.identiteit")
                && !StringUtils.lowerCase(vwGroep.getIdentifier()).contains("cache")
                && !StringUtils.lowerCase(vwGroep.getIdentifier()).contains("verstrekkingsbeperking")
                && !StringUtils.lowerCase(vwObjectType.getNaam()).contains("persoon \\"))
            {
                final String syntax;
                if (StringUtils.lowerCase(vwGroep.getIdentifier()).contains("indicatie")) {
                    syntax = "Indicatie" + vwGroep.getNaam();
                } else {
                    syntax = vwGroep.getNaam();
                }
                final StandaardGroepSymbol standaardGroepSymbol = new StandaardGroepSymbol(syntax, objectType.getNaam(), groep, this, objectType);

                // Controleer op dubbele enums
                if (!toegevoegdeEnumSyntaxes.contains(standaardGroepSymbol.getSyntax())) {
                    symbols.add(standaardGroepSymbol);
                    toegevoegdeEnumSyntaxes.add(standaardGroepSymbol.getSyntax());
                }

            } else {
//                LOGGER.debug("Geen groep of objecttype gevonden voor vwGroep met id: {} en vwObjectType met id: {}.", vwGroep.getId() , vwObjectType.getId());
            }
        }
        // Genereer de code voor de gecreeerde symbolen.
        genereerSymbols(generatorConfiguratie);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.SYMBOL_TABLE_JAVA_GENERATOR;
    }

    /**
     * Genereert de enum voor symbols en voor alle symbols een getterklasse om de betreffende waarde uit een
     * (root)object te halen.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     */
    private void genereerSymbols(final GeneratorConfiguratie generatorConfiguratie) {
        // Genereer de enumeratie.
        final List<JavaSymbolGroepEnum> gegenereerdeEnums = genereerSymbolEnumeratie(generatorConfiguratie);
        // Genereer de Map van id's naar expressietaalattributen.
//        final List<JavaKlasse> gegenereerdeMap = genereerSymbolMap(generatorConfiguratie);
        // Genereer de getterklassen.
        final List<JavaKlasse> gegenereerdeKlassen = genereerGetterKlassen(generatorConfiguratie);

        // Rapporteer over de generatie.
        if (gegenereerdeEnums.size() > 0) {
            final JavaSymbolGroepEnum e = gegenereerdeEnums.get(0);
            genereerGrammatica(generatorConfiguratie, e.getPackagePad());
        }

//        rapporteerOverGegenereerdeJavaTypen(Iterables.concat(gegenereerdeEnums, gegenereerdeMap, gegenereerdeKlassen));
        rapporteerOverGegenereerdeJavaTypen(Iterables.concat(gegenereerdeEnums, gegenereerdeKlassen));
    }

    /**
     * Genereert de enumeratie voor symbols.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @return Enumeratie voor de symbols.
     */
    private List<JavaSymbolGroepEnum> genereerSymbolEnumeratie(final GeneratorConfiguratie generatorConfiguratie) {
        // Bepaal de enumeratie voor symbolen.
        final List<JavaSymbolGroepEnum> enumeraties = new ArrayList<>();
        enumeraties.add(genereerEnumeratieVoorSymbols());
        // Genereer de code voor de enumeratie.
        final JavaWriter<JavaSymbolGroepEnum> enumeratieWriter = javaWriterFactory(generatorConfiguratie, JavaSymbolGroepEnum.class);

        voegGeneratedAnnotatiesToe(enumeraties, generatorConfiguratie);

        return enumeratieWriter.genereerEnSchrijfWeg(enumeraties, this);
    }

    /**
     * Genereert een java-enumeratie voor de attributen uit het BMR die gebruikt worden als symbolen in de
     * expressietaal.
     *
     * @return Representatie van de java-enumeratie.
     */
    private JavaSymbolGroepEnum genereerEnumeratieVoorSymbols() {
        return EnumeratieBuilder.genereerEnumeratieVoorGroepSymbols(symbols);
    }

    /**
     * Genereert een Map voor id's naar expressietaalattributen.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @return JavaKlasse met Map.
     */
    private List<JavaKlasse> genereerSymbolMap(final GeneratorConfiguratie generatorConfiguratie) {
        final GeneratorConfiguratie symbolMapGeneratorConfiguratie = maakConfiguratieVoorSymbolMap(generatorConfiguratie);
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(symbolMapGeneratorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> klassen = new LinkedList<>();
        klassen.add(maakExpressieMap());
//        klassen.add(maakExpressieGroepMap());

        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);

        return klasseWriter.genereerEnSchrijfWeg(klassen, this);
    }

    /**
     * Genereert de getterklassen voor symbols.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @return Lijst met alle getterklassen voor de symbols.
     */
    private List<JavaKlasse> genereerGetterKlassen(final GeneratorConfiguratie generatorConfiguratie) {
        // Genereer de getter-klassen voor symbolen.
        final GeneratorConfiguratie solversGeneratorConfiguratie = maakConfiguratieVoorGetters(generatorConfiguratie);
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(solversGeneratorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> klassen = new ArrayList<>();
        for (final Symbol symbol : symbols) {
            final JavaKlasse klasse = symbol.maakGetterKlasse();
            if (klasse != null) {
                klassen.add(klasse);
            }
        }

        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);

        return klasseWriter.genereerEnSchrijfWeg(klassen, this);
    }

    /**
     * Definieert een generatorconfiguratie voor de generatie van getterklassen gebaseerd op de generatorconfiguratie
     * voor de enumeratie.
     *
     * @param enumeratieGeneratorConfiguratie
     *         Configuratie voor de generator van de enumeratie.
     * @return Generatorconfiguratie voor de generatie van getterklassen.
     */
    private GeneratorConfiguratie maakConfiguratieVoorGetters(final GeneratorConfiguratie enumeratieGeneratorConfiguratie) {
        final GeneratorConfiguratie solversGeneratorConfiguratie = new GeneratorConfiguratie();
        solversGeneratorConfiguratie.setNaam(enumeratieGeneratorConfiguratie.getNaam() + " Solvers");
        solversGeneratorConfiguratie.setAlleenRapportage(enumeratieGeneratorConfiguratie.isAlleenRapportage());
        solversGeneratorConfiguratie.setGenerationGapPatroon(false);
        solversGeneratorConfiguratie.setOverschrijf(true);
        solversGeneratorConfiguratie.setPad(enumeratieGeneratorConfiguratie.getPad());
        return solversGeneratorConfiguratie;
    }

    /**
     * Definieert een generatorconfiguratie voor de generatie van de mapping van id's op attributen (ExpressieMap).
     *
     * @param enumeratieGeneratorConfiguratie
     *         Configuratie voor de generator van de enumeratie.
     * @return Generatorconfiguratie voor de generatie van ExpressieMap.
     */
    private GeneratorConfiguratie maakConfiguratieVoorSymbolMap(
        final GeneratorConfiguratie enumeratieGeneratorConfiguratie)
    {
        final GeneratorConfiguratie symbolMapGeneratorConfiguratie = new GeneratorConfiguratie();
        symbolMapGeneratorConfiguratie.setNaam(enumeratieGeneratorConfiguratie.getNaam() + " SymbolMap");
        symbolMapGeneratorConfiguratie.setAlleenRapportage(enumeratieGeneratorConfiguratie.isAlleenRapportage());
        symbolMapGeneratorConfiguratie.setGenerationGapPatroon(false);
        symbolMapGeneratorConfiguratie.setOverschrijf(true);
        symbolMapGeneratorConfiguratie.setPad(enumeratieGeneratorConfiguratie.getPad());
        return symbolMapGeneratorConfiguratie;
    }

    /**
     * Maakt een Java utility-klasse die een afbeelding van id's (syncid) van gegevenselementen op attributen uit de
     * expressietaal oplevert.
     *
     * @return Java-klasse.
     */
    private JavaKlasse maakExpressieMap() {
        return ExpressieGroepMapBuilder.maakExpressieGroepMap(symbols);
    }

//    /**
//     * Maakt een Java utility-klasse die een mapping oplevert tussen de groepen in Element en de expressietaal.
//     *
//     * @return Java-klasse.
//     */
//    private JavaKlasse maakExpressieGroepMap() {
//        return ExpressieGroepMapBuilder.maakExpressieGroepMap(symbols);
//    }

    /**
     * Genereert een deel van de ANTLR-grammatica van de expressietaal.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @param packagePad            Het package-pad waarbinnen het grammaticabestand geschreven moet worden.
     * @return TRUE als generatie succesvol is afgerond, anders FALSE.
     */
    private boolean genereerGrammatica(final GeneratorConfiguratie generatorConfiguratie, final String packagePad) {
        try {
            GrammaticaBuilder.genereerGrammaticaGroepen(symbols, generatorConfiguratie, packagePad);
            return true;
        } catch (final FileNotFoundException e) {
            return false;
        }
    }

}
