/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.symbols;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.util.Assert;

/**
 * Symbolen voor de symbol table. De symbolen representeren collecties die zijn afgeleid van inverse associaties.
 */
public final class CollectieSymbol extends AbstractSymbol {


    private final String expressieType;
    private final String javaType;

    /**
     * Creëert een symbool met gegeven syntax dat wordt afgebeeld op het gegeven type en tot de gegeven groep
     * behoort.
     *
     * @param groepNaam      Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het symbool behoort.
     * @param bmrAttribuut   Attribuut uit het BMR waarmee het symbool naar verwijst.
     * @param generator      Generator waarin het symbool wordt gebruikt.
     */
    public CollectieSymbol(final String groepNaam, final String objectTypeNaam, final Attribuut bmrAttribuut,
                           final AbstractGenerator generator)
    {
        super(bmrAttribuut, groepNaam, objectTypeNaam, true, null, generator);
        this.expressieType =
                SymbolTableConstants.BMR_OBJECTTYPE_NAAR_EXPRESSIETYPE.get(bmrAttribuut.getObjectType().getNaam());
        this.javaType = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_TYPE.get(this.expressieType);
        Assert.notNull(this.expressieType,
                       String.format("Expressietype voor objecttype '%s' kan niet in de lijst van constanten gevonden worden",
                                     bmrAttribuut.getObjectType().getNaam()));
    }

