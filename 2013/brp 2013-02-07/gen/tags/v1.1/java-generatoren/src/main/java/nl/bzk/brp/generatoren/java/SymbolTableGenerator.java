/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import com.google.common.collect.Iterables;
import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaSymbolEnum;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.symbols.JavaAccessPath;
import nl.bzk.brp.generatoren.java.symbols.Symbol;
import nl.bzk.brp.generatoren.java.symbols.SymbolImpl;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;
import org.springframework.stereotype.Component;

/**
 * Generator die de symbol table voor expressies (voorwaarderegels) genereert.
 */
@Component("symbolTableJavaGenerator")
public class SymbolTableGenerator extends AbstractJavaGenerator {

    /**
     * Packages en javaklassen.
     */
    private static final String BRP_PACKAGE = GeneratiePackage.BRP_BASEPACKAGE.getPackage();
    private static final String MODEL_PACKAGE = GeneratiePackage.BRP_MODEL_BASEPACKAGE.getPackage();
    private static final String EXPRESSIES_PACKAGE = BRP_PACKAGE + ".expressietaal";
    private static final String SYNTAXTREE_PACKAGE = EXPRESSIES_PACKAGE + ".parser.syntaxtree";
    private static final String ATTRIBUTES_ENUM_PACKAGE = EXPRESSIES_PACKAGE + ".symbols";
    private static final String ATTRIBUTES_ENUM_NAAM = "Attributes";
    private static final String SOLVERS_PACKAGE = ATTRIBUTES_ENUM_PACKAGE + ".solvers";
    private static final String PERSOON_IDENTCODE = "Persoon";
    private static final String HUWELIJK_IDENTCODE = "Huwelijk";
    private static final String EXPRESSIE_KLASSENAAM = "Expressie";
    private static final String EXPRESSIETYPE_KLASSENAAM = "ExpressieType";
    private static final String NULLVALUE_KLASSENAAM = "NullValue";
    private static final String ATTRIBUTEGETTER_KLASSENAAM = "AttributeGetter";
    private static final String ROOTOBJECT_KLASSENAAM = "RootObject";
    private static final String GETVALUE_METHODENAAM = "getAttribuutWaarde";
    private static final String GETVALUE_OBJECTPARAMETERNAAM = "rootObject";
    private static final String GETMAXINDEX_METHODENAAM = "getMaxIndex";

    private static final JavaType ATTRIBUTEGETTER_JAVATYPE =
            new JavaType(ATTRIBUTEGETTER_KLASSENAAM, ATTRIBUTES_ENUM_PACKAGE);
    private static final JavaType EXPRESSIETYPE_JAVATYPE =
            new JavaType(EXPRESSIETYPE_KLASSENAAM, SYNTAXTREE_PACKAGE);
    private static final JavaType EXPRESSIE_JAVATYPE =
            new JavaType(EXPRESSIE_KLASSENAAM, SYNTAXTREE_PACKAGE);
    private static final JavaType ROOTOBJECT_JAVATYPE =
            new JavaType(ROOTOBJECT_KLASSENAAM, MODEL_PACKAGE);
    private static final JavaType NULLVALUE_JAVATYPE =
            new JavaType(NULLVALUE_KLASSENAAM, SYNTAXTREE_PACKAGE);

    /**
     * Basistypes uit expressietaal.
     */
    private static final String STRING_TYPE = "STRING";
    private static final String NUMBER_TYPE = "NUMBER";
    private static final String BOOLEAN_TYPE = "BOOLEAN";
    private static final String DATE_TYPE = "DATE";
    private static final String DATETIME_TYPE = "DATETIME";
    private static final String INDEXED_TYPE = "INDEXED";
    private static final String PERSOON_TYPE = "PERSOON";
    private static final String HUWELIJK_TYPE = "HUWELIJK";

    private static final NaamgevingStrategie NAAMGEVINGSSTRATEGIE = new LogischModelNaamgevingStrategie();

    private static final Dictionary<String, String> EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING;

