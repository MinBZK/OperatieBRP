/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.Generator;
import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


/**
 * Generieke (dus platform/doel onafhankelijke) helper klasse die o.a. methodes aanbiedt voor zaken als omzetting van
 * BMR specifieke zaken naar generator algemene zaken.
 */
public final class GeneratieUtil {

    /**
     * Private constructor.
     */
    private GeneratieUtil() {
    }

    /**
     * In het BMR kennen we voor JaNee velden de waarden 'J' en 'N', deze functie vertaal zo'n waarden naar een java
     * boolean. Indien het bmr JaNee veld <code>null</code> is, dan wordt de opgegeven standaard waarde geretourneerd.
     *
     * @param bmrJaNee  De Ja/Nee waarde uit het BMR.
     * @param standaard De standaard waarde die gebruikt dient te worden indien het veld <code>null</code> is.
     * @return true voor 'J', false voor 'N', anders de standaard waarde.
     */
    public static boolean bmrJaNeeNaarBoolean(final String bmrJaNee, final boolean standaard) {
        final boolean resultaat;

        if ("J".equals(bmrJaNee)) {
            resultaat = true;
        } else if ("N".equals(bmrJaNee)) {
            resultaat = false;
        } else {
            resultaat = standaard;
        }
        return resultaat;
    }

    /**
     * In het BMR kennen we voor JaNee velden de waarden 'J' en 'N', deze functie vertaal zo'n waarden naar een java
     * boolean. Indien het bmr JaNee veld <code>null</code> is, dan wordt de opgegeven standaard waarde geretourneerd.
     *
     * @param bmrJaNee  De Ja/Nee waarde uit het BMR.
     * @param standaard De standaard waarde die gebruikt dient te worden indien het veld <code>null</code> is.
     * @return true voor 'J', false voor 'N', anders de standaard waarde.
     */
    public static boolean bmrJaNeeNaarBoolean(final Character bmrJaNee, final boolean standaard) {
        if (bmrJaNee != null) {
            return bmrJaNeeNaarBoolean(String.valueOf(bmrJaNee), standaard);
        } else {
            return standaard;
        }
    }

    /**
     * In het BMR kennen we voor JaNee velden de waarden 'J' en 'N', deze functie vertaal zo'n waarden naar een java
     * boolean.
     *
     * @param bmrJaNee De Ja/Nee waarde uit het BMR.
     * @return true voor 'J', false voor 'N', anders een exceptie om vreemde waarden in het BMR te detecteren.
     */
    public static Boolean bmrJaNeeNaarBoolean(final String bmrJaNee) {
        if ("J".equals(bmrJaNee)) {
            return true;
        } else if ("N".equals(bmrJaNee)) {
            return false;
        } else {
            throw new IllegalArgumentException("Ja/Nee waarde wordt niet ondersteund: " + bmrJaNee);
        }
    }

    /**
     * In het BMR kennen we voor JaNee velden de waarden 'J' en 'N', deze functie vertaal zo'n waarden naar een java
     * boolean.
     *
     * @param bmrJaNee De Ja/Nee waarde uit het BMR.
     * @return true voor 'J', false voor 'N', anders een exceptie om vreemde waarden in het BMR te detecteren.
     */
    public static Boolean bmrJaNeeNaarBoolean(final char bmrJaNee) {
        return bmrJaNeeNaarBoolean(String.valueOf(bmrJaNee));
    }

    /**
     * Hoofdletter maken van het eerste karakter van de input string.
     *
     * @param input the input
     * @return the string
     */
    public static String upperTheFirstCharacter(final String input) {
        return StringUtils.capitalize(input);
    }

    /**
     * Kleine letter maken van het eerste karakter van de input string.
     *
     * @param input the input
     * @return the string
     */
    public static String lowerTheFirstCharacter(final String input) {
        return StringUtils.uncapitalize(input);
    }

    /**
     * Sorteer een lijst van generiek elementen op naam.
     *
     * @param <E>       het specifiek type generiek element
     * @param elementen de elementen
     */
    public static <E extends GeneriekElement> void sorteerOpNaam(final List<E> elementen) {
        Collections.sort(elementen, new Comparator<E>() {
            @Override
            public int compare(final E element1, final E element2) {
                return element1.getNaam().compareTo(element2.getNaam());
            }
        });
    }

    /**
     * Sorteer een lijst van generiek elementen op ident code.
     *
     * @param <E>       het specifiek type generiek element
     * @param elementen de elementen
     */
    public static <E extends GeneriekElement> void sorteerOpIdentCode(final List<E> elementen) {
        Collections.sort(elementen, new Comparator<E>() {
            @Override
            public int compare(final E element1, final E element2) {
                return element1.getIdentCode().compareTo(element2.getIdentCode());
            }
        });
    }

