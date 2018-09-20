/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
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
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

import org.springframework.stereotype.Component;

/**
 * Genereert klassen voor de stamgegevens, waarbij vanuit Java oogpunt alleen de (conform BMR benaming) dynamische
 * stamgegevens worden beschouwd als werkelijke stamgegevens. Hier bij valt te denken aan objecten zoals Partij en
 * Land; objecten die dus wel nog runtime aangepast, toegevoegd en verwijderd kunnen worden.
 * <br/>
 * Deze klassen zijn hibernate entities, dus de ORM annotaties worden ook gegenereerd.
 * <p>
 * Merk op dat de, vanuit het BMR gezien, statische stamgegevens binnen de Java generatoren worden gegenereerd als
 * enumeraties. Zie hiervoor de {@link StatischeStamgegevensGenerator}.
 * </p>
 */
@Component("dynamischeStamgegevensJavaGenerator")
public class DynamischeStamgegevensGenerator extends AbstractJavaGenerator {

    private static final int DATUM_ATTRIBUUTTYPE_ID = 3743;

    private NaamgevingStrategie naamgevingStrategie = new AlgemeneNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        //Haal alle stamgegevens object typen op.
        final List<ObjectType> stamgegevensObjectTypen = getBmrDao().getDynamischeStamgegevensObjectTypen();

        //Itereer over alle objecttypen en maak java klassen aan.
        final List<JavaKlasse> stamgegevens = new ArrayList<JavaKlasse>();

        for (ObjectType objectType : stamgegevensObjectTypen) {
            stamgegevens.add(genereerStamgegeven(objectType));
        }

        //Schrijf de stamgegevens weg via een writer.
        final JavaWriter<JavaKlasse> writer = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> gegenereerdeKlassen = writer.genereerEnSchrijfWeg(stamgegevens, this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.DYNAMISCHE_STAMGEGEVENS_JAVA_GENERATOR;
    }

    /**
     * Genereert op basis van het object type uit het BMR de java klasse.
     *
     * @param objectType Object type uit BMR.
     * @return Een java klasse object.
     */
    private JavaKlasse genereerStamgegeven(final ObjectType objectType) {
        final JavaKlasse clazz = new JavaKlasse(naamgevingStrategie.getJavaTypeVoorElement(objectType),
                bouwJavadocVoorElement(objectType));

        // Genereer de klasse annotaties en zet deze op de klasse
        clazz.setAnnotaties(genereerKlasseAnnotatiesVoorStamgegeven(objectType, clazz));

        //Superklasse is altijd AbstractStatischObjectType
        clazz.setExtendsFrom(JavaType.ABSTRACT_STATISCH_OBJECT_TYPE);

        //De velden met JPA annotaties en accessor functies.
        voegVeldenToeVoorAttributen(objectType, clazz);

        // Eventueel benodigde bestaansperiode velden toevoegen met JPA annotaties en accessor functies.
        voegBestaansPeriodeVeldenToeIndienNodig(objectType, clazz);

        voegStandaardLegeConstructorToeAanKlasse(clazz);
        voegStandaardInstantiatieConstructorToeAanKlasse(clazz);

        return clazz;
    }

    /**
     * Sommige stamgegevens dienen een bestaansperiode (historie patroon) bij te houden. Indien dit benodigd is, zal in
     * het BMR op de identiteit groep van het objecttype (het dynamische stamgegeven) het 'historie vastleggen' veld op
     * 'P' staan. Indien dit zo is, dienen er twee velden toegevoegd te worden t.b.v. het historie patroon.
     *
     * @param objectType het objecttype stamgegeven.
     * @param klasse de Java klasse die voor het stamgegeven wordt gegenereerd.
     */
    private void voegBestaansPeriodeVeldenToeIndienNodig(final ObjectType objectType, final JavaKlasse klasse) {
        boolean heeftBestaansPeriode = false;

        final List<Groep> groepen = getBmrDao().getGroepenVoorObjectType(objectType);
        for (Groep groep : groepen) {
            if (groep.getHistorieVastleggen().equals('P')) {
                heeftBestaansPeriode = true;
                break;
            }
        }

        if (heeftBestaansPeriode) {
            klasse.voegSuperInterfaceToe(JavaType.BESTAANS_PERIODE_STAMGEGEVEN);
            voegBestaandPeriodeVeldEnAccessorToe(klasse, objectType, "datumAanvangGeldigheid",
                                                 "datum aanvang geldigheid", "dataanvgel");
            voegBestaandPeriodeVeldEnAccessorToe(klasse, objectType, "datumEindeGeldigheid", "datum einde geldigheid",
                    "dateindegel");
        }
    }

