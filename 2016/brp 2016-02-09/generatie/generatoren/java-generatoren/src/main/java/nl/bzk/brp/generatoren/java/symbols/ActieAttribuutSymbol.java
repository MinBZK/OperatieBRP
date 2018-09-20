/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.HisMomentNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Symbolen voor de symbol table. De symbolen zijn afgeleid van attributen en worden afgebeeld op een basistype.
 */
public final class ActieAttribuutSymbol extends AbstractSymbol {

    private static final List<String> PARENT_IDENT_CODE_UITZONDERINGEN                  = Arrays.asList("Identiteit", "Standaard");
    private static final List<String> GENERIEKE_ELEMENT_OUDER_IDENT_CODE_UITZONDERINGEN
        = Arrays.asList("Persoon", "Ouder", "Relatie", "Onderzoek");
    private static final NaamgevingStrategie hisMomentNaamgevingStrategie = new HisMomentNaamgevingStrategie();

    private final String expressieType;
    private final String javaType;

    /**
     * Creëert een symbool met gegeven syntax dat wordt afgebeeld op het gegeven type en tot de gegeven groep
     * behoort.
     *
     * @param groepNaam      Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het symbool behoort.
     * @param bmrAttribuut   Attribuut uit het BMR waarmee het symbool naar verwijst.
     * @param parentSymbol   Geïndiceerde symbool/attribuut waar het symbool onderdeel van uitmaakt.
     * @param generator      Generator waarin het symbool wordt gebruikt.
     */
    public ActieAttribuutSymbol(final String groepNaam, final String objectTypeNaam, final Attribuut bmrAttribuut,
        final Symbol parentSymbol, final AbstractGenerator generator, final Groep parentGroep,
        final String verantwoordingExpressie)
    {
        super(bmrAttribuut, groepNaam + "." + verantwoordingExpressie, objectTypeNaam, false, parentSymbol, generator,
                parentGroep, verantwoordingExpressie);
        this.expressieType = Utils.getExpressieBasisType(bmrAttribuut, generator);
        this.javaType = Utils.getJavaBasisType(bmrAttribuut, generator);
    }

    @Override
    public boolean isIndexed() {
        return false;
    }

    @Override
    public String getExpressieType() {
        return expressieType;
    }

    @Override
    public String getJavaType() {
        return javaType;
    }

