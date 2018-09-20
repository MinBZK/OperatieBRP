/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.algemeen.dataaccess.BmrElementFilterObject;
import nl.bzk.brp.generatoren.java.model.EnumeratieWaarde;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaAccessorFunctie;
import nl.bzk.brp.generatoren.java.model.JavaEnumeratie;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.util.JavaGeneratorConstante;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.ObjectType;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Component;

/**
 * Generator die de Java enum maakt voor database objecten. Database objecten zijn een statisch stamgegeven,
 * maar kunnen niet via de normale weg gegenereerd worden, aangezien er geen tuples of waarderegels zijn
 * voor database objecten. Dat komt weer doordat het hier om meta-informatie gaat, namelijk de tabellen
 * en kolommen van de database zelf.
 */
@Component("databaseObjectJavaGenerator")
@Deprecated
public class DatabaseObjectGenerator extends AbstractJavaGenerator {

    /** De basis naam van de target enum klasse. */
    private static final String DATABASE_OBJECT_ENUM_BASIS_NAAM = "DatabaseObject";
    /** Het basis package van de target enum klasse. */
    private static final String DATABASE_OBJECT_ENUM_BASIS_PACKAGE = "nl.bzk.brp.model.algemeen.stamgegeven.";
    /** De te includen schemas. */
    private static final List<String> INCLUDE_SCHEMAS = Arrays.asList("Kern", "Lev", "AutAut");

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.DATABASE_OBJECT_GENERATOR;
    }

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        Map<String, JavaEnumeratie> enumsPerSchema = bouwEnumeratieKlassen();

        List<ObjectType> operationeleObjectTypen =
                this.getBmrDao().getObjectTypen(new BmrElementFilterObject(BmrLaag.OPERATIONEEL));
        for (ObjectType operationeelObjectType : operationeleObjectTypen) {
            // We maken alleen database objects voor bepaalde schemas.
            String schemaNaam = operationeelObjectType.getSchema().getNaam();
            if (INCLUDE_SCHEMAS.contains(schemaNaam)) {
                JavaEnumeratie javaEnum = enumsPerSchema.get(schemaNaam);
                voegEnumWaardeToeVoorObjectType(javaEnum, operationeelObjectType);
                for (Attribuut attribuut : this.getBmrDao().getAttributenVanObjectType(operationeelObjectType)) {
                    voegEnumWaardeToeVoorAttribuut(javaEnum, attribuut);
                }
            }
        }
        for (JavaEnumeratie javaEnum : enumsPerSchema.values()) {
            this.voegUtilityFunctiesToe(javaEnum);
        }

        // Haal de juiste writer op.
        final JavaWriter<JavaEnumeratie> enumeratieWriter =
                javaWriterFactory(generatorConfiguratie, JavaEnumeratie.class);
        final ArrayList<JavaEnumeratie> javaEnumeraties = new ArrayList<>(enumsPerSchema.values());
        voegGeneratedAnnotatiesToe(javaEnumeraties, generatorConfiguratie);
        // Geef de gegenereerde enumeratie door aan de writer.
        final List<JavaEnumeratie> geschrevenEnumeraties = enumeratieWriter.genereerEnSchrijfWeg(javaEnumeraties, this);
        // Rapporteer over de gegenereerde enumeratie.
        rapporteerOverGegenereerdeJavaTypen(geschrevenEnumeraties);
    }

    /**
     * Maak per te genereren schema een JavaEnumeratie aan en stop ze in een map
     * met de schema naam als key.
     *
     * @return de enums
     */
    private Map<String, JavaEnumeratie> bouwEnumeratieKlassen() {
        Map<String, JavaEnumeratie> enums = new HashMap<>();
        for (String schemaNaam : INCLUDE_SCHEMAS) {
            JavaType javaType = new JavaType(DATABASE_OBJECT_ENUM_BASIS_NAAM + schemaNaam,
                    DATABASE_OBJECT_ENUM_BASIS_PACKAGE + schemaNaam.toLowerCase());
            final JavaEnumeratie javaEnum = new JavaEnumeratie(javaType, "");
            javaEnum.setJavaDoc(bouwJavadocVoorElement(getBmrDao().getElement(ID_DATABASE_OBJECT, ObjectType.class))
                    + newline()
                    + "Deze enum is voor alle database objecten uit het " + schemaNaam + " schema.");
            javaEnum.voegSuperInterfaceToe(JavaType.DATABASE_OBJECT);
            javaEnum.voegSuperInterfaceToe(JavaType.PERSISTENT_ENUM);
            this.voegVeldEnGetterToeAanEnum(javaEnum, JavaType.INTEGER, "id");
            this.voegVeldEnGetterToeAanEnum(javaEnum, JavaType.STRING, "naam");
            this.voegVeldEnGetterToeAanEnum(javaEnum, JavaType.STRING, "databaseNaam");
            this.voegVeldEnGetterToeAanEnum(javaEnum, javaType, "ouder");
            javaEnum.voegConstructorToe(this.genereerConstructorVoorEnum(javaEnum));
            enums.put(schemaNaam, javaEnum);
        }
        return enums;
    }

    /**
     * Voeg een veld en een getter toe aan de meegegeven enum,
     * van het meegegeven type en met de meegegeven naam.
     *
     * @param javaEnum de enum
     * @param type het type van het veld
     * @param naam de naam van het veld
     */
    private void voegVeldEnGetterToeAanEnum(final JavaEnumeratie javaEnum, final JavaType type, final String naam) {
        JavaVeld veld = new JavaVeld(type, naam);
        javaEnum.voegVeldToe(veld);

        // Elk veld krijgt een getter.
        final JavaAccessorFunctie getter = new JavaAccessorFunctie(veld);
        getter.setJavaDoc(genereerGetterJavadoc(naam, javaEnum.getNaam()));
        getter.setReturnWaardeJavaDoc(naam + ".");
        javaEnum.voegGetterToe(getter);
    }

    /**
     * Voeg een enum waarde toe voor het object type.
     *
     * @param javaEnum de enum
     * @param operationeelObjectType het object type
     */
    private void voegEnumWaardeToeVoorObjectType(
            final JavaEnumeratie javaEnum, final ObjectType operationeelObjectType)
    {
        this.voegEnumWaardeToe(javaEnum, operationeelObjectType, "tabel", true);
    }

    /**
     * Voeg een enum waarde toe voor het attribuut.
     *
     * @param javaEnum de enum
     * @param attribuut het attribuut
     */
    private void voegEnumWaardeToeVoorAttribuut(final JavaEnumeratie javaEnum, final Attribuut attribuut) {
        this.voegEnumWaardeToe(javaEnum, attribuut, "kolom", false);
    }

    /**
     * Voeg een enum waarde toe voor dit element (object type of attribuut).
     *
     * @param javaEnum de enum
     * @param element het element
     * @param soortNaam het soort element (object type = tabel of attribuut = kolom)
     * @param isObjectType of het een object type is of niet (attribuut)
     */
    private void voegEnumWaardeToe(final JavaEnumeratie javaEnum, final GeneriekElement element,
            final String soortNaam, final boolean isObjectType)
    {
        GeneriekElement ouder = null;
        String enumNaam = this.genereerEnumNaam(element);
        // Volledig uitgeschreven naam van het database object.
        String naam = element.getNaam();
        // Naam van het database object in de database (zonder spaties en meestal afgekort).
        String dbNaam = element.getIdentDb();
        String enumJavaDoc = String.format("Representeert de %1$s: '%2$s'", soortNaam, element.getIdentDb());
        // Als het om een kolom gaat, neem dan de ouder (= tabel) mee in de naamgeving.
        // Zowel voor de duidelijkheid als ook ter voorkoming van dubbele enum waardes.
        if (!isObjectType) {
            ouder = element.getGeneriekElementOuder();
            enumNaam = this.genereerEnumNaam(ouder) + "__" + enumNaam;
            naam = ouder.getNaam() + " - " + naam;
            dbNaam = ouder.getIdentDb() + "." + dbNaam;
            enumJavaDoc += String.format(" van de tabel: '%1$s'", ouder.getIdentDb());
        }
        // Escape de letterlijke string om Java compile fouten te voorkomen.
        naam = StringEscapeUtils.escapeJava(naam);
        // Zet en punt achter de javadoc.
        enumJavaDoc += ".";

        EnumeratieWaarde waarde = new EnumeratieWaarde(enumNaam, enumJavaDoc);

        // Sync id als id van het database object.
        waarde.voegConstructorParameterToe(element.getSyncid().toString(), false);
        waarde.voegConstructorParameterToe(naam, true);
        waarde.voegConstructorParameterToe(dbNaam, true);
        if (!isObjectType) {
            // Ouder van een kolom: de tabel waar hij deel van uit maakt.
            waarde.voegConstructorParameterToe(this.genereerEnumNaam(ouder), false);
        } else {
            // Tabellen hebben geen ouder, maar parameter is wel verplicht.
            waarde.voegConstructorParameterToe("null", false);
        }

        javaEnum.voegEnumeratieWaardeToe(waarde);
    }

    /**
     * Genereer een enum naam voor een generiek element die aan de Java syntax voldoet.
     * Gebruikte de ident code van het element en roept daarmee een util methode aan.
     *
     * @param element het element
     * @return de enum naam
     */
    private String genereerEnumNaam(final GeneriekElement element) {
        return JavaGeneratieUtil.genereerNaamVoorEnumWaarde(element.getIdentCode());
    }

    /**
     * Voeg enkele utility functies toe, die het gebruik van de enum vergemakkelijken.
     *
     * @param javaEnum de enum
     */
    private void voegUtilityFunctiesToe(final JavaEnumeratie javaEnum) {
        JavaFunctie findByIdFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, "findById");
        findByIdFunctie.setStatic(true);
        findByIdFunctie.setJavaDoc("Geeft het database object met een bepaald id terug.");
        String idParameterNaam = "id";
        findByIdFunctie.voegParameterToe(new JavaFunctieParameter(idParameterNaam, JavaType.INTEGER,
                "het id van het gezochte database object"));
        findByIdFunctie.setReturnType(javaEnum.getType());
        findByIdFunctie.setReturnWaardeJavaDoc("het java type of null indien niet gevonden");
        final StringBuilder bodyBuilder = new StringBuilder();
        String zoekVariabele = GeneratieUtil.lowerTheFirstCharacter(DATABASE_OBJECT_ENUM_BASIS_NAAM);
        bodyBuilder.append(String.format("%1$s %2$s = null;%3$s", javaEnum.getType().getNaam(),
                zoekVariabele, JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        String loopVariabele = "enumWaarde";
        bodyBuilder.append(String.format("for (%1$s %2$s : %1$s.values()) {%3$s",
                javaEnum.getType().getNaam(), loopVariabele, newline()));
        bodyBuilder.append(String.format("if (enumWaarde.getId().equals(%1$s)) {%2$s",
                idParameterNaam, JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        bodyBuilder.append(String.format("%1$s = %2$s;%3$s",
                        zoekVariabele, loopVariabele, JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        bodyBuilder.append(String.format("break;%1$s", JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        bodyBuilder.append(String.format("}%1$s", JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        bodyBuilder.append(String.format("}%1$s", JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));
        bodyBuilder.append(String.format("return %1$s;%2$s",
                zoekVariabele, JavaGeneratorConstante.NEWLINE_OS_ONAFHANKELIJK.getWaarde()));

        findByIdFunctie.setBody(bodyBuilder.toString());
        javaEnum.voegFunctieToe(findByIdFunctie);

        JavaFunctie isTabelFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, "isTabel");
        isTabelFunctie.setJavaDoc("Geeft aan of dit database object een tabel is of niet.");
        isTabelFunctie.setReturnType(JavaType.BOOLEAN);
        isTabelFunctie.setReturnWaardeJavaDoc("tabel (true) of kolom (false)");
        isTabelFunctie.setBody("return this.ouder == null;");
        javaEnum.voegFunctieToe(isTabelFunctie);

        JavaFunctie isKolomFunctie = new JavaFunctie(JavaAccessModifier.PUBLIC, "isKolom");
        isKolomFunctie.setJavaDoc("Geeft aan of dit database object een kolom is of niet.");
        isKolomFunctie.setReturnType(JavaType.BOOLEAN);
        isKolomFunctie.setReturnWaardeJavaDoc("kolom (true) of tabel (false)");
        isKolomFunctie.setBody("return this.ouder != null;");
        javaEnum.voegFunctieToe(isKolomFunctie);
    }

}