    /**
     * Maak een identifier van een naam
     * Let op: tijdelijke methode met beperkte functionaliteit!
     *
     * @param input naam
     * @return identifier
     */
    //TODO: Dit is een tijdelijke, snelle uitwerking van het maken van een identifier. Op termijn
    // moet dit de ConvertToIdentifier code uit Pascal bevatten, die een release kan voorbereiden
    // qua afgeleide identifier codes (ident_code, ident_db, etc)
    public static String maakIdentifier(final String input) {
        StringBuilder identifier = new StringBuilder();
        // Hoofdletter beginnen na spatie of liggend streepje.
        String[] parts = input.split("[ -]");
        for (String part : parts) {
            part = part.replaceAll("\\.", "").replace("'", "").replace("?", "");
            if (part.length() > 0) {
                identifier.append(upperTheFirstCharacter(part));
            }
        }
        return identifier.toString();
    }

    /**
     * Zie methode met een String als input. Deze methode leest de stream uit in een String
     * en roept die andere methode aan.
     *
     * @param xmlSnippetStream de xml snippet als stream
     * @param wellFormed       of de snippet  well formed xml is of niet
     * @return een lijst van elementen die in de snippet zaten
     */
    public static List<Element> bouwElementenUitXmlSnippet(final InputStream xmlSnippetStream,
                                                           final boolean wellFormed)
    {
        try {
            return bouwElementenUitXmlSnippet(IOUtils.toString(xmlSnippetStream), wellFormed);
        } catch (IOException e) {
            throw new GeneratorExceptie("Exceptie opgetreden bij het opbouwen van elementen uit een stream.", e);
        }
    }

    /**
     * Voeg commentaar over de versies toe aan het (JDOM) document.
     *
     * @param document  het document
     * @param generator de generator om de versie informatie uit te halen.
     */
    public static void voegVersieCommentaarToe(final Document document, final Generator generator) {
        document.addContent(0, new Comment("\r\nMetaregister versie: " + generator.getMetaRegisterVersie() + "\r\n"
                + "Generator versie: " + generator.getVersie() + "\r\n"
                + "Generator gebouwd op: " + generator.getBuildTimestamp() + "\r\n"
                + "Gegenereerd op: " + new java.util.Date().toString() + "\r\n"));
    }


    /**
     * Maakt een lijst van DOM elementen uit een stuk XML snippet. De meegegeven snippet hoeft
     * zelf dus geen well formed XML te zijn.
     * Deze methode negeert eventuele whitespace tussen de tags.
     *
     * @param xmlSnippet de xml snippet als string
     * @param wellFormed of de snippet  well formed xml is of niet
     * @return een lijst van elementen die in de snippet zaten
     */
    public static List<Element> bouwElementenUitXmlSnippet(final String xmlSnippet, final boolean wellFormed) {
        try {
            // Laadt de standaard structuren in vanuit een XML bestand. Dit is veel simpeler dan
            // alles statisch via JDOM objecten aanmaken.
            SAXBuilder saxBuilder = new SAXBuilder();
            // We hebben geen behoefte aan lelijke Text elementen met alleen white space.
            saxBuilder.setIgnoringBoundaryWhitespace(true);

            String snippetAlsDocument = xmlSnippet;
            // Deze dummy wrapper zorgt ervoor dat deze snippet altijd als valid XML document gezien wordt.
            // Na het inlezen moet dit element genegeerd worden en alleen al zijn kinderen worden meegenomen.
            if (!wellFormed) {
                snippetAlsDocument = "<dummyWrapperAlsRootElement>"
                        + snippetAlsDocument
                        + "</dummyWrapperAlsRootElement>";
            }
            Document structureDocument =
                    saxBuilder.build(new ByteArrayInputStream(snippetAlsDocument.getBytes("UTF-8")));
            List<Element> structuurElementen = new ArrayList<>();
            if (wellFormed) {
                // Indien well-formed, hoeven we alleen de root zelf toe te voegen. Wel nog even detachen.
                Element rootElement = structureDocument.getRootElement();
                rootElement.detach();
                structuurElementen.add(rootElement);
            } else {
                // Neem alleen de kinderen en niet het root element, dat is namelijk slechts een dummy wrapper.
                // Maak een kopie van de lijst, omdat de originele lijst juist degene is waarvan we willen detachen. :)
                // Koppel de kinderen los van hun bestaande ouder, zodat ze vrij zijn
                // om ergens anders onder gehangen te worden. Maar alleen als we een dummy hebben toegevoegd.
                structuurElementen.addAll(structureDocument.getRootElement().getChildren());
                for (Element structuurElement : structuurElementen) {
                    structuurElement.detach();
                }
            }
            return structuurElementen;
        } catch (Exception e) {
            throw new GeneratorExceptie("Exceptie opgetreden bij het opbouwen van elementen uit een snippet.", e);
        }
    }

    /**
     * Maak de map structuur van het meegegeven folder pad aan, als die nog niet bestaat.
     *
     * @param mapStructuur de mapStructuur
     */
    public static void maakMapStructuurAan(final String mapStructuur) {
        final File mapStructuurAlsFile = new File(mapStructuur);
        if (!mapStructuurAlsFile.exists()) {
            final boolean zijnFoldersAangemaakt = mapStructuurAlsFile.mkdirs();

            if (!zijnFoldersAangemaakt) {
                throw new IllegalStateException("Folder voor generatie niet aanwezig en kon niet worden aangemaakt.");
            }
        }
    }

}
