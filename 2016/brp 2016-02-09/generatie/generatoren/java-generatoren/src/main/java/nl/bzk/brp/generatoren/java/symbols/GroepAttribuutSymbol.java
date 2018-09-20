/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.naamgeving.HisMomentNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Symbolen voor de symbol table. De symbolen zijn afgeleid van attributen en worden afgebeeld op een basistype.
 */
public final class GroepAttribuutSymbol extends AbstractSymbol {

    private static final NaamgevingStrategie HIS_MOMENT_NAAMGEVING_STRATEGIE
        = new HisMomentNaamgevingStrategie();

    private final String expressieType;
    private final String javaType;

    /**
     * Creëert een symbool met gegeven syntax dat wordt afgebeeld op het gegeven type en tot de gegeven groep
     * behoort.
     * @param groepNaam      Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het symbool behoort.
     * @param bmrAttribuut   Attribuut uit het BMR waarmee het symbool naar verwijst.
     * @param parentSymbol   Geïndiceerde symbool/attribuut waar het symbool onderdeel van uitmaakt.
     * @param generator      Generator waarin het symbool wordt gebruikt.
     * @param logischeGroep          Naam van de logische groep waartoe het attribuut behoort
     */
    public GroepAttribuutSymbol(final String groepNaam, final String objectTypeNaam, final Attribuut bmrAttribuut,
        final Symbol parentSymbol, final AbstractGenerator generator, final Groep logischeGroep)
    {
        super(bmrAttribuut, groepNaam, objectTypeNaam, false, parentSymbol, generator, logischeGroep, null);
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
            if(attribuut.getIdentCode().equalsIgnoreCase("DatumTijdRegistratie")) {
                attribuut.setIdentCode("TijdstipRegistratie");
            }
            result = new JavaAccessPath(String.format(GETTER_FORMAT, attribuut.getIdentCode()));
            if (metGroep) {
                //omdat attribuut.getGroep null oplevert wordt de logische groep bevraagd
                if (getParentGroep() != null) {
                    result = new JavaAccessPath(String.format(GETTER_FORMAT, getParentGroep().getIdentCode()), result);
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
            String identCode = attribuut.getIdentCode();
            if (identCode.equalsIgnoreCase("DatumTijdRegistratie")) {
                identCode = "TijdstipRegistratie";
            }
            result = new JavaAccessPath(String.format(GETTER_FORMAT, identCode));
            result = new JavaAccessPath("getActueleRecord()", result);
            final String klassenaam = stripHisPrefix(attribuut.getObjectType().getIdentCode());
            final String ident;
            if ("Persoon".equalsIgnoreCase(klassenaam)) {
                ident = klassenaam + attribuut.getGroep().getIdentCode();
            } else {
                ident = klassenaam;
            }
            final String historieMethode = "get" + ident + "Historie()";
            result = new JavaAccessPath(historieMethode, result);
        }
        return result;
    }

    @Override
    protected BodyEnImports maakWaardeGetterBody() {
        final String expressieTypeClass =
                SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());
        final StringBuilder body = new StringBuilder();
        body.append(String.format("%s resultaat = %s;%n", SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam(),
            SymbolTableConstants.NULLVALUE_EXPRESSIE))
            .append(String.format("final %s attribuut = %s(%s);%n", SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(), SymbolTableConstants
                .GETATTRIBUUT_METHODENAAM, SymbolTableConstants.OBJECTPARAMETERNAAM))
            .append(String.format("if (attribuut != null) {%n"))
            .append(String.format("resultaat = %s;%n", maakExpressieConstructor(expressieTypeClass, String.format("attribuut.%s()%n",
                SymbolTableConstants.BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM ))))
            .append(String.format("}%n"))
            .append("return resultaat;");

        final List<JavaType> imports = new ArrayList<>();
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
        imports.add(SymbolTableConstants.NULLVALUE_JAVATYPE);
        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakAttribuutGetterBody() {
        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        body.append(String.format("%s resultaat = null;%n", SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam()));
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
        maakGetterBodyVoorParentKlassenaam(body);
        body.append("else ");
        maakGetterBodyVoorActueleLaag(body, imports);
        body.append("return resultaat;");
        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakHistorischeAttributenGetterBody() {
        final Attribuut attribuut = getBmrAttribuut();
        final JavaType modelKlasse = getModelKlasse(attribuut.getObjectType());
        final String klassenaam = getParentKlassenaam();

        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        if (getJavaAccessPathNaarAttribuutZonderGroep() != null) {

            body.append(String.format("final List<%s> attributen = new ArrayList<%s>();%n",
                    SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
                    SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam()));
            imports.add(JavaType.LIST);
            imports.add(JavaType.ARRAY_LIST);
            imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

            String ident;
            if (attribuut.getObjectType().getIdentCode().equalsIgnoreCase("Persoon")) {
                ident = modelKlasse.getNaam() + attribuut.getGroep().getIdentCode();
            } else {
                ident = modelKlasse.getNaam();
            }
            ident = stripHisPrefix(ident);

            final String historieMethode = "get" + ident + "Historie";
            final String packageName =
                    NAAMGEVINGSSTRATEGIE_OPERATIONEEL.getJavaTypeVoorElement(attribuut.getObjectType())
                            .getPackagePad();
            final String iteratorGenericParameter = packageName + ".His" + ident + "Model";

            final String attrValue =
                    String.format("iterator.next().%s", getJavaAccessPathNaarAttribuutZonderGroep().toString());

            body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                    klassenaam))
                    .append(String.format("final %s p = (%s) %s;", klassenaam, klassenaam,
                            SymbolTableConstants.OBJECTPARAMETERNAAM))
                    .append(String.format("final Iterator<%s> iterator = p.%s().getHistorie().iterator();",
                            iteratorGenericParameter, historieMethode))
                    .append(String.format("while (iterator.hasNext()) {%n"))
                    .append(String.format("attributen.add(%s);%n", attrValue))
                    .append("}\n}\n")
                    .append("return attributen;");
            imports.add(JavaType.ITERATOR);
        } else {
            body.append("return null;");
        }
        return new BodyEnImports(body.toString(), imports);
    }

