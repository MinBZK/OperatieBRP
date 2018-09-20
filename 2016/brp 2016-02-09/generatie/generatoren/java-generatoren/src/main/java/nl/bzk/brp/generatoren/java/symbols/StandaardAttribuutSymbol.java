/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Symbolen voor de symbol table. De symbolen zijn afgeleid van attributen en worden afgebeeld op een basistype.
 */
public final class StandaardAttribuutSymbol extends AbstractSymbol {

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
    public StandaardAttribuutSymbol(final String groepNaam, final String objectTypeNaam, final Attribuut bmrAttribuut,
                                    final Symbol parentSymbol, final AbstractGenerator generator)
    {
        super(bmrAttribuut, groepNaam, objectTypeNaam, false, parentSymbol, generator);
        this.expressieType = Utils.getExpressieBasisType(bmrAttribuut, generator);
        this.javaType = Utils.getJavaBasisType(bmrAttribuut, generator);
    }

    @Override
    public boolean isIndexed() {
        return false;
    }

    public String getExpressieType() {
        return expressieType;
    }

    public String getJavaType() {
        return javaType;
    }

    @Override
    public String getGetterJavaDoc() {
        final JavaType modelKlasse = getModelKlasse(getBmrAttribuut().getObjectType());
        return String.format("Getter voor '%s' in objecttype '%s'.", getSyntax(), modelKlasse.getNaam());
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarWaarde(final Attribuut attribuut) {
        JavaAccessPath result = new JavaAccessPath(String.format(GETTER_FORMAT, attribuut.getIdentCode()));
        final Groep gr = attribuut.getGroep();
        if (gr != null && !getGenerator().IDENTITEIT.equals(gr.getIdentCode())) {
            result = new JavaAccessPath(String.format(GETTER_FORMAT, gr.getIdentCode()), result);
        }

        if (getGenerator().isAttribuutTypeAttribuut(attribuut)) {
            result.voegToe(new JavaAccessPath(SymbolTableConstants.BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM + "()"));
        } else {
            final ObjectType ot = getGenerator().getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);

            if (ot.getSoortInhoud() != BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode()) {
                Attribuut logischeIdentiteit = getGenerator().bepaalLogischeIdentiteitVoorStamgegeven(ot);
                result.voegToe(new JavaAccessPath(String.format(GETTER_FORMAT, logischeIdentiteit.getIdentCode())));
                if (getGenerator().isDynamischStamgegevenAttribuut(attribuut)) {
                    result.voegToe(
                            new JavaAccessPath(SymbolTableConstants.BRP_ATTRIBUUT_GET_WAARDE_METHODENAAM + "()"));
                }
            }
        }
        return result;
    }

