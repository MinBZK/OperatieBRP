/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigInterfaceModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.SymbolTableNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Symbolen voor de symbol table.
 */
public abstract class AbstractGroepSymbol implements Symbol {

    protected static final NaamgevingStrategie            NAAMGEVINGSSTRATEGIE              =
            new LogischModelNaamgevingStrategie();
    protected static final NaamgevingStrategie            NAAMGEVINGSSTRATEGIE_HISTORISCH   =
            new HisVolledigInterfaceModelNaamgevingStrategie();
    protected static final NaamgevingStrategie            NAAMGEVINGSSTRATEGIE_OPERATIONEEL =
            new OperationeelModelNaamgevingStrategie();
    protected static final SymbolTableNaamgevingStrategie SYMBOL_TABLE_NAAMGEVING_STRATEGIE =
            new SymbolTableNaamgevingStrategie();

    protected static final String GETTER_FORMAT = "get%s()";

    /**
     * Subtypes van Betrokkenheid die in de taal niet als afzonderlijk type voorkomen.
     */
    private static final List<String> BETROKKENHEID_SUBTYPES =
        new ArrayList<String>() {
            {
                add("Erkenner");
                add("Instemmer");
                add("Ouder");
                add("Kind");
                add("Naamgever");
                add("Partner");
            }
        };

    private final String            groepNaam;
    private final String            syntax;
    private final String            objectTypeNaam;
    private final Groep             groep;
    private final AbstractGenerator generator;
    private final JavaAccessPath    javaAccessPathNaarGroep;
    private final JavaAccessPath    javaAccessPathNaarActueelRecordGroep;
    private final String            verantwoordingExpressie;
    private final boolean           indicatie;

    /**
     * Constructor.
     *
     * @param groep Groep uit het BMR waarmee het symbool naar verwijst.
     * @param groepNaam Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het symbool behoort.
     * @param generator Generator waarin het symbool wordt gebruikt.
     * @param verantwoordingExpressie De verantwoording expressie
     * @param objecttype Het objecttype
     */
    public AbstractGroepSymbol(final Groep groep, final String groepNaam, final String objectTypeNaam, final AbstractGenerator generator,
                               final String verantwoordingExpressie, final ObjectType objecttype)
    {
        this.verantwoordingExpressie = verantwoordingExpressie;

        this.indicatie = groepNaam.startsWith("Indicatie");
        final String volledigeGroepNaam =  SYMBOL_TABLE_NAAMGEVING_STRATEGIE.maakGroepNaam(groep, objecttype, indicatie);

        this.groepNaam = SYMBOL_TABLE_NAAMGEVING_STRATEGIE.getEnumWaarde(volledigeGroepNaam);
        this.syntax =
            SYMBOL_TABLE_NAAMGEVING_STRATEGIE.vereenvoudigSyntax(volledigeGroepNaam, groepNaam, null, objectTypeNaam, getVerantwoordingExpressie());

        this.objectTypeNaam = objectTypeNaam;
        this.groep = groep;
        this.generator = generator;
        this.javaAccessPathNaarGroep = bepaalJavaAccessPath(groep, true);
        this.javaAccessPathNaarActueelRecordGroep = bepaalJavaAccessPathNaarActueelRecord(groep);
    }

    public Groep getGroep() {
        return groep;
    }

