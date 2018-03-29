/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import java.io.OutputStream;
import javax.xml.stream.XMLStreamWriter;

/**
 * Writers kunnen het berichtenmodel van bijhouding omzetten in XML.
 *
 * @param <T> het type dat moet worden omgezet in XML
 */
public interface Writer<T> {

    /**
     * Schrijf de XML voor een bijhoudingsbericht.
     *
     * @param bericht het bijhouding bericht
     * @param writer de writer waar de XML naartoe wordt geschreven
     * @throws WriteException wanneer het bijhoudingbericht niet kan worden weggeschreven als xml
     */
    void write(final T bericht, final java.io.Writer writer) throws WriteException;

    /**
     * Schrijf de XML voor een bijhoudingsbericht.
     *
     * @param bericht het bijhouding bericht
     * @param xmlAsStream de outputstream waar de XML naartoe wordt geschreven
     * @throws WriteException wanneer het bijhoudingbericht niet kan worden weggeschreven als xml
     */
    void write(final T bericht, final OutputStream xmlAsStream) throws WriteException;

    /**
     * Schrijf de XML voor een bijhoudingsbericht.
     *
     * @param bericht het bijhouding bericht
     * @param xmlStreamWriter de XmlStreamWriter waar de XML naartoe wordt geschreven
     * @throws WriteException wanneer het bijhoudingbericht niet kan worden weggeschreven als xml
     */
    void write(final T bericht, final XMLStreamWriter xmlStreamWriter) throws WriteException;

    /**
     * Schrijft XML voor een bijhoudingselement op basis van de gegeven context.
     *
     * @param element element
     * @param context context
     * @throws WriteException wanneer de xml input niet kan worden omgezet in een bijhoudingsbericht
     */
    void write(final T element, final WriterContext context) throws WriteException;
}