    private void maakGetterBodyVoorActueleLaag(final StringBuilder body, final List<JavaType> imports) {
        //
        final JavaType javaTypeVoorObjectType = HIS_MOMENT_NAAMGEVING_STRATEGIE.getJavaTypeVoorElement(getParentGroep().getObjectType());
        imports.add(javaTypeVoorObjectType);

        body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM, javaTypeVoorObjectType.getNaam()))
            .append(String.format("final %s v = (%s) %s;%n",
                javaTypeVoorObjectType.getNaam(), javaTypeVoorObjectType.getNaam(), SymbolTableConstants.OBJECTPARAMETERNAAM));

        final StringBuilder controlPath = new StringBuilder("v");
        final JavaAccessPath javaAccessPath = bepaalJavaAccessPathNaarAttribuut(getBmrAttribuut(), true);
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
    }

    /**
     * Maakt de body van een standaard getter die ofwel een attribuut of de waarde van een attribuut oplevert.
     *
     * @param body De body van de functie waaraan de code toegevoegd moet worden.
     */
    private void maakGetterBodyVoorParentKlassenaam(final StringBuilder body) {
        final String klassenaam = getParentKlassenaam();
        body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM, klassenaam))
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
    }

    @Override
    protected void voegSpecifiekeImportsToe(final Attribuut attribuut, final JavaKlasse klasse) {
        final String javaClass = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());
        if (javaClass != null) {
            klasse.voegExtraImportsToe(new JavaType(javaClass, SymbolTableConstants.LITERALS_PACKAGE));
        }
        ObjectType objectType;
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
        if (getParentSymbol() != null) {
            return getParentSymbol().getJavaType();
        } else {
            return SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_TYPE.get(getObjectTypeNaam());
        }
    }

    /**
     * Geeft een string terug die gelijk is aan de waarde van het argument exclusief een eventuele prefix 'His' of
     * 'His_'.
     *
     * @param ident Waarde waar de prefix van afgehaald moet worden.
     * @return String zonder prefix 'His' of 'His_'.
     */
    private String stripHisPrefix(final String ident) {
        String result;
        final String hisPrefix = "His";
        if (ident.startsWith(hisPrefix + "_")) {
            result = ident.substring(hisPrefix.length() + 1);
        } else if (ident.startsWith(hisPrefix)) {
            result = ident.substring(hisPrefix.length());
        } else {
            result = ident;
        }
        return result;
    }

}
