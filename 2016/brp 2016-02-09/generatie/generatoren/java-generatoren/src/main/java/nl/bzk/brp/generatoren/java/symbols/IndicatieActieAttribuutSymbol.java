/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.SymbolTableNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Tuple;

/**
 * Symbolen voor de symbol table. De symbolen zijn afgeleid van indicaties en worden afgebeeld op een BOOLEAN.
 */
public final class IndicatieActieAttribuutSymbol implements Symbol {

    /**
     * Indicatieattributen kunnen verschillende 'waarden' opleveren. De waarde van de indicatie zelf, de datum
     * aanvang geldigheid en de datum einde geldigheid.
     */
    public enum IndicatieVerantwoordingAttribuutWaarde {
        PARTIJ,
        SOORT,
        TIJDSTIP_REGISTRATIE,
        DATUM_ONTLENING
    }

    /**
     * Het type verantwoording.
     */
    public enum IndicatieVerantwoordingType {
        VERANTWOORDING_INHOUD,
        VERANTWOORDING_VERVAL,
        VERANTWOORDING_AANPASSING_GELDIGHEID
    }

    private static final SymbolTableNaamgevingStrategie SYMBOL_TABLE_NAAMGEVING_STRATEGIE =
            new SymbolTableNaamgevingStrategie();

    private final String                                 attribuutNaam;
    private final String                                 syntax;
    private final Tuple                                  tuple;
    private final IndicatieVerantwoordingAttribuutWaarde waarde;
    private final IndicatieVerantwoordingType            indicatieVerantwoordingType;

    /**
     * Creëert een symbool met gegeven syntax dat wordt afgebeeld op het gegeven type en tot de gegeven groep
     * behoort.
     *
     * @param aGroepNaam                  Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param aTuple                      Tuplewaarde waarvan het symbool is afgeleid.
     * @param indicatieVerantwoordingType Het type verantwoording.
     * @param waarde                      Soort waarde dat het attribuut moet opleveren.
     * @param parentSymbol                Geïndiceerde symbool/attribuut waar het symbool onderdeel van uitmaakt.
     */
    public IndicatieActieAttribuutSymbol(final String aGroepNaam, final Tuple aTuple,
                                         final IndicatieVerantwoordingType indicatieVerantwoordingType,
                                         final IndicatieVerantwoordingAttribuutWaarde waarde,
                                         final Symbol parentSymbol)
    {
        this.tuple = aTuple;
        this.waarde = waarde;
        this.indicatieVerantwoordingType = indicatieVerantwoordingType;

        String volledigeAttribuutNaam =
                SYMBOL_TABLE_NAAMGEVING_STRATEGIE.bepaalAttribuutNaam(aTuple.getNaam(), aGroepNaam,
                        "PersoonIndicatieHisVolledig");

        switch (indicatieVerantwoordingType) {
            case VERANTWOORDING_INHOUD:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".verantwoordingInhoud";
                break;
            case VERANTWOORDING_VERVAL:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".verantwoordingVerval";
                break;
            case VERANTWOORDING_AANPASSING_GELDIGHEID:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".verantwoordingAanpassingGeldigheid";
                break;
        }

        switch (waarde) {
            case SOORT:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".soort";
                break;
            case PARTIJ:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".partij";
                break;
            case TIJDSTIP_REGISTRATIE:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".tijdstip_registratie";
                break;
            case DATUM_ONTLENING:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".datum_ontlening";
                break;
            default:
                break;
        }

        this.attribuutNaam =
                SYMBOL_TABLE_NAAMGEVING_STRATEGIE.getEnumWaarde(volledigeAttribuutNaam);
        this.syntax = SYMBOL_TABLE_NAAMGEVING_STRATEGIE.vereenvoudigSyntax(
                volledigeAttribuutNaam, aGroepNaam, parentSymbol, "PersoonIndicatieHisVolledig",
                null);
    }

    @Override
    public String getEnumNaam() {
        return attribuutNaam;
    }

    @Override
    public String getSyntax() {
        return syntax;
    }

