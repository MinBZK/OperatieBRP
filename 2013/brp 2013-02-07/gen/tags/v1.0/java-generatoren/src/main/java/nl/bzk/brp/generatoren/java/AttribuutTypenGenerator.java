/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.metaregister.model.AttribuutType;
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
        final List<JavaKlasse> klassen = new ArrayList<JavaKlasse>();
        final List<JavaEnumeratie> enumeraties = new ArrayList<JavaEnumeratie>();
        for (AttribuutType attribuutType : attribuutTypen) {
            // Alleen Javaklasses genereren indien attribuuttype niet een ID-subtype is.
            if (attribuutType.getBasisType().getId() != ID_BASISTYPE_ID) {
                List<WaarderegelWaarde> waardes = getBmrDao().getWaardeEnumeratiesVoorElement(
                    attribuutType, false, true);
                if (waardes.isEmpty()) {
                    klassen.add(genereerKlasseVoorAttribuutType(attribuutType));
                } else {
                    enumeraties.add(genereerEnumeratieVoorAttribuutType(attribuutType, waardes));
                }
            }
        }

        //Geef de klassen en enumeraties door aan de writer.
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final List<JavaKlasse> geschrevenKlassen = klasseWriter.genereerEnSchrijfWeg(klassen, this);
        final JavaWriter<JavaEnumeratie> enumeratieWriter =
            javaWriterFactory(generatorConfiguratie, JavaEnumeratie.class);
        final List<JavaEnumeratie> geschrevenEnumeraties = enumeratieWriter.genereerEnSchrijfWeg(enumeraties, this);
        //Rapportage.
        rapporteerOverGegenereerdeJavaTypen(Iterables.concat(geschrevenEnumeraties, geschrevenKlassen));
    }

    @Override
    public GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.ATTRIBUUT_TYPEN_JAVA_GENERATOR;
    }

    /**
     * Genereert een JavaKlasse object wat correspondeert met het attribuut type.
     *
     * @param attribuutType Het attribuut type.
     * @return Een JavaKlasse object.
     */
    private JavaKlasse genereerKlasseVoorAttribuutType(final AttribuutType attribuutType) {
        final JavaType klasseType = naamgevingStrategie.getJavaTypeVoorElement(attribuutType);
        final String klasseNaam = klasseType.getNaam();
        final JavaKlasse clazz = new JavaKlasse(klasseType, null);

        //Een attribuut type hoort hibernate embeddable te zijn voor de persitentie in de DAL laag.
        clazz.voegAnnotatieToe(new JavaAnnotatie(JavaType.EMBEDDABLE));

        //Bepaald generic parameter voor AbstractGegevensAttribuutType
        final JavaType genericParameterType = this.getJavaTypeVoorAttribuutType(attribuutType);

        //Alle attribuut typen zijn voorlopig gegevens attribuut typen en niet statische.
        clazz.setExtendsFrom(new JavaType(JavaType.ABSTRACT_GEGEVENS_ATTRIBUUT_TYPE, genericParameterType));

        //Private default constructor om te voorkomen dat dit attribuuttype wordt geinstantieerd met een lege waarde.
        final Constructor defaultConstructor = new Constructor(JavaAccessModifier.PRIVATE, klasseNaam);
        defaultConstructor.setJavaDoc("Lege (value-object) constructor voor " + klasseNaam
                + ", niet gedeclareerd als public om te voorkomen dat objecten zonder waarde worden geïnstantieerd.");
        defaultConstructor.setBody("super(null);");
        clazz.voegConstructorToe(defaultConstructor);

        //Constructor met waarde parameter.
        final Constructor constructor = new Constructor(JavaAccessModifier.PUBLIC, klasseNaam);
        constructor.setJavaDoc("Constructor voor " + klasseNaam + " die de waarde toekent aan het (value-)object.");
        constructor.voegAnnotatieToe(new JavaAnnotatie(JavaType.JSON_CREATOR));
        constructor.voegParameterToe(new JavaFunctieParameter("waarde", genericParameterType,
                "De waarde voor dit value-object."));
        constructor.setBody("super(waarde);");
        clazz.voegConstructorToe(constructor);

        return clazz;
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

        // Voeg een waarde veld toe voor het vastleggen van de bijbehorende waarde, inclusief import hiervoor.
        final JavaType javaVeldTypeVoorWaarde = getJavaTypeVoorAttribuutType(attribuutType);
        final JavaVeld waardeVeld = new JavaVeld(javaVeldTypeVoorWaarde, "waarde");
        waardeVeld.setFinal(true);
        javaEnum.voegVeldToe(waardeVeld);

        // Voeg een omschrijving veld toe voor het vastleggen van de omschrijving
        final JavaVeld omschrijvingVeld = new JavaVeld(JavaType.STRING, "omschrijving");
        omschrijvingVeld.setFinal(true);
        javaEnum.voegVeldToe(omschrijvingVeld);

        // Voeg de interface toe
        javaEnum.voegSuperInterfaceToe(new JavaType(JavaType.VASTE_WAARDE_ATRRIBUUT_TYPE, waardeVeld.getType()));

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
