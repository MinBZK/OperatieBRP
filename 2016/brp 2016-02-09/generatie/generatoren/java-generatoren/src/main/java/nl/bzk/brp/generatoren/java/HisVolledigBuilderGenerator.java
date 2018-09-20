/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.CategorieVerantwoording;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaGenericParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BerichtModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigModelBuilderNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Deze generator genereert builder klasses om test objecten voor his volledigs
 * mee te kunnen maken.
 */
@Component("hisVolledigBuilderJavaGenerator")
public class HisVolledigBuilderGenerator extends AbstractHisVolledigGenerator {

    private static final String HISVOLLEDIG_OBJECT_VELDNAAM = "hisVolledigImpl";
    private static final String ACTIE_VELDNAAM = "actie";
    private static final String DIENST_VELDNAAM = "dienst";
    private static final String GROEP_BERICHT_VELDNAAM = "bericht";
    private static final String MAG_GELEVERD_WORDEN_VELDNAAM = "defaultMagGeleverdWordenVoorAttributen";
    private static final String IDENT_CODE_DATUMAANVANGGELDIGHEID = "DatumAanvangGeldigheid";

    private final NaamgevingStrategie hisVolledigModelNaamgevingStrategie =
            new HisVolledigModelNaamgevingStrategie();
    private final NaamgevingStrategie hisVolledigModelBuilderNaamgevingStrategie =
            new HisVolledigModelBuilderNaamgevingStrategie();
    private final NaamgevingStrategie operationeelModelNaamgevingStrategie =
            new OperationeelModelNaamgevingStrategie();
    private final NaamgevingStrategie berichtModelNaamgevingStrategie =
            new BerichtModelNaamgevingStrategie();
    private final NaamgevingStrategie algemeneNaamgevingStrategie =
            new AlgemeneNaamgevingStrategie();
    private final NaamgevingStrategie attribuutWrapperNaamgevingStrategie =
            new AttribuutWrapperNaamgevingStrategie();

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.HIS_VOLLEDIG_BUILDER_GENERATOR;
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<JavaKlasse> klassen = new ArrayList<>();

        for (final ObjectType objectType : getHisVolledigObjectTypen()) {
            // We maken geen builder voor supertypes, aangezien je die nooit direct bouwt.
            if (!isSupertype(objectType)) {
                if (objectType.getId() == ID_PERSOON_INDICATIE) {
                    klassen.addAll(genereerPersoonIndicatieSubtypes(objectType));
                } else {
                    klassen.add(genereerBuilderKlasseVoorObjectType(objectType));
                }
            }
        }
        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(klassen, this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    /**
     * Genereer een complete builder klasse voor een object type.
     *
     * @param objectType het object type
     * @return de builder klasse
     */
    private JavaKlasse genereerBuilderKlasseVoorObjectType(final ObjectType objectType) {
        final JavaKlasse javaKlasse = genereerBuilderJavaKlasse(objectType);


        final List<Groep> hisVolledigGroepenVoorBuilder = getHisVolledigGroepenVoorBuilder(objectType);

        final boolean heeftGroepenMetHistorie = hisVolledigGroepenVoorBuilder.size() > 0;
        voegVeldenToe(javaKlasse, objectType, heeftGroepenMetHistorie);

        // Eentje met, en eentje zonder (eventuele) backreference.
        voegConstructorenToe(javaKlasse, objectType, true, heeftGroepenMetHistorie);
        if (getBackReferenceAttribuut(objectType, null) != null) {
            voegConstructorenToe(javaKlasse, objectType, false, heeftGroepenMetHistorie);
        }


        voegNieuwRecordMethodesToe(javaKlasse, hisVolledigGroepenVoorBuilder);

        voegNieuwRecordInnerKlassenToe(javaKlasse, hisVolledigGroepenVoorBuilder);

        if(isBetrokkenheid(objectType)) {
            voegBetrokkenheidMetVerantwoordingMethodeToe(javaKlasse);
            voegBetrokkenheidBuildMethodeToe(javaKlasse, objectType);
        } else {
            voegBuildMethodeToe(javaKlasse, objectType);
        }

        voegInverseAssociatieMethodesToe(javaKlasse, objectType);

        if (objectType.getId() == ID_PERSOON_LGM) {
            genereerCustomPersoonIndicatieOnderdelen(javaKlasse);
        }

        return javaKlasse;
    }

    /**
     * Genereer de onderdelen die nodig zijn voor de indicaties en voeg ze toe aan de klasse.
     *
     * @param klasse de persoon his volledig klasse
     */
    private void genereerCustomPersoonIndicatieOnderdelen(final JavaKlasse klasse) {
        final ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        final ObjectType persoonIndicatieObjectType = this.getBmrDao().getElement(ID_PERSOON_INDICATIE, ObjectType.class);
        final JavaType indicatieSuperType = hisVolledigModelNaamgevingStrategie
                .getJavaTypeVoorElement(persoonIndicatieObjectType);
        for (final Tuple tuple : soortIndicatieObjectType.getTuples()) {
            // Voeg toe methodes voor alle subtypes.
            final JavaFunctie voegToeFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, klasse.getType(),
                    "voegPersoon" + tuple.getIdentCode() + "Toe", "his volledig builder");
            voegToeFunctie.setJavaDoc("Voeg een indicatie " + tuple.getNaam() + " toe. Zet tevens de back-reference.");
            final String parameterNaam = GeneratieUtil.lowerTheFirstCharacter(tuple.getIdentCode());
            voegToeFunctie.voegParameterToe(new JavaFunctieParameter(parameterNaam,
                    maakPersoonIndicatieSubtypeJavaType(tuple, indicatieSuperType),
                    "indicatie " + tuple.getNaam()));
            final StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append(String.format("this.hisVolledigImpl.set%1$s(%2$s);" + newline(),
                    tuple.getIdentCode(), parameterNaam));
            bodyBuilder.append(String.format("ReflectionTestUtils.setField(%1$s, \"persoon\", this.hisVolledigImpl);"
                    + newline(), parameterNaam));
            bodyBuilder.append("return this;");
            voegToeFunctie.setBody(bodyBuilder.toString());
            klasse.voegFunctieToe(voegToeFunctie);
        }
    }

