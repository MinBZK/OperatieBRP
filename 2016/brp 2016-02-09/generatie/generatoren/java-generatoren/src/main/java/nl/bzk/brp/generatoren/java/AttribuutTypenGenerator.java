/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrElementSoort;
import nl.bzk.brp.generatoren.algemeen.common.BmrSoortInhoud;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.GeneratiePackage;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Schema;
import nl.bzk.brp.metaregister.model.SoortElement;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;
import org.springframework.stereotype.Component;

/** Generator die voor elk attribuut type in het BMR een java klasse (value object) genereert. */
@Component("attribuutTypenJavaGenerator")
public class AttribuutTypenGenerator extends AbstractJavaGenerator {

    private NaamgevingStrategie naamgevingStrategie = new AlgemeneNaamgevingStrategie();

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        //Haal alle attribuut typen op
        final List<AttribuutType> attribuutTypen = getBmrDao().getAttribuutTypen();

        //Genereer de Java klassen ervoor
        final List<JavaKlasse> klassen = new ArrayList<>();
        final List<JavaEnumeratie> enumeraties = new ArrayList<>();
        final List<JavaKlasse> enumWrapperKlassen = new ArrayList<>();
        for (final AttribuutType attribuutType : attribuutTypen) {
            // Alleen Javaklasses genereren indien attribuuttype niet een ID-subtype is.
            if (attribuutType.getBasisType().getId() != ID_BASISTYPE_ID) {
                List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                    attribuutType, false, true);
                if (waardes.isEmpty()) {
                    klassen.add(genereerAttribuutWrapperKlasse(
                            attribuutType, this.getJavaTypeVoorAttribuutType(attribuutType)));
                } else {
                    final JavaEnumeratie enumeratie = genereerEnumeratieVoorAttribuutType(attribuutType, waardes);
                    enumeraties.add(enumeratie);
                    JavaKlasse attribuutWrapperKlasse = genereerAttribuutWrapperKlasse(
                            maakAttribuutEnumeratieGeneriekElement(attribuutType, enumeratie), enumeratie.getType());

                    // Incorrect package, vervang het stamgegeven package pad door het attribuut package pad.
                    attribuutWrapperKlasse.setPackagePad(attribuutWrapperKlasse.getPackagePad().
                            replace(GeneratiePackage.STAMGEGEVEN_STATISCH_PACKAGE.getPackage(),
                                    GeneratiePackage.ATTRIBUUTTYPE_PACKAGE.getPackage()));
                    enumWrapperKlassen.add(attribuutWrapperKlasse);
                }
            }
        }

        // Schrijf de klassen weg (attribuut wrappers).
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(klassen, generatorConfiguratie);
        final List<JavaKlasse> geschrevenKlassen = klasseWriter.genereerEnSchrijfWeg(klassen, this);

        // Schrijf de enums weg (vaste waarde attributen).
        final JavaWriter<JavaEnumeratie> enumeratieWriter =
                javaWriterFactory(generatorConfiguratie, JavaEnumeratie.class);
        voegGeneratedAnnotatiesToe(enumeraties, generatorConfiguratie);
        final List<JavaEnumeratie> geschrevenEnumeraties = enumeratieWriter.genereerEnSchrijfWeg(enumeraties, this);

        // Schrijf de vaste waarde attribuut wrapper klassen weg, altijd zonder generation gap.
        generatorConfiguratie.setGenerationGapPatroon(false);
        final JavaWriter<JavaKlasse> enumWrapperWriter =
                javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        voegGeneratedAnnotatiesToe(enumWrapperKlassen, generatorConfiguratie);
        geschrevenKlassen.addAll(enumWrapperWriter.genereerEnSchrijfWeg(enumWrapperKlassen, this));

        // Rapporteer over alle gegenereerde java types.
        rapporteerOverGegenereerdeJavaTypen(Iterables.concat(geschrevenEnumeraties, geschrevenKlassen));
    }

    /**
     * Maak een generiek element dat een gegenereerde enum voor een attribuut voorstelt.
     * Dit is geen echt in het BMR voorkomend element, maar kan handig gebruikt worden om
     * de juiste output te genereren voor een attribuut wrapper.
     * Alleen de methodes van generiek element die daadwerkelijk nodig zijn worden geimplementeerd.
     *
     * @param attribuutType het attribuut type
     * @param enumeratie de gegenereerde enumeratie
     * @return een generiek element implementatie
     */
    private GeneriekElement maakAttribuutEnumeratieGeneriekElement(
            final AttribuutType attribuutType, final JavaEnumeratie enumeratie)
    {
        return new GeneriekElement() {
            @Override
            public SoortElement getSoortElement() {
                return new SoortElement(BmrElementSoort.OBJECTTYPE.getUniekeCode(), "Attribuut Enumeratie");
            }
            @Override
            public Character getSoortInhoud() {
                return BmrSoortInhoud.STATISCH_STAMGEGEVEN.getCode();
            }
            @Override
            public String getNaam() {
                return attribuutType.getNaam();
            }
            @Override
            public String getIdentCode() {
                return enumeratie.getNaam();
            }
            @Override
            public Schema getSchema() {
                return attribuutType.getSchema();
            }
        };
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.ATTRIBUUT_TYPEN_JAVA_GENERATOR;
    }

    /**
     * Genereert een enumeratie waarde voor een attribuuttype en de daarbij horende waarde regels.
     *
     * @param attribuutType het attribuuttype.
     * @param waardes de waarde regels.
     * @return een nieuwe JavaEnumeratie.
     */
    private JavaEnumeratie genereerEnumeratieVoorAttribuutType(final AttribuutType attribuutType,
        final List<WaarderegelWaarde> waardes)
    {
        final JavaEnumeratie javaEnum = new JavaEnumeratie(naamgevingStrategie.getJavaTypeVoorElement(attribuutType),
                bouwJavadocVoorElement(attribuutType));

        // Voeg een waarde veld toe voor het vastleggen van de bijbehorende waarde.
        final JavaType javaVeldTypeVoorWaarde = getJavaTypeVoorAttribuutType(attribuutType);
        final JavaVeld waardeVeld = new JavaVeld(javaVeldTypeVoorWaarde, "vasteWaarde");
        waardeVeld.setFinal(true);
        javaEnum.voegVeldToe(waardeVeld);

        // Voeg een omschrijving veld toe voor het vastleggen van de omschrijving
        final JavaVeld omschrijvingVeld = new JavaVeld(JavaType.STRING, "omschrijving");
        omschrijvingVeld.setFinal(true);
        javaEnum.voegVeldToe(omschrijvingVeld);

        // Voeg de interface toe.
        javaEnum.voegSuperInterfaceToe(new JavaType(JavaType.VASTE_ATTRIBUUT_WAARDE, waardeVeld.getType()));

        // Accessor functie voor waarde veld
        final JavaAccessorFunctie waardeGetter = new JavaAccessorFunctie(waardeVeld);
        waardeGetter.setJavaDoc(String.format("Retourneert %s voor %s", waardeVeld.getNaam(),
                javaEnum.getNaam()) + ".");
        waardeGetter.setReturnWaardeJavaDoc(String.format("%s voor %s", waardeVeld.getNaam(), javaEnum.getNaam()));
        waardeGetter.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        javaEnum.voegGetterToe(waardeGetter);

        // Accessor functie voor omschrijving veld
        final JavaAccessorFunctie omschrijvingGetter = new JavaAccessorFunctie(omschrijvingVeld);
        omschrijvingGetter.setJavaDoc(
            String.format("Retourneert %s voor %s", omschrijvingVeld.getNaam() + ".", javaEnum.getNaam()));
        omschrijvingGetter.setReturnWaardeJavaDoc(
            String.format("%s voor %s", omschrijvingVeld.getNaam(), javaEnum.getNaam()));
        javaEnum.voegGetterToe(omschrijvingGetter);

        javaEnum.voegConstructorToe(genereerConstructorVoorEnum(javaEnum));
        genereerEnumWaardenVoorWaardeRegels(javaEnum, waardeVeld, waardes);

        // ToString methode
        final JavaFunctie tostring = new JavaFunctie(JavaAccessModifier.PUBLIC, JavaType.STRING, "toString",
            String.format("Tekstuele representatie van %s.", javaEnum.getNaam()));
        tostring.setJavaDoc("Tekstuele representatie van de enumeratie waarde.");
        tostring.voegAnnotatieToe(new JavaAnnotatie(JavaType.OVERRIDE));
        tostring.setBody(String.format("return String.format(\"%%s - %%s\", %1$s, omschrijving);",
            waardeVeld.getNaam()));
        javaEnum.voegFunctieToe(tostring);

        return javaEnum;
    }

    /**
     * Voor elke waarderegel wordt een enumeratie waarde toegevoegd aan de enumeratie voor het opgegeven veld.
     *
     * @param javaEnum De java enumeratie waar waarden in moeten komen.
     * @param waardeVeld Het veld waarvoor de waarde geldt.
     * @param waardes De waardes die bij de enumeratie horen.
     */
    private void genereerEnumWaardenVoorWaardeRegels(final JavaEnumeratie javaEnum, final JavaVeld waardeVeld,
        final List<WaarderegelWaarde> waardes)
    {
        for (WaarderegelWaarde waarde : waardes) {
            String javaDoc = waarde.getWeergave() + ".";
            final EnumeratieWaarde enumWaarde = new EnumeratieWaarde(
                JavaGeneratieUtil.toValidEnumName(waarde.getWaarde()), javaDoc);

            final String waardeEnum;
            if (waardeVeld.getType().getNaam().equalsIgnoreCase("boolean")) {
                waardeEnum = GeneratieUtil.bmrJaNeeNaarBoolean(waarde.getWaarde()).toString();
            } else {
                waardeEnum = waarde.getWaarde();
            }
            enumWaarde.voegConstructorParameterToe(waardeEnum, waardeVeld.getType().getNaam().equals("String"));
            enumWaarde.voegConstructorParameterToe(waarde.getWeergave(), true);
            javaEnum.voegEnumeratieWaardeToe(enumWaarde);
        }
    }

}
