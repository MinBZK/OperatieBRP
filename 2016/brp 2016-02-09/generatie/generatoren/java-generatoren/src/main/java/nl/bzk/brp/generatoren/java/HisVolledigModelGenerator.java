/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaGenericParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigInterfaceModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.springframework.stereotype.Component;

/**
 * Deze generator genereert een model dat een volledige historie bevat. Dat his volledig model wordt onder andere gebruikt voor de serialisatie van PL's.
 */
@Component("hisVolledigModelJavaGenerator")
public class HisVolledigModelGenerator extends AbstractHisVolledigGenerator {

    private final NaamgevingStrategie hisVolledigModelNaamgevingStrategie  = new HisVolledigModelNaamgevingStrategie();
    private final NaamgevingStrategie operationeelModelNaamgevingStrategie = new OperationeelModelNaamgevingStrategie();
    private final NaamgevingStrategie interfaceNaamgevingStrategie         =
        new HisVolledigInterfaceModelNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<JavaKlasse> klassen = new ArrayList<>();
        final List<ObjectType> objectTypen = this.getHisVolledigObjectTypen();

        for (ObjectType objectType : objectTypen) {
            if (objectType.getId() == ID_PERSOON_INDICATIE) {
                // Specifieke uitzondering voor indicaties ivm subtypering.
                klassen.addAll(genereerPersoonIndicatieSubtypes(objectType));
            } else {
                klassen.add(genereerHisVolledigImplKlasse(objectType));
            }
        }
        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(klassen, this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    /**
     * Genereer een his volledig impl klasse voor het meegegeven object type.
     *
     * @param objectType het object type
     * @return de gegenereerde klasse
     */
    private JavaKlasse genereerHisVolledigImplKlasse(final ObjectType objectType) {
        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        filterIndicatiesUitInverseAssociaties(inverseAttrVoorObjectType);

        // Genereer klasse voor het betreffende his volledig objecttype.
        final JavaKlasse klasse = genereerHisVolledigKlasseVoorObjectType(objectType);

        //Voeg een default constructor toe voor JPA.
        Constructor defaultConstructor = genereerDefaultConstructor(klasse, inverseAttrVoorObjectType, objectType);
        klasse.voegConstructorToe(defaultConstructor);

        //Voeg een constructor toe met verplichte velden en eventuele backreference.
        Constructor constructor = genereerConstructorMetIdentiteitParameters(
            klasse, objectType, hisVolledigModelNaamgevingStrategie);

        // voeg constructor alleen toe als deze niet een no-arg constructor is
        if (constructor != null) {
            // gooi eerder gegenereerde default constructor weg
            if (constructor.isConstructorZonderArgumenten() && isSubtype(objectType)) {
                klasse.getConstructoren().remove(defaultConstructor);
            }
            klasse.voegConstructorToe(constructor);

            if (constructor.isConstructorZonderArgumenten() && defaultConstructor.isConstructorZonderArgumenten()) {
                if (constructor.getBody().isEmpty()) {
                    klasse.getConstructoren().remove(constructor);
                }
            }
        }

        // Genereer de velden voor alle groepen die op dit object type van toepassing zijn.
        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);
        voegVeldenToeVoorGroepen(klasse, objectType, groepen);

        // Genereer velden/lijsten voor de inverse associaties.
        voegVeldenToeVoorInverseAssociatieAttributen(klasse, objectType, inverseAttrVoorObjectType);

        if (objectType.getId() == ID_PERSOON_LGM) {
            genereerVeldenVoorIndicaties(klasse);
        }

        // Voeg de 'Element getElement()' functie toe, alleen voor objecttypen van het KERN schema.
        if (objectType.getSchema().getId() == ID_KERN_SCHEMA) {
            voegGetElementFunctieToe(klasse, objectType);
        }

        return klasse;
    }

