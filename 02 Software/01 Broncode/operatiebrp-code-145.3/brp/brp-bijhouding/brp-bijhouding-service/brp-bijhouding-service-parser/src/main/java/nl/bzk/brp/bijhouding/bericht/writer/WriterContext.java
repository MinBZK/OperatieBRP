/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import java.util.Deque;
import java.util.LinkedList;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import nl.bzk.brp.bijhouding.bericht.model.BmrAttribuut;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De context die gedeeld wordt tussen writers gedurerende het write proces van een bijhoudingsbericht.
 */
final class WriterContext {

    private static final String BRP_NAMESPACE_PREFIX = "brp";
    private static final String BRP_NAMESPACE_URI = "http://www.bzk.nl/brp/brp0200";
    private static final String FOUTMELDING = "Fout tijdens het schrijven van XML";
    private static final String NEW_LINE = String.format("%n");
    private static final String IDENT_STEP = "    ";
    private static final Object SEEN_NOTHING = new Object();
    private static final Object SEEN_ELEMENT = new Object();
    private static final Object SEEN_DATA = new Object();

    private final XMLStreamWriter writer;

    private Object state;
    private final Deque<Object> stateStack;
    private int identationDepth;

    private boolean isNamespaceGeschreven;

    /**
     * Maakt een nieuw WriterContext object.
     *
     * @param writer de XMLStreamWriter
     */
    WriterContext(final XMLStreamWriter writer) {
        ValidatieHelper.controleerOpNullWaarde(writer, "writer");
        this.writer = writer;
        this.state = SEEN_NOTHING;
        this.stateStack = new LinkedList<>();
        this.identationDepth = 0;
    }

    /**
     * Schrijft een BMR-attribuut element naar XML. Als element null is doet deze methode niets.
     *
     * @param elementNaam de naam van het element
     * @param element het element
     * @throws WriteException wanneer het schrijven fout gaat
     */
    void writeBmrAttribuut(final String elementNaam, final BmrAttribuut element) throws WriteException {
        if (element != null) {
            writeStartElement(elementNaam);
            writeText(element.toString());
            writeEndElement();
        }
    }

    /**
     * Schrijf een start element tag.
     *
     * @param elementNaam de naam van het element
     * @throws WriteException wanneer het schrijven fout gaat
     */
    void writeStartElement(final String elementNaam) throws WriteException {
        try {
            this.stateStack.push(SEEN_ELEMENT);
            this.state = SEEN_NOTHING;
            if (identationDepth > 0) {
                writer.writeCharacters(NEW_LINE);
                identation();
            }
            identationDepth++;
            writer.writeStartElement(BRP_NAMESPACE_PREFIX, elementNaam, BRP_NAMESPACE_URI);
            writeNamespace();
        } catch (XMLStreamException e) {
            throw new WriteException(FOUTMELDING, e);
        }
    }

    /**
     * Schrijf een leeg element tag.
     *
     * @param elementNaam de naam van het element
     * @throws WriteException wanneer het schrijven fout gaat
     */
    void writeEmptyElement(final String elementNaam) throws WriteException {
        try {
            this.state = SEEN_ELEMENT;
            if (identationDepth > 0) {
                writer.writeCharacters(NEW_LINE);
                identation();
            }
            writer.writeEmptyElement(BRP_NAMESPACE_PREFIX, elementNaam, BRP_NAMESPACE_URI);
            writeNamespace();
        } catch (XMLStreamException e) {
            throw new WriteException(FOUTMELDING, e);
        }
    }

    /**
     * Schrijft een attribuut naar de XML.
     *
     * @param naam naam
     * @param waarde waarde
     * @throws WriteException wanneer het schrijven fout gaat
     */
    void writeAttribute(final String naam, final String waarde) throws WriteException {
        try {
            writer.writeAttribute(BRP_NAMESPACE_PREFIX, BRP_NAMESPACE_URI, naam, waarde);
        } catch (XMLStreamException e) {
            throw new WriteException(FOUTMELDING, e);
        }
    }

    /**
     * Schrijf het einde van een tag.
     *
     * @throws WriteException wanneer het schrijven fout gaat
     */
    void writeEndElement() throws WriteException {
        try {
            identationDepth--;
            if (this.state == SEEN_ELEMENT) {
                writer.writeCharacters(NEW_LINE);
                identation();
            }
            this.state = this.stateStack.pop();
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            throw new WriteException(FOUTMELDING, e);
        }
    }

    /**
     * Schrijf eventueel gecached data weg naar de onderliggende stream.
     *
     * @throws WriteException wanneer het schrijven fout gaat
     */
    void flush() throws WriteException {
        try {
            writer.flush();
        } catch (XMLStreamException e) {
            throw new WriteException(FOUTMELDING, e);
        }
    }

    private void identation() throws XMLStreamException {
        for (int index = 0; index < identationDepth; index++) {
            writer.writeCharacters(IDENT_STEP);
        }
    }

    private void writeText(final String text) throws WriteException {
        try {
            this.state = SEEN_DATA;
            writer.writeCharacters(text);
        } catch (XMLStreamException e) {
            throw new WriteException(FOUTMELDING, e);
        }
    }

    private void writeNamespace() throws XMLStreamException {
        if (!isNamespaceGeschreven) {
            writer.writeNamespace(BRP_NAMESPACE_PREFIX, BRP_NAMESPACE_URI);
            writer.setPrefix(BRP_NAMESPACE_PREFIX, BRP_NAMESPACE_URI);
            isNamespaceGeschreven = true;
        }
    }
}