    static {
        EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING = new Hashtable<String, String>();

        EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.put(STRING_TYPE, "StringLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.put(NUMBER_TYPE, "NumberLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.put(DATE_TYPE, "DateLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.put(DATETIME_TYPE, "DateTimeLiteralExpressie");
        EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.put(BOOLEAN_TYPE, "BooleanLiteralExpressie");
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        BmrElementFilterObject filter = new BmrElementFilterObject(BmrLaag.LOGISCH);
        filter.setSoortInhoud('D');

        // Haal alle objecttypen op
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen(filter);
        List<Symbol> symbols = new ArrayList<Symbol>();

        for (ObjectType objectType : objectTypen) {
            if (behoortTotJavaLogischModel(objectType)
                    && objectType.getIdentCode().equalsIgnoreCase(PERSOON_IDENTCODE))
            {
                symbols.addAll(maakSymbolsVanObjectType(objectType, null, PERSOON_TYPE));
            } else if (behoortTotJavaLogischModel(objectType)
                    && objectType.getIdentCode().equalsIgnoreCase(HUWELIJK_IDENTCODE))
            {
                symbols.addAll(maakSymbolsVanObjectType(objectType, null, HUWELIJK_TYPE));
            }
        }

        genereerSymbols(generatorConfiguratie, symbols);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.SYMBOL_TABLE_JAVA_GENERATOR;
    }

    /**
     * Genereert de enum voor symbols en voor alle symbols een getter om de betreffende waarde uit een (root)object
     * te halen.
     *
     * @param generatorConfiguratie Configuratie voor de generator.
     * @param symbols               Lijst met symbolen die gegenereerd moeten worden.
     */
    private void genereerSymbols(final GeneratorConfiguratie generatorConfiguratie, final Iterable<Symbol> symbols) {
        final List<JavaSymbolEnum> enumeraties = new ArrayList<JavaSymbolEnum>();
        enumeraties.add(genereerEnumeratieVoorSymbols(symbols));

        final JavaWriter<JavaSymbolEnum> enumeratieWriter =
                javaWriterFactory(generatorConfiguratie, JavaSymbolEnum.class);
        final List<JavaSymbolEnum> gegenereerdeEnums = enumeratieWriter.genereerEnSchrijfWeg(enumeraties, this);

        GeneratorConfiguratie solversGeneratorConfiguratie = new GeneratorConfiguratie();
        solversGeneratorConfiguratie.setNaam(generatorConfiguratie.getNaam() + " Solvers");
        solversGeneratorConfiguratie.setAlleenRapportage(generatorConfiguratie.isAlleenRapportage());
        solversGeneratorConfiguratie.setGenerationGapPatroon(false);
        solversGeneratorConfiguratie.setOverschrijf(true);
        solversGeneratorConfiguratie.setPad(generatorConfiguratie.getPad());

        final JavaWriter<JavaKlasse> klasseWriter =
                javaWriterFactory(solversGeneratorConfiguratie, JavaKlasse.class);
        List<JavaKlasse> klassen = new ArrayList<JavaKlasse>();

        for (Symbol symbol : symbols) {
            JavaKlasse klasse = genereerAttribuutGetterKlasse(symbol);
            if (klasse != null) {
                klassen.add(klasse);
            }
        }
        final List<JavaKlasse> gegenereerdeKlassen = klasseWriter.genereerEnSchrijfWeg(klassen, this);
        rapporteerOverGegenereerdeJavaTypen(Iterables.concat(gegenereerdeEnums, gegenereerdeKlassen));
    }

    /**
     * Creëert een Java-klasse voor het symbool en bijbehorend attribuut. De Java-klasse is een implementatie van
     * AttributeGetter.
     *
     * @param symbol Symbool waarvoor een klasse gemaakt moet worden.
     * @return Java-klasse voor het symbool/attribuut.
     */
    private JavaKlasse genereerAttribuutGetterKlasse(final Symbol symbol) {
        Attribuut attribuut = symbol.getBmrAttribuut();
        JavaKlasse klasse = null;

        boolean isInverted = (symbol.getParentSymbol() != null);

        if (isEnumAttribuut(attribuut) || isNormaalAttribuut(attribuut) || isStamgegevenAttribuut(attribuut)) {
            String logischModelKlasse = attribuut.getObjectType().getIdentCode();
            String javaDoc = String.format("Getter voor '%s' in objecttype '%s'.",
                    symbol.getSyntax(), logischModelKlasse);

            klasse = new JavaKlasse(symbol.getGetterClassName(), javaDoc, SOLVERS_PACKAGE);

            voegImportsToe(symbol, attribuut, klasse, isInverted);

            klasse.voegSuperInterfaceToe(ATTRIBUTEGETTER_JAVATYPE);

            JavaFunctie getter = maakNormaleGetter(symbol, isInverted, logischModelKlasse);
            klasse.voegFunctieToe(getter);

            getter = maakIndexGetter(symbol, isInverted);
            klasse.voegFunctieToe(getter);

            getter = maakMaxIndexFunctie(symbol, isInverted);
            klasse.voegFunctieToe(getter);
        } else {
            String logischModelKlasse = attribuut.getObjectType().getIdentCode();
            String javaDoc = String.format("Getter voor '%s' in objecttype '%s'.",
                    symbol.getSyntax(), logischModelKlasse);

            klasse = new JavaKlasse(symbol.getGetterClassName(), javaDoc, SOLVERS_PACKAGE);

            klasse.voegExtraImportToe(NAAMGEVINGSSTRATEGIE.getJavaTypeVoorElement(
                    attribuut.getType()).getFullyQualifiedClassName());

            klasse.voegSuperInterfaceToe(ATTRIBUTEGETTER_JAVATYPE);

            JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC, EXPRESSIE_JAVATYPE,
                    GETVALUE_METHODENAAM, "");
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            getter.setJavaDoc(
                    String.format("Geeft de waarde van attribuut '%s' uit het gegeven (root)object.",
                            symbol.getSyntax()));
            getter.setReturnWaardeJavaDoc("Waarde van het attribuut.");
            JavaFunctieParameter parameter = new JavaFunctieParameter(GETVALUE_OBJECTPARAMETERNAAM,
                    ROOTOBJECT_JAVATYPE);
            parameter.setJavaDoc("Object waarvan het attribuut bepaald moet worden.");
            getter.voegParameterToe(parameter);
            getter.setBody("return null;");
            klasse.voegFunctieToe(getter);

            getter = new JavaFunctie(JavaAccessModifier.PUBLIC, EXPRESSIE_JAVATYPE, GETVALUE_METHODENAAM, "");
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            getter.setJavaDoc(
                    String.format("Geeft de waarde van attribuut '%s' uit een bepaald%n"
                            + "geïndiceerd attribuut van een (root)object.",
                            symbol.getSyntax()));
            getter.setReturnWaardeJavaDoc("Waarde van het attribuut.");
            parameter = new JavaFunctieParameter(GETVALUE_OBJECTPARAMETERNAAM, ROOTOBJECT_JAVATYPE);
            parameter.setJavaDoc("Object waarvan het attribuut bepaald moet worden.");
            getter.voegParameterToe(parameter);
            parameter = new JavaFunctieParameter("index", JavaType.INTEGER);
            parameter.setJavaDoc("Index van het attribuut waartoe het gezochte attribuut behoort.");
            getter.voegParameterToe(parameter);
            getter.setBody("return null;");
            klasse.voegFunctieToe(getter);

            getter = new JavaFunctie(JavaAccessModifier.PUBLIC, JavaType.INTEGER, GETMAXINDEX_METHODENAAM, "");
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            getter.setJavaDoc(
                    String.format("Geeft het aantal waarden van '%s' van een (root)object.%n"
                            + "Dit is gelijk aan het aantal elementen in de verzameling.",
                            symbol.getSyntax()));
            getter.setReturnWaardeJavaDoc("Aantal waarden.");
            parameter = new JavaFunctieParameter(GETVALUE_OBJECTPARAMETERNAAM, ROOTOBJECT_JAVATYPE);
            parameter.setJavaDoc("Object waarvan het aantal waarden bepaald moet worden.");
            getter.voegParameterToe(parameter);
            String body;
            body = String.format("return ((%s) %s).get%s().size();",
                    attribuut.getType().getIdentCode(), GETVALUE_OBJECTPARAMETERNAAM,
                    attribuut.getInverseAssociatieIdentCode());
            getter.setBody(body);
            klasse.voegFunctieToe(getter);
        }

        return klasse;
    }

    /**
     * Voegt import toe voor een getter class.
     *
     * @param symbol    Symbool zoals dat voorkomt in de expressie.
     * @param attribuut Attribuut uit het logisch model.
     * @param klasse    Klasse waaraan imports toegevoegd moeten worden.
     * @param inverted  True als het attribuut een geinverteerde associatie is.
     */
    private void voegImportsToe(final Symbol symbol, final Attribuut attribuut, final JavaKlasse klasse,
                                final boolean inverted)
    {
        if (inverted) {
            klasse.voegExtraImportToe(JavaType.LIST.getFullyQualifiedClassName());
            klasse.voegExtraImportToe(JavaType.ARRAY_LIST.getFullyQualifiedClassName());
        }

        klasse.voegExtraImportToe(SYNTAXTREE_PACKAGE + "."
                + EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.get(symbol.getExpressieType()));
        klasse.voegExtraImportToe(SYNTAXTREE_PACKAGE + "." + NULLVALUE_KLASSENAAM);

        if (inverted) {
            klasse.voegExtraImportToe(NAAMGEVINGSSTRATEGIE.getJavaTypeVoorElement(
                    symbol.getParentSymbol().getBmrAttribuut().getType()).getFullyQualifiedClassName());
            klasse.voegExtraImportToe(NAAMGEVINGSSTRATEGIE.getJavaTypeVoorElement(
                    symbol.getParentSymbol().getBmrAttribuut().getObjectType()).getFullyQualifiedClassName());
        } else {
            klasse.voegExtraImportToe(NAAMGEVINGSSTRATEGIE.getJavaTypeVoorElement(
                    attribuut.getObjectType()).getFullyQualifiedClassName());
        }
    }

    /**
     * Maakt de getterfunctie (getAttribuutWaarde) voor een attribuut.
     *
     * @param symbol             Symbool voor het attribuut.
     * @param inverted           True als het attribuut een geinverteerde associatie is.
     * @param logischModelKlasse Naam van de klasse uit het logisch model waartoe het attribuut behoort.
     * @return Getterfunctie.
     */
    private JavaFunctie maakNormaleGetter(final Symbol symbol, final boolean inverted,
                                          final String logischModelKlasse)
    {
        String expressieTypeClass = EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.get(symbol.getExpressieType());
        JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC, EXPRESSIE_JAVATYPE, GETVALUE_METHODENAAM, "");
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        getter.setJavaDoc(
                String.format("Geeft de waarde van attribuut '%s' uit het gegeven (root)object.",
                        symbol.getSyntax()));
        getter.setReturnWaardeJavaDoc("Waarde van het attribuut.");
        JavaFunctieParameter parameter = new JavaFunctieParameter(GETVALUE_OBJECTPARAMETERNAAM, ROOTOBJECT_JAVATYPE);
        parameter.setJavaDoc("Object waarvan het attribuut bepaald moet worden.");
        getter.voegParameterToe(parameter);

        String body = "";
        if (inverted) {
            body = String.format("return %s(%s, 1);", GETVALUE_METHODENAAM, GETVALUE_OBJECTPARAMETERNAAM);
        } else {
            body = String.format("if (rootObject != null && %s instanceof %s) {\n", GETVALUE_OBJECTPARAMETERNAAM,
                    logischModelKlasse);
            body += String.format("%s v = (%s) %s;\n", logischModelKlasse, logischModelKlasse,
                    GETVALUE_OBJECTPARAMETERNAAM);

            if (symbol.getJavaAccessPath().isLaatsteAanroep()) {
                body += String.format("return new %s(v.%s);\n",
                        expressieTypeClass, symbol.getJavaAccessPath());
            } else {
                String controlPath = "v";
                JavaAccessPath iterator = symbol.getJavaAccessPath();
                body += "if (";
                while (!iterator.isLaatsteAanroep()) {
                    controlPath += "." + iterator.getMethodCall();
                    body += String.format("%s != null", controlPath);
                    iterator = iterator.getNext();
                    if (!iterator.isLaatsteAanroep()) {
                        body += "\n&& ";
                    }
                }
                body += ") {\n";
                body += String.format("return new %s(v.%s);\n",
                        expressieTypeClass, symbol.getJavaAccessPath());
                body += "}";
            }
            body += "}\n";
            body += "return new NullValue();";
        }

        getter.setBody(body);
        return getter;
    }

