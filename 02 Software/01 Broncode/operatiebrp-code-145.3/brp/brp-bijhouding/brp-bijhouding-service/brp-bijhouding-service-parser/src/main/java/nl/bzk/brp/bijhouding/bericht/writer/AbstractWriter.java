/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import java.io.OutputStream;
import java.util.Map;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import nl.bzk.brp.bijhouding.bericht.model.BmrGroep;

/**
 * Deze class bevat de basisfunctionaliteit voor de writers van het bijhoudingsmodel.
 *
 * @param <T> het type dat moet worden omgezet in XML
 */
public abstract class AbstractWriter<T> implements Writer<T> {

    private static final XMLOutputFactory FACTORY = XMLOutputFactory.newFactory();

    @Override
    public final void write(final T bericht, final java.io.Writer writer) throws WriteException {
        try {
            write(bericht, FACTORY.createXMLStreamWriter(writer));
        } catch (XMLStreamException e) {
            throw new WriteException("Fout bij het schrijven van XML voor een bijhoudingsbericht. ", e);
        }
    }

    @Override
    public final void write(final T bericht, final OutputStream xmlAsStream) throws WriteException {
        try {
            write(bericht, FACTORY.createXMLStreamWriter(xmlAsStream));
        } catch (XMLStreamException e) {
            throw new WriteException("Fout bij het schrijven van XML voor een bijhoudingsbericht.", e);
        }
    }

    @Override
    public final void write(final T bericht, final XMLStreamWriter xmlStreamWriter) throws WriteException {
        final WriterContext context = new WriterContext(xmlStreamWriter);
        write(bericht, context);
        context.flush();
    }

    @Override
    public final void write(final T element, final WriterContext context) throws WriteException {
        final boolean heeftChildElementen = heeftChildElementen(element);
        if (heeftChildElementen) {
            context.writeStartElement(getElementNaam(element));
        } else {
            context.writeEmptyElement(getElementNaam(element));
        }
        if (element instanceof BmrGroep) {
            writeAttributen((BmrGroep) element, context);
        }
        if (heeftChildElementen) {
            writeElementInhoud(element, context);
            context.writeEndElement();
        }
    }

    /**
     * Bepaald of het element child elementen heeft of niet.
     *
     * @param element het element
     * @return true als het element child elementen heeft, anders false
     */
    protected abstract boolean heeftChildElementen(final T element);

    /**
     * Schrijf de inhoud van het element weg naar XML.
     *
     * @param element het element
     * @param context de context
     * @throws WriteException wanneer de xml input niet kan worden omgezet in een bijhoudingsbericht
     */
    protected abstract void writeElementInhoud(final T element, final WriterContext context) throws WriteException;

    /**
     * Geeft de naam van het element.
     *
     * @param element het element
     * @return de element naam
     */
    protected abstract String getElementNaam(final T element);

    /*
     * Schrijft de attributen van een BmrGroep naar XML.
     *
     * @param groep de groep
     * 
     * @param context de context
     * 
     * @throws WriteException als het schrijven fout gaat
     */
    private void writeAttributen(final BmrGroep groep, final WriterContext context) throws WriteException {
        try {
            final Map<String, String> attributen = groep.getAttributen();
            for (Map.Entry<String, String> attribuutEntry : attributen.entrySet()) {
                context.writeAttribute(attribuutEntry.getKey(), attribuutEntry.getValue());
            }
        } catch (WriteException e) {
            throw new WriteException("Fout bij het schrijven van de basis attributen voor een groep.", e);
        }
    }
}
