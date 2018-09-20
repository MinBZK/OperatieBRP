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
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;

/**
 * Symbolen voor de symbol table.
 */
public abstract class AbstractSymbol implements Symbol {

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

    private final String            attribuutNaam;
    private final String            syntax;
    private final String            objectTypeNaam;
    private final String            objectExpressieType;
    private final Attribuut         bmrAttribuut;
    private final Symbol            parentSymbol;
    private final AbstractGenerator generator;
    private final JavaAccessPath    javaAccessPathNaarWaarde;
    private final JavaAccessPath    javaAccessPathNaarAttribuut;
    private final JavaAccessPath    javaAccessPathNaarAttribuutZonderGroep;
    private final JavaAccessPath    javaAccessPathNaarActueelRecordAttribuut;
    private final Groep             parentGroep;
    private final String            verantwoordingExpressie;


    /**
     * Constructor.
     *
     * @param attribuut          Attribuut uit het BMR waarmee het symbool naar verwijst.
     * @param groepNaam          Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param objectTypeNaam     Naam van het objecttype waartoe het symbool behoort.
     * @param isInverseAttribuut TRUE als het een geinverteerd attribuut betreft, anders FALSE.
     * @param parentSymbol       Parent symbol waartoe dit symbool behoort.
     * @param generator          Generator waarin het symbool wordt gebruikt.
     */
    public AbstractSymbol(final Attribuut attribuut, final String groepNaam, final String objectTypeNaam,
                          final boolean isInverseAttribuut, final Symbol parentSymbol,
                          final AbstractGenerator generator)
    {
        this(attribuut, groepNaam, objectTypeNaam, isInverseAttribuut, parentSymbol, generator, null, null);
    }

    /**
     * Constructor.
     *
     * @param attribuut               Attribuut uit het BMR waarmee het symbool naar verwijst.
     * @param groepNaam               Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param objectTypeNaam          Naam van het objecttype waartoe het symbool behoort.
     * @param isInverseAttribuut      TRUE als het een geinverteerd attribuut betreft, anders FALSE.
     * @param parentSymbol            Parent symbol waartoe dit symbool behoort.
     * @param generator               Generator waarin het symbool wordt gebruikt.
     * @param parentGroep             De groep van de parent (alleen nodig voor verantwoordingsinfo/acties).
     * @param verantwoordingExpressie De verwantwoording-expressie (alleen nodig voor verantwoordingsinfo/acties).
     */
    public AbstractSymbol(final Attribuut attribuut, final String groepNaam, final String objectTypeNaam,
                          final boolean isInverseAttribuut, final Symbol parentSymbol,
                          final AbstractGenerator generator, final Groep parentGroep,
                          final String verantwoordingExpressie)
    {
        this.parentGroep = parentGroep;
        this.verantwoordingExpressie = verantwoordingExpressie;
        final String volledigeAttribuutNaam =
            SYMBOL_TABLE_NAAMGEVING_STRATEGIE.bepaalAttribuutNaam(attribuut, groepNaam, objectTypeNaam,
                isInverseAttribuut);
        this.attribuutNaam =
                SYMBOL_TABLE_NAAMGEVING_STRATEGIE.getEnumWaarde(volledigeAttribuutNaam);
        this.syntax = SYMBOL_TABLE_NAAMGEVING_STRATEGIE.vereenvoudigSyntax(volledigeAttribuutNaam, groepNaam,
                parentSymbol, objectTypeNaam, getVerantwoordingExpressie());

        this.objectTypeNaam = objectTypeNaam;

        // Sommige objecttypen zijn subtype van Betrokkenheid, maar worden niet als afzonderlijk type in de
        // expressietaal beschikbaar. De naam van het objecttype moet dus de naam van het supertype (BETROKKENHEID)
        // zijn. In andere opzichten is het objecttype net als alle andere (bijvoorbeeld voor het vereenvoudigen van
        // de naam van een attribuut).
        if (BETROKKENHEID_SUBTYPES.contains(objectTypeNaam)) {
            this.objectExpressieType = SymbolTableConstants.BETROKKENHEID_TYPE;
        } else {
            this.objectExpressieType = objectTypeNaam;
        }

        this.bmrAttribuut = attribuut;
        this.parentSymbol = parentSymbol;
        this.generator = generator;
        this.javaAccessPathNaarWaarde = bepaalJavaAccessPathNaarWaarde(bmrAttribuut);
        this.javaAccessPathNaarAttribuut = bepaalJavaAccessPathNaarAttribuut(bmrAttribuut, true);
        this.javaAccessPathNaarAttribuutZonderGroep = bepaalJavaAccessPathNaarAttribuut(bmrAttribuut, false);
        this.javaAccessPathNaarActueelRecordAttribuut =
                bepaalJavaAccessPathNaarActueelRecordAttribuut(bmrAttribuut);
    }