    @Override
    public boolean isIndexed() {
        return false;
    }

    @Override
    public String getExpressieType() {
        String expressieType;
        switch (waarde) {
            case DATUM_ONTLENING:
                expressieType = SymbolTableConstants.DATE_TYPE;
                break;
            case PARTIJ:
                expressieType = SymbolTableConstants.NUMBER_TYPE;
                break;
            default:
                expressieType = SymbolTableConstants.STRING_TYPE;
        }
        return expressieType;
    }

    @Override
    public String getJavaType() {
        String expressieType;
        switch (waarde) {
            case DATUM_ONTLENING:
                expressieType = SymbolTableConstants.DATE_TYPE;
                break;
            case PARTIJ:
                expressieType = SymbolTableConstants.NUMBER_TYPE;
                break;
            default:
                expressieType = SymbolTableConstants.STRING_TYPE;
        }
        return expressieType;
    }

    @Override
    public String getParentExpressieType() {
        return "PERSOON";
    }

    @Override
    public Attribuut getBmrAttribuut() {
        return null;
    }

    @Override
    public Symbol getParentSymbol() {
        return null;
    }

    @Override
    public String getObjectTypeNaam() {
        return "PersoonIndicatieHisVolledig";
    }

    @Override
    public String getEnumeratieJavaDoc() {
        String doc = "";
        switch (waarde) {
            case SOORT:
                doc = String.format("Soort van de verantwoording van indicatie %s", getEnumNaam());
                break;
            case PARTIJ:
                doc = String.format("Partij van de verantwoording van indicatie %s", getEnumNaam());
                break;
            case TIJDSTIP_REGISTRATIE:
                doc = String.format("TSReg van de verantwoording van indicatie %s", getEnumNaam());
                break;
            case DATUM_ONTLENING:
                doc = String.format("Datum ontlening van de verantwoording van indicatie %s", getEnumNaam());
                break;
            default:
                break;
        }
        return doc;
    }

