/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieAnnotatieParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.AnnotatieWaardeParameter;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.ObjectType;

/**
 * Genereert klassen voor de stamgegevens, waarbij vanuit Java oogpunt alleen de (conform BMR benaming) dynamische stamgegevens worden beschouwd als
 * werkelijke stamgegevens. Hierbij valt te denken aan objecten zoals Partij en Land; objecten die dus wel nog runtime aangepast, toegevoegd en verwijderd
 * kunnen worden. <br/> Deze klassen zijn hibernate entities, dus de ORM annotaties worden ook gegenereerd. <p> Merk op dat de, vanuit het BMR gezien,
 * statische stamgegevens binnen de Java generatoren worden gegenereerd als enumeraties. Zie hiervoor de {@link StatischeStamgegevensGenerator}. </p>
 */
public abstract class AbstractDynamischeStamgegevensGenerator extends AbstractJavaGenerator {

    private static final String NAME  = "name";
    private static final String VALUE = "value";

    @Override
    public final void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        // Haal alle stamgegevens object typen op.
        final List<ObjectType> stamgegevensObjectTypen = haalDynamischeStamgegevensObjectTypenOp();

        final List<JavaKlasse> klassen = new ArrayList<>();
        final List<JavaKlasse> wrapperKlassen = new ArrayList<>();

        // Itereer over alle object typen en maak java klassen aan voor het stamgegeven zelf en de attribuut wrapper.
        for (final ObjectType objectType : stamgegevensObjectTypen) {
            final JavaKlasse klasse = genereerStamgegeven(objectType);
            klassen.add(klasse);
            wrapperKlassen.add(genereerAttribuutWrapperKlasse(objectType, klasse.getType()));
        }

        // Schrijf de klassen weg via een writer.
        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(klassen, this);

        // Schrijf de wrapper klassen apart weg, want we willen daar geen generation gap voor.
        generatorConfiguratie.setGenerationGapPatroon(false);
        final JavaWriter<JavaKlasse> wrapperWriter = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(wrapperKlassen, generatorConfiguratie);
        gegenereerdeKlassen.addAll(wrapperWriter.genereerEnSchrijfWeg(wrapperKlassen, this));

        // Rapporteer over alle gegenereerde klassen.
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    /**
     * Haalt de lijst van dynamische stamgegevens-objecttypen op waarmee de generator aan de slag moet gaan.
     *
     * @return De lijst van dynamische stamgegevens-objecttypen.
     */
    protected abstract List<ObjectType> haalDynamischeStamgegevensObjectTypenOp();