    /**
     * Voegt een veld en bijbehorende getter toe voor een bestaansperiode veld.
     *
     * @param klasse de klasse waaraan het veld en getter moet worden toegevoegd.
     * @param objectType het stamgegeven waarvoor dit geldt.
     * @param veldNaam de naam van het veld dat moet worden toegevoegd.
     * @param veldOmschrijving de omschrijving van het veld dat moet worden toegevoegd.
     * @param kolomNaam de kolomnaam in de database.
     */
    private void voegBestaandPeriodeVeldEnAccessorToe(final JavaKlasse klasse, final ObjectType objectType,
        final String veldNaam, final String veldOmschrijving, final String kolomNaam)
    {
        final AttribuutType datumAttribuutType =
                this.getBmrDao().getElement(DATUM_ATTRIBUUTTYPE_ID, AttribuutType.class);
        JavaType datumType = naamgevingStrategie.getJavaTypeVoorElement(datumAttribuutType);
        final JavaVeld datumVeld = new JavaVeld(datumType, veldNaam, String.format("%s.", veldOmschrijving));
        datumVeld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
        datumVeld.voegAnnotatieToe(
            new JavaAnnotatie(JavaType.ATTRIBUTE_OVERRIDE, new AnnotatieWaardeParameter("name", "waarde"),
                new AnnotatieAnnotatieParameter("column",
                    new JavaAnnotatie(JavaType.COLUMN, new AnnotatieWaardeParameter("name", kolomNaam)))));
        klasse.voegVeldToe(datumVeld);

        final JavaAccessorFunctie getterMethode = new JavaAccessorFunctie(datumVeld);
        getterMethode.setJavaDoc(String.format("Retourneert de %s voor %s.", veldOmschrijving, objectType.getNaam()));
        getterMethode.setReturnWaardeJavaDoc(String.format("%s voor %s.", veldOmschrijving, objectType.getNaam()));
        klasse.voegGetterToe(getterMethode);
    }

