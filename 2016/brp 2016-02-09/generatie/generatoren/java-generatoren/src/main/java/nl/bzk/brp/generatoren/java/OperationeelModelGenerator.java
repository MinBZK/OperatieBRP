/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.CategorieVerantwoording;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.common.HistoriePatroon;
import nl.bzk.brp.generatoren.algemeen.common.SoortHistorie;
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
import nl.bzk.brp.generatoren.java.naamgeving.AttribuutWrapperNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.BerichtModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigInterfaceModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.HisVolledigModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.LogischModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.OperationeelModelNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.symbols.SymbolTableConstants;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.AbstractGenerationGapPatroonJavaWriter;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Tuple;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Generator die de Java Model classes genereert voor het operationeel model.
 */
@Component("operationeelModelJavaGenerator")
public class OperationeelModelGenerator extends AbstractJavaGenerator {

    private final NaamgevingStrategie operationeelModelNaamgevingStrategie         = new OperationeelModelNaamgevingStrategie();
    private final NaamgevingStrategie logischModelNaamgevingStrategie              = new LogischModelNaamgevingStrategie();
    private final NaamgevingStrategie berichtModelNaamgevingStrategie              = new BerichtModelNaamgevingStrategie();
    private final NaamgevingStrategie wrapperNaamgevingStrategie                   = new AttribuutWrapperNaamgevingStrategie();
    private final NaamgevingStrategie hisVolledigInterfaceModelNaamgevingStrategie =
        new HisVolledigInterfaceModelNaamgevingStrategie();
    private final NaamgevingStrategie hisVolledigModelNaamgevingStrategie          = new HisVolledigModelNaamgevingStrategie();

    private static final List<Integer> VERANTWOORDINGS_ENTITEITEN = Arrays.asList(ID_ACTIE_LGM, ID_DIENST_LGM);

