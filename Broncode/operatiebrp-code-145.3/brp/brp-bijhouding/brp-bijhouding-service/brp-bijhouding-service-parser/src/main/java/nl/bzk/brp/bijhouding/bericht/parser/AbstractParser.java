/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import nl.bzk.brp.bijhouding.bericht.model.Element;

import java.io.InputStream;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;

/**
 * Deze class bevat de basisfunctionaliteit voor de parsers van het bijhoudingsmodel.
 *
 * @param <T> het resultaat type van deze parser
 */
public abstract class AbstractParser<T> implements Parser<T> {

    private static final XMLInputFactory FACTORY = XMLInputFactory.newFactory();

    @Override
    public final T parse(final InputStream xmlAsStream) throws ParseException {
        return parseObject(xmlAsStream);
    }

    @Override
    public final T parse(final Source source) throws ParseException {
        return parseObject(source);
    }

    @Override
    public final T parse(final XMLStreamReader xmlStreamReader) throws ParseException {
        return parse(new ParserContext(xmlStreamReader));
    }

    @Override
    public final T parse(final ParserContext context) throws ParseException {
        if (!context.isStartElement()) {
            throw new ParseException("De parser verwijst niet naar een start element tag.");
        }
        final String elementNaam = context.getEventNaam();
        if (!getElementNamen().contains(elementNaam)) {
            throw new ParseException(
                String.format(
                    "Het element '%s' kan niet worden verwerkt door deze parser, deze kan alleen volgende elementen verwerken: '%s'.",
                    elementNaam,
                    getElementNamen()));
        }
        verwerkStartElement(context);
        verwerkAttributen(context.getAttributenMap());
        return registreerElement(context, verwerkChildElementenEnElementEinde(elementNaam, context));
    }

    private T registreerElement(final ParserContext context, final T object) throws ParseException {
        if (object instanceof Element) {
            context.registeerElement((Element) object);
        }
        return object;
    }

    private T verwerkChildElementenEnElementEinde(final String elementNaam, final ParserContext context) throws ParseException {
        while (context.heeftVolgendeEvent()) {
            final int event = context.volgendeEvent();
            if (XMLEvent.START_ELEMENT == event) {
                verwerkChildElement(context);
            } else if (XMLEvent.END_ELEMENT == event) {
                if (!elementNaam.equals(context.getEventNaam())) {
                    throw new ParseException(
                        String.format("Onverwacht element einde '%s'. Verwacht einde element is '%s'.", context.getEventNaam(), elementNaam));
                }
                return verwerkEindeElement(context);
            }
        }
        throw new ParseException(String.format("Er is geen einde tag gevonden voor element '%s'", elementNaam));
    }

    /**
     * Dit wordt aangeroepen op het moment dat een start element tag is aangetroffen van een element dat verwerkt kan
     * worden door deze parser. Hiervoor wordt gebruikt gemaakt van {@link #getElementNamen()}.
     *
     * @param context de context
     * @throws ParseException wanneer er fouten optreden tijdens het parsen van de XML
     */
    protected abstract void verwerkStartElement(ParserContext context) throws ParseException;

    /**
     * Dit wordt aangeroepen op het moment dat een einde element tag is aangetroffen van een element dat verwerkt kan
     * worden door deze parser.
     *
     * @param context de context
     * @return het resultaat object van het parsen
     * @throws ParseException wanneer er fouten optreden tijdens het parsen van de XML
     */
    protected abstract T verwerkEindeElement(ParserContext context) throws ParseException;

    /**
     * Dit wordt aangeroepen na {@link #verwerkStartElement(ParserContext)} op het moment dat er één of meerdere
     * attributen worden aangetroffen.
     *
     * @param attributenMap en verzameling attribuut namen met bijbehorende waarde
     * @throws ParseException wanneer er fouten optreden tijdens het parsen van de XML
     */
    protected abstract void verwerkAttributen(Map<String, String> attributenMap) throws ParseException;

    /**
     * Dit wordt aangeroepen na {@link #verwerkStartElement(ParserContext)} op het moment dat er één of meerdere child
     * elementen worden aangetroffen. De context zal op dit moment naar het child element verwijzen. Hier kan worden
     * gekozen om de text van het element te lezen of om de verdere afhandeling te delegeren naar een volgende parser.
     * Bij het einde van deze methode dient het event van de einde tag van het corresponderende element te zijn
     * afgehandeld.
     *
     * @param context de context
     * @throws ParseException wanneer er fouten optreden tijdens het parsen van de XML
     */
    protected abstract void verwerkChildElement(ParserContext context) throws ParseException;

    /**
     * Gebruik dit om een ParseException te gooien wanneer tijdens het parsen een onverwacht element wordt aangetroffen.
     *
     * @param parentNaam de naam van het parent element waarin het onverwachte child element is aangetroffen
     * @param childNaam de naam van het onverwachte child element
     * @throws ParseException de exceptie
     */
    protected final void onverwachtElement(final String parentNaam, final String childNaam) throws ParseException {
        throw new ParseException(String.format("Onverwacht child element '%s' in element '%s'", childNaam, parentNaam));
    }

    private T parseObject(final Object sourceOrStream) throws ParseException {
        try {
            final XMLStreamReader xmlStreamReader;
            if (sourceOrStream instanceof InputStream) {
                xmlStreamReader = FACTORY.createXMLStreamReader((InputStream) sourceOrStream);
            } else if (sourceOrStream instanceof Source) {
                xmlStreamReader = FACTORY.createXMLStreamReader((Source) sourceOrStream);
            } else {
                throw new IllegalArgumentException("Verwacht Source of InputStream");
            }
            // positioneer de cursor van de reader op het eerste element
            xmlStreamReader.nextTag();
            return parse(xmlStreamReader);
        } catch (XMLStreamException e) {
            throw new ParseException("Fout bij het maken van een XmlStreamReader.", e);
        }
    }
}
