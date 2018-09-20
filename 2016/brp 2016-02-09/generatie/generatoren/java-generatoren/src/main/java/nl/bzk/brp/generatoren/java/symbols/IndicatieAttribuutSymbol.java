/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import static nl.bzk.brp.generatoren.java.symbols.SymbolTableConstants.PERSOON_HIS_INDICATIE_STANDAARD_GROEP_JAVATYPE;
import static nl.bzk.brp.generatoren.java.symbols.SymbolTableConstants.PERSOON_HIS_MOMENT_JAVATYPE;
import static nl.bzk.brp.generatoren.java.symbols.SymbolTableConstants.PERSOON_INDICATIE_JAVATYPE;

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
public final class IndicatieAttribuutSymbol implements Symbol {

    //TODO De verschillende waarden zijn nu als enumeratie gecodeerd en met een aantal switches. Als de generator
    //TODO nog lang gebruikt gaat worden, zou dit netjes met subclasses of strategy objects opgelost moeten worden.

    /**
     * Indicatieattributen kunnen verschillende 'waarden' opleveren. De waarde van de indicatie zelf, de datum
     * aanvang geldigheid en de datum einde geldigheid.
     */
    public enum IndicatieAttribuutWaarde {
        /**
         * De waarde van het attribuut betreft de waarde van de indicatie (WAAR, ONWAAR, NULL).
         */
        WAARDE,
        /**
         * De waarde van het attribuut betreft de datum aanvang geldigheid.
         */
        DATUM_AANVANG,
        /**
         * De waarde van het attribuut betreft de datum einde geldigheid.
         */
        DATUM_EIND,
        /**
         * De waarde van het attribuut betreft de tijdstip registratie.
         */
        TIJDSTIP_REGISTRATIE,
        /**
         * De waarde van het attribuut betreft de tijdstip verval.
         */
        TIJDSTIP_VERVAL
    }


    protected static final SymbolTableNaamgevingStrategie SYMBOL_TABLE_NAAMGEVING_STRATEGIE =
            new SymbolTableNaamgevingStrategie();

    private final String                   attribuutNaam;
    private final String                   syntax;
    private final Tuple                    tuple;
    private final IndicatieAttribuutWaarde waarde;