    @Override
    public final GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.DYNAMISCHE_STAMGEGEVENS_JAVA_GENERATOR;
    }

    /**
     * Genereert op basis van het object type uit het BMR de java klasse.
     *
     * @param objectType Object type uit BMR.
     * @return Een java klasse object.
     */
    private JavaKlasse genereerStamgegeven(final ObjectType objectType) {
        final JavaKlasse clazz = new JavaKlasse(getNaamgevingStrategie().getJavaTypeVoorElement(objectType), bouwJavadocVoorElement(objectType));

        //Kan het stamgegeven gesynchroniseerd worden?
        if (objectType.getInBericht() != null && objectType.getInBericht() == 'J') {
            clazz.voegSuperInterfaceToe(JavaType.SYNCHRONISEERBAAR_STAMGEGEVEN);
        }

        // Genereer de klasse annotaties en zet deze op de klasse
        clazz.setAnnotaties(genereerKlasseAnnotatiesVoorStamgegeven(objectType, clazz));

        //De velden met JPA annotaties en accessor functies.
        voegVeldenToeVoorAttributen(objectType, clazz);

        // Eventueel benodigde bestaansperiode velden toevoegen met JPA annotaties en accessor functies.
        voegBestaansPeriodeVeldenToeIndienNodig(objectType, clazz);

        voegStandaardLegeConstructorToeAanKlasse(clazz);
        voegStandaardInstantiatieConstructorToeAanKlasse(clazz);

        // Voeg de 'Element getElement()' functie toe, alleen voor objecttypen van het KERN schema.
        if (objectType.getSchema().getId() == ID_KERN_SCHEMA) {
            voegGetElementFunctieToe(clazz, objectType);
        }

        return clazz;
    }

    /**
     * Sommige stamgegevens dienen een bestaansperiode (historie patroon) bij te houden. Indien dit benodigd is, zal in het BMR op de identiteit groep van
     * het objecttype (het dynamische stamgegeven) het 'historie vastleggen' veld op 'P' staan. Indien dit zo is, dienen er twee velden toegevoegd te
     * worden t.b.v. het historie patroon.
     *
     * @param objectType het objecttype stamgegeven.
     * @param klasse     de Java klasse die voor het stamgegeven wordt gegenereerd.
     */
    private void voegBestaansPeriodeVeldenToeIndienNodig(final ObjectType objectType, final JavaKlasse klasse) {
        if (isBestaansPeriodeStamgegeven(objectType)) {
            klasse.voegSuperInterfaceToe(JavaType.BESTAANS_PERIODE_STAMGEGEVEN);
            voegBestaansPeriodeVeldenEnAccessorsToe(klasse, objectType, getWrapperNaamgevingStrategie());
        }
    }

    /**
     * Voegt velden toe aan opgegeven klasse voor all attributen van het opgegeven objecttype. Hierbij worden ook de annotaties op de velden gezet, worden
     * eventuele benodigde accessor methodes toegevoegd en worden de gebruikte imports toegevoegd aan de klasse.
     *
     * @param objectType het objecttype waarvoor de attributen als velden aan de klasse moeten worden toegevoegd.
     * @param klasse     de klasse waaraan de velden toegevoegd dienen te worden.
     */
    private void voegVeldenToeVoorAttributen(final ObjectType objectType, final JavaKlasse klasse) {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
        for (final Attribuut attribuut : attributen) {
            if (attribuut != null) {
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                //Het is onduidelijk waarom deze 2 velden nu niet werken. Aangezien we ze niet nodig hebben negeren we ze voorlopig...
                if ("databaseObject".equals(veldNaam)
                    || "hisDatabaseObject".equals(veldNaam))
                {
                    continue;
                }
                if (isIdAttribuut(attribuut)) {
                    final JavaVeld idVeld = genereerIdVeldVoorStamgegeven(objectType, klasse, attribuut, veldNaam);
                    klasse.voegVeldToe(idVeld);
                } else {
                    klasse.voegVeldToe(genereerVeldVoorStamgegeven(klasse, attribuut, veldNaam));
                }
            }
        }
    }

    /**
     * Genereert de klasse annotaties voor het opgegeven objecttype.
     *
     * @param objectType het objecttype waarvoor de annotaties gegenereerd moeten worden.
     * @param clazz      de klasse.
     * @return de klasse annotaties.
     */
    protected final List<JavaAnnotatie> genereerKlasseAnnotatiesVoorStamgegeven(final ObjectType objectType, final JavaKlasse clazz) {
        final List<JavaAnnotatie> klasseAnnotaties = new ArrayList<>();
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.TABLE, new AnnotatieWaardeParameter("schema", objectType.getSchema().getNaam()),
            new AnnotatieWaardeParameter(NAME, objectType.getIdentDb())));
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.ACCESS, new AnnotatieWaardeParameter(VALUE, JavaType.ACCESS_TYPE, "FIELD")));

        genereerExtraKlasseAnnotatiesVoorStamgegeven(objectType, clazz, klasseAnnotaties);

        return klasseAnnotaties;
    }

    /**
     * Voegt extra klasse annotaties voor het dynamische stamgegeven toe.
     *
     * @param objectType       het objecttype waarvoor de annotaties gegenereerd moeten worden.
     * @param clazz            de klasse.
     * @param klasseAnnotaties de lijst met klasse annotaties waaraan annotaties toegevoegd kunnen worden
     */
    protected void genereerExtraKlasseAnnotatiesVoorStamgegeven(final ObjectType objectType, final JavaKlasse clazz,
        final List<JavaAnnotatie> klasseAnnotaties)
    {

    }

    /**
     * Bouwt een standaard lege constructor en voegt deze toe aan de opgegeven clazz.
     *
     * @param clazz de clazz waaraan een lege constructor dient te worden toegevoegd.
     */
    protected abstract void voegStandaardLegeConstructorToeAanKlasse(final JavaKlasse clazz);

    /**
     * Retourneert of een veld een id veld is of niet.
     *
     * @param veld het veld dat gecontroleerd dient te worden.
     * @return of het veld een id veld is of niet.
     */
    private boolean isIdVeld(final JavaVeld veld) {
        return "id".equalsIgnoreCase(veld.getNaam());
    }

    /**
     * Bouwt een constructor met daarin alle velden van de opgegeven klasse als constructor parameters, exclusief een eventueel aanwezig 'id' veld.
     *
     * @param clazz de klasse waarvoor de constructor gezet moet worden.
     */
    private void voegStandaardInstantiatieConstructorToeAanKlasse(final JavaKlasse clazz) {
        final Constructor constructor = new Constructor(JavaAccessModifier.PROTECTED, clazz);
        final StringBuilder constructorBody = new StringBuilder();

        for (final JavaVeld veld : clazz.getVelden()) {
            if (!isIdVeld(veld)) {
                final JavaFunctieParameter parameter = new JavaFunctieParameter(veld.getNaam(), veld.getType(),
                    String.format("%s van %s.", veld.getNaam(), clazz.getNaam()));
                constructorBody.append(String.format("this.%1$s = %1$s;%2$s", veld.getNaam(),
                    JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
                constructor.getParameters().add(parameter);
            }
        }
        constructor.setJavaDoc("Constructor die direct alle attributen instantieert.");
        constructor.setBody(constructorBody.toString());
        clazz.voegConstructorToe(constructor);
    }

    /**
     * Genereert een java veld voor een stamgegeven java klasse inclusief een accessor functie.
     *
     * @param clazz     De klasse waarvoor een veld wordt gegenereerd.
     * @param attribuut Het attribuut uit het BMR wat vertaald wordt naar een java veld.
     * @param veldNaam  Naam van het veld.
     * @return Een java veld wat een vertaling is van het attribuut.
     */
    private JavaVeld genereerVeldVoorStamgegeven(final JavaKlasse clazz, final Attribuut attribuut,
        final String veldNaam)
    {
        final GeneriekElement attribuutType = attribuut.getType();

        final boolean isObjectType = BmrElementSoort.OBJECTTYPE == BmrElementSoort
            .getBmrElementSoortBijCode(attribuutType.getSoortElement().getCode());
        final boolean isStatischStamgegeven = isObjectType && 'X' == attribuutType.getSoortInhoud();
        final boolean isDynamischStamgegeven = isObjectType && 'S' == attribuutType.getSoortInhoud();

        final JavaType javaTypeVoorVeld;
        if (isObjectType) {
            // Vanuit een stamgegeven mogen we wel direct een ander stamgegeven 'aanwijzen' (geen indirectie).
            javaTypeVoorVeld = getNaamgevingStrategie().getJavaTypeVoorElement(attribuutType);
        } else {
            // Een attribuut type bevat altijd een 'wrapper indirectie'.
            javaTypeVoorVeld = getWrapperNaamgevingStrategie().getJavaTypeVoorElement(attribuutType);
        }

        final JavaVeld veld = new JavaVeld(javaTypeVoorVeld, veldNaam);

        // Het attribuut van een dynamisch stamgegeven is een (scalair) attribuut type, of zelf ook
        // weer een stamgegeven, statisch dan wel dynamisch. Het zal echter nooit een dynamisch object type zijn.
        if (isObjectType) {
            if (isStatischStamgegeven) {
                final JavaAnnotatie kolomAnn = new JavaAnnotatie(JavaType.COLUMN,
                    new AnnotatieWaardeParameter(NAME, attribuut.getIdentDb()));
                veld.voegAnnotatieToe(kolomAnn);
            } else if (isDynamischStamgegeven) {
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.MANY_TO_ONE));
                final JavaAnnotatie joinKolomAnn = new JavaAnnotatie(JavaType.JOIN_COLUMN,
                    new AnnotatieWaardeParameter(NAME, attribuut.getIdentDb()));
                veld.voegAnnotatieToe(joinKolomAnn);

                // Hibernate specifieke annotatie toevoegen voor het direct fetchen van collecties
                // (o.a. dynamische stamgegevens).
                final JavaAnnotatie fetchAnnotatie = new JavaAnnotatie(JavaType.FETCH);
                fetchAnnotatie.voegParameterToe(new AnnotatieWaardeParameter(VALUE, JavaType.FETCH_MODE, "JOIN"));
                veld.voegAnnotatieToe(fetchAnnotatie);
            } else {
                throw new IllegalStateException("Ongeldig attribuut van dynamisch stamgegeven: '" + attribuut + "'.");
            }
        } else {
            veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
            final JavaAnnotatie attrOverrideAnn = new JavaAnnotatie(
                JavaType.ATTRIBUTE_OVERRIDE,
                new AnnotatieWaardeParameter(NAME, veld.getType().getNaam() + ".WAARDE_VELD_NAAM", true),
                new AnnotatieAnnotatieParameter("column",
                    new JavaAnnotatie(JavaType.COLUMN, new AnnotatieWaardeParameter(NAME, attribuut.getIdentDb()))));
            veld.voegAnnotatieToe(attrOverrideAnn);
            //JSON annotatie voor logische identiteit attributen.
            if (getBmrDao().isLogischIdentiteitAttribuut(attribuut)) {
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
            }
        }

        //Accessor voor het veld.
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        getter.setFinal(true);
        genereerGetterJavaDoc(getter, attribuut.getObjectType(), attribuut);
        clazz.voegGetterToe(getter);


        return veld;
    }

    /**
     * Genereert een Id java veld voor een stamgegeven java klasse.
     *
     * @param clazz     De java klasse waarvoor het veld wordt gegenereerd.
     * @param attribuut Het attrbituut wat vertaald wordt naar een java veld.
     * @param veldNaam  Naam van het veld.   @return Een java veld wat de vertaling is van het id attribuut.
     * @return Een Java veld.
     */
    private JavaVeld genereerIdVeldVoorStamgegeven(final ObjectType objectType, final JavaKlasse clazz, final Attribuut attribuut,
        final String veldNaam)
    {
        //Voor id's gebruiken we enkel java primitive wrappers en geen attribuut typen
        final AttribuutType attribuutType = getBmrDao().getElement(attribuut.getType().getId(), AttribuutType.class);
        final JavaType javaTypeVoorVeld = this.getJavaTypeVoorAttribuutType(attribuutType);
        final JavaVeld veld = new JavaVeld(javaTypeVoorVeld, veldNaam);
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ID));

        // Voor dynamische stamgegevens serialiseren we alleen het id.
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));
        genereerExtraVeldAnnotatiesVoorId(objectType, clazz, veld);

        // Accessor voor het id veld.
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        // Niet public, maar protected, zodat het in de user klasse opengesteld kan worden waar nodig.
        getter.setAccessModifier(bepaalDeAccessModifierVoorIdVeld());
        genereerGetterJavaDoc(getter, attribuut.getObjectType(), attribuut);
        clazz.voegGetterToe(getter);

        return veld;
    }

    /**
     * Haalt de accessmodifier voor het id veld op
     */
    protected abstract JavaAccessModifier bepaalDeAccessModifierVoorIdVeld();

    /**
     * Hook om extra annotaties op het ID veld te genereren.
     *
     * @param objectType Object type
     * @param clazz      de gegenereerde klasse
     * @param veld       het gegenereerde Id veld
     */
    protected void genereerExtraVeldAnnotatiesVoorId(final ObjectType objectType, final JavaKlasse clazz, final JavaVeld veld) {
    }

    /**
     * Geeft de naamgeving strategie.
     *
     * @return de naamgeving strategie
     */
    public abstract NaamgevingStrategie getNaamgevingStrategie();

    /**
     * Geeft de naamgeving strategie voor wrapper klasses.
     *
     * @return de naamgeving strategie voor wrapper klasses
     */
    public abstract NaamgevingStrategie getWrapperNaamgevingStrategie();
}
