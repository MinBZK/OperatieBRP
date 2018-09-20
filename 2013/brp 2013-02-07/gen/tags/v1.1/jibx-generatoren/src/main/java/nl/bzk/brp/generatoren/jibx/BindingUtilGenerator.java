/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx;

import java.util.Arrays;
import java.util.List;

import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorConfiguratie;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.AbstractJavaGenerator;
import nl.bzk.brp.generatoren.java.model.Constructor;
import nl.bzk.brp.generatoren.java.model.JavaAccessModifier;
import nl.bzk.brp.generatoren.java.model.JavaFunctie;
import nl.bzk.brp.generatoren.java.model.JavaFunctieParameter;
import nl.bzk.brp.generatoren.java.model.JavaKlasse;
import nl.bzk.brp.generatoren.java.model.JavaType;
import nl.bzk.brp.generatoren.java.model.JavaVeld;
import nl.bzk.brp.generatoren.java.naamgeving.AlgemeneNaamgevingStrategie;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;
import nl.bzk.brp.generatoren.java.writer.JavaWriter;
import nl.bzk.brp.generatoren.jibx.util.JibxGeneratieUtil;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.Element;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;

import org.springframework.stereotype.Component;

/**
 * Genereert de binding util class. Deze klasse bevat allerlei serialisatie en deserialistatie functies t.b.v. de
 * JiBX bindings voor attribuuttypen.
 */
@Component("bindingUtilGenerator")
public class BindingUtilGenerator extends AbstractJavaGenerator {

    private static final int           AT_JA_ID                                  = 4584;
    private static final List<Integer> NIET_WAARDE_GEBRUIKENDE_ATTRIBUUTTYPE_IDS = Arrays.asList(AT_JA_ID);

    private NaamgevingStrategie naamgevingStrategie = new AlgemeneNaamgevingStrategie();

    private JavaKlasse bindingUtilKlasse = new JavaKlasse("BindingUtilAttribuutTypen",
        "Gegenereerde Binding util voor attribuuttypen.", "nl.bzk.brp.model.binding", true);

    @Override
    public void genereer(final GeneratorConfiguratie generatorConfiguratie) {
        List<AttribuutType> attribuutTypen = this.getBmrDao().getAttribuutTypen();
        for (AttribuutType attribuutType : attribuutTypen) {
            if (isSpecialeConversieNodigTussenXmlEnJava(attribuutType)) {
                bindingUtilKlasse.voegFunctieToe(genereerSerialisatieFunctieVoorAttribuutType(attribuutType));
                bindingUtilKlasse.voegFunctieToe(genereerDeserializerFunctieVoorAttribuutType(attribuutType));
            }
        }
        schrijfBindingUtilKlasse(generatorConfiguratie);
    }

    /**
     * Bepaalt of er een speciale conversie nodig is voor het opgegeven attribuuttype als deze van XML naar Java
     * en vice versa vertaald dient te worden. Dit is met name voor types die in XSD een string zijn en in Java niet,
     * types die behoren tot de StUF namespace en/of attribuuttypes met vaste waardes.
     *
     * @param attribuutType het attribuuttype.
     * @return of er een speciale conversie nodig is voor het attribuuttype.
     */
    private boolean isSpecialeConversieNodigTussenXmlEnJava(final AttribuutType attribuutType) {
        boolean resultaat = false;

        if (attribuutType.getBasisType().getId() != ID_BASISTYPE_ID) {
            final Element xsdType = getXsdTypeVoorAttribuutType(attribuutType);
            final JavaType javaType = getJavaTypeVoorAttribuutType(attribuutType);
            resultaat =
                JibxGeneratieUtil.bepaalIndienAttribuutTypeSpecialeConversieVereistTussenXmlEnJava(xsdType, javaType);

            if (!getWaardesVoorAttribuutType(attribuutType).isEmpty()) {
                resultaat = true;
            }
        }
        return resultaat;
    }

    @Override
    protected GeneratorNaam getGeneratorNaamVoorRapportage() {
        return GeneratorNaam.BINDING_UTIL_GENERATOR;
    }

