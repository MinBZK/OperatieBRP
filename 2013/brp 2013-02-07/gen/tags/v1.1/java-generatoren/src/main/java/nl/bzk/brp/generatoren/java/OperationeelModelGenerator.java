/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BerichtModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.AbstractGenerationGapPatroonJavaWriter;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/** Generator die de Java Model classes genereert voor het operationeel model. */
@Component("operationeelModelJavaGenerator")
public class OperationeelModelGenerator extends AbstractJavaGenerator {

    private final NaamgevingStrategie operationeelModelNaamgevingStrategie = new OperationeelModelNaamgevingStrategie();
    private final NaamgevingStrategie superInterfaceNaamgevingStrategie    = new LogischModelNaamgevingStrategie();
    private final NaamgevingStrategie berichtModelNaamgevingStrategie      = new BerichtModelNaamgevingStrategie();
    private final NaamgevingStrategie algemeneNaamgevingStrategie          = new AlgemeneNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<JavaKlasse> klassen = new ArrayList<JavaKlasse>();
        klassen.addAll(genereerOperationeleObjectTypen());
        klassen.addAll(genereerOperationeleGroepen());
        klassen.addAll(genereerHistorieObjectTypen());

        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(klassen, this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.OPERATIONEEL_MODEL_JAVA_GENERATOR;
    }

    /**
     * Genereert java klassen voor historie object typen uit de operationele laag.
     *
     * @return Lijst van java klassen die historie object typen voorstellen.
     */
    private List<JavaKlasse> genereerHistorieObjectTypen() {
        final List<JavaKlasse> javaKlassen = new ArrayList<JavaKlasse>();
        final List<Groep> groepen = getBmrDao().getGroepenWaarvanHistorieWordtVastgelegd();
        for (Groep groep : groepen) {
            final JavaKlasse klasse = genereerHistorieKlasseVoorGroep(groep);
            if (klasse != null) {
                javaKlassen.add(klasse);
            }
        }
        return javaKlassen;
    }

    /**
     * Genereert voor de groep de historie klasse met JPA (ORM) informatie om historie uit de database te kunnen
     * ophalen. Dit wordt gedaan door op basis van de syncid van de groep het operationele object type op te halen en
     * daarvan een java klasse te genereren.
     *
     * @param groep De groep waarvoor een historie klasse wordt gegenereerd.
     * @return Een java klasse dat het historie object type voorstelt.
     */
    private JavaKlasse genereerHistorieKlasseVoorGroep(final Groep groep) {
        final ObjectType operationeelModelObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        if (operationeelModelObjectType != null) {
            final JavaKlasse klasse = new JavaKlasse(
                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(operationeelModelObjectType), null);

            //Is dit een identiteits groep? Dan is er geen logische interface.
            //Kent de groep een logische interface? Dan extenden we van de logische interface.
            if (!isIdentiteitGroep(groep)
                    && behoortTotJavaLogischModel(groep)
                    && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == groep.getObjectType().getSoortInhoud())
            {
                klasse.voegSuperInterfaceToe(superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(groep));
            }

            //JPA annotaties.
            klasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY));
            final JavaAnnotatie tabelAnn = new JavaAnnotatie(JavaType.TABLE,
                new AnnotatieWaardeParameter("schema", operationeelModelObjectType.getSchema().getNaam()),
                new AnnotatieWaardeParameter("name", operationeelModelObjectType.getIdentDb()));
            klasse.voegAnnotatieToe(tabelAnn);

            final JavaAnnotatie accessAnn = new JavaAnnotatie(JavaType.ACCESS,
                new AnnotatieWaardeParameter("value", JavaType.ACCESS_TYPE, "FIELD"));
            klasse.voegAnnotatieToe(accessAnn);

            final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(operationeelModelObjectType);
            final ObjectType logischModelObjectType = groep.getObjectType();
            for (Attribuut attribuut : attributen) {
                if (!isHistorieGerelateerdVeld(attribuut)) {
                    // De referentie 'terug' naar het object type moet niet worden opgenomen in json.
                    // We gebruiken hier syncid, omdat de groep uit de logische laag groep komt,
                    // maar het het attribuut uit de operationele laag.
                    // We moeten nog een extra regel toepassen voor het geval er meerdere referenties
                    // zijn naar het object type (komt voor bij Persoon / Inschrijving met vorige /volgende persoon).
                    // We gaan ervan uit dat alleen de bij back reference de naam van het attribuut
                    // hetzelfde is als de naam van het object type waar het naar verwijst.
                    boolean isHetzelfdeObjectType = groep.getObjectType().getSyncid().equals(
                        attribuut.getType().getSyncid());
                    boolean isJsonProperty = !isHetzelfdeObjectType
                        || (isHetzelfdeObjectType && !attribuut.getNaam().equals(groep.getObjectType().getNaam()));
                    genereerVeldVoorHistorieAttribuut(klasse, logischModelObjectType,
                        operationeelModelObjectType, attribuut, isJsonProperty);
                }
            }

            //JPA vereist een protected no-arg constructor.
            final Constructor defaultCons = new Constructor(JavaAccessModifier.PROTECTED, klasse.getNaam());
            defaultCons.setJavaDoc("Default constructor t.b.v. JPA");
            klasse.voegConstructorToe(defaultCons);