    /**
     * Specifieke methode voor het genereren van de klasse hierarchie voor persoon indicatie builders.
     * Hierbij wordt voor elke soort indicatie een subklasse gegenereerd.
     * De super klasse is al in het model aanwezig.
     *
     * @param persoonIndicatieObjectType het object type in het BMR van persoon indicatie
     * @return de lijst met (sub)klassen voor de persoon indicatie his volledig
     */
    private List<JavaKlasse> genereerPersoonIndicatieSubtypes(final ObjectType persoonIndicatieObjectType) {
        final List<JavaKlasse> javaKlassen = new ArrayList<>();
        final ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        final ObjectType hisIndicatieObjectType = this.getBmrDao().getElementVoorSyncIdVanLaag(
            SYNC_ID_HIS_PERSOON_INDICATIE, BmrLaag.OPERATIONEEL, ObjectType.class);
        for (final Tuple tuple : soortIndicatieObjectType.getTuples()) {
            final boolean heeftMaterieleHistorie = heeftPersoonIndicatieTupleMaterieleHistorie(tuple);

            final JavaKlasse klasse = this.genereerPersoonIndicatieSubtypeSetup(
                    persoonIndicatieObjectType, tuple, hisVolledigModelBuilderNaamgevingStrategie);
            klasse.getSuperInterfaces().clear();
            final JavaType noGenericsType = JavaType.bouwVoorFullyQualifiedClassName(
                    klasse.getType().getFullyQualifiedClassName());
            noGenericsType.getGenericParameters().clear();
            klasse.getSuperKlasse().getGenericParameters().add(new JavaGenericParameter(noGenericsType));
            final JavaType defaultIndicatieHisVolledigJavaType = hisVolledigModelNaamgevingStrategie
                    .getJavaTypeVoorElement(persoonIndicatieObjectType);
            final JavaType indicatieSubtypeHisVolledig = this.maakPersoonIndicatieSubtypeJavaType(
                    tuple, defaultIndicatieHisVolledigJavaType);

            final JavaType defaultIndicatieHisModelType = operationeelModelNaamgevingStrategie
                    .getJavaTypeVoorElement(hisIndicatieObjectType);
            final JavaType indicatieSubtypeHisModelType = this.maakPersoonIndicatieSubtypeJavaType(
                    tuple, defaultIndicatieHisModelType);

            // Constructors
            final ObjectType backreferenceType = this.getBmrDao().getElement(ID_PERSOON_LGM, ObjectType.class);
            final JavaType backreferenceJavaType = hisVolledigModelNaamgevingStrategie
                    .getJavaTypeVoorElement(backreferenceType);

            //Constructor met backreference parameter
            final Constructor constr1 = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr1.setJavaDoc("Maak een nieuwe builder aan met backreference.");
            constr1.voegParameterToe(new JavaFunctieParameter(
                    "persoon", backreferenceJavaType, "backreference"));
            constr1.setBody("super(persoon);");
            klasse.voegConstructorToe(constr1);

            //Constructor met backreference parameter EN defaultMagGeleverdWorden parameter
            final Constructor constr1Overload = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr1Overload.setJavaDoc("Maak een nieuwe builder aan met backreference.");
            constr1Overload.voegParameterToe(new JavaFunctieParameter(
                    "persoon", backreferenceJavaType, "backreference"));
            constr1Overload.voegParameterToe(new JavaFunctieParameter(
                    MAG_GELEVERD_WORDEN_VELDNAAM, JavaType.BOOLEAN_PRIMITIVE,
                    "default waarde voor magGeleverdWorden voor attributen."));
            constr1Overload.setBody("super(persoon, " + MAG_GELEVERD_WORDEN_VELDNAAM + ");");
            klasse.voegConstructorToe(constr1Overload);

            //Constructor zonder backreference parameter
            final Constructor constr2 = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr2.setJavaDoc("Maak een nieuwe builder aan zonder backreference.");
            constr2.setBody("super();");
            klasse.voegConstructorToe(constr2);

            //Constructor met alleen defaultMagGeleverdWorden parameter
            final Constructor constr2Overload = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr2Overload.setJavaDoc("Maak een nieuwe builder aan zonder backreference.");
            constr2Overload.voegParameterToe(new JavaFunctieParameter(
                    MAG_GELEVERD_WORDEN_VELDNAAM, JavaType.BOOLEAN_PRIMITIVE,
                    "default waarde voor magGeleverdWorden voor attributen."));
            constr2Overload.setBody("super(" + MAG_GELEVERD_WORDEN_VELDNAAM + ");");
            klasse.voegConstructorToe(constr2Overload);

            // Custom methodes
            final JavaType standaardRecordBuilderType = new JavaType("PersoonIndicatieHisVolledigImplBuilderStandaard",
                    klasse.getType().getPackagePad());
            final JavaFunctie nieuwRecordFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC,
                    standaardRecordBuilderType, "nieuwStandaardRecord", null);
            if (heeftMaterieleHistorie) {
                nieuwRecordFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                nieuwRecordFunctie.voegParameterToe(
                        new JavaFunctieParameter("datumAanvangGeldigheid", JavaType.INTEGER));
                nieuwRecordFunctie.voegParameterToe(
                        new JavaFunctieParameter("datumEindeGeldigheid", JavaType.INTEGER));
                nieuwRecordFunctie.setBody("return super.nieuwStandaardRecord("
                        + "datumAanvangGeldigheid, datumEindeGeldigheid, datumRegistratie);");
            } else {
                nieuwRecordFunctie.setBody("return super.nieuwStandaardRecord(null, null, datumRegistratie);");
            }
            nieuwRecordFunctie.voegParameterToe(
                    new JavaFunctieParameter("datumRegistratie", JavaType.INTEGER));
            klasse.voegFunctieToe(nieuwRecordFunctie);

            final JavaFunctie nieuwRecordFunctie2 = new JavaFunctie(JavaAccessModifier.PUBLIC,
                    standaardRecordBuilderType, "nieuwStandaardRecord", null);
            nieuwRecordFunctie2.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            nieuwRecordFunctie2.voegParameterToe(
                    new JavaFunctieParameter("actie", JavaType.ACTIE_MODEL));
            nieuwRecordFunctie2.setBody("return new PersoonIndicatieHisVolledigImplBuilderStandaard(actie);");
            klasse.voegFunctieToe(nieuwRecordFunctie2);

            final JavaFunctie maakHisVolledig = new JavaFunctie(JavaAccessModifier.PROTECTED,
                    indicatieSubtypeHisVolledig, "maakPersoonIndicatieHisVolledig", null);
            maakHisVolledig.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            maakHisVolledig.voegParameterToe(new JavaFunctieParameter("persoon", backreferenceJavaType));
            maakHisVolledig.setBody(String.format("return new %1$s(persoon);", indicatieSubtypeHisVolledig.getNaam()));
            klasse.voegFunctieToe(maakHisVolledig);

            final JavaFunctie maakHisModel = new JavaFunctie(JavaAccessModifier.PROTECTED,
                    indicatieSubtypeHisModelType, "maakHisPersoonIndicatieModel", null);
            maakHisModel.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            maakHisModel.voegParameterToe(new JavaFunctieParameter("groep",
                    JavaType.PERSOON_INDICATIE_STANDAARD_GROEP));
            maakHisModel.voegParameterToe(new JavaFunctieParameter("actie", JavaType.ACTIE_MODEL));
            maakHisModel.setBody(String.format("%1$s record = new %1$s(build(), groep, actie, actie);"
                                             + "if (record.getWaarde() != null) {"
                                             + "record.getWaarde().setMagGeleverdWorden(" + MAG_GELEVERD_WORDEN_VELDNAAM + ");"
                                             + "}"
                                             + "return record;",
                    indicatieSubtypeHisModelType.getNaam()));
            klasse.voegFunctieToe(maakHisModel);

            final JavaFunctie buildFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC,
                    indicatieSubtypeHisVolledig, "build", null);
            buildFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            buildFunctie.setBody(String.format("return (%1$s) getHisVolledigImpl();",
                    indicatieSubtypeHisVolledig.getNaam()));
            klasse.voegFunctieToe(buildFunctie);

