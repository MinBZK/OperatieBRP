/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.xml;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

/**
 * Abstract XML support.
 */
public abstract class AbstractXml {

    private final JAXBContext jaxbContext;
    private final Schema schema;

    /**
     * Constructor.
     * @param xsdResource xsd resource
     * @param jaxbPackages jaxb packages
     */
    protected AbstractXml(final String xsdResource, final String jaxbPackages) {
        try {
            // Schema's
            final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            schemaFactory.setResourceResolver(maakResolver());

            schema = schemaFactory.newSchema(new Source[]{new StreamSource(AbstractXml.class.getResourceAsStream(xsdResource)),});

            // JAXB
            jaxbContext = JAXBContext.newInstance(jaxbPackages);

        } catch (final
        JAXBException
                | SAXException e) {
            throw new IllegalStateException("Parse fout tijdens het initialiseren van BrpXml.", e);
        }
    }

    /**
     * Maak een marshaller.
     * @return marshaller
     * @throws JAXBException bij fouten
     */
    private Marshaller createMarchaller() throws JAXBException {
        return jaxbContext.createMarshaller();
    }

    /**
     * Maak een unmarshaller.
     * @return unmarshaller
     * @throws JAXBException bij fouten
     */
    private Unmarshaller createUnmarchaller() throws JAXBException {
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        return unmarshaller;
    }

    /**
     * Maae een resolver om recursief alle XSD's te resolven.
     * @return De gemaakte resolver.
     */
    protected abstract LSResourceResolver maakResolver();

    /**
     * Converteert een String naar een JAXB element.
     * @param xml de string representatie die geconverteerd dient te worden
     * @return het JAXB element
     * @throws JAXBException bij fouten
     */
    public final JAXBElement<?> stringToElement(final String xml) throws JAXBException {
        return (JAXBElement<?>) createUnmarchaller().unmarshal(new StreamSource(new StringReader(xml)));
    }

    /**
     * Converteert een JAXB element naar een String.
     * @param element Het JAXB element dat geconverteerd dient te worden.
     * @return De string representatie van het JAXB element.
     */
    public final String elementToString(final JAXBElement<?> element) {
        final StringWriter resultWriter = new StringWriter();
        try {
            createMarchaller().marshal(element, resultWriter);
            return resultWriter.toString();
        } catch (final JAXBException e) {
            throw new IllegalArgumentException("Fout tijdens het marshallen van een jaxb element.", e);
        }
    }
}