    @Override
    public String getEnumNaam() {
        return groepNaam;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public String getObjectTypeNaam() {
        return objectTypeNaam;
    }

    public String getVerantwoordingExpressie() {
        return verantwoordingExpressie;
    }

    protected JavaAccessPath getJavaAccessPath() {
        return javaAccessPathNaarGroep;
    }

    protected JavaAccessPath getJavaAccessPathNaarActueelRecord() {
        return javaAccessPathNaarActueelRecordGroep;
    }

    @Override
    public String getEnumeratieJavaDoc() {
        return String.format("Groep %s.%nBMR-groep '%s' van objecttype '%s'", getEnumNaam(),
                getGroep().getNaam(), getGroep().getObjectType().getNaam());
    }

    @Override
    public abstract String getGetterJavaDoc();

    @Override
    public String getGetterClassName() {
        return JavaGeneratieUtil.camelCase(getEnumNaam().replace('_', ' ').toLowerCase()).replaceAll(" ", "") + "GroepGetter";
    }

    @Override
    public String toString() {
        return getEnumNaam();
    }

    protected AbstractGenerator getGenerator() {
        return generator;
    }

    /**
     * Bepaalt het pad van getters in de Java-code om bij de betreffende groep te komen.
     *
     * @param groep Groep waarvoor pad bepaald moet worden.
     * @param metGroep  TRUE als de groep onderdeel van het pad is, anders FALSE.
     * @return Pad van getters of NULL als dat niet gemaakt kan worden.
     */
    protected abstract JavaAccessPath bepaalJavaAccessPath(final Groep groep, final boolean metGroep);

    /**
     * Bepaalt het pad van getters in de Java-code om bij de betreffende groep van het actuele record (uit
     * HisSet - HisVolledig) te komen.
     *
     * @param groep Groep waarvoor pad bepaald moet worden.
     * @return Pad van getters of NULL als dat niet gemaakt kan worden.
     */
    protected abstract JavaAccessPath bepaalJavaAccessPathNaarActueelRecord(final Groep groep);

    /**
     * Geeft het Java-type in het logisch model behorend bij het element.
     *
     * @param generiekElement Element.
     * @return het Java-type in het logisch model behorend bij het element.
     */
    protected JavaType getModelKlasse(final GeneriekElement generiekElement) {
        return NAAMGEVINGSSTRATEGIE.getJavaTypeVoorElement(generiekElement);
    }

    /**
     * Geeft het 'historische' Java-type behorend bij het element.
     *
     * @param generiekElement Element.
     * @return het 'historische' Java-type behorend bij het element.
     */
    protected JavaType getHistorischeModelKlasse(final GeneriekElement generiekElement) {
        return NAAMGEVINGSSTRATEGIE_HISTORISCH.getJavaTypeVoorElement(generiekElement);
    }

    /**
     * Maakt de body van de getterfunctie die een groep oplevert. Tevens retourneert het de imports
     * gebruikt in de body.
     *
     * @return Getterfunctie.
     */
    protected abstract BodyEnImports maakGroepGetterBody();

    /**
     * Maakt de body van de getterfunctie die alle historische groepen oplevert, indien aanwezig. Tevens retourneert
     * het de imports gebruikt in de body.
     *
     * @return Getterfunctie.
     */
    protected abstract BodyEnImports maakHistorischeGroepenGetterBody();

    /**
     * Maakt een constructor gegeven een expressietype en een waarde.
     *
     * @param expressieTypeClass Klasse van het expressietype.
     * @param waarde             Waarde van de expressie.
     * @return Constructor voor gegeven expressietype en waarde.
     */
    protected static String maakExpressieConstructor(final String expressieTypeClass, final String waarde) {
        if (expressieTypeClass.equals("BooleanLiteralExpressie")) {
            return String.format("BooleanLiteralExpressie.getExpressie(%s)", waarde);
        } else {
            return String.format("new %s(%s)", expressieTypeClass, waarde);
        }
    }

    /**
     * Creëert een Java-klasse voor het symbool en bijbehorend groep. De Java-klasse is een implementatie van
     * GroepGetter.
     *
     * @return Java-klasse voor het symbool/groep.
     */
    @Override
    public JavaKlasse maakGetterKlasse() {
        final JavaKlasse klasse = new JavaKlasse(getGetterClassName(), getGetterJavaDoc(), SymbolTableConstants.SOLVERS_PACKAGE);
        klasse.voegSuperInterfaceToe(SymbolTableConstants.GROEPGETTER_JAVATYPE);
        voegSpecifiekeImportsToe(klasse);

        JavaFunctie getter;

        // Maak getGroep()
        getter = maakGroepGetterSignatuur();
        getter.setFinal(true);
        final BodyEnImports groepGetterBodyEnImports = maakGroepGetterBody();
        getter.setBody(groepGetterBodyEnImports.getBody());
        klasse.voegExtraImportsToe(groepGetterBodyEnImports.getExtraImportsAlsArray());
        klasse.voegFunctieToe(getter);

        // Maak getHistorischeGroepen()
        getter = maakHistorischeGroepenGetterSignatuur();
        getter.setFinal(true);
        final BodyEnImports historischeGroepenGetterBodyEnImports = maakHistorischeGroepenGetterBody();
        getter.setBody(historischeGroepenGetterBodyEnImports.getBody());
        klasse.voegExtraImportsToe(historischeGroepenGetterBodyEnImports.getExtraImportsAlsArray());
        klasse.voegFunctieToe(getter);

        return klasse;
    }

    protected abstract void voegSpecifiekeImportsToe(final JavaKlasse klasse);

    /**
     * Maakt een lege JavaFunctie met de signatuur van de functie voor het ophalen van groepen.
     *
     * @return JavaFunctie.
     */
    private JavaFunctie maakGroepGetterSignatuur() {
        final JavaFunctieParameter parameter = new JavaFunctieParameter(SymbolTableConstants.OBJECTPARAMETERNAAM,
                                                                        SymbolTableConstants.ROOTOBJECT_JAVATYPE);
        final JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC, SymbolTableConstants.GROEP_JAVATYPE, SymbolTableConstants.GETGROEP_METHODENAAM, "");
        getter.setFinal(true);
        getter.voegParameterToe(parameter);
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        return getter;
    }

    /**
     * Maakt een lege JavaFunctie met de signatuur van de functie voor het ophalen van historische groepen.
     *
     * @return JavaFunctie.
     */
    private JavaFunctie maakHistorischeGroepenGetterSignatuur() {
        final JavaFunctieParameter parameter = new JavaFunctieParameter(SymbolTableConstants.OBJECTPARAMETERNAAM,
                                                                        SymbolTableConstants.ROOTOBJECT_JAVATYPE);
        final JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC, new JavaType(JavaType.LIST, SymbolTableConstants.GROEP_JAVATYPE),
                                                   SymbolTableConstants.GETHISTORISCHEGROEPEN_METHODENAAM, "");
        getter.setFinal(true);
        getter.voegParameterToe(parameter);
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        return getter;
    }

    public boolean isIndicatie() {
        return indicatie;
    }
}
