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
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Symbolen voor de symbol table. De symbolen zijn afgeleid van groepen.
 */
public final class StandaardGroepSymbol extends AbstractGroepSymbol {

    /**
     * Creëert een symbool met gegeven syntax dat wordt afgebeeld op de gegeven groep.
     *
     * @param groepNaam      Naam van de groep (zoals in de expressietaal gebruikt) waartoe het symbool behoort.
     * @param objectTypeNaam Naam van het objecttype waartoe het symbool behoort.
     * @param groep   Groep uit het BMR waarmee het symbool naar verwijst.
     * @param generator      Generator waarin het symbool wordt gebruikt.
     */
    public StandaardGroepSymbol(final String groepNaam, final String objectTypeNaam, final Groep groep,
                                final AbstractGenerator generator, final ObjectType objectType)
    {
        super(groep, groepNaam, objectTypeNaam, generator, null, objectType);
    }

    @Override
    public boolean isIndexed() {
        return false;
    }

    @Override
    public String getExpressieType() {
        // Dit is een gokje.
        return "GROEP";
    }

    @Override
    public String getGetterJavaDoc() {
        final JavaType modelKlasse = getModelKlasse(getGroep().getObjectType());
        return String.format("Getter voor '%s' in objecttype '%s'.", getSyntax(), modelKlasse.getNaam());
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPath(final Groep groep, final boolean metGroep) {
        final String path;
        if (isIndicatie() && !"Identiteit".equals(groep.getIdentCode())) {
            path = "Indicatie" + groep.getIdentCode() + "().getStandaard";
        } else if (groep.getIdentCode().equals("Identiteit")) {
            path = "Standaard";
        } else {
            path = groep.getIdentCode();
        }
        return new JavaAccessPath(String.format(GETTER_FORMAT, path));
    }

    @Override
    protected JavaAccessPath bepaalJavaAccessPathNaarActueelRecord(final Groep groep) {
        JavaAccessPath result = bepaalJavaAccessPath(groep, false);
        result = new JavaAccessPath("getActueleRecord()", result);
        final JavaType modelKlasse = getModelKlasse(groep.getObjectType());
        String ident = modelKlasse.getNaam();
        if (moetIdentCodeToevoegen(groep)) {
            ident = ident + groep.getIdentCode();
        }
        final String historieMethode = "get" + ident + "Historie()";
        result = new JavaAccessPath(historieMethode, result);

        return result;
    }

    @Override
    protected BodyEnImports maakGroepGetterBody() {
        final Groep groep = getGroep();

        final String expressieTypeClass = getExpressieType();

        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        if (getJavaAccessPath() != null) {
            body.append(String.format("%s resultaat = null;%n", SymbolTableConstants.GROEP_JAVATYPE.getNaam()));
            imports.add(SymbolTableConstants.GROEP_JAVATYPE);
            maakGetterBody(false, false, getModelKlasse(groep.getObjectType()), expressieTypeClass, body, imports);
            body.append("return resultaat;");
        } else {
            body.append("return null;");
        }

        return new BodyEnImports(body.toString(), imports);
    }

    @Override
    protected BodyEnImports maakHistorischeGroepenGetterBody() {
        final Groep groep = getGroep();
        final JavaType modelKlasse = getModelKlasse(groep.getObjectType());
        final JavaType historischeModelKlasse = getHistorischeModelKlasse(groep.getObjectType());

        final StringBuilder body = new StringBuilder();
        final List<JavaType> imports = new ArrayList<>();

        body.append(String.format("final List<%s> groepen = new ArrayList<%s>();%n",
                SymbolTableConstants.GROEP_JAVATYPE.getNaam(),
                SymbolTableConstants.GROEP_JAVATYPE.getNaam()));
        imports.add(JavaType.LIST);
        imports.add(JavaType.ARRAY_LIST);
        imports.add(SymbolTableConstants.GROEP_JAVATYPE);

        if (getGroep().getHistorieVastleggen() != 'G') {
            body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                    modelKlasse.getNaam()))
                    .append(String.format("final %s groep = %s(%s);%n",
                            SymbolTableConstants.GROEP_JAVATYPE.getNaam(),
                            SymbolTableConstants.GETGROEP_METHODENAAM,
                            SymbolTableConstants.OBJECTPARAMETERNAAM))
                    .append("if (groep != null) { groepen.add(groep); }\n")
                    .append("}\n");
            imports.add(modelKlasse);
            imports.add(SymbolTableConstants.GROEP_JAVATYPE);

            final String ident;
            if (moetIdentCodeToevoegen(groep) && !isIndicatie()) {
                ident = modelKlasse.getNaam() + getGroep().getIdentCode();
            } else if(isIndicatie()) {
                ident = modelKlasse.getNaam() + "Indicatie" + getGroep().getIdentCode();
            } else {
                ident = modelKlasse.getNaam();
            }

            final String historieMethode;
            if (isIndicatie()) {
                historieMethode = "getIndicatie" + groep.getIdentCode() + "().getPersoonIndicatieHistorie";
            } else {
                historieMethode = "get" + ident + "Historie";
            }
            final String packageName = NAAMGEVINGSSTRATEGIE_OPERATIONEEL.getJavaTypeVoorElement(getGroep().getObjectType()).getPackagePad();
            final String iteratorGenericParameter;
            if (isIndicatie()) {
                iteratorGenericParameter = packageName + ".His" + ident + "Model";
            } else {
                iteratorGenericParameter = packageName + ".His" + ident + "Model";
            }

            body.append(" else ");

            final String attrValue =
                    String.format("iterator.next()", getJavaAccessPath().toString());

            body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM,
                                      historischeModelKlasse.getNaam()))
                    .append(String.format("final %s p = (%s) %s;", historischeModelKlasse.getNaam(),
                            historischeModelKlasse.getNaam(),
                            SymbolTableConstants.OBJECTPARAMETERNAAM))
                    .append(String.format("final Iterator<%s> iterator = p.%s().getHistorie().iterator();",
                            iteratorGenericParameter, historieMethode))
                    .append(String.format("while (iterator.hasNext()) {%n"))
                    .append(String.format("groepen.add(%s);%n", attrValue))
                    .append("}\n}\n");
            imports.add(historischeModelKlasse);
            imports.add(JavaType.ITERATOR);
        } else {
            body.append(String.format("final %s groep = %s(%s);%n",
                                      SymbolTableConstants.GROEP_JAVATYPE.getNaam(),
                                      SymbolTableConstants.GETGROEP_METHODENAAM,
                                      SymbolTableConstants.OBJECTPARAMETERNAAM))
                    .append("if (groep != null) { groepen.add(groep); }\n");
            imports.add(SymbolTableConstants.GROEP_JAVATYPE);
        }

        body.append("return groepen;");


        return new BodyEnImports(body.toString(), imports);
    }

    private boolean moetIdentCodeToevoegen(final Groep groep) {
        final boolean result = groep.getObjectType().getIdentCode().equalsIgnoreCase("Persoon")
                || (groep.getObjectType().getSuperType() != null
                && groep.getObjectType().getSuperType().getIdentCode().equalsIgnoreCase("Betrokkenheid"))
                || groep.getObjectType().getIdentCode().equalsIgnoreCase("Relatie");
        return result && !groep.getIdentCode().equals("Standaard");
    }

    /**
     * Maakt de body van een standaard getter die een groep oplevert.
     *
     * @param isWaardeGetter        TRUE als de getter de waarde moet opleveren, FALSE als de getter de groep zelf
     *                              moet opleveren.
     * @param isHistorisch          TRUE als het een groep van een PersoonHisVolledig object is, anders FALSE.
     * @param modelKlasse           De Javaklasse die hoort bij het objecttype.
     * @param expressieTypeClass    Het type van de waarde in de expressietaal.
     * @param body                  De body van de functie waaraan de code toegevoegd moet worden.
     * @param imports               De imports die gebruikt worden in de body.
     */
    private void maakGetterBody(final boolean isWaardeGetter, final boolean isHistorisch, final JavaType modelKlasse, final String expressieTypeClass,
                                final StringBuilder body, final List<JavaType> imports)
    {
        body.append(String.format("if (%s instanceof %s) {%n", SymbolTableConstants.OBJECTPARAMETERNAAM, modelKlasse.getNaam()));
        imports.add(modelKlasse);

        body.append(String.format("final %s v = (%s) %s;%n", modelKlasse.getNaam(), modelKlasse.getNaam(), SymbolTableConstants.OBJECTPARAMETERNAAM));
        imports.add(modelKlasse);

        final StringBuilder controlPath = new StringBuilder("v");

        final JavaAccessPath javaAccessPath;
        if (isHistorisch) {
            javaAccessPath = getJavaAccessPathNaarActueelRecord();
        } else {
            javaAccessPath = getJavaAccessPath();
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
        }  else {
            body.append(String.format("resultaat = v.%s;%n", javaAccessPath));
        }

        if (!javaAccessPath.isLaatsteAanroep()) {
            body.append("}\n");
        }

        body.append("}\n");
    }

    protected void voegSpecifiekeImportsToe(final JavaKlasse klasse) {
        // Geen invulling nodig
    }

    @Override
    public String getJavaType() {
        return null;
    }

    @Override
    public String getParentExpressieType() {
        return null;
    }

    @Override
    public Attribuut getBmrAttribuut() {
        return null;
    }

    @Override
    public Symbol getParentSymbol() {
        return null;
    }
}