    /**
     * Voegt velden toe aan opgegeven klasse voor all attributen van het opgegeven objecttype. Hierbij worden
     * ook de annotaties op de velden gezet, worden eventuele benodigde accessor methodes toegevoegd en worden de
     * gebruikte imports toegevoegd aan de klasse.
     *
     * @param objectType het objecttype waarvoor de attributen als velden aan de klasse moeten worden toegevoegd.
     * @param klasse de klasse waaraan de velden toegevoegd dienen te worden.
     */
    private void voegVeldenToeVoorAttributen(final ObjectType objectType, final JavaKlasse klasse) {
        final List<Attribuut> attributen = getBmrDao().getAttributenVanObjectType(objectType);
        // Voeg status historie attributen toen.
        attributen.addAll(getBmrDao().getStatusHistorieAttributenVanObjectType(objectType));
        for (Attribuut attribuut : attributen) {
            if (attribuut != null) {
                final String veldNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getIdentCode());
                if (isIdAttribuut(attribuut)) {
                    klasse.voegVeldToe(genereerIdVeldVoorStamgegeven(klasse, attribuut, veldNaam));
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
     * @param clazz de klasse.
     * @return de klasse annotaties.
     */
    private List<JavaAnnotatie> genereerKlasseAnnotatiesVoorStamgegeven(final ObjectType objectType,
        final JavaKlasse clazz)
    {
        List<JavaAnnotatie> klasseAnnotaties = new ArrayList<JavaAnnotatie>();
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.ENTITY));
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.TABLE,
            new AnnotatieWaardeParameter("schema", objectType.getSchema().getNaam()),
            new AnnotatieWaardeParameter("name", objectType.getIdentDb())));
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.ACCESS,
            new AnnotatieWaardeParameter("value", JavaType.ACCESS_TYPE, "FIELD")));

        // Een entity listener die voorkomt dat stamgegevens door de BRP worden aangemaakt, aangepast of verwijderd:
        klasseAnnotaties.add(new JavaAnnotatie(JavaType.ENTITY_LISTENERS,
            new AnnotatieWaardeParameter("value", "StamgegevenEntityListener.class", true)));
        clazz.voegExtraImportToe(
            GeneratiePackage.BRP_MODEL_BASIS_PACKAGE.createImportClass("StamgegevenEntityListener"));

        return klasseAnnotaties;
    }

    /**
     * Bouwt een standaard lege constructor en voegt deze toe aan de opgegeven clazz.
     *
     * @param clazz de clazz waaraan een lege constructor dient te worden toegevoegd.
     */
    private void voegStandaardLegeConstructorToeAanKlasse(final JavaKlasse clazz) {
        //Een stamgegeven komt uit de database dus het instantieren vanuit de code heeft geen zin,
        //dus we genereren een default protected constructor.
        final Constructor constructor = new Constructor(JavaAccessModifier.PROTECTED, clazz.getNaam());
        constructor.setJavaDoc("Constructor is protected, want de BRP zal geen stamgegevens beheren.");
        clazz.voegConstructorToe(constructor);
    }

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
     * Bouwt een constructor met daarin alle velden van de opgegeven klasse als constructor parameters, exclusief een
     * eventueel aanwezig 'id' veld.
     *
     * @param clazz de klasse waarvoor de constructor gezet moet worden.
     */
    private void voegStandaardInstantiatieConstructorToeAanKlasse(final JavaKlasse clazz) {
        final Constructor constructor = new Constructor(JavaAccessModifier.PROTECTED, clazz.getNaam());
        final StringBuilder constructorBody = new StringBuilder();

        for (JavaVeld veld : clazz.getVelden()) {
            if (!isIdVeld(veld)) {
                final JavaFunctieParameter parameter = new JavaFunctieParameter(veld.getNaam(), veld.getType(),
                    String.format("%s van %s.", veld.getNaam(), clazz.getNaam()));
                constructorBody.append(String.format("this.%1$s = %1$s;%n", veld.getNaam()));
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
     * @param clazz De klasse waarvoor een veld wordt gegenereerd.
     * @param attribuut Het attribuut uit het BMR wat vertaald wordt naar een java veld.
     * @param veldNaam Naam van het veld.
     * @return Een java veld wat een vertaling is van het attribuut.
     */
    private JavaVeld genereerVeldVoorStamgegeven(final JavaKlasse clazz, final Attribuut attribuut,
        final String veldNaam)
    {
        final GeneriekElement attribuutType = attribuut.getType();
        final JavaType javaTypeVoorVeld = naamgevingStrategie.getJavaTypeVoorElement(attribuutType);

        final boolean isObjectType = attribuutType != null && BmrElementSoort.OBJECTTYPE == BmrElementSoort
            .getBmrElementSoortVoorCode(attribuutType.getSoortElement().getCode());
        final boolean isStatischStamgegeven = (isObjectType && 'X' == attribuutType.getSoortInhoud());

        final JavaVeld veld = new JavaVeld(javaTypeVoorVeld, veldNaam);

        //LET OP: Dit attribuut kan op zijn beurt weer van het type objecttype zijn. En dat is dan weer
        //een enum of een klasse. (pfff...)
        if (isObjectType) {
            if (isStatischStamgegeven) {
                //Het is een objecttype
                final JavaAnnotatie kolomAnn = new JavaAnnotatie(JavaType.COLUMN,
                    new AnnotatieWaardeParameter("name", attribuut.getIdentDb()));
                veld.voegAnnotatieToe(kolomAnn);
            } else {
                //Het is een objecttype met dynamische inhoud
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.MANY_TO_ONE));
                final JavaAnnotatie joinKolomAnn = new JavaAnnotatie(JavaType.JOIN_COLUMN,
                    new AnnotatieWaardeParameter("name", attribuut.getIdentDb()));
                veld.voegAnnotatieToe(joinKolomAnn);

                // Hibernate specifieke annotatie toevoegen voor het direct fetchen van collecties
                // (o.a. dynamische stamgegevens).
                final JavaAnnotatie fetchAnnotatie = new JavaAnnotatie(JavaType.FETCH);
                fetchAnnotatie.voegParameterToe(new AnnotatieWaardeParameter("value", JavaType.FETCH_MODE, "JOIN"));
                veld.voegAnnotatieToe(fetchAnnotatie);
            }
        } else {
            //Het is een attribuuttype
            if (isEnumeratieAttribuut(attribuut)) {
                final JavaAnnotatie enumAnn = new JavaAnnotatie(JavaType.TYPE);
                enumAnn.voegParameterToe(
                    new AnnotatieWaardeParameter("type", veld.getType().getNaam()));
                veld.voegAnnotatieToe(enumAnn);

                final JavaAnnotatie kolomAnn = new JavaAnnotatie(JavaType.COLUMN,
                    new AnnotatieWaardeParameter("name", attribuut.getIdentDb()));
                veld.voegAnnotatieToe(kolomAnn);
            } else {
                veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDED));
                final JavaAnnotatie attrOverrideAnn = new JavaAnnotatie(
                    JavaType.ATTRIBUTE_OVERRIDE,
                    new AnnotatieWaardeParameter("name", "waarde"),
                    new AnnotatieAnnotatieParameter("column", new JavaAnnotatie(
                        JavaType.COLUMN, new AnnotatieWaardeParameter("name", attribuut.getIdentDb()))));
                veld.voegAnnotatieToe(attrOverrideAnn);
            }
        }

        //Accessor voor het veld.
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        genereerGetterJavaDoc(getter, attribuut.getObjectType(), attribuut);
        clazz.voegGetterToe(getter);
        return veld;
    }

    /**
     * Genereert een Id java veld voor een stamgegeven java klasse.
     *
     * @param clazz De java klasse waarvoor het veld wordt gegenereerd.
     * @param attribuut Het attrbituut wat vertaald wordt naar een java veld.
     * @param veldNaam Naam van het veld.   @return Een java veld wat de vertaling is van het id attribuut.
     * @return Een Java veld.
     */
    private JavaVeld genereerIdVeldVoorStamgegeven(final JavaKlasse clazz, final Attribuut attribuut,
        final String veldNaam)
    {
        //Voor id's gebruiken we enkel java primitive wrappers en geen attribuut typen
        final AttribuutType attribuutType = getBmrDao().getElement(attribuut.getType().getId(), AttribuutType.class);
        final JavaType javaTypeVoorVeld = this.getJavaTypeVoorAttribuutType(attribuutType);
        final JavaVeld veld = new JavaVeld(javaTypeVoorVeld, veldNaam);
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.ID));

        // Voor dynamische stamgegevens serialiseren we alleen het id.
        veld.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_PROPERTY));

        // Accessor voor het id veld.
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        // Niet public, maar protected, zodat het in de user klasse opengesteld kan worden waar nodig.
        getter.setAccessModifier(JavaAccessModifier.PROTECTED);
        genereerGetterJavaDoc(getter, attribuut.getObjectType(), attribuut);
        clazz.voegGetterToe(getter);

        return veld;
    }
}
