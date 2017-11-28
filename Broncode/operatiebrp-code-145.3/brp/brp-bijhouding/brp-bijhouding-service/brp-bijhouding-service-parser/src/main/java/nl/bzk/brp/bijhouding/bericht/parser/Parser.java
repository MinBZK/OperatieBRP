/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.io.InputStream;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;

/**
 * Parsers kunnen XML omzetten in het berichtenmodel voor bijhouding.
 *
 * @param <T> het resultaat type van deze parser
 */
public interface Parser<T> {

    /**
     * Maakt een bericht op basis van de gegeven inputstream.
     *
     * @param xmlAsStream het xml document als inputstream
     * @return het bericht
     * @throws ParseException wanneer de xml input niet kan worden omgezet in een bericht
     */
    T parse(final InputStream xmlAsStream) throws ParseException;

    /**
     * Maakt een bericht op basis van de gegeven inputstream.
     *
     * @param xmlStreamReader het xml document als XmlStreamReader
     * @return het bericht
     * @throws ParseException wanneer de xml input niet kan worden omgezet in een bericht
     */
    T parse(final XMLStreamReader xmlStreamReader) throws ParseException;

    /**
     * Maakt een bericht op basis van de gegeven {@link javax.xml.transform.Source}.
     *
     * @param source het xml document als source
     * @return het bericht
     * @throws ParseException wanneer de xml input niet kan worden omgezet in een bericht
     */
    T parse(final Source source) throws ParseException;

    /**
     * Maakt een bijhoudingselement op basis van de gegeven xml stream reader.
     *
     * @param context context
     * @return het BijhoudingVerzoekBericht
     * @throws ParseException wanneer de xml input niet kan worden omgezet in een bijhoudingsbericht
     */
    T parse(final ParserContext context) throws ParseException;

    /**
     * Geef de lijst van namen van de elementen die deze parser kan parsen.
     *
     * @return elementNaam
     */
    List<String> getElementNamen();
}