    /**
     * Maakt de getterfunctie (getAttribuutWaarde) voor een (geindiceerd) attribuut.
     *
     * @param symbol   Symbool voor het attribuut.
     * @param inverted True als het attribuut een geinverteerde associatie is.
     * @return Getterfunctie.
     */
    private JavaFunctie maakIndexGetter(final Symbol symbol, final boolean inverted) {
        String expressieTypeClass = EXPRESSIETYPE_NAAR_JAVA_CLASS_MAPPING.get(symbol.getExpressieType());
        JavaFunctie getter;
        JavaFunctieParameter parameter;
        String body;
        getter = new JavaFunctie(JavaAccessModifier.PUBLIC, EXPRESSIE_JAVATYPE, GETVALUE_METHODENAAM, "");
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        getter.setJavaDoc(
                String.format("Geeft de waarde van attribuut '%s' uit een bepaald%n"
                        + "geïndiceerd attribuut van een (root)object.",
                        symbol.getSyntax()));
        getter.setReturnWaardeJavaDoc("Waarde van het attribuut.");
        parameter = new JavaFunctieParameter(GETVALUE_OBJECTPARAMETERNAAM, ROOTOBJECT_JAVATYPE);
        parameter.setJavaDoc("Object waarvan het attribuut bepaald moet worden.");
        getter.voegParameterToe(parameter);
        parameter = new JavaFunctieParameter("index", JavaType.INTEGER);
        parameter.setJavaDoc("Index van het attribuut waartoe het gezochte attribuut behoort.");
        getter.voegParameterToe(parameter);

        if (inverted) {
            String elementType = symbol.getParentSymbol().getBmrAttribuut().getObjectType().getIdentCode();
            body = String.format("if (%s != null && %s instanceof %s && ((%s) %s).get%s() != null) {%n",
                    GETVALUE_OBJECTPARAMETERNAAM,
                    GETVALUE_OBJECTPARAMETERNAAM,
                    symbol.getParentSymbol().getBmrAttribuut().getType().getIdentCode(),
                    symbol.getParentSymbol().getBmrAttribuut().getType().getIdentCode(),
                    GETVALUE_OBJECTPARAMETERNAAM,
                    symbol.getParentSymbol().getBmrAttribuut().getInverseAssociatieIdentCode());
            body += String.format("    List<%s> collectie = new ArrayList<%s>();%n", elementType, elementType);


            body += String.format("    collectie.addAll(((%s) %s).get%s());%n",
                    symbol.getParentSymbol().getBmrAttribuut().getType().getIdentCode(),
                    GETVALUE_OBJECTPARAMETERNAAM,
                    symbol.getParentSymbol().getBmrAttribuut().getInverseAssociatieIdentCode());


            body += "    if (index >= 1 && index <= collectie.size()) {\n";
            body += String.format("        return new %s(collectie.get(index - 1).%s);%n",
                    expressieTypeClass, symbol.getJavaAccessPath());
            body += "    }\n";
            body += "}\n";
            body += "return new NullValue();";
        } else {
            body = String.format("return %s(%s);", GETVALUE_METHODENAAM, GETVALUE_OBJECTPARAMETERNAAM);
        }
        getter.setBody(body);
        return getter;
    }

