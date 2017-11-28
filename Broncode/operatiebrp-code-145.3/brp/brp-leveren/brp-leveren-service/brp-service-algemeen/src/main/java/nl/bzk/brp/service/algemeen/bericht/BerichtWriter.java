/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import java.io.Writer;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Writer voor uitgaande berichten.
 */
public final class BerichtWriter {

    private static final XMLOutputFactory XML_OUTPUT_FACTORY = XMLOutputFactory.newFactory();
    private static final String BRP_NAMESPACE_URI = "http://www.bzk.nl/brp/brp0200";
    private static final String NAMESPACE_PREFIX = "brp";

    private XMLStreamWriter writer;
    private final Writer outputWriter;

    /**
     * Constructor.
     * @param outputWriter de output writer
     */
    public BerichtWriter(final Writer outputWriter) {
        this.outputWriter = outputWriter;
        decorateCall(() -> {
            writer = XML_OUTPUT_FACTORY.createXMLStreamWriter(outputWriter);
            writer.setDefaultNamespace(BerichtWriter.BRP_NAMESPACE_URI);
            writer.setPrefix(BerichtWriter.NAMESPACE_PREFIX, BerichtWriter.BRP_NAMESPACE_URI);
        });
    }

    /**
     * @return de writer
     */
    XMLStreamWriter getWriter() {
        return writer;
    }

    /**
     * Schrijft een start element.
     * @param elementNaam naam van het element.
     */
    public void startElement(final String elementNaam) {
        decorateCall(() -> writer.writeStartElement(NAMESPACE_PREFIX, elementNaam, BRP_NAMESPACE_URI));
    }

    /**
     * Schrijft een element.
     * @param elementNaam naam van het element
     * @param waarde waarde van het element
     */
    public void element(final String elementNaam, final String waarde) {
        decorateCall(() -> {
            writer.writeStartElement(NAMESPACE_PREFIX, elementNaam, BRP_NAMESPACE_URI);
            writer.writeCharacters(waarde);
            writer.writeEndElement();
        });
    }

    /**
     * Schrijft een attribuut.
     * @param attribuutNaam naam van het attribuut
     * @param attribuutValue waarde van het attribuut
     */
    public void attribute(final String attribuutNaam, final String attribuutValue) {
        decorateCall(() -> writer.writeAttribute(NAMESPACE_PREFIX, BRP_NAMESPACE_URI, attribuutNaam, attribuutValue));
    }

    /**
     * Schrijft een end element.
     */
    public void endElement() {
        decorateCall(() -> writer.writeEndElement());
    }

    /**
     * Schrijft de namespace.
     */
    public void writeNamespace() {
        decorateCall(() -> writer.writeNamespace(NAMESPACE_PREFIX, BerichtWriter.BRP_NAMESPACE_URI));
    }

    /**
     * Voert een flush uit op de writer.
     */
    public void flush() {
        decorateCall(() -> writer.flush());
    }

    /**
     * Sluit de writer.
     */
    public void close() {
        decorateCall(() -> writer.close());
    }

    /**
     * Schrijft een start document.
     */
    public void writeStartDocument() {
        decorateCall(() -> writer.writeStartDocument());
    }

    /**
     * Schrijft character data.
     * @param waarde de text
     */
    public void writeCharacters(final String waarde) {
        decorateCall(() -> writer.writeCharacters(waarde));
    }

    public Writer getOutputWriter() {
        return outputWriter;
    }


    private interface WriterCall {

        void call() throws XMLStreamException;
    }

    private void decorateCall(WriterCall writerCall) {
        try {
            writerCall.call();
        } catch (XMLStreamException e) {
            throw new BerichtException(e);
        }
    }
}