            if (BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == groep.getObjectType().getSoortInhoud()) {

                //Voeg velden toe voor de historie gerelateerde zaken.
                if ('F' == groep.getHistorieVastleggen()) {
                    klasse.setExtendsFrom(JavaType.ABSTRACT_FORMELE_HISTORIE_ENTITEIT);
                } else if ('B' == groep.getHistorieVastleggen()) {
                    klasse.setExtendsFrom(JavaType.ABSTRACT_MATERIELE_HISTORIE_ENTITEIT);
                }

                //Voeg een copy constructor toe om op basis van een A laag entiteit en groep een historie object
                //aan te maken. Dit doen we niet voor statische stamgegevens.
                final Constructor constructor1 = genereerCopyConstructorMetGroepAlsBasisVoorHistoryKlasse(
                    klasse, operationeelModelObjectType, logischModelObjectType, groep);
                klasse.voegConstructorToe(constructor1);

                //Voeg een constructor toe om op basis van een C/D laag entiteit een kopie te maken.
                final Constructor constructor2 = genereerCopyConstructorVoorHistoryKlasse(klasse);
                klasse.voegConstructorToe(constructor2);

                if (klasse.getSuperKlasse().getNaam().equals("AbstractMaterieleHistorieEntiteit")) {
                    // Voeg een kopieer functie toe die de copy constructor aanroept. T.b.v. de historie repositories in
                    // de BRP.
                    final JavaFunctie kopieerFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC,
                        new JavaType(klasse.getNaam(), klasse.getPackagePad()), "kopieer", "de kopie");
                    kopieerFunctie.setJavaDoc("Deze functie maakt een kopie van het object "
                            + "dmv het aanroepen van de copy constructor met zichzelf als argument.");
                    kopieerFunctie.getAnnotaties().add(new JavaAnnotatie(JavaType.OVERRIDE));
                    final String constructorBody = String.format("return new %1$s(this);", klasse.getNaam());
                    kopieerFunctie.setBody(constructorBody);
                    klasse.voegFunctieToe(kopieerFunctie);
                }
            }

            return klasse;
        }
        return null;
    }

    /**
     * Bouwt een constructor vor het kunnen kopieren van C/D laag entiteiten. (His_... klassen)
     *
     * @param klasse De klasse waar de constructor voor wordt gebouwd.
     * @return Een copy constructor.
     */
    private Constructor genereerCopyConstructorVoorHistoryKlasse(final JavaKlasse klasse) {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse.getNaam());
        constructor.setJavaDoc("Copy Constructor die op basis van een C/D-laag klasse een C/D-laag "
            + "klasse construeert.");

        // Parameter is de abstracte versie. Note: dit gaat uit van generation gap, niet helemaal netjes!
        final String objectTypeParamTypeNaam = "Abstract" + klasse.getNaam();
        final String objectTypeParamTypePackage = klasse.getPackagePad()
            + AbstractGenerationGapPatroonJavaWriter.GENERATIE_TYPE_SUB_PACKAGE;
        final JavaType objectTypeParamType = new JavaType(objectTypeParamTypeNaam, objectTypeParamTypePackage);
        final String objectTypeParamNaam = "kopie";

        // Parameter is een historie object type. (His_xx klasse).
        final JavaFunctieParameter consParam = new JavaFunctieParameter(objectTypeParamNaam, objectTypeParamType);
        consParam.setJavaDoc("instantie van A-laag klasse.");
        constructor.voegParameterToe(consParam);

        final StringBuilder constructorBody = new StringBuilder();
        constructorBody.append(String.format("super(%1$s);%n", objectTypeParamNaam));
        for (JavaVeld javaVeld : klasse.getVelden()) {
            if (!"id".equalsIgnoreCase(javaVeld.getNaam())) {
                constructorBody.append(String.format("%1$s = %2$s.get%3$s();%n",
                    javaVeld.getNaam(),
                    objectTypeParamNaam,
                    GeneratieUtil.upperTheFirstCharacter(javaVeld.getNaam())));
            }
        }
        constructor.setBody(constructorBody.toString());
        return constructor;
    }

    /**
     * Genereert een copy constructor voor de historie klasse, om op basis van een A-Laag klasse een Historie C/D laag
     * klasse te instantieren.
     *
     * @param klasse De historie klasse waar de copy constructor in moet.
     * @param operationeelModelObjectType Historie object type uit het BMR voor de groep.
     * @param logischModelObjectType Objecttype uit logsch model waar de groep bij hoort.
     * @param groep De groep uit het logisch model in het BMR.  @return Copy constructor met als type parameter de
     * klasse die overeenkomt met het object type uit de logische laag.
     * @return Copy constructor.
     */
    private Constructor genereerCopyConstructorMetGroepAlsBasisVoorHistoryKlasse(
        final JavaKlasse klasse, final ObjectType operationeelModelObjectType,
        final ObjectType logischModelObjectType, final Groep groep)
    {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse.getNaam());
        constructor.setJavaDoc("Copy Constructor die op basis van een A-laag klasse en groep een C/D laag "
            + "klasse construeert.");

        final JavaType objectTypeParamType =
            operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(logischModelObjectType);
        final String objectTypeParamNaam = GeneratieUtil.lowerTheFirstCharacter(objectTypeParamType.getNaam());

        //Eerste parameter is een A-laag object type
        final JavaFunctieParameter consParam1 = new JavaFunctieParameter(objectTypeParamNaam, objectTypeParamType);
        consParam1.setJavaDoc("instantie van A-laag klasse.");
        constructor.voegParameterToe(consParam1);

        //2de parameter is de groep.
        //Let op: als dit een identiteit groep is dan nemen we geen parameter op, want classes voor Identiteit groepen
        //worden niet gegenereerd, die worden platgeslagen in het operationeel model.
        final String groepParamNaam = "groep";
        if (!IDENTITEIT.equals(groep.getNaam())) {
            final JavaType paramType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
            final JavaFunctieParameter groepParam = new JavaFunctieParameter("groep", paramType);
            groepParam.setJavaDoc("Groep uit de A Laag.");
            constructor.voegParameterToe(groepParam);
        }

        final StringBuilder constructorBody = new StringBuilder();
        final List<Attribuut> attributenVanHisObjectType =
            getBmrDao().getAttributenVanObjectType(operationeelModelObjectType);
        for (Attribuut attribuut : attributenVanHisObjectType) {
            GeneriekElement finaalsupertype;
            if (this.isSubtype(logischModelObjectType)) {
                finaalsupertype = logischModelObjectType.getFinaalSupertype();
            } else {
                finaalsupertype = logischModelObjectType;
            }
            if (!isHistorieGerelateerdVeld(attribuut)
                && !"ID".equalsIgnoreCase(attribuut.getIdentCode()))
            {
                if ("OT".equals(attribuut.getType().getSoortElement().getCode())
                    && attribuut.getIdentCode().equalsIgnoreCase(finaalsupertype.getIdentCode()))
                {
                    constructorBody.append(
                        String.format("this.%1$s = %2$s;%n",
                            GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                            objectTypeParamNaam)
                    );
                } else {
                    constructorBody.append(
                        String.format("this.%1$s = %2$s.get%3$s();%n",
                            GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                            groepParamNaam,
                            attribuut.getIdentCode())
                    );
                }
            }
        }
        constructor.setBody(constructorBody.toString());
        return constructor;
    }

    /**
     * Genereert een java veld voor een attribuut van een historie object type.
     *
     * @param klasse Java klasse waarin het veld moet worden opgenomen.
     * @param logischModelObjectType Objecttype uit het logisch model.
     * @param operationeelModelObjectType Historie object type.
     * @param attribuut Het attribuut waarvoor een veld wordt gegenereerd.
     * @param isJsonProperty of het een json property is of niet.
     * @return Java veld voor attribuut.
     */
    private JavaVeld genereerVeldVoorHistorieAttribuut(final JavaKlasse klasse, final ObjectType logischModelObjectType,
        final ObjectType operationeelModelObjectType, final Attribuut attribuut, final boolean isJsonProperty)
    {
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
        JavaType javaType;
        if (isIdAttribuut(attribuut)) {
            final AttribuutType attribuutType = getBmrDao().getElement(
                attribuut.getType().getId(), AttribuutType.class);
            javaType = this.getJavaTypeVoorAttribuutType(attribuutType);
        } else {
            GeneriekElement type;
            if (attribuut.getType().getSoortInhoud() != null && 'D' == attribuut.getType().getSoortInhoud()) {
                type = logischModelObjectType;
                //Als het type een supertype en subtypes kent, neem dan het finaal supertype als type. Omdat
                //tussenliggende typen geen JPA Entity zijn en dus niet te mappen zijn.
                if (logischModelObjectType.getFinaalSupertype() != null
                    && !logischModelObjectType.getSubtypen().isEmpty())
                {
                    type = logischModelObjectType.getFinaalSupertype();
                }
            } else {
                type = attribuut.getType();
            }

            javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(type);
        }

        final JavaVeld veld = new JavaVeld(javaType, veldNaam);
        klasse.voegVeldToe(veld);
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        genereerGetterJavaDoc(getter, operationeelModelObjectType, attribuut);
        klasse.voegGetterToe(getter);

        annoteerAttribuutVeld(operationeelModelObjectType, attribuut, veld, false, isJsonProperty, false);
        return veld;
    }

    /**
     * Bepaalt of een attribuut een attribuut is wat gerelateerd is aan een historie patroon in het BMR.
     *
     * @param attribuut Een attribuut.
     * @return True indien het attribuut bij een historie patroon hoort, anders false.
     */
    private boolean isHistorieGerelateerdVeld(final Attribuut attribuut) {
        return "TsReg".equals(attribuut.getIdentDb())
            || "TsVerval".equals(attribuut.getIdentDb())
            || "ActieInh".equals(attribuut.getIdentDb())
            || "ActieVerval".equals(attribuut.getIdentDb())
            || "DatAanvGel".equals(attribuut.getIdentDb())
            || "ActieAanpGel".equals(attribuut.getIdentDb())
            || "DatEindeGel".equals(attribuut.getIdentDb());
    }

    /**
     * Genereert de groepen met JPA annotaties voor ORM.
     *
     * @return Lijst van java klassen die groepen representeren uit het BMR.
     */
    private List<JavaKlasse> genereerOperationeleGroepen() {
        final List<JavaKlasse> klassenVoorGroepen = new ArrayList<JavaKlasse>();
        final List<Groep> groepen = getBmrDao().getGroepen();

        for (Groep groep : groepen) {
            if (this.behoortTotJavaOperationeelModel(groep)
                && !IDENTITEIT.equals(groep.getNaam()))
            {
                klassenVoorGroepen.add(genereerJavaKlasseVoorGroep(groep));
            }
        }
        return klassenVoorGroepen;
    }

    /**
     * Genereert een Java klasse representatie van een groep.
     *
     * @param groep De groep waar een Java klasse voor wordt gegenereerd.
     * @return Java klasse die de groep representeert.
     */
    private JavaKlasse genereerJavaKlasseVoorGroep(final Groep groep) {
        final JavaKlasse clazz = new JavaKlasse(operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep),
            bouwJavadocVoorElement(groep));
        //Persistency annotaties
        clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDABLE));

        if (behoortTotJavaLogischModel(groep)) {
            clazz.voegSuperInterfaceToe(superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(groep));
        }

        genereerAttributenMetAccessMethodes(groep, clazz);
        clazz.voegConstructorToe(genereerLegeConstructor(clazz));
        clazz.voegConstructorToe(genereerConstructorMetAttributen(groep, clazz));

        //Alleen als de groep EN het objecttyoe tot het bericht java model behoren EN er is een logische interface voor
        //de groep (voor de constructor parameter), genereren we een copy
        //constructor om van bericht model een operationeel model instantie te maken.
        if (behoortTotJavaBerichtModel(groep)
            && behoortTotJavaBerichtModel(groep.getObjectType())
            && behoortTotJavaLogischModel(groep))
        {
            clazz.voegConstructorToe(genereerGroepCopyConstructorVoorOmzettingVanuitBerichtModel(groep, clazz));
        }
        return clazz;
    }

    /**
     * Genereert een copy constructor om van een groep in bericht model een goep uit het operationeel model te
     * instantieren.
     *
     * @param groep de groep.
     * @param clazz class waar de copy constructor in moet.
     * @return Copy constructor.
     */
    private Constructor genereerGroepCopyConstructorVoorOmzettingVanuitBerichtModel(final Groep groep,
        final JavaKlasse clazz)
    {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz.getNaam());
        final JavaType paramType = superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(groep);
        final String paramNaam = GeneratieUtil.lowerTheFirstCharacter(paramType.getNaam());
        final JavaFunctieParameter consParam = new JavaFunctieParameter(paramNaam, paramType);
        consParam.setJavaDoc("te kopieren groep.");
        constructor.voegParameterToe(consParam);

        //Voor elk attribuut dat in de interface zit genereren we een regel code die dat attribuut kopieert in het
        //huidige object.
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);

        final StringBuilder constructorBody = new StringBuilder();
        for (Attribuut attribuut : attributen) {
            //Attributen die verwijzen naar een object type binnen de groep worden niet gekopieerd.
            //Dit is namelijk het geval bij PersoonInschrijving groep. (Volgende- en vorige persoon)
            //Echter stamgegevens objecttypen worden wel gekopieerd.
            if (behoortTotJavaLogischModel(attribuut)
                &&
                (("OT".equals(attribuut.getType().getSoortElement().getCode())
                    && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() != attribuut.getType().getSoortInhoud())
                    || "AT".equals(attribuut.getType().getSoortElement().getCode())))
            {
                final String copyCode = "this.%1$s = %2$s.get%3$s();%n";
                constructorBody.append(
                    String.format(copyCode,
                        GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                        paramNaam,
                        attribuut.getIdentCode()));
            }
        }

        //Kopieer velden die zowel in het bericht model zitten als in het operationeel model zitten. Die kunnen we ook
        //gewoon kopieren. LET OP: Velden die we hierboven al gekopieerd hebben nemen we niet nog een keer mee!
        final JavaType downCastType = berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
        final String downCastVariabele = GeneratieUtil.lowerTheFirstCharacter(downCastType.getNaam());
        for (Attribuut attribuut : attributen) {
            if (behoortTotJavaBerichtModel(attribuut) && behoortTotJavaOperationeelModel(groep)
                && !behoortTotJavaLogischModel(attribuut))
            {
                if (constructorBody.indexOf("instanceof") == -1) {
                    constructorBody.append("if (").append(paramNaam).append(" instanceof ")
                                   .append(downCastType).append(") {").append(String.format("%n"));
                    constructorBody.append("final ")
                                   .append(downCastType)
                                   .append(" ")
                                   .append(downCastVariabele)
                                   .append(" = ")
                                   .append(String.format("%n"))
                                   .append("(").append(downCastType).append(") ")
                                   .append(paramNaam)
                                   .append(String.format(";%n"));
                }
                //Attributen van het type object type kopieren we niet! Er is maar één groep die dit heeft;
                //PersoonInschrijving. (vorige- en volgende persoon)
                if (!"OT".equals(attribuut.getType().getSoortElement().getCode())) {
                    final String copyCode = "this.%1$s = %2$s.get%3$s();%n";
                    constructorBody.append(
                        String.format(copyCode,
                            GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                            downCastVariabele,
                            attribuut.getIdentCode()));
                }

            }
        }
        if (constructorBody.indexOf("instanceof") > -1) {
            constructorBody.append("}");
        }

        constructor.setBody(constructorBody.toString());
        constructor.setJavaDoc("Copy constructor om vanuit het bericht model een instantie van het operationeel model"
            + " te maken.");
        return constructor;
    }

    /**
     * Voegt een constructor toe met alle velden als parameters, waarbij direct alle velden in de constructor worden
     * gezet naar de waardes van de parameters.
     *
     * @param groep de groep waarvoor de constructor moet worden aangemaakt.
     * @param clazz de Java klasse die de groep representeert en waarvoor de constructor moet worden aangemaakt.
     * @return Constructor.
     */
    private Constructor genereerConstructorMetAttributen(final Groep groep, final JavaKlasse clazz) {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz.getNaam());
        final StringBuilder constructorBody = new StringBuilder();

        for (JavaVeld veld : clazz.getVelden()) {
            constructor.getParameters().add(new JavaFunctieParameter(veld.getNaam(), veld.getType(),
                String.format("%1$s van %2$s.", veld.getNaam(), groep.getNaam())));
            constructorBody.append(String.format("this.%1$s = %1$s;%n", veld.getNaam()));
        }
        constructor.setJavaDoc("Basis constructor die direct alle velden instantieert.");
        constructor.setBody(constructorBody.toString());
        return constructor;
    }

    /**
     * Genereert velden voor alle attributen die horen bij de groep inclusief de getters, maar exclusief setters daar
     * de groepen immutable zijn.
     *
     * @param groep De groep waarvoor attributen moeten worden gebouwd.
     * @param clazz De Java klasse waar de attributen en getters in moeten.
     */
    protected void genereerAttributenMetAccessMethodes(final Groep groep, final JavaKlasse clazz) {
        //Haal attributen van groep op.
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            if (behoortTotJavaOperationeelModel(attribuut)) {
                final JavaType javaTypeVoorVeld =
                    operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                final JavaVeld attribuutVeldInGroep = new JavaVeld(javaTypeVoorVeld, veldNaam);

                //Persistency annotaties
                annoteerAttribuutVeld(groep.getObjectType(), attribuut, attribuutVeldInGroep,
                    false, isJsonProperty(attribuut), false);
                clazz.voegVeldToe(attribuutVeldInGroep);

                //Maak een accessor aan oftewel een getter.
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(attribuutVeldInGroep);
                if (behoortTotJavaLogischModel(attribuut)) {
                    getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                } else {
                    genereerGetterJavaDoc(getter, groep, attribuut);
                }
                clazz.voegGetterToe(getter);
            }
        }
    }

    /**
     * Genereert java klassen die object typen representeren.
     *
     * @return Lijst van Java klassen.
     */
    private List<JavaKlasse> genereerOperationeleObjectTypen() {
        final List<JavaKlasse> klassenVoorObjectTypen = new ArrayList<JavaKlasse>();
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen();
        for (ObjectType objectType : objectTypen) {
            // Generatie van A laag klassen, deze volgen de structuur van het Logische model.
            if (behoortTotJavaOperationeelModel(objectType)) {
                klassenVoorObjectTypen.add(genereerJavaKlasseVoorObjectType(objectType));
            }
        }
        return klassenVoorObjectTypen;
    }

    /**
     * Genereert een java klasse voor een object typen uit het BMR inclusief JPA annotaties voor ORM.
     *
     * @param objectType Het object type waar een java klasse voor wordt gegenereerd.
     * @return Java klasse representatie van object type.
     */
    private JavaKlasse genereerJavaKlasseVoorObjectType(final ObjectType objectType) {
        final JavaKlasse clazz = new JavaKlasse(
            operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
            bouwJavadocVoorElement(objectType));

        // Bepaal het eventueel aanwezige discriminator attribuut.
        Attribuut discrAttribuut = this.bepaalDiscriminatorAttribuut(objectType);

        if (heeftSupertype(objectType)) {
            clazz.setExtendsFrom(
                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        } else {
            clazz.setExtendsFrom(JavaType.ABSTRACT_DYNAMISCH_OBJECT_TYPE);
        }

        // Als een klasse subtypes heeft, oftewel zelf geen finaal subtype is, dan moet hij abstract zijn.
        if (heeftSubtypen(objectType)) {
            clazz.setAbstractClass(true);
        }
        // Niet hierarchische en de blaadjes van een hierarchie zijn een entity.
        if (isNietHierarchischType(objectType) || isFinaalSubtype(objectType)) {
            clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY));
        }

        //Let op, de super interface oftewel de LGM interface moet enkel ge-set worden indien dit object type het
        //vlaggetje in_lgm op true heeft staan.
        if (behoortTotJavaLogischModel(objectType)) {
            clazz.voegSuperInterfaceToe(superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(objectType));
        }

        if (isTussenliggendType(objectType)) {
            clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.MAPPED_SUPER_CLASS));
        } else if (isFinaalSubtype(objectType)) {
            Tuple discriminatorTuple = bepaalDiscriminatorTuple(objectType);
            clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.DISCRIMINATOR_VALUE,
                new AnnotatieWaardeParameter("value", "" + discriminatorTuple.getVolgnummerT())));
        } else {
            final JavaAnnotatie tabelAnn = new JavaAnnotatie(
                JavaType.TABLE, new AnnotatieWaardeParameter("schema", objectType.getSchema().getNaam()),
                new AnnotatieWaardeParameter("name", objectType.getIdentDb()));
            clazz.voegAnnotatieToe(tabelAnn);

            final JavaAnnotatie accessAnnotatie = new JavaAnnotatie(JavaType.ACCESS,
                new AnnotatieWaardeParameter("value", JavaType.ACCESS_TYPE, "FIELD"));
            clazz.voegAnnotatieToe(accessAnnotatie);
        }

        // Voeg standaard lege constructor toe voor objecttype
        clazz.voegConstructorToe(genereerLegeConstructor(clazz));
        // Voeg constructor toe met parameters voor alle identiteits velden.
        // Let op: deze call moet na die voor de lege constructor komen,
        // omdat die eventueel weer verwijderd wordt.
        genereerConstructorMetIdentiteitParameters(clazz, objectType);

        //Voeg copy constructor toe om van bericht model een operationeel model object type te instantieren.
        if (behoortTotJavaBerichtModel(objectType)
            && behoortTotJavaLogischModel(objectType))
        {
            genereerObjectTypeCopyConstructorVoorOmzettingVanuitBerichtModel(clazz, objectType);
        }

        //Genereer velden voor groepen.
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
        for (Groep groep : groepenVoorObjectType) {
            if (behoortTotJavaOperationeelModel(groep)) {
                // De identiteit groep wordt hier plat geslagen.
                if (isIdentiteitGroep(groep)) {
                    genereerVeldenVoorIdentiteitGroep(clazz, objectType, groep, discrAttribuut);
                } else {
                    genereerVeldVoorGroep(clazz, objectType, groep, false);
                }
            }
        }

        //Eventuele Single table per class hierarchy mapping
        //Het object type heeft subtypen en heeft zelf geen supertype, dus we willen hier alleen super typen annoteren.
        if (isFinaalSupertype(objectType)) {
            clazz.voegAnnotatieToe(new JavaAnnotatie(
                JavaType.INHERITANCE,
                new AnnotatieWaardeParameter("strategy", JavaType.INHERITANCE_TYPE, "SINGLE_TABLE")));
            clazz.voegAnnotatieToe(new JavaAnnotatie(
                JavaType.DISCRIMINATOR_COLUMN,
                new AnnotatieWaardeParameter("name", discrAttribuut.getIdentDb()),
                new AnnotatieWaardeParameter("discriminatorType", JavaType.DISCRIMINATOR_TYPE, "INTEGER")));
        }

        // Voeg de inverse associaties toe.
        genereerInverseAssociatiesVoorObjectType(objectType, clazz);

        //Voeg status historie velden en initialisatie toe.
        genereerStatusHistorieVeldenVoorObjectType(objectType, clazz);
        voegStatusHisVeldenInitialisatieToe(objectType, clazz);
        return clazz;
    }

    /**
     * Genereert een copy constructor voor een object type klasse om van het bericht model een operationeel model
     * instantie te maken.
     *
     * @param clazz De klasse waar de copy constructor in moet.
     * @param objectType Object type dat bij de klasse hoort.
     */
    private void genereerObjectTypeCopyConstructorVoorOmzettingVanuitBerichtModel(final JavaKlasse clazz,
        final ObjectType objectType)
    {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz.getNaam());
        constructor.setJavaDoc("Copy constructor om vanuit het bericht model een instantie van het "
            + "operationeel model te maken. ");
        // Constructor parameter.
        final JavaType consParamType = superInterfaceNaamgevingStrategie.getJavaTypeVoorElement(objectType);
        final String consParamNaam = GeneratieUtil.lowerTheFirstCharacter(consParamType.getNaam());
        final JavaFunctieParameter consParam = new JavaFunctieParameter(consParamNaam, consParamType);
        consParam.setJavaDoc("Te kopieren object type.");
        constructor.voegParameterToe(consParam);
        boolean kopieObjectTypeGebruiktInBody = false;

        final List<Groep> groepen = new ArrayList<Groep>();

        // Indien we te maken hebben met een subtype dan moeten we attributen van de identiteit groep van het
        // supertype opnemen als constructor parameters.
        if (isSubtype(objectType)) {
            final ObjectType superObjectType = objectType.getFinaalSupertype();
            final List<Groep> groepenSupertype = getBmrDao().getGroepenVoorObjectType(superObjectType);
            for (Groep groep : groepenSupertype) {
                if (IDENTITEIT.equals(groep.getNaam())) {
                    groepen.add(groep);
                }
            }
        }

        //Plus de groepen van het objecttype zelf.
        groepen.addAll(getBmrDao().getGroepenVoorObjectType(objectType));

        //constructor body.
        final StringBuilder constructorBody = new StringBuilder();
        //Roep de default constructor aan om eventuele Lists te instantieren. Let wel op, bij subtypes doen we dit niet
        //want die krijgen een call naar super();
        if (!isSubtype(objectType)) {
            constructorBody.append(String.format("this();%n"));
        }

        for (Groep groep : groepen) {
            if (behoortTotJavaOperationeelModel(groep)) {
                if (IDENTITEIT.equals(groep.getNaam())) {
                    //Voeg parameters toe voor identiteit groep attributen.
                    final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
                    for (Attribuut attribuut : attributen) {
                        if (behoortTotJavaLogischModel(attribuut)) {
                            //Alleen Dynamische object typen worden als parameter opgenomen.
                            if ("OT".equals(attribuut.getType().getSoortElement().getCode())
                                && 'D' == attribuut.getType().getSoortInhoud())
                            {
                                final JavaType paramType;
                                if ("OT".equals(attribuut.getType().getSoortElement().getCode())) {
                                    paramType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                                        attribuut.getType());
                                } else {
                                    paramType = algemeneNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                                }
                                String paramNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                                final JavaFunctieParameter param = new JavaFunctieParameter(paramNaam, paramType);
                                param.setJavaDoc("Bijbehorende " + attribuut.getType().getNaam() + ".");
                                constructor.voegParameterToe(param);
                                if (!isSubtype(objectType)) {
                                    constructorBody.append(String.format(
                                        "this.%1$s = %2$s;%n",
                                        GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                                        paramNaam));
                                }
                            } else if (!"id".equalsIgnoreCase(attribuut.getIdentCode())
                                && !isSubtype(objectType))
                            {
                                kopieObjectTypeGebruiktInBody = true;
                                final String copyCode = "this.%1$s = %2$s.get%3$s();%n";
                                constructorBody.append(
                                    String.format(copyCode,
                                        GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                                        consParamNaam,
                                        attribuut.getIdentCode()));
                            }
                        }
                    }
                    //Bij een subtype roepen we de super constructor aan met alle constructor parameters.
                    if (isSubtype(objectType)) {
                        kopieObjectTypeGebruiktInBody = true;
                        constructorBody.append("super(");
                        for (JavaFunctieParameter consParameter : constructor.getParameters()) {
                            constructorBody.append(consParameter.getNaam()).append(", ");
                        }
                        constructorBody.deleteCharAt(constructorBody.lastIndexOf(","));
                        constructorBody.append(String.format(");%n"));
                    }
                } else {
                    //Maak een copy statement in de body.
                    kopieObjectTypeGebruiktInBody = true;
                    constructorBody.append(
                        String.format("if (%2$s.get%3$s() != null) {this.%1$s = new %4$s(%2$s.get%3$s());}%n",
                            GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode()),
                            consParamNaam,
                            groep.getIdentCode(),
                            operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam()));
                }
            }
        }
        constructor.setBody(constructorBody.toString());

        // Als de eerste (kopie) parameter niet gebruikt wordt, verwijder hem dan (PMD check).
        if (!kopieObjectTypeGebruiktInBody) {
            constructor.getParameters().remove(0);
        }
        // Boeg de nieuwe constructor alleen toe als die niet al bestaat (zou kunnen door verwijderen kopie parameter).
        if (!clazz.heeftConstructor(constructor)) {
            clazz.voegConstructorToe(constructor);
        }
    }

    /**
     * Bouwt velden voor alle status historie velden.
     *
     * @param objectType Het object type dat status historie velden kent in het operationeel model.
     * @param clazz De klasse waaraan de velden moeten worden toegevoegd.
     */
    private void genereerStatusHistorieVeldenVoorObjectType(final ObjectType objectType, final JavaKlasse clazz) {
        final List<Attribuut> statusHistorieAttributenVoorObjectType =
            getBmrDao().getStatusHistorieAttributenVanObjectType(objectType);

        for (Attribuut attribuut : statusHistorieAttributenVoorObjectType) {
            final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
            JavaType javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());

            final JavaVeld veld = new JavaVeld(javaType, veldNaam);
            clazz.voegVeldToe(veld);

            final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
            genereerGetterJavaDoc(getter, objectType, attribuut);
            clazz.voegGetterToe(getter);

            final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
            genereerSetterJavaDoc(setter, objectType, attribuut);
            clazz.voegSetterToe(setter);

            annoteerAttribuutVeld(objectType, attribuut, veld, false, true, false);
        }
    }

    /**
     * Voeg het initialiseren van de status his velden (= op 'X' zetten) toe aan de constructoren
     * waarvoor dat van toepassing is.
     *
     * @param objectType het object type
     * @param klasse de klasse
     */
    private void voegStatusHisVeldenInitialisatieToe(final ObjectType objectType, final JavaKlasse klasse) {
        final List<Attribuut> statusHistorieAttributenVoorObjectType =
                getBmrDao().getStatusHistorieAttributenVanObjectType(objectType);

        final StringBuilder extraConstructorBody = new StringBuilder();

        for (Attribuut attribuut : statusHistorieAttributenVoorObjectType) {
            final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
            // Elk status his veld wordt standaard op 'X' gezet.
            // Bij het aanmaken van een nieuw object is dit de default waarde.
            // Als Hibernate het initialiseert, zul de waarde overschreven worden met de DB waarde.
            extraConstructorBody.append(String.format("this.%s = StatusHistorie.X;%n", veldNaam));
        }
        List<Constructor> aanTePassenConstructoren = new ArrayList<Constructor>();
        if (this.heeftSupertype(objectType)) {
            // Voeg het initialiseren van de status his velden toe aan alle constructoren
            // bij hierarchische types, omdat daar geen constructor is waar 'alle code langs komt'.
            aanTePassenConstructoren.addAll(klasse.getConstructoren());
        } else {
            // Dit, in tegenstelling tot niet-hierarchische types, waar alle code langs de no-args constructor komt.
            aanTePassenConstructoren.add(klasse.getConstructor());
        }
        for (Constructor constructor : aanTePassenConstructoren) {
            constructor.setBody(constructor.getBody() + extraConstructorBody.toString());
        }
    }

    /**
     * Bouwt een standaard lege constructor met een standaard javadoc voor de opgegeven klasse.
     *
     * @param clazz de klasse waarvoor de constructor moet worden gebouwd..
     * @return de constructor.
     */
    private Constructor genereerLegeConstructor(final JavaKlasse clazz) {
        final Constructor constructor = new Constructor(JavaAccessModifier.PROTECTED, clazz.getNaam());
        constructor.setJavaDoc("Standaard constructor (t.b.v. Hibernate/JPA).");
        return constructor;
    }

    /**
     * Bouwt een standaard constructor met een standaard javadoc voor de opgegeven klasse, waarbij alle identiteits
     * velden als parameters meegegeven kunnen worden (en gezet worden).
     *
     * @param clazz de klasse waarvoor de constructor moet worden gebouwd.
     * @param objectType het objecttype waarvoor de klasse geldt.
     */
    private void genereerConstructorMetIdentiteitParameters(final JavaKlasse clazz,
        final ObjectType objectType)
    {
        boolean genereerConstructor = false;
        Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz.getNaam());
        final StringBuilder constructorBody = new StringBuilder();
        constructorBody.append(String.format("this();%n"));

        List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
        // Bij een subtype moeten we de Identiteit groep van het finale supertype hebben.
        if (this.isSubtype(objectType)) {
            groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType.getFinaalSupertype());
        }
        for (Groep groep : groepenVoorObjectType) {
            if (isIdentiteitGroep(groep)) {
                final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
                for (Attribuut attribuut : attributen) {
                    //ID wordt niet als parameter opgenomen.
                    if (!isIdAttribuut(attribuut) && behoortTotJavaOperationeelModel(attribuut)) {
                        genereerConstructor = true;
                        final JavaType javaType =
                            operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                        final String naam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                        constructor.getParameters().add(new JavaFunctieParameter(
                            naam, javaType, String.format("%1$s van %2$s.", naam, objectType.getNaam())));
                        constructorBody.append(String.format("this.%1$s = %1$s;%n", naam));
                    }
                }
                break;
            }
        }
        if (genereerConstructor) {
            if (this.isSubtype(objectType)) {
                Attribuut discriminatorAttribuut = this.bepaalDiscriminatorAttribuut(objectType);
                Tuple discriminatorTuple = this.bepaalDiscriminatorTuple(objectType);
                final ObjectType discriminatorObjectType =
                    this.getBmrDao().getElement(discriminatorAttribuut.getType().getId(), ObjectType.class);
                // In het geval van een finaal subtype, willen we het discriminator attribuut specifiek meegeven
                // en de super constructor aanroepen.
                if (isFinaalSubtype(objectType)) {
                    JavaType javaTypeDiscriminatorObject = new AlgemeneNaamgevingStrategie().
                            getJavaTypeVoorElement(discriminatorObjectType);
                    clazz.voegExtraImportToe(javaTypeDiscriminatorObject.getFullyQualifiedClassName());
                    String enumExpressie = javaTypeDiscriminatorObject.getNaam();
                    enumExpressie += ".";
                    enumExpressie += JavaGeneratieUtil.genereerNaamVoorEnumWaarde(discriminatorTuple.getIdentCode());
                    Map<String, String> discriminatorOverride = new HashMap<String, String>();
                    String veldNaam = GeneratieUtil.lowerTheFirstCharacter(discriminatorAttribuut.getIdentCode());
                    discriminatorOverride.put(veldNaam, enumExpressie);
                    constructor = kopieerConstructorMetSuperAanroep(constructor, discriminatorOverride);
                } else {
                    // Een tussenliggend type geeft 'gewoon' alles door aan de super constructor.
                    constructor = kopieerConstructorMetSuperAanroep(constructor);
                }
            } else {
                // In het geval van geen subtype, geven we de 'normale' assignment body voor de constructor.
                constructor.setBody(constructorBody.toString());
            }
            // Als er nu een constructor zonder parameters bijkomt, haal dan de eventueel
            // al bestaande constructors zonder parameters weg.
            if (constructor.getParameters().isEmpty()) {
                Iterator<Constructor> constructorIterator = clazz.getConstructoren().iterator();
                while (constructorIterator.hasNext()) {
                    Constructor eenConstructor = constructorIterator.next();
                    if (eenConstructor.getParameters().isEmpty()) {
                        constructorIterator.remove();
                    }
                }
            }
            constructor.setJavaDoc("Standaard constructor die direct alle identificerende attributen "
                    + "instantieert of doorgeeft.");
            clazz.voegConstructorToe(constructor);
        }
    }

    /**
     * Bouwt de inverse associaties voor een object type. Adres kent een Persoon dus hier wordt aan Persoon een lijst
     * van adressen toegevoegd.
     * LET OP: Inverse associatie wordt enkel gegenereerd indien de inverse associatie een identCode waarde heeft
     * in het BMR.
     *
     * @param objectType Object type dat mogelijk inverse associaties kent.
     * @param clazz De Java klasse representatie van het object type waar de inverse associatie in gemaakt moet worden.
     */
    private void genereerInverseAssociatiesVoorObjectType(final ObjectType objectType, final JavaKlasse clazz) {
        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        String body = clazz.getConstructor().getBody();
        final StringBuilder constructorBody = new StringBuilder(body);

        for (Attribuut inverseAttr : inverseAttrVoorObjectType) {
            final JavaType inverseType =
                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(inverseAttr.getObjectType());
            final JavaType javaType = new JavaType(JavaType.SET, inverseType);
            final String classVariabele =
                GeneratieUtil.lowerTheFirstCharacter(inverseAttr.getInverseAssociatieIdentCode());
            final JavaVeld veld = new JavaVeld(javaType, classVariabele);
            clazz.voegVeldToe(veld);
            // JPA annotaties.
            AnnotatieWaardeParameter lazinessAnnotatieParameter =
                new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, "LAZY");
            //FIXME: specifieke hack om de collectie van betrokkenheden in relatie eager te maken.
            //TODO: Moet uiteindelijk opgelost worden met een extra veldje in het BMR m.b.t. lazy/eager inverse ass.
            if (objectType.getIdentCode().equals("Relatie") && classVariabele.equals("betrokkenheden")) {
                lazinessAnnotatieParameter = new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, "EAGER");
            }
            final JavaAnnotatie oneToManyAnn = new JavaAnnotatie(JavaType.ONE_TO_MANY,
                lazinessAnnotatieParameter,
                new AnnotatieWaardeParameter("cascade", JavaType.CASCADE_TYPE, "ALL"));
            veld.voegAnnotatieToe(oneToManyAnn);

            final JavaAnnotatie joinColAnn = new JavaAnnotatie(JavaType.JOIN_COLUMN,
                new AnnotatieWaardeParameter("name", inverseAttr.getIdentDb()));
            veld.voegAnnotatieToe(joinColAnn);

            this.voegJsonPropertyAnnotatieToe(veld);
            boolean sorteringToegevoegd = this.voegSorteringAnnotatieToeIndienNodig(veld, inverseAttr);

            final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
            if (clazz.getSuperInterfaces().isEmpty()
                || 'N' == inverseAttr.getObjectType().getInLgm())
            {
                // Klasse implementeert niet het logisch model. Dus ook geen functies daarvan. Of het
                // object type zit niet in het logisch model. (in_set = Nee)
                genereerInverseAssociatieGetterJavaDoc(getter, objectType, inverseAttr);
            } else {
                getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            }
            clazz.voegGetterToe(getter);

            // Er wordt geen setter toegevoegd, daar deze immutable (maar wel aanpasbaar) moeten zijn.
            // Hierdoor dient de collectie wel reeds in de constructor te worden geinstantieerd.
            JavaType collectionJavaType = bepaalSetImplementatie(sorteringToegevoegd);
            constructorBody.append(String.format("%1$s = new %2$s<%3$s>();%n",
                veld.getNaam(), collectionJavaType.getNaam(), inverseType.getNaam()));
            clazz.voegExtraImportToe(collectionJavaType.getFullyQualifiedClassName());
        }

        clazz.getConstructor().setBody(constructorBody.toString());
    }

    /**
     * Genereert voor een groep een veld in een java klasse.
     *
     * @param clazz De klasse waaraan het veld wordt toegevoegd.
     * @param objectType Het object type waar de groep bij hoort.
     * @param groep De groep waarvoor een veld wordt aangemaakt.
     * @param groepBehoortTotSubType Geeft aan of groep een direct attribuut is van objecttype of van een subtype van
     * objecttype.
     */
    private void genereerVeldVoorGroep(final JavaKlasse clazz, final ObjectType objectType, final Groep groep,
        final boolean groepBehoortTotSubType)
    {
        // Dit is dus een attribuut met als type Groep.
        final JavaType javaTypeVoorGroepVeld = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode());
        final JavaVeld groepVeld = new JavaVeld(javaTypeVoorGroepVeld, veldNaam);
        groepVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
        this.voegJsonPropertyAnnotatieToe(groepVeld);
        clazz.voegVeldToe(groepVeld);

        final JavaAccessorFunctie getter = new JavaAccessorFunctie(groepVeld);
        if (!clazz.getSuperInterfaces().isEmpty() && !groepBehoortTotSubType) {
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        } else {
            genereerGetterJavaDoc(getter, objectType, groep);
        }
        clazz.voegGetterToe(getter);

        genereerSetterVoorGroepVeld(clazz, objectType, groep, groepVeld);
    }

    /**
     * Genereer een setter voor een groep-veld (dus niet een veld in een groep), inclusief
     * eventuele aanpassing van het bijbehorende status his veld.
     *
     * @param clazz de klasse
     * @param objectType het object type
     * @param groep de groep
     * @param groepVeld het groep-veld
     */
    private void genereerSetterVoorGroepVeld(final JavaKlasse clazz,
            final ObjectType objectType, final Groep groep, final JavaVeld groepVeld)
    {
        final JavaMutatorFunctie setter = new JavaMutatorFunctie(groepVeld);
        genereerSetterJavaDoc(setter, objectType, groep);
        Attribuut statusHisAttribuut = this.getBmrDao().getStatusHisAttribuutVoorGroep(groep);
        if (statusHisAttribuut != null) {
            // Als er een status his attribuut bij deze groep hoort, dan wordt dat op A gezet
            // als er een niet-null groep object wordt gezet in deze setter.
            StringBuilder setterBody = new StringBuilder(setter.getBody());
            setterBody.append(String.format("if (%s != null) {%n", setter.getParameters().get(0).getNaam()));
            String statusHisVeldNaam = GeneratieUtil.lowerTheFirstCharacter(statusHisAttribuut.getIdentCode());
            setterBody.append(String.format("    %s = StatusHistorie.A;%n", statusHisVeldNaam));
            setterBody.append(String.format("}%n"));
            setter.setBody(setterBody.toString());

            // Voeg ook een extra comment toe aan de javadoc. Eerst de huidige joinen, omdat het een list is geworden.
            String huidigeJavaDoc = StringUtils.join(setter.getJavaDoc(), " ");
            setter.setJavaDoc(huidigeJavaDoc + " Zet tevens het bijbehorende "
                    + "status his veld op 'A' als het argument niet null is.");
        }
        clazz.voegSetterToe(setter);
    }

    /**
     * Genereert velden voor elk attribuut dat voorkomt in de identiteit groep. Voor elk veld (behalve een ID veld)
     * worden ook een getter toegevoegd, maar geen setter.
     *
     * @param clazz Java klasse waarin de velden terecht komen.
     * @param objectType Object type uit het BMR waar deze groep bij hoort.
     * @param groep De identiteit groep.
     * @param discrAttribuut Eventuele discriminator attribuut dat aanwezig kan zijn in de groep.
     */
    private void genereerVeldenVoorIdentiteitGroep(final JavaKlasse clazz, final ObjectType objectType,
        final Groep groep, final Attribuut discrAttribuut)
    {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            if (isIdAttribuut(attribuut)) {
                final JavaVeld idVeld = genereerJavaVeldVoorAttribuutType(attribuut);
                annoteerIdVeld(objectType, idVeld, false);
                clazz.voegVeldToe(idVeld);
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(idVeld);
                genereerGetterJavaDoc(getter, objectType, attribuut);
                clazz.voegGetterToe(getter);
            } else if (behoortTotJavaOperationeelModel(attribuut)) {
                final JavaType javaType =
                    operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                final JavaVeld veld =
                    new JavaVeld(javaType, GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()));
                clazz.voegVeldToe(veld);

                annoteerAttribuutVeld(objectType, attribuut, veld,
                        isDiscriminatorAttribuut(attribuut, discrAttribuut),
                        isJsonProperty(attribuut), false);

                final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
                genereerGetterJavaDoc(getter, objectType, attribuut);
                clazz.voegGetterToe(getter);
            }
        }
    }

    /**
     * Voegt een sortering/ordering annotatie toe aan het veld indien nodig. Methode retourneert een boolean die
     * aangeeft of de sortering/ordering annotatie is toegevoegd of niet.
     *
     * @param veld het veld
     * @param attribuut het attribuut in het BMR waarvoor het veld is aangemaakt.
     * @return boolean die aangeeft of de annotatie is toegevoegd of niet.
     */
    private boolean voegSorteringAnnotatieToeIndienNodig(final JavaVeld veld, final Attribuut attribuut) {
        final boolean sorteringToegevoegd;

        // Alleen sortering toevoegen als het niet om een koppeling objecttype gaat.
        if (attribuut.getGroep().getObjectType().getKoppeling() == null
            || !attribuut.getGroep().getObjectType().getKoppeling().equals('J'))
        {
            veld.voegAnnotatieToe(
                new JavaAnnotatie(JavaType.SORT, new AnnotatieWaardeParameter("type", JavaType.SORT_TYPE, "NATURAL")));
            sorteringToegevoegd = true;
        } else {
            sorteringToegevoegd = false;
        }
        return sorteringToegevoegd;
    }

    /**
     * Retourneert een te gebruiken {@link java.util.Set} implementatie (simple name) op basis van of de set
     * sorteerbaar moet zijn of niet.
     *
     * @param sorteerbaar of de set implementatie sorteerbaar moet zijn.
     * @return een java type voor de te gebruiken Set interface.
     */
    private JavaType bepaalSetImplementatie(final boolean sorteerbaar) {
        final JavaType setImplementatie;
        if (sorteerbaar) {
            setImplementatie = JavaType.TREE_SET;
        } else {
            setImplementatie = JavaType.HASH_SET;
        }
        return setImplementatie;
    }

}