    /**
     * Maakt een functie die de maximale index van een attribuut teruggeeft.
     *
     * @param symbol   Symbool voor het attribuut.
     * @param inverted True als het attribuut een geinverteerde associatie is.
     * @return Javafunctie.
     */
    private JavaFunctie maakMaxIndexFunctie(final Symbol symbol, final boolean inverted) {
        JavaFunctie getter;
        JavaFunctieParameter parameter;
        String body;
        getter = new JavaFunctie(JavaAccessModifier.PUBLIC, JavaType.INTEGER, GETMAXINDEX_METHODENAAM, "");
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        getter.setJavaDoc(
                String.format("Geeft de hoogste indexwaarde voor '%s' uit een bepaald%n"
                        + "geïndiceerd attribuut van een (root)object.%n"
                        + "Dit is gelijk aan het aantal elementen in de verzameling.",
                        symbol.getSyntax()));
        getter.setReturnWaardeJavaDoc("Maximale index.");
        parameter = new JavaFunctieParameter(GETVALUE_OBJECTPARAMETERNAAM, ROOTOBJECT_JAVATYPE);
        parameter.setJavaDoc("Object waarvan de hoogste indexwaarde bepaald moet worden.");
        getter.voegParameterToe(parameter);
        if (inverted) {
            body = String.format("return ((%s) %s).get%s().size();",
                    symbol.getParentSymbol().getBmrAttribuut().getType().getIdentCode(),
                    GETVALUE_OBJECTPARAMETERNAAM,
                    symbol.getParentSymbol().getBmrAttribuut().getInverseAssociatieIdentCode());
        } else {
            body = String.format("return 1;");
        }
        getter.setBody(body);
        return getter;
    }