    /**
     * Geeft TRUE als het pad naar het attribuut 'regulier' is, oftewel een standaard pad van groepen en attribuutnamen.
     * Dat geldt in ieder geval voor normale attributen en stamgegevens.
     *
     * @param attribuut Attribuut dat gecontroleerd moet worden.
     * @return TRUE als het pad naar het attribuut 'regulier' is.
     */
    private boolean kanRegulierPadBepalen(final Attribuut attribuut) {
        return isNormaalAttribuut(attribuut) || getGenerator().isStamgegevenAttribuut(attribuut)
                || attribuut.getType().getNaam().equals("Ja")
                || attribuut.getType().getNaam().equals("Nee")
                || attribuut.getType().getNaam().equals("Aanduiding bij huisnummer")
                || attribuut.getType().getNaam().equals("Locatie ten opzichte van adres");
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarAttribuut(final Attribuut attribuut, final boolean metGroep) {
        JavaAccessPath result = null;
        if (kanRegulierPadBepalen(attribuut)) {
            result = new JavaAccessPath(String.format(GETTER_FORMAT, attribuut.getIdentCode()));
            if (metGroep) {
                final Groep gr = attribuut.getGroep();
                if (gr != null && !getGenerator().IDENTITEIT.equals(gr.getIdentCode())) {
                    result = new JavaAccessPath(String.format(GETTER_FORMAT, gr.getIdentCode()), result);
                }
            } else {
                // Het is (bij uitzondering) mogelijk dat een identiteitsgroep historie heeft. In dat geval is het pad
                // naar attributen afwijkend.
                Groep groep = getBmrAttribuut().getGroep();
                if (groep != null && groep.getNaam().equals("Identiteit")
                        && groep.getHistorieVastleggen().charValue() == 'F')
                {
                    result = new JavaAccessPath(String.format(GETTER_FORMAT, groep.getObjectType().getIdentCode()),
                            result);
                }
            }
        }
        return result;
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarActueelRecordAttribuut(final Attribuut attribuut) {
        JavaAccessPath result = null;
        if (kanRegulierPadBepalen(attribuut)) {
            result = new JavaAccessPath(String.format(GETTER_FORMAT, attribuut.getIdentCode()));

            // Het is (bij uitzondering) mogelijk dat een identiteitsgroep historie heeft. In dat geval is het pad
            // naar attributen afwijkend.
            final Groep groep = getBmrAttribuut().getGroep();
            if (groep != null && groep.getNaam().equals("Identiteit")
                    && groep.getHistorieVastleggen().charValue() == 'F')
            {
                result = new JavaAccessPath(String.format(GETTER_FORMAT, groep.getObjectType().getIdentCode()), result);
            }

            result = new JavaAccessPath("getActueleRecord()", result);
            final JavaType modelKlasse = getModelKlasse(attribuut.getObjectType());
            String ident = modelKlasse.getNaam();
            if (moetIdentCodeToevoegen(attribuut)) {
                ident = ident + attribuut.getGroep().getIdentCode();
            }
            final String historieMethode = "get" + ident + "Historie()";
            result = new JavaAccessPath(historieMethode, result);
        }
        return result;
    }

    @Override
    protected BodyEnImports maakWaardeGetterBody() {
        final Attribuut attribuut = getBmrAttribuut();
        final String expressieTypeClass =
                SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(expressieType);

        final JavaType modelKlasse = getModelKlasse(getBmrAttribuut().getObjectType());
        final JavaType historischeModelKlasse = getHistorischeModelKlasse(getBmrAttribuut().getObjectType());

        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        if (getJavaAccessPathNaarAttribuut() != null) {
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
                waarde = "attribuut.getWaarde()";
            }

            body.append(String.format("%s resultaat = %s;%n", SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam(),
                    SymbolTableConstants.NULLVALUE_EXPRESSIE));
            imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
            imports.add(SymbolTableConstants.NULLVALUE_JAVATYPE);

            if (getBmrAttribuut().getGroep().getHistorieVastleggen() != 'G') {
                body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                        modelKlasse.getNaam()));
                imports.add(modelKlasse);
            } else {
                body.append(String.format("if (%s instanceof %s || %s instanceof %s) {%n",
                        SymbolTableConstants.OBJECTPARAMETERNAAM,
                        modelKlasse.getNaam(), SymbolTableConstants.OBJECTPARAMETERNAAM,
                        historischeModelKlasse.getNaam()));
                imports.add(modelKlasse);
                imports.add(historischeModelKlasse);
            }
            body.append(String.format(
                    "final %s attribuut = %s(%s);%n",
                    SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
                    SymbolTableConstants.GETATTRIBUUT_METHODENAAM,
                    SymbolTableConstants.OBJECTPARAMETERNAAM))
                    .append(String.format("if (attribuut != null) {%n"))
                    .append(String.format("resultaat = %s;%n", maakExpressieConstructor(expressieTypeClass, waarde)))
                    .append(String.format("}%n"));
            imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

            if (getBmrAttribuut().getGroep().getHistorieVastleggen() != 'G') {
                body.append(String.format("} else if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                        historischeModelKlasse.getNaam()))
                        .append(String.format("final List<%s> attributen = %s(%s);%n",
                                SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
                                SymbolTableConstants.GETHISTORISCHEATTRIBUTEN_METHODENAAM,
                                SymbolTableConstants.OBJECTPARAMETERNAAM))
                        .append(String.format("if (attributen != null) {%n"))
                        .append(String.format("final List<%s> elementen = new ArrayList<%s>();%n",
                                SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam(),
                                SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam()))
                        .append(String.format("for (final %s attribuut : attributen) {%n",
                                SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam()))
                        .append(String.format("if (attribuut != null) {%n"))
                        .append(String.format("elementen.add(%s);%n", maakExpressieConstructor(expressieTypeClass,
                                waarde)))
                        .append(String.format("}%n"))
                        .append(String.format("}%n"))
                        .append(String.format("resultaat = new %s(elementen);",
                                SymbolTableConstants.LIJSTEXPRESSIE_JAVATYPE.getNaam()))
                        .append(String.format("}%n"))
                        .append(String.format("}%n"));
                imports.add(historischeModelKlasse);
                imports.add(JavaType.LIST);
                imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
                imports.add(JavaType.ARRAY_LIST);
                imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
                imports.add(SymbolTableConstants.LIJSTEXPRESSIE_JAVATYPE);
            } else {
                body.append(String.format("}%n"));
            }
            body.append(String.format("return resultaat;"));
        } else {
            body.append(String.format("Expressie resultaat = %s;", SymbolTableConstants.NULLVALUE_EXPRESSIE));
            imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
            imports.add(SymbolTableConstants.NULLVALUE_JAVATYPE);

            maakGetterBody(true, false, getModelKlasse(getBmrAttribuut().getObjectType()),
                Utils.isStatischStamgegevenAttribuut(attribuut),
                expressieTypeClass, body, imports);
            body.append("else ");
            maakGetterBody(true, true, getHistorischeModelKlasse(getBmrAttribuut().getObjectType()),
                    Utils.isStatischStamgegevenAttribuut(attribuut), expressieTypeClass, body, imports);
            body.append("return resultaat;");
        }

        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakAttribuutGetterBody() {
        final Attribuut attribuut = getBmrAttribuut();

        final boolean isStatischStamgegeven = Utils.isStatischStamgegevenAttribuut(attribuut);
        final String expressieTypeClass = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());

        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        if (getJavaAccessPathNaarAttribuut() != null) {
            body.append(String.format("%s resultaat = null;%n", SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam()));
            imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
            maakGetterBody(false, false, getModelKlasse(attribuut.getObjectType()), isStatischStamgegeven,
                           expressieTypeClass, body, imports);

            body.append("else ");

            if (isStatischStamgegeven && !(attribuut.getObjectType().getNaam().equals("Betrokkenheid")
                    && getObjectTypeNaam().equals("PERSOON")))
            {
                String viewKlassenaam;
                String historischeKlassenaam;
                if (getObjectTypeNaam().equals("HUWELIJK")) {
                    // Huwelijk is een subklasse van Relatie en heeft wat specifieke kenmerken die niet op het niveau
                    // van Relatie toegankelijk zijn, dus ingrijpen is nodig.
                    viewKlassenaam = "HuwelijkView";
                    historischeKlassenaam = "HuwelijkHisVolledig";
                } else if (getObjectTypeNaam().equals("GEREGISTREERDPARTNERSCHAP")) {
                    viewKlassenaam = "GeregistreerdPartnerschapView";
                    historischeKlassenaam = "GeregistreerdPartnerschapHisVolledig";
                } else if (getObjectTypeNaam().equals("FAMILIERECHTELIJKEBETREKKING")) {
                    viewKlassenaam = "FamilierechtelijkeBetrekkingView";
                    historischeKlassenaam = "FamilierechtelijkeBetrekkingHisVolledig";
                } else if ((attribuut.getObjectType().getNaam().equals("Betrokkenheid")) && !getObjectTypeNaam().equals("PERSOON")) {
                    viewKlassenaam = getObjectTypeNaam() + "View";
                    historischeKlassenaam = getObjectTypeNaam() + "HisVolledig";
                } else {
                    viewKlassenaam = getModelKlasse(attribuut.getObjectType()).getNaam() + "View";
                    historischeKlassenaam = getHistorischeModelKlasse(attribuut.getObjectType()).getNaam();
                }

                String viewConstructor;

                if (viewKlassenaam.equals("PersoonView")) {
                    viewConstructor = String.format("final %s x = new %s((%s) %s);%n",
                            getModelKlasse(attribuut.getObjectType()).getNaam(),
                            viewKlassenaam,
                            historischeKlassenaam,
                            SymbolTableConstants.OBJECTPARAMETERNAAM);
                    imports.add(getModelKlasse(attribuut.getObjectType()));
                    imports.add(new JavaType(viewKlassenaam, "nl.bzk.brp.model.hisvolledig.momentview.kern"));
                } else {
                    viewConstructor =
                            String.format("final %s x = new %s((%s) %s, new DatumTijdAttribuut(), new DatumAttribuut());%n",
                                    getModelKlasse(attribuut.getObjectType()).getNaam(),
                                    viewKlassenaam,
                                    historischeKlassenaam,
                                    SymbolTableConstants.OBJECTPARAMETERNAAM);
                    imports.add(getModelKlasse(attribuut.getObjectType()));
                    imports.add(new JavaType(viewKlassenaam, "nl.bzk.brp.model.hisvolledig.momentview.kern"));
                    imports.add(new JavaType(historischeKlassenaam, "nl.bzk.brp.model.hisvolledig.kern"));

                    final String datumAttributenPackage = "nl.bzk.brp.model.algemeen.attribuuttype.kern";
                    imports.add(new JavaType("DatumAttribuut", datumAttributenPackage));
                    imports.add(new JavaType("DatumTijdAttribuut", datumAttributenPackage));
                }

                body.append(String.format("if (%s instanceof %s) {", SymbolTableConstants.OBJECTPARAMETERNAAM, historischeKlassenaam));
                body.append(viewConstructor);
                body.append(String.format("resultaat = getAttribuut(x);%n"));
                body.append("}");
            } else if (getBmrAttribuut().getGroep().getHistorieVastleggen() != 'G') {
                maakGetterBody(false, true, getHistorischeModelKlasse(attribuut.getObjectType()),
                        isStatischStamgegeven, expressieTypeClass, body, imports);
            } else {
                maakGetterBody(false, false, getHistorischeModelKlasse(attribuut.getObjectType()),
                        isStatischStamgegeven, expressieTypeClass, body, imports);
            }
            body.append("return resultaat;");
        } else {
            body.append("return null;");
        }

        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakHistorischeAttributenGetterBody() {
        final Attribuut attribuut = getBmrAttribuut();
        final JavaType modelKlasse = getModelKlasse(attribuut.getObjectType());
        final JavaType historischeModelKlasse = getHistorischeModelKlasse(attribuut.getObjectType());

        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        if (getJavaAccessPathNaarAttribuutZonderGroep() != null) {

            body.append(String.format("final List<%s> attributen = new ArrayList<%s>();%n",
                    SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
                    SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam()));
            imports.add(JavaType.LIST);
            imports.add(JavaType.ARRAY_LIST);
            imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

            if (attribuut.getGroep().getHistorieVastleggen() != 'G') {
                body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                        modelKlasse.getNaam()))
                        .append(String.format("final %s attribuut = %s(%s);%n",
                                SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
                                SymbolTableConstants.GETATTRIBUUT_METHODENAAM,
                                SymbolTableConstants.OBJECTPARAMETERNAAM))
                        .append("if (attribuut != null) { attributen.add(attribuut); }\n")
                        .append("}\n");
                imports.add(modelKlasse);
                imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

                String ident;
                if (moetIdentCodeToevoegen(attribuut)) {
                    ident = modelKlasse.getNaam() + attribuut.getGroep().getIdentCode();
                } else {
                    ident = modelKlasse.getNaam();
                }
                final String historieMethode = "get" + ident + "Historie";
                final String packageName = NAAMGEVINGSSTRATEGIE_OPERATIONEEL.getJavaTypeVoorElement(attribuut.getObjectType()).getPackagePad();
                final String iteratorGenericParameter = packageName + ".His" + ident + "Model";

                body.append(" else ");

                final String attrValue = String.format("iterator.next().%s", getJavaAccessPathNaarAttribuutZonderGroep().toString());

                body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                        historischeModelKlasse.getNaam()))
                        .append(String.format("final %s p = (%s) %s;", historischeModelKlasse.getNaam(),
                                historischeModelKlasse.getNaam(),
                                SymbolTableConstants.OBJECTPARAMETERNAAM))
                        .append(String.format("final Iterator<%s> iterator = p.%s().getHistorie().iterator();",
                                iteratorGenericParameter, historieMethode))
                        .append(String.format("while (iterator.hasNext()) {%n"))
                        .append(String.format("attributen.add(%s);%n", attrValue))
                        .append("}\n}\n");
                imports.add(historischeModelKlasse);
                imports.add(JavaType.ITERATOR);
            } else {
                body.append(String.format("final %s attribuut = %s(%s);%n",
                        SymbolTableConstants.ATTRIBUUT_JAVATYPE.getNaam(),
                        SymbolTableConstants.GETATTRIBUUT_METHODENAAM,
                        SymbolTableConstants.OBJECTPARAMETERNAAM))
                        .append("if (attribuut != null) { attributen.add(attribuut); }\n");
                imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
            }