    /**
     * Creëert een symbool met gegeven syntax dat wordt afgebeeld op het gegeven type en tot de gegeven groep
     * behoort.
     *
     * @param aGroepNaam   Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param aTuple       Tuplewaarde waarvan het symbool is afgeleid.
     * @param waarde       Soort waarde dat het attribuut moet opleveren.
     * @param parentSymbol Geïndiceerde symbool/attribuut waar het symbool onderdeel van uitmaakt.
     */
    public IndicatieAttribuutSymbol(final String aGroepNaam, final Tuple aTuple, final IndicatieAttribuutWaarde waarde,
                                    final Symbol parentSymbol)
    {
        this.tuple = aTuple;
        this.waarde = waarde;

        String volledigeAttribuutNaam =
                SYMBOL_TABLE_NAAMGEVING_STRATEGIE.bepaalAttribuutNaam(aTuple.getNaam(), aGroepNaam,
                        "PersoonIndicatieHisVolledig");

        switch (waarde) {
            case WAARDE:
                break;
            case DATUM_AANVANG:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".datum_aanvang_geldigheid";
                break;
            case DATUM_EIND:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".datum_einde_geldigheid";
                break;
            case TIJDSTIP_REGISTRATIE:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".datum_tijd_registratie";
                break;
            case TIJDSTIP_VERVAL:
                volledigeAttribuutNaam = volledigeAttribuutNaam + ".datum_tijd_verval";
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
        if (waarde == IndicatieAttribuutWaarde.WAARDE) {
            return SymbolTableConstants.BOOLEAN_TYPE;
        } else {
            return SymbolTableConstants.DATE_TYPE;
        }
    }

    public String getJavaType() {
        if (waarde == IndicatieAttribuutWaarde.WAARDE) {
            return "Boolean";
        } else {
            return "Integer";
        }
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
            case WAARDE:
                doc = String.format("Indicatie %s", getEnumNaam());
                break;
            case DATUM_AANVANG:
                doc = String.format("Datum aanvang geldigheid van indicatie %s", getEnumNaam());
                break;
            case DATUM_EIND:
                doc = String.format("Datum einde geldigheid van indicatie %s", getEnumNaam());
                break;
            case TIJDSTIP_REGISTRATIE:
                doc = String.format("Tijdstip registratie van indicatie %s", getEnumNaam());
                break;
            case TIJDSTIP_VERVAL:
                doc = String.format("Tijdstip verval van indicatie %s", getEnumNaam());
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
            case WAARDE:
                doc = String.format("Getter voor indicatie '%s' in objecttype 'Persoon'.", getSyntax());
                break;
            case DATUM_AANVANG:
                doc = String.format("Getter voor datum aanvang geldigheid van indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            case DATUM_EIND:
                doc = String.format("Getter voor datum einde geldigheid indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            case TIJDSTIP_REGISTRATIE:
                doc = String.format("Getter voor tijdstip registratie van indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            case TIJDSTIP_VERVAL:
                doc = String.format("Getter voor tijdstip verval van indicatie '%s' in objecttype 'Persoon'.",
                        getSyntax());
                break;
            default:
                break;
        }
        return doc;
    }

    @Override
    public String getGetterClassName() {
        return JavaGeneratieUtil.camelCase(getEnumNaam().replace('_', ' ').toLowerCase()).replaceAll(" ", "")
                + "Getter";
    }

    @Override
    public JavaKlasse maakGetterKlasse() {
        final JavaKlasse klasse =
                new JavaKlasse(getGetterClassName(), getGetterJavaDoc(), SymbolTableConstants.SOLVERS_PACKAGE);
        klasse.voegSuperInterfaceToe(SymbolTableConstants.ATTRIBUTEGETTER_JAVATYPE);

//        final String javaClass = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());
//        if (javaClass != null) {
//            System.out.println(" - " + javaClass);
//            klasse.voegExtraImportsToe(new JavaType(javaClass, SymbolTableConstants.LITERALS_PACKAGE));
//            klasse.voegExtraImportsToe(new JavaType("StringLiteralExpressie", SymbolTableConstants.LITERALS_PACKAGE));
//        }
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

        final String getMethode = "getWaarde";
        final String expressieInstantiatie;
        switch (waarde) {
            case WAARDE:
                expressieInstantiatie = "BooleanLiteralExpressie.getExpressie";
                imports.add(new JavaType("BooleanLiteralExpressie", SymbolTableConstants.LITERALS_PACKAGE));
                break;
            case DATUM_AANVANG:
            case DATUM_EIND:
            case TIJDSTIP_REGISTRATIE:
            case TIJDSTIP_VERVAL:
                expressieInstantiatie = "new StringLiteralExpressie";
                imports.add(new JavaType("StringLiteralExpressie", SymbolTableConstants.LITERALS_PACKAGE));
                break;
            default:
                expressieInstantiatie = "";
                break;
        }

        body.append(String.format("Expressie resultaat = %s;", SymbolTableConstants.NULLVALUE_EXPRESSIE));
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
        imports.add(SymbolTableConstants.NULLVALUE_JAVATYPE);

        if (waarde == IndicatieAttribuutWaarde.WAARDE) {
            body.append("if (brpObject instanceof Persoon) {")
                    .append("    final Attribuut attribuut = getAttribuut(brpObject);")
                    .append("    if (attribuut != null) {")
                    .append("        resultaat = BooleanLiteralExpressie.getExpressie(attribuut.getWaarde());")
                    .append("    }")
                    .append("} else ");
            imports.add(new JavaType("Persoon", "nl.bzk.brp.model.logisch.kern"));
            imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
            imports.add(new JavaType("BooleanLiteralExpressie", SymbolTableConstants.LITERALS_PACKAGE));
        } else {
            body.append(String.format("if (brpObject instanceof %s) { ", PERSOON_HIS_MOMENT_JAVATYPE.getNaam()))
                .append(" final Attribuut attribuut = getAttribuut(brpObject);")
                .append(" if(attribuut != null) {")
                .append("       resultaat = new StringLiteralExpressie(attribuut.getWaarde());")
                .append(" }")
                .append("} else ");
            imports.add(PERSOON_HIS_MOMENT_JAVATYPE);
        }
        body.append("if (brpObject instanceof PersoonHisVolledig) {")
                .append("    final List<Attribuut> attributen = getHistorischeAttributen(brpObject);")
                .append("    if (attributen != null) {")
                .append("        final List<Expressie> elementen = new ArrayList<Expressie>();")
                .append("        for (Attribuut attribuut : attributen) {")
                .append(String.format(
                        "            if (attribuut != null) {%n"))
                .append(String.format(
                        "                elementen.add(%s(attribuut.%s()));", expressieInstantiatie, getMethode))
                .append("            }")
                .append("        }")
                .append(String.format(
                        "        resultaat = new %s(elementen);", SymbolTableConstants.LIJSTEXPRESSIE_KLASSENAAM))
                .append("    }")
                .append("}")
                .append("return resultaat;");
        imports.add(JavaType.LIST);
        imports.add(JavaType.ARRAY_LIST);
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
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
        final List<JavaType> imports = new ArrayList<>();

        final String getMethode;
        switch (waarde) {
            case WAARDE:
                getMethode = "getWaarde";
                break;
            case DATUM_AANVANG:
                getMethode = "getDatumAanvangGeldigheid";
                break;
            case DATUM_EIND:
                getMethode = "getDatumEindeGeldigheid";
                break;
            case TIJDSTIP_REGISTRATIE:
                getMethode = "getTijdstipRegistratie";
                break;
            case TIJDSTIP_VERVAL:
                getMethode = "getDatumTijdVerval";
                break;
            default:
                getMethode = "";
                break;
        }
        body.append("Attribuut resultaat = null;");
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);

        if (waarde == IndicatieAttribuutWaarde.WAARDE) {
            body.append("if (brpObject instanceof Persoon) {")
                    .append("    final Persoon p = (Persoon) brpObject;")
                    .append(String.format(
                            "    if (p.get%s() != null && p.get%s().getStandaard() != null) {",
                            tuple.getIdentCode(), tuple.getIdentCode()))
                    .append(String.format("        resultaat = p.get%s().getStandaard().getWaarde();",
                            tuple.getIdentCode()))
                    .append("    }")
                    .append("} else ");
            imports.add(new JavaType("Persoon", "nl.bzk.brp.model.logisch.kern"));
        } else {
            imports.add(PERSOON_HIS_MOMENT_JAVATYPE);
            imports.add(PERSOON_INDICATIE_JAVATYPE);
            imports.add(PERSOON_HIS_INDICATIE_STANDAARD_GROEP_JAVATYPE);

            body.append(String.format("if (brpObject instanceof %s) { ", PERSOON_HIS_MOMENT_JAVATYPE.getNaam()));
            body.append(String.format("final %s persoonHisMoment = (%s) brpObject;", PERSOON_HIS_MOMENT_JAVATYPE.getNaam(), PERSOON_HIS_MOMENT_JAVATYPE
                .getNaam()));
            body.append(String.format("final %s persoonIndicatie = persoonHisMoment.get%s();", PERSOON_INDICATIE_JAVATYPE.getNaam(), tuple.getIdentCode()));
            body.append("if (persoonIndicatie != null) { ")
                .append(String.format("final %s standaardGroep = (%s) persoonIndicatie.getStandaard();", PERSOON_HIS_INDICATIE_STANDAARD_GROEP_JAVATYPE
                    .getNaam(), PERSOON_HIS_INDICATIE_STANDAARD_GROEP_JAVATYPE.getNaam()))
                .append(String.format("resultaat = standaardGroep.%s();", getMethode))
                .append(" }")
                .append("} else ");
        }
        body.append("if (brpObject instanceof PersoonHisVolledig) {")
                .append("    final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;")
                .append(String.format(
                        "    if (p.get%s() != null) {", tuple.getIdentCode()))
                .append(String.format(
                        "        resultaat = p.get%s().getPersoonIndicatieHistorie().getActueleRecord().%s();",
                        tuple.getIdentCode(), getMethode))
                .append("    }")
                .append("}")
                .append("return resultaat;");
        return new BodyEnImports(body.toString(), imports);
    }

    /**
     * Maakt de body van de getterfunctie die alle historische attributen oplevert, indien aanwezig.
     *
     * @return Getterfunctie.
     */
    private BodyEnImports maakHistorischeAttributenGetterBody() {
        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        final String getMethode;
        switch (waarde) {
            case WAARDE:
                getMethode = "getWaarde";
                break;
            case DATUM_AANVANG:
                getMethode = "getDatumAanvangGeldigheid";
                break;
            case DATUM_EIND:
                getMethode = "getDatumEindeGeldigheid";
                break;
            case TIJDSTIP_REGISTRATIE:
                getMethode = "getTijdstipRegistratie";
                break;
            case TIJDSTIP_VERVAL:
                getMethode = "getDatumTijdVerval";
                break;
            default:
                getMethode = "";
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
                .append(String.format(
                        "            attributen.add(iterator.next().%s());", getMethode))
                .append("        }")
                .append("    }")
                .append("}")
                .append("return attributen;");
        imports.add(SymbolTableConstants.ATTRIBUUT_JAVATYPE);
        imports.add(JavaType.LIST);
        imports.add(JavaType.ARRAY_LIST);
        imports.add(JavaType.ITERATOR);

        return new BodyEnImports(body.toString(), imports);
    }
}