    /**
     * Stel de symbolische naam van de gegeven groep samen.
     *
     * @param groep      Groep waarvoor de naam gemaakt wordt.
     * @param objectType Objecttype waartoe de groep behoort.
     * @return Symbolische naam van de groep.
     */
    private String maakGroepNaam(final Groep groep, final ObjectType objectType) {

        String groepNaam;
        if (groep != null && !groep.getIdentCode().equals(STANDAARD) && !groep.getIdentCode().equals(IDENTITEIT)) {
            groepNaam = String.format("%s.%s",
                    objectType.getIdentCode(),
                    groep.getIdentCode());
        } else {
            groepNaam = String.format("%s",
                    objectType.getIdentCode());
        }
        groepNaam = JavaGeneratieUtil.normalise(groepNaam).toLowerCase();
        return groepNaam;
    }

    /**
     * Maakt een collectie met symbolen die overeenstemmen met de attributen uit het gegeven objecttype.
     *
     * @param objectType     Type waartoe de attributen moeten behoren.
     * @param parentIndex    Geïndiceerd symbool waartoe de attributen behoren of NULL.
     * @param objectTypeNaam Naam van het objecttype.
     * @return Collectie met symbolen.
     */
    private Collection<Symbol> maakSymbolsVanObjectType(final ObjectType objectType, final Symbol parentIndex,
                                                        final String objectTypeNaam)
    {
        final Collection<Symbol> symbols = new ArrayList<Symbol>();
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType, true);

        for (Groep groep : groepenVoorObjectType) {
            if (behoortTotJavaLogischModel(groep)) {
                String groepNaam = maakGroepNaam(groep, objectType);
                symbols.addAll(maakSymbolsVanGroep(groepNaam, groep, parentIndex, objectTypeNaam));
            }
        }

        List<Attribuut> inverseAttributen = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        for (Attribuut attr : inverseAttributen) {
            String groepNaam = maakGroepNaam(null, objectType);

            Symbol newParentIndex = maakSymbolVanInverseAttribuut(attr, groepNaam, objectTypeNaam);
            symbols.add(newParentIndex);

            ObjectType ot = attr.getObjectType();
            symbols.addAll(maakSymbolsVanObjectType(ot, newParentIndex, objectTypeNaam));
        }