            body.append("return attributen;");
        } else {
            body.append("return null;");
        }

        return new BodyEnImports(body.toString(), imports);
    }

    private boolean moetIdentCodeToevoegen(final Attribuut attribuut) {
        boolean result = attribuut.getObjectType().getIdentCode().equalsIgnoreCase("Persoon")
                || (attribuut.getObjectType().getSuperType() != null
                && attribuut.getObjectType().getSuperType().getIdentCode().equalsIgnoreCase("Betrokkenheid"))
                || attribuut.getObjectType().getIdentCode().equalsIgnoreCase("Relatie");
        return result && !attribuut.getGroep().getIdentCode().equals("Standaard");
    }

    /**
     * Maakt de body van een standaard getter die ofwel een attribuut of de waarde van een attribuut oplevert.
     *
     * @param isWaardeGetter        TRUE als de getter de waarde moet opleveren, FALSE als de getter het attribuut zelf
     *                              moet opleveren.
     * @param isHistorischAttribuut TRUE als het een attribuut van een PersoonHisVolledig object is, anders FALSE.
     * @param modelKlasse           De Javaklasse die hoort bij het objecttype.
     * @param statischStamgegeven   TRUE als het een statisch stamgegeven is.
     * @param expressieTypeClass    Het type van de waarde in de expressietaal.
     * @param body                  De body van de functie waaraan de code toegevoegd moet worden.
     * @param imports               De imports die gebruikt worden in de body.
     */
    private void maakGetterBody(final boolean isWaardeGetter, final boolean isHistorischAttribuut,
                                final JavaType modelKlasse, final boolean statischStamgegeven,
                                final String expressieTypeClass, final StringBuilder body, final List<JavaType> imports)
    {
        body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                modelKlasse.getNaam()));
        imports.add(modelKlasse);

        body.append(
                String.format("final %s v = (%s) %s;%n", modelKlasse.getNaam(), modelKlasse.getNaam(),
                        SymbolTableConstants.OBJECTPARAMETERNAAM));
        imports.add(modelKlasse);

        final StringBuilder controlPath = new StringBuilder("v");

        JavaAccessPath javaAccessPath;
        if (isWaardeGetter) {
            javaAccessPath = getJavaAccessPathNaarWaarde();
        } else if (statischStamgegeven) {
            javaAccessPath = getJavaAccessPathNaarAttribuut();
        } else if (isHistorischAttribuut) {
            javaAccessPath = getJavaAccessPathNaarActueelRecordAttribuut();
        } else {
            javaAccessPath = getJavaAccessPathNaarAttribuut();
        }
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

        if (isWaardeGetter) {
            body.append(String.format("resultaat = %s;%n",
                    maakExpressieConstructor(expressieTypeClass, "v." + javaAccessPath)));
        } else if (getBmrAttribuut().getObjectType().getNaam().equals("Persoon \\ Indicatie")
                && isHistorischAttribuut)
        {
            // Door een tekortkoming van Java mbt generic types heeft getActueleRecord niet het juiste type bij het
            // bepalen van het waarde-attribuut. Daarom is een cast nodig, uniek voor dit geval omdat het samenhangt
            // met hoe indicaties gemodelleerd zijn.
            body.append(String.format(
                    "resultaat = ((nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel) "
                            + " v.getPersoonIndicatieHistorie().getActueleRecord()).getWaarde();"));
        } else {
            body.append(String.format("resultaat = v.%s;%n", javaAccessPath));
        }

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

        if (Utils.isStatischStamgegevenAttribuut(getBmrAttribuut())) {
            if (getObjectTypeNaam().equals("HUWELIJK")) {
                final String historischeKlassenaam = "HuwelijkHisVolledig";
                final JavaType hisType = new JavaType(historischeKlassenaam, "nl.bzk.brp.model.hisvolledig.kern");
                klasse.voegExtraImportsToe(hisType);
            } else if (attribuut.getObjectType().getNaam().equals("Betrokkenheid")
                    && !getObjectTypeNaam().equals("PERSOON"))
            {
                final String historischeKlassenaam = getObjectTypeNaam() + "HisVolledig";
                final JavaType hisType = new JavaType(historischeKlassenaam, "nl.bzk.brp.model.hisvolledig.kern");
                klasse.voegExtraImportsToe(hisType);
            }
        }

        if (Utils.isStatischStamgegevenAttribuut(attribuut) || getGenerator().isDynamischStamgegevenAttribuut(
                attribuut))
        {
            final ObjectType ot = getGenerator().getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
            final JavaType tt = NAAMGEVINGSSTRATEGIE.getJavaTypeVoorElement(ot);
            klasse.voegExtraImportsToe(tt);
        }
    }
}