    /**
     * Genereer de velden met getters en setters voor ieder soort persoon indicatie.
     *
     * @param klasse de persoon his volledig klasse
     */
    private void genereerVeldenVoorIndicaties(final JavaKlasse klasse) {
        ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        ObjectType persoonIndicatieObjectType = this.getBmrDao().getElement(ID_PERSOON_INDICATIE, ObjectType.class);
        JavaType indicatieSuperType = hisVolledigModelNaamgevingStrategie
            .getJavaTypeVoorElement(persoonIndicatieObjectType);
        String lijstVanIndicatieGetters = "";
        for (Tuple tuple : soortIndicatieObjectType.getTuples()) {
            // Velden voor alle subtypen.
            JavaType indicatieJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, indicatieSuperType);
            JavaVeld veld = new JavaVeld(indicatieJavaType, GeneratieUtil.lowerTheFirstCharacter(tuple.getIdentCode()));
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ONE_TO_ONE,
                new AnnotatieWaardeParameter("cascade", JavaType.CASCADE_TYPE, "ALL"),
                new AnnotatieWaardeParameter("mappedBy", "persoon")));
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
            klasse.voegVeldToe(veld);

            // Getters voor alle velden.
            JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            klasse.voegGetterToe(getter);

            // Setters voor alle velden.
            // Nodig voor het toevoegen van een indicatie his volledig van een specifiek subtype.
            JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
            setter.getParameters().get(0).setJavaDoc("indicatie");
            setter.setJavaDoc("Zet de indicatie his volledig voor subtype " + tuple.getNaam() + ". "
                + "Let op: alleen gebruiken als er nog geen his volledig van dit subtype is! "
                + "Voor het toevoegen van historie aan een bestaande indicatie his volledig, "
                + "gebruik de indicatie his volledig getter + getHistorie + voegToe.");
            klasse.voegSetterToe(setter);

            if (lijstVanIndicatieGetters.length() > 0) {
                lijstVanIndicatieGetters += ", ";
            }
            lijstVanIndicatieGetters += getter.getNaam() + "()";
        }

        voegCustomGetIndicatiesToe(klasse, indicatieSuperType, lijstVanIndicatieGetters);
    }

    /**
     * Specifieke methode voor het genereren van de klasse hierarchie voor persoon indicaties. Hierbij wordt voor elke soort indicatie een subklasse
     * gegenereerd. De super klasse is al in het model aanwezig.
     *
     * @param persoonIndicatieObjectType het object type in het BMR van persoon indicatie
     * @return de lijst met historie (sub)klassen voor de persoon indicatie his volledig
     */
    private List<JavaKlasse> genereerPersoonIndicatieSubtypes(final ObjectType persoonIndicatieObjectType) {
        List<JavaKlasse> javaKlassen = new ArrayList<>();
        ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        for (Tuple tuple : soortIndicatieObjectType.getTuples()) {
            boolean heeftMaterieleHistorie = heeftPersoonIndicatieTupleMaterieleHistorie(tuple);

            JavaKlasse klasse = this.genereerPersoonIndicatieSubtypeSetup(
                persoonIndicatieObjectType, tuple, hisVolledigModelNaamgevingStrategie);
            klasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY));
            klasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.DISCRIMINATOR_VALUE,
                new AnnotatieWaardeParameter("value", tuple.getVolgnummerT().toString())));
            klasse.voegConstructorToe(genereerDefaultLegeConstructor(klasse));

            // Constructor
            ObjectType backreferenceType = this.getBmrDao().getElement(ID_PERSOON_LGM, ObjectType.class);
            JavaType backreferenceJavaType = hisVolledigModelNaamgevingStrategie
                .getJavaTypeVoorElement(backreferenceType);

            Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constructor.setJavaDoc("Constructor met backreference naar persoon.");
            constructor.voegParameterToe(new JavaFunctieParameter(
                "persoon", backreferenceJavaType, "backreference"));
            JavaType soortIndicatieJavaType = new AlgemeneNaamgevingStrategie()
                .getJavaTypeVoorElement(soortIndicatieObjectType);
            JavaType soortIndicatieAttribuutJavaType = new AttribuutWrapperNaamgevingStrategie()
                .getJavaTypeVoorElement(soortIndicatieObjectType);
            constructor.setBody(String.format("super(persoon, new %1$s(%2$s.%3$s));",
                soortIndicatieAttribuutJavaType.getNaam(),
                soortIndicatieJavaType.getNaam(),
                JavaGeneratieUtil.genereerNaamVoorEnumWaarde(tuple.getIdentCode())));
            klasse.voegExtraImportsToe(soortIndicatieAttribuutJavaType, soortIndicatieJavaType);
            klasse.voegConstructorToe(constructor);

            // Methode voor ophalen historie
            JavaType smartSetInterfaceType;
            JavaType smartSetImplType;
            if (heeftMaterieleHistorie) {
                smartSetInterfaceType = JavaType.MATERIELE_HISTORIE_SET;
                smartSetImplType = JavaType.MATERIELE_HISTORIE_SET_IMPL;
            } else {
                smartSetInterfaceType = JavaType.FORMELE_HISTORIE_SET;
                smartSetImplType = JavaType.FORMELE_HISTORIE_SET_IMPL;
            }
            JavaType hisIndicatieJavaType = bepaalHisIndicatieJavaType(tuple);
            JavaType returnType = new JavaType(smartSetInterfaceType, new JavaGenericParameter(hisIndicatieJavaType));
            JavaFunctie historieFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, returnType,
                "getPersoonIndicatieHistorie", null);
            historieFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append("if (hisPersoonIndicatieLijst == null) {").append(newline());
            bodyBuilder.append(String.format("hisPersoonIndicatieLijst = new HashSet<>();" + newline(),
                hisIndicatieJavaType.getNaam()));
            bodyBuilder.append("}").append(newline());
            bodyBuilder.append("if (persoonIndicatieHistorie == null) {").append(newline());
            bodyBuilder.append(String.format("persoonIndicatieHistorie = new %1$s<%2$s>(hisPersoonIndicatieLijst);"
                    + newline(),
                smartSetImplType.getNaam(),
                hisIndicatieJavaType.getNaam()));
            bodyBuilder.append("}").append(newline());
            bodyBuilder.append(String.format("return (%1$s<%2$s>) persoonIndicatieHistorie;" + newline(),
                smartSetInterfaceType.getNaam(),
                hisIndicatieJavaType.getNaam()));
            historieFunctie.setBody(bodyBuilder.toString());
            klasse.voegFunctieToe(historieFunctie);
            klasse.voegExtraImportsToe(JavaType.HASH_SET, smartSetImplType);

            javaKlassen.add(klasse);
        }
        return javaKlassen;
    }

    /**
     * Genereer een default constructor (nodig voor JPA). Initialiseert tevens alle inverse associatie sets op een nieuwe lege hashset.
     *
     * @param klasse                    de klasse
     * @param inverseAttrVoorObjectType alle inverse associaties
     * @param objectType
     * @return de gemaakte constructor
     */
    private Constructor genereerDefaultConstructor(
        final JavaKlasse klasse, final List<Attribuut> inverseAttrVoorObjectType, final ObjectType objectType)
    {
        final Constructor defaultConstructor;
        if (heeftAlleenIdAttribuutInIdentiteitGroep(objectType) && !isSubtype(objectType)) {
            defaultConstructor = new Constructor(JavaAccessModifier.PUBLIC, klasse);
        } else {
            defaultConstructor = new Constructor(JavaAccessModifier.PROTECTED, klasse);
        }
        final StringBuilder body = new StringBuilder();
        for (Attribuut attribuut : inverseAttrVoorObjectType) {
            if (behoortTotJavaOperationeelModel(attribuut.getObjectType())) {
                final String naam =
                    JavaGeneratieUtil.toValidJavaVariableName(attribuut.getInverseAssociatieIdentCode());
                final JavaType javaTypeVoorElement = hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(
                    attribuut.getObjectType());

                boolean isGesorteerd = false;
                boolean gebruiktStandaardComparator = true;
                JavaType setType = JavaType.HASH_SET;
                if (bevatVolgnummer(attribuut.getObjectType())) {
                    setType = JavaType.TREE_SET;
                    isGesorteerd = true;
                    gebruiktStandaardComparator = false;
                }
                body.append(String.format(
                    "%s = %s;%s", naam,
                    bouwSetInitialisatieCodeFragmentEnVoegImportsToe(setType, attribuut.getObjectType(), javaTypeVoorElement,
                        klasse, isGesorteerd, gebruiktStandaardComparator, JavaType.HIS_VOLLEDIG_COMPARATOR_FACTORY),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            }
        }

        defaultConstructor.setBody(body.toString());
        defaultConstructor.setJavaDoc("Default contructor voor JPA.");
        return defaultConstructor;
    }

    /**
     * Bepaalt of een identiteitgroep van een objecttype alleen een ID attribuut heeft.
     *
     * @param objectType object type waarin de identiteitgroep wordt gecontroleerd
     * @return {@code true} indien
     */
    private boolean heeftAlleenIdAttribuutInIdentiteitGroep(final ObjectType objectType) {
        final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(getIdentiteitGroep(objectType));

        for (Attribuut attribuut : attributenVanGroep) {
            if (!isIdAttribuut(attribuut)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Voegt velden toe voor de inverse associatie attributen velden in de Persoon klasse. Tevens worden getters en setters toegevoegd en bijbehorende
     * annotaties.
     *
     * @param klasse                    de persoon klasse
     * @param objectType                het object type
     * @param inverseAttrVoorObjectType de inverse associatie attributen van persoon
     */
    private void voegVeldenToeVoorInverseAssociatieAttributen(final JavaKlasse klasse,
        final ObjectType objectType,
        final List<Attribuut> inverseAttrVoorObjectType)
    {
        for (Attribuut attribuut : inverseAttrVoorObjectType) {
            final ObjectType inverseObjectType = attribuut.getObjectType();
            if (behoortTotJavaOperationeelModel(inverseObjectType)) {

                final JavaType javaTypeVoorElement = hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(
                    inverseObjectType);
                JavaType setType = JavaType.SET;
                if (bevatVolgnummer(attribuut.getObjectType())) {
                    setType = JavaType.SORTED_SET;
                }
                final JavaVeld veld = new JavaVeld(
                    new JavaType(setType, javaTypeVoorElement),
                    JavaGeneratieUtil.toValidJavaVariableName(attribuut.getInverseAssociatieIdentCode()));

                boolean jsonPropertyOpnemenEnEagerCollectie = true;
                // Custom hack om in specifieke gevallen geen property annotatie op te nemen en
                // de collectie lazy te loaden, zie ROMEO-131.
                if ((objectType.getId() == ID_RELATIE_LGM && attribuut.getObjectType().getId() == ID_BETROKKENHEID_LGM)
                    || (objectType.getId() == ID_ONDERZOEK_LGM && attribuut.getObjectType().getId() == ID_PERSOON_ONDERZOEK_LGM))
                {
                    jsonPropertyOpnemenEnEagerCollectie = false;
                }
                annoteerCollectieVeld(veld, inverseObjectType, objectType,
                    jsonPropertyOpnemenEnEagerCollectie, jsonPropertyOpnemenEnEagerCollectie, false);

                klasse.voegVeldToe(veld);

                //Genereer een getter.
                final JavaAccessorFunctie javaAccessorFunctie = new JavaAccessorFunctie(veld);
                javaAccessorFunctie.setJavaDoc("Retourneert lijst van " + inverseObjectType.getNaam() + ".");
                javaAccessorFunctie.setReturnWaardeJavaDoc("lijst van " + inverseObjectType.getNaam());
                javaAccessorFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                klasse.voegGetterToe(javaAccessorFunctie);

                //Genereer een setter.
                final JavaMutatorFunctie javaMutatorFunctie = new JavaMutatorFunctie(veld);
                javaMutatorFunctie.setJavaDoc("Zet lijst van " + inverseObjectType.getNaam() + ".");
                javaMutatorFunctie.getParameters().get(0).setJavaDoc("lijst van " + inverseObjectType.getNaam());
                klasse.voegSetterToe(javaMutatorFunctie);
            }
        }
    }

    /**
     * Genereert een klasse voor een objectType. Voegt nog geen velden toe.
     *
     * @param objectType het objecttype
     * @return java klasse
     */
    private JavaKlasse genereerHisVolledigKlasseVoorObjectType(final ObjectType objectType) {
        final JavaKlasse hisVolledigJavaKlasse = new JavaKlasse(
            hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
            "HisVolledig klasse voor " + objectType.getNaam() + ".");

        // Bepaal het eventueel aanwezige discriminator attribuut.
        Attribuut discrAttribuut = this.bepaalDiscriminatorAttribuut(objectType);

        if (isFinaalSupertype(objectType) || isTussenliggendType(objectType)) {
            hisVolledigJavaKlasse.setAbstractClass(true);
        }

        if (!isTussenliggendType(objectType)) {
            hisVolledigJavaKlasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY));
        }

        if (isFinaalSupertype(objectType) || isNietHierarchischType(objectType)) {
            //Voeg JPA annotaties toe voor discriminator
            final JavaAnnotatie tabelAnnotatie = new JavaAnnotatie(JavaType.TABLE,
                new AnnotatieWaardeParameter("schema", objectType.getSchema().getNaam()),
                new AnnotatieWaardeParameter("name", objectType.getIdentDb()));
            hisVolledigJavaKlasse.voegAnnotatieToe(tabelAnnotatie);

            //Tagging interface
            hisVolledigJavaKlasse.voegSuperInterfaceToe(JavaType.HISVOLLEDIGIMPL);

            if (isFinaalSupertype(objectType)) {
                hisVolledigJavaKlasse.voegAnnotatieToe(new JavaAnnotatie(
                    JavaType.INHERITANCE,
                    new AnnotatieWaardeParameter("strategy", JavaType.INHERITANCE_TYPE, "SINGLE_TABLE")));
                hisVolledigJavaKlasse.voegAnnotatieToe(new JavaAnnotatie(
                    JavaType.DISCRIMINATOR_COLUMN,
                    new AnnotatieWaardeParameter("name", discrAttribuut.getIdentDb()),
                    new AnnotatieWaardeParameter("discriminatorType", JavaType.DISCRIMINATOR_TYPE, "INTEGER")));
            }
        }

        if (isSubtype(objectType)) {
            hisVolledigJavaKlasse.setExtendsFrom(
                hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        }

        if (isTussenliggendType(objectType)) {
            hisVolledigJavaKlasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.MAPPED_SUPER_CLASS));
        }

        if (isFinaalSubtype(objectType)) {
            Tuple discriminatorTuple = bepaalDiscriminatorTuple(objectType);
            hisVolledigJavaKlasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.DISCRIMINATOR_VALUE,
                new AnnotatieWaardeParameter("value", discriminatorTuple.getVolgnummerT().toString())));
        }

        hisVolledigJavaKlasse.voegSuperInterfaceToe(interfaceNaamgevingStrategie.getJavaTypeVoorElement(objectType));

        if (heeftMinstensEenGroepMetHistorie(objectType)) {
            hisVolledigJavaKlasse.voegSuperInterfaceToe(JavaType.A_LAAG_AFLEIDBAAR);
            voegALaagAfleidingToe(hisVolledigJavaKlasse, objectType);
        }

        //JPA Access annotatie
        hisVolledigJavaKlasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.ACCESS,
            new AnnotatieWaardeParameter("value", JavaType.ACCESS_TYPE, "FIELD")));

        return hisVolledigJavaKlasse;
    }

    /**
     * Voegt voor het opgegeven objecttype een methode toe aan de opgegeven klasse. Deze methode verzorgt de A-Laag afleiding voor het objecttype door alle
     * groepen te vervangen door een nieuwe groep met de juiste attribuut waardes die worden gehaald uit de actuele historie instanties van die groepen.
     *
     * @param hisVolledigJavaKlasse de Java klasse waar de A-Laag afleiding methode aan toegevoegd moet worden.
     * @param objectType            het objecttype waarvoor de A-Laag afleiding moet gebeuren.
     */
    private void voegALaagAfleidingToe(final JavaKlasse hisVolledigJavaKlasse, final ObjectType objectType) {
        final JavaFunctie aLaagAfleidingFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, "leidALaagAf");
        aLaagAfleidingFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        aLaagAfleidingFunctie.setJavaDoc("Afleiding ALaag attrinbuten/groepen behorende bij objecttype.");

        final StringBuilder functieBody = new StringBuilder();
        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);
        for (Groep groep : groepen) {
            if (behoortTotJavaOperationeelModel(groep) && groepKentHistorie(groep) && !isIdentiteitGroep(groep)) {
                final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
                final String actueelRecordNaam = String.format("actueel%s", groep.getIdentCode());
                final JavaType javaTypeVoorElement = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                    hisObjectType);
                final String variabeleNaam = bepaalHistorieSetNaamVoorGroep(hisObjectType);

                // Zorg eerst dat er voor elke groep de actuele historie instantie wordt opgehaald.
                functieBody.append(String.format("    final %s %s = get%s().getActueleRecord();%s",
                    javaTypeVoorElement.getNaam(), actueelRecordNaam, variabeleNaam,
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                functieBody.append(String.format("    if (%s != null) {%s", actueelRecordNaam,
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                // Creeer een nieuwe groep instantie en zet de A-Laag groep naar deze nieuwe instantie.
                functieBody.append(String.format("    this.%s = new %s(%s",
                    GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode()),
                    operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam(),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

                // Voeg de attributen van de groep toe aan de constructor met de waardes uit de actuele groep instantie.
                List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
                for (Attribuut attribuut : attributen) {
                    if (behoortTotJavaLogischModel(attribuut)) {
                        final boolean inOgm = GeneratieUtil.bmrJaNeeNaarBoolean(attribuut.getInOgm(),
                            GeneratieUtil.bmrJaNeeNaarBoolean(attribuut.getInLgm(), true));
                        if (inOgm) {
                            functieBody.append(String.format("        %s.get%s(),", actueelRecordNaam,
                                attribuut.getIdentCode()));
                        } else {
                            // Indien een attribuut niet tot het OGM behoort, dan gewoon op null zetten, want dan is
                            // deze ook niet afleidbaar vanuit de HIS instances en wordt deze toch niet opgeslagen.
                            functieBody.append("        null,");
                        }
                        functieBody.append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
                    }
                }
                int laatsteKommaPositie = functieBody.lastIndexOf(",");
                functieBody.delete(laatsteKommaPositie, functieBody.length());

                functieBody.append(String.format("        );%1$s",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

                // Indien er geen actueel record is, dan groep op null zetten
                functieBody.append(String.format("} else {%1$s this.%2$s = null;%1$s }%1$s",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde(),
                    GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode())));
            }
        }
        aLaagAfleidingFunctie.setBody(functieBody.toString());
        hisVolledigJavaKlasse.voegFunctieToe(aLaagAfleidingFunctie);
    }


    /**
     * Bepaalt de naam van een historische set veld voor een specifieke (operationele) groep.
     *
     * @param groep het operationele groep objecte type waarvoor de naam bepaald dient te worden.
     * @return de naam.
     */
    private String bepaalHistorieSetNaamVoorGroep(final ObjectType groep) {
        return groep.getIdentCode().replace("His_", "") + "Historie";
    }

    /**
     * Genereer de velden voor de groepen die horen bij dit object type in de meegegeven klasse en voeg deze toe aan de klasse. Voegt tevens benodigde
     * annotaties en getters en setters toe.
     *
     * @param klasse     de java klasse
     * @param objectType het object type
     * @param groepen    de groepen
     */
    private void voegVeldenToeVoorGroepen(final JavaKlasse klasse, final ObjectType objectType,
        final List<Groep> groepen)
    {
        for (Groep groep : groepen) {
            if (behoortTotJavaOperationeelModel(groep)) {
                if (isIdentiteitGroep(groep)) {
                    //Identiteit groep platslaan en annoteren.
                    voegVeldenToeVoorAttributenInIdentiteitsGroep(klasse, objectType, groep);
                } else {
                    voegVeldToeVoorGroep(klasse, objectType, groep);
                }
                if (groepKentHistorie(groep)) {
                    // Als de groep historie kent, dan ook velden voor de Persistent Set van Hibernate en de eigen
                    // Set implementatie.
                    voegVeldToeVoorPersistentHisSet(klasse, objectType, groep);
                    voegVeldToeVoorEigenHistorieSetImplementatie(klasse, groep);
                }
            }
        }
    }

    /**
     * Genereert voor een groep een veld in een java klasse.
     *
     * @param klasse     De klasse waaraan het veld wordt toegevoegd.
     * @param objectType Het object type waar de groep bij hoort.
     * @param groep      De groep waarvoor een veld wordt aangemaakt.
     */
    private void voegVeldToeVoorGroep(final JavaKlasse klasse, final ObjectType objectType, final Groep groep) {
        // Dit is dus een attribuut met als type Groep.
        final JavaType javaTypeVoorGroepVeld = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode());
        final JavaVeld groepVeld = new JavaVeld(javaTypeVoorGroepVeld, veldNaam);
        groepVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
        klasse.voegVeldToe(groepVeld);

        // Indien er geen historie is voor de groep, dan dient er een setter te komen voor deze groep.
        // Dit daar de groep niet afgeleid kan worden (er is geen historie voor de groep), maar mogelijk wel aangepast
        // dient te worden.
        if (!groepKentHistorie(groep)) {
            final JavaMutatorFunctie setter = new JavaMutatorFunctie(groepVeld);
            genereerSetterJavaDoc(setter, objectType, groep);
            klasse.voegSetterToe(setter);
        }
    }

    /**
     * Genereert een extra transient (niet persistent) veld voor de historie. Het type van de  referentie is een eigen Set interface implementatie. Dit
     * veld kent enkel een getter waarbij de eigen Set implementatie wordt geretourneerd die de PersistentSet wrapt.
     *
     * @param klasse klasse waar het veld in moet.
     * @param groep  de groep die historie kent.
     */
    private void voegVeldToeVoorEigenHistorieSetImplementatie(final JavaKlasse klasse, final Groep groep) {
        final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        if (hisObjectType != null) {

            final JavaType javaTypeVoorElement = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                hisObjectType);

            final JavaType javaInterfaceTypeVoorSet = bepaalSlimmeSetInterfaceVoorGroep(groep);
            final JavaType javaTypeVoorSetImplementatie = bepaalSlimmeSetImplementatieVoorGroep(groep);

            final String variabeleNaam =
                GeneratieUtil.lowerTheFirstCharacter(bepaalHistorieSetNaamVoorGroep(hisObjectType));
            final JavaVeld veld = new JavaVeld(new JavaType(javaInterfaceTypeVoorSet, javaTypeVoorElement),
                variabeleNaam,
                "Lijst van de historie, maar dan in een eigen Set implementatie.");

            //De lijst is transient.
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.TRANSIENT));
            klasse.voegVeldToe(veld);

            JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC,
                new JavaType(javaInterfaceTypeVoorSet, javaTypeVoorElement),
                "get" + GeneratieUtil.upperTheFirstCharacter(veld.getNaam()),
                "Historie met " + hisObjectType.getNaam());
            getter.setJavaDoc("Retourneert de historie van " + hisObjectType.getNaam() + ".");
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            klasse.voegFunctieToe(getter);

            final String persistentSetVariabeleNaam =
                JavaGeneratieUtil.toValidJavaVariableName(hisObjectType.getIdentCode()) + "Lijst";
            final StringBuilder getterBody = new StringBuilder();

            getterBody.append(String.format("if (%1$s == null) {%2$s", persistentSetVariabeleNaam,
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()))
                .append(String.format("%1$s = new %2$s<>();%4$s",
                    persistentSetVariabeleNaam,
                    JavaType.HASH_SET.getNaam(),
                    javaTypeVoorElement.getNaam(),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()))
                .append("}");

            getterBody.append(String.format("if (%1$s == null) {%2$s", veld.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()))
                .append(String.format("%1$s = new %2$s<%3$s>(%4$s);%5$s",
                    veld.getNaam(),
                    javaTypeVoorSetImplementatie.getNaam(),
                    javaTypeVoorElement.getNaam(),
                    JavaGeneratieUtil.toValidJavaVariableName(hisObjectType.getIdentCode()) + "Lijst",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            getterBody.append("}");
            getterBody.append(String.format("return %1$s;", veld.getNaam()));

            getter.setBody(getterBody.toString());

            klasse.voegExtraImportsToe(javaTypeVoorSetImplementatie, JavaType.HASH_SET);
        }
    }

    /**
     * Genereert een lijst in javaKlasse waar historie objecttypen in zitten die horen bij groep. Voegt tevens de benodigde annotaties toe. Getters en
     * Setters worden niet gegenereerd. Dit gebeurt in voegVeldToeVoorEigenHistorieSetImplementatie(). Hier wordt dus puur het persistent veld gegenereerd
     * met JPA annotaties.
     *
     * @param javaKlasse klasse waar de lijst in moet.
     * @param objectType objecttype uit BMR.
     * @param groep      groep die historie kent.
     */
    private void voegVeldToeVoorPersistentHisSet(final JavaKlasse javaKlasse, final ObjectType objectType,
        final Groep groep)
    {
        final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);

        if (hisObjectType != null) {
            final JavaType javaTypeVoorElement = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                hisObjectType);
            final JavaVeld veld = new JavaVeld(new JavaType(JavaType.SET, javaTypeVoorElement),
                JavaGeneratieUtil.toValidJavaVariableName(hisObjectType.getIdentCode()) + "Lijst");

            annoteerCollectieVeld(veld, hisObjectType, objectType, true, true, true);
            javaKlasse.voegVeldToe(veld);
        }
    }

    /**
     * Annoteer het veld van een his collectie. Geldt voor zowel inverse associaties als voor groepen.
     *
     * @param veld              het veld
     * @param inverseObjectType het (his) object type dat in de collectie zit
     * @param objectType        het object type van de klasse waar het veld in zit
     * @param eager             of het een eager annotatie moet worden of niet
     * @param jsonAnnotatie     of er een json property annotatie moet worden toegevoegd
     * @param komtUitGroep      of het his object afkomstig is van een groep
     */
    private void annoteerCollectieVeld(final JavaVeld veld,
        final ObjectType inverseObjectType, final ObjectType objectType,
        final boolean eager, final boolean jsonAnnotatie, final boolean komtUitGroep)
    {
        String fetchTypeEnumWaarde = "EAGER";
        if (!eager) {
            fetchTypeEnumWaarde = "LAZY";
        }
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ONE_TO_MANY,
            new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, fetchTypeEnumWaarde),
            new AnnotatieWaardeParameter("mappedBy", getMappedBy(inverseObjectType, objectType, komtUitGroep)),
            new AnnotatieWaardeParameter("cascade", JavaType.CASCADE_TYPE, "ALL"),
            new AnnotatieWaardeParameter("orphanRemoval", "true", true)));
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.FETCH,
            new AnnotatieWaardeParameter("value", JavaType.FETCH_MODE, "SELECT")));
        if (jsonAnnotatie) {
            voegJsonPropertyAnnotatieToe(veld);
            // De back reference/inverse associatie moet JSON managed zijn, behalve bij
            // een referentie naar een abstract type (supertype), dat wordt nog niet ondersteund.
            if (!isSupertype(inverseObjectType)) {
                voegJsonManagedreferenceAnnotatieToe(veld);
            }
        }

        // Markeer veld als sorteerbaar indien object een volgnummer bevat.
        if (bevatVolgnummer(inverseObjectType)) {
            annoteerCollectieVeldVoorSortering(veld, JavaType.VOLGNUMMER_COMPARATOR);
        }
    }

    /**
     * Bepaal de waarde van de mapped by voor de JPA annotatie. Doe dit als volgt: zoek het attribuut van het van object type dat wijst naar het naar
     * object type en neem daarvan de ident code.
     *
     * @param vanObjectType  het van object type
     * @param naarObjectType het naar object type
     * @param komtUitGroep   of het his object afkomstig is van een groep
     * @return de mapped by
     */
    private String getMappedBy(final ObjectType vanObjectType, final ObjectType naarObjectType,
        final boolean komtUitGroep)
    {
        String mappedBy = null;

        ObjectType naarType = naarObjectType;
        if (isSubtype(naarObjectType)) {
            naarType = naarObjectType.getFinaalSupertype();
        }

        // We lopen door de lijst van attributen totdat we het juiste attribuut vinden.
        for (Attribuut attribuut : this.getBmrDao().getAttributenVanObjectType(vanObjectType)) {
            final Attribuut lgmAttribuut;
            if (attribuut.getElementByLaag().getId() == BmrLaag.OPERATIONEEL.getWaardeInBmr()) {
                // Haal het equivalente logische laag attribuut op. Gebruik daarvoor de org sync id,
                // omdat die de link legt vanuit het his groep attribuut in de operationele laag.
                lgmAttribuut = this.getBmrDao().getElementVoorSyncIdVanLaag(
                    attribuut.getOrgSyncid(), BmrLaag.LOGISCH, Attribuut.class);
            } else {
                lgmAttribuut = attribuut;
            }

            // TODO: deze logica (backreference bepaling) is dubbel geimplementeerd, code mergen?
            // zie: OperationeelModelGenerator.genereerHistorieKlasseVoorGroep

            // We hebben de backreferene gevonden als het type van het attribuut het object type is
            // en het equivalente lgm attribuut niet bestaat of in de identiteit groep zit.
            // (dat laatste is nodig ivm lgm attributen met het 'back-reference type', zoals vorige/volgende persoon).
            if (attribuut.getType().getSyncid().equals(naarType.getSyncid())
                && (komtUitGroep && lgmAttribuut == null
                || !komtUitGroep && isIdentiteitGroep(attribuut.getGroep())))
            {
                mappedBy = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                break;
            }
        }
        return mappedBy;
    }

    /**
     * Genereert velden voor elk attribuut dat voorkomt in de identiteit groep. Voor elk veld wordt ook een getter toegevoegd, maar geen setter.
     *
     * @param clazz      Java klasse waarin de velden terecht komen.
     * @param objectType Object type uit het BMR waar deze groep bij hoort.
     * @param groep      De identiteit groep.
     */
    private void voegVeldenToeVoorAttributenInIdentiteitsGroep(final JavaKlasse clazz, final ObjectType objectType,
        final Groep groep)
    {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (Attribuut attribuut : attributen) {
            if (behoortTotJavaOperationeelModel(attribuut)) {
                if (isIdAttribuut(attribuut)) {
                    voegVeldToeVoorIdAttribuutInIdentiteitGroep(clazz, objectType, attribuut);
                } else {
                    voegVeldToeVoorAttribuutInIdentiteitGroep(clazz, objectType, attribuut);
                }
            }
        }
    }

    /**
     * Voegt veld toe voor een id attribuut dat voorkomt in de identiteit groep. Tevens wordt er een getter methode toegevoegd voor het veld.
     *
     * @param klasse     Java klasse waarin de velden terecht komen.
     * @param objectType Object type uit het BMR waar deze groep bij hoort.
     * @param attribuut  Het attribuut.
     */
    private void voegVeldToeVoorIdAttribuutInIdentiteitGroep(final JavaKlasse klasse,
        final ObjectType objectType, final Attribuut attribuut)
    {
        final JavaVeld idVeld = genereerJavaVeldVoorAttribuutType(attribuut);
        //Let op: Omdat boven de HisVolledig klassen een interface hangt hebben we een probleem met het LAZY loaden
        //van ManyToOne associaties, de interface heeft namelijk geen ID veld, dus de Hibernate proxy ook niet, dus zal
        //hibernate altijd EAGER loaden op het moment dat je getID aanroept.
        //De oplossing is de ID te annoteren op de getter ipv het veld, omdat de interface wél een getID()
        //functie kent, zal de proxy dat ook kennen, en kun je gebruik maken van LAZY Loading.
        //ID veld wordt dus met @Transient ge-annoteerd.

        idVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.TRANSIENT));
        idVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
        klasse.voegVeldToe(idVeld);

        final JavaAccessorFunctie getter = new JavaAccessorFunctie(idVeld);
        annoteerIdGetter(objectType, getter, false);
        genereerGetterJavaDoc(getter, objectType, attribuut);
        klasse.voegGetterToe(getter);

        //JPA verplicht de aanwezigheid van een setID() functie wanneer de Id annotatie op een getter zit.
        //We makken de setter protected.
        genereerIdVeldSetterVoorKlasse(klasse, idVeld);
    }

    /**
     * Genereert velden voor een niet id attribuut dat voorkomt in de identiteit groep. Voor elk veld wordt ook een getter toegevoegd, maar geen setter.
     *
     * @param klasse     Java klasse waarin de velden terecht komen.
     * @param objectType Object type uit het BMR waar deze groep bij hoort.
     * @param attribuut  Het attribuut.
     */
    private void voegVeldToeVoorAttribuutInIdentiteitGroep(final JavaKlasse klasse,
        final ObjectType objectType, final Attribuut attribuut)
    {
        final JavaType javaType;
        final String veldNaam;
        final boolean isDatabaseKnipAttribuut = isDatabaseKnipAttribuut(objectType, attribuut);

        if (isDatabaseKnipAttribuut) {
            javaType = bepaalJavaTypeVoorDatabaseKnipAttribuut(attribuut);
        } else if (attribuut.getType().getSoortInhoud() != null
            && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud())
        {
            final ObjectType element = getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
            javaType = hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(element);
        } else {
            javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
        }

        if (isDatabaseKnipAttribuut) {
            veldNaam = bepaalVeldnaamVoorDatabaseKnipAttribuut(attribuut);
        } else {
            veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
        }
        final JavaVeld veld = new JavaVeld(javaType, veldNaam);
        final boolean heeftJsonProperty = isJsonProperty(attribuut);
        final boolean isDiscrAttr = isDiscriminatorAttribuut(attribuut, bepaalDiscriminatorAttribuut(objectType));
        this.annoteerAttribuutVeld(objectType, attribuut, veld,
            isDiscrAttr,
            heeftJsonProperty, false);

        if (!heeftJsonProperty) {
            this.voegJsonBackreferenceAnnotatieToe(veld);
        }
        klasse.voegVeldToe(veld);

        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        genereerGetterJavaDoc(getter, objectType, attribuut);
        klasse.voegGetterToe(getter);

        // ROMEO-388: Voeg een setter voor persoon en relatie toe op de BetrokkenheidHisVolledigImpl.
        if (objectType.getId() == ID_BETROKKENHEID_LGM
            && (attribuut.getType().getId() == ID_PERSOON_LGM || attribuut.getType().getId() == ID_RELATIE_LGM))
        {
            final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
            genereerSetterJavaDoc(setter, objectType, attribuut);
            klasse.voegSetterToe(setter);
        }
    }

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.HIS_VOLLEDIG_MODEL_JAVA_GENERATOR;
    }
}