        return symbols;
    }

    /**
     * Maakt een collectie met symbolen die overeenstemmen met de attributen uit de gegeven groep.
     *
     * @param groepNaam      Symbolische naam van de groep.
     * @param groep          Groep waartoe de attributen behoren.
     * @param parentIndex    Geïndexeerde symbool waartoe het attribuut behoort of NULL, indien dat niet bestaat.
     * @param objectTypeNaam Naam van het objecttype waartoe de groep behoort.
     * @return Collectie met symbolen.
     */
    private Collection<Symbol> maakSymbolsVanGroep(final String groepNaam, final Groep groep,
                                                   final Symbol parentIndex, final String objectTypeNaam)
    {
        final Collection<Symbol> symbols = new ArrayList<Symbol>();
        final List<Attribuut> attributen = getAttributenVanGroep(groep);

        for (Attribuut attribuut : attributen) {
            if (isNormaalAttribuut(attribuut) || isEnumAttribuut(attribuut)) {
                Symbol symbol = maakSymbolVanAttribuut(attribuut, groepNaam, getBasisType(attribuut), parentIndex,
                        objectTypeNaam);
                symbols.add(symbol);
            } else if (isStamgegevenAttribuut(attribuut)) {
                Symbol symbol;
                if (attribuut.getIdentCode().equalsIgnoreCase(groepNaam)) {
                    symbol = maakSymbolVanAttribuut(attribuut, "", STRING_TYPE, parentIndex, objectTypeNaam);
                } else {
                    symbol = maakSymbolVanAttribuut(attribuut, groepNaam, STRING_TYPE, parentIndex, objectTypeNaam);
                }
                symbols.add(symbol);
            }
        }
        return symbols;
    }

    /**
     * Zet een attribuut uit het BMR om in een symbool, dat gebruikt kan worden in expressies.
     *
     * @param attribuut          Attribuut uit het BMR.
     * @param groepNaam          Symbolische naam van de groep waartoe het attribuut behoort.
     * @param expressieType      Het (expressie)type van het symbool.
     * @param parentIndex        Het geïndiceerde symbool waartoe het attribuut behoort.
     * @param objectTypeNaam     Naam van het objecttype waartoe het attribuut behoort.
     * @param isInverseAttribuut TRUE als het een inverse attribuut betreft, anders FALSE.
     * @return Het symbool voor het attribuut.
     */
    private Symbol maakSymbolVanAttribuut(final Attribuut attribuut, final String groepNaam,
                                          final String expressieType, final Symbol parentIndex,
                                          final String objectTypeNaam, final boolean isInverseAttribuut)
    {
        String attribuutNaam;
        if (isInverseAttribuut) {
            attribuutNaam = JavaGeneratieUtil.cleanUpInvalidJavaCharacters(
                    attribuut.getInverseAssociatieIdentCode()).toLowerCase();

        } else {
            attribuutNaam = JavaGeneratieUtil.cleanUpInvalidJavaCharacters(
                    attribuut.getNaam()).toLowerCase();

        }

        attribuutNaam = JavaGeneratieUtil.normalise(attribuutNaam);
        attribuutNaam = vereenvoudigAttribuutNaam(attribuutNaam, groepNaam, objectTypeNaam);
        attribuutNaam = attribuutNaam.replace(' ', '_');

        String volledigeAttribuutNaam;
        if (groepNaam.length() > 0 && attribuutNaam.length() > 0) {
            volledigeAttribuutNaam = String.format("%s.%s", groepNaam, attribuutNaam);
        } else if (attribuutNaam.length() > 0) {
            volledigeAttribuutNaam = attribuutNaam;
        } else {
            volledigeAttribuutNaam = groepNaam;
        }

        String syntax = vereenvoudigSyntax(volledigeAttribuutNaam, groepNaam, parentIndex, objectTypeNaam);

        JavaAccessPath javaAccessPath;
        if (!isInverseAttribuut) {
            javaAccessPath = bepaalJavaAccessPath(attribuut);
        } else {
            javaAccessPath = null;
        }
        return new SymbolImpl(getEnumWaarde(volledigeAttribuutNaam), syntax, expressieType, objectTypeNaam, attribuut,
                parentIndex, javaAccessPath);
    }

    /**
     * Bepaalt het pad van getters in de Java-code om bij het betreffende attributen te komen.
     *
     * @param attribuut Attribuut waarvoor pad bepaald moet worden.
     * @return Pad van getters.
     */
    private JavaAccessPath bepaalJavaAccessPath(final Attribuut attribuut) {
        JavaAccessPath result = new JavaAccessPath(String.format("get%s()", attribuut.getIdentCode()));

        Groep gr = attribuut.getGroep();
        if (!IDENTITEIT.equals(gr.getIdentCode())) {
            result = new JavaAccessPath(String.format("get%s()", gr.getIdentCode()), result);
        }

        //TODO: De twee onderstaande controles zijn hacks omdat het BMR nu nog inconsequent omgaat met naamgeving.
        //TODO: ipv getCode moet het met andere paden.
        if (attribuut.getIdentCode().equals("Soort") && attribuut.getType().getIdentCode().equals("SoortIndicatie")) {
            result.voegToe(new JavaAccessPath("getNaam()"));
            //result = String.format("%s.getNaam()", result);
        } else if (attribuut.getIdentCode().equals("BijzondereVerblijfsrechtelijkePositie")) {
            result.voegToe(new JavaAccessPath("getNaam()"));
            result.voegToe(new JavaAccessPath("getWaarde()"));
            //result = String.format("%s.getNaam().getWaarde()", result);
        } else if (isNormaalAttribuut(attribuut) || isEnumAttribuut(attribuut)) {
            result.voegToe(new JavaAccessPath("getWaarde()"));
            //result = String.format("%s.getWaarde()", result);
        } else if (isStatischStamgegevenAttribuut(attribuut)) {
            result.voegToe(new JavaAccessPath("getCode()"));
            //result = String.format("%s.getCode()", result);
        } else if (isDynamischStamgegevenAttribuut(attribuut)) {
            result.voegToe(new JavaAccessPath("getCode()"));
            result.voegToe(new JavaAccessPath("getWaarde()"));
            //result = String.format("%s.getCode().getWaarde()", result);
        }
        return result;
    }

    /**
     * Zet een attribuut uit het BMR om in een symbool, dat gebruikt kan worden in expressies.
     *
     * @param attribuut      Attribuut uit het BMR.
     * @param groepNaam      Symbolische naam van de groep waartoe het attribuut behoort.
     * @param expressieType  Het (expressie)type van het symbool.
     * @param parentIndex    Het geïndiceerde symbool waartoe het attribuut behoort.
     * @param objectTypeNaam Objecttype waartoe het attribuut behoort.
     * @return Het symbool voor het attribuut.
     */
    private Symbol maakSymbolVanAttribuut(final Attribuut attribuut, final String groepNaam,
                                          final String expressieType, final Symbol parentIndex,
                                          final String objectTypeNaam)
    {
        return maakSymbolVanAttribuut(attribuut, groepNaam, expressieType, parentIndex, objectTypeNaam, false);
    }

    /**
     * Zet een geïnverteerd attribuut (door associatie) uit het BMR om in een symbool, dat gebruikt kan worden in
     * expressies.
     *
     * @param attribuut      Attribuut uit het BMR.
     * @param groepNaam      Symbolische naam van de groep waartoe het attribuut behoort.
     * @param objectTypeNaam Objecttype waartoe het attribuut behoort.
     * @return Het symbool voor het attribuut.
     */
    private Symbol maakSymbolVanInverseAttribuut(final Attribuut attribuut, final String groepNaam,
                                                 final String objectTypeNaam)
    {
        return maakSymbolVanAttribuut(attribuut, groepNaam, INDEXED_TYPE, null, objectTypeNaam, true);
    }

    /**
     * Geeft TRUE als het attribuut van een normaal attribuuttype is (i.p.v. een objectType of enumeratie).
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut van een normaal attribuuttype is.
     */
    private boolean isNormaalAttribuut(final Attribuut attribuut) {
        if (attribuut.getType().getSoortElement().getCode().equals("AT")) {
            List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                    attribuut.getType(), false, true);
            return waardes.isEmpty();
        } else {
            return false;
        }
    }

    /**
     * Geeft TRUE als het attribuut van een enumeratie-attribuuttype is (i.p.v. een objectType).
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut van een attribuuttype is.
     */
    private boolean isEnumAttribuut(final Attribuut attribuut) {
        if (attribuut.getType().getSoortElement().getCode().equals("AT")) {
            List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                    attribuut.getType(), false, true);
            return !waardes.isEmpty();
        } else {
            return false;
        }
    }

    /**
     * Geeft TRUE als het attribuut een statisch stamgegeven is.
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut een statisch stamgegeven is.
     */
    private boolean isStatischStamgegevenAttribuut(final Attribuut attribuut) {
        String typeCode = attribuut.getType().getSoortElement().getCode();
        Character inhoud = attribuut.getType().getSoortInhoud();
        return typeCode.equals("OT") && inhoud.equals('X');
    }

    /**
     * Geeft TRUE als het attribuut een dynamisch stamgegeven is.
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut een dynamisch stamgegeven is.
     */
    private boolean isDynamischStamgegevenAttribuut(final Attribuut attribuut) {
        String typeCode = attribuut.getType().getSoortElement().getCode();
        Character inhoud = attribuut.getType().getSoortInhoud();
        return typeCode.equals("OT") && inhoud.equals('S');
    }

    /**
     * Geeft TRUE als het attribuut een stamgegeven is.
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut een stamgegeven  is.
     */
    private boolean isStamgegevenAttribuut(final Attribuut attribuut) {
        return isDynamischStamgegevenAttribuut(attribuut) || isStatischStamgegevenAttribuut(attribuut);
    }

    /**
     * Bepaalt de enumwaarde die behoort bij het attribuut.
     *
     * @param attribuut Attribuut waarvoor de enumwaarde bepaald moet worden.
     * @return Enumwaarde van het attribuut.
     */
    private String getEnumWaarde(final String attribuut) {
        String result = attribuut.replace('.', ' ');
        result = result.replace(' ', '_').toUpperCase();
        return result;
    }

    /**
     * Geeft een lijst met attributen die tot een groep behoren.
     *
     * @param groep Groep waarvan de attributen bepaald moeten worden.
     * @return Lijst met attributen uit de groep.
     */
    private List<Attribuut> getAttributenVanGroep(final Groep groep) {
        final List<Attribuut> result = new ArrayList<Attribuut>();
        final List<nl.bzk.brp.metaregister.model.Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);

        for (Attribuut attribuut : attributen) {
            if (behoortTotJavaLogischModel(attribuut)) {
                result.add(attribuut);
            }
        }
        return result;
    }

    /**
     * Geeft het basistype, zoals dat in expressies gebruikt wordt, waarop het attribuut wordt afgebeeld.
     *
     * @param attribuut Attribuut waarvan het basistype bepaald moet worden.
     * @return Basistype van het attribuut.
     */
    private String getBasisType(final Attribuut attribuut) {
        final AttribuutType attribuutType = getBmrDao().getAttribuutTypeVoorAttribuut(attribuut);
        return getBmrDao().getBasisTypeVoorAttribuutType(attribuutType,
                BmrTargetPlatform.EXPRESSIES).getBasisType().getNaam();
    }

    /**
     * Probeert de attribuutnaam te vereenvoudigen.
     *
     * @param attribuutnaam  Naam van het attribuut.
     * @param groepnaam      Naam van de groep waartoe het attribuut behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het attribuut behoort.
     * @return De vereenvoudigde attribuutnaam.
     */
    private String vereenvoudigAttribuutNaam(final String attribuutnaam, final String groepnaam,
                                             final String objectTypeNaam)
    {
        String result = " " + attribuutnaam + " ";
        result = result.replaceAll(" " + groepnaam + " ", "");

        if (groepnaam.toLowerCase().startsWith(objectTypeNaam.toLowerCase() + ".")) {
            String substr = groepnaam.substring(objectTypeNaam.length() + 1);
            result = result.replaceAll(" " + substr + " ", "");
        }

        return result.trim();
    }

    /**
     * Probeert de syntax van een symbool te vereenvoudigen op basis van de groepnaam en het feit dat het mogelijk een
     * 'subattribuut' is van een geïndexeerd attribuut.
     *
     * @param attribuutnaam  Naam van het attribuut.
     * @param groepnaam      Naam van de groep waartoe het attribuut behoort.
     * @param parentIndex    Symbol van het geïndexeerde attribuut waartoe het symbool behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het attribuut behoort.
     * @return De vereenvoudigde attribuutnaam.
     */
    private String vereenvoudigSyntax(final String attribuutnaam, final String groepnaam, final Symbol parentIndex,
                                      final String objectTypeNaam)
    {
        String result = attribuutnaam;
        if (attribuutnaam != null && groepnaam != null && groepnaam.length() > 0) {
            if (parentIndex != null) {
                /*
                 * Attributen die tot een ander attribuut behoren (zoals huisnummer bij adres), delen in hun naam zowel
                 * de groep als het overkoepelende attribuut. Die verdubbeling is wel nodig om onderscheid te kunnen
                 * maken in attributen (bijvoorbeeld omdat 'code' als attribuut meer dan één keer kan voorkomen).
                 * In de syntax is die verdubbeling echter niet nodig, omdat de naam van het overkoepelende attribuut
                 * verplicht voorafgaand is opgenomen. Daarom kan 'adressen[1].adres.huisnummer' worden afgekort tot
                 * 'adressen[1].huisnummer'.
                 * De hier toegepaste vereenvoudigingen hebben dus uitsluitend betrekking op de syntax van het
                 * attribuut, niet op de naamgeving in de enumeratie.
                 */
                if (attribuutnaam.startsWith(groepnaam + ".")) {
                    result = attribuutnaam.substring(groepnaam.length() + 1);
                }
            } else {
                /*
                 * Attributen die niet tot een ander attribuut behoren, maar waarvan de syntax begint met "persoon.",
                 * kunnen vereenvoudigd worden. De parser gaat namelijk uit van "persoon" als 'standaard object'
                 * waarop de expressies betrekking hebben.
                 */
                if (attribuutnaam.toLowerCase().startsWith(objectTypeNaam.toLowerCase() + ".")) {
                    result = attribuutnaam.substring(objectTypeNaam.length() + 1);
                }
            }
        }

        return result;
    }

    /**
     * Genereert een java-enumeratie voor de attributen uit het BMR die gebruikt worden als symbolen in de
     * expressietaal.
     *
     * @param symbols Lijst met symbolen.
     * @return Representatie van de java-enumeratie.
     */
    private JavaSymbolEnum genereerEnumeratieVoorSymbols(final Iterable<Symbol> symbols) {
        final JavaSymbolEnum javaEnum = new JavaSymbolEnum(ATTRIBUTES_ENUM_NAAM, ATTRIBUTES_ENUM_PACKAGE);

        for (Symbol symbol : symbols) {
            String javaDoc = symbol.getGetterJavaDoc();
            final EnumeratieWaarde enumWaarde = new EnumeratieWaarde(
                    symbol.getEnumAttribuutNaam(),
                    javaDoc + ".");

            enumWaarde.voegConstructorParameterToe(symbol.getSyntax(), true);
            enumWaarde.voegConstructorParameterToe(EXPRESSIETYPE_KLASSENAAM + "." + symbol.getExpressieType(), false);
            enumWaarde.voegConstructorParameterToe(EXPRESSIETYPE_KLASSENAAM + "."
                    + symbol.getExpressieTypeGroep(), false);

            if (symbol.getParentSymbol() != null) {
                enumWaarde.voegConstructorParameterToe(ATTRIBUTES_ENUM_NAAM + "."
                        + symbol.getParentSymbol().getEnumAttribuutNaam(), false);
            }

            enumWaarde.voegConstructorParameterToe("new " + symbol.getGetterClassName() + "()", false);
            javaEnum.voegExtraImportToe(SOLVERS_PACKAGE + "." + symbol.getGetterClassName());

            javaEnum.voegExtraImportToe(EXPRESSIE_JAVATYPE.getFullyQualifiedClassName());
            javaEnum.voegExtraImportToe(EXPRESSIETYPE_JAVATYPE.getFullyQualifiedClassName());
            javaEnum.voegExtraImportToe(ROOTOBJECT_JAVATYPE.getFullyQualifiedClassName());

            javaEnum.voegEnumeratieWaardeToe(enumWaarde);
        }
        return javaEnum;
    }
}