    private static final Set<String> EXTRA_KLASSEN_DIE_ELEMENT_IDENTIFICEERBAAR_DIENEN_TE_ZIJN = new HashSet<>(
        Collections.singletonList("HisPersoonAfnemerindicatieModel")
    );

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final List<JavaKlasse> klassen = new ArrayList<>();
        klassen.addAll(genereerOperationeleObjectTypen());
        klassen.addAll(genereerOperationeleGroepen());
        klassen.addAll(genereerHistorieObjectTypen());

        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
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
        final List<JavaKlasse> javaKlassen = new ArrayList<>();
        final List<Groep> groepen = getBmrDao().getGroepenWaarvanHistorieWordtVastgelegd();
        for (final Groep groep : groepen) {


            final ObjectType operationeelModelObjectType = getBmrDao().getOperationeelModelObjectTypeVoorGroep(groep);
            if (operationeelModelObjectType != null) {
                if (operationeelModelObjectType.getSyncid() == SYNC_ID_HIS_PERSOON_INDICATIE) {
                    javaKlassen.addAll(genereerHistorieHierarchieVoorPersoonIndicatie(operationeelModelObjectType));
                } else {
                    javaKlassen.add(genereerHistorieKlasseVoorGroep(groep, operationeelModelObjectType));
                }
            }
        }
        return javaKlassen;
    }

    /**
     * Specifieke methode voor het genereren van de klasse hierarchie voor persoon indicaties. Hierbij wordt voor elke soort indicatie een subklasse
     * gegenereerd. De super klasse is al in het model aanwezig.
     *
     * @param hisPersoonIndicatieObjectType het object type in het BMR van his persoon indicatie (OGM)
     * @return de lijst met historie (sub)klassen voor de his persoon indicatie
     */
    private List<JavaKlasse> genereerHistorieHierarchieVoorPersoonIndicatie(final ObjectType hisPersoonIndicatieObjectType) {
        final List<JavaKlasse> javaKlassen = new ArrayList<>();

        final ObjectType soortInidcatieObjectType = this.getBmrDao().getElement(ID_SOORT_INDICATIE, ObjectType.class);
        for (final Tuple tuple : soortInidcatieObjectType.getTuples()) {
            final boolean heeftMaterieleHistorie = heeftPersoonIndicatieTupleMaterieleHistorie(tuple);

            // Algemene zaken en mappings
            final JavaType defaultKlasseType = operationeelModelNaamgevingStrategie
                .getJavaTypeVoorElement(hisPersoonIndicatieObjectType);
            final JavaType indicatieJavaType = maakPersoonIndicatieSubtypeJavaType(tuple, defaultKlasseType);
            final JavaKlasse klasse = new JavaKlasse(indicatieJavaType, "Subtype klasse voor indicatie "
                + tuple.getNaam());
            klasse.voegSuperInterfaceToe(JavaType.ELEMENT_IDENTIFICEERBAAR);
            klasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY));
            klasse.voegAnnotatieToe(new JavaAnnotatie(JavaType.DISCRIMINATOR_VALUE,
                new AnnotatieWaardeParameter("value", tuple.getVolgnummerT().toString())));
            klasse.voegConstructorToe(genereerDefaultLegeConstructor(klasse));

            // Constructoren
            final ObjectType persoonInidcatieObjectType = this.getBmrDao().getElement(ID_PERSOON_INDICATIE, ObjectType.class);
            final JavaType defaultBackreferenceType = hisVolledigModelNaamgevingStrategie
                .getJavaTypeVoorElement(persoonInidcatieObjectType);
            final JavaType backreferenceType = maakPersoonIndicatieSubtypeJavaType(tuple, defaultBackreferenceType);

            final Constructor constr1 = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr1.setJavaDoc("Copy constructor met een groep instantie.");
            constr1.voegParameterToe(new JavaFunctieParameter(
                "persoonIndicatieHisVolledig", backreferenceType, "backreference"));
            constr1.voegParameterToe(new JavaFunctieParameter(
                "groep", JavaType.PERSOON_INDICATIE_STANDAARD_GROEP, "groep"));
            constr1.voegParameterToe(new JavaFunctieParameter("historie", JavaType.MATERIELE_HISTORIE, "historie"));
            constr1.voegParameterToe(new JavaFunctieParameter("actieInhoud", JavaType.ACTIE_MODEL, "actieInhoud"));
            constr1.setBody("super(persoonIndicatieHisVolledig, groep, historie, actieInhoud);");
            klasse.voegConstructorToe(constr1);

            final Constructor constr2 = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr2.setJavaDoc("Copy constructor met alle atribuut waarden.");
            constr2.voegParameterToe(new JavaFunctieParameter(
                "persoonIndicatieHisVolledig", backreferenceType, "backreference"));
            constr2.voegParameterToe(new JavaFunctieParameter("waarde", JavaType.JA_ATTRIBUUT, "waarde"));
            constr2.voegParameterToe(new JavaFunctieParameter("actieInhoud", JavaType.ACTIE_MODEL, "actieInhoud"));
            constr2.voegParameterToe(new JavaFunctieParameter("historie", JavaType.MATERIELE_HISTORIE, "historie"));
            constr2.setBody("super(persoonIndicatieHisVolledig, waarde, actieInhoud, historie);");
            klasse.voegConstructorToe(constr2);

            final Constructor constr3 = new Constructor(JavaAccessModifier.PUBLIC, klasse);
            constr3.setJavaDoc("Copy constructor.");
            // We gaan uit van generation gap.
            final JavaType kopieParameterType = new JavaType(
                "Abstract" + klasse.getType().getNaam(),
                klasse.getType().getPackagePad() + AbstractGenerationGapPatroonJavaWriter.GENERATIE_TYPE_SUB_PACKAGE);
            constr3.voegParameterToe(new JavaFunctieParameter("kopie", kopieParameterType, "kopie"));
            constr3.setBody("super(kopie);");
            klasse.voegConstructorToe(constr3);

            if (heeftMaterieleHistorie) {
                klasse.setExtendsFrom(JavaType.HIS_PERSOON_INDICATIE_MATERIELE_HISTORIE_MODEL);

                // Verplichte kopie functie bij materiele historie.
                final JavaFunctie kopieFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, "kopieer");
                kopieFunctie.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                kopieFunctie.setReturnType(klasse.getType());
                kopieFunctie.setReturnWaardeJavaDoc("kopie");
                kopieFunctie.setBody("return new " + klasse.getType().getNaam() + "(this);");
                klasse.voegFunctieToe(kopieFunctie);
            } else {
                klasse.setExtendsFrom(JavaType.HIS_PERSOON_INDICATIE_FORMELE_HISTORIE_MODEL);
            }
            javaKlassen.add(klasse);
        }

        return javaKlassen;
    }

    /**
     * Genereert voor de groep de historie klasse met JPA (ORM) informatie om historie uit de database te kunnen ophalen. Dit wordt gedaan door op basis
     * van de syncid van de groep het operationele object type op te halen en daarvan een java klasse te genereren.
     *
     * @param groep                       De groep waarvoor een historie klasse wordt gegenereerd.
     * @param operationeelModelObjectType Het object type waarvoor een historie klasse wordt gegenereerd.
     * @return Een java klasse dat het historie object type voorstelt.
     */
    private JavaKlasse genereerHistorieKlasseVoorGroep(final Groep groep, final ObjectType operationeelModelObjectType) {
        final JavaKlasse klasse = new JavaKlasse(
            operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(operationeelModelObjectType), null);

        // Uitzondering afvangen: geen 'Attribuut' suffix van toepassing hier. Wordt wel gemaakt voor HisPartij,
        // omdat partij een stamgegeven is. Dus knippen we het eraf.
        final String attribuutSuffix = AttribuutWrapperNaamgevingStrategie.ATTRIBUUT_SUFFIX;
        if (klasse.getNaam().endsWith(attribuutSuffix)) {
            klasse.setNaam(klasse.getNaam().substring(0, klasse.getNaam().length() - attribuutSuffix.length()));
        }

        //Is dit een identiteits groep? Dan is er geen logische interface.
        //Kent de groep een logische interface? Dan extenden we van de logische interface.
        if (!isIdentiteitGroep(groep)
            && behoortTotJavaLogischModel(groep)
            && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == groep.getObjectType().getSoortInhoud())
        {
            klasse.voegSuperInterfaceToe(logischModelNaamgevingStrategie.getJavaTypeVoorElement(groep));
        }

        final JavaType idType = getJavaTypeVoorIdVeld(operationeelModelObjectType);
        if (idType != null && idType.isNumeriek()) {
            klasse.voegSuperInterfaceToe(new JavaType(JavaType.MODEL_IDENTIFICEERBAAR, idType));
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

        //Groep accessor annotatie voor AOP. Enkel voor dynamische object typen.
        if (BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == groep.getObjectType().getSoortInhoud()
            && groep.getObjectType().getSchema().getId() == ID_KERN_SCHEMA)
        {
            klasse.voegAnnotatieToe(new JavaAnnotatie(
                JavaType.GROEP_ACCESSOR, new AnnotatieWaardeParameter("dbObjectId",
                String.valueOf(groep.getSyncid()),
                true)));
        }

        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(operationeelModelObjectType);
        final ObjectType logischModelObjectType = groep.getObjectType();
        for (final Attribuut attribuut : attributen) {
            if (!isHistorieGerelateerdAttribuut(attribuut)) {
                // We gebruiken hier syncid, omdat de groep uit de logische laag groep komt,
                // maar het attribuut uit de operationele laag.
                // We moeten nog een extra regel toepassen voor het geval er meerdere referenties
                // zijn naar het object type (komt voor bij Persoon / Inschrijving met vorige /volgende persoon).
                // We gaan er van uit dat alleen bij back reference de naam van het attribuut
                // hetzelfde is als de naam van het object type waar het naar verwijst.
                final boolean isBackreference = isBackreference(logischModelObjectType, attribuut);
                genereerVeldVoorHistorieAttribuut(klasse, logischModelObjectType, operationeelModelObjectType, attribuut, isBackreference);
            }
        }

        genereerHisGetterVoorAlleAttributen(klasse, groep, attributen);
        klasse.voegConstructorToe(genereerDefaultLegeConstructor(klasse));

        if (BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == groep.getObjectType().getSoortInhoud()
            || BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == groep.getObjectType().getSoortInhoud())
        {
            //Voeg velden toe voor de historie gerelateerde zaken.
            if (SoortHistorie.FORMEEL.getHistorieVastleggen() == groep.getHistorieVastleggen()) {
                if (CategorieVerantwoording.DIENST.getCode() == groep.getVerantwoordingCategorie()) {
                    klasse.setExtendsFrom(JavaType.ABSTRACT_FORMEEL_HISTORISCH_MET_DIENST_VERANTWOORDING);
                } else if (CategorieVerantwoording.ACTIE.getCode() == groep.getVerantwoordingCategorie()) {
                    klasse.setExtendsFrom(JavaType.ABSTRACT_FORMEEL_HISTORISCH_MET_ACTIE_VERANTWOORDING);
                } else if ('G' == groep.getVerantwoordingCategorie()) {
                    // TODO dit is tijdelijke oplossing ivm BMR-42 wijzigingen in POC Bijhouding
                    klasse.setExtendsFrom(JavaType.ABSTRACT_FORMEEL_HISTORISCH_MET_ACTIE_VERANTWOORDING);
                } else {
                    throw new IllegalArgumentException("Verantwoording categorie wordt niet ondersteund: "
                        + groep.getVerantwoordingCategorie());
                }
            } else if (SoortHistorie.MATERIEEL.getHistorieVastleggen() == groep.getHistorieVastleggen()) {
                if (CategorieVerantwoording.DIENST.getCode() == groep.getVerantwoordingCategorie()) {
                    klasse.setExtendsFrom(JavaType.ABSTRACT_MATERIEEL_HISTORISCH_MET_DIENST_VERANTWOORDING);
                } else if (CategorieVerantwoording.ACTIE.getCode() == groep.getVerantwoordingCategorie()) {
                    klasse.setExtendsFrom(JavaType.ABSTRACT_MATERIEEL_HISTORISCH_MET_ACTIE_VERANTWOORDING);
                } else if ('G' == groep.getVerantwoordingCategorie()) {
                    // TODO dit is tijdelijke oplossing ivm BMR-42 wijzigingen in POC Bijhouding
                    klasse.setExtendsFrom(JavaType.ABSTRACT_MATERIEEL_HISTORISCH_MET_ACTIE_VERANTWOORDING);
                } else {
                    throw new IllegalArgumentException("Verantwoording categorie wordt niet ondersteund: "
                        + groep.getVerantwoordingCategorie());
                }
            }

            if (HistoriePatroon.BESTAANSPERIODE_FORMEEL.getPatroon().equals(groep.getHistoriePatroon())) {
                klasse.voegSuperInterfaceToe(JavaType.BESTAANSPERIODE_FORMEEL);
            } else if (HistoriePatroon.BESTAANSPERIODE_FORMEEL_IMPLICIETMATERIEEL.getPatroon().equals(groep.getHistoriePatroon())) {
                if (klasse.getPackagePad().contains("autaut")) {
                    klasse.voegSuperInterfaceToe(JavaType.BESTAANSPERIODE_FORMEEL_IMPLICIETMATERIEEL_AUTAUT);
                } else {
                    klasse.voegSuperInterfaceToe(JavaType.BESTAANSPERIODE_FORMEEL_IMPLICIETMATERIEEL);
                }
            } else if (HistoriePatroon.BESTAANSPERIODE_FORMEEL_MATERIEEL.getPatroon().equals(groep.getHistoriePatroon())) {
                klasse.voegSuperInterfaceToe(JavaType.BESTAANSPERIODE_FORMEEL_MATERIEEL);
            }

            //Voeg een constructor toe met alle velden los als parameters
            final Constructor constructor = genereerConstructorMetAlleParametersVoorKlasse(klasse, groep);
            klasse.voegConstructorToe(constructor);

            //Voeg een constructor toe om op basis van een C/D laag entiteit een kopie te maken.
            final Constructor constructor2 = genereerCopyConstructorVoorHistoryKlasse(klasse);
            klasse.voegConstructorToe(constructor2);
        }

        if (BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() == groep.getObjectType().getSoortInhoud()) {
            // Voeg een copy constructor toe om op basis van een logische interface een historie object
            // aan te maken. Dit is voor Identiteit groepen niet van belang, omdat deze groepen geen inhoudelijke velden kennen.
            // Dit doen we niet voor statische stamgegevens.
            if (!isIdentiteitGroep(groep)) {
                final Constructor constructor1 =
                    genereerCopyConstructorMetGroepParameterVoorHistoryKlasse(klasse, operationeelModelObjectType, logischModelObjectType, groep);
                klasse.voegConstructorToe(constructor1);
            }
        }

        if (klasse.getSuperKlasse() != null
            && klasse.getSuperKlasse().getNaam().equals(JavaType.ABSTRACT_MATERIEEL_HISTORISCH_MET_ACTIE_VERANTWOORDING.getNaam()))
        {
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

        // Voeg de 'Element getElement()' functie toe, alleen voor groepen van het KERN schema of welke in de extra_klassen constants staan.
        if (groep.getSchema().getId() == ID_KERN_SCHEMA || EXTRA_KLASSEN_DIE_ELEMENT_IDENTIFICEERBAAR_DIENEN_TE_ZIJN.contains(klasse.getNaam())) {
            voegGetElementFunctieToe(klasse, groep);
        }

        return klasse;
    }


    /**
     * Controleert of het attribuut een backreference is.
     *
     * @param logischModelObjectType logisch model object type
     * @param attribuut              attribuut
     * @return true als het backreference is, anders false
     */
    private boolean isBackreference(final ObjectType logischModelObjectType, final Attribuut attribuut) {
        ObjectType backreferenceType = logischModelObjectType;
        if (heeftSupertype(logischModelObjectType)) {
            // In het geval van een hierarchisch type kijken we voor de type vergelijking
            // naar het finale supertype, omdat in het operationeel
            // model de backreference daar heen gaat en niet naar het specifieke subtype.
            backreferenceType = logischModelObjectType.getFinaalSupertype();
        }
        final boolean isBackreferenceType = backreferenceType.getSyncid().equals(attribuut.getType().getSyncid());
        return isBackreferenceType && attribuut.getNaam().equals(backreferenceType.getNaam());
    }

    /**
     * Genereert een getter die alle attributen retourneert voor een groep-klasse van het hismodel.
     *
     * @param klasse     klasse
     * @param groep      groep
     * @param attributen lijst met attributen
     */
    private void genereerHisGetterVoorAlleAttributen(final JavaKlasse klasse, final Groep groep, final List<Attribuut> attributen) {
        final List<Attribuut> gefilterdeAttributen = new ArrayList<>();

        // Er wordt gebruik gemaakt van de velden ipv rechtstreeks op attributen aangezien het JavaType van het JavaVeld invloed heeft op welke
        // velden wel of niet worden teruggegeven, zie methode 'genereerVeldVoorHistorieAttribuut'.
        final Set<JavaVeld> velden = klasse.getVelden();
        for (final JavaVeld javaVeld : velden) {
            final String attribuutIdentCode = GeneratieUtil.upperTheFirstCharacter(javaVeld.getNaam());
            final Attribuut attribuut = geefAttribuutMetIdentCode(attributen, attribuutIdentCode);
            final boolean isBackreference = isBackreference(groep.getObjectType(), attribuut);
            if (attribuut != null && !"id".equalsIgnoreCase(attribuut.getIdentDb()) && !isBackreference) {
                gefilterdeAttributen.add(attribuut);
            }
        }
        genereerGetterVoorAlleAttributen(klasse, groep, gefilterdeAttributen);
    }


    /**
     * Geeft attribuut obv ident code.
     *
     * @param attributen         attributen
     * @param attribuutIdentCode attribuut ident code
     * @return attribuut als gevonden, anders null
     */
    private Attribuut geefAttribuutMetIdentCode(final List<Attribuut> attributen, final String attribuutIdentCode) {
        for (Attribuut attribuut : attributen) {
            if (attribuutIdentCode.equals(attribuut.getIdentCode())) {
                return attribuut;
            }
        }
        return null;
    }

    /**
     * Genereert een constructor waarbij alle velden als parameter kunnen worden meegegeven plus een ActieModel veld en een Formele- of Materiele historie
     * veld. (De groep)
     *
     * @param klasse de klasse waar de constructor in moet
     * @param groep  de groep waarvoor een Historie klasse wordt gegenereerd.
     * @return constructor met parameters
     */
    private Constructor genereerConstructorMetAlleParametersVoorKlasse(final JavaKlasse klasse, final Groep groep) {
        final Constructor constructor = super.genereerConstructorMetAlleParametersVoorKlasse(klasse);
        constructor.setJavaDoc("Constructor voor het initialiseren van alle attributen.");
        final StringBuilder consBody = new StringBuilder(constructor.getBody());
        String historieVeldGetterNaam = null;
        JavaType historieJavaType = null;

        if (kentFormeleHistorie(groep)) {
            historieJavaType = JavaType.FORMELE_HISTORIE;
            historieVeldGetterNaam = "getFormeleHistorie";
        } else if (kentMaterieleHistorie(groep)) {
            historieJavaType = JavaType.MATERIELE_HISTORIE;
            historieVeldGetterNaam = "getMaterieleHistorie";
        }

        final CategorieVerantwoording categorieVerantwoording = bepaalCategorieVerantoordingVoorGroep(groep);

        //We hebben een extra parameter nodig; ActieInhoud
        final JavaType verantwoordingJavaType;
        final String verantwoordingParamNaam;
        if (CategorieVerantwoording.DIENST == categorieVerantwoording) {
            verantwoordingJavaType = operationeelModelNaamgevingStrategie
                .getJavaTypeVoorElement(getBmrDao().getElement(ID_DIENST_LGM, ObjectType.class));
            verantwoordingParamNaam = "dienstInhoud";
        } else if (CategorieVerantwoording.ACTIE == categorieVerantwoording) {
            verantwoordingJavaType = operationeelModelNaamgevingStrategie
                .getJavaTypeVoorElement(getBmrDao().getElement(ID_ACTIE_LGM, ObjectType.class));
            verantwoordingParamNaam = "actieInhoud";
        } else {
            throw new IllegalStateException("Verantwoording categorie wordt niet ondersteund: "
                + groep.getVerantwoordingCategorie());
        }

        final JavaFunctieParameter verantwoordingInhoudParam = new JavaFunctieParameter(verantwoordingParamNaam,
            verantwoordingJavaType,
            "Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.");
        constructor.voegParameterToe(verantwoordingInhoudParam);

        //Voeg voor dienst verantwoording een losse parameter toe voor de tijdstip registratie:
        if (CategorieVerantwoording.DIENST == categorieVerantwoording) {
            constructor.voegParameterToe(new JavaFunctieParameter(
                "tijdstipRegistratie", JavaType.DATUMTIJD_ATTRIBUUT,
                "Moment dat dit historie record wordt aangemaakt via de dienst."));
        }


        //Zet de actieInhoud.
        consBody.append(String.format("setVerantwoordingInhoud(%1$s);%2$s",
            verantwoordingInhoudParam.getNaam(),
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        if (CategorieVerantwoording.ACTIE == categorieVerantwoording) {
            //Zet de tijdstip registratie naar de actie tijdstip registratie
            consBody.append(String.format("%1$s().setDatumTijdRegistratie(%2$s.getTijdstipRegistratie());%3$s",
                historieVeldGetterNaam,
                verantwoordingParamNaam,
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        } else if (CategorieVerantwoording.DIENST == categorieVerantwoording) {
            //Zet de tijdstip registratie naar de parameter tijdstip registratie
            consBody.append(String.format("%1$s().setDatumTijdRegistratie(tijdstipRegistratie);%2$s",
                historieVeldGetterNaam,
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        }

        if (kentMaterieleHistorie(groep)) {
            final String historieParamNaam = "historie";
            //We hebben een extra parameter nodig; Formele of MaterieleHistorie
            final JavaFunctieParameter historieParam =
                new JavaFunctieParameter(historieParamNaam, historieJavaType);
            historieParam.setJavaDoc("De groep uit een bericht");
            constructor.voegParameterToe(historieParam);

            //Zet de datumAanvangGeldigheid. getMaterieleHistorie().setDatumAanvangGeldigheid();
            consBody.append(String.format("%1$s().setDatumAanvangGeldigheid(%2$s.getDatumAanvangGeldigheid());%3$s",
                historieVeldGetterNaam,
                historieParam.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

            //Zet de datumEindeGeldigheid
            consBody.append(String.format("%1$s().setDatumEindeGeldigheid(%2$s.getDatumEindeGeldigheid());%3$s",
                historieVeldGetterNaam,
                historieParam.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        }
        constructor.setBody(consBody.toString());
        return constructor;
    }

    /**
     * Bouwt een constructor voor het kunnen kopieren van C/D laag entiteiten. (His_... klassen)
     *
     * @param klasse De klasse waar de constructor voor wordt gebouwd.
     * @return Een copy constructor.
     */
    private Constructor genereerCopyConstructorVoorHistoryKlasse(final JavaKlasse klasse) {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse);
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
        consParam.setJavaDoc("backrefence instantie.");
        constructor.voegParameterToe(consParam);

        final StringBuilder constructorBody = new StringBuilder();
        constructorBody.append(String.format("super(%1$s);%2$s", objectTypeParamNaam,
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        for (final JavaVeld javaVeld : klasse.getVelden()) {
            if (!"id".equalsIgnoreCase(javaVeld.getNaam())) {
                constructorBody.append(String.format("%1$s = %2$s.get%3$s();%4$s",
                    javaVeld.getNaam(),
                    objectTypeParamNaam,
                    GeneratieUtil.upperTheFirstCharacter(javaVeld.getNaam()),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            }
        }
        constructor.setBody(constructorBody.toString());
        return constructor;
    }

    /**
     * Genereert een copy constructor voor de historie klasse, om op basis van een A-Laag klasse een Historie C/D laag klasse te instantieren.
     *
     * @param klasse                      De historie klasse waar de copy constructor in moet.
     * @param operationeelModelObjectType Historie object type uit het BMR voor de groep.
     * @param logischModelObjectType      Objecttype uit logsch model waar de groep bij hoort.
     * @param groep                       De groep uit het logisch model in het BMR.
     * @return Copy constructor met als type parameter de klasse die overeenkomt met het object type uit de logische laag.
     */
    private Constructor genereerCopyConstructorMetGroepParameterVoorHistoryKlasse(
        final JavaKlasse klasse, final ObjectType operationeelModelObjectType,
        final ObjectType logischModelObjectType, final Groep groep)
    {
        final boolean isSubType = logischModelObjectType.getSuperType() != null;
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasse);
        constructor.setJavaDoc("Copy Constructor die op basis van een interface een C/D laag "
            + "klasse construeert.");

        final JavaType objectTypeParamType;
        ObjectType type = logischModelObjectType;

        if (isSubType) {
            type = logischModelObjectType.getFinaalSupertype();
        }

        if (isHisVolledigType(type)) {
            //we pakken de interface als reference type.
            objectTypeParamType = hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(type);
        } else {
            objectTypeParamType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(type);
        }

        final String objectTypeParamNaam = GeneratieUtil.lowerTheFirstCharacter(objectTypeParamType.getNaam());

        final boolean heeftFormeleHistorie = groep.getHistorieVastleggen() != null
            && 'F' == groep.getHistorieVastleggen();
        final boolean heeftMaterieleHistorie = groep.getHistorieVastleggen() != null
            && 'B' == groep.getHistorieVastleggen();

        //Eerste parameter is een A-laag object type
        final JavaFunctieParameter consParam1 = new JavaFunctieParameter(objectTypeParamNaam, objectTypeParamType);
        consParam1.setJavaDoc("instantie van A-laag klasse.");
        constructor.voegParameterToe(consParam1);

        //2de parameter is de groep.
        //Let op: als dit een identiteit groep is dan nemen we geen parameter op, want classes voor Identiteit groepen
        //worden niet gegenereerd, die worden platgeslagen in het operationeel model.
        final String groepParamNaam = "groep";
        if (!IDENTITEIT.equals(groep.getNaam())) {
            final JavaType paramType = logischModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
            final JavaFunctieParameter groepParam = new JavaFunctieParameter(groepParamNaam, paramType);
            groepParam.setJavaDoc(groepParamNaam);
            constructor.voegParameterToe(groepParam);
        }

        // 3de parameter is een historie data gerelateerd object, indien van toepassing, en enkel nodig voor materiele historie.
        final String historieParamNaam = "historie";
        if (heeftMaterieleHistorie) {
            final JavaFunctieParameter historieParam =
                new JavaFunctieParameter(historieParamNaam, JavaType.MATERIELE_HISTORIE);
            historieParam.setJavaDoc(historieParamNaam);
            constructor.voegParameterToe(historieParam);
        }

        final CategorieVerantwoording categorieVerantwoording = bepaalCategorieVerantoordingVoorGroep(groep);

        //Genereer een extra parameter voor de ActieInhoud/DienstInhoud, daar we deze altijd willen zetten als we een nieuwe
        //historie entiteit maken.
        final JavaType verantwoordingJavaType;
        final String verantwoordingParamNaam;
        if (CategorieVerantwoording.DIENST == categorieVerantwoording) {
            verantwoordingJavaType = operationeelModelNaamgevingStrategie
                .getJavaTypeVoorElement(getBmrDao().getElement(ID_DIENST_LGM, ObjectType.class));
            verantwoordingParamNaam = "dienstInhoud";
        } else if (CategorieVerantwoording.ACTIE == categorieVerantwoording) {
            verantwoordingJavaType = operationeelModelNaamgevingStrategie
                .getJavaTypeVoorElement(getBmrDao().getElement(ID_ACTIE_LGM, ObjectType.class));
            verantwoordingParamNaam = "actieInhoud";
        } else {
            throw new IllegalStateException("Verantwoording categorie wordt niet ondersteund: "
                + groep.getVerantwoordingCategorie());
        }

        final JavaFunctieParameter actieInhoudParam = new JavaFunctieParameter(verantwoordingParamNaam,
            verantwoordingJavaType,
            "Actie inhoud; de actie die leidt tot dit nieuwe record.");
        constructor.voegParameterToe(actieInhoudParam);

        //Losse tijdstip registratie parameter voor dienst verantwoording:
        if (CategorieVerantwoording.DIENST == categorieVerantwoording) {
            constructor.voegParameterToe(new JavaFunctieParameter(
                "tijdstipRegistratie", JavaType.DATUMTIJD_ATTRIBUUT,
                "Moment dat dit historie record wordt aangemaakt via de dienst."));
        }

        final String constructorBody = genereerConstructorBodyVoorCopyConstructorMetGroepVoorHistoryKlasse(
            operationeelModelObjectType, logischModelObjectType,
            objectTypeParamNaam, groepParamNaam, historieParamNaam, actieInhoudParam,
            heeftFormeleHistorie, heeftMaterieleHistorie, categorieVerantwoording);

        constructor.setBody(constructorBody);
        return constructor;
    }

    /**
     * Bepaal categorie verantwoording van groep.
     *
     * @param groep de groep
     * @return CategorieVerantwoording.DIENST of CategorieVerantwoording.ACTIE
     */
    private CategorieVerantwoording bepaalCategorieVerantoordingVoorGroep(final Groep groep) {
        CategorieVerantwoording categorieVerantwoording = null;
        if (CategorieVerantwoording.DIENST.getCode() == groep.getVerantwoordingCategorie()) {
            categorieVerantwoording = CategorieVerantwoording.DIENST;
            // TODO dit is tijdelijke oplossing ivm BMR-42 wijzigingen in POC Bijhouding
        } else if (CategorieVerantwoording.ACTIE.getCode() == groep.getVerantwoordingCategorie() || 'G' == groep.getVerantwoordingCategorie()) {
            categorieVerantwoording = CategorieVerantwoording.ACTIE;
        }
        return categorieVerantwoording;
    }

    /**
     * Genereer de body van de copy constructor voor het maken van een history object vanuit een groep.
     *
     * @param operationeelModelObjectType het object type in het operationele model
     * @param logischModelObjectType      het object type in het logische model
     * @param objectTypeParamNaam         de parameter naam van het object type
     * @param groepParamNaam              de parameter naam van de groep
     * @param historieParamNaam           de parameter naam van het historie object
     * @param actieInhoudParam            parameter voor de actie inhoud
     * @param heeftFormeleHistorie        of de groep formele historie heeft
     * @param heeftMaterieleHistorie      of de groep materiele historie heeft
     * @param categorieVerantwoording     categorie verantwoording van de groep
     * @return de Java body van de copy constructor
     */
    private String genereerConstructorBodyVoorCopyConstructorMetGroepVoorHistoryKlasse(
        final ObjectType operationeelModelObjectType, final ObjectType logischModelObjectType,
        final String objectTypeParamNaam, final String groepParamNaam, final String historieParamNaam,
        final JavaFunctieParameter actieInhoudParam, final boolean heeftFormeleHistorie,
        final boolean heeftMaterieleHistorie, final CategorieVerantwoording categorieVerantwoording)
    {
        final StringBuilder constructorBody = new StringBuilder();
        final List<Attribuut> attributenVanHisObjectType = getBmrDao().getAttributenVanObjectType(operationeelModelObjectType);
        for (final Attribuut attribuut : attributenVanHisObjectType) {
            final GeneriekElement finaalsupertype;
            if (this.isSubtype(logischModelObjectType)) {
                finaalsupertype = logischModelObjectType.getFinaalSupertype();
            } else {
                finaalsupertype = logischModelObjectType;
            }
            if (!isHistorieGerelateerdAttribuut(attribuut)
                && !"ID".equalsIgnoreCase(attribuut.getIdentCode()))
            {
                if ("OT".equals(attribuut.getType().getSoortElement().getCode())
                    && 'D' == attribuut.getType().getSoortInhoud())
                {
                    // Er zijn twee mogelijkheden:
                    if (attribuut.getIdentCode().equalsIgnoreCase(finaalsupertype.getIdentCode())) {
                        // Het is de backreference oftewel het logisch model objecttype (A-Laag back reference)
                        // Zoals Persoon in HisPersAfgeleidAdministratief.
                        constructorBody.append(
                            String.format("this.%1$s = %2$s;%3$s",
                                GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                                objectTypeParamNaam,
                                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                    } else {
                        // Het is een ander objecttype, zoals administratieveHandeling in HisPersoonAfgeleidAdministratief.
                        // Roep de copy constructor van het objecttype aan:
                        constructorBody.append(String.format("if (%1$s.get%2$s() != null) {",
                            groepParamNaam, attribuut.getIdentCode()));
                        constructorBody.append(
                            String.format("this.%1$s = new %2$s(%3$s.get%4$s());",
                                GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType()).getNaam(),
                                groepParamNaam,
                                attribuut.getIdentCode())
                        );
                        constructorBody.append("}");
                    }
                } else {
                    // Voor relatie is er niet altijd een standaardgroep, hierdoor kan de groep NULL zijn. Dit dient dus afgevangen te worden
                    // zodat er geen nullpointerexception optreedt. Zie AbstractHisRelatieModel.java.
                    final boolean isRelatieStandaardGroep = "relatie".equals(StringUtils.lowerCase(logischModelObjectType.getNaam()))
                        && "groep".equals(groepParamNaam);

                    if (isRelatieStandaardGroep) {
                        constructorBody.append(String.format("if (%s != null) {", groepParamNaam));
                    }

                    constructorBody.append(
                        String.format("this.%1$s = %2$s.get%3$s();%4$s",
                            GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                            groepParamNaam,
                            attribuut.getIdentCode(),
                            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde())
                    );

                    if (isRelatieStandaardGroep) {
                        constructorBody.append("}");
                    }
                }
            }
        }

        // Zet de waarde van de formele en materiele historie velden.
        String getterNaam = null;
        final List<String> methodeNamen = new ArrayList<>();
        if (heeftFormeleHistorie) {
            getterNaam = "getFormeleHistorie";
        } else if (heeftMaterieleHistorie) {
            getterNaam = "getMaterieleHistorie";
            methodeNamen.add("DatumAanvangGeldigheid");
            methodeNamen.add("DatumEindeGeldigheid");
        }

        for (final String methodeNaam : methodeNamen) {
            constructorBody.append(String.format("%1$s().set%2$s(%3$s.get%2$s());%4$s", getterNaam, methodeNaam,
                historieParamNaam, JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        }

        //Nu nog de losse actieInhoud parameter
        constructorBody.append(String.format("setVerantwoordingInhoud(%1$s);%2$s",
            actieInhoudParam.getNaam(),
            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        if (CategorieVerantwoording.DIENST == categorieVerantwoording) {
            constructorBody.append(String.format("%1$s().set%2$s(tijdstipRegistratie);%3$s",
                getterNaam, "DatumTijdRegistratie",
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        } else if (CategorieVerantwoording.ACTIE == categorieVerantwoording) {
            //TijdstipRegistratie moet gezet worden op de tijdstip registratie van de actie.
            constructorBody.append(String.format("%1$s().set%2$s(%3$s.getTijdstipRegistratie());%4$s",
                getterNaam, "DatumTijdRegistratie",
                actieInhoudParam.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        }

        return constructorBody.toString();
    }

    /**
     * Genereert een java veld voor een attribuut van een historie object type.
     *
     * @param klasse                      Java klasse waarin het veld moet worden opgenomen.
     * @param logischModelObjectType      Objecttype uit het logisch model.
     * @param operationeelModelObjectType Historie object type.
     * @param attribuut                   Het attribuut waarvoor een veld wordt gegenereerd.
     * @param isBackreference             of de referentie 'terug' naar het logische model type is.
     * @return Java veld voor attribuut.
     */
    private JavaVeld genereerVeldVoorHistorieAttribuut(final JavaKlasse klasse, final ObjectType logischModelObjectType,
        final ObjectType operationeelModelObjectType,
        final Attribuut attribuut, final boolean isBackreference)
    {
        final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
        final JavaType javaType;
        boolean targetEntityAnnotatieNodig = false;
        JavaType targetEntityType = null;
        if (isIdAttribuut(attribuut)) {
            final AttribuutType attribuutType = getBmrDao().getElement(
                attribuut.getType().getId(), AttribuutType.class);
            javaType = this.getJavaTypeVoorAttribuutType(attribuutType);
        } else {
            if (attribuut.getType().getSoortInhoud() != null && 'D' == attribuut.getType().getSoortInhoud()) {
                GeneriekElement type = logischModelObjectType;
                // Als het type een subtype is, neem dan het finaal supertype als type.
                // Dit, omdat subtypen geen hisVolledig klasse kennen.
                if (isSubtype(logischModelObjectType)) {
                    type = logischModelObjectType.getFinaalSupertype();
                }
                final Attribuut logischeLaagAttribuut = this.getBmrDao().getElementVoorSyncIdVanLaag(
                    attribuut.getOrgSyncid(), BmrLaag.LOGISCH, Attribuut.class);
                if (logischeLaagAttribuut == null
                    || logischeLaagAttribuut.getGroep().getNaam().equals(IDENTITEIT))
                {
                    // Hier betreft het een back reference in de Identiteit groep.
                    if (isHisVolledigType(type)) {
                        javaType = hisVolledigInterfaceModelNaamgevingStrategie.getJavaTypeVoorElement(type);
                        targetEntityAnnotatieNodig = true;
                        targetEntityType = hisVolledigModelNaamgevingStrategie.getJavaTypeVoorElement(type);
                    } else if (logischeLaagAttribuut == null) {
                        javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(type);
                    } else {
                        javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                            logischeLaagAttribuut.getType());
                    }
                } else {
                    // Dynamische types die niet in de identiteit groep zitten, krijgen
                    // het operationeel model type. (Bijv. AdministratieveHandeling in HisPersoonAfgeleidAdministratief)
                    javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                        logischeLaagAttribuut.getType());
                }
            } else {
                // Als het attribuut verwijst naar een stamgegeven en het historie objecttype,
                // historie van een stamgeven is, (bijv HisPartij) dan willen we geen wrapper attribuut gebruiken als
                // type van het veld maar gewoon direct het stamgegeven.
                if (BmrElementSoort.OBJECTTYPE.hoortBijCode(attribuut.getType().getSoortElement().getCode())
                    && BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == attribuut.getType().getSoortInhoud()
                    && BmrSoortInhoud.DYNAMISCH_STAMGEGEVEN.getCode() == operationeelModelObjectType.getSoortInhoud())
                {
                    javaType = new AlgemeneNaamgevingStrategie().getJavaTypeVoorElement(attribuut.getType());
                } else {
                    javaType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                }
            }
        }

        final JavaVeld veld = new JavaVeld(javaType, veldNaam);
        klasse.voegVeldToe(veld);
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        genereerGetterJavaDoc(getter, operationeelModelObjectType, attribuut);

        //Override en AttribuutAccessor annotaties: Behoort tot het interfaces model en is geen backreference type.
        if (behoortTotJavaLogischModel(logischModelObjectType)
            && behoortTotJavaLogischModel(attribuut)
            && !isBackreference)
        {
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
            //AttribuutAccessor annotatie, registreert het dbobjectid van het attribuut dat geretourneerd wordt door de
            //getter.
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.ATTRIBUUT_ACCESSOR,
                new AnnotatieWaardeParameter(
                    "dbObjectId", String.valueOf(attribuut.getSyncid()),
                    true)));
        }
        klasse.voegGetterToe(getter);

        // De referentie 'terug' naar het object type moet niet worden opgenomen in json.
        final boolean isJsonProperty = !isBackreference;
        annoteerAttribuutVeld(operationeelModelObjectType, attribuut, veld,
            false, isJsonProperty, false, isBackreference);

        //TargetEntity annotatie indien nodig, tevens backreference.
        if (targetEntityAnnotatieNodig) {
            final JavaAnnotatie manyToOneAnnotatie = veld.getAnnotatieVanType(JavaType.MANY_TO_ONE);
            manyToOneAnnotatie.voegParameterToe(new AnnotatieWaardeParameter(
                "targetEntity", targetEntityType.getNaam() + ".class", true));
            klasse.voegExtraImportsToe(targetEntityType);
            // Een backreference naar de his volledig dient een JSON backreference annotatie te hebben.
            if (isBackreference) {
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_BACKREFERENCE));
            }
        }
        return veld;
    }

    /**
     * Genereert de groepen met JPA annotaties voor ORM.
     *
     * @return Lijst van java klassen die groepen representeren uit het BMR.
     */
    private List<JavaKlasse> genereerOperationeleGroepen() {
        final List<JavaKlasse> klassenVoorGroepen = new ArrayList<>();
        final List<Groep> groepen = getBmrDao().getGroepen();

        for (final Groep groep : groepen) {
            if (this.behoortTotJavaOperationeelModel(groep) && !IDENTITEIT.equals(groep.getNaam())) {
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
            clazz.voegSuperInterfaceToe(logischModelNaamgevingStrategie.getJavaTypeVoorElement(groep));
        }

        genereerAttributenMetAccessMethodes(groep, clazz);
        clazz.voegConstructorToe(genereerDefaultLegeConstructor(clazz));
        if (clazz.getVelden().size() > 0) {
            // constructor met attributen heeft alleen zin als er attributen zijn
            clazz.voegConstructorToe(genereerConstructorMetAttributen(groep, clazz));
        }

        //Alleen als de groep EN het objecttype tot het bericht java model behoren EN er is een logische interface voor
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
     * Bepaal of er voor dit element setters gegenereerd moeten worden voor de attributen. Voor de objecten BerichtModel en BerichtStuurgegevensgroepModel
     * van het BER schema worden voor de attributen setters gegenereerd omdat deze gegevens tijdens instantiatie van het object nog niet voorhanden zijn.
     *
     * @param element het element
     * @return true wanneer er voor dit objectType setters gegenereerd moeten worden
     */
    private boolean isGroepOfObjecttypeWaarvoorSettersGegenereerdMoetenWorden(final GeneriekElement element) {
        return (ID_BER_SCHEMA == element.getSchema().getId()
            && (ID_BERICHT_LGM == element.getId() || ID_STUURGEGEVENS == element.getId()))
            || ID_STANDAARD_PERSCACHE == element.getId()
            || ID_STANDAARD_BERICHT == element.getId();
    }

    /**
     * Voeg setters toe voor de attributen van dit element.
     *
     * @param clazz     de klasse waaraan de setters moeten worden toegevoegd
     * @param attribuut het attribuut waarvoor de setters moeten worden toegevoegd
     * @param groep     de groep waaronder dit attribuut valt
     * @param veld      het JavaVeld waarvoor een setter gegenereerd moet worden
     */
    private void genereerSetterVoorAttribuut(final JavaKlasse clazz, final Attribuut attribuut, final Groep groep,
        final JavaVeld veld)
    {
        final JavaMutatorFunctie setter = new JavaMutatorFunctie(veld);
        if (groep != null) {
            genereerSetterJavaDoc(setter, groep, attribuut);
        } else {
            genereerSetterJavaDoc(setter, attribuut.getObjectType(), attribuut);
        }
        clazz.voegSetterToe(setter);
    }

    /**
     * Genereert een copy constructor om van een groep in bericht model een goep uit het operationeel model te instantieren.
     *
     * @param groep de groep.
     * @param clazz class waar de copy constructor in moet.
     * @return Copy constructor.
     */
    private Constructor genereerGroepCopyConstructorVoorOmzettingVanuitBerichtModel(final Groep groep,
        final JavaKlasse clazz)
    {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz);
        final JavaType paramType = logischModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
        final String paramNaam = GeneratieUtil.lowerTheFirstCharacter(paramType.getNaam());
        final JavaFunctieParameter consParam = new JavaFunctieParameter(paramNaam, paramType);
        consParam.setJavaDoc("te kopieren groep.");
        constructor.voegParameterToe(consParam);

        //Voor elk attribuut dat in de interface zit genereren we een regel code die dat attribuut kopieert in het
        //huidige object.
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);

        final StringBuilder constructorBody = new StringBuilder();
        for (final Attribuut attribuut : attributen) {
            //Attributen die verwijzen naar een object type binnen de groep worden niet gekopieerd.
            //Dit is namelijk het geval bij PersoonInschrijving groep. (Volgende- en vorige persoon)
            //Echter stamgegevens objecttypen worden wel gekopieerd.
            if (behoortTotJavaLogischModel(attribuut) && behoortTotJavaOperationeelModel(attribuut)
                && !isDatabaseKnipAttribuut(groep.getObjectType(), attribuut)
                &&
                ("OT".equals(attribuut.getType().getSoortElement().getCode())
                    && BmrSoortInhoud.DYNAMISCH_OBJECT_TYPE.getCode() != attribuut.getType().getSoortInhoud()
                    || "AT".equals(attribuut.getType().getSoortElement().getCode())))
            {
                final String copyCode = "this.%1$s = %2$s.get%3$s();%4$s";
                constructorBody.append(
                    String.format(copyCode,
                        GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                        paramNaam,
                        attribuut.getIdentCode(),
                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            }
        }

        //Kopieer velden die zowel in het bericht model zitten als in het operationeel model zitten. Die kunnen we ook
        //gewoon kopieren. LET OP: Velden die we hierboven al gekopieerd hebben nemen we niet nog een keer mee!
        final JavaType downCastType = berichtModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
        final String downCastVariabele = GeneratieUtil.lowerTheFirstCharacter(downCastType.getNaam());
        for (final Attribuut attribuut : attributen) {
            if (behoortTotJavaBerichtModel(attribuut) && behoortTotJavaOperationeelModel(groep)
                && !behoortTotJavaLogischModel(attribuut))
            {
                if (constructorBody.indexOf("instanceof") == -1) {
                    constructorBody.append("if (").append(paramNaam).append(" instanceof ")
                        .append(downCastType).append(") {").append(String.format("%s",
                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                    constructorBody.append("final ")
                        .append(downCastType)
                        .append(" ")
                        .append(downCastVariabele)
                        .append(" = ")
                        .append(String.format("%s",
                            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()))
                        .append("(").append(downCastType).append(") ")
                        .append(paramNaam)
                        .append(String.format(";%s",
                            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                }
                //Attributen van het type object type kopieren we niet! Er is maar één groep die dit heeft;
                //PersoonInschrijving. (vorige- en volgende persoon)
                if (!"OT".equals(attribuut.getType().getSoortElement().getCode())) {
                    final String copyCode = "this.%1$s = %2$s.get%3$s();%4$s";
                    constructorBody.append(
                        String.format(copyCode,
                            GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                            downCastVariabele,
                            attribuut.getIdentCode(),
                            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
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
     * Voegt een constructor toe met alle velden als parameters, waarbij direct alle velden in de constructor worden gezet naar de waardes van de
     * parameters.
     *
     * @param groep de groep waarvoor de constructor moet worden aangemaakt.
     * @param clazz de Java klasse die de groep representeert en waarvoor de constructor moet worden aangemaakt.
     * @return Constructor.
     */
    private Constructor genereerConstructorMetAttributen(final Groep groep, final JavaKlasse clazz) {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz);
        final StringBuilder constructorBody = new StringBuilder();

        for (final JavaVeld veld : clazz.getVelden()) {
            constructor.getParameters().add(new JavaFunctieParameter(veld.getNaam(), veld.getType(),
                String.format("%1$s van %2$s.", veld.getNaam(), groep.getNaam())));
            constructorBody.append(String.format("this.%1$s = %1$s;%2$s", veld.getNaam(),
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        }
        constructor.setJavaDoc("Basis constructor die direct alle velden instantieert.");
        constructor.setBody(constructorBody.toString());
        return constructor;
    }

    /**
     * Genereert velden voor alle attributen die horen bij de groep inclusief de getters, maar exclusief setters daar de groepen immutable zijn.
     *
     * @param groep De groep waarvoor attributen moeten worden gebouwd.
     * @param clazz De Java klasse waar de attributen en getters in moeten.
     */
    protected void genereerAttributenMetAccessMethodes(final Groep groep, final JavaKlasse clazz) {
        //Haal attributen van groep op.
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (final Attribuut attribuut : attributen) {
            if (behoortTotJavaOperationeelModel(attribuut)) {
                final JavaVeld attribuutVeldInGroep;
                if (isDatabaseKnipAttribuut(groep.getObjectType(), attribuut)) {
                    attribuutVeldInGroep = genereerDatabaseKnipVeld(attribuut);

                    //DAtabase knip velden krijgen ook een setter, want je moet de Id uit de andere database zelf kunnen zetten.
                    final JavaMutatorFunctie setter = new JavaMutatorFunctie(attribuutVeldInGroep);
                    genereerSetterJavaDoc(setter, groep, attribuut);
                    clazz.voegSetterToe(setter);
                } else {
                    final JavaType javaTypeVoorVeld =
                        operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
                    final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                    attribuutVeldInGroep = new JavaVeld(javaTypeVoorVeld, veldNaam);

                    // Alleen voor de objecten Stuurgegevens en Bericht genereren we setters voor de attributen
                    // Deze setters zijn nodig omdat niet alle gegevens voorhanden zijn tijdens initialisatie van het object
                    if (isGroepOfObjecttypeWaarvoorSettersGegenereerdMoetenWorden(groep)) {
                        genereerSetterVoorAttribuut(clazz, attribuut, groep, attribuutVeldInGroep);
                    }
                }

                //Persistency annotaties
                annoteerAttribuutVeld(groep.getObjectType(), attribuut, attribuutVeldInGroep,
                    false, isJsonProperty(attribuut), false);

                clazz.voegVeldToe(attribuutVeldInGroep);

                //Maak een accessor aan oftewel een getter.
                final JavaAccessorFunctie getter = new JavaAccessorFunctie(attribuutVeldInGroep);
                if (behoortTotJavaLogischModel(attribuut) && !isDatabaseKnipAttribuut(groep.getObjectType(), attribuut)) {
                    getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
                } else {
                    genereerGetterJavaDoc(getter, groep, attribuut);
                }
                clazz.voegGetterToe(getter);
            }
        }

        // Genereert een getter waarin een lijst van alle attributen wordt teruggegeven.
        genereerGetterVoorAlleAttributen(clazz, groep, attributen);
    }

    /**
     * Genereert een getter waarmee alle attributen van de groep kunnen worden opgehaald. Deze getter wordt aan de klasse toegevoegd.
     *
     * @param clazz      klasse van groep
     * @param groep      groep
     * @param attributen lijst van attributen
     */
    private void genereerGetterVoorAlleAttributen(final JavaKlasse clazz, final Groep groep, final List<Attribuut> attributen) {
        final JavaFunctie attributenGetter = new JavaFunctie(JavaAccessModifier.PUBLIC,
            new JavaType(JavaType.LIST, SymbolTableConstants.ATTRIBUUT_JAVATYPE),
            "getAttributen", "Lijst met attributen van de groep.");
        attributenGetter.setFinal(true);
        attributenGetter.setJavaDoc("Geeft alle attributen van de groep met uitzondering van attributen die null zijn.");

        final StringBuilder stringBuilder = new StringBuilder("final List<Attribuut> attributen = new ArrayList<>();");
        for (final Attribuut attribuut : attributen) {
            // Sommige groepen bevatten attributen die niet de Attribuut-interface implementeren
            final JavaType javaTypeVoorElement = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
            if (javaTypeVoorElement.getNaam().toLowerCase().endsWith("attribuut")
                && behoortTotJavaOperationeelModel(attribuut)
                && !isDatabaseKnipAttribuut(groep.getObjectType(), attribuut))
            {
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                stringBuilder.append("if (");
                stringBuilder.append(veldNaam);
                stringBuilder.append(" != null) {\n");
                stringBuilder.append("attributen.add(");
                stringBuilder.append(veldNaam);
                stringBuilder.append(");\n");
                stringBuilder.append("}\n");
            }
        }
        stringBuilder.append("return attributen;");
        attributenGetter.setBody(stringBuilder.toString());

        clazz.voegExtraImportsToe(JavaType.LIST);
        clazz.voegExtraImportsToe(JavaType.ARRAY_LIST);

        clazz.voegFunctieToe(attributenGetter);
    }

    /**
     * Genereert een getter waarmee alle attributen van de groep kunnen worden opgehaald. Deze getter wordt aan de klasse toegevoegd.
     *
     * @param clazz
     * @param obectType
     * @param groepen
     */
    private void genereerGetterVoorAlleGroepen(final JavaKlasse clazz, final ObjectType obectType, final List<Groep> groepen) {
        final JavaFunctie groepenGetter = new JavaFunctie(JavaAccessModifier.PUBLIC,
            new JavaType(JavaType.LIST, SymbolTableConstants.GROEP_JAVATYPE),
            "getGroepen", "Lijst met groepen.");
        groepenGetter.setFinal(false);
        groepenGetter.setJavaDoc("Geeft alle groepen van de object met uitzondering van groepen die null zijn.");
        final StringBuilder stringBuilder = new StringBuilder("final List<Groep> groepen = new ArrayList<>();");
        for (final Groep groep : groepen) {
            final JavaType javaTypeVoorGroepVeld = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep);
            final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode());
            final JavaVeld groepVeld = new JavaVeld(javaTypeVoorGroepVeld, veldNaam);

            if (behoortTotJavaOperationeelModel(groep) && !isIdentiteitGroep(groep)) {
                stringBuilder.append("if (");
                stringBuilder.append(veldNaam);
                stringBuilder.append(" != null) {\n");
                stringBuilder.append("groepen.add(");
                stringBuilder.append(veldNaam);
                stringBuilder.append(");\n");
                stringBuilder.append("}\n");
            }
        }
        stringBuilder.append("return groepen;");
        groepenGetter.setBody(stringBuilder.toString());

        clazz.voegExtraImportsToe(JavaType.LIST);
        clazz.voegExtraImportsToe(JavaType.ARRAY_LIST);

        clazz.voegFunctieToe(groepenGetter);
    }

    /**
     * Genereert een getter waarmee alle attributen van de groep kunnen worden opgehaald. Deze getter wordt aan de klasse toegevoegd.
     *
     * @param clazz      klasse van groep
     * @param attributen lijst van attributen
     */
    private void genereerGetterVoorAlleAttributen(final JavaKlasse clazz, final List<Attribuut> attributen, ObjectType objectType) {
        final JavaFunctie attributenGetter = new JavaFunctie(JavaAccessModifier.PUBLIC,
            new JavaType(JavaType.LIST, SymbolTableConstants.ATTRIBUUT_JAVATYPE),
            "getAttributen", "Lijst met attributen van het object.");
        attributenGetter.setFinal(false);
        attributenGetter.setJavaDoc("Geeft alle attributen van de groep met uitzondering van attributen die null zijn.");


        final List<Attribuut> gefilterdeAttributen = new ArrayList<>();

        final StringBuilder stringBuilder = new StringBuilder("final List<Attribuut> attributen = new ArrayList<>();");
        for (final Attribuut attribuut : attributen) {
            // Sommige groepen bevatten attributen die niet de Attribuut-interface implementeren

            final JavaType javaTypeVoorElement = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
            final Groep attrGroep = attribuut.getGroep();
            //alleen identiteits groep attributen voor de object getAttributen
            if (attrGroep.getNaam().toLowerCase().contains("identiteit") && !isIdAttribuut(attribuut) && javaTypeVoorElement.getNaam().toLowerCase()
                .endsWith
                    ("attribuut")
                && behoortTotJavaOperationeelModel(attribuut) && !isBackreference(objectType, attribuut) && !"id".equalsIgnoreCase(attribuut.getIdentDb()))
            {
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                stringBuilder.append("if (");
                stringBuilder.append(veldNaam);
                stringBuilder.append(" != null) {\n");
                stringBuilder.append("attributen.add(");
                stringBuilder.append(veldNaam);
                stringBuilder.append(");\n");
                stringBuilder.append("}\n");
            }
        }
        stringBuilder.append("return attributen;");
        attributenGetter.setBody(stringBuilder.toString());

        clazz.voegExtraImportsToe(JavaType.LIST);
        clazz.voegExtraImportsToe(JavaType.ARRAY_LIST);

        clazz.voegFunctieToe(attributenGetter);
    }

    /**
     * Genereert java klassen die object typen representeren.
     *
     * @return Lijst van Java klassen.
     */
    private List<JavaKlasse> genereerOperationeleObjectTypen() {
        final List<JavaKlasse> klassenVoorObjectTypen = new ArrayList<>();
        final List<ObjectType> objectTypen = getBmrDao().getObjectTypen();
        for (final ObjectType objectType : objectTypen) {
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
        final Attribuut discrAttribuut = this.bepaalDiscriminatorAttribuut(objectType);

        if (heeftSupertype(objectType)) {
            clazz.setExtendsFrom(
                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(objectType.getSuperType()));
        } else {
            clazz.setExtendsFrom(JavaType.ABSTRACT_DYNAMISCH_OBJECT);
        }

        // Als een klasse subtypes heeft, oftewel zelf geen finaal subtype is, dan moet hij abstract zijn.
        if (heeftSubtypen(objectType)) {
            clazz.setAbstractClass(true);
        }
        // Niet hierarchische en de blaadjes van een hierarchie zijn een entity.
        if (isNietHierarchischType(objectType) || isFinaalSubtype(objectType)) {
            clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.ENTITY));
        }

        // Indien het een verantwoordings entiteit is, voeg een tagging interface toe.
        if (VERANTWOORDINGS_ENTITEITEN.contains(objectType.getId())) {
            clazz.voegSuperInterfaceToe(JavaType.VERANTWOORDINGS_ENTITEIT);
        }

        //Let op, de super interface oftewel de LGM interface moet enkel ge-set worden indien dit object type het
        //vlaggetje in_lgm op true heeft staan.
        if (behoortTotJavaLogischModel(objectType)) {
            clazz.voegSuperInterfaceToe(logischModelNaamgevingStrategie.getJavaTypeVoorElement(objectType));
        }

        // Model identificeerbaar interface toevoegen
        final JavaType idJavaType = getJavaTypeVoorIdVeld(objectType);
        if (idJavaType != null && idJavaType.isNumeriek()) {
            clazz.voegSuperInterfaceToe(new JavaType(JavaType.MODEL_IDENTIFICEERBAAR, idJavaType));
        }

        // Indien Object een volgnummer bevat, dan dit aangeven met juiste interface
        if (bevatVolgnummer(objectType)) {
            clazz.voegSuperInterfaceToe(JavaType.VOLGNUMMER_BEVATTEND);
        }

        if (isTussenliggendType(objectType)) {
            clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.MAPPED_SUPER_CLASS));
        } else if (isFinaalSubtype(objectType)) {
            final Tuple discriminatorTuple = bepaalDiscriminatorTuple(objectType);
            clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.DISCRIMINATOR_VALUE,
                new AnnotatieWaardeParameter("value", discriminatorTuple.getVolgnummerT().toString())));
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
        final Constructor defaultLegeConstructor = genereerDefaultLegeConstructor(clazz);
        clazz.voegConstructorToe(defaultLegeConstructor);
        // Voeg constructor toe met parameters voor alle identiteits velden.
        // Let op: deze call moet na die voor de lege constructor komen,
        // omdat die eventueel weer verwijderd wordt.
        Constructor constructor = genereerConstructorMetIdentiteitParameters(
            clazz, objectType, operationeelModelNaamgevingStrategie);
        // TANGO-527: Nog een hack: de constructor moet ook aangepast worden.
        if (objectType.getId() == ID_OT_PERSOON_CACHE) {
            // Custom override: gebruik persoonId, type Integer als parameter.
            constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz);
            constructor.setJavaDoc("Standaard constructor die direct alle identificerende "
                + "attributen instantieert of doorgeeft.");
            constructor.voegParameterToe(new JavaFunctieParameter("persoonId",
                JavaType.INTEGER, "persoon id van Persoon cache."));
            constructor.setBody("this();\nthis.persoonId = persoonId;");
        }

        if (constructor != null) {
            if (constructor.isConstructorZonderArgumenten()) {
                // gooi eerder gegenereerde default constructor weg
                clazz.getConstructoren().remove(defaultLegeConstructor);
            }
            clazz.voegConstructorToe(constructor);
        }

        //Voeg copy constructor toe om van bericht model een operationeel model object type te instantieren.
        if (behoortTotJavaBerichtModel(objectType)
            && behoortTotJavaLogischModel(objectType))
        {
            genereerObjectTypeCopyConstructorVoorOmzettingVanuitBerichtModel(clazz, objectType);
        }

        //Genereer velden voor groepen.
        final List<Groep> groepenVoorObjectType = getBmrDao().getGroepenVoorObjectType(objectType);
        for (final Groep groep : groepenVoorObjectType) {
            if (behoortTotJavaOperationeelModel(groep)) {
                // De identiteit groep wordt hier plat geslagen.
                if (isIdentiteitGroep(groep)) {
                    genereerVeldenVoorIdentiteitGroep(clazz, objectType, groep, discrAttribuut);
                } else {
                    genereerVeldVoorGroep(clazz, objectType, groep, false);
                }
            }
        }
        genereerGetterVoorAlleGroepen(clazz, objectType, groepenVoorObjectType);

        genereerGetterVoorAlleAttributen(clazz, getBmrDao().getAttributenVanObjectType(objectType), objectType);

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

        if (objectType.getId() == ID_PERSOON_LGM) {
            voegGettersToeVoorIndicaties(clazz, operationeelModelNaamgevingStrategie, true);
        }

        return clazz;
    }

    /**
     * Genereert een copy constructor voor een object type klasse om van het bericht model een operationeel model instantie te maken.
     *
     * @param clazz      De klasse waar de copy constructor in moet.
     * @param objectType Object type dat bij de klasse hoort.
     */
    private void genereerObjectTypeCopyConstructorVoorOmzettingVanuitBerichtModel(final JavaKlasse clazz,
        final ObjectType objectType)
    {
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, clazz);
        constructor.setJavaDoc("Copy constructor om vanuit het bericht model een instantie van het "
            + "operationeel model te maken. ");
        // Constructor parameter.
        final JavaType consParamType = logischModelNaamgevingStrategie.getJavaTypeVoorElement(objectType);
        final String consParamNaam = GeneratieUtil.lowerTheFirstCharacter(consParamType.getNaam());
        final JavaFunctieParameter consParam = new JavaFunctieParameter(consParamNaam, consParamType);
        consParam.setJavaDoc("Te kopieren object type.");
        constructor.voegParameterToe(consParam);
        boolean kopieObjectTypeGebruiktInBody = false;

        final List<Groep> groepen = new ArrayList<>();

        // Indien we te maken hebben met een subtype dan moeten we attributen van de identiteit groep van het
        // supertype opnemen als constructor parameters.
        if (isSubtype(objectType)) {
            final ObjectType superObjectType = objectType.getFinaalSupertype();
            final List<Groep> groepenSupertype = getBmrDao().getGroepenVoorObjectType(superObjectType);
            for (final Groep groep : groepenSupertype) {
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
            constructorBody.append(String.format("this();%s",
                JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        }

        for (final Groep groep : groepen) {
            if (behoortTotJavaOperationeelModel(groep)) {
                if (IDENTITEIT.equals(groep.getNaam())) {
                    //Voeg parameters toe voor identiteit groep attributen.
                    final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
                    for (final Attribuut attribuut : attributen) {
                        if (behoortTotJavaLogischModel(attribuut) && behoortTotJavaOperationeelModel(attribuut)) {
                            //Alleen Dynamische object typen worden als parameter opgenomen.
                            if ("OT".equals(attribuut.getType().getSoortElement().getCode())
                                && 'D' == attribuut.getType().getSoortInhoud())
                            {
                                final String paramNaam;
                                final JavaType paramType;
                                if (isDatabaseKnipAttribuut(objectType, attribuut)) {
                                    final JavaVeld veld = genereerDatabaseKnipVeld(attribuut);
                                    paramNaam = veld.getNaam();
                                    paramType = veld.getType();
                                } else {
                                    if ("OT".equals(attribuut.getType().getSoortElement().getCode())) {
                                        paramType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(
                                            attribuut.getType());
                                    } else {
                                        paramType = wrapperNaamgevingStrategie
                                            .getJavaTypeVoorElement(attribuut.getType());
                                    }
                                    paramNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                                }
                                // Veld in klasse heet hetzelfde als de paramater.
                                final JavaFunctieParameter param = new JavaFunctieParameter(paramNaam, paramType);
                                param.setJavaDoc("Bijbehorende " + attribuut.getType().getNaam() + ".");
                                constructor.voegParameterToe(param);
                                if (!isSubtype(objectType)) {
                                    constructorBody.append(String.format(
                                        "this.%1$s = %2$s;%3$s",
                                        paramNaam,
                                        paramNaam,
                                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                                }
                            } else if (!"id".equalsIgnoreCase(attribuut.getIdentCode())
                                && !isSubtype(objectType))
                            {
                                kopieObjectTypeGebruiktInBody = true;
                                final String copyCode = "this.%1$s = %2$s.get%3$s();%4$s";
                                constructorBody.append(
                                    String.format(copyCode,
                                        GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()),
                                        consParamNaam,
                                        attribuut.getIdentCode(),
                                        JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                            }
                        }
                    }
                    //Bij een subtype roepen we de super constructor aan met alle constructor parameters.
                    if (isSubtype(objectType)) {
                        kopieObjectTypeGebruiktInBody = true;
                        constructorBody.append("super(");
                        for (final JavaFunctieParameter consParameter : constructor.getParameters()) {
                            constructorBody.append(consParameter.getNaam()).append(", ");
                        }
                        constructorBody.deleteCharAt(constructorBody.lastIndexOf(","));
                        constructorBody.append(String.format(");%s",
                            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                    }
                } else {
                    //Maak een copy statement in de body.
                    kopieObjectTypeGebruiktInBody = true;
                    constructorBody.append(
                        String.format("if (%2$s.get%3$s() != null) {this.%1$s = new %4$s(%2$s.get%3$s());}%5$s",
                            GeneratieUtil.lowerTheFirstCharacter(groep.getIdentCode()),
                            consParamNaam,
                            groep.getIdentCode(),
                            operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(groep).getNaam(),
                            JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
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
     * Bouwt de inverse associaties voor een object type. Adres kent een Persoon dus hier wordt aan Persoon een lijst van adressen toegevoegd. LET OP:
     * Inverse associatie wordt enkel gegenereerd indien de inverse associatie een identCode waarde heeft in het BMR.
     *
     * @param objectType Object type dat mogelijk inverse associaties kent.
     * @param clazz      De Java klasse representatie van het object type waar de inverse associatie in gemaakt moet
     */
    private void genereerInverseAssociatiesVoorObjectType(final ObjectType objectType, final JavaKlasse clazz) {
        final List<Attribuut> inverseAttrVoorObjectType = getBmrDao().getInverseAttributenVoorObjectType(objectType);
        final String body = clazz.getConstructor().getBody();
        final StringBuilder constructorBody = new StringBuilder(body);

        for (final Attribuut inverseAttr : inverseAttrVoorObjectType) {
            // Neem geen inverse associaties op die naar een type verwijzen dat niet in het OGM hoort.
            if (inverseAttr.getObjectType().getInOgm() == null || inverseAttr.getObjectType().getInOgm() == 'J') {
                final JavaType inverseType;

                if (isDatabaseKnipAttribuut(objectType, inverseAttr)) {
                    inverseType = bepaalJavaTypeVoorDatabaseKnipAttribuut(inverseAttr);
                } else {
                    inverseType = operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(inverseAttr.getObjectType());
                }

                boolean isGesorteerdeCollectie = false;
                boolean heeftStandaardComparator = true;
                JavaType setImplType = JavaType.HASH_SET;
                JavaType setType = JavaType.SET;
                if (bevatVolgnummer(inverseAttr.getObjectType())) {
                    setType = JavaType.SORTED_SET;
                    setImplType = JavaType.TREE_SET;
                    isGesorteerdeCollectie = true;
                    heeftStandaardComparator = false;
                }
                final JavaType javaType = new JavaType(setType, inverseType);
                final String classVariabele =
                    GeneratieUtil.lowerTheFirstCharacter(inverseAttr.getInverseAssociatieIdentCode());
                final JavaVeld veld = new JavaVeld(javaType, classVariabele);
                clazz.voegVeldToe(veld);

                // JPA annotaties.
                AnnotatieWaardeParameter lazinessAnnotatieParameter =
                    new AnnotatieWaardeParameter("fetch", JavaType.FETCH_TYPE, "LAZY");
                //FIXME: specifieke hack om de collectie van betrokkenheden in relatie eager te maken.
                //TODO: Moet uiteindelijk opgelost worden met een extra veldje in het BMR m.b.t. lazy/eager inverse ass.
                if ((objectType.getIdentCode().equals("Relatie") && classVariabele.equals("betrokkenheden"))) {
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

                if (bevatVolgnummer(inverseAttr.getObjectType())) {
                    annoteerCollectieVeldVoorSortering(veld, JavaType.VOLGNUMMER_COMPARATOR);
                }

                final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
                if (clazz.getSuperInterfaces().isEmpty()
                    || 'N' == inverseAttr.getObjectType().getInLgm()
                    || !behoortTotJavaBerichtModel(inverseAttr.getObjectType()))
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
                constructorBody.append(String.format("%1$s = %2$s;%3$s", veld.getNaam(),
                    bouwSetInitialisatieCodeFragmentEnVoegImportsToe(setImplType,
                        inverseAttr.getObjectType(), inverseType, clazz, isGesorteerdeCollectie, heeftStandaardComparator, JavaType.COMPARATOR_FACTORY),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
            }
        }

        clazz.getConstructor().setBody(constructorBody.toString());
    }

    /**
     * Genereert voor een groep een veld in een java klasse.
     *
     * @param clazz                  De klasse waaraan het veld wordt toegevoegd.
     * @param objectType             Het object type waar de groep bij hoort.
     * @param groep                  De groep waarvoor een veld wordt aangemaakt.
     * @param groepBehoortTotSubType Geeft aan of groep een direct attribuut is van objecttype of van een subtype van objecttype.
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
     * Genereer een setter voor een groep-veld (dus niet een veld in een groep).
     *
     * @param clazz      de klasse
     * @param objectType het object type
     * @param groep      de groep
     * @param groepVeld  het groep-veld
     */
    private void genereerSetterVoorGroepVeld(final JavaKlasse clazz,
        final ObjectType objectType, final Groep groep, final JavaVeld groepVeld)
    {
        final JavaMutatorFunctie setter = new JavaMutatorFunctie(groepVeld);
        genereerSetterJavaDoc(setter, objectType, groep);
        clazz.voegSetterToe(setter);
    }

    /**
     * Genereert velden voor elk attribuut dat voorkomt in de identiteit groep. Voor elk veld (behalve een ID veld) worden ook een getter toegevoegd, maar
     * geen setter.
     *
     * @param clazz          Java klasse waarin de velden terecht komen.
     * @param objectType     Object type uit het BMR waar deze groep bij hoort.
     * @param groep          De identiteit groep.
     * @param discrAttribuut Eventuele discriminator attribuut dat aanwezig kan zijn in de groep.
     */
    private void genereerVeldenVoorIdentiteitGroep(final JavaKlasse clazz, final ObjectType objectType,
        final Groep groep, final Attribuut discrAttribuut)
    {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanGroep(groep);
        for (final Attribuut attribuut : attributen) {
            if (isIdAttribuut(attribuut)) {
                final JavaVeld idVeld = genereerJavaVeldVoorAttribuutType(attribuut);
                annoteerIdVeldVoorLazyLoading(clazz, objectType, attribuut, idVeld);

            } else if (behoortTotJavaOperationeelModel(attribuut)) {
                // TEAMBRP-408: Als het attribuut naar een object type verwijst dat in een andere database
                // opgeslagen wordt, verwijs dan naar het id daarvan ipv het 'hele' object.
                if (isDatabaseKnipAttribuut(objectType, attribuut)) {
                    this.genereerDatabaseKnipVeldVoorIdentiteitAttribuut(clazz, objectType, attribuut);
                } else {
                    this.genereerRegulierVeldVoorIdentiteitGroep(clazz, objectType, discrAttribuut, attribuut);
                }
            }
        }
    }

    /**
     * Genereer een veld dat 'over de grens' gaat tussen 2 databases voor identiteit groep en voeg het toe aan de klasse.
     *
     * @param clazz      klasse
     * @param objectType object type
     * @param attribuut  attribuut
     */
    private void genereerDatabaseKnipVeldVoorIdentiteitAttribuut(
        final JavaKlasse clazz, final ObjectType objectType, final Attribuut attribuut)
    {
        final JavaVeld veld = genereerDatabaseKnipVeld(attribuut);
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.COLUMN,
            new AnnotatieWaardeParameter("name", attribuut.getIdentDb())));
        clazz.voegVeldToe(veld);

        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        genereerGetterJavaDoc(getter, objectType, attribuut);
        clazz.voegGetterToe(getter);
        genereerSetterVoorAttribuut(clazz, attribuut, null, veld);
    }

    /**
     * Genereer regulier veld voor identiteit groep.
     *
     * @param clazz          klasse
     * @param objectType     object type
     * @param discrAttribuut discriminator attribuut
     * @param attribuut      attribuut
     */
    private void genereerRegulierVeldVoorIdentiteitGroep(
        final JavaKlasse clazz, final ObjectType objectType,
        final Attribuut discrAttribuut, final Attribuut attribuut)
    {
        final boolean overrideAnnotatieBenodigd;
        final JavaVeld veld;
        if (attribuut.getId() == ID_OT_PERSOON_CACHE_ATTRIBUUT_PERSOON) {
            overrideAnnotatieBenodigd = false;
            // Speciale uitzondering (aka hack) voor het veld 'persoon' in het object type 'persoon cache',
            // zodat het id als type wordt gebruikt ipv het hele persoon object. TANGO-527
            final JavaType javaType = JavaType.INTEGER;
            veld = new JavaVeld(javaType, "persoonId");
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.COLUMN,
                new AnnotatieWaardeParameter("name", "pers")));
        } else {
            overrideAnnotatieBenodigd =
                behoortTotJavaLogischModel(attribuut) && behoortTotJavaOperationeelModel(attribuut);
            final JavaType javaType =
                operationeelModelNaamgevingStrategie.getJavaTypeVoorElement(attribuut.getType());
            veld = new JavaVeld(javaType, GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode()));

            annoteerAttribuutVeld(objectType, attribuut, veld,
                isDiscriminatorAttribuut(attribuut, discrAttribuut),
                isJsonProperty(attribuut), false);

        }
        clazz.voegVeldToe(veld);
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);

        //Als het attribuut meegenomen wordt door de LogischeModelGenerator dan een override annotatie:
        if (overrideAnnotatieBenodigd) {
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        }
        genereerGetterJavaDoc(getter, objectType, attribuut);
        clazz.voegGetterToe(getter);

        // Alleen voor de objecten Stuurgegevens en Bericht genereren we setters voor de attributen
        // Deze setters zijn nodig omdat niet alle gegevens voorhanden zijn tijdens initialisatie van het object
        if (isGroepOfObjecttypeWaarvoorSettersGegenereerdMoetenWorden(objectType)) {
            genereerSetterVoorAttribuut(clazz, attribuut, null, veld);
        }
    }

    /**
     * Annoteert het ID veld van een HisVolledig entiteit. Omdat boven de HisVolledig klassen een interface hangt hebben we een probleem met het LAZY
     * loaden van ManyToOne associaties; de interface heeft namelijk geen ID veld. Hierdoor heeft de Hibernate proxy ook geen ID veld, dus zal hibernate
     * altijd de gehele entiteit loaden op het moment dat je getID aanroept, ook al staat de entiteit als Lazy op de ManyToOne annotatie.<br/> De oplossing
     * is de ID te annoteren op de getter ipv het veld, omdat de interface wél een getID() functie kent, zal de proxy dat ook kennen, en kun je gebruik
     * maken van LAZY Loading. Het ID veld wordt dus met @Transient ge-annoteerd.
     *
     * @param klasse     De klasse waarvoor het id veld geannoteerd moet worden.
     * @param objectType het ObjectType waarvoor het id veld geannoteerd moet worden.
     * @param attribuut  het id attribuut.
     * @param idVeld     het id Veld dat aangemaakt is.
     */
    private void annoteerIdVeldVoorLazyLoading(final JavaKlasse klasse, final ObjectType objectType,
        final Attribuut attribuut, final JavaVeld idVeld)
    {
        idVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.TRANSIENT));
        idVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
        klasse.voegVeldToe(idVeld);

        final JavaAccessorFunctie getter = new JavaAccessorFunctie(idVeld);
        annoteerIdGetter(objectType, getter, false);

        //Als ModelIdentificeerbaar een superinterface is dan override annotatie boven de getter.
        if (klasse.getSuperInterfaces().contains(JavaType.MODEL_IDENTIFICEERBAAR)) {
            getter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        }
        genereerGetterJavaDoc(getter, objectType, attribuut);
        klasse.voegGetterToe(getter);

        //JPA verplicht de aanwezigheid van een setID() functie wanneer de Id annotatie op een getter zit.
        //We makken de setter protected.
        genereerIdVeldSetterVoorKlasse(klasse, idVeld);
    }
}