    @Override
    public String getEnumNaam() {
        return attribuutNaam;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    public String getParentExpressieType() {
        if (parentSymbol != null) {
            return parentSymbol.getExpressieType();
        } else {
            return objectExpressieType;
        }
    }

    public Attribuut getBmrAttribuut() {
        return bmrAttribuut;
    }

    public Symbol getParentSymbol() {
        return parentSymbol;
    }

    @Override
    public String getObjectTypeNaam() {
        return objectTypeNaam;
    }

    public Groep getParentGroep() {
        return parentGroep;
    }

    public String getVerantwoordingExpressie() {
        return verantwoordingExpressie;
    }

    protected JavaAccessPath getJavaAccessPathNaarWaarde() {
        return javaAccessPathNaarWaarde;
    }

    protected JavaAccessPath getJavaAccessPathNaarAttribuut() {
        return javaAccessPathNaarAttribuut;
    }

    protected JavaAccessPath getJavaAccessPathNaarAttribuutZonderGroep() {
        return javaAccessPathNaarAttribuutZonderGroep;
    }

    protected JavaAccessPath getJavaAccessPathNaarActueelRecordAttribuut() {
        return javaAccessPathNaarActueelRecordAttribuut;
    }

    @Override
    public String getEnumeratieJavaDoc() {
        return String.format("Attribuut %s.%nBMR-attribuut '%s' van objecttype '%s'", getEnumNaam(),
                getBmrAttribuut().getNaam(), getBmrAttribuut().getObjectType().getNaam());
    }

    @Override
    public abstract String getGetterJavaDoc();

    @Override
    public String getGetterClassName() {
        return JavaGeneratieUtil.camelCase(getEnumNaam().replace('_', ' ').toLowerCase()).replaceAll(" ", "")
                + "Getter";
    }

    @Override
    public String toString() {
        return getEnumNaam();
    }

    protected AbstractGenerator getGenerator() {
        return generator;
    }

    /**
     * Geeft TRUE als het attribuut van een normaal attribuuttype is (i.p.v. een objectType of enumeratie).
     *
     * @param attribuut Te controleren attribuut.
     * @return True als het attribuut van een normaal attribuuttype is.
     */
    protected boolean isNormaalAttribuut(final Attribuut attribuut) {
        return generator.isAttribuutTypeAttribuut(attribuut)
                && generator.getBmrDao().getWaardeEnumeratiesVoorElement(attribuut.getType(), false, true).isEmpty();
    }

    /**
     * Bepaalt het pad van getters in de Java-code om bij de waarde van het betreffende attribuut te komen.
     *
     * @param attribuut Attribuut waarvoor pad bepaald moet worden.
     * @return Pad van getters.
     */
    protected abstract JavaAccessPath bepaalJavaAccessPathNaarWaarde(final Attribuut attribuut);

    /**
     * Bepaalt het pad van getters in de Java-code om bij het betreffende attribuut te komen.
     *
     * @param attribuut Attribuut waarvoor pad bepaald moet worden.
     * @param metGroep  TRUE als de groep onderdeel van het pad is, anders FALSE.
     * @return Pad van getters of NULL als dat niet gemaakt kan worden.
     */
    protected abstract JavaAccessPath bepaalJavaAccessPathNaarAttribuut(final Attribuut attribuut,
                                                                        final boolean metGroep);

    /**
     * Bepaalt het pad van getters in de Java-code om bij het betreffende attribuut van het actuele record (uit
     * HisSet - HisVolledig) te komen.
     *
     * @param attribuut Attribuut waarvoor pad bepaald moet worden.
     * @return Pad van getters of NULL als dat niet gemaakt kan worden.
     */
    protected abstract JavaAccessPath bepaalJavaAccessPathNaarActueelRecordAttribuut(final Attribuut attribuut);

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
     * Voegt import toe voor een getter class.
     *
     * @param attribuut Attribuut uit het logisch model.
     * @param klasse    Klasse waaraan imports toegevoegd moeten worden.
     */
    protected abstract void voegSpecifiekeImportsToe(final Attribuut attribuut, final JavaKlasse klasse);


    /**
     * Maakt de body van de getterfunctie die de waarde van een attribuut oplevert. Tevens retourneert het de imports
     * gebruikt in de body.
     *
     * @return Getterfunctie.
     */
    protected abstract BodyEnImports maakWaardeGetterBody();

    /**
     * Maakt de body van de getterfunctie die een attribuut (niet de waarde) oplevert. Tevens retourneert het de imports
     * gebruikt in de body.
     *
     * @return Getterfunctie.
     */
    protected abstract BodyEnImports maakAttribuutGetterBody();

    /**
     * Maakt de body van de getterfunctie die alle historische attributen oplevert, indien aanwezig. Tevens retourneert
     * het de imports gebruikt in de body.
     *
     * @return Getterfunctie.
     */
    protected abstract BodyEnImports maakHistorischeAttributenGetterBody();

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
     * Creëert een Java-klasse voor het symbool en bijbehorend attribuut. De Java-klasse is een implementatie van
     * AttributeGetter.
     *
     * @return Java-klasse voor het symbool/attribuut.
     */
    @Override
    public JavaKlasse maakGetterKlasse() {
        final JavaKlasse klasse =
                new JavaKlasse(getGetterClassName(), getGetterJavaDoc(), SymbolTableConstants.SOLVERS_PACKAGE);
        klasse.voegSuperInterfaceToe(SymbolTableConstants.ATTRIBUTEGETTER_JAVATYPE);
        voegSpecifiekeImportsToe(getBmrAttribuut(), klasse);

        JavaFunctie getter;

        // Maak getAttribuutWaarde()
        getter = maakWaardeGetterSignatuur();
        getter.setFinal(true);
        final BodyEnImports waardeGetterBodyEnImports = maakWaardeGetterBody();
        getter.setBody(waardeGetterBodyEnImports.getBody());
        klasse.voegExtraImportsToe(waardeGetterBodyEnImports.getExtraImportsAlsArray());
        klasse.voegFunctieToe(getter);

        // Maak getAttribuut()
        getter = maakAttribuutGetterSignatuur();
        getter.setFinal(true);
        final BodyEnImports attribuutGetterBodyEnImports = maakAttribuutGetterBody();
        getter.setBody(attribuutGetterBodyEnImports.getBody());
        klasse.voegExtraImportsToe(attribuutGetterBodyEnImports.getExtraImportsAlsArray());
        klasse.voegFunctieToe(getter);

        // Maak getHistorischeAttributen()
        getter = maakHistorischeAttributenGetterSignatuur();
        getter.setFinal(true);
        final BodyEnImports historischeAttributenGetterBodyEnImports = maakHistorischeAttributenGetterBody();
        getter.setBody(historischeAttributenGetterBodyEnImports.getBody());
        klasse.voegExtraImportsToe(historischeAttributenGetterBodyEnImports.getExtraImportsAlsArray());
        klasse.voegFunctieToe(getter);

        return klasse;
    }

    /**
     * Maakt een lege JavaFunctie met de signatuur van de functie voor het ophalen van attribuutwaarden.
     *
     * @return JavaFunctie.
     */
    private JavaFunctie maakWaardeGetterSignatuur() {
        final JavaFunctieParameter parameter = new JavaFunctieParameter(
                SymbolTableConstants.OBJECTPARAMETERNAAM, SymbolTableConstants.ROOTOBJECT_JAVATYPE);
        final JavaFunctie getter = new JavaFunctie(
                JavaAccessModifier.PUBLIC, SymbolTableConstants.EXPRESSIE_JAVATYPE,
                SymbolTableConstants.GETATTRIBUUTWAARDE_METHODENAAM, "");
        getter.setFinal(true);
        getter.voegParameterToe(parameter);
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        return getter;
    }

    /**
     * Maakt een lege JavaFunctie met de signatuur van de functie voor het ophalen van attributen.
     *
     * @return JavaFunctie.
     */
    private JavaFunctie maakAttribuutGetterSignatuur() {
        final JavaFunctieParameter parameter = new JavaFunctieParameter(
                SymbolTableConstants.OBJECTPARAMETERNAAM, SymbolTableConstants.ROOTOBJECT_JAVATYPE);
        final JavaFunctie getter = new JavaFunctie(
                JavaAccessModifier.PUBLIC, SymbolTableConstants.ATTRIBUUT_JAVATYPE,
                SymbolTableConstants.GETATTRIBUUT_METHODENAAM, "");
        getter.setFinal(true);
        getter.voegParameterToe(parameter);
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        return getter;
    }

    /**
     * Maakt een lege JavaFunctie met de signatuur van de functie voor het ophalen van historische attributen.
     *
     * @return JavaFunctie.
     */
    private JavaFunctie maakHistorischeAttributenGetterSignatuur() {
        final JavaFunctieParameter parameter = new JavaFunctieParameter(
                SymbolTableConstants.OBJECTPARAMETERNAAM, SymbolTableConstants.ROOTOBJECT_JAVATYPE);
        final JavaFunctie getter = new JavaFunctie(
                JavaAccessModifier.PUBLIC, new JavaType(JavaType.LIST, SymbolTableConstants.ATTRIBUUT_JAVATYPE),
                SymbolTableConstants.GETHISTORISCHEATTRIBUTEN_METHODENAAM, "");
        getter.setFinal(true);
        getter.voegParameterToe(parameter);
        getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        return getter;
    }

    /**
     * Geeft de attribuut naam.
     *
     * @return attribuut naam
     */
    public String getAttribuutNaam() {
        return attribuutNaam;
    }

}
