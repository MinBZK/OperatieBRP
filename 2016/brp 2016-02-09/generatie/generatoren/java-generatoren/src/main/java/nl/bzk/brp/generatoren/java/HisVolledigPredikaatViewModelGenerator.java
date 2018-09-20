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
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaMutatorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigInterfaceModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigPredikaatViewModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.springframework.stereotype.Component;

/**
 * Generator voor views klassen op his volledig objecten. Een view kan naar eigen behoefte gevormd worden door het gebruik van een predikaat. Deze
 * generator maakt alle algemene view definities voor alle his volledig klassen.
 */
@Component("hisVolledigPredikaatViewModelGenerator")
public class HisVolledigPredikaatViewModelGenerator extends AbstractHisVolledigGenerator {

    private static final String PREDIKAAT_VELDNAAM                          = "predikaat";
    private static final String PREDIKAAT_GETTERNAAM                        = "getPredikaat";
    private static final String PEILMOMENT_ALTIJD_TONEN_GROEPEN_VELDNAAM    = "peilmomentVoorAltijdTonenGroepen";
    private static final String PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM = "getPeilmomentVoorAltijdTonenGroepen";

    private final NaamgevingStrategie hisVolledigInterfaceModelNaamgevingStrategie     =
        new HisVolledigInterfaceModelNaamgevingStrategie();
    private final NaamgevingStrategie hisVolledigPredikaatViewModelNaamgevingStrategie =
        new HisVolledigPredikaatViewModelNaamgevingStrategie();
    private final NaamgevingStrategie operationeelModelNaamgevingStrategie             =
        new OperationeelModelNaamgevingStrategie();

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.HIS_VOLLEDIG_PREDIKAAT_VIEW_MODEL_GENERATOR;
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<JavaKlasse> klassen = new ArrayList<>();
        final List<ObjectType> objectTypen = getHisVolledigObjectTypen();

        for (ObjectType objectType : objectTypen) {
            if (objectType.getId() == ID_PERSOON_INDICATIE) {
                // Specifieke uitzondering voor indicaties ivm subtypering.
                klassen.addAll(genereerPersoonIndicatieSubtypes(objectType));
            } else {
                klassen.add(genereerPredikaatViewKlasseVoorObjectType(objectType));
            }
        }

        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
        final List<JavaKlasse> gegenereerdeInterfaces = writer.genereerEnSchrijfWeg(klassen, this);

