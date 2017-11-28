/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.Configuration;
import nl.bzk.algemeenbrp.util.xml.model.ConfigurationHelper;
import nl.bzk.algemeenbrp.util.xml.model.Root;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Xml.
 */
public final class Xml {

    private static final Configuration DEFAULT_CONFIGURATION = new Configuration();

    private Xml() {
        // Niet instantieerbaar
    }

    /**
     * Encodeer een gegeven object.
     * @param configuration configuratie (bij null wordt de default configuratie gebruikt)
     * @param object object
     * @param writer writer om XML naar te schrijven
     * @param <T> type van het object
     * @throws ConfigurationException bij configuratie problemen (annoties op de klassen)
     * @throws EncodeException bij encodeer problemen
     */
    public static <T> void encode(final Configuration configuration, final T object, final Writer writer) throws XmlException {
        if (object == null) {
            return;
        }

        final Context context = new Context();
        final Configuration theConfiguration = configuration == null ? DEFAULT_CONFIGURATION : configuration;
        ConfigurationHelper.setConfiguration(context, theConfiguration);

        @SuppressWarnings("unchecked")
        final Class<T> objectClass = (Class<T>) object.getClass();
        final Root<T> item = theConfiguration.getModelFor(objectClass);
        item.encode(context, object, writer);
    }

    /**
     * Decodeer een object.
     * @param configuration configuratie
     * @param clazz te decoderen object
     * @param reader reader om XML van de lezen
     * @param <T> type van het object
     * @return het gedecodeerde object
     * @throws ConfigurationException bij configuratie problemen (annoties op de klassen)
     * @throws DecodeException bij decodeer problemen
     */
    public static <T> T decode(final Configuration configuration, final Class<T> clazz, final Reader reader) throws XmlException {
        final Context context = new Context();
        final Configuration theConfiguration = configuration == null ? DEFAULT_CONFIGURATION : configuration;
        ConfigurationHelper.setConfiguration(context, theConfiguration);

        final Root<T> item = theConfiguration.getModelFor(clazz);

        // Jaxp first
        final Document document;
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            final DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(reader));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new DecodeException(context.getElementStack(), e);
        }

        return item.decode(context, document);
    }

}