    @Override
    public boolean isIndexed() {
        return true;
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
        return String.format("Getter voor collectie '%s' in objecttype '%s'.", getSyntax(), modelKlasse.getNaam());
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarWaarde(final Attribuut attribuut) {
        return new JavaAccessPath(String.format(GETTER_FORMAT, attribuut.getIdentCode()));
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarAttribuut(final Attribuut attribuut, final boolean metGroep) {
        return null;
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarActueelRecordAttribuut(final Attribuut attribuut) {
        return null;
    }

    @Override
    protected BodyEnImports maakWaardeGetterBody() {
        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        final Attribuut attribuut = getBmrAttribuut();
        body.append(String.format("%s resultaat = %s;", SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam(),
                SymbolTableConstants.NULLVALUE_EXPRESSIE));
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
        imports.add(SymbolTableConstants.NULLVALUE_JAVATYPE);
        maakCollectieGetterBody(getModelKlasse(attribuut.getType()), getModelKlasse(attribuut.getObjectType()), body,
            false, imports);
        body.append("else ");
        maakCollectieGetterBody(getHistorischeModelKlasse(attribuut.getType()),
            getHistorischeModelKlasse(attribuut.getObjectType()), body, true, imports);

        body.append("return resultaat;");

        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakAttribuutGetterBody() {
        return new BodyEnImports("return null;", Collections.EMPTY_LIST);
    }

    @Override
    protected BodyEnImports maakHistorischeAttributenGetterBody() {
        return new BodyEnImports("return null;", Collections.EMPTY_LIST);
    }

    /**
     * Maakt de body van een getter die een lijst met waarden oplevert (voor inversie associaties).
     *
     * @param parentModelKlasse  Javaklasse van het objecttype waartoe de collectie behoort.
     * @param elementModelKlasse Javaklasse van de elementen van de collectie.
     * @param body               Body van de getter waaraan de code toegevoegd moet worden.
     * @param isHistorisch       TRUE als de getter voor historische waarden van de collectie moet worden gemaakt,
     *                           anders FALSE.
     * @param imports            collectie waar imports aan toegevoegd dienen te worden.
     */
    private void maakCollectieGetterBody(final JavaType parentModelKlasse, final JavaType elementModelKlasse,
                                         final StringBuilder body, final boolean isHistorisch,
                                         final List<JavaType> imports)
    {
        final Attribuut attribuut = getBmrAttribuut();
        body.append(
                String.format(
                        "if (%s instanceof %s && ((%s) %s).get%s() != null) {%n",
                        SymbolTableConstants.OBJECTPARAMETERNAAM,
                        parentModelKlasse.getNaam(),
                        parentModelKlasse.getNaam(),
                        SymbolTableConstants.OBJECTPARAMETERNAAM,
                        attribuut.getInverseAssociatieIdentCode()));
        imports.add(parentModelKlasse);
        body.append(String.format("final List<%s> elementen = new ArrayList<%s>();%n",
            SymbolTableConstants.EXPRESSIE_KLASSENAAM, SymbolTableConstants.EXPRESSIE_KLASSENAAM));
        imports.add(JavaType.LIST);
        imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
        imports.add(JavaType.ARRAY_LIST);

        body.append(
            String.format(
                "for(%s element: ((%s)%s).get%s()) {%n",
                elementModelKlasse.getNaam(),
                parentModelKlasse.getNaam(),
                SymbolTableConstants.OBJECTPARAMETERNAAM, attribuut.getInverseAssociatieIdentCode()));
        imports.add(elementModelKlasse);

        if (!isHistorisch || elementModelKlasse.getNaam().equals("BetrokkenheidHisVolledig")) {
            body.append(
                    String.format(
                            "elementen.add(new %s(element, %s.%s));%n",
                            SymbolTableConstants.ROOTOBJECT_EXPRESSIE_KLASSENAAM,
                            SymbolTableConstants.EXPRESSIETYPE_KLASSENAAM,
                            getExpressieType()));
            imports.add(SymbolTableConstants.EXPRESSIETYPE_JAVATYPE);
        } else {
            String ident;
            if (attribuut.getObjectType().getIdentCode().equalsIgnoreCase("Persoon")) {
                ident = getModelKlasse(attribuut.getObjectType()).getNaam() + attribuut.getGroep().getIdentCode();
            } else {
                ident = getModelKlasse(attribuut.getObjectType()).getNaam();
            }
            final String getHistorieMethode = "get" + ident + "Historie";
            final String packageName =
                    NAAMGEVINGSSTRATEGIE_OPERATIONEEL.getJavaTypeVoorElement(attribuut.getObjectType())
                            .getPackagePad();
            final String hisModelKlasse = packageName + ".His" + ident + "Model";
            final String getObjectMethode = "get" + ident;

            body.append(String.format("final Iterator<%s> iterator = element.%s().iterator();%n", hisModelKlasse,
                    getHistorieMethode))
                    .append(String.format("final List<%s> historie = new ArrayList<%s>();%n",
                            SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam(),
                            SymbolTableConstants.EXPRESSIE_JAVATYPE.getNaam()))
                    .append(String.format("while (iterator.hasNext()) {%n"))
                    .append(String.format("historie.add(new %s(iterator.next().%s(), %s.%s));%n",
                            SymbolTableConstants.ROOTOBJECT_EXPRESSIE_KLASSENAAM, getObjectMethode,
                            SymbolTableConstants.EXPRESSIETYPE_KLASSENAAM, getExpressieType()))
                    .append(String.format("}%n"))
                    .append(String.format("elementen.add(new %s(historie));%n",
                            SymbolTableConstants.LIJSTEXPRESSIE_KLASSENAAM));
            imports.add(JavaType.ITERATOR);
            imports.add(JavaType.LIST);
            imports.add(JavaType.ARRAY_LIST);
            imports.add(SymbolTableConstants.EXPRESSIE_JAVATYPE);
            imports.add(SymbolTableConstants.EXPRESSIETYPE_JAVATYPE);
            imports.add(SymbolTableConstants.LIJSTEXPRESSIE_JAVATYPE);
        }

        body.append(String.format("}%n"));
        body.append(String.format("resultaat = new %s(elementen);%n", SymbolTableConstants.LIJSTEXPRESSIE_KLASSENAAM));
        body.append(String.format("}%n"));
        imports.add(SymbolTableConstants.LIJSTEXPRESSIE_JAVATYPE);
    }

    @Override
    protected void voegSpecifiekeImportsToe(final Attribuut attribuut, final JavaKlasse klasse) {
        final String javaClass = SymbolTableConstants.EXPRESSIETYPE_NAAR_JAVA_EXPRESSIECLASS.get(getExpressieType());
        if (javaClass != null) {
            klasse.voegExtraImportsToe(new JavaType(javaClass, SymbolTableConstants.LITERALS_PACKAGE));
        }
        final ObjectType objectType = attribuut.getObjectType();
        klasse.voegExtraImportsToe(getModelKlasse(objectType));
        klasse.voegExtraImportsToe(getHistorischeModelKlasse(objectType));

        klasse.voegExtraImportsToe(getModelKlasse(attribuut.getType()));
        klasse.voegExtraImportsToe(getHistorischeModelKlasse(attribuut.getType()));
    }
}
