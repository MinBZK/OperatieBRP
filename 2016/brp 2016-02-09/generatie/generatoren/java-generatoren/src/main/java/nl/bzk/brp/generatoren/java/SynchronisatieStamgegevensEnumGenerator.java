/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaGenericParameter;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.springframework.stereotype.Component;

/**
 * Genereert een enumeratie van alle stamgegevens die gesynchroniseerd kunnen worden.
 */
@Component("synchronisatieStamgegevensEnumGenerator")
public class SynchronisatieStamgegevensEnumGenerator extends AbstractJavaGenerator  {

    private NaamgevingStrategie naamgevingStrategie = new AlgemeneNaamgevingStrategie();

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.SYNCHRONISATIE_STAMGEGEVENS_ENUM_GENERATOR;
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        final JavaEnumeratie javaEnum = new JavaEnumeratie(
                "SynchronisatieStamgegeven",
                "Een Enum benodigd voor de functionaliteit van synchronisatie stamgegevens. Deze enum bevat een mapping"
                        + "tussen een tabelnaam zoals de in het request wordt aangeduid in de parameters van het bericht"
                        + "en een stamgegeven klasse.",
                "nl.bzk.brp.model.algemeen.stamgegeven");

        // Een String veld voor de tabel naam.
        JavaVeld veld = new JavaVeld(JavaType.STRING, "tabelNaam");
        javaEnum.voegVeldToe(veld);
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        getter.setJavaDoc(genereerGetterJavadoc("tabelNaam", javaEnum.getNaam()));
        getter.setReturnWaardeJavaDoc("tabelNaam.");
        javaEnum.voegGetterToe(getter);

        // Een String veld voor de Entity class of enum class behorend bij het stamgegeven.
        JavaVeld veld1 = new JavaVeld(new JavaType(
                "Class", "java.lang", new JavaGenericParameter(JavaType.SYNCHRONISEERBAAR_STAMGEGEVEN, true)),
                                      "stamgegevenKlasse");
        javaEnum.voegVeldToe(veld1);
        final JavaAccessorFunctie getter1 = new JavaAccessorFunctie(veld1);
        getter1.setJavaDoc(genereerGetterJavadoc("tabelNaam", javaEnum.getNaam()));
        getter1.setReturnWaardeJavaDoc("tabelNaam.");
        javaEnum.voegGetterToe(getter1);

        //Constructor
        javaEnum.voegConstructorToe(genereerConstructorVoorEnum(javaEnum));

        //Genereer de waarden:
        genereerWaardenVoorSynchronisatieStamgegevenEnum(javaEnum);

        //Genereer een functie om bij een tabelnaam de juiste enum te vinden
        genereerVindEnumVoorTabelNaamFunctie(javaEnum);

        // Haal de juiste writer op.
        final JavaWriter<JavaEnumeratie> enumeratieWriter =
                javaWriterFactory(generatorConfiguratie, JavaEnumeratie.class);
        final List<JavaEnumeratie> javaEnumeraties = Arrays.asList(javaEnum);
        voegGeneratedAnnotatiesToe(javaEnumeraties, generatorConfiguratie);
        // Geef de gegenereerde enumeratie door aan de writer.
        final List<JavaEnumeratie> geschrevenEnumeraties = enumeratieWriter.genereerEnSchrijfWeg(javaEnumeraties, this);
        // Rapporteer over de gegenereerde enumeratie.
        rapporteerOverGegenereerdeJavaTypen(geschrevenEnumeraties);
    }

    private void genereerVindEnumVoorTabelNaamFunctie(final JavaEnumeratie javaEnum) {
        JavaFunctie functie = new JavaFunctie(
                JavaAccessModifier.PUBLIC,
                new JavaType("SynchronisatieStamgegeven", "nl.bzk.brp.model.algemeen.stamgegeven"),
                "vindEnumVoorTabelNaam",
                "De enum behorende bij stamgegevenTabelnaam.",
                true);
        functie.setJavaDoc("Vind bij een stamgegeven tabel naam zoals deze wordt aangeduid in het koppelvlak de"
                                   + " bijbehorende enumeratie.");

        functie.voegParameterToe(new JavaFunctieParameter("stamgegevenTabelnaam", JavaType.STRING));

        final StringBuilder body = new StringBuilder();
        body.append("for (SynchronisatieStamgegeven synchronisatieStamgegeven : values()) {")
                .append("if (synchronisatieStamgegeven.getTabelNaam().equalsIgnoreCase(stamgegevenTabelnaam)) {")
                .append("return synchronisatieStamgegeven;")
                .append("}")
                .append("}")
                .append("return null;");

        functie.setBody(body.toString());
        javaEnum.voegFunctieToe(functie);
    }

    private void genereerWaardenVoorSynchronisatieStamgegevenEnum(final JavaEnumeratie javaEnum) {
        final List<ObjectType> stamgegevensObjectTypen = new ArrayList<>();
        stamgegevensObjectTypen.addAll(getBmrDao().getDynamischeStamgegevensObjectTypen());
        stamgegevensObjectTypen.addAll(getBmrDao().getStatischeStamgegevensObjectTypen());

        for (ObjectType objectType : stamgegevensObjectTypen) {
            if (objectType.getInBericht() != null && objectType.getInBericht() == 'J') {
                genereerEnumWaardeVoorStamgegeven(javaEnum, objectType);
            }
        }
    }

    private void genereerEnumWaardeVoorStamgegeven(final JavaEnumeratie javaEnum, final ObjectType objectType) {
        final String enumNaam = objectType.getIdentCode().toUpperCase();
        final String tabelNaam = objectType.getIdentCode() + "Tabel";
        final String klasseNaam = objectType.getIdentCode();

        EnumeratieWaarde waarde = new EnumeratieWaarde(enumNaam, "De " + tabelNaam + ".");

        // Sync id als id van het database object.
        waarde.voegConstructorParameterToe(tabelNaam, true);
        waarde.voegConstructorParameterToe(klasseNaam + ".class", false);

        javaEnum.voegEnumeratieWaardeToe(waarde);

        javaEnum.voegExtraImportsToe(naamgevingStrategie.getJavaTypeVoorElement(objectType));
    }
}