    /** {@inheritDoc}. */
    @Override
    protected JavaType getJavaTypeVoorAttribuutType(final AttribuutType attribuutType) {
        final BasisType defaultJavaType =
            getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.JAVA).getBasisType();
        return JavaGeneratieUtil.bepaalJavaBasisTypeVoorAttribuutType(attribuutType, defaultJavaType);
    }

    /**
     * Retourneert het XSD type voor het opgegeven attribuuttype.
     *
     * @param attribuutType het attribuuttype waarvoor het XSD type gevraagd wordt.
     * @return het XSD type dat gebruikt dient te worden voor opgegeven attribuuttype.
     */
    private Element getXsdTypeVoorAttribuutType(final AttribuutType attribuutType) {
        return getBmrDao().getBasisTypeVoorAttribuutType(attribuutType, BmrTargetPlatform.XSD).getTypeImpl()
            .getElementByBasistype();
    }

    /**
     * Retourneert een lijst van Waarderegel waardes (mogelijk waardes voor een element) voor het opgegeven
     * attribuuttype. Indien er geen vaste waardes voor het attribuuttype zijn (dus geen waarderegels), dan is de
     * lijst leeg.
     *
     * @param attribuutType het attribuuttype waarvoor de lijst van waardes wordt opgevraagd..
     * @return de (potentieel lege) lijst van waardes die gelden voor opgegeven attribuuttype.
     */
    private List<WaarderegelWaarde> getWaardesVoorAttribuutType(final AttribuutType attribuutType) {
        return getBmrDao().getWaardeEnumeratiesVoorElement(attribuutType, false, true);
    }

    /**
     * Schrijft de gegenereerde BindingUtil klasse weg als Java source bestand.
     *
     * @param generatorConfiguratie de generator configuratie die gebruikt dient te worden.
     */
    private void schrijfBindingUtilKlasse(final GeneratorConfiguratie generatorConfiguratie) {
        final JavaWriter<JavaKlasse> klasseWriter = javaWriterFactory(generatorConfiguratie, JavaKlasse.class);
        final Constructor constructor = new Constructor(JavaAccessModifier.PRIVATE, bindingUtilKlasse.getNaam());
        constructor.setJavaDoc("Private constructor, want dit is een utility klasse.");
        bindingUtilKlasse.voegConstructorToe(constructor);
        final List<JavaKlasse> gegenereerdeKlassen =
            klasseWriter.genereerEnSchrijfWeg(Arrays.asList(bindingUtilKlasse), this);
        rapporteerOverGegenereerdeJavaTypen(gegenereerdeKlassen);
    }

    /**
     * Genereert een jibx serialisatie functie voor een attribuut typen.
     *
     * @param attribuutType het attribuut type wat ge-serialiseert moet worden.
     * @return serialisatie functie.
     */
    private JavaFunctie genereerSerialisatieFunctieVoorAttribuutType(final AttribuutType attribuutType) {
        final Element xsdType = getXsdTypeVoorAttribuutType(attribuutType);
        final JavaType javaType = getJavaTypeVoorAttribuutType(attribuutType);

        final JavaFunctie serialisatieFunctie = bouwSerialisatieFunctie(attribuutType);

        final String paramNaam = GeneratieUtil.lowerTheFirstCharacter(
            naamgevingStrategie.getJavaTypeVoorElement(attribuutType).getNaam());
        JavaType paramType = naamgevingStrategie.getJavaTypeVoorElement(attribuutType);

        final String serialisatieBody;
        if (heeftSpecifiekeSerialisatie(attribuutType)) {
            serialisatieBody = bouwSerialisatieBodyVoorSpecifiekAttribuuttype(attribuutType, paramNaam);
        } else if (gebruiktVasteWaardesDirectInBericht(attribuutType)) {
            serialisatieBody = bouwSerialisatieBodyVoorVasteWaardeAttribuuttype(paramNaam);
        } else if (JibxGeneratieUtil.isStufBasisType(xsdType)) {
            serialisatieBody = bouwSerialisatieBodyVoorStufBasisType(xsdType, paramNaam);
            paramType = javaType;
        } else {
            serialisatieBody = bouwSerialisatieBodyVoorStandaardAttribuuttype(attribuutType, javaType, paramNaam);
            paramType = javaType;
        }
        serialisatieFunctie.setBody(serialisatieBody);
        serialisatieFunctie.voegParameterToe(new JavaFunctieParameter(paramNaam, paramType,
            "Om te zetten " + attribuutType.getNaam()));
        return serialisatieFunctie;
    }

    /**
     * Bouwt een {@link JavaFunctie} instantie met opgegeven naam voor een conversie methode. De javadoc zal gebaseerd
     * zijn op de opgegeven namen van de elementen van waar naar waartoe geconverteerd moet worden, en verder zal het
     * geretourneerde type worden gezet naar het opgegeven type.
     *
     * @param naam de naam van de methode.
     * @param javaDocVanAttribuut naam in de javadoc van het attribuut dat geconverteerd dient te worden.
     * @param javaDocNaarAttribuut naam in de javadoc van het attribuut waarnaar geconverteerd dient te worden.
     * @param returnWaardeJavaDoc de javaDoc voor de return waarde.
     * @param returnType het Java type dat de methode retourneert.
     * @return een Java Functie instantie.
     */
    private JavaFunctie bouwFunctie(final String naam, final String javaDocVanAttribuut,
        final String javaDocNaarAttribuut, final String returnWaardeJavaDoc, final JavaType returnType)
    {
        final JavaFunctie functie = new JavaFunctie(JavaAccessModifier.PUBLIC, naam);
        functie.setJavaDoc(String.format("Zet een %s om naar een %s.", javaDocVanAttribuut, javaDocNaarAttribuut));
        functie.setReturnWaardeJavaDoc(returnWaardeJavaDoc);
        functie.setStatic(true);
        functie.setReturnType(returnType);
        return functie;
    }

    /**
     * Bouwt een standaard functie instantie ten behoeve van serialisatie van het opgegeven attribuuttype. Zaken als
     * de javadoc en signature van de Java functie worden in deze methode al gezet.
     *
     * @param attribuutType het attribuuttype waarvoor de functie moet worden geinstantieerd.
     * @return een Java Functie instantie.
     */
    private JavaFunctie bouwSerialisatieFunctie(final AttribuutType attribuutType) {
        final String functieNaam = GeneratieUtil.lowerTheFirstCharacter(attribuutType.getIdentCode()) + "NaarXml";
        final String returnWaardeJavaDoc =
            String.format("Xml string representatie van een %s.", attribuutType.getNaam());
        return bouwFunctie(functieNaam, attribuutType.getNaam(), "xml string", returnWaardeJavaDoc, JavaType.STRING);
    }

    /**
     * Retourneert of het opgegeven attribuuttype een geheel eigen/specifieke serialisatie methode vereist of niet.
     * Hierbij wordt met eigen/specifiek bedoeld dat de serialisatie niet gebaseerd is op de binnen het model en/of
     * BMR bekende informatie, maar dus specifiek geschreven dient te worden.
     *
     * @param attribuutType het attribuuttype dat geserialiseerd dient te worden.
     * @return of het attribuuttype een specifeke serialisatie vereist.
     */
    private boolean heeftSpecifiekeSerialisatie(final AttribuutType attribuutType) {
        return NIET_WAARDE_GEBRUIKENDE_ATTRIBUUTTYPE_IDS.contains(Integer.valueOf(attribuutType.getId()));
    }

    /**
     * Retourneert of het opgegeven attribuuttype vaste waardes heeft en of deze dan ook gebruikt worden in het bericht
     * of niet.
     *
     * @param attribuutType het attribuuttype dat gecontroleerd dient te worden.
     * @return of het attribuuttype vaste waardes heeft die in het bericht gebruikte worden of niet.
     */
    private boolean gebruiktVasteWaardesDirectInBericht(final AttribuutType attribuutType) {
        return !getWaardesVoorAttribuutType(attribuutType).isEmpty()
            && !NIET_WAARDE_GEBRUIKENDE_ATTRIBUUTTYPE_IDS.contains(Integer.valueOf(attribuutType.getId()));
    }

    /**
     * Methode die de body (de code) retourneert voor de serialisatie methode van "specifieke" attribuuttypes.
     * Standaard is dit een call naar een utility class waar dan een methode wordt aangeroepen specifiek voor het
     * opgegeven attribuuttype. Hierbij wordt met specifiek bedoeld dat de serialisatie niet gebaseerd is op de binnen
     * het model en/of BMR bekende informatie, maar dus specifiek geschreven dient te worden.
     *
     * @param attribuutType het attribuuttype dat geserialiseerd dient te worden.
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @return de body (code) voor een serialisatie functie.
     */
    private String bouwSerialisatieBodyVoorSpecifiekAttribuuttype(final AttribuutType attribuutType,
        final String paramNaam)
    {
        return String.format("return BindingUtil.%sNaarString(%s);", GeneratieUtil.lowerTheFirstCharacter(
            attribuutType.getIdentCode()), paramNaam);
    }

    /**
     * Methode die de body (de code) retourneert voor de deserialisatie methode van "specifieke" attribuuttypes.
     * Standaard is dit een call naar een utility class waar dan een methode wordt aangeroepen specifiek voor het
     * opgegeven attribuuttype. Hierbij wordt met specifiek bedoeld dat de deserialisatie niet gebaseerd is op de
     * binnen het model en/of BMR bekende informatie, maar dus specifiek geschreven dient te worden.
     *
     * @param attribuutType het attribuuttype dat gedeserialiseerd dient te worden.
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @return de body (code) voor een deserialisatie functie.
     */
    private String bouwDeserialisatieBodyVoorSpecifiekAttribuuttype(final AttribuutType attribuutType,
        final String paramNaam)
    {
        return String.format("return BindingUtil.stringNaar%s(%s);", attribuutType.getIdentCode(), paramNaam);
    }

    /**
     * Methode die de body (de code) retourneert voor de serialisatie methode van attribuuttypes die een vaste waarde
     * reeks hebben (een reeks die in het BMR en het Model is vastgelegd als enumeratie).
     *
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @return de body (code) voor een serialisatie functie.
     */
    private String bouwSerialisatieBodyVoorVasteWaardeAttribuuttype(final String paramNaam) {
        return String.format("return BindingUtil.enumeratiewaardeNaarString(%s);", paramNaam);
    }

    /**
     * Methode die de body (de code) retourneert voor de deserialisatie methode van attribuuttypes die een vaste waarde
     * reeks hebben (een reeks die in het BMR en het Model is vastgelegd als enumeratie).
     *
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @param returnType het Java type dat geretourneerd wordt door de deserialisatie functie.
     * @return de body (code) voor een deserialisatie functie.
     */
    private String bouwDeserialisatieBodyVoorVasteWaardeAttribuuttype(final String paramNaam,
        final JavaType returnType)
    {
        return String
            .format("return BindingUtil.stringNaarEnumeratiewaarde(%s, %s.class);", paramNaam, returnType.getNaam());
    }

    /**
     * Methode die de body (de code) retourneert voor de serialisatie methode van attribuuttypes die in het bericht
     * een StUF basistype gebruiken.
     *
     * @param xsdType het StUF basistype element waarvoor de serialisatie plaats dient te vinden.
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @return de body (code) voor een serialisatie functie.
     */
    private String bouwSerialisatieBodyVoorStufBasisType(final Element xsdType, final String paramNaam) {
        final String body;
        if (JibxGeneratieUtil.ID_BASISTYPE_STUF_DATUM == xsdType.getId()
            || JibxGeneratieUtil.ID_BASISTYPE_STUF_ONVOLLEDIGE_DATUM == xsdType.getId())
        {
            body = String.format("return BindingUtil.datumAlsGetalNaarDatumAlsString(%s);", paramNaam);
        } else if (JibxGeneratieUtil.ID_BASISTYPE_STUF_DATUM_TIJD == xsdType.getId()) {
            body = String.format("return BindingUtil.javaDateNaarW3cDatumString(%s);", paramNaam);
        } else {
            throw new IllegalArgumentException("Geen conversie code gedefinieerd voor stuf basis type: "
                + xsdType.getNaam());
        }
        return body;
    }

    /**
     * Methode die de body (de code) retourneert voor de deserialisatie methode van attribuuttypes die in het bericht
     * een StUF basistype gebruiken.
     *
     * @param xsdType het StUF basistype element waarvoor de deserialisatie plaats dient te vinden.
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @return de body (code) voor een deserialisatie functie.
     */
    private String bouwDeserialisatieBodyVoorStufBasisType(final Element xsdType, final String paramNaam) {
        final String body;
        if (JibxGeneratieUtil.ID_BASISTYPE_STUF_DATUM == xsdType.getId()
            || JibxGeneratieUtil.ID_BASISTYPE_STUF_ONVOLLEDIGE_DATUM == xsdType.getId())
        {
            body = String.format("return BindingUtil.datumAlsStringNaarDatumAlsGetal(%s);", paramNaam);
        } else if (JibxGeneratieUtil.ID_BASISTYPE_STUF_DATUM_TIJD == xsdType.getId()) {
            body = String.format("return BindingUtil.w3cDatumStringNaarJavaDate(%s);", paramNaam);
        } else {
            throw new IllegalArgumentException("Geen conversie code gedefinieerd voor stuf basis type: "
                + xsdType.getNaam());
        }
        return body;
    }

    /**
     * Methode die de body (de code) retourneert voor de serialisatie methode van standaard attribuuttypes. Hierbij
     * wordt de wijze van serialisatie met name bepaald door het type van het attribuuttype in Java.
     *
     * @param attribuutType het attribuuttype dat geserialiseerd dient te worden.
     * @param javaType het Java type waarvan het attribuut omgezet dient te worden.
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @return de body (code) voor een serialisatie functie.
     */
    private String bouwSerialisatieBodyVoorStandaardAttribuuttype(final AttribuutType attribuutType,
        final JavaType javaType, final String paramNaam)
    {
        final String body;
        if (javaType.equals(JavaType.BOOLEAN)) {
            body = String.format("return BindingUtil.booleanNaarString(%s);", paramNaam);
        } else if (javaType.isNumeriek()) {
            Integer minimumLengte = attribuutType.getMinimumLengte();
            if (minimumLengte == null) {
                minimumLengte = 0;
            }

            String constanteNaam = voegConstanteToeAanKlasse(attribuutType, minimumLengte);
            body = String.format("return BindingUtil.getalNaarStringMetVoorloopnullen(%s, %s);",
                paramNaam, constanteNaam);
        } else {
            body = bouwSerialisatieBodyVoorSpecifiekAttribuuttype(attribuutType, paramNaam);
        }
        return body;
    }

    /**
     * Voegt een constante toe (een {@link JavaVeld}) met de opgegeven minimumlengte als waarde voor het opgegeven
     * attribuuttype.
     *
     * @param attribuutType het attribuuttype waarvoor een constante voor de minimumlengte moet worden toegevoegd.
     * @param minimumLengte de minimum lengte voor het opgegeven attribuuttype.
     * @return de naam van het toegevoegde constante/veld.
     */
    private String voegConstanteToeAanKlasse(final AttribuutType attribuutType, final Integer minimumLengte) {
        final JavaVeld minLengteVeld = new JavaVeld(JavaType.INTEGER, String.format("MIN_LENGTE_%s",
            attribuutType.getIdentCode().toUpperCase()));
        minLengteVeld.setStatic(true);
        minLengteVeld.setFinal(true);
        minLengteVeld.setGeinstantieerdeWaarde(minimumLengte.toString());
        bindingUtilKlasse.voegVeldToe(minLengteVeld);

        return minLengteVeld.getNaam();
    }

    /**
     * Methode die de body (de code) retourneert voor de deserialisatie methode van standaard attribuuttypes. Hierbij
     * wordt de wijze van deserialisatie met name bepaald door het type van het attribuuttype in Java.
     *
     * @param attribuutType het attribuuttype dat gedeserialiseerd dient te worden.
     * @param javaType het Java type waarvan het attribuut omgezet dient te worden.
     * @param paramNaam de parameter naam die gebruikt dient te worden in de body.
     * @return de body (code) voor een deserialisatie functie.
     */
    private String bouwDeserialisatieBodyVoorStandaardAttribuuttype(final AttribuutType attribuutType,
        final JavaType javaType, final String paramNaam)
    {
        final String body;
        if (javaType.equals(JavaType.BOOLEAN) || javaType.isNumeriek()) {
            body = String.format("return BindingUtil.stringNaar%s(%s);", javaType.getNaam(), paramNaam);
        } else {
            body = bouwDeserialisatieBodyVoorSpecifiekAttribuuttype(attribuutType, paramNaam);
        }
        return body.toString();
    }

    /**
     * Bouwt een standaard functie instantie ten behoeve van serialisatie van het opgegeven attribuuttype. Zaken als
     * de javadoc en signature van de Java functie worden in deze methode al gezet.
     *
     * @param attribuutType het attribuuttype waarvoor de functie moet worden geinstantieerd.
     * @param returnType het Java type dat geretourneerd wordt door de deserialisatie functie.
     * @return een Java Functie instantie.
     */
    private JavaFunctie bouwDeserialisatieFunctie(final AttribuutType attribuutType, final JavaType returnType) {
        final String functieNaam = "xmlNaar" + attribuutType.getIdentCode();
        final String returnWaardeJavaDoc =
            String.format("waarde van een %s.", attribuutType.getNaam());
        return bouwFunctie(functieNaam, "xml string", attribuutType.getNaam(), returnWaardeJavaDoc, returnType);
    }

    /**
     * Genereert een jibx de-serialisatie functie voor dit attribuut type.
     *
     * @param attribuutType het attribuut type wat gedeserialiseerd dient te worden.
     * @return de-serialisatie functie.
     */
    private JavaFunctie genereerDeserializerFunctieVoorAttribuutType(final AttribuutType attribuutType) {
        final Element xsdType = getXsdTypeVoorAttribuutType(attribuutType);
        final JavaType javaType = getJavaTypeVoorAttribuutType(attribuutType);

        final String paramNaam = "xml";
        final JavaType returnType;

        final String deserialisatieBody;
        if (heeftSpecifiekeSerialisatie(attribuutType)) {
            returnType = naamgevingStrategie.getJavaTypeVoorElement(attribuutType);
            deserialisatieBody = bouwDeserialisatieBodyVoorSpecifiekAttribuuttype(attribuutType, paramNaam);
        } else if (gebruiktVasteWaardesDirectInBericht(attribuutType)) {
            returnType = naamgevingStrategie.getJavaTypeVoorElement(attribuutType);
            deserialisatieBody = bouwDeserialisatieBodyVoorVasteWaardeAttribuuttype(paramNaam, returnType);
        } else if (JibxGeneratieUtil.isStufBasisType(xsdType)) {
            returnType = javaType;
            deserialisatieBody = bouwDeserialisatieBodyVoorStufBasisType(xsdType, paramNaam);
        } else {
            returnType = javaType;
            deserialisatieBody =
                bouwDeserialisatieBodyVoorStandaardAttribuuttype(attribuutType, javaType, paramNaam);
        }

        final JavaFunctie deserialisatieFunctie = bouwDeserialisatieFunctie(attribuutType, returnType);
        deserialisatieFunctie.voegParameterToe(new JavaFunctieParameter(paramNaam, JavaType.STRING, "xml string."));
        deserialisatieFunctie.setBody(deserialisatieBody);
        return deserialisatieFunctie;
    }

}