        rapporteerOverGegenereerdeJavaTypen(gegenereerdeInterfaces);
    }

    /**
     * Genereert een predikaat view klasse voor een objecttype.
     *
     * @param objectType het objecttype uit het BMR.
     * @return predikaat view klasse voor dit objecttype.
     */
    private JavaKlasse genereerPredikaatViewKlasseVoorObjectType(final ObjectType objectType) {
        final JavaKlasse klasse = new JavaKlasse(
            hisVolledigPredikaatViewModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
            "Predikaat view voor " + objectType.getNaam() + ".");

        final boolean isSubType = isSubtype(objectType);

        //Haal de inverse associatie attributen alvast op.
        final List<Attribuut> inverseAttributenVoorObjectType =
            getBmrDao().getInverseAttributenVoorObjectType(objectType);

        //De super interface:
        klasse.voegSuperInterfaceToe(hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(objectType));

        if (isSubType) {
            // Een suptype predikaat view erft van zijn supertype predikaat view.
            klasse.setExtendsFrom(
                hisVolledigPredikaatViewModelNaamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        } else {
            // Alle niet subtype predikaat views erven van de abstracte super klasse.
            klasse.setExtendsFrom(JavaType.ABSTRACT_HIS_VOLLEDIG_PREDIKAAT_VIEW);
        }

        if (isSupertype(objectType)) {
            klasse.setAbstractClass(true);
        }

        String proxyObjectVeldNaam = GeneratieUtil.lowerTheFirstCharacter(objectType.getIdentCode());
        if (isSubType) {
            // Bij een subtype is de veldnaam van het proxy object gelijk aan het finale supertype.
            proxyObjectVeldNaam = GeneratieUtil.lowerTheFirstCharacter(objectType.getFinaalSupertype().getIdentCode());
        }

        // Als dit geen subtype is, oftewel een finaal supertype of een niet hierarchisch type,
        // dan voegen we enkele velden toe die nodig zijn voor de predikaat view.
        if (!isSubType) {
            // Neem een veld op voor het predikaat.
            //voegVeldGetterEnSetterToe(klasse, PREDIKAAT_VELDNAAM, JavaType.PREDIKAAT_APACHE);
            // En neem een veld op voor het peilmoment voor altijd tonen groepen.
            voegVeldGetterEnSetterToe(klasse, PEILMOMENT_ALTIJD_TONEN_GROEPEN_VELDNAAM, JavaType.DATUMTIJD_ATTRIBUUT);

            // Neem een veld op voor het ge-proxie-ied object. Het veld is van het type his volledig interface:
            final JavaVeld proxyObjectVeld = new JavaVeld(
                hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(objectType),
                proxyObjectVeldNaam, true);

            // Subtypen moeten het proxy-object veld kunnen benaderen:
            proxyObjectVeld.setAccessModifier(JavaAccessModifier.PROTECTED);
            klasse.voegVeldToe(proxyObjectVeld);
        }

        genereerConstructoren(objectType, klasse, isSubType, proxyObjectVeldNaam);

        //Haal groepen op:
        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);

        //Genereer interface implementaties voor attributen identiteit groep.
        genereerInterfaceImplementatiesVoorIdentiteitGroepAttributen(klasse, groepen, proxyObjectVeldNaam);

        //Genereer interface implementaties voor groepen.
        for (Groep groep : groepen) {
            if (groepKentHistorie(groep)) {
                genereerInterfaceImplementatieVoorHistorieGroep(objectType, klasse,
                    groep, proxyObjectVeldNaam, isSubType);
            }
        }


        //Genereer heeftXXX functies voor inverse associaties. Bijvoorbeeld heeftAdressen() en heeftNationaliteiten()
        //Deze functies zijn nodig voor een correcte werking van de JiBX binding. Ze worden gebruikt als test-method, om
        //te bepalen of container tags ge-marshalled moeten worden.
        genereerTestMethodFunctiesVoorInverseAssociaties(klasse, inverseAttributenVoorObjectType);
        genereerTestMethodFunctiesVoorInverseAssociatiesVoorLeveren(klasse, inverseAttributenVoorObjectType);

        // Filter nu de indicaties eruit, omdat die 'getter' niet gewenst is volgens dit patroon.
        filterIndicatiesUitInverseAssociaties(inverseAttributenVoorObjectType);
        //Genereer interface implementaties voor inverse associaties. (1 - N associaties.)
        genereerInterfaceImplementatiesVoorInverseAssociatieAttributen(klasse, proxyObjectVeldNaam,
            inverseAttributenVoorObjectType);

        //Genereer een functie die alle historie records van alle groepen retourneert
        genereerFunctieDieAlleHisRecordsRetourneert(klasse, groepen, isSubType);

        if (objectType.getId() == ID_PERSOON_LGM) {
            genereerGettersVoorIndicaties(klasse);
        }

        // Voeg de 'Element getElement()' functie toe, alleen voor objecttypen van het KERN schema.
        if (objectType.getSchema().getId() == ID_KERN_SCHEMA) {
            voegGetElementFunctieToe(klasse, objectType);
        }

        return klasse;
    }

    /**
     * Genereert voor elke soort indicatie een getter.
     *
     * @param klasse de persoon his volledig klasse
     */
    private void genereerGettersVoorIndicaties(final JavaKlasse klasse) {
        ObjectType soortIndicatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        ObjectType persoonIndicatieObjectType = this.getBmrDao().getElement(ID_PERSOON_INDICATIE, ObjectType.class);
        JavaType interfaceSuperType = hisVolledigInterfaceModelNaamgevingStrategie
            .getJavaTypeVoorElement(persoonIndicatieObjectType);
        JavaType predikaatViewSuperType = hisVolledigPredikaatViewModelNaamgevingStrategie
            .getJavaTypeVoorElement(persoonIndicatieObjectType);
        String lijstVanIndicatieGetters = "";
        for (Tuple tuple : soortIndicatieObjectType.getTuples()) {
            // Getters voor alle velden.
            JavaType interfaceJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, interfaceSuperType);
            JavaType predikaatViewJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, predikaatViewSuperType);
            JavaFunctie getter = new JavaFunctie(JavaAccessModifier.PUBLIC,
                interfaceJavaType, "get" + tuple.getIdentCode(), null);
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            StringBuilder bodyBuilder = new StringBuilder();
            bodyBuilder.append(String.format("final %1$s view;" + newline(), predikaatViewJavaType.getNaam()));
            bodyBuilder.append(String.format("if (persoon.get%1$s() != null) {" + newline(), tuple.getIdentCode()));
            bodyBuilder.append(String.format("view = new %1$s(persoon.get%2$s(), %3$s(), %4$s());%5$s",
                predikaatViewJavaType.getNaam(), tuple.getIdentCode(), PREDIKAAT_GETTERNAAM, PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM, newline()));
            bodyBuilder.append("} else {").append(newline());
            bodyBuilder.append("view = null;").append(newline());
            bodyBuilder.append("}").append(newline());
            bodyBuilder.append("return view;");
            getter.setFinal(true);
            getter.setBody(bodyBuilder.toString());

            klasse.voegFunctieToe(getter);
            klasse.voegExtraImportsToe(predikaatViewJavaType);

            if (lijstVanIndicatieGetters.length() > 0) {
                lijstVanIndicatieGetters += ", ";
            }
            lijstVanIndicatieGetters += getter.getNaam() + "()";
        }

        voegCustomGetIndicatiesToe(klasse, interfaceSuperType, lijstVanIndicatieGetters);
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

            JavaKlasse klasse = genereerPersoonIndicatieSubtypeSetup(
                persoonIndicatieObjectType, tuple, hisVolledigPredikaatViewModelNaamgevingStrategie);

            // Constructor
            JavaType defaultWrappedJavaType = hisVolledigInterfaceModelNaamgevingStrategie
                .getJavaTypeVoorElement(persoonIndicatieObjectType);
            JavaType wrappedJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, defaultWrappedJavaType);

            Constructor constr1 = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr1.setJavaDoc("Constructor met predikaat en peilmoment.");
            constr1.voegParameterToe(new JavaFunctieParameter(
                "persoonIndicatieHisVolledig", wrappedJavaType, "wrapped indicatie"));
            constr1.voegParameterToe(new JavaFunctieParameter(
                "predikaat", JavaType.PREDIKAAT_APACHE, "predikaat"));
            constr1.voegParameterToe(new JavaFunctieParameter("peilmomentVoorAltijdTonenGroepen",
                JavaType.DATUMTIJD_ATTRIBUUT, "peilmoment voor altijd tonen groepen"));
            constr1.setBody("super(persoonIndicatieHisVolledig, predikaat, peilmomentVoorAltijdTonenGroepen);");
            klasse.voegConstructorToe(constr1);

            Constructor constr2 = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr2.setJavaDoc("Constructor met predikaat.");
            constr2.voegParameterToe(new JavaFunctieParameter(
                "persoonIndicatieHisVolledig", wrappedJavaType, "wrapped indicatie"));
            constr2.voegParameterToe(new JavaFunctieParameter(
                "predikaat", JavaType.PREDIKAAT_APACHE, "predikaat"));
            constr2.setBody("super(persoonIndicatieHisVolledig, predikaat);");
            klasse.voegConstructorToe(constr2);

            // Methode voor ophalen historie
            final ObjectType hisIndicatieObjectType = this.getBmrDao().getElementVoorSyncIdVanLaag(
                SYNC_ID_HIS_PERSOON_INDICATIE, BmrLaag.OPERATIONEEL, ObjectType.class);
            genereerInterfaceImplementatieVoorHistorieGroep(persoonIndicatieObjectType,
                hisIndicatieObjectType, klasse, "getPersoonIndicatie()", false,
                bepaalHisIndicatieJavaType(tuple), heeftMaterieleHistorie);

            javaKlassen.add(klasse);
        }
        return javaKlassen;
    }

    /**
     * Genereert de getAlleHistorieRecords() functie, deze functie dient alle historie records die voldoen aan het predikaat van de view te retourneren.
     *
     * @param klasse    de view klasse
     * @param groepen   de groepen behorende bij de klasse
     * @param isSubType geeft aan of de klasse een subtype betreft
     */
    private void genereerFunctieDieAlleHisRecordsRetourneert(final JavaKlasse klasse, final List<Groep> groepen,
        final boolean isSubType)
    {
        final JavaFunctie functie = new JavaFunctie(
            JavaAccessModifier.PUBLIC, new JavaType(JavaType.SET, JavaType.HISTORIE_ENTITEIT),
            "getAlleHistorieRecords", "Retourneert alle historie records van alle groepen.");
        final StringBuilder body = new StringBuilder();
        body.append(String.format("final Set<%1$s> resultaat = ", JavaType.HISTORIE_ENTITEIT.getNaam()));
        if (isSubType) {
            body.append("super.getAlleHistorieRecords();");
        } else {
            body.append("new HashSet<>();");
            klasse.voegExtraImportsToe(JavaType.HASH_SET);
        }
        for (Groep groep : groepen) {
            final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
            if (hisObjectType != null) {
                body.append(String.format("resultaat.addAll(get%1$sHistorie().getHistorie());",
                    hisObjectType.getIdentCode().replace("His_", "")));
            }
        }
        body.append("return resultaat;");
        functie.setBody(body.toString());
        klasse.voegFunctieToe(functie);
    }

    /**
     * Voeg een private veld met getter toe.
     *
     * @param klasse de klasse
     * @param naam   de naam van het veld
     * @param type   het type van het veld
     */
    private void voegVeldGetterEnSetterToe(final JavaKlasse klasse, final String naam, final JavaType type) {
        final JavaVeld peilmomentAltijdTonenVeld = new JavaVeld(type, naam, false);
        peilmomentAltijdTonenVeld.setAccessModifier(JavaAccessModifier.PRIVATE);
        klasse.voegVeldToe(peilmomentAltijdTonenVeld);

        // Voeg ook een getter toe, zodat die in de subtypes gebruikt kan worden.
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(peilmomentAltijdTonenVeld);
        getter.setJavaDoc("Geeft de " + naam + " terug.");
        getter.setReturnWaardeJavaDoc("de " + naam);
        getter.setFinal(true);
        klasse.voegGetterToe(getter);

        final JavaMutatorFunctie setter = new JavaMutatorFunctie(peilmomentAltijdTonenVeld);
        setter.setJavaDoc("Zet de waarde van het veld " + naam + ".");
        setter.setFinal(true);
        klasse.voegSetterToe(setter);
    }

    /**
     * Genereert twee constructoren voor de predikaat view klasse. De constructor initialiseert direct ook alle klasse variabelen. Bij subtypen wordt de
     * super constructor aangeroepen met de parameters.
     *
     * @param objectType          objecttype uit BMR dat bij de klasse hoort
     * @param klasse              de predikaat view klasse
     * @param isSubType           boolean die aangeeft of het om een subtype gaat
     * @param proxyObjectVeldNaam proxy object veld naam
     */
    private void genereerConstructoren(final ObjectType objectType, final JavaKlasse klasse,
        final boolean isSubType, final String proxyObjectVeldNaam)
    {
        final Constructor constructorZonderPeilmoment = new Constructor(JavaAccessModifier.PUBLIC, klasse);
        constructorZonderPeilmoment.setJavaDoc("Constructor die alle klasse "
            + "variabelen initialiseert, behalve peilmoment.");

        //Parameter type moet van het java type zijn dat hoort bij het objecttype. Dus niet van een supertype o.i.d.
        final JavaType proxyObjectParameterType =
            hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(objectType);
        final String proxyObjectParameterNaam =
            GeneratieUtil.lowerTheFirstCharacter(proxyObjectParameterType.getNaam());

        constructorZonderPeilmoment.voegParameterToe(new JavaFunctieParameter(
            proxyObjectParameterNaam, proxyObjectParameterType, proxyObjectVeldNaam));
        constructorZonderPeilmoment.voegParameterToe(new JavaFunctieParameter(
            PREDIKAAT_VELDNAAM, JavaType.PREDIKAAT_APACHE, PREDIKAAT_VELDNAAM));

        // Roep 'onderstaande' constructor aan met 'null' voor peilmoment.
        constructorZonderPeilmoment.setBody(String.format("this(%1$s, %2$s, null);%3$s",
            proxyObjectParameterNaam, PREDIKAAT_VELDNAAM,
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        final Constructor constructorMetPeilmoment;
        try {
            constructorMetPeilmoment = (Constructor) constructorZonderPeilmoment.clone();
            constructorMetPeilmoment.setJavaDoc("Constructor die alle klasse variabelen initialiseert.");
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Onverwachte clone not supported exceptie.", e);
        }

        constructorMetPeilmoment.voegParameterToe(new JavaFunctieParameter(PEILMOMENT_ALTIJD_TONEN_GROEPEN_VELDNAAM,
            JavaType.DATUMTIJD_ATTRIBUUT, PEILMOMENT_ALTIJD_TONEN_GROEPEN_VELDNAAM));

        if (isSubType) {
            constructorMetPeilmoment.setBody(
                String.format("super(%1$s, %2$s, %3$s);%4$s", proxyObjectParameterNaam, PREDIKAAT_VELDNAAM,
                    PEILMOMENT_ALTIJD_TONEN_GROEPEN_VELDNAAM,
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        } else {
            constructorMetPeilmoment.setBody(genereerConstructorBodyMetInitialisatieVelden(
                proxyObjectVeldNaam, proxyObjectParameterNaam));
        }

        klasse.voegConstructorToe(constructorZonderPeilmoment);
        klasse.voegConstructorToe(constructorMetPeilmoment);
    }

    /**
     * Genereert de implementatie van interface functies die attributen van de identiteit groep retourneren.
     *
     * @param klasse              de predikaat view klass
     * @param groepen             alle groepen van het objecttype
     * @param proxyObjectVeldNaam proxy object veld naam in de klasse
     */
    private void genereerInterfaceImplementatiesVoorIdentiteitGroepAttributen(final JavaKlasse klasse,
        final List<Groep> groepen,
        final String proxyObjectVeldNaam)
    {
        for (Groep groep : groepen) {
            if (isIdentiteitGroep(groep)) {
                final List<Attribuut> attributenVanGroep = getBmrDao().getAttributenVanGroep(groep);
                for (Attribuut attribuut : attributenVanGroep) {
                    if (behoortTotJavaOperationeelModel(attribuut)) {
                        final JavaType javaType;
                        final boolean isDatabaseKnipAttribuut = isDatabaseKnipAttribuut(groep.getObjectType(), attribuut);
                        if (isIdAttribuut(attribuut)) {
                            final AttribuutType attrType =
                                getBmrDao().getElement(attribuut.getType().getId(), AttribuutType.class);
                            javaType = getJavaTypeVoorAttribuutType(attrType);
                        } else if (isDatabaseKnipAttribuut) {
                            javaType = bepaalJavaTypeVoorDatabaseKnipAttribuut(attribuut);
                        } else if (attribuut.getType().getSoortInhoud() != null
                            && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == attribuut.getType().getSoortInhoud())
                        {
                            final ObjectType element =
                                getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                            javaType = hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(element);
                        } else {
                            javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                        }

                        String functieNaam = "get";
                        if (isDatabaseKnipAttribuut) {
                            functieNaam += GeneratieUtil.upperTheFirstCharacter(bepaalVeldnaamVoorDatabaseKnipAttribuut(attribuut));
                        } else {
                            functieNaam += attribuut.getIdentCode();
                        }

                        final JavaFunctie javaFunctie =
                            new JavaFunctie(JavaAccessModifier.PUBLIC, javaType, functieNaam,
                                attribuut.getNaam() + " van " + groep.getObjectType().getNaam());
                        javaFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));

                        if (isDatabaseKnipAttribuut) {
                            javaFunctie.setFinal(true);
                            javaFunctie.setBody(String.format("return %1$s.%2$s();%3$s", proxyObjectVeldNaam, functieNaam,
                                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                        } else if (isDynamischObjecttypeAttribuut(attribuut)) {
                            final ObjectType objectType =
                                getBmrDao().getElement(attribuut.getType().getId(), ObjectType.class);
                            javaFunctie.setBody(
                                genereerFunctieBodyMetDynamischObjectTypeAlsReturnWaarde(
                                    klasse, objectType, javaType, proxyObjectVeldNaam, javaFunctie));

                        } else {
                            javaFunctie.setFinal(true);
                            javaFunctie.setBody(String.format("return %1$s.%2$s();%3$s", proxyObjectVeldNaam,
                                functieNaam,
                                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                        }
                        klasse.voegFunctieToe(javaFunctie);
                    }
                }
            }
        }
    }

    /**
     * Genereert een body voor een functie die een dynamisch objecttype klasse moet retourneren. Als dit objecttype een hierarchisch type is dan moet dus
     * ook de klasse die overeenkomt met het subtype worden geretourneerd, dus deze functie genereert dan ook een "if (x instanceOf subType)....else if"
     * blok waarin steeds een downcast wordt gedaan van het proxyobject om deze mee te geven aan de constructor van de predikaat view klasse die
     * overeenkomt met het subtype.
     *
     * @param klasse              klasse waarin de functie staat waarvoor deze functiebody wordt gegenereerd.
     * @param returnObjectType    return waarde objecttype, dit kan een hierarchisch type zijn zoals "Relatie".
     * @param javaType            dit is het java type wat geretourneerd moet worden. Het is in dit geval de isVolledig interface.
     * @param proxyObjectVeldNaam proxy object veld naam in de klasse.
     * @param javaFunctie         de javafunctie zelf waarvoor hier een body wordt gegenereerd.
     * @return string met de functie body.
     */
    private String genereerFunctieBodyMetDynamischObjectTypeAlsReturnWaarde(final JavaKlasse klasse,
        final ObjectType returnObjectType,
        final JavaType javaType,
        final String proxyObjectVeldNaam,
        final JavaFunctie javaFunctie)
    {
        //Body moet een predikaat view retourneren.
        final StringBuilder functieBody = new StringBuilder();
        final JavaType predikaatViewType =
            hisVolledigPredikaatViewModelNaamgevingStrategie.getJavaTypeVoorElement(returnObjectType);
        klasse.voegExtraImportsToe(predikaatViewType);
        if (isHierarchischType(returnObjectType) && verzamelFinaleSubtypen(returnObjectType).size() > 1) {
            //Genereer body die een subtype retourneert.
            final List<ObjectType> subTypen = verzamelFinaleSubtypen(returnObjectType);
            functieBody.append(String.format(
                "final %1$s %2$s = %3$s.%4$s();%5$s",
                javaType.getNaam(),
                GeneratieUtil.lowerTheFirstCharacter(returnObjectType.getIdentCode()),
                proxyObjectVeldNaam,
                javaFunctie.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            functieBody.append(String.format("final %1$s predikaatView;%2$s", predikaatViewType.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK
                    .getWaarde()));
            for (int i = 0; i < subTypen.size(); i++) {
                final ObjectType subType = subTypen.get(i);
                if (i > 0) {
                    functieBody.append(" else ");
                }
                final JavaType predikaatViewSubType =
                    hisVolledigPredikaatViewModelNaamgevingStrategie.getJavaTypeVoorElement(subType);
                final JavaType downCastType =
                    hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(subType);
                functieBody.append(
                    String.format("if (%1$s instanceof %2$s) {%3$s",
                        GeneratieUtil
                            .lowerTheFirstCharacter(returnObjectType.getIdentCode()),
                        downCastType.getNaam(),
                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                functieBody.append(
                    String.format("predikaatView = new %1$s((%2$s) %3$s, %4$s(), %5$s());%6$s",
                        predikaatViewSubType.getNaam(),
                        downCastType.getNaam(),
                        GeneratieUtil
                            .lowerTheFirstCharacter(returnObjectType.getIdentCode()),
                        PREDIKAAT_GETTERNAAM,
                        PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM,
                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                functieBody.append(String.format("}%s",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK
                        .getWaarde()));
                klasse.voegExtraImportsToe(downCastType, predikaatViewSubType);
            }
            functieBody.append(" else {")
                .append("predikaatView = null;")
                .append("}");
            functieBody.append(String.format("return predikaatView;%1$s",
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK
                    .getWaarde()));
        } else {
            functieBody.append(String.format("if (%1$s.%2$s() != null) {%3$s",
                proxyObjectVeldNaam,
                javaFunctie.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            functieBody.append(
                String.format("return new %1$s(%2$s.%3$s(), %4$s(), %5$s());%6$s",
                    predikaatViewType.getNaam(),
                    proxyObjectVeldNaam,
                    javaFunctie.getNaam(),
                    PREDIKAAT_GETTERNAAM,
                    PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM,
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            functieBody.append(String.format("}%1$sreturn null;",
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        }
        return functieBody.toString();
    }

    /**
     * Genereert functies die interface functies implementeren die inverse assoicaties retourneren. Bijvoorbeeld betrokkenheden en adressen in persoon.
     *
     * @param klasse                          de predikaat view klasse
     * @param proxyObjectVeldNaam             proxy object veld naam in de klasse
     * @param inverseAttributenVoorObjectType alle inverse associatie attributen van het objecttype.
     */
    private void genereerInterfaceImplementatiesVoorInverseAssociatieAttributen(
        final JavaKlasse klasse,
        final String proxyObjectVeldNaam,
        final List<Attribuut> inverseAttributenVoorObjectType)
    {
        for (Attribuut attribuut : inverseAttributenVoorObjectType) {
            final ObjectType inverseObjectType = attribuut.getObjectType();
            if (behoortTotJavaOperationeelModel(inverseObjectType)) {

                final JavaType javaTypeVoorElement =
                    hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(inverseObjectType);
                final JavaType predikaatViewType =
                    hisVolledigPredikaatViewModelNaamgevingStrategie.getJavaTypeVoorElement(inverseObjectType);

                final String functieNaam =
                    "get" + GeneratieUtil.upperTheFirstCharacter(attribuut.getInverseAssociatieIdentCode());
                final JavaFunctie javaFunctie =
                    new JavaFunctie(JavaAccessModifier.PUBLIC,
                        new JavaType(JavaType.SET, javaTypeVoorElement, true),
                        functieNaam, null);
                javaFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));

                final StringBuilder functieBody = new StringBuilder();
                functieBody.append(String.format("final %1$s<%2$s> resultaat = %3$s;%4$s",
                    JavaType.SORTED_SET.getNaam(),
                    javaTypeVoorElement.getNaam(),
                    bouwSetInitialisatieCodeFragmentEnVoegImportsToe(JavaType.TREE_SET, inverseObjectType, javaTypeVoorElement,
                        klasse, true, false, JavaType.HIS_VOLLEDIG_COMPARATOR_FACTORY),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                klasse.voegExtraImportsToe(JavaType.SORTED_SET);
                functieBody.append(String.format("for (%1$s %2$s : %3$s.%4$s()) {%5$s",
                    javaTypeVoorElement.getNaam(),
                    GeneratieUtil.lowerTheFirstCharacter(inverseObjectType.getIdentCode()),
                    proxyObjectVeldNaam,
                    functieNaam,
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

                if (isSupertype(inverseObjectType)) {
                    //Bij supertypen kijken we welke finale subtypen van toepassing zijn, voor elke subtype doen we een
                    //instanceOf check en een downcast zodat we het predikaat view subtype klasse kunnen instantieren.
                    final List<ObjectType> subTypen = verzamelFinaleSubtypen(inverseObjectType);
                    functieBody.append(String.format("final %1$s predikaatView;%2$s", predikaatViewType.getNaam(),
                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                    for (int i = 0; i < subTypen.size(); i++) {
                        final ObjectType subType = subTypen.get(i);
                        if (i > 0) {
                            functieBody.append(" else ");
                        }
                        final JavaType predikaatViewSubType =
                            hisVolledigPredikaatViewModelNaamgevingStrategie.getJavaTypeVoorElement(subType);
                        final JavaType downCastType =
                            hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(subType);
                        functieBody.append(
                            String.format("if (%1$s instanceof %2$s) {%3$s",
                                GeneratieUtil.lowerTheFirstCharacter(inverseObjectType.getIdentCode()),
                                downCastType.getNaam(),
                                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                        functieBody.append(
                            String.format("predikaatView = new %1$s((%2$s) %3$s, %4$s(), %5$s());%6$s",
                                predikaatViewSubType.getNaam(),
                                downCastType.getNaam(),
                                GeneratieUtil.lowerTheFirstCharacter(inverseObjectType.getIdentCode()),
                                PREDIKAAT_GETTERNAAM,
                                PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM,
                                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                        functieBody.append(String.format("}%s",
                            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

                        klasse.voegExtraImportsToe(downCastType, predikaatViewSubType);
                    }
                    functieBody.append("else {")
                        .append(String.format("throw new IllegalArgumentException(\"Onbekend type %1$s.\");", inverseObjectType.getNaam()))
                        .append("}");
                } else {
                    functieBody.append(String.format("final %1$s predikaatView = new %1$s(%2$s, %3$s(), %4$s());%5$s",
                        predikaatViewType.getNaam(),
                        GeneratieUtil.lowerTheFirstCharacter(inverseObjectType.getIdentCode()),
                        PREDIKAAT_GETTERNAAM,
                        PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM,
                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                }

                functieBody.append(String.format("resultaat.add(predikaatView);%s",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                functieBody.append(String.format("}%s", JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                functieBody.append(String.format("return resultaat;%s",
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                javaFunctie.setBody(functieBody.toString());
                klasse.voegExtraImportsToe(JavaType.HASH_SET, predikaatViewType);
                klasse.voegFunctieToe(javaFunctie);
            }
        }
    }

    /**
     * Genereer heeftXXX functies voor inverse associaties. Bijvoorbeeld heeftAdressen() en heeftNationaliteiten() Deze functies zijn nodig voor een
     * correcte werking van de JiBX binding. Ze worden gebruikt als test-method, om te bepalen of container tags ge-marshalled moeten worden.
     *
     * @param klasse                          de klasse waar de functie aan wordt toegevoegd
     * @param inverseAttributenVoorObjectType inverse attributen waarvoor een functie moet worden gegenereerd.
     */
    private void genereerTestMethodFunctiesVoorInverseAssociaties(final JavaKlasse klasse,
        final List<Attribuut> inverseAttributenVoorObjectType)
    {
        for (Attribuut attribuut : inverseAttributenVoorObjectType) {
            final ObjectType inverseObjectType = attribuut.getObjectType();
            if (behoortTotJavaOperationeelModel(inverseObjectType)) {

                //Hierarchische typen slaan we over omdat deze te complex zijn om daarvoor een test-method te genereren.
                //Dit ivm diverse subtypen en groepen etc. De implementatie hiervan zou dus moeten worden toegevoegd in
                // de
                //generation gap user klasse. Om dit af te dwingen voegen we een abstracte functie toe.
                final boolean hierarchischType = isHierarchischType(inverseObjectType);
                final String functieNaam =
                    "heeft" + GeneratieUtil.upperTheFirstCharacter(attribuut.getInverseAssociatieIdentCode());
                final JavaFunctie javaFunctie = new JavaFunctie(
                    JavaAccessModifier.PUBLIC, new JavaType("boolean", "java.lang"), functieNaam,
                    "true indien de predikaat historie records kent");
                javaFunctie.setJavaDoc(
                    String.format("Retourneert of de predikaat historie records kent voor %1$s of niet.",
                        attribuut.getInverseAssociatieNaam()));

                if (hierarchischType) {
                    javaFunctie.setAbstract(true);
                } else if (heeftMinstensEenGroepMetHistorie(inverseObjectType)) {
                    javaFunctie.setFinal(true);
                    final StringBuilder functieBody = new StringBuilder();
                    final JavaType javaTypeVoorElement =
                        hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(inverseObjectType);
                    functieBody.append(
                        String.format(
                            "for (%1$s %2$s : get%3$s()) {",
                            javaTypeVoorElement.getNaam(),
                            GeneratieUtil.lowerTheFirstCharacter(javaTypeVoorElement.getNaam()),
                            GeneratieUtil.upperTheFirstCharacter(attribuut.getInverseAssociatieIdentCode())));
                    functieBody.append(String.format("if(!%1$s.get%2$sHistorie().isLeeg()) {",
                        GeneratieUtil.lowerTheFirstCharacter(javaTypeVoorElement.getNaam()),
                        inverseObjectType.getIdentCode()));
                    functieBody.append("return true;");
                    functieBody.append("}");
                    functieBody.append("}");
                    functieBody.append("return false;");
                    javaFunctie.setBody(functieBody.toString());
                } else {
                    // We gaan er nu van uit dat hier de return waarde true moet zijn, omdat het inverse objecttype
                    // op zijn beurt weer inverse associatie heeft die historie kennen.
                    javaFunctie.setBody("return true;");
                }

                klasse.voegFunctieToe(javaFunctie);
            }
        }
    }

    /**
     * Genereer heeftXXXVoorLeveren functies voor inverse associaties. Bijvoorbeeld heeftAdressenVoorLeveren() en heeftNationaliteitenVoorLeveren() Deze
     * functies zijn nodig voor een correcte werking van de JiBX binding. Ze worden gebruikt als test-method, om te bepalen of container tags ge-marshalled
     * moeten worden.
     *
     * @param klasse                          de klasse waar de functie aan wordt toegevoegd
     * @param inverseAttributenVoorObjectType inverse attributen waarvoor een functie moet worden gegenereerd.
     */
    private void genereerTestMethodFunctiesVoorInverseAssociatiesVoorLeveren(
        final JavaKlasse klasse, final List<Attribuut> inverseAttributenVoorObjectType)
    {
        for (Attribuut attribuut : inverseAttributenVoorObjectType) {
            final ObjectType inverseObjectType = attribuut.getObjectType();
            if (behoortTotJavaOperationeelModel(inverseObjectType)) {
                if (inverseObjectType.getSchema().getId() == ID_KERN_SCHEMA) {
                    //Hierarchische typen slaan we over omdat deze te complex zijn om daarvoor een test-method te
                    // genereren.
                    //Dit ivm diverse subtypen en groepen etc. De implementatie hiervan zou dus moeten worden toegevoegd
                    //in de generation gap user klasse. Om dit af te dwingen voegen we een abstracte functie toe.
                    final boolean hierarchischType = isHierarchischType(inverseObjectType);
                    final String functieNaam =
                        "heeft" + GeneratieUtil.upperTheFirstCharacter(attribuut.getInverseAssociatieIdentCode()
                            + "VoorLeveren");
                    final JavaFunctie javaFunctie = new JavaFunctie(
                        JavaAccessModifier.PUBLIC, new JavaType("boolean", "java.lang"), functieNaam,
                        "true indien de predikaat historie records kent die geleverd mogen worden");
                    javaFunctie.setJavaDoc(
                        String.format("Retourneert of de predikaat historie records kent die geleverd mogen "
                                + "worden voor %1$s of niet.",
                            attribuut.getInverseAssociatieNaam()));

                    if (hierarchischType) {
                        javaFunctie.setAbstract(true);
                    } else if (heeftMinstensEenGroepMetHistorie(inverseObjectType)) {
                        javaFunctie.setFinal(true);
                        final Groep groepMetHistorie = getGroepMetHistorie(inverseObjectType);
                        if (groepMetHistorie != null) {
                            final StringBuilder functieBody = new StringBuilder();
                            final JavaType javaTypeVoorElement =
                                hisVolledigInterfaceModelNaamgevingStrategie
                                    .getJavaTypeVoorElement(inverseObjectType);
                            String genericType = "";

                            if (inverseObjectType.getId() == ID_PERSOON_INDICATIE) {
                                genericType = "<?>";
                            }
                            functieBody.append(
                                String.format(
                                    "for (%1$s%2$s %3$s : get%4$s()) {",
                                    javaTypeVoorElement.getNaam(),
                                    genericType,
                                    GeneratieUtil.lowerTheFirstCharacter(javaTypeVoorElement.getNaam()),
                                    GeneratieUtil
                                        .upperTheFirstCharacter(
                                            attribuut.getInverseAssociatieIdentCode())));
                            functieBody.append(String.format("if(!%1$s.get%2$sHistorie().isLeeg()) {",
                                GeneratieUtil
                                    .lowerTheFirstCharacter(javaTypeVoorElement.getNaam()),
                                inverseObjectType.getIdentCode()));

                            final ObjectType hisObjectType =
                                getBmrDao().getOperationeelModelObjectTypeVoorGroep(groepMetHistorie);
                            final JavaType hisObjectJavaType =
                                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(hisObjectType);
                            klasse.voegExtraImportsToe(hisObjectJavaType);
                            functieBody.append(String.format(
                                "for (%1$s %2$s : %3$s.get%4$sHistorie().getHistorie()) {",
                                hisObjectJavaType.getNaam(),
                                GeneratieUtil.lowerTheFirstCharacter(hisObjectJavaType.getNaam()),
                                GeneratieUtil.lowerTheFirstCharacter(javaTypeVoorElement.getNaam()),
                                inverseObjectType.getIdentCode()));
                            functieBody.append("return true;");
                            functieBody.append("}");


                            functieBody.append("}");
                            functieBody.append("}");
                            functieBody.append("return false;");
                            javaFunctie.setBody(functieBody.toString());

                        }

                    } else {
                        // We gaan er nu van uit dat hier de return waarde true moet zijn, omdat het inverse objecttype
                        // op zijn beurt weer inverse associatie heeft die historie kennen.
                        javaFunctie.setBody("return true;");
                    }

                    klasse.voegFunctieToe(javaFunctie);
                }
            }
        }
    }

    /**
     * Geeft een groep met historie. Indien er een standaardGroep is deze, anders de identiteitGroep indien deze historie heeft.
     *
     * @param inverseObjectType het inverse object type waarvan de historie groep gevraagd wordt
     * @return een groep met historie
     */
    private Groep getGroepMetHistorie(final ObjectType inverseObjectType) {
        Groep resultaat = getStandaardGroep(inverseObjectType);
        if (resultaat == null) {
            Groep identiteitGroep = getIdentiteitGroep(inverseObjectType);
            if (kentHistorie(identiteitGroep)) {
                resultaat = identiteitGroep;
            }
        }

        return resultaat;
    }

    /**
     * Forwarding functie, zie hier beneden.
     *
     * @param objectType          .
     * @param klasse              .
     * @param groep               .
     * @param proxyObjectVeldNaam .
     * @param subType             .
     */
    private void genereerInterfaceImplementatieVoorHistorieGroep(final ObjectType objectType, final JavaKlasse klasse,
        final Groep groep, final String proxyObjectVeldNaam,
        final boolean subType)
    {
        final ObjectType hisObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
        if (hisObjectType != null) {
            final JavaType historieObjectTypeJavaType =
                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(hisObjectType);
            // Geen materiele historie betekent in dit geval automatisch formele historie.
            boolean heeftMaterieleHistorie = kentMaterieleHistorie(groep);
            genereerInterfaceImplementatieVoorHistorieGroep(objectType, hisObjectType, klasse,
                proxyObjectVeldNaam, subType, historieObjectTypeJavaType,
                heeftMaterieleHistorie);
        }
    }

    /**
     * Genereert functies die interface functies implementeren die de historie van een groep retourneren maar dan op basis van het predikaat.
     *
     * @param objectType                 het objecttype uit het BMR
     * @param hisObjectType              het object type van het his object
     * @param klasse                     de predikaat view klass
     * @param proxyObjectVeldNaam        proxy object veld naam in de klasse
     * @param subType                    boolean die aangeeft of het om een subtype gaat
     * @param historieObjectTypeJavaType historie object java type
     * @param heeftMaterieleHistorie     of het type materiele historie heeft (true) of formele (false) CHECKSTYLE-BRP:OFF - MAX PARAMS
     */
    private void genereerInterfaceImplementatieVoorHistorieGroep(
        final ObjectType objectType, final ObjectType hisObjectType, final JavaKlasse klasse,
        final String proxyObjectVeldNaam, final boolean subType,
        final JavaType historieObjectTypeJavaType, final boolean heeftMaterieleHistorie)
    {
        //CHECKSTYLE-BRP:ON - MAX PARAMS
        final JavaType setInterface = bepaalSlimmeSetInterface(heeftMaterieleHistorie);
        final JavaType setImplementatie = bepaalSlimmeSetImplementatie(heeftMaterieleHistorie);
        final String functieNaam = "get" + hisObjectType.getIdentCode().replace("His_", "") + "Historie";
        final JavaFunctie functieImplementatie = new JavaFunctie(
            JavaAccessModifier.PUBLIC, new JavaType(setInterface, historieObjectTypeJavaType, false),
            functieNaam,
            "Historie met " + hisObjectType.getNaam());
        functieImplementatie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        functieImplementatie.setFinal(true);

        final StringBuilder functieBody = new StringBuilder();
        if (subType) {
            //Subtypen behandelen we op een andere manier, het proxy object zit namelijk in het finale super type.
            //Dit proxy object moeten we dus downcasten om de historie van een groep van het subtype te kunnen
            //opvragen. Downcasten is veilig, want het subtype kent een constructor waar alleen het subtype type
            //zelf in kan.
            final String proxyObjectVeldInSuperType =
                GeneratieUtil.lowerTheFirstCharacter(objectType.getFinaalSupertype().getIdentCode());
            final JavaType downCastType =
                hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(objectType);

            functieBody.append(String.format(
                "final Set<%1$s> historie = new HashSet<%1$s>(((%2$s)%3$s).%4$s().getHistorie());%5$s",
                historieObjectTypeJavaType.getNaam(),
                downCastType.getNaam(),
                GeneratieUtil.lowerTheFirstCharacter(proxyObjectVeldInSuperType),
                functieNaam,
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        } else {
            functieBody.append(String.format(
                "final Set<%1$s> historie = new HashSet<%1$s>(%2$s.%3$s().getHistorie());%4$s",
                historieObjectTypeJavaType.getNaam(),
                proxyObjectVeldNaam,
                functieNaam,
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        }

        functieBody.append(String.format("final Set<%1$s> teTonenHistorie = "
                + "new HashSet<%1$s>();%n",
            historieObjectTypeJavaType.getNaam()));


        functieBody.append(String.format("teTonenHistorie.addAll(historie);%1$s%1$s",
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        functieBody.append(String.format("CollectionUtils.filter(teTonenHistorie, %1$s());%2$s",
            PREDIKAAT_GETTERNAAM,
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        // Check op altijd tonen groepen.
        // Alleen als het objecttype naast de Identiteit groep ook een andere groep heeft!
        // De identiteit groep is géén altijd-tonen-groep, vandaar de conditie.
        if (!heeftEnkelIdentiteitGroep(objectType)) {
            functieBody.append(String.format("if (HisVolledigPredikaatViewUtil."
                    + "isAltijdTonenGroep(%1$s.class) && teTonenHistorie.isEmpty() "
                    + "&& %2$s() != null) {%3$s",
                historieObjectTypeJavaType.getNaam(),
                PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM,
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            functieBody.append(String.format("CollectionUtils.select(historie, "
                    + "AltijdTonenGroepPredikaat.bekendOp(%1$s()), teTonenHistorie);%2$s",
                // Gebruik altijd de getter, zodat we geen onderscheid hoeven te maken tussen sub en supertypes.
                PEILMOMENT_ALTIJD_TONEN_GROEPEN_GETTER_NAAM,
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            functieBody.append("}")
                .append(JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde());
            klasse.voegExtraImportsToe(JavaType.ALTIJD_TONEN_GROEP_PREDIKAAT,
                JavaType.HIS_VOLLEDIG_PREDIKAAT_VIEW_UTIL);
        }

        if (!objectType.getNaam().contains("Indicatie")) {
            functieBody
                .append("CollectionUtils.select(historie, new AndPredicate(new IsInOnderzoekPredikaat(), getHistorischPredikaat()), teTonenHistorie);");
            klasse.voegExtraImportsToe(JavaType.IS_IN_ONDERZOEK_PREDIKAAT,
                JavaType.HIS_VOLLEDIG_PREDIKAAT_VIEW_UTIL, JavaType.COLLECTION_UTILS_ANDPREDICATE);
        }

        JavaType comparatorType;
        if (heeftMaterieleHistorie) {
            comparatorType = JavaType.MATERIELE_HISTORIE_ENTITEIT_COMPARATOR;
        } else {
            comparatorType = JavaType.FORMELE_HISTORIE_ENTITEIT_COMPARATOR;
        }

        functieBody.append(String.format("final Set<%1$s> gesorteerdeHistorie = "
                + "new TreeSet<%1$s>(new %2$s<%1$s>());%3$s",
            historieObjectTypeJavaType.getNaam(), comparatorType.getNaam(),
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        klasse.voegExtraImportsToe(comparatorType);

        functieBody.append(String.format("gesorteerdeHistorie.addAll(teTonenHistorie);%1$s%1$s",
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        functieBody.append(String.format("return new %1$s<%2$s>(gesorteerdeHistorie);%3$s",
            setImplementatie.getNaam(), historieObjectTypeJavaType.getNaam(),
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        functieImplementatie.setBody(functieBody.toString());
        klasse.voegFunctieToe(functieImplementatie);

        // Extra imports, die nodig zijn vanuit de code van de functie body.
        klasse.voegExtraImportsToe(setImplementatie, setInterface,
            JavaType.COLLECTION_UTILS, JavaType.SET, JavaType.HASH_SET, JavaType.TREE_SET);
    }

    /**
     * Genereert de body voor een constructor van een predikaat view klasse. De constructor initialiseert gelijk ook alle klasse variabelen.
     *
     * @param proxyObjectVeldNaam      proxy object veld naam in de klasse
     * @param proxyObjectParameterNaam constructor parameter naam van het proxy object.
     * @return body voor de constructor
     */
    private String genereerConstructorBodyMetInitialisatieVelden(final String proxyObjectVeldNaam,
        final String proxyObjectParameterNaam)
    {
        final StringBuilder constructorBody = new StringBuilder();

        constructorBody.append(String.format("super(%1$s);%2$s", PREDIKAAT_VELDNAAM,
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        constructorBody.append(String.format("this.%1$s = %2$s;%3$s", proxyObjectVeldNaam,
            proxyObjectParameterNaam, JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        constructorBody.append(String.format("this.%1$s = %1$s;%2$s", PEILMOMENT_ALTIJD_TONEN_GROEPEN_VELDNAAM,
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        return constructorBody.toString();
    }

}