    @Override
    public String getGetterJavaDoc() {
        final JavaType modelKlasse = getModelKlasse(getBmrAttribuut().getObjectType());
        return String.format("Getter voor groepattribuut '%s' in objecttype '%s'.", getSyntax(), modelKlasse.getNaam());
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarWaarde(final Attribuut attribuut) {
        final JavaAccessPath result = bepaalJavaAccessPathNaarActueelRecordAttribuut(attribuut);
        result.voegToe(new JavaAccessPath(SymbolTableConstants.BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM + "()"));
        return result;
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarAttribuut(final Attribuut attribuut, final boolean metGroep) {
        JavaAccessPath result = null;
        if (isNormaalAttribuut(attribuut) || getGenerator().isStamgegevenAttribuut(attribuut)
                || attribuut.getType().getNaam().equals("Ja") || attribuut.getType().getNaam().equals("Nee"))
        {
            result = new JavaAccessPath(String.format(GETTER_FORMAT, attribuut.getIdentCode()));
            result = new JavaAccessPath(String.format(GETTER_FORMAT, JavaGeneratieUtil
                    .camelCase(getVerantwoordingExpressie())), result);

            if(metGroep) {
                final Groep gr = getParentGroep();
                if (gr != null) {
                    result = new JavaAccessPath(String.format(GETTER_FORMAT, gr.getIdentCode()), result);
                }
            }
        }
        return result;
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarActueelRecordAttribuut(final Attribuut attribuut) {
        JavaAccessPath result = null;
        if (isNormaalAttribuut(attribuut) || getGenerator().isStamgegevenAttribuut(attribuut)
                || attribuut.getType().getNaam().equals("Ja") || attribuut.getType().getNaam().equals("Nee"))
        {
            String ident = getParentIdentificatie(
                getParentGroep().getIdentCode(),
                getParentGroep().getGeneriekElementOuder().getIdentCode());

            final String historieMethode = "get" + ident + "Historie()";
            result = new JavaAccessPath(String.format(GETTER_FORMAT, attribuut.getIdentCode()));
            result = new JavaAccessPath(String.format(GETTER_FORMAT, JavaGeneratieUtil.camelCase(
                    getVerantwoordingExpressie())), result);
            result = new JavaAccessPath("getActueleRecord()", result);
            result = new JavaAccessPath(historieMethode, result);
        }
        return result;
    }

    @Override
    protected BodyEnImports maakWaardeGetterBody() {
        final String expressieTypeClass =
                SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());

        final Attribuut attribuut = getBmrAttribuut();
        final List<JavaType> imports = new ArrayList<>();
        final StringBuilder body = new StringBuilder();

        body.append(String.format(
            "final %s attribuut = %s(%s);%n",
            SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
            SymbolTableConstants.GETATTRIBUUT_METHODENAAM,
            SymbolTableConstants.OBJECTPARAMETERNAAM));
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

        body.append(String.format("%s resultaat = %s;%n", SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam(),
                SymbolTableConstants.NULLVALUE_EXPRESSIE));
        body.append(String.format("if (attribuut != null) {%n"));

        String waarde;
        if (Utils.isStatischStamgegevenAttribuut(attribuut)
            || getGenerator().isDynamischStamgegevenAttribuut(attribuut))
        {
            final ObjectType ot = getGenerator().getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
            final JavaType tt = NAAMGEVINGSSTRATEGIE.getJavaTypeVoorElement(ot);
            final Attribuut logischeIdentiteit = getGenerator().bepaalLogischeIdentiteitVoorStamgegeven(ot);
            waarde = String.format("((%s)attribuut).%s().%s", tt.getNaam(),
                SymbolTableConstants.BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM,
                String.format(GETTER_FORMAT, logischeIdentiteit.getIdentCode()));
            imports.add(tt);
            if (getGenerator().isDynamischStamgegevenAttribuut(attribuut)) {
                waarde = waarde + "." + SymbolTableConstants.BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM + "()";
            }

        } else {
            waarde = String.format("%s(%s).%s()", SymbolTableConstants.GETATTRIBUUT_METHODENAAM,
                SymbolTableConstants.OBJECTPARAMETERNAAM, SymbolTableConstants.BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM);
        }

        body.append(String.format("resultaat = %s;%n", maakExpressieConstructor(expressieTypeClass, waarde)))
                .append(String.format("}%n"))
                .append("return resultaat;");
        imports.add(SymbolTableConstants.NULLVALUE_JAVATYPE);
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);

        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakAttribuutGetterBody() {
        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<JavaType>();
        body.append(String.format("%s resultaat = null;%n", SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam()));
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

        final String klassenaam = getParentKlassenaam();

        final JavaType hisMoment = hisMomentNaamgevingStrategie.getJavaTypeVoorElement(getParentGroep().getObjectType());
        imports.add(hisMoment);

        //hismoment
        body.append(String.format("if (%s instanceof %s) {", SymbolTableConstants.OBJECTPARAMETERNAAM, hisMoment.getNaam()))
            .append(String.format("final %s v = (%s) brpObject;", hisMoment.getNaam(), hisMoment.getNaam()));
        final StringBuilder hisMomentControlPath = new StringBuilder("v");
        final JavaAccessPath hisMomentJavaAccessPath = getJavaAccessPathNaarAttribuut();
        JavaAccessPath hisMomentPathIterator = hisMomentJavaAccessPath;
        if (!hisMomentJavaAccessPath.isLaatsteAanroep()) {
            body.append("if (");
            while (!hisMomentPathIterator.isLaatsteAanroep()) {
                hisMomentControlPath.append(".").append(hisMomentPathIterator.getMethodCall());
                body.append(String.format("%s != null", hisMomentControlPath.toString()));
                hisMomentPathIterator = hisMomentPathIterator.getNext();
                if (!hisMomentPathIterator.isLaatsteAanroep()) {
                    body.append("\n&& ");
                }
            }
            body.append(") {\n");
        }
        body.append(String.format("resultaat = v.%s;", hisMomentJavaAccessPath.toString()));
        body.append("}");
        body.append("}");

        //hisvolledig
        body.append(String.format("else if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM, klassenaam))
            .append(String.format("final %s v = (%s) %s;%n", klassenaam, klassenaam,
                SymbolTableConstants.OBJECTPARAMETERNAAM));

        final StringBuilder controlPath = new StringBuilder("v");
        final JavaAccessPath javaAccessPath = getJavaAccessPathNaarActueelRecordAttribuut();
        JavaAccessPath iterator = javaAccessPath;

        if (!javaAccessPath.isLaatsteAanroep()) {
            body.append("if (");
            while (!iterator.isLaatsteAanroep()) {
                controlPath.append(".").append(iterator.getMethodCall());
                body.append(String.format("%s != null", controlPath.toString()));
                iterator = iterator.getNext();
                if (!iterator.isLaatsteAanroep()) {
                    body.append("\n&& ");
                }
            }
            body.append(") {\n");
        }

        body.append(String.format("resultaat = v.%s;%n", javaAccessPath));

        if (!javaAccessPath.isLaatsteAanroep()) {
            body.append("}\n");
        }

        body.append("}\n");
        body.append("return resultaat;");
        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakHistorischeAttributenGetterBody() {
        final Attribuut attribuut = getBmrAttribuut();
        final String klassenaam = getParentKlassenaam();

        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<JavaType>();

        if (getJavaAccessPathNaarAttribuutZonderGroep() != null) {

            body.append(String.format("final List<%s> attributen = new ArrayList<%s>();%n",
                    SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
                    SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam()));
            imports.add(JavaType.LIST);
            imports.add(JavaType.ARRAY_LIST);
            imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

            String parentIdent = getParentIdentificatie(getParentGroep().getIdentCode(), getParentGroep().getGeneriekElementOuder().getIdentCode());

            final String historieMethode = "get" + parentIdent + "Historie";
            final String packageName =
                    NAAMGEVINGSSTRATEGIE_OPERATIONEEL.getJavaTypeVoorElement(attribuut.getObjectType())
                            .getPackagePad();
            final String iteratorGenericParameter = packageName + ".His" + parentIdent + "Model";

            final String attrValue =
                    String.format("hisModel.%s", getJavaAccessPathNaarAttribuutZonderGroep().toString());

            body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                    klassenaam))
                    .append(String.format("final %s p = (%s) %s;", klassenaam, klassenaam,
                            SymbolTableConstants.OBJECTPARAMETERNAAM))
                    .append(String.format("final Iterator<%s> iterator = p.%s().getHistorie().iterator();",
                            iteratorGenericParameter, historieMethode))
                    .append(String.format("while (iterator.hasNext()) {%n"))
                    .append(String.format("final %s hisModel = iterator.next();", iteratorGenericParameter))
                    .append(String.format("if (hisModel.get%s() != null) {", JavaGeneratieUtil
                            .camelCase(getVerantwoordingExpressie())))
                    .append(String.format("attributen.add(%s);%n", attrValue))
                    .append(String.format("}", attrValue))
                    .append("}\n}\n")
                    .append("return attributen;");
            imports.add(JavaType.ITERATOR);
        } else {
            body.append("return null;");
        }
        return new BodyEnImports(body.toString(), imports);
    }

    @VisibleForTesting
    static String getParentIdentificatie(final String parentIdentCode, final String generiekeElementOuderIdentCode) {
        if (PARENT_IDENT_CODE_UITZONDERINGEN.contains(parentIdentCode)) {
            return generiekeElementOuderIdentCode;
        }
        if (GENERIEKE_ELEMENT_OUDER_IDENT_CODE_UITZONDERINGEN.contains(generiekeElementOuderIdentCode)) {
            return generiekeElementOuderIdentCode + parentIdentCode;
        }
        return parentIdentCode;
    }

    @Override
    protected void voegSpecifiekeImportsToe(final Attribuut attribuut, final JavaKlasse klasse) {
        final String javaClass = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());
        if (javaClass != null) {
            klasse.voegExtraImportsToe(new JavaType(javaClass, SymbolTableConstants.LITERALS_PACKAGE));
        }

        final ObjectType objectType;
        if (getParentSymbol() != null) {
            objectType = getParentSymbol().getBmrAttribuut().getObjectType();
        } else {
            objectType = attribuut.getObjectType();
        }

        klasse.voegExtraImportsToe(
            new JavaType(getParentKlassenaam(), getHistorischeModelKlasse(objectType).getPackagePad()));
    }

    /**
     * Geeft de naam van de klasse waartoe het attribuut (de groep) behoort.
     *
     * @return De naam van de klasse waartoe het attribuut (de groep) behoort.
     */
    private String getParentKlassenaam() {
        if (getParentSymbol() != null && getParentSymbol().getJavaType() != null) {
            return getParentSymbol().getJavaType();
        } else {
            return SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_TYPE.get(getObjectTypeNaam());
        }
    }

}