    @Override
    public String getGetterJavaDoc() {
        String doc = "";
        switch (waarde) {
            case SOORT:
                doc = String.format(
                        "Getter voor soort van de verantwoording van indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            case PARTIJ:
                doc = String.format(
                        "Getter voor partij van de verantwoording van indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            case TIJDSTIP_REGISTRATIE:
                doc = String.format(
                        "Getter voor TSReg van de verantwoording van indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            case DATUM_ONTLENING:
                doc = String.format(
                        "Getter voor datum ontlening van de verantwoording  van indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            default:
                break;
        }
        return doc;
    }

    @Override
    public String getGetterClassName() {
        return JavaGeneratieUtil.camelCase(getEnumNaam().replace('_', ' ').toLowerCase()).replaceAll(" ", "") + "Getter";
    }

    @Override
    public JavaKlasse maakGetterKlasse() {
        final JavaKlasse klasse =
                new JavaKlasse(getGetterClassName(), getGetterJavaDoc(), SymbolTableConstants.SOLVERS_PACKAGE);
        klasse.voegSuperInterfaceToe(SymbolTableConstants.ATTRIBUTEGETTER_JAVATYPE);

        final String javaClass = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());
        if (javaClass != null) {
            klasse.voegExtraImportsToe(new JavaType(javaClass, SymbolTableConstants.LITERALS_PACKAGE));
            klasse.voegExtraImportsToe(new JavaType("ActieModel", "nl.bzk.brp.model.operationeel.kern"));
        }
        final JavaType persoonHisVolledigType = new JavaType("PersoonHisVolledig", "nl.bzk.brp.model.hisvolledig.kern");
        klasse.voegExtraImportsToe(persoonHisVolledigType);

        final JavaType indicatieType = new JavaType(String.format("HisPersoon%sModel", tuple.getIdentCode()),
                "nl.bzk.brp.model.operationeel.kern");
        klasse.voegExtraImportsToe(indicatieType);

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
     * Maakt de body van de getterfunctie die de waarde van een attribuut oplevert.
     *
     * @return Getterfunctie.
     */
    private BodyEnImports maakWaardeGetterBody() {
        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();
        final String expressieInstantiatie;
        switch (waarde) {
            case SOORT:
            case TIJDSTIP_REGISTRATIE:
                imports.add(new JavaType("StringLiteralExpressie", SymbolTableConstants.LITERALS_PACKAGE));
                expressieInstantiatie = "new StringLiteralExpressie(attribuut.getWaarde())";
                break;
            case DATUM_ONTLENING:
                imports.add(new JavaType("DatumLiteralExpressie", SymbolTableConstants.LITERALS_PACKAGE));
                expressieInstantiatie = "new DatumLiteralExpressie(attribuut.getWaarde())";
                break;
            case PARTIJ:
                imports.add(new JavaType("GetalLiteralExpressie", SymbolTableConstants.LITERALS_PACKAGE));
                imports.add(new JavaType("Partij", "nl.bzk.brp.model.algemeen.stamgegeven.kern"));
                expressieInstantiatie = "new GetalLiteralExpressie(((Partij) attribuut.getWaarde()).getCode().getWaarde())";
                break;
            default:
                expressieInstantiatie = "";
                break;
        }

        body.append(String.format("Expressie resultaat = %s;", SymbolTableConstants.NULLVALUE_EXPRESSIE));
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
        imports.add(SymbolTableConstants.NULLVALUE_JAVATYPE);

        //hismoment
        body.append("if(brpObject instanceof PersoonHisMoment) {")
            .append("final Attribuut attribuut = getAttribuut(brpObject);")
            .append("if(attribuut != null) {")
            .append(String.format("resultaat = %s;", expressieInstantiatie))
            .append("}")
        .append("}");
        //hisvolledig
        body.append("else if (brpObject instanceof PersoonHisVolledig) {")
                .append("    final List<Attribuut> attributen = getHistorischeAttributen(brpObject);")
                .append("    if (attributen != null) {")
                .append("        final List<Expressie> elementen = new ArrayList<Expressie>();")
                .append("        for (Attribuut attribuut : attributen) {")
                .append(String.format(
                        "            if (attribuut != null) {%n"))
                .append(String.format(
                        "                elementen.add(%s);", expressieInstantiatie))
                .append("            }")
                .append("        }")
                .append(String.format(
                        "        resultaat = new %s(elementen);", SymbolTableConstants.LIJSTEXPRESSIE_KLASSENAAM))
                .append("    }")
                .append("}")
                .append("return resultaat;");
        imports.add(JavaType.LIST);
        imports.add(JavaType.ARRAY_LIST);
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
        imports.add(SymbolTableConstants.LIJSTEXPRESSIE_JAVATYPE);

        return new BodyEnImports(body.toString(), imports);
    }

    /**
     * Maakt de body van de getterfunctie die een attribuut (niet de waarde) oplevert.
     *
     * @return Getterfunctie.
     */
    private BodyEnImports maakAttribuutGetterBody() {
        final StringBuilder body = new StringBuilder();
        final String getVerantwoordingMethode = maakGetterVoorVerantwoording();
        final List<JavaType> imports = new ArrayList<>();

        String getMethode = "";
        switch (waarde) {
            case SOORT:
                getMethode += ".getSoort";
                break;
            case PARTIJ:
                getMethode += ".getPartij";
                break;
            case TIJDSTIP_REGISTRATIE:
                getMethode += ".getTijdstipRegistratie";
                break;
            case DATUM_ONTLENING:
                getMethode += ".getDatumOntlening";
                break;
            default:
                break;
        }

        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
        imports.add(SymbolTableConstants.PERSOON_HIS_MOMENT_JAVATYPE);
        imports.add(SymbolTableConstants.PERSOON_INDICATIE_JAVATYPE);
        imports.add(SymbolTableConstants.PERSOON_HIS_INDICATIE_STANDAARD_GROEP_JAVATYPE);

        //hismoment
        body.append("Attribuut resultaat = null;");
        body.append("if (brpObject instanceof PersoonHisMoment) {")
                .append("final PersoonHisMoment persoonHisMoment = (PersoonHisMoment) brpObject;")
            .append(String.format("final PersoonIndicatie indicatie = persoonHisMoment.get%s();", tuple.getIdentCode()))
                .append("if(indicatie != null) {")
            .append("final HisPersoonIndicatieStandaardGroep standaard = (HisPersoonIndicatieStandaardGroep) indicatie.getStandaard();")
            .append(String.format("if (standaard.%s != null) {", getVerantwoordingMethode))
            .append(String.format("return standaard.%s%s();", getVerantwoordingMethode, getMethode))
            .append("}")
            .append("}")
        .append("}");
        //hisvolledig
        body.append("else if (brpObject instanceof PersoonHisVolledig) {")
                .append("    final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;")
                .append(String.format(
                        "    if (p.get%s() != null) {", tuple.getIdentCode()))
                .append(String.format("final ActieModel actieModel =\n"
                        + "                        p.get%s().getPersoonIndicatieHistorie()"
                        + ".getActueleRecord().%s;", tuple.getIdentCode(), getVerantwoordingMethode))
            .append("       if (actieModel != null) {")
                .append(String.format(
                        "        resultaat = actieModel%s();", getMethode))
                .append("       }")
                .append("    }")
            .append("}");

        body.append("return resultaat;");
        imports.add(JavaType.ACTIE_MODEL);
        return new BodyEnImports(body.toString(), imports);
    }

    private String maakGetterVoorVerantwoording() {
        final String getMethode;
        switch (indicatieVerantwoordingType) {
            case VERANTWOORDING_INHOUD:
                getMethode = "getVerantwoordingInhoud()";
                break;
            case VERANTWOORDING_VERVAL:
                getMethode = "getVerantwoordingVerval()";
                break;
            case VERANTWOORDING_AANPASSING_GELDIGHEID:
                getMethode = "getVerantwoordingAanpassingGeldigheid()";
                break;
            default:
                getMethode = "";
        }
        return getMethode;
    }

    /**
     * Maakt de body van de getterfunctie die alle historische attributen oplevert, indien aanwezig.
     *
     * @return de body van de methode, inclusief de gebruikte imports.
     */
    private BodyEnImports maakHistorischeAttributenGetterBody() {
        final StringBuilder body = new StringBuilder();
        final String getMethodeVerantwoording = maakGetterVoorVerantwoording();
        final List<JavaType> imports = new ArrayList<>();

        String getMethode = "";
        switch (waarde) {
            case SOORT:
                getMethode += ".getSoort";
                break;
            case PARTIJ:
                getMethode += ".getPartij";
                break;
            case TIJDSTIP_REGISTRATIE:
                getMethode += ".getTijdstipRegistratie";
                break;
            case DATUM_ONTLENING:
                getMethode += ".getDatumOntlening";
                break;
            default:
                break;
        }
        body.append("final List<Attribuut> attributen = new ArrayList<Attribuut>();")
                .append("if (brpObject instanceof PersoonHisVolledig) {")
                .append("    final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;")
                .append(String.format(
                        "    if (p.get%s() != null) {", tuple.getIdentCode()))
                .append(String.format(
                        "        final Iterator<HisPersoon%sModel> iterator =", tuple.getIdentCode()))
                .append(String.format(
                        "            p.get%s().getPersoonIndicatieHistorie().iterator();",
                        tuple.getIdentCode()))
                .append("        while (iterator.hasNext()) {")
                .append(String.format("final ActieModel actieModel = iterator.next().%s;", getMethodeVerantwoording))
                .append("           if (actieModel != null) {")
                .append(String.format("      attributen.add(actieModel%s());", getMethode))
                .append("           }")
                .append("        }")
            .append("    }")
            .append("}")
            .append("return attributen;");
        imports.add(JavaType.LIST);
        imports.add(JavaType.ARRAY_LIST);
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
        imports.add(JavaType.ITERATOR);
        imports.add(JavaType.ACTIE_MODEL);
        return new BodyEnImports(body.toString(), imports);
    }
}