            javaKlassen.add(klasse);
        }
        return javaKlassen;
    }

    /**
     * Genereer het java klasse object voor het betreffende object type.
     *
     * @param objectType het object type
     * @return de java klasse
     */
    private JavaKlasse genereerBuilderJavaKlasse(final ObjectType objectType) {
        return new JavaKlasse(
                this.hisVolledigModelBuilderNaamgevingStrategie.getJavaTypeVoorElement(objectType),
                "Builder klasse voor " + objectType.getNaam() + ".");
    }

    /**
     * Voeg alle velden toe die in de builder nodig zijn.
     *
     * @param javaKlasse java klasse
     * @param objectType object type
     * @param heeftGroepenMetHistorie of de builder groepen heeft met historie.
     */
    private void voegVeldenToe(final JavaKlasse javaKlasse, final ObjectType objectType, final boolean heeftGroepenMetHistorie) {
        javaKlasse.voegVeldToe(
                new JavaVeld(
                        hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
                        HISVOLLEDIG_OBJECT_VELDNAAM));
        if (heeftGroepenMetHistorie) {
            javaKlasse.voegVeldToe(new JavaVeld(JavaType.BOOLEAN_PRIMITIVE, MAG_GELEVERD_WORDEN_VELDNAAM));
        }
    }

    /**
     * Voeg alle constructors toe die in de builder nodig zijn.
     *
     * @param javaKlasse       java klasse
     * @param objectType       object type
     * @param metBackreference of de backreference in de constructor moet zitten of niet, indien aanwezig
     */
    private void voegConstructorenToe(final JavaKlasse javaKlasse, final ObjectType objectType,
                                      final boolean metBackreference, final boolean heeftGroepenMetHistorie)
    {
        final Constructor constructor = this.genereerConstructorMetIdentiteitParameters(
            javaKlasse, objectType, hisVolledigModelNaamgevingStrategie);
        boolean defaultMagGeleverdWordenParameterNodig = false;
        if (constructor != null) {
            constructor.setJavaDoc("Maak een nieuwe builder aan met de identificerende gegevens.");

            // De standaard body voldoet niet aan onze builder wensen, dus maak een nieuwe.
            final StringBuilder constructorBody = new StringBuilder();
            // Voor sommige parameters moeten we het isMagGeleverdWorden vlaggetje via extra statements setten.
            final StringBuilder zetMagGeleverdWordenStatements = new StringBuilder();
            constructorBody.append("this.").append(HISVOLLEDIG_OBJECT_VELDNAAM).append(" = new ");
            final String implKlasseNaam = hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(objectType).getNaam();
            constructorBody.append(implKlasseNaam).append("(");
            final int constructorBodyStartLengte = constructorBody.length();
            for (final JavaFunctieParameter parameter : constructor.getParameters()) {
                final JavaType parameterType = parameter.getJavaType();
                final String parameterTypeNaam = parameter.getJavaType().getNaam();

                if (parameterTypeNaam.endsWith("Attribuut")) {
                    zetMagGeleverdWordenStatements.append("if (")
                                                  .append(HISVOLLEDIG_OBJECT_VELDNAAM)
                                                  .append(".").append("get")
                                                  .append(GeneratieUtil.upperTheFirstCharacter(parameter.getNaam()))
                                                  .append("()").append(" != null) {")
                                                  .append(HISVOLLEDIG_OBJECT_VELDNAAM)
                                                  .append(".").append("get")
                                                  .append(GeneratieUtil.upperTheFirstCharacter(parameter.getNaam()))
                                                  .append("()")
                                                  .append(".setMagGeleverdWorden(")
                                                  .append(MAG_GELEVERD_WORDEN_VELDNAAM).append(");")
                                                  .append("}");
                    defaultMagGeleverdWordenParameterNodig = true;
                }
                if (constructorBody.length() > constructorBodyStartLengte) {
                    constructorBody.append(", ");
                }
                if (!metBackreference && isBackReferenceJavaType(objectType, parameter.getJavaType())) {
                    // We nemen standaard geen backreference op in de builder.
                    constructorBody.append("null");
                } else if (parameterType.getPackagePad().contains(
                                GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE.getPackage())
                        || parameterType.getPackagePad().contains(
                                GeneratiePackage.STAMGEGEVEN_DYNAMISCH_PACKAGE.getPackage()))
                {
                    // 'downgrade' de parameter types voor stamgegevens,
                    // zodat de wrapper klasses niet meegegeven hoeven worden.
                    final String nieuweParameterTypeNaam = parameterTypeNaam.substring(0, parameter.getJavaType().
                            getNaam().length() - AttribuutWrapperNaamgevingStrategie.ATTRIBUUT_SUFFIX.length());
                    parameter.setJavaType(
                            new JavaType(nieuweParameterTypeNaam, parameter.getJavaType().getPackagePad()));
                    constructorBody.append(String.format("new %1$s(%2$s)", parameterTypeNaam, parameter.getNaam()));
                    // Het 'oude' type, de wrapper, moet nu wel als extra import worden aangewezen.
                    javaKlasse.voegExtraImportsToe(parameterType);
                } else {
                    constructorBody.append(parameter.getNaam());
                }
            }
            constructorBody.append(");");

            if (defaultMagGeleverdWordenParameterNodig || heeftGroepenMetHistorie) {
                //Voeg een extra parameter toe voor het veld: defaultMagGeleverdWordenVoorAttributen
                constructor.voegParameterToe(
                    new JavaFunctieParameter(MAG_GELEVERD_WORDEN_VELDNAAM,
                        JavaType.BOOLEAN_PRIMITIVE,
                        "waarde voor het vlaggetje isMagGeleverdWorden van alle attributen."));
            }
            if (heeftGroepenMetHistorie) {
                constructorBody.append("this.").append(MAG_GELEVERD_WORDEN_VELDNAAM).append(" = ")
                    .append(MAG_GELEVERD_WORDEN_VELDNAAM).append(";");
            }
            constructorBody.append(zetMagGeleverdWordenStatements);


            constructor.setBody(constructorBody.toString());

            if (!metBackreference) {
                verwijderBackreferenceUitParameters(objectType, constructor);
            }

            javaKlasse.voegConstructorToe(constructor);
        }

        // Maak nu een overload van de constructor zonder de "defaultMagGeleverdWordenVoorAttributen" parameter.
        // Echter dit is alleen nodig als de parameter benodigd was in de vorige constructor, anders krijg je twee identieke constructoren want niet mag
        // in Java.
        if (defaultMagGeleverdWordenParameterNodig || heeftGroepenMetHistorie) {
            final Constructor constructorOverload = this.genereerConstructorMetIdentiteitParameters(
                javaKlasse, objectType, hisVolledigModelNaamgevingStrategie);
            if (constructorOverload != null) {

                final StringBuilder constructorBody = new StringBuilder();
                constructorBody.append("this(");
                final int constructorBodyStartLengte = constructorBody.length();
                for (final JavaFunctieParameter parameter : constructorOverload.getParameters()) {
                    if (constructorBody.length() > constructorBodyStartLengte) {
                        constructorBody.append(", ");
                    }
                    final JavaType parameterType = parameter.getJavaType();
                    final String parameterTypeNaam = parameter.getJavaType().getNaam();
                    if (!metBackreference && isBackReferenceJavaType(objectType, parameter.getJavaType())) {
                        // We nemen standaard geen backreference op in de builder.
                        constructorBody.append("null");
                    } else if (parameterType.getPackagePad().contains(
                        GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE.getPackage())
                        || parameterType.getPackagePad().contains(
                        GeneratiePackage.STAMGEGEVEN_DYNAMISCH_PACKAGE.getPackage()))
                    {
                        // 'downgrade' de parameter types voor stamgegevens,
                        // zodat de wrapper klasses niet meegegeven hoeven worden.
                        final String nieuweParameterTypeNaam = parameterTypeNaam.substring(0, parameter.getJavaType().
                            getNaam().length() - AttribuutWrapperNaamgevingStrategie.ATTRIBUUT_SUFFIX.length());
                        parameter.setJavaType(
                            new JavaType(nieuweParameterTypeNaam, parameter.getJavaType().getPackagePad()));
                        constructorBody.append(parameter.getNaam());
                        // Het 'oude' type, de wrapper, moet nu wel als extra import worden aangewezen.
                        javaKlasse.voegExtraImportsToe(parameterType);
                    } else {
                        constructorBody.append(parameter.getNaam());
                    }
                }
                // De magGeleverdWorden vlag is default false:
                if (constructorBody.length() > constructorBodyStartLengte) {
                    constructorBody.append(", ");
                }
                constructorBody.append("false);");
                constructorOverload.setBody(constructorBody.toString());
                if (!metBackreference) {
                    verwijderBackreferenceUitParameters(objectType, constructorOverload);
                }
                javaKlasse.voegConstructorToe(constructorOverload);
            }
        }
    }

    /**
     * Haal de (eventuele) parameter naar de backreference uit de constructor parameters,
     * die hoeven we namelijk niet op te geven.
     *
     * @param objectType  het object type
     * @param constructor de constructyor
     */
    private void verwijderBackreferenceUitParameters(final ObjectType objectType, final Constructor constructor) {
        final Iterator<JavaFunctieParameter> paramIter = constructor.getParameters().iterator();
        while (paramIter.hasNext()) {
            final JavaFunctieParameter parameter = paramIter.next();
            if (isBackReferenceJavaType(objectType, parameter.getJavaType())) {
                paramIter.remove();
            }
        }
    }

    /**
     * Checkt of het meegegeven java type het backreference java type is van het object type.
     *
     * @param objectType het object type
     * @param javaType   het java type
     * @return true indien gelijk, anders false
     */
    private boolean isBackReferenceJavaType(final ObjectType objectType, final JavaType javaType) {
        boolean isBackReferenceJavaType = false;
        final Attribuut backreferenceAttribuut = getBackReferenceAttribuut(objectType, null);
        if (backreferenceAttribuut != null) {
            final ObjectType backreferenceType = this.getBmrDao().
                    getElement(backreferenceAttribuut.getType().getId(), ObjectType.class);
            final JavaType backreferenceJavaType = hisVolledigModelNaamgevingStrategie.
                    getJavaTypeVoorElement(backreferenceType);
            if (backreferenceJavaType.equals(javaType)) {
                isBackReferenceJavaType = true;
            }
        }
        return isBackReferenceJavaType;
    }

    /**
     * Bepaalt het backreference attribuut van een object type.
     *
     * @param inverseAssociatieObjectType het object type
     * @return het backreference attribuut, of null, indien niet bestaand
     */
    private Attribuut getBackReferenceAttribuut(final ObjectType inverseAssociatieObjectType, final ObjectType vanObjectType) {
        Attribuut backReferenceAttribuut = null;
        for (final Attribuut attribuut : this.getBmrDao().getAttributenVanObjectType(inverseAssociatieObjectType)) {
            // Een backreference is een attribuut in de identiteit groep met een inverse associatie.
            if (isIdentiteitGroep(attribuut.getGroep())
                    && StringUtils.isNotBlank(attribuut.getInverseAssociatieNaam()))
            {
                if (vanObjectType == null) {
                    backReferenceAttribuut = attribuut;
                } else if (attribuut.getType().getId() == vanObjectType.getId()) {
                    // vanObjectType is niet null & moet worden gebruikt om te checken of
                    // de backreference van het juiste type is.
                    backReferenceAttribuut = attribuut;
                }
            }
        }
        return backReferenceAttribuut;
    }

    /**
     * Voeg de methode toe waarmee het gebouwde object opgehaald kan worden.
     *
     * @param javaKlasse java klasse
     * @param objectType object type
     */
    private void voegBuildMethodeToe(final JavaKlasse javaKlasse, final ObjectType objectType) {
        final JavaFunctie buildFunctie = new JavaFunctie(
                JavaAccessModifier.PUBLIC,
                hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
                "build", "het his volledig object");
        buildFunctie.setJavaDoc("Bouw het his volledig object.");
        buildFunctie.setBody("return " + HISVOLLEDIG_OBJECT_VELDNAAM + ";");
        javaKlasse.voegFunctieToe(buildFunctie);
    }

    /**
     * Voeg de methode toe waarmee het gebouwde object opgehaald kan worden.
     *
     * @param javaKlasse java klasse
     * @param objectType object type
     */
    private void voegBetrokkenheidBuildMethodeToe(final JavaKlasse javaKlasse, final ObjectType objectType) {
        final JavaFunctie buildFunctie = new JavaFunctie(
            JavaAccessModifier.PUBLIC,
            hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
            "build", "het his volledig object");
        buildFunctie.setJavaDoc("Bouw het his volledig betrokkenheid object. Relatie, persoon, verantwoording kunnen null zijn. Dit stelt de ontwikkelaar "
            + "in staat specifieke scenario's als 'ontbrekende/onbekende ouder' te ondersteunen.");
        final StringBuilder bodyBuilder = new StringBuilder();

        bodyBuilder.append("if (this.").append(HISVOLLEDIG_OBJECT_VELDNAAM).append(".getRelatie() != null) {").
            append(HISVOLLEDIG_OBJECT_VELDNAAM).append(".getRelatie").append("().getBetrokkenheden().add(").append(HISVOLLEDIG_OBJECT_VELDNAAM)
            .append(");}");
        bodyBuilder.append("if (this.").append(HISVOLLEDIG_OBJECT_VELDNAAM).append(".getPersoon() != null) {").
            append(HISVOLLEDIG_OBJECT_VELDNAAM).append(".getPersoon").append("().getBetrokkenheden().add(").append(HISVOLLEDIG_OBJECT_VELDNAAM)
            .append(");}");
        bodyBuilder.append("if (this.").append("verantwoording").append(" != null) {").
                append(HISVOLLEDIG_OBJECT_VELDNAAM).append(".getBetrokkenheidHistorie")
            .append("().voegToe(new HisBetrokkenheidModel(").append(HISVOLLEDIG_OBJECT_VELDNAAM).append(", this.verantwoording)); }");
        javaKlasse.voegExtraImportsToe(JavaType.HIS_BETROKKENHEID_MODEL);

        bodyBuilder.append("return " + HISVOLLEDIG_OBJECT_VELDNAAM + ";");
        buildFunctie.setBody(bodyBuilder.toString());
        javaKlasse.voegFunctieToe(buildFunctie);
    }

    /**
     * Voeg de methode toe waarmee verantwoording voor de betrokkenheid historie records
     * gezet wordt.
     *
     * @param javaKlasse java klasse
     */
    private void voegBetrokkenheidMetVerantwoordingMethodeToe(final JavaKlasse javaKlasse) {
        final JavaFunctie functie = new JavaFunctie(
            JavaAccessModifier.PUBLIC,
            javaKlasse.getType(), "metVerantwoording", "de betrokkenheid builder.");
        functie.setJavaDoc("Verantwoording voor de betrokkenheid historie.");
        functie.voegParameterToe(new JavaFunctieParameter("actie", JavaType.ACTIE_MODEL, "verantwoording voor de historie records "));

        javaKlasse.getVelden().add(new JavaVeld(JavaType.ACTIE_MODEL, "verantwoording"));

        final StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("this.verantwoording = actie;");
        bodyBuilder.append("return this;");
        functie.setBody(bodyBuilder.toString());
        javaKlasse.voegFunctieToe(functie);
    }

    /**
     * Voeg voor alle inverse associaties methodes toe om een element toe te voegen.
     *
     * @param javaKlasse java klasse
     * @param objectType object type
     */
    private void voegInverseAssociatieMethodesToe(final JavaKlasse javaKlasse, final ObjectType objectType) {
        final List<Attribuut> inverseAssociatieAttributen = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        filterIndicatiesUitInverseAssociaties(inverseAssociatieAttributen);
        for (final Attribuut inverseAssociatieAttribuut : inverseAssociatieAttributen) {
            if (behoortTotJavaOperationeelModel(inverseAssociatieAttribuut.getObjectType())) {
                final ObjectType inverseAssociatieObjectType = inverseAssociatieAttribuut.getObjectType();
                final JavaFunctie functie = new JavaFunctie(
                        JavaAccessModifier.PUBLIC, javaKlasse.getType(),
                        "voeg" + inverseAssociatieObjectType.getIdentCode() + "Toe", "his volledig builder");
                functie.setJavaDoc(
                        "Voeg een " + inverseAssociatieObjectType.getNaam() + " toe. "
                                + "Zet tevens de back-reference van " + inverseAssociatieObjectType.getNaam() + ".");
                final String paramNaam = GeneratieUtil.lowerTheFirstCharacter(inverseAssociatieObjectType.getIdentCode());
                functie.voegParameterToe(
                        new JavaFunctieParameter(
                                paramNaam,
                                hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(inverseAssociatieObjectType),
                                "een " + inverseAssociatieObjectType.getNaam()));

                final StringBuilder bodyBuilder = new StringBuilder();
                bodyBuilder.append("this.").append(HISVOLLEDIG_OBJECT_VELDNAAM).append(".get")
                           .append(inverseAssociatieAttribuut.getInverseAssociatieIdentCode()).append("().add(")
                           .append(paramNaam).append(");").append(newline());
                final Attribuut backreferenceAttribuut = getBackReferenceAttribuut(inverseAssociatieObjectType, objectType);
                bodyBuilder.append("ReflectionTestUtils.setField(").append(paramNaam).append(", \"")
                           .append(GeneratieUtil.lowerTheFirstCharacter(backreferenceAttribuut.getIdentCode()))
                           .append("\", this.").append(HISVOLLEDIG_OBJECT_VELDNAAM).append(");").append(newline());
                bodyBuilder.append("return this;");
                functie.setBody(bodyBuilder.toString());
                javaKlasse.voegFunctieToe(functie);
                javaKlasse.voegExtraImportsToe(JavaType.REFLECTION_TEST_UTILS);
            }
        }
    }

    /**
     * Voeg voor alle groepen een nieuw record methode toe.
     *
     * @param javaKlasse java klasse
     * @param hisVolledigGroepenVoorBuilder de groepen voor deze builder
     */
    private void voegNieuwRecordMethodesToe(final JavaKlasse javaKlasse, final List<Groep> hisVolledigGroepenVoorBuilder) {
        for (final Groep groep : hisVolledigGroepenVoorBuilder) {
            final String functieNaam = "nieuw" + groep.getIdentCode() + "Record";
            final JavaType returnType = getInnerKlasseJavaType(javaKlasse, groep);
            final JavaType verantwoordingType = getJavaTypeVoorVerantwoording(groep);
            final String verantwoordingVeldNaam = getVeldNaamVoorVerantwoording(groep);

            if (getJavaTypeVoorVerantwoording(groep).equals(JavaType.ACTIE_MODEL)) {
                final JavaFunctie functieMetData = new JavaFunctie(
                        JavaAccessModifier.PUBLIC,
                        returnType, functieNaam, groep.getNaam() + " groep builder");
                functieMetData.setJavaDoc("Start een nieuw " + groep.getNaam() + " record, aan de hand van data.");
                if (kentMaterieleHistorie(groep)) {
                    functieMetData.voegParameterToe(
                            new JavaFunctieParameter("datumAanvangGeldigheid", JavaType.INTEGER, "datum aanvang"));
                    functieMetData.voegParameterToe(
                            new JavaFunctieParameter("datumEindeGeldigheid", JavaType.INTEGER, "datum einde"));
                }
                functieMetData.voegParameterToe(
                        new JavaFunctieParameter("datumRegistratie", JavaType.INTEGER, "datum registratie (geen tijd)"));

                final StringBuilder bodyBuilder = new StringBuilder();
                bodyBuilder.append("final ActieBericht actieBericht = new ActieBericht("
                        + "new SoortActieAttribuut(SoortActie.DUMMY)) {};").append(newline());
                if (kentMaterieleHistorie(groep)) {
                    final JavaType datumType = getJavaTypeVoorHistorieDatumAttributen(groep);
                    javaKlasse.voegExtraImportsToe(datumType);

                    bodyBuilder.append("if (datumAanvangGeldigheid != null) {");
                    bodyBuilder.append("actieBericht.setDatumAanvangGeldigheid("
                            + "new " + datumType.getNaam() + "(datumAanvangGeldigheid));")
                            .append(newline());
                    bodyBuilder.append("}");
                    bodyBuilder.append("if (datumEindeGeldigheid != null) {");
                    bodyBuilder.append("actieBericht.setDatumEindeGeldigheid(new " + datumType.getNaam() + "(datumEindeGeldigheid));")
                            .append(newline());
                    bodyBuilder.append("}");
                }

                bodyBuilder.append("if (datumRegistratie != null) {");
                bodyBuilder.append(
                        "actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut("
                                + "new DatumAttribuut(datumRegistratie).toDate()));").append(newline());
                bodyBuilder.append("}");
                bodyBuilder.append("return ").append(functieNaam).append("(new ActieModel(actieBericht, null));");
                functieMetData.setBody(bodyBuilder.toString());
                javaKlasse.voegExtraImportsToe(
                        JavaType.ACTIE_BERICHT, JavaType.ACTIE_MODEL, JavaType.SOORT_ACTIE, JavaType.SOORT_ACTIE_ATTRIBUUT,
                        JavaType.DATUM_ATTRIBUUT, JavaType.DATUMTIJD_ATTRIBUUT);
                javaKlasse.voegFunctieToe(functieMetData);
            }

            final JavaFunctie functieMetVerantwoording = new JavaFunctie(
                    JavaAccessModifier.PUBLIC,
                    returnType, functieNaam, groep.getNaam() + " groep builder");
            functieMetVerantwoording.setJavaDoc("Start een nieuw " + groep.getNaam() + " record, aan de hand van een actie.");
            functieMetVerantwoording.voegParameterToe(new JavaFunctieParameter(verantwoordingVeldNaam, verantwoordingType, verantwoordingVeldNaam));
            functieMetVerantwoording.setBody("return new " + returnType.getNaam() + "(" +verantwoordingVeldNaam+ ");");
            javaKlasse.voegFunctieToe(functieMetVerantwoording);
        }
    }

    private JavaType getJavaTypeVoorHistorieDatumAttributen(final Groep groep) {
        final ObjectType hisObjectType =
                getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        for (final Attribuut attribuut : getBmrDao().getAttributenVanObjectType(hisObjectType)) {
            if (IDENT_CODE_DATUMAANVANGGELDIGHEID.equals(attribuut.getIdentCode())) {
                return attribuutWrapperNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
            }
        }
        throw new IllegalArgumentException("Kan JavaType voor DatumAttributen niet bepalen");
    }

    /**
     * Bouw het inner klasse java type op uit de klasse en de groep.
     *
     * @param javaKlasse klasse
     * @param groep      groep
     * @return java type voor inner klasse
     */
    private JavaType getInnerKlasseJavaType(final JavaKlasse javaKlasse, final Groep groep) {
        return new JavaType(javaKlasse.getNaam() + groep.getIdentCode(), javaKlasse.getPackagePad());
    }

    /**
     * Voeg voor alle groepen een inner klasse builder toe.
     *
     * @param javaKlasse java klasse
     * @param hisVolledigGroepenVoorBuilder de groepen voor deze builder
     */
    private void voegNieuwRecordInnerKlassenToe(final JavaKlasse javaKlasse, final List<Groep> hisVolledigGroepenVoorBuilder)
    {
        for (final Groep groep : hisVolledigGroepenVoorBuilder) {
            final boolean groepKentMinstensEenAttribuut = groepKentMinstensEenAttribuut(groep);
            final JavaKlasse innerKlasse = new JavaKlasse(
                    getInnerKlasseJavaType(javaKlasse, groep),
                    "Inner klasse builder voor de groep " + groep.getNaam());
            voegInnerKlasseVeldenToe(innerKlasse, groep);
            voegInnerKlasseConstructorsToe(innerKlasse, groep);
            voegInnerKlasseAttribuutMethodesToe(innerKlasse, groep);
            voegInnerKlasseEindeRecordMethodeToe(javaKlasse, innerKlasse, groep, groepKentMinstensEenAttribuut);
            if (groepKentMinstensEenAttribuut) {
                voegZetMagGeleverdWordenVlaggetjesFunctieToe(innerKlasse, groep);
            }

            javaKlasse.voegInnerKlasseToe(innerKlasse);
        }
    }

    /**
     * Controlleert of een groep minstens één attribuut kent, dit is het geval als er een attribuut is wat niet een dynamisch objecttype attribuut is.
     * @param groep de te controleren groep
     * @return true indien er minstens één attribuut is dat een stamgegeven of attribuuttype is, anders false.
     */
    private boolean groepKentMinstensEenAttribuut(final Groep groep) {
        final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
        boolean resultaat = false;
        for (final Attribuut attribuut : attributenVanGroep) {
            if (!isDynamischObjecttypeAttribuut(attribuut)) {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

    /**
     * Voeg voor de inner klasse alle velden toe.
     *
     * @param innerKlasse inner klasse
     * @param groep       groep
     */
    private void voegInnerKlasseVeldenToe(final JavaKlasse innerKlasse, final Groep groep) {
        final JavaType verantwoordingType = getJavaTypeVoorVerantwoording(groep);
        final String verantwoordingVeldNaam = getVeldNaamVoorVerantwoording(groep);

        innerKlasse.voegVeldToe(new JavaVeld(verantwoordingType, verantwoordingVeldNaam));
        innerKlasse.voegVeldToe(
                new JavaVeld(
                        berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep),
                        GROEP_BERICHT_VELDNAAM));
    }

    private JavaType getJavaTypeVoorVerantwoording(final Groep groep) {
        if (CategorieVerantwoording.ACTIE.getCode() == groep.getVerantwoordingCategorie()) {
            return JavaType.ACTIE_MODEL;
        } else if ('G' == groep.getVerantwoordingCategorie()) {
            // TODO dit is tijdelijke oplossing ivm BMR-42 wijzigingen in POC Bijhouding
            return JavaType.ACTIE_MODEL;
        } else if (CategorieVerantwoording.DIENST.getCode() == groep.getVerantwoordingCategorie()) {
            return JavaType.DIENST;
        } else {
            throw new IllegalArgumentException("Onbekende verantwoordingscategorie voor groep " + groep.getNaam());
        }
    }

    private String getVeldNaamVoorVerantwoording(final Groep groep) {
        if (CategorieVerantwoording.ACTIE.getCode() == groep.getVerantwoordingCategorie()) {
            return ACTIE_VELDNAAM;
        } else if ('G' == groep.getVerantwoordingCategorie()) {
            // TODO dit is tijdelijke oplossing ivm BMR-42 wijzigingen in POC Bijhouding
            return ACTIE_VELDNAAM;
        } else if (CategorieVerantwoording.DIENST.getCode() == groep.getVerantwoordingCategorie()) {
            return DIENST_VELDNAAM;
        } else {
            throw new IllegalArgumentException("Onbekende verantwoordingscategorie voor groep " + groep.getNaam());
        }
    }

    /**
     * Voeg voor de inner klasse alle constructors toe.
     *
     * @param innerKlasse inner klasse
     * @param groep       groep
     */
    private void voegInnerKlasseConstructorsToe(final JavaKlasse innerKlasse, final Groep groep) {
        final JavaType verantwoordingType = getJavaTypeVoorVerantwoording(groep);
        final String verantwoordingVeldNaam = getVeldNaamVoorVerantwoording(groep);

        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, innerKlasse);
        constructor.setJavaDoc("Constructor met actie.");
        constructor.voegParameterToe(new JavaFunctieParameter(verantwoordingVeldNaam, verantwoordingType, verantwoordingVeldNaam));
        final StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("this.").append(verantwoordingVeldNaam).append(" = ")
                .append(verantwoordingVeldNaam).append(";").append(newline());
        bodyBuilder.append("this.").append(GROEP_BERICHT_VELDNAAM).append(" = new ")
                .append(berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam()).append("();");
        constructor.setBody(bodyBuilder.toString());
        innerKlasse.voegConstructorToe(constructor);
    }

    /**
     * Voeg voor de inner klasse alle attribuut methodes toe.
     *
     * @param innerKlasse inner klasse
     * @param groep       groep
     */
    private void voegInnerKlasseAttribuutMethodesToe(final JavaKlasse innerKlasse, final Groep groep) {
        for (final Attribuut attribuut : this.getBmrDao().getAttributenVanGroep(groep)) {
            if (!isIdAttribuut(attribuut) && behoortTotJavaOperationeelModel(attribuut)) {
                if (isStatischStamgegevenAttribuut(attribuut)) {
                    this.voegZelfdeTypeAttribuutMethodeToe(innerKlasse, attribuut, algemeneNaamgevingStrategie);
                } else if (isDynamischStamgegevenAttribuut(attribuut)) {
                    final ObjectType objectType = this.getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                    final Attribuut logischeIdentiteitAttribuut =
                            bepaalLogischeIdentiteitVoorStamgegeven(objectType);
                    final AttribuutType attribuutType = this.getBmrDao().getElement(
                            logischeIdentiteitAttribuut.getType().getId(), AttribuutType.class);
                    JavaType paramType = getJavaTypeVoorAttribuutType(attribuutType);
                    final String paramNaam = GeneratieUtil.lowerTheFirstCharacter(logischeIdentiteitAttribuut.getIdentCode());
                    final JavaType logischeIdentiteitAttribuutJavaType = attribuutWrapperNaamgevingStrategie.
                            getJavaTypeVoorElement(logischeIdentiteitAttribuut.getType());
                    final JavaType attribuutJavaType =
                            algemeneNaamgevingStrategie.getJavaTypeVoorElement(objectType);
                    StringBuilder builder = new StringBuilder();
                    final JavaType wrapperType = attribuutWrapperNaamgevingStrategie.
                            getJavaTypeVoorElement(objectType);
                    builder.append("new ").append(wrapperType.getNaam()).append("(")
                            .append("StamgegevenBuilder.bouwDynamischStamgegeven(")
                            .append(attribuutJavaType.getNaam()).append(".class, new ")
                            .append(logischeIdentiteitAttribuutJavaType.getNaam()).append("(")
                            .append(paramNaam).append(")))");
                    innerKlasse.voegExtraImportsToe(
                            JavaType.STAMGEGEVEN_BUILDER,
                            logischeIdentiteitAttribuutJavaType, attribuutJavaType, wrapperType);
                    this.voegAttribuutMethodeToe(innerKlasse, attribuut, paramNaam, paramType, builder.toString());

                    // Voeg ook een methode toe voor het attribuut type.
                    paramType = logischeIdentiteitAttribuutJavaType;
                    builder = new StringBuilder();
                    builder.append("new ").append(wrapperType.getNaam()).append("(")
                            .append("StamgegevenBuilder.bouwDynamischStamgegeven(")
                            .append(attribuutJavaType.getNaam()).append(".class, ").append(paramNaam).append("))");
                    this.voegAttribuutMethodeToe(innerKlasse, attribuut, paramNaam, paramType, builder.toString());

                    // Voeg ook een methode toe voor direct stamgegevens toevoegen.
                    this.voegZelfdeTypeAttribuutMethodeToe(innerKlasse, attribuut, algemeneNaamgevingStrategie);
                } else if (isDynamischObjecttypeAttribuut(attribuut)) {
                    voegZelfdeTypeAttribuutMethodeToe(innerKlasse, attribuut, berichtModelNaamgevingStrategie);
                } else if (isAttribuutTypeAttribuut(attribuut)) {
                    final List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                            attribuut.getType(), false, true);
                    if (waardes.isEmpty()) {
                        // Het is in dit geval een 'gewoon' attribuut type.
                        final String paramNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                        final JavaType paramType = getJavaTypeVoorAttribuutType(
                                this.getBmrDao().getElement(
                                        attribuut.getType().getId(), AttribuutType.class));
                        final JavaType wrapperType = attribuutWrapperNaamgevingStrategie.
                                getJavaTypeVoorElement(attribuut.getType());
                        final StringBuilder builder = new StringBuilder();
                        builder.append("new ").append(wrapperType.getNaam()).append("(").append(paramNaam).append(")");
                        innerKlasse.voegExtraImportsToe(wrapperType);
                        this.voegAttribuutMethodeToe(innerKlasse, attribuut, paramNaam, paramType, builder.toString());

                        // Voeg ook een methode toe voor direct attribuut types toevoegen.
                        this.voegZelfdeTypeAttribuutMethodeToe(innerKlasse, attribuut,
                                attribuutWrapperNaamgevingStrategie);
                    } else {
                        // Het attribuut type is in dit geval een enum.
                        this.voegZelfdeTypeAttribuutMethodeToe(innerKlasse, attribuut, algemeneNaamgevingStrategie);
                    }
                }
            }
        }
    }

    /**
     * Voeg een attribuut methode toe met 'gewoon' het type als parameter.
     *
     * @param javaKlasse          de java klasse
     * @param attribuut           het attribuut
     * @param naamgevingStrategie de naamgeving strategie voor het type
     */
    private void voegZelfdeTypeAttribuutMethodeToe(final JavaKlasse javaKlasse, final Attribuut attribuut,
                                                   final NaamgevingStrategie naamgevingStrategie)
    {
        final String paramNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
        final JavaType paramType = naamgevingStrategie.getJavaTypeVoorElement(getBmrDao().getElement(
                attribuut.getType().getId(), GeneriekElement.class));
        final String setterInnerExpressie;
        if (isDynamischObjecttypeAttribuut(attribuut)
                || isAttribuutTypeAttribuut(attribuut) && !isEnumeratieAttribuut(attribuut))
        {
            // Object types hebben geen wrapper en (niet enum) attribuut types 'zijn' al een wrapper.
            setterInnerExpressie = paramNaam;
        } else {
            // Zet de attribuut wrapper er nog omheen.
            final JavaType wrapperType = attribuutWrapperNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
            setterInnerExpressie = String.format("new %1$s(%2$s)",
                    wrapperType.getNaam(), paramNaam);
            javaKlasse.voegExtraImportsToe(wrapperType);
        }
        this.voegAttribuutMethodeToe(javaKlasse, attribuut, paramNaam, paramType, setterInnerExpressie);
    }

    /**
     * Voeg een attribuut methode toe aan de klasse.
     *
     * @param javaKlasse           de klasse
     * @param attribuut            het attribuut
     * @param paramNaam            de param naam
     * @param paramType            het param type
     * @param setterInnerExpressie de expressie die in de set(...) moet komen
     */
    private void voegAttribuutMethodeToe(final JavaKlasse javaKlasse, final Attribuut attribuut,
                                         final String paramNaam, final JavaType paramType,
                                         final String setterInnerExpressie)
    {
        final String functieNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
        final JavaFunctie functie = new JavaFunctie(
                JavaAccessModifier.PUBLIC, javaKlasse.getType(),
                functieNaam, "de groep builder");
        functie.setJavaDoc("Geef attribuut " + attribuut.getNaam() + " een waarde.");

        final StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("this.").append(GROEP_BERICHT_VELDNAAM).append(".set")
                .append(attribuut.getIdentCode()).append("(");

        bodyBuilder.append(setterInnerExpressie);

        bodyBuilder.append(");").append(newline());
        bodyBuilder.append("return this;");
        functie.setBody(bodyBuilder.toString());
        functie.voegParameterToe(
                new JavaFunctieParameter(
                        paramNaam, paramType,
                        attribuut.getNaam() + " van " + attribuut.getGroep().getNaam()));

        javaKlasse.voegFunctieToe(functie);
    }

    /**
     * Voeg voor de inner klasse een einde record methode toe.
     *
     * @param javaKlasse  java klasse
     * @param innerKlasse inner klasse
     * @param groep       groep
     * @param groepKentMinstensEenAttribuut of de groep minstens één attribuut kent waarvan het magGeleverdWorden vlag gezet moet worden
     */
    private void voegInnerKlasseEindeRecordMethodeToe(final JavaKlasse javaKlasse, final JavaKlasse innerKlasse, final Groep groep,
        final boolean groepKentMinstensEenAttribuut)
    {
        final JavaFunctie eindeRecordFunctie = new JavaFunctie(
                JavaAccessModifier.PUBLIC,
                javaKlasse.getType(), "eindeRecord", "his volledig builder");
        eindeRecordFunctie.setJavaDoc("Beeindig het record.");

        //Overload met Id parameter:
        final JavaFunctie eindeRecordFunctieMetIdParam = new JavaFunctie(
                JavaAccessModifier.PUBLIC,
                javaKlasse.getType(), "eindeRecord", "his volledig builder");
        eindeRecordFunctieMetIdParam.setJavaDoc("Beeindig het record.");

        final ObjectType operationeelModelObjectTypeVoorGroep =
                getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        final JavaType javaTypeVoorIdVeld = getJavaTypeVoorIdVeld(operationeelModelObjectTypeVoorGroep);
        eindeRecordFunctieMetIdParam.voegParameterToe(new JavaFunctieParameter("id", javaTypeVoorIdVeld,
                                                                               "id van het historie record"));

        final StringBuilder bodyBuilder = new StringBuilder();
        final ObjectType hisGroep = this.getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        final JavaType hisJavaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(hisGroep);
        String groepsAanduiding = groep.getObjectType().getIdentCode();
        if (!isStandaardGroep(groep)) {
            groepsAanduiding += groep.getIdentCode();
        }

        //Record instantiatie:
        // TODO dit is tijdelijke oplossing ivm BMR-42 wijzigingen in POC Bijhouding
        if (CategorieVerantwoording.ACTIE.getCode() == groep.getVerantwoordingCategorie() || 'G' == groep.getVerantwoordingCategorie()) {
            bodyBuilder.append("final ").append(hisJavaType.getNaam()).append(" record = new ").append(hisJavaType.getNaam())
                       .append("(")
                       .append(HISVOLLEDIG_OBJECT_VELDNAAM)
                       .append(", ").append(GROEP_BERICHT_VELDNAAM);

            if (kentMaterieleHistorie(groep)) {
                bodyBuilder.append(", ").append(ACTIE_VELDNAAM);
            }

            bodyBuilder.append(", ").append(ACTIE_VELDNAAM)
                       .append(");");
        } else if (CategorieVerantwoording.DIENST.getCode() == groep.getVerantwoordingCategorie()) {
            javaKlasse.voegExtraImportsToe(JavaType.DATUMTIJD_ATTRIBUUT);
            bodyBuilder.append(hisJavaType.getNaam()).append(" record = new ").append(hisJavaType.getNaam())
                       .append("(")
                       .append(HISVOLLEDIG_OBJECT_VELDNAAM)
                       .append(", ").append(GROEP_BERICHT_VELDNAAM)
                       .append(", ").append(DIENST_VELDNAAM)
                       .append(", ").append("DatumTijdAttribuut.nu()")
                       .append(");");
        } else {
            throw new IllegalArgumentException("Onbekende verantwoordingscategorie voor groep " + groep.getNaam());
        }

        if (groepKentMinstensEenAttribuut) {
            // Zet de magGeleverdWorden vlaggetjes op alle attributen.
            bodyBuilder.append("zetMagGeleverdWordenVlaggetjes(record);");
        }

        //Voegtoe functie aanroep:
        bodyBuilder.append(HISVOLLEDIG_OBJECT_VELDNAAM).append(".get").append(groepsAanduiding).append("Historie")
                .append("().voegToe(record);");

        eindeRecordFunctie.setBody(bodyBuilder.toString());
        eindeRecordFunctieMetIdParam.setBody(bodyBuilder.toString());

        eindeRecordFunctie.setBody(eindeRecordFunctie.getBody() + "return " + javaKlasse.getNaam() + ".this;");

        eindeRecordFunctieMetIdParam.setBody(eindeRecordFunctieMetIdParam.getBody()
                                                     + "ReflectionTestUtils.setField(record, \"iD\", id);"
                                                     + "return " + javaKlasse.getNaam() + ".this;");

        innerKlasse.voegFunctieToe(eindeRecordFunctie);
        innerKlasse.voegFunctieToe(eindeRecordFunctieMetIdParam);
        innerKlasse.voegExtraImportsToe(hisJavaType);
        innerKlasse.voegExtraImportsToe(JavaType.REFLECTION_TEST_UTILS);
    }

    /**
     * private functie om de isMagGeleverdWorden vlaggetjes te zetten naar de default waarde waarmee de builder is
     * geinstantieerd.
     * @param innerKlasse innerklasse
     * @param groep de groep.
     */
    private void voegZetMagGeleverdWordenVlaggetjesFunctieToe(final JavaKlasse innerKlasse,
                                                              final Groep groep)
    {
        final JavaFunctie zetMagGeleverdWordenFunctie = new JavaFunctie(JavaAccessModifier.PRIVATE,
                                                                  "zetMagGeleverdWordenVlaggetjes");
        zetMagGeleverdWordenFunctie.setJavaDoc("Zet van alle attributen de isMagGeleverdWorden vlag naar de default "
                                                       + "waarde waarmee deze ImplBuilder is geinstantieerd.");
        final ObjectType hisGroep = this.getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        final JavaType hisJavaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(hisGroep);
        zetMagGeleverdWordenFunctie.voegParameterToe(new JavaFunctieParameter("record", hisJavaType, "Het his record"));
        final StringBuilder bodyBuilder = new StringBuilder();
        for (final Attribuut attribuut : getBmrDao().getAttributenVanGroep(groep)) {
            if (!isDynamischObjecttypeAttribuut(attribuut)) {
                bodyBuilder.append("if (record.get").append(attribuut.getIdentCode()).append("() != null) {");
                bodyBuilder.append("record.get").append(attribuut.getIdentCode()).append("().setMagGeleverdWorden(")
                           .append(MAG_GELEVERD_WORDEN_VELDNAAM).append(");");
                bodyBuilder.append("}");
            }
        }
        zetMagGeleverdWordenFunctie.setBody(bodyBuilder.toString());
        innerKlasse.voegFunctieToe(zetMagGeleverdWordenFunctie);
    }

    /**
     * Haal alle groepen uit het object type op die van toepassing zijn voor de builder.
     *
     * @param objectType het object type
     * @return de his volledig groepen
     */
    protected List<Groep> getHisVolledigGroepenVoorBuilder(final ObjectType objectType) {
        final List<Groep> hisVolledigGroepen = getHisVolledigGroepen(objectType);
        final List<Groep> hisVolledigGroepenVoorBuilder = new ArrayList<>();
        for (final Groep groep : hisVolledigGroepen) {
            // Voor een builder moet een groep historie hebben en tot het bericht model behoren
            // (anders is er geen bericht klasse beschikbaar als data 'vehikel').
            if (groepKentHistorie(groep)
                    && behoortTotJavaBerichtModel(objectType) && behoortTotJavaBerichtModel(groep))
            {
                hisVolledigGroepenVoorBuilder.add(groep);
            }
        }
        // Voeg ook alle groepen van het super type, indien van toepassing.
        if (objectType.getSuperType() != null) {
            hisVolledigGroepenVoorBuilder.addAll(getHisVolledigGroepenVoorBuilder(objectType.getSuperType()));
        }
        return hisVolledigGroepenVoorBuilder;
    }
}
